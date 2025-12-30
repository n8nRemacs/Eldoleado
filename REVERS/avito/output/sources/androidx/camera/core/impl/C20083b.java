package androidx.camera.core.impl;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.impl.UseCaseConfigFactory;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AutoValue_AttachedSurfaceInfo.java */
/* renamed from: androidx.camera.core.impl.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20083b extends AbstractC20081a {

    /* renamed from: a, reason: collision with root package name */
    public final SurfaceConfig f24079a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24080b;

    /* renamed from: c, reason: collision with root package name */
    public final Size f24081c;

    /* renamed from: d, reason: collision with root package name */
    public final androidx.camera.core.F f24082d;

    /* renamed from: e, reason: collision with root package name */
    public final ArrayList f24083e;

    /* renamed from: f, reason: collision with root package name */
    public final Config f24084f;

    /* renamed from: g, reason: collision with root package name */
    public final Range<Integer> f24085g;

    public C20083b(SurfaceConfig surfaceConfig, int i12, Size size, androidx.camera.core.F f12, ArrayList arrayList, @j.P Config config, @j.P Range range) {
        if (surfaceConfig == null) {
            throw new NullPointerException("Null surfaceConfig");
        }
        this.f24079a = surfaceConfig;
        this.f24080b = i12;
        if (size == null) {
            throw new NullPointerException("Null size");
        }
        this.f24081c = size;
        if (f12 == null) {
            throw new NullPointerException("Null dynamicRange");
        }
        this.f24082d = f12;
        this.f24083e = arrayList;
        this.f24084f = config;
        this.f24085g = range;
    }

    @Override // androidx.camera.core.impl.AbstractC20081a
    @j.N
    public final List<UseCaseConfigFactory.CaptureType> b() {
        return this.f24083e;
    }

    @Override // androidx.camera.core.impl.AbstractC20081a
    @j.N
    public final androidx.camera.core.F c() {
        return this.f24082d;
    }

    @Override // androidx.camera.core.impl.AbstractC20081a
    public final int d() {
        return this.f24080b;
    }

    @Override // androidx.camera.core.impl.AbstractC20081a
    @j.P
    public final Config e() {
        return this.f24084f;
    }

    public final boolean equals(Object obj) {
        Config config;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractC20081a)) {
            return false;
        }
        AbstractC20081a abstractC20081a = (AbstractC20081a) obj;
        if (this.f24079a.equals(abstractC20081a.g()) && this.f24080b == abstractC20081a.d() && this.f24081c.equals(abstractC20081a.f()) && this.f24082d.equals(abstractC20081a.c()) && this.f24083e.equals(abstractC20081a.b()) && ((config = this.f24084f) != null ? config.equals(abstractC20081a.e()) : abstractC20081a.e() == null)) {
            Range<Integer> range = this.f24085g;
            if (range == null) {
                if (abstractC20081a.h() == null) {
                    return true;
                }
            } else if (range.equals(abstractC20081a.h())) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.camera.core.impl.AbstractC20081a
    @j.N
    public final Size f() {
        return this.f24081c;
    }

    @Override // androidx.camera.core.impl.AbstractC20081a
    @j.N
    public final SurfaceConfig g() {
        return this.f24079a;
    }

    @Override // androidx.camera.core.impl.AbstractC20081a
    @j.P
    public final Range<Integer> h() {
        return this.f24085g;
    }

    public final int hashCode() {
        int iHashCode = (((((((((this.f24079a.hashCode() ^ 1000003) * 1000003) ^ this.f24080b) * 1000003) ^ this.f24081c.hashCode()) * 1000003) ^ this.f24082d.hashCode()) * 1000003) ^ this.f24083e.hashCode()) * 1000003;
        Config config = this.f24084f;
        int iHashCode2 = (iHashCode ^ (config == null ? 0 : config.hashCode())) * 1000003;
        Range<Integer> range = this.f24085g;
        return iHashCode2 ^ (range != null ? range.hashCode() : 0);
    }

    public final String toString() {
        return "AttachedSurfaceInfo{surfaceConfig=" + this.f24079a + ", imageFormat=" + this.f24080b + ", size=" + this.f24081c + ", dynamicRange=" + this.f24082d + ", captureTypes=" + this.f24083e + ", implementationOptions=" + this.f24084f + ", targetFrameRate=" + this.f24085g + "}";
    }
}
