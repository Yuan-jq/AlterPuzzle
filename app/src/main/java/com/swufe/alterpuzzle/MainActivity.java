package com.swufe.alterpuzzle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this, null);
        setContentView(gameView);

        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.img);
        // 调用压缩方法
        Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), imageResId, 300, 300);
        gameView.setupPuzzle(bitmap);
    }

    // 压缩图片方法
    public static Bitmap decodeSampledBitmapFromResource(android.content.res.Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // 第一次解码，只获取图片的尺寸信息
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 计算 inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 第二次解码，使用计算好的 inSampleSize 来获取压缩后的图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 原始图片的宽和高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // 计算最大的 inSampleSize 值，使得图片的宽和高都不小于目标宽和高
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}