package org.sainsburys.objects;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class Result {
	private String title;
	private BigDecimal unit_price;
	private String description;
	
	public Result(String title, BigDecimal unit_price, String description) {
		this.title = title;
		this.unit_price = unit_price;
		this.description = description;
	}
	
	public BigDecimal getUnitPrice() {
		return unit_price;
	}
}
