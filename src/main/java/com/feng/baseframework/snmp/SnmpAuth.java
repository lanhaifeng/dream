package com.feng.baseframework.snmp;

import lombok.Builder;
import org.apache.commons.lang.StringUtils;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;

import java.io.Serializable;
import java.util.Arrays;

/**
 * baseframework
 * 2020/8/6 16:05
 * snmp认证domain
 *
 * @author lanhaifeng
 * @since
 **/
@Builder(toBuilder = true)
public class SnmpAuth implements Serializable {

	//版本，[SnmpConstants.version1, SnmpConstants.version2c, SnmpConstants.version3]
	private int version;
	//团体名
	private String community;
	//授权模式,见org.snmp4j.security.SecurityModel
    //SECURITY_MODEL_SNMPv1 v1
    //SECURITY_MODEL_SNMPv2c v2c
    //SECURITY_MODEL_USM v3  默认授权模式
    //SECURITY_MODEL_TSM v3  用于SSH or DTLS
    //SECURITY_MODEL_KSM v3  用于支持Kerberos
	private int securityModel;
	//snmpv3使用，认证级别,见org.snmp4j.security.SecurityLevel
	private int securityLevel;
	//snmpv3使用，认证用户，是否需要传值由securityLevel决定
	private String userName;
    //snmpv3使用，认证方式
	private String authType;
	//snmpv3使用，认证密码，是否需要传值由securityLevel决定
	private String passAuth;
    //snmpv3使用，加密方式
    private String privType;
	//snmpv3使用，加密密码，是否需要传值由securityLevel决定
	private String privPass;
	//snmp服务端ip
	private String ip;
	//snmp服务端端口
	private int port;

    public SnmpAuth() {
    }

    public SnmpAuth(int version, String community, int securityModel, int securityLevel, String userName, String authType, String passAuth, String privType, String privPass, String ip, int port) {
        this.version = version;
        this.community = community;
        this.securityModel = securityModel;
        this.securityLevel = securityLevel;
        this.userName = userName;
        this.authType = authType;
        this.passAuth = passAuth;
        this.privType = privType;
        this.privPass = privPass;
        this.ip = ip;
        this.port = port;
    }

    public boolean validate(){
		boolean validateResult = true;
		if(StringUtils.isBlank(getIp()) || getPort() < 1 || getPort() > 65535
				|| !Arrays.asList(SnmpConstants.version1, SnmpConstants.version2c, SnmpConstants.version3).contains(getVersion())){
			validateResult = false;
		}
		if ((getVersion() == SnmpConstants.version1 || getVersion() == SnmpConstants.version2c) && StringUtils.isBlank(getCommunity())) {
			validateResult = false;
		}

		return validateResult;
	}

	public SnmpAuth withVersion(int version){
		this.version = version;
		return this;
	}

	public SnmpAuth withCommunity(String community){
		this.community = community;
		return this;
	}

	public SnmpAuth withSecurityModel(int securityModel){
		this.securityModel = securityModel;
		return this;
	}

	public SnmpAuth withSecurityLevel(int securityLevel){
		this.securityLevel = securityLevel;
		return this;
	}

	public SnmpAuth withUserName(String userName){
		this.userName = userName;
		return this;
	}

	public SnmpAuth withPassAuth(String passAuth){
		this.passAuth = passAuth;
		return this;
	}

	public SnmpAuth withPrivatePass(String privatePass){
		this.privPass = privatePass;
		return this;
	}

	public SnmpAuth withIp(String ip){
		this.ip = ip;
		return this;
	}

	public SnmpAuth withPort(int port){
		this.port = port;
		return this;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public int getSecurityModel() {
		return securityModel;
	}

	public void setSecurityModel(int securityModel) {
		this.securityModel = securityModel;
	}

	public int getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(int securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassAuth() {
		return passAuth;
	}

	public void setPassAuth(String passAuth) {
		this.passAuth = passAuth;
	}

	public String getPrivPass() {
		return privPass;
	}

	public void setPrivPass(String privPass) {
		this.privPass = privPass;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getPrivType() {
        return privType;
    }

    public void setPrivType(String privType) {
        this.privType = privType;
    }

}
