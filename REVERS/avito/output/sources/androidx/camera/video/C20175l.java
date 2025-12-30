package androidx.camera.video;

import androidx.camera.video.A;

/* compiled from: AutoValue_Quality_ConstantQuality.java */
/* renamed from: androidx.camera.video.l, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20175l extends A.b {

    /* renamed from: j, reason: collision with root package name */
    public final int f25288j;

    /* renamed from: k, reason: collision with root package name */
    public final String f25289k;

    public C20175l(int i12, String str) {
        this.f25288j = i12;
        this.f25289k = str;
    }

    @Override // androidx.camera.video.A.b
    @j.N
    public final String a() {
        return this.f25289k;
    }

    @Override // androidx.camera.video.A.b
    public final int b() {
        return this.f25288j;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof A.b)) {
            return false;
        }
        A.b bVar = (A.b) obj;
        return this.f25288j == bVar.b() && this.f25289k.equals(bVar.a());
    }

    public final int hashCode() {
        return ((this.f25288j ^ 1000003) * 1000003) ^ this.f25289k.hashCode();
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ConstantQuality{value=");
        sb2.append(this.f25288j);
        sb2.append(", name=");
        return AK.c.s(sb2, this.f25289k, "}");
    }
}
