package androidx.appcompat.widget;

import android.R;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.widget.AbsSeekBar;

/* compiled from: AppCompatProgressBarHelper.java */
/* loaded from: classes.dex */
class C {

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f22102c = {R.attr.indeterminateDrawable, R.attr.progressDrawable};

    /* renamed from: a, reason: collision with root package name */
    public final AbsSeekBar f22103a;

    /* renamed from: b, reason: collision with root package name */
    public Bitmap f22104b;

    /* compiled from: AppCompatProgressBarHelper.java */
    @j.X
    public static class a {
    }

    public C(AbsSeekBar absSeekBar) {
        this.f22103a = absSeekBar;
    }

    public void a(AttributeSet attributeSet, int i12) {
        AbsSeekBar absSeekBar = this.f22103a;
        D0 d0E = D0.e(absSeekBar.getContext(), attributeSet, f22102c, i12);
        Drawable drawableC = d0E.c(0);
        if (drawableC != null) {
            if (drawableC instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) drawableC;
                int numberOfFrames = animationDrawable.getNumberOfFrames();
                AnimationDrawable animationDrawable2 = new AnimationDrawable();
                animationDrawable2.setOneShot(animationDrawable.isOneShot());
                for (int i13 = 0; i13 < numberOfFrames; i13++) {
                    Drawable drawableB = b(animationDrawable.getFrame(i13), true);
                    drawableB.setLevel(10000);
                    animationDrawable2.addFrame(drawableB, animationDrawable.getDuration(i13));
                }
                animationDrawable2.setLevel(10000);
                drawableC = animationDrawable2;
            }
            absSeekBar.setIndeterminateDrawable(drawableC);
        }
        Drawable drawableC2 = d0E.c(1);
        if (drawableC2 != null) {
            absSeekBar.setProgressDrawable(b(drawableC2, false));
        }
        d0E.f();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @j.k0
    public final Drawable b(Drawable drawable, boolean z12) {
        if (drawable instanceof androidx.core.graphics.drawable.j) {
            androidx.core.graphics.drawable.j jVar = (androidx.core.graphics.drawable.j) drawable;
            Drawable drawableB = jVar.b();
            if (drawableB != null) {
                jVar.a(b(drawableB, z12));
            }
        } else {
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                Drawable[] drawableArr = new Drawable[numberOfLayers];
                for (int i12 = 0; i12 < numberOfLayers; i12++) {
                    int id2 = layerDrawable.getId(i12);
                    drawableArr[i12] = b(layerDrawable.getDrawable(i12), id2 == 16908301 || id2 == 16908303);
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                for (int i13 = 0; i13 < numberOfLayers; i13++) {
                    layerDrawable2.setId(i13, layerDrawable.getId(i13));
                    layerDrawable2.setLayerGravity(i13, layerDrawable.getLayerGravity(i13));
                    layerDrawable2.setLayerWidth(i13, layerDrawable.getLayerWidth(i13));
                    layerDrawable2.setLayerHeight(i13, layerDrawable.getLayerHeight(i13));
                    layerDrawable2.setLayerInsetLeft(i13, layerDrawable.getLayerInsetLeft(i13));
                    layerDrawable2.setLayerInsetRight(i13, layerDrawable.getLayerInsetRight(i13));
                    layerDrawable2.setLayerInsetTop(i13, layerDrawable.getLayerInsetTop(i13));
                    layerDrawable2.setLayerInsetBottom(i13, layerDrawable.getLayerInsetBottom(i13));
                    layerDrawable2.setLayerInsetStart(i13, layerDrawable.getLayerInsetStart(i13));
                    layerDrawable2.setLayerInsetEnd(i13, layerDrawable.getLayerInsetEnd(i13));
                }
                return layerDrawable2;
            }
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (this.f22104b == null) {
                    this.f22104b = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null));
                shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                shapeDrawable.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter());
                return z12 ? new ClipDrawable(shapeDrawable, 3, 1) : shapeDrawable;
            }
        }
        return drawable;
    }
}
