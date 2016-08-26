package com.f8boss.zhihuribao.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by lucy on 2016/8/23.
 * Bitmap工具类
 */
public class BitmapUtil {

    private BitmapUtil() {
    }

    /**
     * 回收ImageView占用的图像内存;
     *
     * @param imageView
     */
    public static void recycleImageView(ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                imageView.setImageBitmap(null);
            }
        }
    }

}
