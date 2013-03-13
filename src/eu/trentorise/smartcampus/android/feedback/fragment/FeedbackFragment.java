package eu.trentorise.smartcampus.android.feedback.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.feedback.R;
import eu.trentorise.smartcampus.android.feedback.activity.FeedbackFragmentActivity;

public class FeedbackFragment extends SherlockFragment {
	private FeedbackFragmentActivity mFedFragAct;
	private Button mHandleButton;

	@Override
	public void onStart() {
		super.onStart();
		mFedFragAct = (FeedbackFragmentActivity) getActivity();
		
		if(mFedFragAct.isUsingFeedback())
			inflateHandleButton();
	}
	
	private void inflateHandleButton() {
		View firstViewInLayout=( (ViewGroup)getView()).getChildAt(0);
		if(!(firstViewInLayout instanceof RelativeLayout))
			throw new IllegalStateException(
					"The first layout should be a RelativeLayout");
		RelativeLayout layout =(RelativeLayout) firstViewInLayout;
		createButton();
		layout.addView(mHandleButton);
	}
	
	private void createButton(){
		mHandleButton = new Button(getActivity());
		mHandleButton.setBackgroundDrawable(getResources().
				getDrawable(R.drawable.btn_openfeedback));
		
		RelativeLayout.LayoutParams lp = 
				new RelativeLayout.LayoutParams(50,100);
		
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mHandleButton.setLayoutParams(lp);
		
		mHandleButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mFedFragAct.toggle();
			}
		});
	}
	
	public void toggleHandleButton(){
		if(mFedFragAct.getSlidingMenu().isMenuShowing())
			mHandleButton.setBackgroundDrawable(getResources().
					getDrawable(R.drawable.btn_closefeedback));
		else
			mHandleButton.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.btn_openfeedback));
	}

}
