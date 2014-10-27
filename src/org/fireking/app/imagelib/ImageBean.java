package org.fireking.app.imagelib;

public class ImageBean {

	public String parentName;
	public long size;
	public String displayName;
	public String path;

	public ImageBean() {
		super();
	}

	public ImageBean(String parentName, long size, String displayName,
			String path) {
		super();
		this.parentName = parentName;
		this.size = size;
		this.displayName = displayName;
		this.path = path;
	}

	@Override
	public String toString() {
		return "ImageBean [parentName=" + parentName + ", size=" + size
				+ ", displayName=" + displayName + ", path=" + path + "]";
	}

}
