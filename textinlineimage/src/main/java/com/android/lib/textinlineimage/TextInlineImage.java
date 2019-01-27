package com.android.lib.textinlineimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInlineImage extends AppCompatTextView {

    private float imageSize = 0;
    private int imageAlignment = 0;

    public TextInlineImage(Context context) {
        super(context);
    }

    public TextInlineImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextInlineImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextInlineImage,
                0, 0);
        try {
            imageSize = a.getDimension(R.styleable.TextInlineImage_imageSize, 0);
            imageAlignment = a.getInt(R.styleable.TextInlineImage_imageAlignment, 0);
            Log.d(getClass().getName(), String.valueOf(imageSize));

        } finally {
            a.recycle();
        }
        setText(getText().toString());

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (imageSize == 0)
            imageSize = getTextSize();
        Spannable s = getTextWithImages(getContext(), text);
        super.setText(s, BufferType.SPANNABLE);
    }

    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

    private boolean addImages(Context context, Spannable spannable) {
        Pattern refImg = Pattern.compile("\\Q[img src=\\E([a-zA-Z0-9_]+?)\\Q/]\\E");
        boolean hasChanges = false;

        Matcher matcher = refImg.matcher(spannable);
        while (matcher.find()) {
            boolean set = true;
            for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class)) {
                if (spannable.getSpanStart(span) >= matcher.start()
                        && spannable.getSpanEnd(span) <= matcher.end()
                        ) {
                    spannable.removeSpan(span);
                } else {
                    set = false;
                    break;
                }
            }
            String resname = spannable.subSequence(matcher.start(1), matcher.end(1)).toString().trim();
            int id = context.getResources().getIdentifier(resname, "drawable", context.getPackageName());
            if (set) {
                hasChanges = true;
                Drawable imageDrawable = context.getResources().getDrawable(id);
                int width = (int) (imageSize);
                int height = (int) (imageSize);

                imageDrawable.setBounds(0, 0, width, height);
                ImageSpan imageSpan = getImageSpan(imageDrawable);
                spannable.setSpan(
                        imageSpan, // Span to add
                        matcher.start(),
                        matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// Do not extend the span when text add later
            }
        }

        return hasChanges;
    }

    private ImageSpan getImageSpan(Drawable imageDrawable) {
        switch (imageAlignment) {
            case 0:
                return new CenterImageSpan(imageDrawable, ImageSpan.ALIGN_BASELINE, this);

            case 1:
                return new ImageSpan(imageDrawable, ImageSpan.ALIGN_BASELINE);

            case 2:
                return new ImageSpan(imageDrawable, ImageSpan.ALIGN_BOTTOM);

            default:
                return new CenterImageSpan(imageDrawable, ImageSpan.ALIGN_BASELINE, this);

        }
    }

    private Spannable getTextWithImages(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addImages(context, spannable);
        return spannable;
    }

    public float getImageSize() {
        return imageSize;
    }

    public void setImageSize(float imageSize) {
        this.imageSize = imageSize;
    }
}

