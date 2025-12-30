package androidx.camera.core.impl;

import androidx.camera.core.impl.SessionConfig;
import java.util.List;

/* compiled from: AutoValue_SessionConfig_OutputConfig.java */
/* renamed from: androidx.camera.core.impl.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20101k extends SessionConfig.e {

    /* renamed from: a, reason: collision with root package name */
    public final DeferrableSurface f24136a;

    /* renamed from: b, reason: collision with root package name */
    public final List<DeferrableSurface> f24137b;

    /* renamed from: c, reason: collision with root package name */
    public final String f24138c;

    /* renamed from: d, reason: collision with root package name */
    public final int f24139d;

    /* renamed from: e, reason: collision with root package name */
    public final androidx.camera.core.F f24140e;

    /* compiled from: AutoValue_SessionConfig_OutputConfig.java */
    /* renamed from: androidx.camera.core.impl.k$b */
    public static final class b extends SessionConfig.e.a {

        /* renamed from: a, reason: collision with root package name */
        public DeferrableSurface f24141a;

        /* renamed from: b, reason: collision with root package name */
        public List<DeferrableSurface> f24142b;

        /* renamed from: c, reason: collision with root package name */
        public Integer f24143c;

        /* renamed from: d, reason: collision with root package name */
        public androidx.camera.core.F f24144d;

        @Override // androidx.camera.core.impl.SessionConfig.e.a
        public final SessionConfig.e a() {
            String strQ = this.f24141a == null ? " surface" : "";
            if (this.f24142b == null) {
                strQ = strQ.concat(" sharedSurfaces");
            }
            if (this.f24143c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " surfaceGroupId");
            }
            if (this.f24144d == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " dynamicRange");
            }
            if (strQ.isEmpty()) {
                return new C20101k(this.f24141a, this.f24142b, null, this.f24143c.intValue(), this.f24144d, null);
            }
            throw new IllegalStateException("Missing required properties:".concat(strQ));
        }
    }

    public C20101k() {
        throw null;
    }

    public C20101k(DeferrableSurface deferrableSurface, List list, String str, int i12, androidx.camera.core.F f12, a aVar) {
        this.f24136a = deferrableSurface;
        this.f24137b = list;
        this.f24138c = str;
        this.f24139d = i12;
        this.f24140e = f12;
    }

    @Override // androidx.camera.core.impl.SessionConfig.e
    @j.N
    public final androidx.camera.core.F b() {
        return this.f24140e;
    }

    @Override // androidx.camera.core.impl.SessionConfig.e
    @j.P
    public final String c() {
        return this.f24138c;
    }

    @Override // androidx.camera.core.impl.SessionConfig.e
    @j.N
    public final List<DeferrableSurface> d() {
        return this.f24137b;
    }

    @Override // androidx.camera.core.impl.SessionConfig.e
    @j.N
    public final DeferrableSurface e() {
        return this.f24136a;
    }

    public final boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SessionConfig.e)) {
            return false;
        }
        SessionConfig.e eVar = (SessionConfig.e) obj;
        return this.f24136a.equals(eVar.e()) && this.f24137b.equals(eVar.d()) && ((str = this.f24138c) != null ? str.equals(eVar.c()) : eVar.c() == null) && this.f24139d == eVar.f() && this.f24140e.equals(eVar.b());
    }

    @Override // androidx.camera.core.impl.SessionConfig.e
    public final int f() {
        return this.f24139d;
    }

    public final int hashCode() {
        int iHashCode = (((this.f24136a.hashCode() ^ 1000003) * 1000003) ^ this.f24137b.hashCode()) * 1000003;
        String str = this.f24138c;
        return ((((iHashCode ^ (str == null ? 0 : str.hashCode())) * 1000003) ^ this.f24139d) * 1000003) ^ this.f24140e.hashCode();
    }

    public final String toString() {
        return "OutputConfig{surface=" + this.f24136a + ", sharedSurfaces=" + this.f24137b + ", physicalCameraId=" + this.f24138c + ", surfaceGroupId=" + this.f24139d + ", dynamicRange=" + this.f24140e + "}";
    }
}
