package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.cardview.widget.CardView;
import j.P;

/* compiled from: CardViewBaseImpl.java */
/* loaded from: classes.dex */
class e implements g {
    public e() {
        new RectF();
    }

    @Override // androidx.cardview.widget.g
    public final float a(f fVar) {
        return ((i) fVar.b()).f25513j;
    }

    @Override // androidx.cardview.widget.g
    public final void b(f fVar) {
        Rect rect = new Rect();
        ((i) fVar.b()).getPadding(rect);
        fVar.a((int) Math.ceil(k(fVar)), (int) Math.ceil(j(fVar)));
        fVar.f(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // androidx.cardview.widget.g
    public final void c(f fVar, float f12) {
        i iVar = (i) fVar.b();
        iVar.c(iVar.f25513j, f12);
        b(fVar);
    }

    @Override // androidx.cardview.widget.g
    public final void d(f fVar, float f12) {
        i iVar = (i) fVar.b();
        iVar.c(f12, iVar.f25511h);
    }

    @Override // androidx.cardview.widget.g
    public final ColorStateList e(f fVar) {
        return ((i) fVar.b()).f25514k;
    }

    @Override // androidx.cardview.widget.g
    public final void f(CardView.a aVar, Context context, ColorStateList colorStateList, float f12, float f13, float f14) {
        i iVar = new i(context.getResources(), colorStateList, f12, f13, f14);
        iVar.f25518o = CardView.this.getPreventCornerOverlap();
        iVar.invalidateSelf();
        aVar.g(iVar);
        b(aVar);
    }

    @Override // androidx.cardview.widget.g
    public final float h(f fVar) {
        return ((i) fVar.b()).f25509f;
    }

    @Override // androidx.cardview.widget.g
    public final float i(f fVar) {
        return ((i) fVar.b()).f25511h;
    }

    @Override // androidx.cardview.widget.g
    public final float j(f fVar) {
        i iVar = (i) fVar.b();
        float f12 = iVar.f25511h;
        float f13 = iVar.f25509f;
        float f14 = iVar.f25504a;
        return (((iVar.f25511h * 1.5f) + f14) * 2.0f) + (Math.max(f12, ((f12 * 1.5f) / 2.0f) + f13 + f14) * 2.0f);
    }

    @Override // androidx.cardview.widget.g
    public final float k(f fVar) {
        i iVar = (i) fVar.b();
        float f12 = iVar.f25511h;
        float f13 = iVar.f25509f;
        float f14 = iVar.f25504a;
        return ((iVar.f25511h + f14) * 2.0f) + (Math.max(f12, (f12 / 2.0f) + f13 + f14) * 2.0f);
    }

    @Override // androidx.cardview.widget.g
    public final void l(f fVar, float f12) {
        i iVar = (i) fVar.b();
        if (f12 < 0.0f) {
            iVar.getClass();
            throw new IllegalArgumentException("Invalid radius " + f12 + ". Must be >= 0");
        }
        float f13 = (int) (f12 + 0.5f);
        if (iVar.f25509f != f13) {
            iVar.f25509f = f13;
            iVar.f25515l = true;
            iVar.invalidateSelf();
        }
        b(fVar);
    }

    @Override // androidx.cardview.widget.g
    public final void m(f fVar) {
        i iVar = (i) fVar.b();
        iVar.f25518o = fVar.c();
        iVar.invalidateSelf();
        b(fVar);
    }

    @Override // androidx.cardview.widget.g
    public final void n(f fVar, @P ColorStateList colorStateList) {
        i iVar = (i) fVar.b();
        if (colorStateList == null) {
            iVar.getClass();
            colorStateList = ColorStateList.valueOf(0);
        }
        iVar.f25514k = colorStateList;
        iVar.f25505b.setColor(colorStateList.getColorForState(iVar.getState(), iVar.f25514k.getDefaultColor()));
        iVar.invalidateSelf();
    }

    @Override // androidx.cardview.widget.g
    public final void g(f fVar) {
    }
}
