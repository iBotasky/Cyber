package com.botasky.cyberblack.view;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by botasky on 24/01/2017.
 */

public class AlphaImageView extends ImageView {
    public AlphaImageView(Context context) {
        super(context);
        this.setOnTouchListener( (view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                AlphaImageView.this.setAlpha(0.5f);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                AlphaImageView.this.setAlpha(1.0f);
            }
            return false;
        });
    }
}
