package com.devjolima.data;

public class Country {
    
    public String name;
    public String capital;
    private String numericCode;

    /**
     * @return String return the numericCode
     */
    public String getNumericCode() {
        return numericCode;
    }

    /**
     * @param numericCode the numericCode to set
     */
    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

}