package com.feng.baseframework.snmp;

import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.OctetString;

/**
 * baseframework
 * 2020/8/6 16:00
 *
 * @author lanhaifeng
 * @since
 **/
public abstract class AbstractSnmp {

	/**
	 * 2020/8/6 15:58
	 * 构建v3认证用户
	 *
	 * @param securityLevel
	 * @param userName
	 * @param authPass
	 * @param privPass
	 * @author lanhaifeng
	 * @return org.snmp4j.security.UsmUser
	 */
	public UsmUser buildUsmUser(SecurityLevel securityLevel, String userName, String authPass, String privPass){
		UsmUser usmUser = null;
		switch (securityLevel){
			case noAuthNoPriv:
				usmUser = new UsmUser(new OctetString(String.valueOf(securityLevel.getSnmpValue())),
						null, null, null, null);
				break;
			case authNoPriv:
				usmUser = new UsmUser(new OctetString(userName), AuthMD5.ID,
						new OctetString(authPass), null, null);
				break;
			case authPriv:
				usmUser = new UsmUser(new OctetString(userName),AuthMD5.ID,
						new OctetString(authPass), PrivDES.ID, new OctetString(privPass));
				break;
		}

		return usmUser;
	}
}
