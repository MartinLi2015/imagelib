package org.fireking.app.imagelib;

import java.io.Serializable;

public class ImageBean implements Serializable {

	public String parentName;
	public long size;
	public String displayName;
	public String path;
	public boolean isChecked;

	public ImageBean() {
		super();
	}

	public ImageBean(String parentName, long size, String displayName,
			String path, boolean isChecked) {
		super();
		this.parentName = parentName;
		this.size = size;
		this.displayName = displayName;
		this.path = path;
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "ImageBean [parentName=" + parentName + ", size=" + size
				+ ", displayName=" + displayName + ", path=" + path
				+ ", isChecked=" + isChecked + "]";
	}

}
