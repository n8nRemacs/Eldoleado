package androidx.camera.core.imagecapture;

import androidx.camera.core.imagecapture.A;

/* compiled from: AutoValue_ProcessingNode_In.java */
/* renamed from: androidx.camera.core.imagecapture.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20070f extends A.a {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.processing.l<A.b> f23825a;

    /* renamed from: b, reason: collision with root package name */
    public final int f23826b;

    /* renamed from: c, reason: collision with root package name */
    public final int f23827c;

    public C20070f(androidx.camera.core.processing.l<A.b> lVar, int i12, int i13) {
        this.f23825a = lVar;
        this.f23826b = i12;
        this.f23827c = i13;
    }

    @Override // androidx.camera.core.imagecapture.A.a
    public final androidx.camera.core.processing.l<A.b> a() {
        return this.f23825a;
    }

    @Override // androidx.camera.core.imagecapture.A.a
    public final int b() {
        return this.f23826b;
    }

    @Override // androidx.camera.core.imagecapture.A.a
    public final int c() {
        return this.f23827c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof A.a)) {
            return false;
        }
        A.a aVar = (A.a) obj;
        return this.f23825a.equals(aVar.a()) && this.f23826b == aVar.b() && this.f23827c == aVar.c();
    }

    public final int hashCode() {
        return ((((this.f23825a.hashCode() ^ 1000003) * 1000003) ^ this.f23826b) * 1000003) ^ this.f23827c;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("In{edge=");
        sb2.append(this.f23825a);
        sb2.append(", inputFormat=");
        sb2.append(this.f23826b);
        sb2.append(", outputFormat=");
        return AK.c.i(this.f23827c, "}", sb2);
    }
}
