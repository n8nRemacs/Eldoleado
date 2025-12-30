package androidx.camera.core.impl;

import android.util.Size;
import java.util.Map;

/* compiled from: AutoValue_SurfaceSizeDefinition.java */
/* renamed from: androidx.camera.core.impl.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20108o extends I0 {

    /* renamed from: a, reason: collision with root package name */
    public final Size f24163a;

    /* renamed from: b, reason: collision with root package name */
    public final Map<Integer, Size> f24164b;

    /* renamed from: c, reason: collision with root package name */
    public final Size f24165c;

    /* renamed from: d, reason: collision with root package name */
    public final Map<Integer, Size> f24166d;

    /* renamed from: e, reason: collision with root package name */
    public final Size f24167e;

    /* renamed from: f, reason: collision with root package name */
    public final Map<Integer, Size> f24168f;

    /* renamed from: g, reason: collision with root package name */
    public final Map<Integer, Size> f24169g;

    public C20108o(Size size, Map<Integer, Size> map, Size size2, Map<Integer, Size> map2, Size size3, Map<Integer, Size> map3, Map<Integer, Size> map4) {
        if (size == null) {
            throw new NullPointerException("Null analysisSize");
        }
        this.f24163a = size;
        if (map == null) {
            throw new NullPointerException("Null s720pSizeMap");
        }
        this.f24164b = map;
        if (size2 == null) {
            throw new NullPointerException("Null previewSize");
        }
        this.f24165c = size2;
        if (map2 == null) {
            throw new NullPointerException("Null s1440pSizeMap");
        }
        this.f24166d = map2;
        if (size3 == null) {
            throw new NullPointerException("Null recordSize");
        }
        this.f24167e = size3;
        if (map3 == null) {
            throw new NullPointerException("Null maximumSizeMap");
        }
        this.f24168f = map3;
        if (map4 == null) {
            throw new NullPointerException("Null ultraMaximumSizeMap");
        }
        this.f24169g = map4;
    }

    @Override // androidx.camera.core.impl.I0
    @j.N
    public final Size b() {
        return this.f24163a;
    }

    @Override // androidx.camera.core.impl.I0
    @j.N
    public final Map<Integer, Size> c() {
        return this.f24168f;
    }

    @Override // androidx.camera.core.impl.I0
    @j.N
    public final Size d() {
        return this.f24165c;
    }

    @Override // androidx.camera.core.impl.I0
    @j.N
    public final Size e() {
        return this.f24167e;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof I0)) {
            return false;
        }
        I0 i02 = (I0) obj;
        return this.f24163a.equals(i02.b()) && this.f24164b.equals(i02.g()) && this.f24165c.equals(i02.d()) && this.f24166d.equals(i02.f()) && this.f24167e.equals(i02.e()) && this.f24168f.equals(i02.c()) && this.f24169g.equals(i02.h());
    }

    @Override // androidx.camera.core.impl.I0
    @j.N
    public final Map<Integer, Size> f() {
        return this.f24166d;
    }

    @Override // androidx.camera.core.impl.I0
    @j.N
    public final Map<Integer, Size> g() {
        return this.f24164b;
    }

    @Override // androidx.camera.core.impl.I0
    @j.N
    public final Map<Integer, Size> h() {
        return this.f24169g;
    }

    public final int hashCode() {
        return ((((((((((((this.f24163a.hashCode() ^ 1000003) * 1000003) ^ this.f24164b.hashCode()) * 1000003) ^ this.f24165c.hashCode()) * 1000003) ^ this.f24166d.hashCode()) * 1000003) ^ this.f24167e.hashCode()) * 1000003) ^ this.f24168f.hashCode()) * 1000003) ^ this.f24169g.hashCode();
    }

    public final String toString() {
        return "SurfaceSizeDefinition{analysisSize=" + this.f24163a + ", s720pSizeMap=" + this.f24164b + ", previewSize=" + this.f24165c + ", s1440pSizeMap=" + this.f24166d + ", recordSize=" + this.f24167e + ", maximumSizeMap=" + this.f24168f + ", ultraMaximumSizeMap=" + this.f24169g + "}";
    }
}
