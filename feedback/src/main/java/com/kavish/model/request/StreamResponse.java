package com.kavish.model.request;

public class StreamResponse {
    private Tweet data;
	
	public static StreamResponse emptyResponse() {
		return new StreamResponse();
	}

	public Tweet getData() {
		return data;
	}

	public void setData(Tweet data) {
		this.data = data;
	}
}
