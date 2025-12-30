package androidx.camera.video.internal.config;

import androidx.camera.core.impl.S;
import androidx.camera.video.internal.config.e;
import j.N;
import j.P;

/* compiled from: AutoValue_AudioMimeInfo.java */
/* loaded from: classes.dex */
final class h extends e {

    /* renamed from: a, reason: collision with root package name */
    public final String f25076a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25077b;

    /* renamed from: c, reason: collision with root package name */
    public final S.a f25078c;

    /* compiled from: AutoValue_AudioMimeInfo.java */
    public static final class b extends e.a {

        /* renamed from: a, reason: collision with root package name */
        public String f25079a;

        /* renamed from: b, reason: collision with root package name */
        public Integer f25080b;

        /* renamed from: c, reason: collision with root package name */
        public S.a f25081c;
    }

    public h(String str, int i12, S.a aVar, a aVar2) {
        this.f25076a = str;
        this.f25077b = i12;
        this.f25078c = aVar;
    }

    @Override // androidx.camera.video.internal.config.j
    @N
    public final String a() {
        return this.f25076a;
    }

    @Override // androidx.camera.video.internal.config.j
    public final int b() {
        return this.f25077b;
    }

    @Override // androidx.camera.video.internal.config.e
    @P
    public final S.a c() {
        return this.f25078c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        if (this.f25076a.equals(eVar.a()) && this.f25077b == eVar.b()) {
            S.a aVar = this.f25078c;
            if (aVar == null) {
                if (eVar.c() == null) {
                    return true;
                }
            } else if (aVar.equals(eVar.c())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (((this.f25076a.hashCode() ^ 1000003) * 1000003) ^ this.f25077b) * 1000003;
        S.a aVar = this.f25078c;
        return iHashCode ^ (aVar == null ? 0 : aVar.hashCode());
    }

    public final String toString() {
        return "AudioMimeInfo{mimeType=" + this.f25076a + ", profile=" + this.f25077b + ", compatibleAudioProfile=" + this.f25078c + "}";
    }
}
