package androidx.appcompat.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.b;
import androidx.appcompat.graphics.drawable.e;
import androidx.collection.C20204c0;
import androidx.collection.q1;
import androidx.collection.r1;
import androidx.core.graphics.drawable.i;
import j.N;
import j.P;

/* compiled from: AnimatedStateListDrawableCompat.java */
/* loaded from: classes.dex */
public class a extends androidx.appcompat.graphics.drawable.e implements i {

    /* renamed from: q, reason: collision with root package name */
    public c f21581q;

    /* renamed from: r, reason: collision with root package name */
    public g f21582r;

    /* renamed from: s, reason: collision with root package name */
    public int f21583s;

    /* renamed from: t, reason: collision with root package name */
    public int f21584t;

    /* renamed from: u, reason: collision with root package name */
    public boolean f21585u;

    /* compiled from: AnimatedStateListDrawableCompat.java */
    public static class b extends g {

        /* renamed from: a, reason: collision with root package name */
        public final Animatable f21586a;

        public b(Animatable animatable) {
            super();
            this.f21586a = animatable;
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final void c() {
            this.f21586a.start();
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final void d() {
            this.f21586a.stop();
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat.java */
    public static class c extends e.a {

        /* renamed from: J, reason: collision with root package name */
        public C20204c0<Long> f21587J;

        /* renamed from: K, reason: collision with root package name */
        public q1<Integer> f21588K;

        public c(@P c cVar, @N a aVar, @P Resources resources) {
            super(cVar, aVar, resources);
            if (cVar != null) {
                this.f21587J = cVar.f21587J;
                this.f21588K = cVar.f21588K;
            } else {
                this.f21587J = new C20204c0<>();
                this.f21588K = new q1<>();
            }
        }

        @Override // androidx.appcompat.graphics.drawable.e.a, androidx.appcompat.graphics.drawable.b.d
        public final void d() {
            this.f21587J = this.f21587J.clone();
            this.f21588K = this.f21588K.clone();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object[]] */
        /* JADX WARN: Type inference failed for: r5v2 */
        public final int f(int i12) {
            ?? r52;
            if (i12 < 0) {
                return 0;
            }
            q1<Integer> q1Var = this.f21588K;
            int i13 = 0;
            int iA2 = K.a.a(q1Var.f25803e, i12, q1Var.f25801c);
            if (iA2 >= 0 && (r52 = q1Var.f25802d[iA2]) != r1.f25805a) {
                i13 = r52;
            }
            return i13.intValue();
        }

        @Override // androidx.appcompat.graphics.drawable.e.a, android.graphics.drawable.Drawable.ConstantState
        @N
        public final Drawable newDrawable() {
            return new a(this, null);
        }

        @Override // androidx.appcompat.graphics.drawable.e.a, android.graphics.drawable.Drawable.ConstantState
        @N
        public final Drawable newDrawable(Resources resources) {
            return new a(this, resources);
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat.java */
    public static class d extends g {

        /* renamed from: a, reason: collision with root package name */
        public final androidx.vectordrawable.graphics.drawable.c f21589a;

        public d(androidx.vectordrawable.graphics.drawable.c cVar) {
            super();
            this.f21589a = cVar;
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final void c() {
            this.f21589a.start();
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final void d() {
            this.f21589a.stop();
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat.java */
    public static class e extends g {

        /* renamed from: a, reason: collision with root package name */
        public final ObjectAnimator f21590a;

        /* renamed from: b, reason: collision with root package name */
        public final boolean f21591b;

        public e(AnimationDrawable animationDrawable, boolean z12, boolean z13) {
            super();
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            int i12 = z12 ? numberOfFrames - 1 : 0;
            int i13 = z12 ? 0 : numberOfFrames - 1;
            f fVar = new f();
            int numberOfFrames2 = animationDrawable.getNumberOfFrames();
            fVar.f21593b = numberOfFrames2;
            int[] iArr = fVar.f21592a;
            if (iArr == null || iArr.length < numberOfFrames2) {
                fVar.f21592a = new int[numberOfFrames2];
            }
            int[] iArr2 = fVar.f21592a;
            int i14 = 0;
            for (int i15 = 0; i15 < numberOfFrames2; i15++) {
                int duration = animationDrawable.getDuration(z12 ? (numberOfFrames2 - i15) - 1 : i15);
                iArr2[i15] = duration;
                i14 += duration;
            }
            fVar.f21594c = i14;
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", i12, i13);
            objectAnimatorOfInt.setAutoCancel(true);
            objectAnimatorOfInt.setDuration(fVar.f21594c);
            objectAnimatorOfInt.setInterpolator(fVar);
            this.f21591b = z13;
            this.f21590a = objectAnimatorOfInt;
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final boolean a() {
            return this.f21591b;
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final void b() {
            this.f21590a.reverse();
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final void c() {
            this.f21590a.start();
        }

        @Override // androidx.appcompat.graphics.drawable.a.g
        public final void d() {
            this.f21590a.cancel();
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat.java */
    public static class f implements TimeInterpolator {

        /* renamed from: a, reason: collision with root package name */
        public int[] f21592a;

        /* renamed from: b, reason: collision with root package name */
        public int f21593b;

        /* renamed from: c, reason: collision with root package name */
        public int f21594c;

        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f12) {
            int i12 = (int) ((f12 * this.f21594c) + 0.5f);
            int i13 = this.f21593b;
            int[] iArr = this.f21592a;
            int i14 = 0;
            while (i14 < i13) {
                int i15 = iArr[i14];
                if (i12 < i15) {
                    break;
                }
                i12 -= i15;
                i14++;
            }
            return (i14 / i13) + (i14 < i13 ? i12 / this.f21594c : 0.0f);
        }
    }

    public a() {
        this(null, null);
    }

    @Override // androidx.appcompat.graphics.drawable.e, androidx.appcompat.graphics.drawable.b
    public final b.d b() {
        return new c(this.f21581q, this, null);
    }

    @Override // androidx.appcompat.graphics.drawable.e, androidx.appcompat.graphics.drawable.b
    public final void e(@N b.d dVar) {
        super.e(dVar);
        if (dVar instanceof c) {
            this.f21581q = (c) dVar;
        }
    }

    @Override // androidx.appcompat.graphics.drawable.e
    /* renamed from: f */
    public final e.a b() {
        return new c(this.f21581q, this, null);
    }

    @Override // androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    public final void jumpToCurrentState() {
        super.jumpToCurrentState();
        g gVar = this.f21582r;
        if (gVar != null) {
            gVar.d();
            this.f21582r = null;
            d(this.f21583s);
            this.f21583s = -1;
            this.f21584t = -1;
        }
    }

    @Override // androidx.appcompat.graphics.drawable.e, androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    @N
    public final Drawable mutate() {
        if (!this.f21585u) {
            super.mutate();
            this.f21581q.d();
            this.f21585u = true;
        }
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d1  */
    @Override // androidx.appcompat.graphics.drawable.e, androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onStateChange(@j.N int[] r15) {
        /*
            Method dump skipped, instructions count: 220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.graphics.drawable.a.onStateChange(int[]):boolean");
    }

    @Override // androidx.appcompat.graphics.drawable.b, android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z12, boolean z13) {
        boolean visible = super.setVisible(z12, z13);
        g gVar = this.f21582r;
        if (gVar != null && (visible || z13)) {
            if (z12) {
                gVar.c();
            } else {
                jumpToCurrentState();
            }
        }
        return visible;
    }

    public a(@P c cVar, @P Resources resources) {
        this.f21583s = -1;
        this.f21584t = -1;
        e(new c(cVar, this, resources));
        onStateChange(getState());
        jumpToCurrentState();
    }

    /* compiled from: AnimatedStateListDrawableCompat.java */
    public static abstract class g {
        public g() {
        }

        public boolean a() {
            return false;
        }

        public abstract void c();

        public abstract void d();

        public void b() {
        }
    }
}
