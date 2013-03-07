package eu.trentorise.smartcampus.android.feedback.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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

		// set the background fragent for the menu
		setBehindContentView(R.layout.feedback_layout);
		setUseFeedBack();
		setUpMenu();
	}

	private void setUseFeedBack() {
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(this.getPackageName(),
					PackageManager.GET_META_DATA);
			Bundle aBundle=ai.metaData;
			mUseFeedback=aBundle.getBoolean("use-feedback");
		} catch (NameNotFoundException e) {
			mUseFeedback=false;
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
		mSlidingMenu.setMenu(R.layout.feedback_fragment_layout);
		mSlidingMenu.setMode(SlidingMenu.RIGHT);
		mSlidingMenu.setBehindOffsetRes(R.dimen.feedback_fragment_offset);
		
		if(!mUseFeedback)
			mSlidingMenu.setSlidingEnabled(false);
		setUpMenuListeners();
	}
	
	public boolean isUsingFeedback() {
		return mUseFeedback;
	}


	@Override
	public void toggle() {
		if(mUseFeedback)
			super.toggle();
	}

	private void setUpMenuListeners() {
		mSlidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {

			@Override
			public void onOpened() {
				refreshFragment();
			}
		});
	}
	
	protected void refreshFragment() {
		FeedbackSenderFragment ff = (FeedbackSenderFragment) getSupportFragmentManager()
				.findFragmentById(R.id.feedback_fragment_container);
		if (ff != null) {
			Bitmap bmp = ScreenShooter.viewToBitmap(mSlidingMenu.getContent());
			ff.refreshImage(bmp.copy(Bitmap.Config.RGB_565, false));
			if (getSupportActionBar().getSelectedTab() != null) {
				ff.refreshText(getSupportActionBar().getSelectedTab().getText().toString());
			} else if (getSupportActionBar().getTitle() != null) {
				ff.refreshText(getSupportActionBar().getTitle().toString());
			}
			Fragment f = getSupportFragmentManager().findFragmentById(android.R.id.content);
			if (f != null) {
				ff.refreshActivity(f.getClass().getName());
			}
		}
	}
	
	public abstract String getAppToken();
	public abstract String getAuthToken();

}
