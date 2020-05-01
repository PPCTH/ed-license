package com.pccth.edlicense.data;

import lombok.Data;

@Data
public class SearchAddress {
	private String province;
	private String amphur;
	private String district;
	
	@Override
	public String toString() {
		
		return province + " " + amphur + " " + district;
	}
}
