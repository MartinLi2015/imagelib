package org.fireking.app.imagelib;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class HostActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.publish);
		Button btn = (Button) this.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HostActivity.this,
						PicSelectActivity.class);
				startActivityForResult(intent, 0x123);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0x123 && resultCode == RESULT_OK) {
			Intent intent = data;
			List<ImageBean> images = (List<ImageBean>) intent
					.getSerializableExtra("images");
			for (ImageBean b : images) {
				System.out.println(b.toString());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
