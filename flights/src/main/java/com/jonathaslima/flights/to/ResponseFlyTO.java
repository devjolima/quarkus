package com.jonathaslima.flights.to;

import java.io.Serializable;
import java.util.Map;

public class ResponseFlyTO implements Serializable{

	private static final long serialVersionUID = -3857027014997040297L;
	
	private Map<String, ResponseDetailTO> aiportCode ;

	public Map<String, ResponseDetailTO> getAiportCode() {
		return aiportCode;
	}

	public void setAiportCode(Map<String, ResponseDetailTO> aiportCode) {
		this.aiportCode = aiportCode;
	}



}
