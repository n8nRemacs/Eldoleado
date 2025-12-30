package androidx.camera.core.internal;

import android.util.Rational;
import android.util.Size;
import androidx.camera.core.impl.B;
import androidx.camera.core.impl.InterfaceC20088d0;
import j.N;
import j.P;
import j.X;

/* compiled from: SupportedOutputSizesSorterLegacy.java */
@X
/* loaded from: classes.dex */
class h {

    /* renamed from: a, reason: collision with root package name */
    public final int f24357a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24358b;

    /* renamed from: c, reason: collision with root package name */
    public final Rational f24359c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f24360d;

    public h(@N B b12, @P Rational rational) {
        this.f24357a = b12.f();
        this.f24358b = b12.b();
        this.f24359c = rational;
        boolean z12 = true;
        if (rational != null && rational.getNumerator() < rational.getDenominator()) {
            z12 = false;
        }
        this.f24360d = z12;
    }

    @P
    public final Size a(@N InterfaceC20088d0 interfaceC20088d0) {
        int iD2 = interfaceC20088d0.D(0);
        Size sizeK = interfaceC20088d0.k();
        if (sizeK == null) {
            return sizeK;
        }
        int iA2 = androidx.camera.core.impl.utils.e.a(androidx.camera.core.impl.utils.e.b(iD2), this.f24357a, 1 == this.f24358b);
        return (iA2 == 90 || iA2 == 270) ? new Size(sizeK.getHeight(), sizeK.getWidth()) : sizeK;
    }
}
