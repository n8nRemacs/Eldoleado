package androidx.camera.core;

import android.view.Surface;
import androidx.camera.core.K0;

/* compiled from: AutoValue_SurfaceRequest_Result.java */
/* renamed from: androidx.camera.core.l, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20123l extends K0.c {

    /* renamed from: a, reason: collision with root package name */
    public final int f24381a;

    /* renamed from: b, reason: collision with root package name */
    public final Surface f24382b;

    public C20123l(int i12, Surface surface) {
        this.f24381a = i12;
        if (surface == null) {
            throw new NullPointerException("Null surface");
        }
        this.f24382b = surface;
    }

    @Override // androidx.camera.core.K0.c
    public final int a() {
        return this.f24381a;
    }

    @Override // androidx.camera.core.K0.c
    @j.N
    public final Surface b() {
        return this.f24382b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof K0.c)) {
            return false;
        }
        K0.c cVar = (K0.c) obj;
        return this.f24381a == cVar.a() && this.f24382b.equals(cVar.b());
    }

    public final int hashCode() {
        return this.f24382b.hashCode() ^ ((this.f24381a ^ 1000003) * 1000003);
    }

    public final String toString() {
        return "Result{resultCode=" + this.f24381a + ", surface=" + this.f24382b + "}";
    }
}
