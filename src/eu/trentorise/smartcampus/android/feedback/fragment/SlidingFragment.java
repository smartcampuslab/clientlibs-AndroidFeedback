package eu.trentorise.smartcampus.android.feedback.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.feedback.R;
import eu.trentorise.smartcampus.android.feedback.interfaces.OnBackPressedListener;
import eu.trentorise.smartcampus.android.feedback.interfaces.onDrawerVisibleListener;
import eu.trentorise.smartcampus.android.feedback.utils.ScreenShooter;

/**
 * This class helps to use the FeedbackFragment
 * 
 * @author Giovanni De Francesco
 * 
 */

public class SlidingFragment extends Fragment  implements OnTouchListener{

	public static final String APPID_PASSED_KEY = "appid1";
	public static final String ACTID_PASSED_KEY = "activityid1";
	public static final String IMG_PASSED_KEY = "screenshotbitmap1";

	private Bitmap mBitmap;

	private SlidingDrawer mSlidingDrawer;
	private Button mSlidingButton;
	private TextView mAssignementTextView;
	private TextView mRateTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstance) {
		View v = inflater.inflate(R.layout.feedback_layout, container);
		//v.setOnTouchListener(this);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		mSlidingDrawer = (SlidingDrawer) getActivity().findViewById(
				R.id.feedback_sd);
		mSlidingDrawer.setOnTouchListener(this);
		mSlidingButton = (Button) getActivity().findViewById(
				R.id.feedback_handle);
		setSlidingDrawerListeners();
		mRateTextView = (TextView) getActivity().findViewById(
				R.id.feedback_rate_link_tv);
		// mRateTextView.setMovementMethod(LinkMovementMethod.getInstance());
		mAssignementTextView = (TextView) getActivity().findViewById(
				R.id.feedback_assignment_tv);
		// mAssignementTextView.setText("");
	}

	private void setSlidingDrawerListeners() {
		mSlidingDrawer
				.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {

					@Override
					public void onDrawerOpened() {
						// This is an hack to avoid that the sliding drawer
						// would be visible in the screenshot.
						mSlidingDrawer.setVisibility(View.INVISIBLE);
						mBitmap = ScreenShooter.viewToBitmap((getActivity()
								.findViewById(R.id.feedback_internal_root_rl))
								.getRootView());
						mSlidingDrawer.setVisibility(View.VISIBLE);
						
						mSlidingButton
								.setBackgroundResource(R.drawable.btn_closefeedback);

						FeedbackFragment ff = (FeedbackFragment) getActivity()
								.getSupportFragmentManager().findFragmentById(
										R.id.feedback_content);
						
						if (ff != null)
							ff.refreshImage(mBitmap);
						ViewGroup v = 
								(ViewGroup) getActivity().findViewById(R.id.fragment_container);
						mSlidingDrawer.setClickable(true);
					}
				});
		mSlidingDrawer
				.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {

					@Override
					public void onDrawerClosed() {
						Fragment currentFragment = getActivity().getSupportFragmentManager()
								.findFragmentById(R.id.fragment_container);
						mSlidingButton
								.setBackgroundResource(R.drawable.btn_openfeedback);
						ViewGroup v = 
								(ViewGroup) getActivity().findViewById(R.id.fragment_container);
						mSlidingDrawer.setClickable(false);
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
	 * @param backstack
	 *            if you want to add the fragent to the backstack
	 * @param tag
	 *            the tag of the fragment
	 */
	public void replaceFragmentWithTransition(Fragment fragment,
			Integer transactionTransition, boolean backstack, String tag) {
		if (mSlidingDrawer != null && mSlidingDrawer.isOpened())
			mSlidingDrawer.animateClose();
		FragmentTransaction fragmentTransaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		if (transactionTransition == null)
			transactionTransition = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
		Fragment currentFragment = getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.fragment_container);	
		if (mAssignementTextView != null) {
			mAssignementTextView.setText(getString(
					R.string.feedback_assignment, tag));
		}
		if(currentFragment!=null && backstack){
			fragmentTransaction.addToBackStack(currentFragment.getTag());
		}
		fragmentTransaction.setTransition(transactionTransition);
		fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
		fragmentTransaction.commit();
	}
	
	private void toggleEnableFragment(ViewGroup viewGroup,boolean enabled) {
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = viewGroup.getChildAt(i);
			view.setEnabled(enabled);
			if (view instanceof ViewGroup) {
				toggleEnableFragment((ViewGroup) view,enabled);
			}
		}
	}

	/**
	 * Manage the back button of the fragment actually used
	 */
	public boolean backButtonPressed() {
		boolean managed = false;
		// Closing sliding drawer
		if (mSlidingDrawer.isShown() && mSlidingDrawer.isOpened()) {
			mSlidingDrawer.animateClose();
			return true;
		}
		Fragment currentFragment = getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.fragment_container);
		if(currentFragment!=null&& currentFragment instanceof OnBackPressedListener)
			managed = ((OnBackPressedListener) currentFragment)
				.onBackPressed();
		return managed;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		Log.e("lol", "touched");
		return false;
	}

}