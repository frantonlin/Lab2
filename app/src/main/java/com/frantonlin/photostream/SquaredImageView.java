package com.frantonlin.photostream;

/**
 * Created by franton on 10/1/15.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/** An image view which always remains square with respect to its width. */
final class SquaredImageView extends ImageView {
    private boolean saved;

    public SquaredImageView(Context context) {
        super(context);
        saved = false;
    }

    public SquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

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

    public void save() {
        this.saved = true;
        invalidate();
    }
}
