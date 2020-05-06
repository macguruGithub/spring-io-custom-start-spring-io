package io.spring.start.site.custom.VO;

import java.io.Serializable;
import java.util.List;

public class DBValuesResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4688565183436301554L;

	private String id;

	private List<String> dialects;
	
	private List<String> ddlAuto;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getDialects() {
		return dialects;
	}

	public void setDialects(List<String> dialects) {
		this.dialects = dialects;
	}

	public List<String> getDdlAuto() {
		return ddlAuto;
	}

	public void setDdlAuto(List<String> ddlAuto) {
		this.ddlAuto = ddlAuto;
	}

	@Override
	public String toString() {
		return "DBValuesResponse [id=" + id + ", dialects=" + dialects + ", ddlAuto=" + ddlAuto + "]";
	}

}
