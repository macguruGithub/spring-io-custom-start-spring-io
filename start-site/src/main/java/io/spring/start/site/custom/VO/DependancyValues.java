package io.spring.start.site.custom.VO;

import java.util.List;

import io.spring.initializr.web.VO.DependancyList;

public class DependancyValues {

	private String name;
	private List<DependancyList> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DependancyList> getValues() {
		return values;
	}

	public void setValues(List<DependancyList> values) {
		this.values = values;
	}

}
