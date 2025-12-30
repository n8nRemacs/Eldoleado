package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.avito.android.R;

/* compiled from: RoundRectDrawableWithShadow.java */
/* loaded from: classes.dex */
class i extends Drawable {

    /* renamed from: q, reason: collision with root package name */
    public static final double f25503q = Math.cos(Math.toRadians(45.0d));

    /* renamed from: a, reason: collision with root package name */
    public final int f25504a;

    /* renamed from: c, reason: collision with root package name */
    public final Paint f25506c;

    /* renamed from: d, reason: collision with root package name */
    public final Paint f25507d;

    /* renamed from: e, reason: collision with root package name */
    public final RectF f25508e;

    /* renamed from: f, reason: collision with root package name */
    public float f25509f;

    /* renamed from: g, reason: collision with root package name */
    public Path f25510g;

    /* renamed from: h, reason: collision with root package name */
    public float f25511h;

    /* renamed from: i, reason: collision with root package name */
    public float f25512i;

    /* renamed from: j, reason: collision with root package name */
    public float f25513j;

    /* renamed from: k, reason: collision with root package name */
    public ColorStateList f25514k;

    /* renamed from: m, reason: collision with root package name */
    public final int f25516m;

    /* renamed from: n, reason: collision with root package name */
    public final int f25517n;

    /* renamed from: l, reason: collision with root package name */
    public boolean f25515l = true;

    /* renamed from: o, reason: collision with root package name */
    public boolean f25518o = true;

    /* renamed from: p, reason: collision with root package name */
    public boolean f25519p = false;

    /* renamed from: b, reason: collision with root package name */
    public final Paint f25505b = new Paint(5);

    /* compiled from: RoundRectDrawableWithShadow.java */
    public interface a {
    }

    public i(Resources resources, ColorStateList colorStateList, float f12, float f13, float f14) {
        this.f25516m = resources.getColor(R.color.cardview_shadow_start_color);
        this.f25517n = resources.getColor(R.color.cardview_shadow_end_color);
        this.f25504a = resources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
        colorStateList = colorStateList == null ? ColorStateList.valueOf(0) : colorStateList;
        this.f25514k = colorStateList;
        this.f25505b.setColor(colorStateList.getColorForState(getState(), this.f25514k.getDefaultColor()));
        Paint paint = new Paint(5);
        this.f25506c = paint;
        paint.setStyle(Paint.Style.FILL);
        this.f25509f = (int) (f12 + 0.5f);
        this.f25508e = new RectF();
        Paint paint2 = new Paint(paint);
        this.f25507d = paint2;
        paint2.setAntiAlias(false);
        c(f13, f14);
    }

    public static float a(float f12, float f13, boolean z12) {
        if (!z12) {
            return f12;
        }
        return (float) (((1.0d - f25503q) * f13) + f12);
    }

    public static float b(float f12, float f13, boolean z12) {
        if (!z12) {
            return f12 * 1.5f;
        }
        return (float) (((1.0d - f25503q) * f13) + (f12 * 1.5f));
    }

