package com.android.lib.textinlineimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;
import android.util.Log;

import java.lang.ref.WeakReference;

public class CenterImageSpan  extends ImageSpan {
    private WeakReference<Drawable> mDrawableRef;

    // Extra variables used to redefine the Font Metrics when an ImageSpan is added
    private int initialDescent = 0;
    private int extraSpace = 0;
    private TextInlineImage textInlineImage;

    public CenterImageSpan(Context context, final int drawableRes) {
        super(context, drawableRes);
    }

    public CenterImageSpan(Drawable drawableRes, int verticalAlignment, TextInlineImage textInlineImage) {
        super(drawableRes, verticalAlignment);
        this.textInlineImage=textInlineImage;
    }

    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getCachedDrawable();
        Rect rect = d.getBounds();

        if (fm != null) {
            // Centers the text with the ImageSpan
            if (rect.bottom - (fm.descent - fm.ascent) >= 0) {
                // Stores the initial descent and computes the margin available
                initialDescent = fm.descent;
                extraSpace = rect.bottom - (fm.descent - fm.ascent);
            }

            fm.descent = extraSpace / 2 + initialDescent;
            fm.bottom = fm.descent;

            fm.ascent = -rect.bottom + fm.descent;
            fm.top = fm.ascent;
        }

        return rect.right;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getCachedDrawable();
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        canvas.save();
//        int transY = bottom - b.getBounds().bottom;
//        // this is the key
//        if(textSize<=imageSize) {
//            transY -= (paint.getFontMetricsInt().descent / 2);
//        } else{
//            transY -= ((textSize-(imageSize/2))/2);
//        }
        canvas.translate(x,rect.top+((rect.height()-textInlineImage.getImageSize())/2));
        b.draw(canvas);
        canvas.restore();
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }

    // Redefined locally because it is a private member from DynamicDrawableSpan
    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }
        return d;
    }
}
