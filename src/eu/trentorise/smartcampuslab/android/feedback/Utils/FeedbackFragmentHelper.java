package eu.trentorise.smartcampuslab.android.feedback.Utils;

import eu.trentorise.smartcampuslab.android.feedback.R;
import eu.trentorise.smartcampuslab.android.feedback.interfaces.OnBackPressedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.SlidingDrawer;

public class FeedbackFragmentHelper {

	private FragmentActivity mHostActivity;
	private FragmentManager mFragmentManager;
	private SlidingDrawer mSlidingDrawer;

	/**
	 * Initialize the helper
	 * 
	 * @param hostActivity
	 *            the FragmentActivity that use feedback_layout.xml
	 */
	public FeedbackFragmentHelper(FragmentActivity hostActivity) {
		super();
		this.mHostActivity = hostActivity;
		this.mFragmentManager = mHostActivity.getSupportFragmentManager();
		if (mHostActivity.findViewById(R.id.feedback_sd).isShown()){
			mSlidingDrawer = (SlidingDrawer) mHostActivity
					.findViewById(R.id.feedback_sd);
			setSlidingDrawerListeners();
		}
		else
			throw new RuntimeException(
					"The host activity don't use feedback_layout.xml");
	}
	
	private void setSlidingDrawerListeners() {
		mSlidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			
			@Override
			public void onDrawerOpened() {
				//TODO send the screenshot to the fragment
			}
		});
	}

	/**
	 * 
	 * @return the Sliding Drawer in use
	 */
	public SlidingDrawer getSlidingDrawer() {
		return mSlidingDrawer;
	}


	/**
	 * Does a Fragment transaction replacing fragments
	 * 
	 * @param fragment
	 *            the fragment that has to be shown
	 * @param transactionTransition
	 *            the transition cthat we want to apply
	 */
	public void replaceFragmentWithTransition(Fragment fragment,
			int transactionTransition) {
		FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();
		fragmentTransaction.setTransition(transactionTransition);
		if (mHostActivity.findViewById(R.id.fragment_container).isShown())
			fragmentTransaction.replace(R.id.fragment_container, fragment);
		else
			throw new RuntimeException(
					"The host activity don't use feedback_layout.xml");
		fragmentTransaction.commit();
	}

	/**
	 * Manage the back button of the fragment actually used
	 */
	public boolean backButtonPressed() {
		boolean managed = false;
		// Closing sliding drawer
		if (mSlidingDrawer.isShown() && mSlidingDrawer.isOpened()) {
			mSlidingDrawer.animateClose();
		} else {
			Fragment currentFragment = mFragmentManager
					.findFragmentById(R.id.fragment_container);
			// Checking if there is a fragment that it's listening for back
			// button
			if (currentFragment != null
					&& currentFragment instanceof OnBackPressedListener) {
				managed = ((OnBackPressedListener) currentFragment)
						.onBackPressed();
				return managed;
			}

		}
		return false;
	}
}