    public final void c(float f12, float f13) {
        if (f12 < 0.0f) {
            throw new IllegalArgumentException("Invalid shadow size " + f12 + ". Must be >= 0");
        }
        if (f13 < 0.0f) {
            throw new IllegalArgumentException("Invalid max shadow size " + f13 + ". Must be >= 0");
        }
        int i12 = (int) (f12 + 0.5f);
        if (i12 % 2 == 1) {
            i12--;
        }
        float f14 = i12;
        int i13 = (int) (f13 + 0.5f);
        if (i13 % 2 == 1) {
            i13--;
        }
        float f15 = i13;
        if (f14 > f15) {
            if (!this.f25519p) {
                this.f25519p = true;
            }
            f14 = f15;
        }
        if (this.f25513j == f14 && this.f25511h == f15) {
            return;
        }
        this.f25513j = f14;
        this.f25511h = f15;
        this.f25512i = (int) ((f14 * 1.5f) + this.f25504a + 0.5f);
        this.f25515l = true;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean z12;
        int i12;
        boolean z13 = this.f25515l;
        Paint paint = this.f25507d;
        Paint paint2 = this.f25506c;
        RectF rectF = this.f25508e;
        if (z13) {
            Rect bounds = getBounds();
            float f12 = this.f25511h;
            float f13 = 1.5f * f12;
            rectF.set(bounds.left + f12, bounds.top + f13, bounds.right - f12, bounds.bottom - f13);
            float f14 = this.f25509f;
            float f15 = -f14;
            RectF rectF2 = new RectF(f15, f15, f14, f14);
            RectF rectF3 = new RectF(rectF2);
            float f16 = -this.f25512i;
            rectF3.inset(f16, f16);
            Path path = this.f25510g;
            if (path == null) {
                this.f25510g = new Path();
            } else {
                path.reset();
            }
            this.f25510g.setFillType(Path.FillType.EVEN_ODD);
            this.f25510g.moveTo(-this.f25509f, 0.0f);
            this.f25510g.rLineTo(-this.f25512i, 0.0f);
            this.f25510g.arcTo(rectF3, 180.0f, 90.0f, false);
            this.f25510g.arcTo(rectF2, 270.0f, -90.0f, false);
            this.f25510g.close();
            float f17 = this.f25509f;
            float f18 = f17 / (this.f25512i + f17);
            float f19 = this.f25509f + this.f25512i;
            int i13 = this.f25516m;
            int i14 = this.f25517n;
            z12 = true;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            paint2.setShader(new RadialGradient(0.0f, 0.0f, f19, new int[]{i13, i13, i14}, new float[]{0.0f, f18, 1.0f}, tileMode));
            float f22 = -this.f25509f;
            float f23 = this.f25512i;
            paint.setShader(new LinearGradient(0.0f, f22 + f23, 0.0f, f22 - f23, new int[]{i13, i13, i14}, new float[]{0.0f, 0.5f, 1.0f}, tileMode));
            paint.setAntiAlias(false);
            this.f25515l = false;
        } else {
            z12 = true;
        }
        canvas.translate(0.0f, this.f25513j / 2.0f);
        float f24 = this.f25509f;
        float f25 = (-f24) - this.f25512i;
        float f26 = (this.f25513j / 2.0f) + f24 + this.f25504a;
        float f27 = f26 * 2.0f;
        boolean z14 = rectF.width() - f27 > 0.0f ? z12 : false;
        if (rectF.height() - f27 <= 0.0f) {
            z12 = false;
        }
        int iSave = canvas.save();
        canvas.translate(rectF.left + f26, rectF.top + f26);
        canvas.drawPath(this.f25510g, paint2);
        if (z14) {
            i12 = iSave;
            canvas.drawRect(0.0f, f25, rectF.width() - f27, -this.f25509f, paint);
        } else {
            i12 = iSave;
        }
        canvas.restoreToCount(i12);
        int iSave2 = canvas.save();
        canvas.translate(rectF.right - f26, rectF.bottom - f26);
        canvas.rotate(180.0f);
        canvas.drawPath(this.f25510g, paint2);
        if (z14) {
            canvas.drawRect(0.0f, f25, rectF.width() - f27, (-this.f25509f) + this.f25512i, paint);
        }
        canvas.restoreToCount(iSave2);
        int iSave3 = canvas.save();
        canvas.translate(rectF.left + f26, rectF.bottom - f26);
        canvas.rotate(270.0f);
        canvas.drawPath(this.f25510g, paint2);
        if (z12) {
            canvas.drawRect(0.0f, f25, rectF.height() - f27, -this.f25509f, paint);
        }
        canvas.restoreToCount(iSave3);
        int iSave4 = canvas.save();
        canvas.translate(rectF.right - f26, rectF.top + f26);
        canvas.rotate(90.0f);
        canvas.drawPath(this.f25510g, paint2);
        if (z12) {
            canvas.drawRect(0.0f, f25, rectF.height() - f27, -this.f25509f, paint);
        }
        canvas.restoreToCount(iSave4);
        canvas.translate(0.0f, (-this.f25513j) / 2.0f);
        throw null;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        int iCeil = (int) Math.ceil(b(this.f25511h, this.f25509f, this.f25518o));
        int iCeil2 = (int) Math.ceil(a(this.f25511h, this.f25509f, this.f25518o));
        rect.set(iCeil2, iCeil, iCeil2, iCeil);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList = this.f25514k;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.f25515l = true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        ColorStateList colorStateList = this.f25514k;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        Paint paint = this.f25505b;
        if (paint.getColor() == colorForState) {
            return false;
        }
        paint.setColor(colorForState);
        this.f25515l = true;
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i12) {
        this.f25505b.setAlpha(i12);
        this.f25506c.setAlpha(i12);
        this.f25507d.setAlpha(i12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f25505b.setColorFilter(colorFilter);
    }
}
