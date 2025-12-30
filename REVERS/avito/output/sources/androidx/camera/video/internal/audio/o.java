package androidx.camera.video.internal.audio;

import androidx.camera.video.internal.audio.AudioStream;

/* compiled from: AutoValue_AudioStream_PacketInfo.java */
/* loaded from: classes.dex */
final class o extends AudioStream.b {

    /* renamed from: a, reason: collision with root package name */
    public final int f25020a;

    /* renamed from: b, reason: collision with root package name */
    public final long f25021b;

    public o(int i12, long j12) {
        this.f25020a = i12;
        this.f25021b = j12;
    }

    @Override // androidx.camera.video.internal.audio.AudioStream.b
    public final int a() {
        return this.f25020a;
    }

    @Override // androidx.camera.video.internal.audio.AudioStream.b
    public final long b() {
        return this.f25021b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AudioStream.b)) {
            return false;
        }
        AudioStream.b bVar = (AudioStream.b) obj;
        return this.f25020a == bVar.a() && this.f25021b == bVar.b();
    }

    public final int hashCode() {
        int i12 = (this.f25020a ^ 1000003) * 1000003;
        long j12 = this.f25021b;
        return i12 ^ ((int) ((j12 >>> 32) ^ j12));
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("PacketInfo{sizeInBytes=");
        sb2.append(this.f25020a);
        sb2.append(", timestampNs=");
        return AK.c.j(this.f25021b, "}", sb2);
    }
}
