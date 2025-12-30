package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.cardview.widget.CardView;
import j.P;
import j.X;

/* compiled from: CardViewApi21Impl.java */
@X
/* loaded from: classes.dex */
class c implements g {
    @Override // androidx.cardview.widget.g
    public final float a(f fVar) {
        return fVar.d().getElevation();
    }

    @Override // androidx.cardview.widget.g
    public final void b(f fVar) {
        if (!fVar.e()) {
            fVar.f(0, 0, 0, 0);
            return;
        }
        float f12 = ((h) fVar.b()).f25496e;
        float f13 = ((h) fVar.b()).f25492a;
        int iCeil = (int) Math.ceil(i.a(f12, f13, fVar.c()));
        int iCeil2 = (int) Math.ceil(i.b(f12, f13, fVar.c()));
        fVar.f(iCeil, iCeil2, iCeil, iCeil2);
    }

    @Override // androidx.cardview.widget.g
    public final void c(f fVar, float f12) {
        h hVar = (h) fVar.b();
        boolean zE2 = fVar.e();
        boolean zC2 = fVar.c();
        if (f12 != hVar.f25496e || hVar.f25497f != zE2 || hVar.f25498g != zC2) {
            hVar.f25496e = f12;
            hVar.f25497f = zE2;
            hVar.f25498g = zC2;
            hVar.b(null);
            hVar.invalidateSelf();
        }
        b(fVar);
    }

    @Override // androidx.cardview.widget.g
    public final void d(f fVar, float f12) {
        fVar.d().setElevation(f12);
    }

    @Override // androidx.cardview.widget.g
    public final ColorStateList e(f fVar) {
        return ((h) fVar.b()).f25499h;
    }

    @Override // androidx.cardview.widget.g
    public final void f(CardView.a aVar, Context context, ColorStateList colorStateList, float f12, float f13, float f14) {
        aVar.g(new h(colorStateList, f12));
        CardView cardView = CardView.this;
        cardView.setClipToOutline(true);
        cardView.setElevation(f13);
        c(aVar, f14);
    }

    @Override // androidx.cardview.widget.g
    public final void g(f fVar) {
        c(fVar, i(fVar));
    }

    @Override // androidx.cardview.widget.g
    public final float h(f fVar) {
        return ((h) fVar.b()).f25492a;
    }

    @Override // androidx.cardview.widget.g
    public final float i(f fVar) {
        return ((h) fVar.b()).f25496e;
    }

    @Override // androidx.cardview.widget.g
    public final float j(f fVar) {
        return h(fVar) * 2.0f;
    }

    @Override // androidx.cardview.widget.g
    public final float k(f fVar) {
        return h(fVar) * 2.0f;
    }

    @Override // androidx.cardview.widget.g
    public final void l(f fVar, float f12) {
        h hVar = (h) fVar.b();
        if (f12 == hVar.f25492a) {
            return;
        }
        hVar.f25492a = f12;
        hVar.b(null);
        hVar.invalidateSelf();
    }

    @Override // androidx.cardview.widget.g
    public final void m(f fVar) {
        c(fVar, i(fVar));
    }

    @Override // androidx.cardview.widget.g
    public final void n(f fVar, @P ColorStateList colorStateList) {
        h hVar = (h) fVar.b();
        if (colorStateList == null) {
            hVar.getClass();
            colorStateList = ColorStateList.valueOf(0);
        }
        hVar.f25499h = colorStateList;
        hVar.f25493b.setColor(colorStateList.getColorForState(hVar.getState(), hVar.f25499h.getDefaultColor()));
        hVar.invalidateSelf();
    }
}
