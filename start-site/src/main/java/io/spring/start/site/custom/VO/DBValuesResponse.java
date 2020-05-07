package io.spring.start.site.custom.VO;

import java.io.Serializable;
import java.util.Map;

public class DBValuesResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4688565183436301554L;

	private String id;

	private Map<String,String> dialects;
	
	private Map<String,String> ddlAuto;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getDialects() {
		return dialects;
	}

	public void setDialects(Map<String, String> dialects) {
		this.dialects = dialects;
	}

	public Map<String, String> getDdlAuto() {
		return ddlAuto;
	}

	public void setDdlAuto(Map<String, String> ddlAuto) {
		this.ddlAuto = ddlAuto;
	}

	@Override
	public String toString() {
		return "DBValuesResponse [id=" + id + ", dialects=" + dialects + ", ddlAuto=" + ddlAuto + "]";
	}

}
