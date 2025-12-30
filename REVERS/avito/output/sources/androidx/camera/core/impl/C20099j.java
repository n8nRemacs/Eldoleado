package androidx.camera.core.impl;

import android.util.Size;
import android.view.Surface;

/* compiled from: AutoValue_OutputSurface.java */
/* renamed from: androidx.camera.core.impl.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20099j extends t0 {

    /* renamed from: a, reason: collision with root package name */
    public final Surface f24129a;

    /* renamed from: b, reason: collision with root package name */
    public final Size f24130b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24131c;

    public C20099j(Surface surface, Size size, int i12) {
        if (surface == null) {
            throw new NullPointerException("Null surface");
        }
        this.f24129a = surface;
        this.f24130b = size;
        this.f24131c = i12;
    }

    @Override // androidx.camera.core.impl.t0
    public final int b() {
        return this.f24131c;
    }

    @Override // androidx.camera.core.impl.t0
    @j.N
    public final Size c() {
        return this.f24130b;
    }

    @Override // androidx.camera.core.impl.t0
    @j.N
    public final Surface d() {
        return this.f24129a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof t0)) {
            return false;
        }
        t0 t0Var = (t0) obj;
        return this.f24129a.equals(t0Var.d()) && this.f24130b.equals(t0Var.c()) && this.f24131c == t0Var.b();
    }

    public final int hashCode() {
        return ((((this.f24129a.hashCode() ^ 1000003) * 1000003) ^ this.f24130b.hashCode()) * 1000003) ^ this.f24131c;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("OutputSurface{surface=");
        sb2.append(this.f24129a);
        sb2.append(", size=");
        sb2.append(this.f24130b);
        sb2.append(", imageFormat=");
        return AK.c.i(this.f24131c, "}", sb2);
    }
}
