package androidx.camera.core.impl;

import android.graphics.Rect;
import androidx.camera.core.impl.SessionConfig;
import java.util.ArrayList;

/* compiled from: ForwardingCameraControl.java */
@j.X
/* loaded from: classes.dex */
public class V implements CameraControlInternal {

    /* renamed from: b, reason: collision with root package name */
    public final CameraControlInternal f24061b;

    public V(@j.N CameraControlInternal cameraControlInternal) {
        this.f24061b = cameraControlInternal;
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public com.google.common.util.concurrent.D0<Void> a(float f12) {
        return this.f24061b.a(f12);
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public com.google.common.util.concurrent.D0<Void> b(float f12) {
        return this.f24061b.b(f12);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    @j.N
    public final Config c() {
        return this.f24061b.c();
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    @j.N
    public com.google.common.util.concurrent.D0 d(@j.N ArrayList arrayList, int i12, int i13) {
        return this.f24061b.d(arrayList, i12, i13);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void e() {
        this.f24061b.e();
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public com.google.common.util.concurrent.D0<Void> f(boolean z12) {
        return this.f24061b.f(z12);
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public com.google.common.util.concurrent.D0<androidx.camera.core.N> g(@j.N androidx.camera.core.M m12) {
        return this.f24061b.g(m12);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void h(@j.N Config config) {
        this.f24061b.h(config);
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public com.google.common.util.concurrent.D0<Integer> i(int i12) {
        return this.f24061b.i(i12);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    @j.N
    public final Rect j() {
        return this.f24061b.j();
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void k(int i12) {
        this.f24061b.k(i12);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void l(@j.N SessionConfig.b bVar) {
        this.f24061b.l(bVar);
    }
}
