package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.view.C22823h0;
import com.avito.android.R;
import l.C43521a;

/* compiled from: AppCompatSeekBarHelper.java */
/* loaded from: classes.dex */
class I extends C {

    /* renamed from: d, reason: collision with root package name */
    public final H f22155d;

    /* renamed from: e, reason: collision with root package name */
    public Drawable f22156e;

    /* renamed from: f, reason: collision with root package name */
    public ColorStateList f22157f;

    /* renamed from: g, reason: collision with root package name */
    public PorterDuff.Mode f22158g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f22159h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f22160i;

    public I(H h12) {
        super(h12);
        this.f22157f = null;
        this.f22158g = null;
        this.f22159h = false;
        this.f22160i = false;
        this.f22155d = h12;
    }

    @Override // androidx.appcompat.widget.C
    public final void a(AttributeSet attributeSet, int i12) {
        super.a(attributeSet, R.attr.seekBarStyle);
        H h12 = this.f22155d;
        Context context = h12.getContext();
        int[] iArr = C43521a.m.f413358g;
        D0 d0E = D0.e(context, attributeSet, iArr, R.attr.seekBarStyle);
        C22823h0.B(h12, h12.getContext(), iArr, attributeSet, d0E.f22121b, R.attr.seekBarStyle);
        Drawable drawableC = d0E.c(0);
        if (drawableC != null) {
            h12.setThumb(drawableC);
        }
        Drawable drawableB = d0E.b(1);
        Drawable drawable = this.f22156e;
        if (drawable != null) {
            drawable.setCallback(null);
        }
        this.f22156e = drawableB;
        if (drawableB != null) {
            drawableB.setCallback(h12);
            drawableB.setLayoutDirection(h12.getLayoutDirection());
            if (drawableB.isStateful()) {
                drawableB.setState(h12.getDrawableState());
            }
            c();
        }
        h12.invalidate();
        TypedArray typedArray = d0E.f22121b;
        if (typedArray.hasValue(3)) {
            this.f22158g = V.c(typedArray.getInt(3, -1), this.f22158g);
            this.f22160i = true;
        }
        if (typedArray.hasValue(2)) {
            this.f22157f = d0E.a(2);
            this.f22159h = true;
        }
        d0E.f();
        c();
    }

    public final void c() {
        Drawable drawable = this.f22156e;
        if (drawable != null) {
            if (this.f22159h || this.f22160i) {
                Drawable drawableMutate = drawable.mutate();
                this.f22156e = drawableMutate;
                if (this.f22159h) {
                    drawableMutate.setTintList(this.f22157f);
                }
                if (this.f22160i) {
                    this.f22156e.setTintMode(this.f22158g);
                }
                if (this.f22156e.isStateful()) {
                    this.f22156e.setState(this.f22155d.getDrawableState());
                }
            }
        }
    }

    public final void d(Canvas canvas) {
        if (this.f22156e != null) {
            int max = this.f22155d.getMax();
            if (max > 1) {
                int intrinsicWidth = this.f22156e.getIntrinsicWidth();
                int intrinsicHeight = this.f22156e.getIntrinsicHeight();
                int i12 = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i13 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.f22156e.setBounds(-i12, -i13, i12, i13);
                float width = ((r0.getWidth() - r0.getPaddingLeft()) - r0.getPaddingRight()) / max;
                int iSave = canvas.save();
                canvas.translate(r0.getPaddingLeft(), r0.getHeight() / 2);
                for (int i14 = 0; i14 <= max; i14++) {
                    this.f22156e.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(iSave);
            }
        }
    }
}
