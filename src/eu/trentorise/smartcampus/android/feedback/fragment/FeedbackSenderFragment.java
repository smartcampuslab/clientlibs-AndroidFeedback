package eu.trentorise.smartcampus.android.feedback.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

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

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
	
	private String mActId;
	
	private TextView mAssingmentTV;
	private Spinner mTypeSpinner;
	private ImageView mScreenShotIV;
	private CheckBox mAttachScreenshotCB;
	private EditText mNoteEditText;
	private Button mSendBtn;

	private Bitmap bmp;
	
	private Context mCtx;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.feedback_fragment_layout, container);
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mCtx = getActivity().getApplicationContext();
		mAssingmentTV = (TextView) getActivity().
				findViewById(R.id.feedback_assignment_tv);
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
				feedback.setType(mTypeSpinner.getSelectedItem().toString());
				feedback.setActivityId(mActId);
				FeedbackFragmentActivity ffa = (FeedbackFragmentActivity) getActivity();
				feedback.setAppId(ffa.getAppToken());
				new SendFeedbackAsyncTask(FeedbackSenderFragment.this, feedback,
						mAttachScreenshotCB.isChecked(), ffa.getAppToken(),
						ffa.getAuthToken()).execute(bmp);
				ffa.toggle();
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
					      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(mNoteEditText.getWindowToken(), 0);
			}
		});
	}
	
	private void setImage() {
		bmp = ScreenShooter.getScreenshotFromBundle(getArguments(), IMG_KEY);
		mScreenShotIV.setImageBitmap(bmp);
	}
	
	public void refresh(){
		this.mNoteEditText.setText("");
		this.mTypeSpinner.setSelection(0);
		this.mAttachScreenshotCB.setChecked(true);
	}

	public void refreshImage(Bitmap image){
		this.mScreenShotIV.setImageBitmap(image);
		this.bmp = image;
	}
	
	public void refreshText(String param){
		String out = String.format(getResources().
				getString(R.string.feedback_assignment), param);
		this.mAssingmentTV.setText(out);
	}

	public void refreshActivity(String actId) {
		this.mActId = actId;
	}
	
	@Override
	public void onFeedbackSent(String s) {
		Toast.makeText(mCtx, s, Toast.LENGTH_SHORT).show();
	}

}
