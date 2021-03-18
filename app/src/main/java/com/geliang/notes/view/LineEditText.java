package com.geliang.notes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class LineEditText extends androidx.appcompat.widget.AppCompatEditText {
	private Rect mRect;
	private Paint mPaint;

	public LineEditText(Context context, AttributeSet attrs) {
		super(context,attrs);
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setColor(Color.GRAY);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//得到EditText的总行数
		int lineCount = getLineCount();
		Rect r = mRect;
		Paint p = mPaint;

	}
}

