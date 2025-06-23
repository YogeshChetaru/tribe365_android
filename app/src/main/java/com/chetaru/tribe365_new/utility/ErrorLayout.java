package com.chetaru.tribe365_new.utility;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.chetaru.tribe365_new.R;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by prakhar on 3/18/2016.
 */
public class ErrorLayout {
    public static final long LENGTH_SHORT = 1500L;
    public static final long LENGTH_LONG = 5500L;
    private final Handler handler = new Handler();
    public TextView mErrorTv;
    Vibrator vVibrator;
    private Animation mMoveUpAnim, mMoveDownAnim;
    private AtomicBoolean mErrorShown;
    private ErrorLayoutListener mErrorLayoutListener;

    public ErrorLayout(View view) {
        mErrorTv = view.findViewById(R.id.error_tv);
        vVibrator = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mErrorTv.setVisibility(View.GONE);
        mMoveUpAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.move_up);
        mMoveDownAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.move_down);
        //FontLoader.setRobotoMediumTypeface(mErrorTv,mErrorTv1);
        mErrorShown = new AtomicBoolean(false);
    }

    public void showAlert(String error, MsgType msgType) {
        mErrorTv.setVisibility(View.VISIBLE);
        mErrorTv.setText(error);
        int color = 0;
        switch (msgType) {
            case Error:
                color = Color.RED;
                break;
            case Success:
                color = Color.GREEN;
                break;
            case Info:
                color = Color.DKGRAY;
                break;
            case Warning:
                color = Color.YELLOW;
                break;
            default:
                color = Color.RED;
                break;
        }
        mErrorTv.setBackgroundColor(color);
        if (!mErrorShown.get()) {
            mErrorShown.set(true);
            vVibrator.vibrate(150);
            mErrorTv.startAnimation(mMoveDownAnim);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mErrorTv.startAnimation(mMoveUpAnim);
                    mErrorShown.set(false);
                    if (null != mErrorLayoutListener) {
                        mErrorLayoutListener.onErrorHidden();
                    }
                }
            }, 100 <= error.length() ? LENGTH_LONG : LENGTH_SHORT);
        }
    }

    public void showError(String error) {
        mErrorTv.setVisibility(View.VISIBLE);
        mErrorTv.setText(error);
        mErrorTv.setBackgroundColor(Color.RED);
        if (!mErrorShown.get()) {
            mErrorShown.set(true);
            vVibrator.vibrate(150);
            mErrorTv.startAnimation(mMoveDownAnim);
            /*handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mErrorTv.startAnimation(mMoveUpAnim);
                    mErrorShown.set(false);
                    if (null != mErrorLayoutListener) {
                        mErrorLayoutListener.onErrorHidden();
                    }
                }
            }, 100 <= error.length() ? LENGTH_LONG : LENGTH_SHORT);*/
        }
    }

    public void hideError() {
        if (mErrorTv.getVisibility() == View.VISIBLE) {
            mErrorTv.startAnimation(mMoveUpAnim);
            mErrorShown.set(false);
            if (null != mErrorLayoutListener) {
                mErrorLayoutListener.onErrorHidden();
            }
        }
    }

    public void setErrorLayoutListener(ErrorLayoutListener listener) {
        this.mErrorLayoutListener = listener;
    }

    public enum MsgType {
        Error, Success, Info, Warning
    }

    public interface ErrorLayoutListener {
        void onErrorShown();

        void onErrorHidden();
    }
}
