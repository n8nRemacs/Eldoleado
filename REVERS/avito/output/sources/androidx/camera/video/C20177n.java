package androidx.camera.video;

/* compiled from: AutoValue_RecordingStats.java */
/* renamed from: androidx.camera.video.n, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20177n extends V {

    /* renamed from: a, reason: collision with root package name */
    public final long f25317a;

    /* renamed from: b, reason: collision with root package name */
    public final long f25318b;

    /* renamed from: c, reason: collision with root package name */
    public final AbstractC20151b f25319c;

    public C20177n(long j12, long j13, AbstractC20151b abstractC20151b) {
        this.f25317a = j12;
        this.f25318b = j13;
        this.f25319c = abstractC20151b;
    }

    @Override // androidx.camera.video.V
    @j.N
    public final AbstractC20151b a() {
        return this.f25319c;
    }

    @Override // androidx.camera.video.V
    public final long b() {
        return this.f25318b;
    }

    @Override // androidx.camera.video.V
    public final long c() {
        return this.f25317a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof V)) {
            return false;
        }
        V v12 = (V) obj;
        return this.f25317a == v12.c() && this.f25318b == v12.b() && this.f25319c.equals(v12.a());
    }

    public final int hashCode() {
        long j12 = this.f25317a;
        int i12 = (((int) (j12 ^ (j12 >>> 32))) ^ 1000003) * 1000003;
        long j13 = this.f25318b;
        return ((i12 ^ ((int) ((j13 >>> 32) ^ j13))) * 1000003) ^ this.f25319c.hashCode();
    }

    public final String toString() {
        return "RecordingStats{recordedDurationNanos=" + this.f25317a + ", numBytesRecorded=" + this.f25318b + ", audioStats=" + this.f25319c + "}";
    }
}
