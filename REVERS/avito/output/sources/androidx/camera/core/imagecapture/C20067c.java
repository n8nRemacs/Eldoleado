package androidx.camera.core.imagecapture;

import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.imagecapture.C20079o;

/* compiled from: AutoValue_CaptureNode_Out.java */
/* renamed from: androidx.camera.core.imagecapture.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20067c extends C20079o.c {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.processing.l<InterfaceC20120j0> f23817a;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.core.processing.l<B> f23818b;

    /* renamed from: c, reason: collision with root package name */
    public final int f23819c;

    /* renamed from: d, reason: collision with root package name */
    public final int f23820d;

    public C20067c(androidx.camera.core.processing.l<InterfaceC20120j0> lVar, androidx.camera.core.processing.l<B> lVar2, int i12, int i13) {
        this.f23817a = lVar;
        this.f23818b = lVar2;
        this.f23819c = i12;
        this.f23820d = i13;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.c
    public final androidx.camera.core.processing.l<InterfaceC20120j0> a() {
        return this.f23817a;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.c
    public final int b() {
        return this.f23819c;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.c
    public final int c() {
        return this.f23820d;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.c
    public final androidx.camera.core.processing.l<B> d() {
        return this.f23818b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20079o.c)) {
            return false;
        }
        C20079o.c cVar = (C20079o.c) obj;
        return this.f23817a.equals(cVar.a()) && this.f23818b.equals(cVar.d()) && this.f23819c == cVar.b() && this.f23820d == cVar.c();
    }

    public final int hashCode() {
        return ((((((this.f23817a.hashCode() ^ 1000003) * 1000003) ^ this.f23818b.hashCode()) * 1000003) ^ this.f23819c) * 1000003) ^ this.f23820d;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("Out{imageEdge=");
        sb2.append(this.f23817a);
        sb2.append(", requestEdge=");
        sb2.append(this.f23818b);
        sb2.append(", inputFormat=");
        sb2.append(this.f23819c);
        sb2.append(", outputFormat=");
        return AK.c.i(this.f23820d, "}", sb2);
    }
}
