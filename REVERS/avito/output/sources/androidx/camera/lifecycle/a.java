package androidx.camera.lifecycle;

import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.camera.lifecycle.e;
import androidx.view.InterfaceC22983P;
import j.N;

/* compiled from: AutoValue_LifecycleCameraRepository_Key.java */
/* loaded from: classes.dex */
final class a extends e.a {

    /* renamed from: a, reason: collision with root package name */
    public final InterfaceC22983P f24656a;

    /* renamed from: b, reason: collision with root package name */
    public final CameraUseCaseAdapter.a f24657b;

    public a(InterfaceC22983P interfaceC22983P, CameraUseCaseAdapter.a aVar) {
        if (interfaceC22983P == null) {
            throw new NullPointerException("Null lifecycleOwner");
        }
        this.f24656a = interfaceC22983P;
        if (aVar == null) {
            throw new NullPointerException("Null cameraId");
        }
        this.f24657b = aVar;
    }

    @Override // androidx.camera.lifecycle.e.a
    @N
    public final CameraUseCaseAdapter.a a() {
        return this.f24657b;
    }

    @Override // androidx.camera.lifecycle.e.a
    @N
    public final InterfaceC22983P b() {
        return this.f24656a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof e.a)) {
            return false;
        }
        e.a aVar = (e.a) obj;
        return this.f24656a.equals(aVar.b()) && this.f24657b.equals(aVar.a());
    }

    public final int hashCode() {
        return ((this.f24656a.hashCode() ^ 1000003) * 1000003) ^ this.f24657b.hashCode();
    }

    public final String toString() {
        return "Key{lifecycleOwner=" + this.f24656a + ", cameraId=" + this.f24657b + "}";
    }
}
