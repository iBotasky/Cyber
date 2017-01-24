package com.botasky.cyberblack.view;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by botasky on 24/01/2017.
 */

public class AlphaTextView extends TextView {
    public AlphaTextView(Context context) {
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
