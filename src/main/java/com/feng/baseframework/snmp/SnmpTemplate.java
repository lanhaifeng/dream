package com.feng.baseframework.snmp;

import com.feng.baseframework.constant.ResultEnum;
import com.feng.baseframework.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.*;

/**
 * baseframework
 * 2020/12/12 11:12
 * snmp模板类
 *
 * @author lanhaifeng
 */
public class SnmpTemplate extends AbstractSnmp {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnmpTemplate.class);
    private static String IP_V4_FORMATE = "udp:%s/%s";
    private static String IP_V6_FORMATE = "udp:[%s]/%s";
    private Snmp snmp;
    private SnmpAuth snmpAuth;

    /**
     * 初始化方法
     * @param snmpAuth
     */
    private void init(SnmpAuth snmpAuth){
        if(Objects.isNull(snmpAuth) || !snmpAuth.validate()){
            throw new BusinessException(ResultEnum.PARAM_ILLEGAL_ERROR);
        }
        try {
            TransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            if (snmpAuth.getVersion() == SnmpConstants.version3) {
                // 设置安全模式
                //USM模式
                if(snmpAuth.getSecurityModel() == SecurityModel.SECURITY_MODEL_USM){
                    USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
                    SecurityModels.getInstance().addSecurityModel(usm);
                    // 添加用户
                    snmp.getUSM().addUser(new OctetString(snmpAuth.getUserName()), buildUsmUser(snmpAuth));
                }
                if(snmpAuth.getSecurityModel() == SecurityModel.SECURITY_MODEL_TSM
                        || snmpAuth.getSecurityModel() == SecurityModel.SECURITY_MODEL_TSM){
                    throw new BusinessException("不支持授权模式：" + snmpAuth.getSecurityModel());
                }
            }
            // 开始监听消息
            transport.listen();
        } catch (Exception e) {
            LOGGER.error("初始化snmp失败，错误：" + ExceptionUtils.getFullStackTrace(e));
        }
    }

    public SnmpTemplate(SnmpAuth snmpAuth) {
        this.snmpAuth = snmpAuth;
        init(snmpAuth);
    }

    private Target createTarget() {
        Target target = null;
        if (snmpAuth.getVersion() == SnmpConstants.version3) {
            target = new UserTarget();
            // 设置安全级别
            ((UserTarget) target).setSecurityLevel(snmpAuth.getSecurityLevel());
            ((UserTarget) target).setSecurityModel(snmpAuth.getSecurityModel());
            ((UserTarget) target).setSecurityName(new OctetString(snmpAuth.getUserName()));
        } else {
            target = new CommunityTarget();
            if (snmpAuth.getVersion() == SnmpConstants.version1) {
                target.setVersion(SnmpConstants.version1);
                ((CommunityTarget) target).setCommunity(new OctetString(snmpAuth.getCommunity()));
            } else {
                target.setVersion(SnmpConstants.version2c);
                ((CommunityTarget) target).setCommunity(new OctetString(snmpAuth.getCommunity()));
            }
        }
        target.setVersion(snmpAuth.getVersion());
        //必须指定，没有设置就会报错。
        target.setAddress(GenericAddress.parse(String.format(IP_V4_FORMATE, snmpAuth.getIp(), snmpAuth.getPort())));
//		target.setRetries(3);  //不停轮询所以无需重试
        target.setTimeout(1000);
        return target;
    }

    private PDU createPDU(int type, String oid){
        PDU pdu = null;
        if (snmpAuth.getVersion() == SnmpConstants.version3) {
            pdu = new ScopedPDU();
        }else {
            pdu = new PDUv1();
        }
        pdu.setType(type);
        //可以添加多个变量oid
        pdu.add(new VariableBinding(new OID(oid)));
        return pdu;
    }

    private static boolean checkWalkFinished(OID targetOID, PDU pdu, VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
            LOGGER.error("[true] responsePDU.getErrorStatus() != 0 ");
            LOGGER.error(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            LOGGER.error("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
            LOGGER.error("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            //LOGGER.debug("[true] targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            LOGGER.error("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            LOGGER.info(vb.toString() + " <= " + targetOID);
            finished = true;
        } else if(vb.getVariable().toString().equals("noSuchObject")){
            LOGGER.error(targetOID.toString() + " noSuchObject ");
            finished = true;
        }
        return finished;

    }

    private static boolean checkGetReponseStatu(PDU pdu, VariableBinding vb){
        boolean illegal = false;
        if(pdu.getErrorStatus() != 0){
            illegal = true;
        }else if(vb.getVariable().toString().equals("noSuchObject")){
            illegal = true;
        }else if(Null.isExceptionSyntax(vb.getVariable().getSyntax())){
            illegal = true;
        }

        return illegal;
    }

    public long snmpGet(String oid){
        long count = 0;
//		LOGGER.debug("snmpget发送PDU");
        try {
            //1、初始化snmp,并开启监听
            //initSnmp(SnmpConstants.version2c);
            //2、创建目标对象
            Target target = createTarget();
            //3、创建报文
            PDU pdu = createPDU(PDU.GET, oid);
            //4、发送报文，并获取返回结果
            ResponseEvent responseEvent = snmp.send(pdu, target);
            PDU response = responseEvent.getResponse();
            System.out.println("snmpget返回结果：" + response);
            if(response == null || checkGetReponseStatu(response, response.get(0))){
                return -1;
            }
            count = response.get(0).getVariable().toLong();
//			LOGGER.debug("snmpget返回结果：" + response);
        } catch (Exception e) {
            count = -1;
            LOGGER.error("snmpGet出现异常：" + ExceptionUtils.getFullStackTrace(e));
        }
        return count;
    }

    public String snmpGetString(String oid){
        String result = null;
        try {
            //2、创建目标对象
            Target target = createTarget();
            //3、创建报文
            PDU pdu = createPDU(PDU.GET, oid);
            //4、发送报文，并获取返回结果
            ResponseEvent responseEvent = snmp.send(pdu, target);
            PDU response = responseEvent.getResponse();
            if(response == null || checkGetReponseStatu(response, response.get(0))){
                return result;
            }
            result = response.get(0).getVariable().toString();
        } catch (Exception e) {
            result = null;
            LOGGER.error("snmpGet出现异常：" + ExceptionUtils.getFullStackTrace(e));
        }
        return result;
    }

    public List<Entry> snmpWalk(String oid) {
        List<Entry> result = new ArrayList<>();
        //LOGGER.debug("snmpwalk发送PDU");
        try {
            //1、初始化snmp,并开启监听
            //initSnmp(SnmpConstants.version2c);
            //2、创建目标对象
            Target target = createTarget();
            //3、创建报文
            PDU pdu = createPDU(PDU.GETNEXT, oid);

            //4、发送报文，并获取返回结果
            boolean matched = true;

            while (matched) {
                ResponseEvent responseEvent = snmp.send(pdu, target);
                if (responseEvent == null || responseEvent.getResponse() == null) {
                    break;
                }
                PDU response = responseEvent.getResponse();
                String nextOid = null;
                Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
                for (int i = 0; i < variableBindings.size(); i++) {
                    VariableBinding variableBinding = variableBindings.elementAt(i);
                    Variable variable = variableBinding.getVariable();
                    nextOid = variableBinding.getOid().toDottedString();
                    //如果不是这个节点下的oid则终止遍历，否则会输出很多，直到整个遍历完。
                    if (!nextOid.startsWith(oid) && checkWalkFinished(new OID(oid), pdu, variableBinding)) {
                        matched = false;
                        break;
                    }
                    //LOGGER.debug("variable:" + variable.toString());
                    result.add(new Entry(oid, nextOid, variable.toString()));
                }
                if (!matched) {
                    break;
                }
                pdu.clear();
                pdu.add(new VariableBinding(new OID(nextOid)));
                //LOGGER.debug("snmpwalk返回结果：" + response);
            }
        } catch (Exception e) {
            LOGGER.error("snmp walk失败，错误：" + ExceptionUtils.getFullStackTrace(e));
            return null;
        }

        return result;
    }

    static class Entry{
        String sourceOid;
        String nextOid;
        String index;
        String value;

        Entry(String sourceOid, String nextOid, String value) {
            this.sourceOid = sourceOid;
            this.nextOid = nextOid;
            parseIndex(sourceOid, nextOid);
            this.value = value;
        }

        private void parseIndex(String sourceOid, String nextOid){
            String index = "";
            if(StringUtils.isNotBlank(sourceOid) && StringUtils.isNotBlank(nextOid) && !sourceOid.equals(nextOid)
                    && nextOid.compareTo(sourceOid) > 0){
                index = nextOid.replace(sourceOid + ".", "");
            }

            this.index = index;
        }
    }

    public static void main(String[] args) {
        //net-snmp-create-v3-user -ro -A auth123456 -X priv123456 -a MD5 -x DES lan
        String ip = "192.168.230.206";
        int port = 161;
        int version = SnmpConstants.version1;
        int securityLevel = SecurityLevel.AUTH_PRIV;
        int securityModel = SecurityModel.SECURITY_MODEL_SNMPv1;
        String community = "hzmc+Ra2$yuL";
        String userName = "lan";
        String authType = SnmpV3AuthType.MD5.toString();
        String passAuth = "auth123456";
        String privType = SnmpV3PrivType.DES.toString();
        String privPass = "priv123456";

        version = SnmpConstants.version3;
        securityLevel = SecurityLevel.AUTH_PRIV;
        securityModel = SecurityModel.SECURITY_MODEL_USM;

        SnmpAuth snmpAuth = SnmpAuth.builder().ip(ip).port(port).version(version).securityLevel(securityLevel).securityModel(securityModel)
                .community(community)
                .userName(userName).authType(authType).passAuth(passAuth).privType(privType).privPass(privPass)
                .build();
        SnmpTemplate snmpTemplate = new SnmpTemplate(snmpAuth);

        Long now = new Date().getTime();
        System.out.println("now:" + now);

        long count = snmpTemplate.snmpGet("1.3.6.1.4.1.2021.11.11.0");
        System.out.println(String.format("idle cpu:%s", (count)) + "%");
        System.out.println(String.format("used cpu:%s", (100 - count)) + "%");

        SnmpTemplate snmpTemplate1 = new SnmpTemplate(snmpAuth);
        count = snmpTemplate1.snmpGet("1.3.6.1.4.1.2021.4.5.0");
        System.out.println(String.format("total memory:%s", (count/1024.0/1024)));
    }
}
