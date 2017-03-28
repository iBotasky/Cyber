package com.botasky.cyberblack.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by botasky on 24/01/2017.
 */

public class AlphaImageView extends ImageView {
    public AlphaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == 0) {
                    AlphaImageView.this.setAlpha(0.5F);
                } else if(motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    AlphaImageView.this.setAlpha(1.0F);
                }

                return false;
            }
        });
    }
}
