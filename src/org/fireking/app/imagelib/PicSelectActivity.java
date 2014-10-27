package org.fireking.app.imagelib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fireking.app.imagelib.MyImageView.OnMeasureListener;
import org.fireking.app.imagelib.NativeImageLoader.NativeImageCallBack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * @author fireking
 * 
 */
public class PicSelectActivity extends BaseActivity {

	GridView gridView;
	PicSelectAdapter adapter;
	TextView album;
	TextView complete;
	TextView preView;

	List<ImageBean> selecteds = new ArrayList<ImageBean>();

	static final int SCAN_OK = 0x1001;

	static boolean isOpened = false;
	PopupWindow popWindow;

	int height = 0;
	List<AlbumBean> mAlbumBean;
	public static final String IMAGES = "images";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.the_picture_selection);

		album = (TextView) this.findViewById(R.id.album);
		complete = (TextView) this.findViewById(R.id.complete);
		preView = (TextView) this.findViewById(R.id.preview);
		preView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		complete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(IMAGES, (Serializable) selecteds);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		album.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isOpened && popWindow != null) {
					height = getWindow().getDecorView().getHeight();
					popWindow.showAtLocation(
							findViewById(android.R.id.content),
							Gravity.NO_GRAVITY,
							0,
							height
									- CommonsUitls.dip2px(
											PicSelectActivity.this, 448));
				} else {
					if (popWindow != null)
						popWindow.dismiss();
				}
			}
		});
		gridView = (GridView) this.findViewById(R.id.child_grid);
		adapter = new PicSelectAdapter(PicSelectActivity.this, gridView,
				onImageSelectedCountListener);
		gridView.setAdapter(adapter);
		adapter.setOnImageSelectedListener(onImageSelectedListener);
		showPic();
	}

	OnImageSelectedCountListener onImageSelectedCountListener = new OnImageSelectedCountListener() {

		@Override
		public int getImageSelectedCount() {
			return selecteds.size();
		}
	};

	OnImageSelectedListener onImageSelectedListener = new OnImageSelectedListener() {

		@Override
		public void onImageSelected(ImageBean ib) {
			if (ib.isChecked) {
				selecteds.add(ib);
			} else {
				selecteds.remove(ib);
			}
			if (selecteds.size() != 0) {
				// 更改完成按钮上的数字
				complete.setText("完成(" + selecteds.size() + "/9)");
				// 更改预览按钮的数字
				preView.setText("预览(" + selecteds.size() + ")");
			}

		}
	};

	private void showPic() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				msg.what = SCAN_OK;
				msg.obj = AlbumHelper.newInstance(PicSelectActivity.this)
						.getFolders();
				msg.sendToTarget();
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (SCAN_OK == msg.what) {
				mAlbumBean = (List<AlbumBean>) msg.obj;
				adapter.taggle(mAlbumBean.get(0));
				popWindow = showPopWindow();
			}
		};
	};

	private PopupWindow showPopWindow() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.the_picture_selection_pop, null);
		final PopupWindow mPopupWindow = new PopupWindow(view,
				LayoutParams.MATCH_PARENT, CommonsUitls.dip2px(
						PicSelectActivity.this, 400), true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		ListView listView = (ListView) view.findViewById(R.id.list);
		AlbumAdapter albumAdapter = new AlbumAdapter(PicSelectActivity.this,
				listView);
		listView.setAdapter(albumAdapter);
		albumAdapter.setData(mAlbumBean);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlbumBean b = (AlbumBean) parent.getItemAtPosition(position);

				adapter.taggle(b);
				// 更改选中的文字
				album.setText(b.folderName);
				mPopupWindow.dismiss();
			}
		});
		return mPopupWindow;
	}

	class AlbumAdapter extends BaseAdapter {
		LayoutInflater inflater;
		List<AlbumBean> albums;
		private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
		ListView mListView;

		public AlbumAdapter(Context context, ListView mListView) {
			this.inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.mListView = mListView;
		}

		public void setData(List<AlbumBean> albums) {
			this.albums = albums;
		}

		@Override
		public int getCount() {
			return albums == null || albums.size() == 0 ? 0 : albums.size();
		}

		@Override
		public Object getItem(int position) {
			return albums.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(
						R.layout.the_picture_selection_pop_item, null);
				viewHolder.album_count = (TextView) convertView
						.findViewById(R.id.album_count);
				viewHolder.album_name = (TextView) convertView
						.findViewById(R.id.album_name);
				viewHolder.mCheckBox = (CheckBox) convertView
						.findViewById(R.id.album_ck);
				viewHolder.mImageView = (MyImageView) convertView
						.findViewById(R.id.album_image);
				viewHolder.mImageView
						.setOnMeasureListener(new OnMeasureListener() {

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
			final AlbumBean b = (AlbumBean) getItem(position);
			viewHolder.mImageView.setTag(b.thumbnail);

			viewHolder.album_name.setText(b.folderName);
			viewHolder.album_count.setText(b.count + "");

			Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(
					b.thumbnail, mPoint, new NativeImageCallBack() {

						@Override
						public void onImageLoader(Bitmap bitmap, String path) {
							ImageView mImageView = (ImageView) mListView
									.findViewWithTag(b.thumbnail);
							if (mImageView != null && bitmap != null) {
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
	}

	public interface OnImageSelectedListener {
		void onImageSelected(ImageBean ib);
	}

	public interface OnImageSelectedCountListener {
		int getImageSelectedCount();
	}

	public static class ViewHolder {
		public MyImageView mImageView;
		public CheckBox mCheckBox;
		public TextView album_name;
		public TextView album_count;
	}
}
