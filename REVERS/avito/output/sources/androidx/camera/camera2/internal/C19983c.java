package androidx.camera.camera2.internal;

import android.util.Size;
import androidx.camera.camera2.internal.C20039w;
import androidx.camera.core.impl.SessionConfig;

/* compiled from: AutoValue_Camera2CameraImpl_UseCaseInfo.java */
/* renamed from: androidx.camera.camera2.internal.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C19983c extends C20039w.h {

    /* renamed from: a, reason: collision with root package name */
    public final String f23112a;

    /* renamed from: b, reason: collision with root package name */
    public final Class<?> f23113b;

    /* renamed from: c, reason: collision with root package name */
    public final SessionConfig f23114c;

    /* renamed from: d, reason: collision with root package name */
    public final androidx.camera.core.impl.L0<?> f23115d;

    /* renamed from: e, reason: collision with root package name */
    public final Size f23116e;

    public C19983c(String str, Class<?> cls, SessionConfig sessionConfig, androidx.camera.core.impl.L0<?> l02, @j.P Size size) {
        if (str == null) {
            throw new NullPointerException("Null useCaseId");
        }
        this.f23112a = str;
        this.f23113b = cls;
        if (sessionConfig == null) {
            throw new NullPointerException("Null sessionConfig");
        }
        this.f23114c = sessionConfig;
        if (l02 == null) {
            throw new NullPointerException("Null useCaseConfig");
        }
        this.f23115d = l02;
        this.f23116e = size;
    }

    @Override // androidx.camera.camera2.internal.C20039w.h
    @j.N
    public final SessionConfig a() {
        return this.f23114c;
    }

    @Override // androidx.camera.camera2.internal.C20039w.h
    @j.P
    public final Size b() {
        return this.f23116e;
    }

    @Override // androidx.camera.camera2.internal.C20039w.h
    @j.N
    public final androidx.camera.core.impl.L0<?> c() {
        return this.f23115d;
    }

    @Override // androidx.camera.camera2.internal.C20039w.h
    @j.N
    public final String d() {
        return this.f23112a;
    }

    @Override // androidx.camera.camera2.internal.C20039w.h
    @j.N
    public final Class<?> e() {
        return this.f23113b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20039w.h)) {
            return false;
        }
        C20039w.h hVar = (C20039w.h) obj;
        if (this.f23112a.equals(hVar.d()) && this.f23113b.equals(hVar.e()) && this.f23114c.equals(hVar.a()) && this.f23115d.equals(hVar.c())) {
            Size size = this.f23116e;
            if (size == null) {
                if (hVar.b() == null) {
                    return true;
                }
            } else if (size.equals(hVar.b())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (((((((this.f23112a.hashCode() ^ 1000003) * 1000003) ^ this.f23113b.hashCode()) * 1000003) ^ this.f23114c.hashCode()) * 1000003) ^ this.f23115d.hashCode()) * 1000003;
        Size size = this.f23116e;
        return iHashCode ^ (size == null ? 0 : size.hashCode());
    }

    public final String toString() {
        return "UseCaseInfo{useCaseId=" + this.f23112a + ", useCaseType=" + this.f23113b + ", sessionConfig=" + this.f23114c + ", useCaseConfig=" + this.f23115d + ", surfaceResolution=" + this.f23116e + "}";
    }
}
