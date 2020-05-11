package io.spring.start.site.custom.VO;

import java.util.List;

public class DependancyResp {

	private String type;
	private List<DependancyValues> values;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DependancyValues> getValues() {
		return values;
	}

	public void setValues(List<DependancyValues> values) {
		this.values = values;
	}

}
