package com.feng.baseframework.snmp;

import java.io.Serializable;

/**
 * baseframework
 * 2020/8/6 16:05
 * snmp认证domain
 *
 * @author lanhaifeng
 * @since
 **/
public class SnmpAuth implements Serializable {

	//版本，[SnmpConstants.version1, SnmpConstants.version2c, SnmpConstants.version3]
	private int version;
	//团体名
	private String community;
	//认证模式,见org.snmp4j.security.SecurityModel
	private int securityModel;
	//snmpv3使用，认证级别,见org.snmp4j.security.SecurityLevel
	private int securityLevel;
	//snmpv3使用，认证用户，是否需要传值由securityLevel决定
	private String userName;
	//snmpv3使用，认证密码，是否需要传值由securityLevel决定
	private String passAuth;
	//snmpv3使用，加密密码，是否需要传值由securityLevel决定
	private String privatePass;

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
		this.privatePass = privatePass;
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

	public String getPrivatePass() {
		return privatePass;
	}

	public void setPrivatePass(String privatePass) {
		this.privatePass = privatePass;
	}
}
