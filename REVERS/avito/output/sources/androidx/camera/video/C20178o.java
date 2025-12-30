package androidx.camera.video;

import androidx.camera.core.K0;
import androidx.camera.video.W;

/* compiled from: AutoValue_StreamInfo.java */
/* renamed from: androidx.camera.video.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20178o extends W {

    /* renamed from: d, reason: collision with root package name */
    public final int f25323d;

    /* renamed from: e, reason: collision with root package name */
    public final W.a f25324e;

    /* renamed from: f, reason: collision with root package name */
    public final K0.d f25325f;

    public C20178o(int i12, W.a aVar, @j.P K0.d dVar) {
        this.f25323d = i12;
        this.f25324e = aVar;
        this.f25325f = dVar;
    }

    @Override // androidx.camera.video.W
    public final int a() {
        return this.f25323d;
    }

    @Override // androidx.camera.video.W
    @j.P
    public final K0.d b() {
        return this.f25325f;
    }

    @Override // androidx.camera.video.W
    @j.N
    public final W.a c() {
        return this.f25324e;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof W)) {
            return false;
        }
        W w12 = (W) obj;
        if (this.f25323d == w12.a() && this.f25324e.equals(w12.c())) {
            K0.d dVar = this.f25325f;
            if (dVar == null) {
                if (w12.b() == null) {
                    return true;
                }
            } else if (dVar.equals(w12.b())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (((this.f25323d ^ 1000003) * 1000003) ^ this.f25324e.hashCode()) * 1000003;
        K0.d dVar = this.f25325f;
        return iHashCode ^ (dVar == null ? 0 : dVar.hashCode());
    }

    public final String toString() {
        return "StreamInfo{id=" + this.f25323d + ", streamState=" + this.f25324e + ", inProgressTransformationInfo=" + this.f25325f + "}";
    }
}
