package eu.trentorise.smartcampus.android.feedback.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import eu.trentorise.smartcampus.android.feedback.R;
import eu.trentorise.smartcampus.android.feedback.fragment.FeedbackSenderFragment;
import eu.trentorise.smartcampus.android.feedback.utils.ScreenShooter;

public abstract class FeedbackFragmentActivity extends SlidingFragmentActivity {

	protected SlidingMenu mSlidingMenu;
	private boolean mUseFeedback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the background fragment for the menu
		setBehindContentView(R.layout.feedback_layout);
		setUseFeedBack();
		setUpMenu();
	}

	private void setUseFeedBack() {
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(
					this.getPackageName(), PackageManager.GET_META_DATA);
			Bundle aBundle = ai.metaData;
			mUseFeedback = aBundle.getBoolean("use-feedback");
		} catch (NameNotFoundException e) {
			mUseFeedback = false;
			Log.e(FeedbackFragmentActivity.class.getName(),
					"you should set the use-feedback metadata in app manifest");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mSlidingMenu.isMenuShowing())
			mSlidingMenu.showContent(false);
	}

	private void setUpMenu() {
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.getContent().setFocusable(false);
		mSlidingMenu.setMenu(R.layout.feedback_fragment_layout);
		mSlidingMenu.setMode(SlidingMenu.RIGHT);
		mSlidingMenu.setBehindOffsetRes(R.dimen.feedback_fragment_offset);

		if (!mUseFeedback)
			mSlidingMenu.setSlidingEnabled(false);

		setUpMenuListeners();
	}

	public boolean isUsingFeedback() {
		return mUseFeedback;
	}

	@Override
	public void toggle() {
		if (mUseFeedback)
			super.toggle();
	}

	private void setUpMenuListeners() {
		mSlidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {

			@Override
			public void onOpened() {
				// This two lines are necessary for 2.2 compatibility
				View v = findViewById(android.R.id.content);
				v.invalidate();

				refreshFragment();
				toggleHandleButton();
			}
		});
		mSlidingMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {

			@Override
			public void onClosed() {
				toggleHandleButton();
			}
		});
	}

	protected void refreshFragment() {
		FeedbackSenderFragment ff = (FeedbackSenderFragment) getSupportFragmentManager()
				.findFragmentById(R.id.feedback_fragment_container);
		if (ff != null) {
			ff.refresh();
			Bitmap bmp = ScreenShooter.viewToBitmap(mSlidingMenu.getContent());
			ff.refreshImage(bmp.copy(Bitmap.Config.RGB_565, false));
			if (getSupportActionBar().getSelectedTab() != null) {
				ff.refreshText(getSupportActionBar().getSelectedTab().getText()
						.toString());
			} else if (getSupportActionBar().getTitle() != null) {
				ff.refreshText(getSupportActionBar().getTitle().toString());
			}
			Fragment f = getSupportFragmentManager().findFragmentById(
					android.R.id.content);
			if (f != null) {
				ff.refreshActivity(f.getClass().getName());
			}
		}
	}

	public void toggleHandleButton() {
		Button handleButton = (Button) findViewById(R.id.feedback_button_handler);
		if (getSlidingMenu().isMenuShowing())
			handleButton.setBackgroundResource(R.drawable.btn_closefeedback);
		else
			handleButton.setBackgroundResource(R.drawable.btn_openfeedback);
	}

	public abstract String getAppToken();

	public abstract String getAuthToken();

}
