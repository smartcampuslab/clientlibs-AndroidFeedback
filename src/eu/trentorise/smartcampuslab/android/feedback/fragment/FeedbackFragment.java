package eu.trentorise.smartcampuslab.android.feedback.fragment;

import eu.trentorise.smartcampuslab.android.feedback.R;
import eu.trentorise.smartcampuslab.android.feedback.Utils.FeedbackFragmentHelper;
import eu.trentorise.smartcampuslab.android.feedback.Utils.ScreenShooter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class FeedbackFragment extends Fragment {
	
	public Bitmap mScreenshot;
	
	private TextView mAssingmentTV;
	private SeekBar mDifficultySB;
	private Spinner mTypeSpinner;
	private ImageView mScreenShotIV;
	private CheckBox mAttachScreenshotCB;
	private Button mSendBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.feedback_layout_internal, container);
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mAssingmentTV = (TextView) getActivity().
				findViewById(R.id.feedback_assignment_tv);
		mDifficultySB = (SeekBar) getActivity().
				findViewById(R.id.feedback_difficulty_sb);
		mTypeSpinner = (Spinner) getActivity().
				findViewById(R.id.feedback_type_sp);
		mScreenShotIV = (ImageView) getActivity().
				findViewById(R.id.feedback_screenshot_imgv);
		mAttachScreenshotCB = (CheckBox) getActivity().
				findViewById(R.id.feedback_attach_cb);
		mSendBtn = (Button) getActivity().
				findViewById(R.id.feedback_send_Btn);
		
	}
	

	@Override
	public void onResume() {
		super.onResume();
		mScreenshot = ScreenShooter.getScreenshotFromIntent(getActivity().
				getIntent(),FeedbackFragmentHelper.IMG_PASSED_KEY);
		if(mScreenshot!=null)
			mScreenShotIV.setImageBitmap(mScreenshot);
	}
	
}
