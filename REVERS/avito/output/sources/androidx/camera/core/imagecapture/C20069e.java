package androidx.camera.core.imagecapture;

import androidx.camera.core.C20056e0;
import androidx.camera.core.imagecapture.t;

/* compiled from: AutoValue_JpegBytes2Disk_In.java */
/* renamed from: androidx.camera.core.imagecapture.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20069e extends t.a {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.processing.r<byte[]> f23823a;

    /* renamed from: b, reason: collision with root package name */
    public final C20056e0.l f23824b;

    public C20069e(androidx.camera.core.processing.r<byte[]> rVar, C20056e0.l lVar) {
        if (rVar == null) {
            throw new NullPointerException("Null packet");
        }
        this.f23823a = rVar;
        if (lVar == null) {
            throw new NullPointerException("Null outputFileOptions");
        }
        this.f23824b = lVar;
    }

    @Override // androidx.camera.core.imagecapture.t.a
    @j.N
    public final C20056e0.l a() {
        return this.f23824b;
    }

    @Override // androidx.camera.core.imagecapture.t.a
    @j.N
    public final androidx.camera.core.processing.r<byte[]> b() {
        return this.f23823a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof t.a)) {
            return false;
        }
        t.a aVar = (t.a) obj;
        return this.f23823a.equals(aVar.b()) && this.f23824b.equals(aVar.a());
    }

    public final int hashCode() {
        return ((this.f23823a.hashCode() ^ 1000003) * 1000003) ^ this.f23824b.hashCode();
    }

    public final String toString() {
        return "In{packet=" + this.f23823a + ", outputFileOptions=" + this.f23824b + "}";
    }
}
