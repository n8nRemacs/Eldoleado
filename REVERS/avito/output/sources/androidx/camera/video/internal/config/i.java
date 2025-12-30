package androidx.camera.video.internal.config;

import androidx.camera.core.impl.S;
import androidx.camera.video.internal.config.n;
import j.N;
import j.P;

/* compiled from: AutoValue_VideoMimeInfo.java */
/* loaded from: classes.dex */
final class i extends n {

    /* renamed from: a, reason: collision with root package name */
    public final String f25082a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25083b;

    /* renamed from: c, reason: collision with root package name */
    public final S.c f25084c;

    /* compiled from: AutoValue_VideoMimeInfo.java */
    public static final class b extends n.a {

        /* renamed from: a, reason: collision with root package name */
        public String f25085a;

        /* renamed from: b, reason: collision with root package name */
        public Integer f25086b;

        /* renamed from: c, reason: collision with root package name */
        public S.c f25087c;
    }

    public i(String str, int i12, S.c cVar, a aVar) {
        this.f25082a = str;
        this.f25083b = i12;
        this.f25084c = cVar;
    }

    @Override // androidx.camera.video.internal.config.j
    @N
    public final String a() {
        return this.f25082a;
    }

    @Override // androidx.camera.video.internal.config.j
    public final int b() {
        return this.f25083b;
    }

    @Override // androidx.camera.video.internal.config.n
    @P
    public final S.c c() {
        return this.f25084c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof n)) {
            return false;
        }
        n nVar = (n) obj;
        if (this.f25082a.equals(nVar.a()) && this.f25083b == nVar.b()) {
            S.c cVar = this.f25084c;
            if (cVar == null) {
                if (nVar.c() == null) {
                    return true;
                }
            } else if (cVar.equals(nVar.c())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (((this.f25082a.hashCode() ^ 1000003) * 1000003) ^ this.f25083b) * 1000003;
        S.c cVar = this.f25084c;
        return iHashCode ^ (cVar == null ? 0 : cVar.hashCode());
    }

    public final String toString() {
        return "VideoMimeInfo{mimeType=" + this.f25082a + ", profile=" + this.f25083b + ", compatibleVideoProfile=" + this.f25084c + "}";
    }
}
