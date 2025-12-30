package androidx.camera.core;

import androidx.camera.core.CameraState;

/* compiled from: AutoValue_CameraState_StateError.java */
/* renamed from: androidx.camera.core.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20059g extends CameraState.a {

    /* renamed from: a, reason: collision with root package name */
    public final int f23742a;

    /* renamed from: b, reason: collision with root package name */
    public final Throwable f23743b;

    public C20059g(int i12, @j.P Throwable th2) {
        this.f23742a = i12;
        this.f23743b = th2;
    }

    @Override // androidx.camera.core.CameraState.a
    @j.P
    public final Throwable c() {
        return this.f23743b;
    }

    @Override // androidx.camera.core.CameraState.a
    public final int d() {
        return this.f23742a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CameraState.a)) {
            return false;
        }
        CameraState.a aVar = (CameraState.a) obj;
        if (this.f23742a == aVar.d()) {
            Throwable th2 = this.f23743b;
            if (th2 == null) {
                if (aVar.c() == null) {
                    return true;
                }
            } else if (th2.equals(aVar.c())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i12 = (this.f23742a ^ 1000003) * 1000003;
        Throwable th2 = this.f23743b;
        return i12 ^ (th2 == null ? 0 : th2.hashCode());
    }

    public final String toString() {
        return "StateError{code=" + this.f23742a + ", cause=" + this.f23743b + "}";
    }
}
