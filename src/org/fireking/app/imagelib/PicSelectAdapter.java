package org.fireking.app.imagelib;

import org.fireking.app.imagelib.MyImageView.OnMeasureListener;
import org.fireking.app.imagelib.NativeImageLoader.NativeImageCallBack;
import org.fireking.app.imagelib.PicSelectActivity.OnImageSelectedCountListener;
import org.fireking.app.imagelib.PicSelectActivity.OnImageSelectedListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class PicSelectAdapter extends BaseAdapter {

	Context context;
	private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
	AlbumBean bean;
	private GridView mGridView;
	OnImageSelectedListener onImageSelectedListener;
	OnImageSelectedCountListener onImageSelectedCountListener;

	// 最大选中数量
	final int limit = 9;

	public PicSelectAdapter(Context context, GridView mGridView,
			OnImageSelectedCountListener onImageSelectedCountListener) {
		this.context = context;
		this.mGridView = mGridView;
		this.onImageSelectedCountListener = onImageSelectedCountListener;
	}

	public void taggle(AlbumBean bean) {
		this.bean = bean;
		notifyDataSetChanged();
	}

	public void setOnImageSelectedListener(
			OnImageSelectedListener onImageSelectedListener) {
		this.onImageSelectedListener = onImageSelectedListener;
	}

	@Override
	public int getCount() {
		return bean == null || bean.count == 0 ? 0 : bean.count;
	}

	@Override
	public Object getItem(int position) {
		return bean == null ? null : bean.sets.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		final ImageBean ib = (ImageBean) getItem(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.the_picture_selection_item, null);
			viewHolder.mImageView = (MyImageView) convertView
					.findViewById(R.id.child_image);
			viewHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.child_checkbox);
			viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener() {

				@Override
				public void onMeasureSize(int width, int height) {
					mPoint.set(width, height);
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		viewHolder.mImageView.setTag(ib.path);
		viewHolder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						int count = onImageSelectedCountListener
								.getImageSelectedCount();
						if (count == limit) {
							Toast.makeText(context, "最多只能现在" + limit + "张图片",
									Toast.LENGTH_SHORT).show();
							buttonView.setChecked(false);
						} else {
							// 如果是未选中的CheckBox,则添加动画
							if (!ib.isChecked && isChecked) {
								addAnimation(viewHolder.mCheckBox);
							}
							ib.isChecked = isChecked;
							onImageSelectedListener.onImageSelected(ib);
						}
					}
				});

		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(
				ib.path, mPoint, new NativeImageCallBack() {

					@Override
					public void onImageLoader(Bitmap bitmap, String path) {
						ImageView mImageView = (ImageView) mGridView
								.findViewWithTag(ib.path);
						if (bitmap != null && mImageView != null) {
							mImageView.setImageBitmap(bitmap);
						}
					}
				});
		if (bitmap != null) {
			viewHolder.mImageView.setImageBitmap(bitmap);
		} else {
			viewHolder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		return convertView;
	}

	/**
	 * 给CheckBox加点击动画，利用开源库nineoldandroids设置动画
	 * 
	 * @param view
	 */
	private void addAnimation(View view) {
		float[] vaules = new float[] { 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f,
				1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f };
		AnimatorSet set = new AnimatorSet();
		set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules),
				ObjectAnimator.ofFloat(view, "scaleY", vaules));
		set.setDuration(150);
		set.start();
	}

	public static class ViewHolder {
		public MyImageView mImageView;
		public CheckBox mCheckBox;
	}

}
