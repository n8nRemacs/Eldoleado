package androidx.camera.video.internal;

import androidx.camera.core.impl.S;
import j.N;
import j.P;
import java.util.List;

/* compiled from: AutoValue_VideoValidatedEncoderProfilesProxy.java */
/* loaded from: classes.dex */
final class a extends f {

    /* renamed from: a, reason: collision with root package name */
    public final int f24945a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24946b;

    /* renamed from: c, reason: collision with root package name */
    public final List<S.a> f24947c;

    /* renamed from: d, reason: collision with root package name */
    public final List<S.c> f24948d;

    /* renamed from: e, reason: collision with root package name */
    public final S.a f24949e;

    /* renamed from: f, reason: collision with root package name */
    public final S.c f24950f;

    public a(int i12, int i13, List<S.a> list, List<S.c> list2, @P S.a aVar, S.c cVar) {
        this.f24945a = i12;
        this.f24946b = i13;
        if (list == null) {
            throw new NullPointerException("Null audioProfiles");
        }
        this.f24947c = list;
        if (list2 == null) {
            throw new NullPointerException("Null videoProfiles");
        }
        this.f24948d = list2;
        this.f24949e = aVar;
        if (cVar == null) {
            throw new NullPointerException("Null defaultVideoProfile");
        }
        this.f24950f = cVar;
    }

    @Override // androidx.camera.core.impl.S
    public final int a() {
        return this.f24946b;
    }

    @Override // androidx.camera.core.impl.S
    @N
    public final List<S.a> b() {
        return this.f24947c;
    }

    @Override // androidx.camera.core.impl.S
    public final int c() {
        return this.f24945a;
    }

    @Override // androidx.camera.core.impl.S
    @N
    public final List<S.c> d() {
        return this.f24948d;
    }

    public final boolean equals(Object obj) {
        S.a aVar;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        a aVar2 = (a) fVar;
        if (this.f24945a == aVar2.f24945a) {
            if (this.f24946b == aVar2.f24946b && this.f24947c.equals(aVar2.f24947c) && this.f24948d.equals(aVar2.f24948d) && ((aVar = this.f24949e) != null ? aVar.equals(fVar.f()) : fVar.f() == null) && this.f24950f.equals(fVar.g())) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.camera.video.internal.f
    @P
    public final S.a f() {
        return this.f24949e;
    }

    @Override // androidx.camera.video.internal.f
    @N
    public final S.c g() {
        return this.f24950f;
    }

    public final int hashCode() {
        int iHashCode = (((((((this.f24945a ^ 1000003) * 1000003) ^ this.f24946b) * 1000003) ^ this.f24947c.hashCode()) * 1000003) ^ this.f24948d.hashCode()) * 1000003;
        S.a aVar = this.f24949e;
        return ((iHashCode ^ (aVar == null ? 0 : aVar.hashCode())) * 1000003) ^ this.f24950f.hashCode();
    }

    public final String toString() {
        return "VideoValidatedEncoderProfilesProxy{defaultDurationSeconds=" + this.f24945a + ", recommendedFileFormat=" + this.f24946b + ", audioProfiles=" + this.f24947c + ", videoProfiles=" + this.f24948d + ", defaultAudioProfile=" + this.f24949e + ", defaultVideoProfile=" + this.f24950f + "}";
    }
}
