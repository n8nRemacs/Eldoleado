package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import androidx.camera.core.imagecapture.C20073i;

/* compiled from: AutoValue_Bitmap2JpegBytes_In.java */
/* renamed from: androidx.camera.core.imagecapture.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20065a extends C20073i.a {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.processing.r<Bitmap> f23808a;

    /* renamed from: b, reason: collision with root package name */
    public final int f23809b;

    public C20065a(androidx.camera.core.processing.r<Bitmap> rVar, int i12) {
        if (rVar == null) {
            throw new NullPointerException("Null packet");
        }
        this.f23808a = rVar;
        this.f23809b = i12;
    }

    @Override // androidx.camera.core.imagecapture.C20073i.a
    public final int a() {
        return this.f23809b;
    }

    @Override // androidx.camera.core.imagecapture.C20073i.a
    public final androidx.camera.core.processing.r<Bitmap> b() {
        return this.f23808a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20073i.a)) {
            return false;
        }
        C20073i.a aVar = (C20073i.a) obj;
        return this.f23808a.equals(aVar.b()) && this.f23809b == aVar.a();
    }

    public final int hashCode() {
        return ((this.f23808a.hashCode() ^ 1000003) * 1000003) ^ this.f23809b;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("In{packet=");
        sb2.append(this.f23808a);
        sb2.append(", jpegQuality=");
        return AK.c.i(this.f23809b, "}", sb2);
    }
}
