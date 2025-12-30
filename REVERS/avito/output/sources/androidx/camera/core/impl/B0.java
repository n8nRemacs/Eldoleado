package androidx.camera.core.impl;

import android.util.Range;
import android.util.Rational;
import androidx.camera.core.Q0;
import androidx.view.AbstractC22991Y;
import androidx.view.C23038g0;

/* compiled from: RestrictedCameraInfo.java */
@j.X
/* loaded from: classes.dex */
public class B0 extends W {

    /* renamed from: b, reason: collision with root package name */
    public final B f23873b;

    /* renamed from: c, reason: collision with root package name */
    public final A0 f23874c;

    /* compiled from: RestrictedCameraInfo.java */
    public class a implements androidx.camera.core.K {
        @Override // androidx.camera.core.K
        public final boolean a() {
            return false;
        }

        @Override // androidx.camera.core.K
        public final int b() {
            return 0;
        }

        @Override // androidx.camera.core.K
        @j.N
        public final Rational c() {
            return Rational.ZERO;
        }

        @Override // androidx.camera.core.K
        @j.N
        public final Range<Integer> d() {
            return new Range<>(0, 0);
        }
    }

    public B0(@j.N B b12, @j.N A0 a02) {
        super(b12);
        this.f23873b = b12;
        this.f23874c = a02;
    }

    @Override // androidx.camera.core.impl.W, androidx.camera.core.InterfaceC20142t
    @j.N
    public final AbstractC22991Y<Q0> e() {
        return !this.f23874c.m(0) ? new C23038g0(androidx.camera.core.internal.e.e()) : this.f23873b.e();
    }

    @Override // androidx.camera.core.impl.W, androidx.camera.core.InterfaceC20142t
    @j.N
    public final AbstractC22991Y<Integer> h() {
        return !this.f23874c.m(6) ? new C23038g0(0) : this.f23873b.h();
    }

    @Override // androidx.camera.core.impl.W, androidx.camera.core.InterfaceC20142t
    public final boolean n() {
        if (this.f23874c.m(5)) {
            return this.f23873b.n();
        }
        return false;
    }

    @Override // androidx.camera.core.impl.W, androidx.camera.core.impl.B
    @j.N
    public final B p() {
        return this.f23873b;
    }

    @Override // androidx.camera.core.impl.W, androidx.camera.core.InterfaceC20142t
    @j.N
    public final androidx.camera.core.K q() {
        return !this.f23874c.m(7) ? new a() : this.f23873b.q();
    }
}
