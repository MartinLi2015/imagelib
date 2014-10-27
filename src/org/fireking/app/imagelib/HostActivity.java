package org.fireking.app.imagelib;

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
				startActivity(intent);
			}
		});

		final Button pop = (Button) this.findViewById(R.id.pop);
		pop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater
						.from(HostActivity.this);
				View view = inflater.inflate(
						R.layout.the_picture_selection_pop, null);
				final PopupWindow mPopupWindow = new PopupWindow(view,
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						true);
				mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
				mPopupWindow.setOutsideTouchable(true);
				mPopupWindow.showAsDropDown(pop);
			}
		});
	}

}
