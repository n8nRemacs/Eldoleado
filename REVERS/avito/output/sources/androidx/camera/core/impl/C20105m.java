package androidx.camera.core.impl;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.impl.G0;

/* compiled from: AutoValue_StreamSpec.java */
/* renamed from: androidx.camera.core.impl.m, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20105m extends G0 {

    /* renamed from: b, reason: collision with root package name */
    public final Size f24152b;

    /* renamed from: c, reason: collision with root package name */
    public final androidx.camera.core.F f24153c;

    /* renamed from: d, reason: collision with root package name */
    public final Range<Integer> f24154d;

    /* renamed from: e, reason: collision with root package name */
    public final Config f24155e;

    /* compiled from: AutoValue_StreamSpec.java */
    /* renamed from: androidx.camera.core.impl.m$b */
    public static final class b extends G0.a {

        /* renamed from: a, reason: collision with root package name */
        public Size f24156a;

        /* renamed from: b, reason: collision with root package name */
        public androidx.camera.core.F f24157b;

        /* renamed from: c, reason: collision with root package name */
        public Range<Integer> f24158c;

        /* renamed from: d, reason: collision with root package name */
        public Config f24159d;

        public b() {
        }

        @Override // androidx.camera.core.impl.G0.a
        public final G0 a() {
            String strQ = this.f24156a == null ? " resolution" : "";
            if (this.f24157b == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " dynamicRange");
            }
            if (this.f24158c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " expectedFrameRateRange");
            }
            if (strQ.isEmpty()) {
                return new C20105m(this.f24156a, this.f24157b, this.f24158c, this.f24159d, null);
            }
            throw new IllegalStateException("Missing required properties:".concat(strQ));
        }

        @Override // androidx.camera.core.impl.G0.a
        public final G0.a b(androidx.camera.core.F f12) {
            if (f12 == null) {
                throw new NullPointerException("Null dynamicRange");
            }
            this.f24157b = f12;
            return this;
        }

        @Override // androidx.camera.core.impl.G0.a
        public final G0.a c(Range<Integer> range) {
            if (range == null) {
                throw new NullPointerException("Null expectedFrameRateRange");
            }
            this.f24158c = range;
            return this;
        }

        @Override // androidx.camera.core.impl.G0.a
        public final G0.a d(Config config) {
            this.f24159d = config;
            return this;
        }

        @Override // androidx.camera.core.impl.G0.a
        public final G0.a e(Size size) {
            if (size == null) {
                throw new NullPointerException("Null resolution");
            }
            this.f24156a = size;
            return this;
        }

        public b(G0 g02, a aVar) {
            this.f24156a = g02.e();
            this.f24157b = g02.b();
            this.f24158c = g02.c();
            this.f24159d = g02.d();
        }
    }

    public C20105m() {
        throw null;
    }

    public C20105m(Size size, androidx.camera.core.F f12, Range range, Config config, a aVar) {
        this.f24152b = size;
        this.f24153c = f12;
        this.f24154d = range;
        this.f24155e = config;
    }

    @Override // androidx.camera.core.impl.G0
    @j.N
    public final androidx.camera.core.F b() {
        return this.f24153c;
    }

    @Override // androidx.camera.core.impl.G0
    @j.N
    public final Range<Integer> c() {
        return this.f24154d;
    }

    @Override // androidx.camera.core.impl.G0
    @j.P
    public final Config d() {
        return this.f24155e;
    }

    @Override // androidx.camera.core.impl.G0
    @j.N
    public final Size e() {
        return this.f24152b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof G0)) {
            return false;
        }
        G0 g02 = (G0) obj;
        if (this.f24152b.equals(g02.e()) && this.f24153c.equals(g02.b()) && this.f24154d.equals(g02.c())) {
            Config config = this.f24155e;
            if (config == null) {
                if (g02.d() == null) {
                    return true;
                }
            } else if (config.equals(g02.d())) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.camera.core.impl.G0
    public final G0.a f() {
        return new b(this, null);
    }

    public final int hashCode() {
        int iHashCode = (((((this.f24152b.hashCode() ^ 1000003) * 1000003) ^ this.f24153c.hashCode()) * 1000003) ^ this.f24154d.hashCode()) * 1000003;
        Config config = this.f24155e;
        return iHashCode ^ (config == null ? 0 : config.hashCode());
    }

    public final String toString() {
        return "StreamSpec{resolution=" + this.f24152b + ", dynamicRange=" + this.f24153c + ", expectedFrameRateRange=" + this.f24154d + ", implementationOptions=" + this.f24155e + "}";
    }
}
