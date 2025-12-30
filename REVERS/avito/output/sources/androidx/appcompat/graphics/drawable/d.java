package androidx.appcompat.graphics.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.r;
import j.InterfaceC42167x;
import j.N;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: DrawerArrowDrawable.java */
/* loaded from: classes.dex */
public class d extends Drawable {

    /* renamed from: e, reason: collision with root package name */
    public static final float f21645e = (float) Math.toRadians(45.0d);

    /* renamed from: a, reason: collision with root package name */
    public final Paint f21646a;

    /* renamed from: b, reason: collision with root package name */
    public final Path f21647b;

    /* renamed from: c, reason: collision with root package name */
    public float f21648c;

    /* renamed from: d, reason: collision with root package name */
    public final int f21649d;

    /* compiled from: DrawerArrowDrawable.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface a {
    }

    public d() {
        Paint paint = new Paint();
        this.f21646a = paint;
        this.f21647b = new Path();
        this.f21649d = 2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        throw null;
    }

    public static float a(float f12, float f13, float f14) {
        return r.c(f13, f12, f14, f12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(@N Canvas canvas) {
        Rect bounds = getBounds();
        int i12 = this.f21649d;
        boolean z12 = false;
        if (i12 != 0 && (i12 == 1 || (i12 == 3 ? getLayoutDirection() == 0 : getLayoutDirection() == 1))) {
            z12 = true;
        }
        float fA2 = a(0.0f, (float) Math.sqrt(0.0f * 0.0f * 2.0f), this.f21648c);
        float fA3 = a(0.0f, 0.0f, this.f21648c);
        float fRound = Math.round(a(0.0f, 0.0f, this.f21648c));
        double d12 = fA2;
        double dA2 = a(0.0f, f21645e, this.f21648c);
        float fRound2 = Math.round(Math.cos(dA2) * d12);
        float fRound3 = Math.round(Math.sin(dA2) * d12);
        Path path = this.f21647b;
        path.rewind();
        Paint paint = this.f21646a;
        float fA4 = a(paint.getStrokeWidth() + 0.0f, -0.0f, this.f21648c);
        float f12 = (-fA3) / 2.0f;
        path.moveTo(f12 + fRound, 0.0f);
        path.rLineTo(fA3 - (fRound * 2.0f), 0.0f);
        path.moveTo(f12, fA4);
        path.rLineTo(fRound2, fRound3);
        path.moveTo(f12, -fA4);
        path.rLineTo(fRound2, -fRound3);
        path.close();
        canvas.save();
        float strokeWidth = paint.getStrokeWidth();
        canvas.translate(bounds.centerX(), (strokeWidth * 1.5f) + 0.0f + ((((int) ((bounds.height() - (3.0f * strokeWidth)) - (2.0f * 0.0f))) / 4) * 2));
        if (z12) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i12) {
        Paint paint = this.f21646a;
        if (i12 != paint.getAlpha()) {
            paint.setAlpha(i12);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f21646a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setProgress(@InterfaceC42167x float f12) {
        if (this.f21648c != f12) {
            this.f21648c = f12;
            invalidateSelf();
        }
    }
}
