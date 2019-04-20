package com.feng.baseframework.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * baseframework
 * 2018/12/24 15:10
 * solr索引更新实体
 *
 * @author lanhaifeng
 * @since
 **/
public class SolrUpdateLog implements Serializable {

	private static final long serialVersionUID = -2802633470274766552L;
	private Integer id;
	private String auditId;
	private String field;
	private String fieldValue;
	private String collection;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public Map getUpdateAtomicVal(){
		Map<String, Object> atomic = new HashMap<String, Object>(1);
		atomic.put("set", getFieldValue());
		return atomic;
	}
}
