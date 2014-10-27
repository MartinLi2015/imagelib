package org.fireking.app.imagelib;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlbumBean {

	// 文件夹
	public String folderName;

	// 文件夹内文件数量
	public int count;

	// 文件
	public List<ImageBean> sets = new ArrayList<ImageBean>();

	// 缩略图
	public String thumbnail;

	public AlbumBean() {
		super();
	}

	public AlbumBean(String folderName, int count, List<ImageBean> sets,
			String thumbnail) {
		super();
		this.folderName = folderName;
		this.count = count;
		this.sets = sets;
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "AlbumBean [folderName=" + folderName + ", count=" + count
				+ ", sets=" + sets + ", thumbnail=" + thumbnail + "]";
	}

}
