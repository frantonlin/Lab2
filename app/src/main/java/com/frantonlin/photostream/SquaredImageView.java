package com.frantonlin.photostream;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An image view which always remains square with respect to its width
 * Created by Franton on 10/1/15
 */
final class SquaredImageView extends ImageView {
    // Whether or not the image has been saved in the photostream
    private boolean saved;

    /**
     * Constructor
     * @param context the context of the ImageView
     */
    public SquaredImageView(Context context) {
        super(context);
        saved = false;
    }

    /**
     * Constructor
     * @param context the context of the ImageView
     * @param attrs attributes of the ImageView
     */
    public SquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the measured dimensions
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec vertical space requirements as imposed by the parent
     */
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    /**
     * Draws the image, adding a check mark if the image has been saved
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(saved) {
            Bitmap check = BitmapFactory.decodeResource(
                    getResources(), R.drawable.check);
            int width = check.getWidth();
            int height = check.getHeight();
            int margin = 8;
            int x = canvas.getWidth() - width - margin;
            int y = canvas.getHeight() - height - margin;
            canvas.drawBitmap(check, x, y, new Paint());
        }
    }

    /**
     * Sets the ImageView's save state to true and updates the view
     */
    public void save() {
        this.saved = true;
        invalidate();
    }
}
