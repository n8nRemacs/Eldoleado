package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Range;
import android.util.Rational;

/* compiled from: ExposureStateImpl.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.n0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20023n0 implements androidx.camera.core.K {

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.n f23381b;

    /* renamed from: a, reason: collision with root package name */
    public final Object f23380a = new Object();

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public int f23382c = 0;

    public C20023n0(androidx.camera.camera2.internal.compat.n nVar) {
        this.f23381b = nVar;
    }

    @Override // androidx.camera.core.K
    public final boolean a() {
        Range range = (Range) this.f23381b.a(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
        return (range == null || ((Integer) range.getLower()).intValue() == 0 || ((Integer) range.getUpper()).intValue() == 0) ? false : true;
    }

    @Override // androidx.camera.core.K
    public final int b() {
        int i12;
        synchronized (this.f23380a) {
            i12 = this.f23382c;
        }
        return i12;
    }

    @Override // androidx.camera.core.K
    @j.N
    public final Rational c() {
        return !a() ? Rational.ZERO : (Rational) this.f23381b.a(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP);
    }

    @Override // androidx.camera.core.K
    @j.N
    public final Range<Integer> d() {
        return (Range) this.f23381b.a(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
    }
}
