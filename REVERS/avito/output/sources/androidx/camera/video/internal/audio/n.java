package androidx.camera.video.internal.audio;

import androidx.camera.video.internal.audio.a;
import j.F;

/* compiled from: AutoValue_AudioSettings.java */
/* loaded from: classes.dex */
final class n extends androidx.camera.video.internal.audio.a {

    /* renamed from: b, reason: collision with root package name */
    public final int f25012b;

    /* renamed from: c, reason: collision with root package name */
    public final int f25013c;

    /* renamed from: d, reason: collision with root package name */
    public final int f25014d;

    /* renamed from: e, reason: collision with root package name */
    public final int f25015e;

    /* compiled from: AutoValue_AudioSettings.java */
    public static final class b extends a.AbstractC1538a {

        /* renamed from: a, reason: collision with root package name */
        public Integer f25016a;

        /* renamed from: b, reason: collision with root package name */
        public Integer f25017b;

        /* renamed from: c, reason: collision with root package name */
        public Integer f25018c;

        /* renamed from: d, reason: collision with root package name */
        public Integer f25019d;

        public b() {
        }

        @Override // androidx.camera.video.internal.audio.a.AbstractC1538a
        public final a.AbstractC1538a b(int i12) {
            this.f25019d = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.audio.a.AbstractC1538a
        public final a.AbstractC1538a c(int i12) {
            this.f25016a = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.audio.a.AbstractC1538a
        public final a.AbstractC1538a d(int i12) {
            this.f25018c = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.audio.a.AbstractC1538a
        public final a.AbstractC1538a e(int i12) {
            this.f25017b = Integer.valueOf(i12);
            return this;
        }

        public b(androidx.camera.video.internal.audio.a aVar, a aVar2) {
            this.f25016a = Integer.valueOf(aVar.c());
            this.f25017b = Integer.valueOf(aVar.f());
            this.f25018c = Integer.valueOf(aVar.e());
            this.f25019d = Integer.valueOf(aVar.b());
        }
    }

    public n(int i12, int i13, int i14, int i15, a aVar) {
        this.f25012b = i12;
        this.f25013c = i13;
        this.f25014d = i14;
        this.f25015e = i15;
    }

    @Override // androidx.camera.video.internal.audio.a
    public final int b() {
        return this.f25015e;
    }

    @Override // androidx.camera.video.internal.audio.a
    public final int c() {
        return this.f25012b;
    }

    @Override // androidx.camera.video.internal.audio.a
    @F
    public final int e() {
        return this.f25014d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof androidx.camera.video.internal.audio.a)) {
            return false;
        }
        androidx.camera.video.internal.audio.a aVar = (androidx.camera.video.internal.audio.a) obj;
        return this.f25012b == aVar.c() && this.f25013c == aVar.f() && this.f25014d == aVar.e() && this.f25015e == aVar.b();
    }

    @Override // androidx.camera.video.internal.audio.a
    @F
    public final int f() {
        return this.f25013c;
    }

    public final int hashCode() {
        return ((((((this.f25012b ^ 1000003) * 1000003) ^ this.f25013c) * 1000003) ^ this.f25014d) * 1000003) ^ this.f25015e;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AudioSettings{audioSource=");
        sb2.append(this.f25012b);
        sb2.append(", sampleRate=");
        sb2.append(this.f25013c);
        sb2.append(", channelCount=");
        sb2.append(this.f25014d);
        sb2.append(", audioFormat=");
        return AK.c.i(this.f25015e, "}", sb2);
    }
}
