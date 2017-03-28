package com.botasky.cyberblack.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by botasky on 24/01/2017.
 */

public class AlphaLinearLayout extends LinearLayout {
    public AlphaLinearLayout(Context context, AttributeSet attrs) {
        super(context);
        this.setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                this.setAlpha(0.5f);
            }else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL){
                this.setAlpha(1.0f);
            }
            return false;
        });

    }
}
