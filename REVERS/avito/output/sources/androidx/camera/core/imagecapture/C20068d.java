package androidx.camera.core.imagecapture;

import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.imagecapture.C20080p;

/* compiled from: AutoValue_Image2JpegBytes_In.java */
/* renamed from: androidx.camera.core.imagecapture.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20068d extends C20080p.a {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.processing.r<InterfaceC20120j0> f23821a;

    /* renamed from: b, reason: collision with root package name */
    public final int f23822b;

    public C20068d(androidx.camera.core.processing.r<InterfaceC20120j0> rVar, int i12) {
        if (rVar == null) {
            throw new NullPointerException("Null packet");
        }
        this.f23821a = rVar;
        this.f23822b = i12;
    }

    @Override // androidx.camera.core.imagecapture.C20080p.a
    public final int a() {
        return this.f23822b;
    }

    @Override // androidx.camera.core.imagecapture.C20080p.a
    public final androidx.camera.core.processing.r<InterfaceC20120j0> b() {
        return this.f23821a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20080p.a)) {
            return false;
        }
        C20080p.a aVar = (C20080p.a) obj;
        return this.f23821a.equals(aVar.b()) && this.f23822b == aVar.a();
    }

    public final int hashCode() {
        return ((this.f23821a.hashCode() ^ 1000003) * 1000003) ^ this.f23822b;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("In{packet=");
        sb2.append(this.f23821a);
        sb2.append(", jpegQuality=");
        return AK.c.i(this.f23822b, "}", sb2);
    }
}
