package eu.trentorise.smartcampus.android.feedback.utils;

import eu.trentorise.smartcampus.android.feedback.R;
import eu.trentorise.smartcampus.android.feedback.activity.FeedbackFragmentActivity;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class FeedbackFragmentInflater {

	/**
	 * This method must be called in onStart of the fragment
	 * 
	 * @param
	 */
	public static void inflateHandleButton(Activity fedFragAct, View v) {
		if (!(fedFragAct instanceof FeedbackFragmentActivity))
			throw new IllegalStateException(
					"Activity must be a FeedbackFragmentActivity");

		View firstViewInLayout = ((ViewGroup) v).getChildAt(0);
		if (!(firstViewInLayout instanceof RelativeLayout))
			throw new IllegalStateException(
					"The first layout should be a RelativeLayout");

		RelativeLayout layout = (RelativeLayout) firstViewInLayout;

		if (((FeedbackFragmentActivity) fedFragAct).isUsingFeedback()) {
			Button b = createButton((FeedbackFragmentActivity) fedFragAct);
			layout.addView(b);
		}
	}

	/**
	 * 
	 */
	public static void inflateHandleButtonInRelativeLayout(Activity fedFragAct,
			RelativeLayout layout) {
		if (!(fedFragAct instanceof FeedbackFragmentActivity))
			throw new IllegalStateException(
					"Activity must be a FeedbackFragmentActivity");

		if (((FeedbackFragmentActivity) fedFragAct).isUsingFeedback()) {
			Button b = createButton((FeedbackFragmentActivity) fedFragAct);
			layout.addView(b);
		}
	}

	public static Button createButton(final FeedbackFragmentActivity fedFragAct) {
		Button b = new Button(fedFragAct);
		b.setBackgroundResource(R.drawable.btn_openfeedback);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				getPixelFromDPMetrics(fedFragAct, 20), getPixelFromDPMetrics(
						fedFragAct, 100));
		
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		b.setLayoutParams(lp);

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				fedFragAct.toggle();
			}
		});
		return b;
	}

	private static int getPixelFromDPMetrics(Context ctx, float dp) {
		DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
		return (int) (metrics.density * dp + 0.5f);
	}
}
