package androidx.camera.video;

import androidx.camera.video.C20181s;

/* compiled from: AutoValue_FallbackStrategy_RuleStrategy.java */
/* renamed from: androidx.camera.video.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20154e extends C20181s.b {

    /* renamed from: b, reason: collision with root package name */
    public final A f24902b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24903c;

    public C20154e(A a12, int i12) {
        if (a12 == null) {
            throw new NullPointerException("Null fallbackQuality");
        }
        this.f24902b = a12;
        this.f24903c = i12;
    }

    @Override // androidx.camera.video.C20181s.b
    @j.N
    public final A b() {
        return this.f24902b;
    }

    @Override // androidx.camera.video.C20181s.b
    public final int c() {
        return this.f24903c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20181s.b)) {
            return false;
        }
        C20181s.b bVar = (C20181s.b) obj;
        return this.f24902b.equals(bVar.b()) && this.f24903c == bVar.c();
    }

    public final int hashCode() {
        return ((this.f24902b.hashCode() ^ 1000003) * 1000003) ^ this.f24903c;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("RuleStrategy{fallbackQuality=");
        sb2.append(this.f24902b);
        sb2.append(", fallbackRule=");
        return AK.c.i(this.f24903c, "}", sb2);
    }
}
