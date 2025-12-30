package androidx.camera.video.internal.encoder;

/* compiled from: AutoValue_VideoEncoderDataSpace.java */
/* renamed from: androidx.camera.video.internal.encoder.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20164f extends K {

    /* renamed from: e, reason: collision with root package name */
    public final int f25158e;

    /* renamed from: f, reason: collision with root package name */
    public final int f25159f;

    /* renamed from: g, reason: collision with root package name */
    public final int f25160g;

    public C20164f(int i12, int i13, int i14) {
        this.f25158e = i12;
        this.f25159f = i13;
        this.f25160g = i14;
    }

    @Override // androidx.camera.video.internal.encoder.K
    public final int a() {
        return this.f25160g;
    }

    @Override // androidx.camera.video.internal.encoder.K
    public final int b() {
        return this.f25158e;
    }

    @Override // androidx.camera.video.internal.encoder.K
    public final int c() {
        return this.f25159f;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof K)) {
            return false;
        }
        K k12 = (K) obj;
        return this.f25158e == k12.b() && this.f25159f == k12.c() && this.f25160g == k12.a();
    }

    public final int hashCode() {
        return this.f25160g ^ ((((this.f25158e ^ 1000003) * 1000003) ^ this.f25159f) * 1000003);
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("VideoEncoderDataSpace{standard=");
        sb2.append(this.f25158e);
        sb2.append(", transfer=");
        sb2.append(this.f25159f);
        sb2.append(", range=");
        return AK.c.i(this.f25160g, "}", sb2);
    }
}
