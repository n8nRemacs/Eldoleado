package androidx.camera.video.internal.encoder;

import android.util.Size;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.internal.encoder.J;

/* compiled from: AutoValue_VideoEncoderConfig.java */
/* renamed from: androidx.camera.video.internal.encoder.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20163e extends J {

    /* renamed from: a, reason: collision with root package name */
    public final String f25140a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25141b;

    /* renamed from: c, reason: collision with root package name */
    public final Timebase f25142c;

    /* renamed from: d, reason: collision with root package name */
    public final Size f25143d;

    /* renamed from: e, reason: collision with root package name */
    public final int f25144e;

    /* renamed from: f, reason: collision with root package name */
    public final K f25145f;

    /* renamed from: g, reason: collision with root package name */
    public final int f25146g;

    /* renamed from: h, reason: collision with root package name */
    public final int f25147h;

    /* renamed from: i, reason: collision with root package name */
    public final int f25148i;

    /* compiled from: AutoValue_VideoEncoderConfig.java */
    /* renamed from: androidx.camera.video.internal.encoder.e$b */
    public static final class b extends J.a {

        /* renamed from: a, reason: collision with root package name */
        public String f25149a;

        /* renamed from: b, reason: collision with root package name */
        public Integer f25150b;

        /* renamed from: c, reason: collision with root package name */
        public Timebase f25151c;

        /* renamed from: d, reason: collision with root package name */
        public Size f25152d;

        /* renamed from: e, reason: collision with root package name */
        public Integer f25153e;

        /* renamed from: f, reason: collision with root package name */
        public K f25154f;

        /* renamed from: g, reason: collision with root package name */
        public Integer f25155g;

        /* renamed from: h, reason: collision with root package name */
        public Integer f25156h;

        /* renamed from: i, reason: collision with root package name */
        public Integer f25157i;

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J a() {
            String strQ = this.f25149a == null ? " mimeType" : "";
            if (this.f25150b == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " profile");
            }
            if (this.f25151c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " inputTimebase");
            }
            if (this.f25152d == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " resolution");
            }
            if (this.f25153e == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " colorFormat");
            }
            if (this.f25154f == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " dataSpace");
            }
            if (this.f25155g == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " frameRate");
            }
            if (this.f25156h == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " IFrameInterval");
            }
            if (this.f25157i == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " bitrate");
            }
            if (strQ.isEmpty()) {
                return new C20163e(this.f25149a, this.f25150b.intValue(), this.f25151c, this.f25152d, this.f25153e.intValue(), this.f25154f, this.f25155g.intValue(), this.f25156h.intValue(), this.f25157i.intValue(), null);
            }
            throw new IllegalStateException("Missing required properties:".concat(strQ));
        }

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J.a b(int i12) {
            this.f25157i = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J.a c(K k12) {
            if (k12 == null) {
                throw new NullPointerException("Null dataSpace");
            }
            this.f25154f = k12;
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J.a d(int i12) {
            this.f25155g = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J.a e(Timebase timebase) {
            if (timebase == null) {
                throw new NullPointerException("Null inputTimebase");
            }
            this.f25151c = timebase;
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J.a f(String str) {
            if (str == null) {
                throw new NullPointerException("Null mimeType");
            }
            this.f25149a = str;
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J.a g(int i12) {
            this.f25150b = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.J.a
        public final J.a h(Size size) {
            if (size == null) {
                throw new NullPointerException("Null resolution");
            }
            this.f25152d = size;
            return this;
        }
    }

    public C20163e(String str, int i12, Timebase timebase, Size size, int i13, K k12, int i14, int i15, int i16, a aVar) {
        this.f25140a = str;
        this.f25141b = i12;
        this.f25142c = timebase;
        this.f25143d = size;
        this.f25144e = i13;
        this.f25145f = k12;
        this.f25146g = i14;
        this.f25147h = i15;
        this.f25148i = i16;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20171m
    @j.N
    public final String a() {
        return this.f25140a;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20171m
    @j.N
    public final Timebase b() {
        return this.f25142c;
    }

    @Override // androidx.camera.video.internal.encoder.J
    public final int e() {
        return this.f25148i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof J)) {
            return false;
        }
        J j12 = (J) obj;
        if (this.f25140a.equals(((C20163e) j12).f25140a)) {
            if (this.f25141b == j12.j() && this.f25142c.equals(((C20163e) j12).f25142c) && this.f25143d.equals(j12.k()) && this.f25144e == j12.f() && this.f25145f.equals(j12.g()) && this.f25146g == j12.h() && this.f25147h == j12.i() && this.f25148i == j12.e()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.camera.video.internal.encoder.J
    public final int f() {
        return this.f25144e;
    }

    @Override // androidx.camera.video.internal.encoder.J
    @j.N
    public final K g() {
        return this.f25145f;
    }

    @Override // androidx.camera.video.internal.encoder.J
    public final int h() {
        return this.f25146g;
    }

    public final int hashCode() {
        return ((((((((((((((((this.f25140a.hashCode() ^ 1000003) * 1000003) ^ this.f25141b) * 1000003) ^ this.f25142c.hashCode()) * 1000003) ^ this.f25143d.hashCode()) * 1000003) ^ this.f25144e) * 1000003) ^ this.f25145f.hashCode()) * 1000003) ^ this.f25146g) * 1000003) ^ this.f25147h) * 1000003) ^ this.f25148i;
    }

    @Override // androidx.camera.video.internal.encoder.J
    public final int i() {
        return this.f25147h;
    }

    @Override // androidx.camera.video.internal.encoder.J
    public final int j() {
        return this.f25141b;
    }

    @Override // androidx.camera.video.internal.encoder.J
    @j.N
    public final Size k() {
        return this.f25143d;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("VideoEncoderConfig{mimeType=");
        sb2.append(this.f25140a);
        sb2.append(", profile=");
        sb2.append(this.f25141b);
        sb2.append(", inputTimebase=");
        sb2.append(this.f25142c);
        sb2.append(", resolution=");
        sb2.append(this.f25143d);
        sb2.append(", colorFormat=");
        sb2.append(this.f25144e);
        sb2.append(", dataSpace=");
        sb2.append(this.f25145f);
        sb2.append(", frameRate=");
        sb2.append(this.f25146g);
        sb2.append(", IFrameInterval=");
        sb2.append(this.f25147h);
        sb2.append(", bitrate=");
        return AK.c.i(this.f25148i, "}", sb2);
    }
}
