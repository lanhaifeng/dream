package com.feng.baseframework.snmp;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * baseframework
 * 2020/8/4 19:49
 * snmp trap发送
 *
 * @author lanhaifeng
 * @since
 **/
public class SnmpTrapSender extends AbstractSnmp {

	private static Logger log = LoggerFactory.getLogger(SnmpTrapSender.class);
	private final static String ADDRESS_TEMPLATE = "udp:%s/%s";
	private Snmp snmp = null;

	public void initComm(SnmpAuth snmpAuth) throws IOException {
		TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		if (snmpAuth.getVersion() == SnmpConstants.version3) {
			USM usm = snmp.getUSM();
			if (Objects.isNull(usm)) {
				usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
			}
			SecurityModels.getInstance().addSecurityModel(usm);
			usm.addUser(buildUsmUser(SecurityLevel.get(snmpAuth.getSecurityLevel()),
					snmpAuth.getUserName(), snmpAuth.getPassAuth(), snmpAuth.getPrivatePass()));
		}
		transport.listen();
	}

	/**
	 * 2020/8/5 16:37
	 * 构建目标地址
	 *
	 * @param targetIp
	 * @param targetPort
	 * @return org.snmp4j.smi.Address
	 * @author lanhaifeng
	 */
	public Address buildAddress(String targetIp, int targetPort) {
		return GenericAddress.parse(String.format(ADDRESS_TEMPLATE, targetIp, String.valueOf(targetPort)));
	}


	/**
	 * 2020/8/5 16:32
	 * 构建target
	 *
	 * @param snmpAuth
	 * @param targetAddress
	 * @return org.snmp4j.CommunityTarget
	 * @author lanhaifeng
	 */
	public Target buildCommunityTarget(SnmpAuth snmpAuth, Address targetAddress) {
		// 设置 target
		Target target = null;

		int version = snmpAuth.getVersion();
		//版本1和2c基于团体名验证
		if (version == SnmpConstants.version1 || version == SnmpConstants.version2c) {
			// 目标地址和团体名
			target = new CommunityTarget(targetAddress, new OctetString(snmpAuth.getCommunity()));
		}
		if (version == SnmpConstants.version3) {
			target = new UserTarget();
			if (snmpAuth.getSecurityLevel() == SecurityLevel.AUTH_NOPRIV
					|| snmpAuth.getSecurityLevel() == SecurityLevel.AUTH_PRIV) {
				target.setSecurityName(new OctetString(snmpAuth.getUserName()));
			}
			target.setSecurityLevel(snmpAuth.getSecurityLevel());
			target.setSecurityModel(snmpAuth.getSecurityModel());
			//设置目标地址
			target.setAddress(targetAddress);
		}

		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		// snmp版本
		target.setVersion(version);
		return target;
	}

	/**
	 * 2020/8/5 19:33
	 * 解析返回结果
	 *
	 * @param respEvnt
	 * @return void
	 * @author lanhaifeng
	 */
	public void parseResponse(ResponseEvent respEvnt) {
		if (respEvnt != null && respEvnt.getResponse() != null) {
			List<? extends VariableBinding> recVBs = respEvnt.getResponse()
					.getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.get(i);
				log.info(recVB.getOid() + " : " + recVB.getVariable());
			}
		}
	}

	/**
	 * 向管理进程发送Trap 1报文
	 *
	 * @param snmpAuth
	 * @param datas
	 * @param notification
	 * @return void
	 * @author lanhaifeng
	 */
	public void sendPDU(SnmpAuth snmpAuth, List<VariableBinding> datas, String notification) throws IOException {
		Address address = buildAddress(snmpAuth.getIp(), snmpAuth.getPort());
		int version = snmpAuth.getVersion();
		Target target = buildCommunityTarget(snmpAuth, address);

		// 创建 PDU
		PDU pdu = null;
		if (version == SnmpConstants.version1) {
			pdu = new PDUv1();
		}
		if (version == SnmpConstants.version2c) {
			pdu = new PDU();
			pdu.setType(PDU.TRAP);
		}
		if (version == SnmpConstants.version3) {
			pdu = new ScopedPDU();
			pdu.setType(PDU.NOTIFICATION);
		}

		if (datas == null || pdu == null) {
			return;
		}

		//define snmp trap OID
		//解决问题：Cannot find TrapOID in TRAP2 PDU
        //trap2时需要传oid为SnmpConstants.snmpTrapOID，值为发送trap的oid
		if (version == SnmpConstants.version2c || version == SnmpConstants.version3) {
			datas.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(notification)));
		}

		//构造数据
		pdu.addAll(datas);

		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);

		// 解析Response
		parseResponse(respEvnt);
	}

	/**
	 * 2020/8/11 14:51
	 * 关闭snmp
	 *
	 * @param
	 * @return void
	 * @author lanhaifeng
	 */
	public void close() {
		try {
			if (snmp != null) {
				snmp.close();
			}
		} catch (Exception e) {
			log.error("关闭snmp失败，错误：" + ExceptionUtils.getFullStackTrace(e));
		}
	}

}