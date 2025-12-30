package androidx.appcompat.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.StateSet;
import androidx.appcompat.graphics.drawable.b;
import j.N;
import j.X;

/* compiled from: StateListDrawableCompat.java */
/* loaded from: classes.dex */
public class e extends b {

    /* renamed from: o, reason: collision with root package name */
    public a f21650o;

    /* renamed from: p, reason: collision with root package name */
    public boolean f21651p;

    /* compiled from: StateListDrawableCompat.java */
    public static class a extends b.d {

        /* renamed from: I, reason: collision with root package name */
        public int[][] f21652I;

        public a(a aVar, e eVar, Resources resources) {
            super(aVar, eVar, resources);
            if (aVar != null) {
                this.f21652I = aVar.f21652I;
            } else {
                this.f21652I = new int[this.f21624g.length][];
            }
        }

        @Override // androidx.appcompat.graphics.drawable.b.d
        public void d() {
            int[][] iArr = this.f21652I;
            int[][] iArr2 = new int[iArr.length][];
            for (int length = iArr.length - 1; length >= 0; length--) {
                int[] iArr3 = this.f21652I[length];
                iArr2[length] = iArr3 != null ? (int[]) iArr3.clone() : null;
            }
            this.f21652I = iArr2;
        }

        public final int e(int[] iArr) {
            int[][] iArr2 = this.f21652I;
            int i12 = this.f21625h;
            for (int i13 = 0; i13 < i12; i13++) {
                if (StateSet.stateSetMatches(iArr2[i13], iArr)) {
                    return i13;
                }
            }
            return -1;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @N
        public Drawable newDrawable() {
            return new e(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @N
        public Drawable newDrawable(Resources resources) {
            return new e(this, resources);
        }
    }

    public e() {
        this(null, null);
    }

    @Override // androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    @X
    public final void applyTheme(@N Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    @Override // androidx.appcompat.graphics.drawable.b
    public void e(@N b.d dVar) {
        super.e(dVar);
        if (dVar instanceof a) {
            this.f21650o = (a) dVar;
        }
    }

    @Override // androidx.appcompat.graphics.drawable.b
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public a b() {
        return new a(this.f21650o, this, null);
    }

    @Override // androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    @N
    public Drawable mutate() {
        if (!this.f21651p) {
            super.mutate();
            this.f21650o.d();
            this.f21651p = true;
        }
        return this;
    }

    @Override // androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    public boolean onStateChange(@N int[] iArr) {
        boolean zOnStateChange = super.onStateChange(iArr);
        int iE2 = this.f21650o.e(iArr);
        if (iE2 < 0) {
            iE2 = this.f21650o.e(StateSet.WILD_CARD);
        }
        return d(iE2) || zOnStateChange;
    }

    public e(a aVar, Resources resources) {
        e(new a(aVar, this, resources));
        onStateChange(getState());
    }
}
