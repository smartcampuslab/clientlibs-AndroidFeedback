package eu.trentorise.smartcampus.android.feedback.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.common.GlobalConfig;
import eu.trentorise.smartcampus.android.feedback.R;
import eu.trentorise.smartcampus.android.feedback.activity.FeedbackFragmentActivity;
import eu.trentorise.smartcampus.android.feedback.asynctask.SendFeedbackAsyncTask;
import eu.trentorise.smartcampus.android.feedback.interfaces.OnFeedbackSent;
import eu.trentorise.smartcampus.android.feedback.model.Feedback;
import eu.trentorise.smartcampus.android.feedback.utils.ScreenShooter;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.FileRequestParam;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.ObjectRequestParam;
import eu.trentorise.smartcampus.protocolcarrier.custom.RequestParam;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

public class FeedbackSenderFragment extends Fragment implements OnFeedbackSent {
	
	public static final String IMG_KEY = "screenshotbitmap1";
	
	private String mAppId;
	private String mActId;
	
	private TextView mAssingmentTV;
	private SeekBar mDifficultySB;
	private Spinner mTypeSpinner;
	private ImageView mScreenShotIV;
	private CheckBox mAttachScreenshotCB;
	private EditText mNoteEditText;
	private Button mSendBtn;

	private Bitmap bmp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.feedback_fragment_layout, container);
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
		setImage();
		mAttachScreenshotCB = (CheckBox) getActivity().
				findViewById(R.id.feedback_attach_cb);
		mNoteEditText = (EditText) getActivity().
				findViewById(R.id.feedback_notes_et);
		mSendBtn = (Button) getActivity().
				findViewById(R.id.feedback_send_Btn);
		
		mSendBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Feedback feedback = new Feedback();
				feedback.setNote(mNoteEditText.getText().toString());
				feedback.setDifficulty(mDifficultySB.getProgress());
				feedback.setType(mTypeSpinner.getSelectedItem().toString());
				feedback.setAppId(mAppId);
				feedback.setActivityId(mActId);
				FeedbackFragmentActivity ffa = (FeedbackFragmentActivity) getActivity();
				new SendFeedbackAsyncTask(FeedbackSenderFragment.this, feedback,
						ffa.getAppToken(), ffa.getAuthToken()).execute(bmp);
			}
		});
	}
	
	private void setImage() {
		bmp = ScreenShooter.getScreenshotFromBundle(getArguments(), IMG_KEY);
		mScreenShotIV.setImageBitmap(bmp);
	}

	public void refreshImage(Bitmap image){
		this.mScreenShotIV.setImageBitmap(image);
	}
	
	public void refreshText(String param){
		String out = String.format(getResources().
				getString(R.string.feedback_assignment), param);
		this.mAssingmentTV.setText(out);
	}

	@Override
	public void onFeedbackSent(String s) {
		Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
	}

}
