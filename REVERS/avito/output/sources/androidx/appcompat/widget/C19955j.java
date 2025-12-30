package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.C22823h0;
import l.C43521a;

/* compiled from: AppCompatBackgroundHelper.java */
/* renamed from: androidx.appcompat.widget.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19955j {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final View f22548a;

    /* renamed from: d, reason: collision with root package name */
    public B0 f22551d;

    /* renamed from: e, reason: collision with root package name */
    public B0 f22552e;

    /* renamed from: f, reason: collision with root package name */
    public B0 f22553f;

    /* renamed from: c, reason: collision with root package name */
    public int f22550c = -1;

    /* renamed from: b, reason: collision with root package name */
    public final r f22549b = r.a();

    public C19955j(@j.N View view) {
        this.f22548a = view;
    }

    public final void a() {
        View view = this.f22548a;
        Drawable background = view.getBackground();
        if (background != null) {
            if (this.f22551d != null) {
                if (this.f22553f == null) {
                    this.f22553f = new B0();
                }
                B0 b02 = this.f22553f;
                b02.f22095a = null;
                b02.f22098d = false;
                b02.f22096b = null;
                b02.f22097c = false;
                ColorStateList colorStateListJ = C22823h0.j(view);
                if (colorStateListJ != null) {
                    b02.f22098d = true;
                    b02.f22095a = colorStateListJ;
                }
                PorterDuff.Mode modeK = C22823h0.k(view);
                if (modeK != null) {
                    b02.f22097c = true;
                    b02.f22096b = modeK;
                }
                if (b02.f22098d || b02.f22097c) {
                    r.e(background, b02, view.getDrawableState());
                    return;
                }
            }
            B0 b03 = this.f22552e;
            if (b03 != null) {
                r.e(background, b03, view.getDrawableState());
                return;
            }
            B0 b04 = this.f22551d;
            if (b04 != null) {
                r.e(background, b04, view.getDrawableState());
            }
        }
    }

    public final ColorStateList b() {
        B0 b02 = this.f22552e;
        if (b02 != null) {
            return b02.f22095a;
        }
        return null;
    }

    public final PorterDuff.Mode c() {
        B0 b02 = this.f22552e;
        if (b02 != null) {
            return b02.f22096b;
        }
        return null;
    }

    public final void d(@j.P AttributeSet attributeSet, int i12) {
        ColorStateList colorStateListG;
        View view = this.f22548a;
        Context context = view.getContext();
        int[] iArr = C43521a.m.f413350A;
        D0 d0E = D0.e(context, attributeSet, iArr, i12);
        TypedArray typedArray = d0E.f22121b;
        View view2 = this.f22548a;
        C22823h0.B(view2, view2.getContext(), iArr, attributeSet, d0E.f22121b, i12);
        try {
            if (typedArray.hasValue(0)) {
                this.f22550c = typedArray.getResourceId(0, -1);
                r rVar = this.f22549b;
                Context context2 = view.getContext();
                int i13 = this.f22550c;
                synchronized (rVar) {
                    colorStateListG = rVar.f22611a.g(i13, context2);
                }
                if (colorStateListG != null) {
                    g(colorStateListG);
                }
            }
            if (typedArray.hasValue(1)) {
                C22823h0.F(view, d0E.a(1));
            }
            if (typedArray.hasValue(2)) {
                C22823h0.G(view, V.c(typedArray.getInt(2, -1), null));
            }
        } finally {
            d0E.f();
        }
    }

    public final void e() {
        this.f22550c = -1;
        g(null);
        a();
    }

    public final void f(int i12) {
        ColorStateList colorStateListG;
        this.f22550c = i12;
        r rVar = this.f22549b;
        if (rVar != null) {
            Context context = this.f22548a.getContext();
            synchronized (rVar) {
                colorStateListG = rVar.f22611a.g(i12, context);
            }
        } else {
            colorStateListG = null;
        }
        g(colorStateListG);
        a();
    }

    public final void g(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.f22551d == null) {
                this.f22551d = new B0();
            }
            B0 b02 = this.f22551d;
            b02.f22095a = colorStateList;
            b02.f22098d = true;
        } else {
            this.f22551d = null;
        }
        a();
    }

    public final void h(ColorStateList colorStateList) {
        if (this.f22552e == null) {
            this.f22552e = new B0();
        }
        B0 b02 = this.f22552e;
        b02.f22095a = colorStateList;
        b02.f22098d = true;
        a();
    }

    public final void i(PorterDuff.Mode mode) {
        if (this.f22552e == null) {
            this.f22552e = new B0();
        }
        B0 b02 = this.f22552e;
        b02.f22096b = mode;
        b02.f22097c = true;
        a();
    }
}
