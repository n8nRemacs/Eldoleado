package androidx.camera.core;

import androidx.camera.core.CameraState;

/* compiled from: AutoValue_CameraState.java */
/* renamed from: androidx.camera.core.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20057f extends CameraState {

    /* renamed from: a, reason: collision with root package name */
    public final CameraState.Type f23740a;

    /* renamed from: b, reason: collision with root package name */
    public final CameraState.a f23741b;

    public C20057f(CameraState.Type type, @j.P CameraState.a aVar) {
        this.f23740a = type;
        this.f23741b = aVar;
    }

    @Override // androidx.camera.core.CameraState
    @j.P
    public final CameraState.a c() {
        return this.f23741b;
    }

    @Override // androidx.camera.core.CameraState
    @j.N
    public final CameraState.Type d() {
        return this.f23740a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CameraState)) {
            return false;
        }
        CameraState cameraState = (CameraState) obj;
        if (this.f23740a.equals(cameraState.d())) {
            CameraState.a aVar = this.f23741b;
            if (aVar == null) {
                if (cameraState.c() == null) {
                    return true;
                }
            } else if (aVar.equals(cameraState.c())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (this.f23740a.hashCode() ^ 1000003) * 1000003;
        CameraState.a aVar = this.f23741b;
        return iHashCode ^ (aVar == null ? 0 : aVar.hashCode());
    }

    public final String toString() {
        return "CameraState{type=" + this.f23740a + ", error=" + this.f23741b + "}";
    }
}
