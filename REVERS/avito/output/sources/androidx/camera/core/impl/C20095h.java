package androidx.camera.core.impl;

import androidx.camera.core.impl.S;

/* compiled from: AutoValue_EncoderProfilesProxy_VideoProfileProxy.java */
/* renamed from: androidx.camera.core.impl.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20095h extends S.c {

    /* renamed from: a, reason: collision with root package name */
    public final int f24114a;

    /* renamed from: b, reason: collision with root package name */
    public final String f24115b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24116c;

    /* renamed from: d, reason: collision with root package name */
    public final int f24117d;

    /* renamed from: e, reason: collision with root package name */
    public final int f24118e;

    /* renamed from: f, reason: collision with root package name */
    public final int f24119f;

    /* renamed from: g, reason: collision with root package name */
    public final int f24120g;

    /* renamed from: h, reason: collision with root package name */
    public final int f24121h;

    /* renamed from: i, reason: collision with root package name */
    public final int f24122i;

    /* renamed from: j, reason: collision with root package name */
    public final int f24123j;

    public C20095h(int i12, String str, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i22) {
        this.f24114a = i12;
        if (str == null) {
            throw new NullPointerException("Null mediaType");
        }
        this.f24115b = str;
        this.f24116c = i13;
        this.f24117d = i14;
        this.f24118e = i15;
        this.f24119f = i16;
        this.f24120g = i17;
        this.f24121h = i18;
        this.f24122i = i19;
        this.f24123j = i22;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int b() {
        return this.f24121h;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int c() {
        return this.f24116c;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int d() {
        return this.f24122i;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int e() {
        return this.f24114a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof S.c)) {
            return false;
        }
        S.c cVar = (S.c) obj;
        return this.f24114a == cVar.e() && this.f24115b.equals(cVar.i()) && this.f24116c == cVar.c() && this.f24117d == cVar.f() && this.f24118e == cVar.k() && this.f24119f == cVar.h() && this.f24120g == cVar.j() && this.f24121h == cVar.b() && this.f24122i == cVar.d() && this.f24123j == cVar.g();
    }

    @Override // androidx.camera.core.impl.S.c
    public final int f() {
        return this.f24117d;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int g() {
        return this.f24123j;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int h() {
        return this.f24119f;
    }

    public final int hashCode() {
        return ((((((((((((((((((this.f24114a ^ 1000003) * 1000003) ^ this.f24115b.hashCode()) * 1000003) ^ this.f24116c) * 1000003) ^ this.f24117d) * 1000003) ^ this.f24118e) * 1000003) ^ this.f24119f) * 1000003) ^ this.f24120g) * 1000003) ^ this.f24121h) * 1000003) ^ this.f24122i) * 1000003) ^ this.f24123j;
    }

    @Override // androidx.camera.core.impl.S.c
    @j.N
    public final String i() {
        return this.f24115b;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int j() {
        return this.f24120g;
    }

    @Override // androidx.camera.core.impl.S.c
    public final int k() {
        return this.f24118e;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("VideoProfileProxy{codec=");
        sb2.append(this.f24114a);
        sb2.append(", mediaType=");
        sb2.append(this.f24115b);
        sb2.append(", bitrate=");
        sb2.append(this.f24116c);
        sb2.append(", frameRate=");
        sb2.append(this.f24117d);
        sb2.append(", width=");
        sb2.append(this.f24118e);
        sb2.append(", height=");
        sb2.append(this.f24119f);
        sb2.append(", profile=");
        sb2.append(this.f24120g);
        sb2.append(", bitDepth=");
        sb2.append(this.f24121h);
        sb2.append(", chromaSubsampling=");
        sb2.append(this.f24122i);
        sb2.append(", hdrFormat=");
        return AK.c.i(this.f24123j, "}", sb2);
    }
}
