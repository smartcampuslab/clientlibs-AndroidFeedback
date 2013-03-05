package eu.trentorise.smartcampus.android.feedback.utils;


import java.io.ByteArrayOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
/**
 * @author Giovanni De Francesco
 * this is a utility class to take a screenshot
 * (as Bitmap) of a view.
 */
public class ScreenShooter {

	/**
	 * Set right options of a view to create a screenshot of it.
	 * @param v the view that we want reproduce as screenshot
	 */
	private static void setupView(View v) {
		// The following line is fundamental to take the screenshot
		v.setDrawingCacheEnabled(true);

		// without this lines the screenshot results poor and black
		v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		v.setDrawingCacheBackgroundColor(Color.WHITE);
	}
	
	/**
	 * Make a bitmap from a view
	 * @param v the the view which we want to represent as bitmap
	 * @return a bitmap of the view
	 * @return null if the view cannot be converted to Bitmap
	 */
	public static Bitmap viewToBitmap(View v){
		setupView(v);
		Bitmap bitmap = v.getDrawingCache();
		return (bitmap!=null)?bitmap:null;
	}
	
	/**
	 * Make a compressed bitmap from a view
	 * @param v the view which we want to take the screenshot
	 * @return a byte array of the bitmap
	 * @return null if the view cannot be converted to Bitmap
	 */
	public static byte[] viewToCompressedBitmapAsByteArray(View v) {
		Bitmap bitmap = viewToBitmap(v);
		if (bitmap != null) {
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 50, bais);
			return bais.toByteArray();
		}
		return null;
	}
	
	/**
	 * Insert a Screenshot of a view in an intent
	 * @param i the intent where we want to put the screenshot
	 * @param v the view which we want to make the screenshot
	 * @param screenshotKey a string that represent an id for that screenshot
	 */
	public static void insertScreenshotInIntent(Intent i,View v,String screenshotKey){
		Bundle b = new Bundle();
		b.putByteArray(screenshotKey, viewToCompressedBitmapAsByteArray(v));
		i.putExtras(b);
	}
	public static void insertScreenshotInBundle(Bundle b,View v,String screenshotKey){
		b.putByteArray(screenshotKey, viewToCompressedBitmapAsByteArray(v));
	}
	
	/**
	 * Set the ImageView to use the image contained in the intent
	 * @param i the intent where that contains the screenshot
	 * @param screenshotKey a string that represent an id for that screenshot
	 */
	public static Bitmap getScreenshotFromIntent(Intent i,String screenshotKey) {
		Bundle extras = i.getExtras();
		if(extras!=null){
			byte[] image=extras.getByteArray(screenshotKey);
			return BitmapFactory.decodeByteArray(image, 0, image.length);
		}
		return null;
	}
	/**
	 * Set the ImageView to use the image contained in the intent
	 * @param i the intent where that contains the screenshot
	 * @param screenshotKey a string that represent an id for that screenshot
	 */
	public static Bitmap getScreenshotFromBundle(Bundle extras,String screenshotKey) {
		if(extras!=null){
			byte[] image=extras.getByteArray(screenshotKey);
			return BitmapFactory.decodeByteArray(image, 0, image.length);
		}
		return null;
	}
}
