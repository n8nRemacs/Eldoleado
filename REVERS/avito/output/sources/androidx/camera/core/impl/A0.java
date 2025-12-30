package androidx.camera.core.impl;

import androidx.camera.core.M;
import java.util.ArrayList;
import java.util.Set;

/* compiled from: RestrictedCameraControl.java */
@j.X
/* loaded from: classes.dex */
public class A0 extends V {

    /* renamed from: c, reason: collision with root package name */
    public final CameraControlInternal f23870c;

    /* renamed from: d, reason: collision with root package name */
    public volatile boolean f23871d;

    /* renamed from: e, reason: collision with root package name */
    @a
    @j.P
    public volatile Set<Integer> f23872e;

    /* compiled from: RestrictedCameraControl.java */
    public @interface a {
    }

    public A0(@j.N CameraControlInternal cameraControlInternal) {
        super(cameraControlInternal);
        this.f23871d = false;
        this.f23870c = cameraControlInternal;
    }

    @Override // androidx.camera.core.impl.V, androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Void> a(float f12) {
        return !m(0) ? androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Zoom is not supported")) : this.f23870c.a(f12);
    }

    @Override // androidx.camera.core.impl.V, androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Void> b(float f12) {
        return !m(0) ? androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Zoom is not supported")) : this.f23870c.b(f12);
    }

    @Override // androidx.camera.core.impl.V, androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Void> f(boolean z12) {
        return !m(6) ? androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Torch is not supported")) : this.f23870c.f(z12);
    }

    @Override // androidx.camera.core.impl.V, androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<androidx.camera.core.N> g(@j.N androidx.camera.core.M m12) {
        boolean z12;
        M.a aVar = new M.a(m12);
        boolean z13 = true;
        if (m12.f23606a.isEmpty() || m(1, 2)) {
            z12 = false;
        } else {
            aVar.b(1);
            z12 = true;
        }
        if (!m12.f23607b.isEmpty() && !m(3)) {
            aVar.b(2);
            z12 = true;
        }
        if (m12.f23608c.isEmpty() || m(4)) {
            z13 = z12;
        } else {
            aVar.b(4);
        }
        if (z13) {
            androidx.camera.core.M m13 = new androidx.camera.core.M(aVar);
            m12 = (m13.f23606a.isEmpty() && m13.f23607b.isEmpty() && m13.f23608c.isEmpty()) ? null : new androidx.camera.core.M(aVar);
        }
        return m12 == null ? androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("FocusMetering is not supported")) : this.f23870c.g(m12);
    }

    @Override // androidx.camera.core.impl.V, androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Integer> i(int i12) {
        return !m(7) ? androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("ExposureCompensation is not supported")) : this.f23870c.i(i12);
    }

    public final boolean m(@j.N @a int... iArr) {
        if (!this.f23871d || this.f23872e == null) {
            return true;
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i12 : iArr) {
            arrayList.add(Integer.valueOf(i12));
        }
        return this.f23872e.containsAll(arrayList);
    }
}
