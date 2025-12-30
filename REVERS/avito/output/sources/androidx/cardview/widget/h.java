package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import j.X;

/* compiled from: RoundRectDrawable.java */
@X
/* loaded from: classes.dex */
class h extends Drawable {

    /* renamed from: a, reason: collision with root package name */
    public float f25492a;

    /* renamed from: b, reason: collision with root package name */
    public final Paint f25493b;

    /* renamed from: c, reason: collision with root package name */
    public final RectF f25494c;

    /* renamed from: d, reason: collision with root package name */
    public final Rect f25495d;

    /* renamed from: e, reason: collision with root package name */
    public float f25496e;

    /* renamed from: h, reason: collision with root package name */
    public ColorStateList f25499h;

    /* renamed from: i, reason: collision with root package name */
    public PorterDuffColorFilter f25500i;

    /* renamed from: j, reason: collision with root package name */
    public ColorStateList f25501j;

    /* renamed from: f, reason: collision with root package name */
    public boolean f25497f = false;

    /* renamed from: g, reason: collision with root package name */
    public boolean f25498g = true;

    /* renamed from: k, reason: collision with root package name */
    public PorterDuff.Mode f25502k = PorterDuff.Mode.SRC_IN;

    public h(ColorStateList colorStateList, float f12) {
        this.f25492a = f12;
        Paint paint = new Paint(5);
        this.f25493b = paint;
        colorStateList = colorStateList == null ? ColorStateList.valueOf(0) : colorStateList;
        this.f25499h = colorStateList;
        paint.setColor(colorStateList.getColorForState(getState(), this.f25499h.getDefaultColor()));
        this.f25494c = new RectF();
        this.f25495d = new Rect();
    }

    public final PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public final void b(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        RectF rectF = this.f25494c;
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        Rect rect2 = this.f25495d;
        rect2.set(rect);
        if (this.f25497f) {
            rect2.inset((int) Math.ceil(i.a(this.f25496e, this.f25492a, this.f25498g)), (int) Math.ceil(i.b(this.f25496e, this.f25492a, this.f25498g)));
            rectF.set(rect2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean z12;
        Paint paint = this.f25493b;
        if (this.f25500i == null || paint.getColorFilter() != null) {
            z12 = false;
        } else {
            paint.setColorFilter(this.f25500i);
            z12 = true;
        }
        RectF rectF = this.f25494c;
        float f12 = this.f25492a;
        canvas.drawRoundRect(rectF, f12, f12, paint);
        if (z12) {
            paint.setColorFilter(null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        outline.setRoundRect(this.f25495d, this.f25492a);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.f25501j;
        return (colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.f25499h) != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        b(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.f25499h;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        Paint paint = this.f25493b;
        boolean z12 = colorForState != paint.getColor();
        if (z12) {
            paint.setColor(colorForState);
        }
        ColorStateList colorStateList2 = this.f25501j;
        if (colorStateList2 == null || (mode = this.f25502k) == null) {
            return z12;
        }
        this.f25500i = a(colorStateList2, mode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i12) {
        this.f25493b.setAlpha(i12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f25493b.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        this.f25501j = colorStateList;
        this.f25500i = a(colorStateList, this.f25502k);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        this.f25502k = mode;
        this.f25500i = a(this.f25501j, mode);
        invalidateSelf();
    }
}
