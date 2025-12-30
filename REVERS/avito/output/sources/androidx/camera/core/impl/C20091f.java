package androidx.camera.core.impl;

import androidx.camera.core.impl.S;

/* compiled from: AutoValue_EncoderProfilesProxy_AudioProfileProxy.java */
/* renamed from: androidx.camera.core.impl.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20091f extends S.a {

    /* renamed from: a, reason: collision with root package name */
    public final int f24103a;

    /* renamed from: b, reason: collision with root package name */
    public final String f24104b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24105c;

    /* renamed from: d, reason: collision with root package name */
    public final int f24106d;

    /* renamed from: e, reason: collision with root package name */
    public final int f24107e;

    /* renamed from: f, reason: collision with root package name */
    public final int f24108f;

    public C20091f(int i12, int i13, String str, int i14, int i15, int i16) {
        this.f24103a = i12;
        if (str == null) {
            throw new NullPointerException("Null mediaType");
        }
        this.f24104b = str;
        this.f24105c = i13;
        this.f24106d = i14;
        this.f24107e = i15;
        this.f24108f = i16;
    }

    @Override // androidx.camera.core.impl.S.a
    public final int b() {
        return this.f24105c;
    }

    @Override // androidx.camera.core.impl.S.a
    public final int c() {
        return this.f24107e;
    }

    @Override // androidx.camera.core.impl.S.a
    public final int d() {
        return this.f24103a;
    }

    @Override // androidx.camera.core.impl.S.a
    @j.N
    public final String e() {
        return this.f24104b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof S.a)) {
            return false;
        }
        S.a aVar = (S.a) obj;
        return this.f24103a == aVar.d() && this.f24104b.equals(aVar.e()) && this.f24105c == aVar.b() && this.f24106d == aVar.g() && this.f24107e == aVar.c() && this.f24108f == aVar.f();
    }

    @Override // androidx.camera.core.impl.S.a
    public final int f() {
        return this.f24108f;
    }

    @Override // androidx.camera.core.impl.S.a
    public final int g() {
        return this.f24106d;
    }

    public final int hashCode() {
        return ((((((((((this.f24103a ^ 1000003) * 1000003) ^ this.f24104b.hashCode()) * 1000003) ^ this.f24105c) * 1000003) ^ this.f24106d) * 1000003) ^ this.f24107e) * 1000003) ^ this.f24108f;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AudioProfileProxy{codec=");
        sb2.append(this.f24103a);
        sb2.append(", mediaType=");
        sb2.append(this.f24104b);
        sb2.append(", bitrate=");
        sb2.append(this.f24105c);
        sb2.append(", sampleRate=");
        sb2.append(this.f24106d);
        sb2.append(", channels=");
        sb2.append(this.f24107e);
        sb2.append(", profile=");
        return AK.c.i(this.f24108f, "}", sb2);
    }
}
