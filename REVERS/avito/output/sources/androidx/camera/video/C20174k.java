package androidx.camera.video;

import androidx.camera.video.C;

/* compiled from: AutoValue_QualityRatioToResolutionsTable_QualityRatio.java */
/* renamed from: androidx.camera.video.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20174k extends C.a {

    /* renamed from: a, reason: collision with root package name */
    public final A f25285a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25286b;

    public C20174k(A a12, int i12) {
        if (a12 == null) {
            throw new NullPointerException("Null quality");
        }
        this.f25285a = a12;
        this.f25286b = i12;
    }

    @Override // androidx.camera.video.C.a
    public final int a() {
        return this.f25286b;
    }

    @Override // androidx.camera.video.C.a
    @j.N
    public final A b() {
        return this.f25285a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C.a)) {
            return false;
        }
        C.a aVar = (C.a) obj;
        return this.f25285a.equals(aVar.b()) && this.f25286b == aVar.a();
    }

    public final int hashCode() {
        return ((this.f25285a.hashCode() ^ 1000003) * 1000003) ^ this.f25286b;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("QualityRatio{quality=");
        sb2.append(this.f25285a);
        sb2.append(", aspectRatio=");
        return AK.c.i(this.f25286b, "}", sb2);
    }
}
