package com.chetaru.tribe365_new.UI.AppTour.ShowCaseView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Spannable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.chetaru.tribe365_new.R;

/**
 * Created by Mohammad Reza Eram  on 20/01/2018.
 */

public class GuideMessageView extends LinearLayout {

    public TextView mTitleTextView;
    public TextView mContentTextView;
    public TextView skipButton;
    public TextView nextButton;
    public LinearLayout childContentButton;
    public int padding = 0;
    public int paddingBetween = 0;
    public String skipButtonText = "Skip Tutorial", nextText = "Next";
    int[] location = new int[2];
    private final Paint mPaint;
    private final RectF mRect;
    private final LinearLayout childContent;


    GuideMessageView(Context context) {
        super(context);

        float density = context.getResources().getDisplayMetrics().density;
        setWillNotDraw(false);
        setOrientation(VERTICAL);
        // setGravity(Gravity.CENTER);


        mRect = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        final int padding = (int) (10 * density);



        childContent = new LinearLayout(context);
        childContent.setOrientation(VERTICAL);
        mTitleTextView = new TextView(context);
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        mTitleTextView.setTextColor(Color.WHITE);
        mTitleTextView.setTypeface(mTitleTextView.getTypeface(), Typeface.BOLD);
        childContent.addView(mTitleTextView);

       /* mTitleTextView = new TextView(context);
        mTitleTextView.setPadding(padding, padding, padding, paddingBetween);
        mTitleTextView.setGravity(Gravity.CENTER);
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        mTitleTextView.setTextColor(Color.WHITE);
        addView(mTitleTextView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mContentTextView = new TextView(context);
        mContentTextView.setTextColor(Color.WHITE);
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        mContentTextView.setPadding(padding, paddingBetween, padding, padding);
        mContentTextView.setGravity(Gravity.CENTER);
        addView(mContentTextView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));*/

        mContentTextView = new TextView(context);
        mContentTextView.setTextColor(Color.WHITE);
        mContentTextView.setPadding(padding, paddingBetween, padding, padding);
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mContentTextView.setTypeface(ResourcesCompat.getFont(context, R.font.roboto));
        childContent.addView(mContentTextView);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params1.setMargins(10, 0, 0, 1);
        params1.leftMargin=20;
        params1.rightMargin=10;
        //params1.setMargins(convertDp(20), convertDp(15), convertDp(5), convertDp(5));
        addView(childContent, params1);

        childContentButton = new LinearLayout(context);
        childContentButton.setOrientation(HORIZONTAL);
        childContentButton.setGravity(Gravity.CENTER);
        boolean isTablet=getResources().getBoolean(R.bool.isTablet);

        skipButton = new TextView(context);
        skipButton.setText(skipButtonText);
        skipButton.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
        skipButton.setTextColor(Color.WHITE);
        if (isTablet) {
            skipButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        }else {
            skipButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        }
        skipButton.setTypeface(ResourcesCompat.getFont(context, R.font.roboto_medium));
        skipButton.setGravity(Gravity.START);
        // okButton.setBackground(ContextCompat.getDrawable(context,R.drawable.round_bt_red_white));
        LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childParam1.weight = 0.5f;

        skipButton.setLayoutParams(childParam1);


        nextButton = new TextView(context);
        nextButton.setText(nextText);
        nextButton.setTextAlignment(TEXT_ALIGNMENT_VIEW_END);
        nextButton.setTextColor(Color.WHITE);
        if (isTablet) {
            nextButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        }else {
            nextButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        }
        nextButton.setTypeface(ResourcesCompat.getFont(context, R.font.roboto_medium));
        nextButton.setGravity(Gravity.END);
        // okButton.setBackground(ContextCompat.getDrawable(context,R.drawable.round_bt_red_white));
        LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childParam2.weight = 0.5f;
        nextButton.setLayoutParams(childParam2);
        childContentButton.addView(skipButton);
        childContentButton.addView(nextButton);


        //  addView(mSkipImageButton, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(convertDp(15), convertDp(15), convertDp(15), convertDp(15));
        addView(childContentButton, params);
    }



    public void setTitle(String title) {
        if (title == null) {
            removeView(mTitleTextView);
            return;
        }
        mTitleTextView.setText(title);
    }

    public void setContentText(String content) {
        mContentTextView.setText(content);
    }

    public void setContentSpan(Spannable content) {
        mContentTextView.setText(content);
    }

    public void setContentTypeFace(Typeface typeFace) {
        mContentTextView.setTypeface(typeFace);
    }

    public void setTitleTypeFace(Typeface typeFace) {
        mTitleTextView.setTypeface(typeFace);
    }

    public void setTitleTextSize(int size) {
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setContentTextSize(int size) {
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setColor(int color) {

        mPaint.setAlpha(255);
        mPaint.setColor(color);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        this.getLocationOnScreen(location);


        mRect.set(getPaddingLeft(),
                getPaddingTop(),
                getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom());


        canvas.drawRoundRect(mRect, 15, 15, mPaint);
    }

    private int convertDp(int margin) {
        int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, getResources()
                .getDisplayMetrics());

        return marginInDp;
    }
}
