package org.sainsburys.objects;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class ResultWithKCal extends Result {
	private Integer kcal_per_100g;
	
	public ResultWithKCal(String title, Integer kcal_per_100g, BigDecimal unit_price, String description) {
		super(description, unit_price, description);
		this.kcal_per_100g = kcal_per_100g;
	}
}
