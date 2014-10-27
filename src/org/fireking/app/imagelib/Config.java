package org.fireking.app.imagelib;

public class Config {

	static int limit;

	static {
		limit = 4;
	}

	public static void setLimit(int limit) {
		Config.limit = limit;
	}

}
