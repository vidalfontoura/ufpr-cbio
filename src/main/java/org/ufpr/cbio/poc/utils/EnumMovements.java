package org.ufpr.cbio.poc.utils;

public enum EnumMovements {

	ROTATE_90_CLOCKWISE(1),
	ROTATE_180_CLOCKWISE(2),
	CORNER(3),
	NAO_LEMBRO(4);
	
	private int id;
	
	private EnumMovements(int id) {
		this.id = id;
	}
}
