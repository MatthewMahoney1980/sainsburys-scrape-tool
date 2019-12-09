package org.sainsburys.objects;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class Total {
	private BigDecimal gross;
	private BigDecimal vat;
	
	public Total(BigDecimal gross, BigDecimal vat){
		this.gross = gross;
		this.vat = vat;
	}
}
