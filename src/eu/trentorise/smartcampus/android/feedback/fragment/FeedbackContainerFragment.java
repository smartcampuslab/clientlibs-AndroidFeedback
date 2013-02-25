package eu.trentorise.smartcampus.android.feedback.fragment;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.feedback.R;
import eu.trentorise.smartcampus.android.feedback.interfaces.OnBackPressedListener;
import eu.trentorise.smartcampus.android.feedback.interfaces.onDrawerVisibleListener;
import eu.trentorise.smartcampus.android.feedback.utils.ScreenShooter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * This class helps to use the FeedbackFragment
 * @author Giovanni De Francesco
 *
 */

public class FeedbackContainerFragment  extends SherlockFragment{
	
	public static final  String IMG_PASSED_KEY="screenshotbitmap1";

	private FragmentManager mFragmentManager;
	private SlidingDrawer mSlidingDrawer;
	private Button mSlidingButton;
	private TextView mRateTextView;

	private Bitmap mBitmap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstance) {
		View v = inflater.inflate(R.layout.feedback_layout, container);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		mSlidingDrawer = (SlidingDrawer) getActivity()
				.findViewById(R.id.feedback_sd);
		mSlidingButton =(Button) getActivity().
				findViewById(R.id.feedback_handle);
		setSlidingDrawerListeners();
		mBitmap = ScreenShooter.viewToBitmap((getActivity().
				findViewById(R.id.feedback_internal_root_rl)).getRootView());
		mRateTextView = (TextView) getActivity().findViewById(R.id.feedback_rate_link_tv);
		mRateTextView.setMovementMethod(LinkMovementMethod.getInstance());
	}



	private void setSlidingDrawerListeners() {
		mSlidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			
			@Override
			public void onDrawerOpened() {
				mSlidingButton.setBackgroundResource(R.drawable.btn_closefeedback);
				((onDrawerVisibleListener)getActivity()).disableViewsBehindDrawer();
				FeedbackFragment ff = (FeedbackFragment) getActivity().getSupportFragmentManager().
						findFragmentById(R.id.feedback_content);
				if(ff !=null)
					ff.refreshImage(mBitmap);
			}
		});
		mSlidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
			
			@Override
			public void onDrawerClosed() {
				mSlidingButton.setBackgroundResource(R.drawable.btn_openfeedback);
				((onDrawerVisibleListener)getActivity()).enableViewsBehindDrawer();
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
		fragmentTransaction.replace(R.id.fragment_container, fragment);
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
