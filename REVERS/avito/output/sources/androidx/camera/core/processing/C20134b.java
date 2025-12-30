package androidx.camera.core.processing;

import android.opengl.EGLSurface;
import androidx.camera.core.processing.p;
import j.N;

/* compiled from: AutoValue_OpenGlRenderer_OutputSurface.java */
/* renamed from: androidx.camera.core.processing.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20134b extends p.a {

    /* renamed from: a, reason: collision with root package name */
    public final EGLSurface f24411a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24412b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24413c;

    public C20134b(EGLSurface eGLSurface, int i12, int i13) {
        if (eGLSurface == null) {
            throw new NullPointerException("Null eglSurface");
        }
        this.f24411a = eGLSurface;
        this.f24412b = i12;
        this.f24413c = i13;
    }

    @Override // androidx.camera.core.processing.p.a
    @N
    public final EGLSurface a() {
        return this.f24411a;
    }

    @Override // androidx.camera.core.processing.p.a
    public final int b() {
        return this.f24413c;
    }

    @Override // androidx.camera.core.processing.p.a
    public final int c() {
        return this.f24412b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof p.a)) {
            return false;
        }
        p.a aVar = (p.a) obj;
        return this.f24411a.equals(aVar.a()) && this.f24412b == aVar.c() && this.f24413c == aVar.b();
    }

    public final int hashCode() {
        return ((((this.f24411a.hashCode() ^ 1000003) * 1000003) ^ this.f24412b) * 1000003) ^ this.f24413c;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("OutputSurface{eglSurface=");
        sb2.append(this.f24411a);
        sb2.append(", width=");
        sb2.append(this.f24412b);
        sb2.append(", height=");
        return AK.c.i(this.f24413c, "}", sb2);
    }
}
