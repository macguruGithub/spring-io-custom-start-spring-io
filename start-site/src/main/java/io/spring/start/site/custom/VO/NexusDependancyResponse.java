package io.spring.start.site.custom.VO;

import java.util.List;

public class NexusDependancyResponse {

	private List<NexusDependancyItems> items;
	private String continuationToken;

	public List<NexusDependancyItems> getItems() {
		return items;
	}

	public void setItems(List<NexusDependancyItems> items) {
		this.items = items;
	}

	public String getContinuationToken() {
		return continuationToken;
	}

	public void setContinuationToken(String continuationToken) {
		this.continuationToken = continuationToken;
	}

}
