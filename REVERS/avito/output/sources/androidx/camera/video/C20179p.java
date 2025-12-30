package androidx.camera.video;

import android.util.Range;
import androidx.camera.video.n0;

/* compiled from: AutoValue_VideoSpec.java */
/* renamed from: androidx.camera.video.p, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20179p extends n0 {

    /* renamed from: d, reason: collision with root package name */
    public final D f25326d;

    /* renamed from: e, reason: collision with root package name */
    public final Range<Integer> f25327e;

    /* renamed from: f, reason: collision with root package name */
    public final Range<Integer> f25328f;

    /* renamed from: g, reason: collision with root package name */
    public final int f25329g;

    /* compiled from: AutoValue_VideoSpec.java */
    /* renamed from: androidx.camera.video.p$b */
    public static final class b extends n0.a {

        /* renamed from: a, reason: collision with root package name */
        public D f25330a;

        /* renamed from: b, reason: collision with root package name */
        public Range<Integer> f25331b;

        /* renamed from: c, reason: collision with root package name */
        public Range<Integer> f25332c;

        /* renamed from: d, reason: collision with root package name */
        public Integer f25333d;

        public b() {
        }

        @Override // androidx.camera.video.n0.a
        public final n0 a() {
            String strQ = this.f25330a == null ? " qualitySelector" : "";
            if (this.f25331b == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " frameRate");
            }
            if (this.f25332c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " bitrate");
            }
            if (this.f25333d == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " aspectRatio");
            }
            if (strQ.isEmpty()) {
                return new C20179p(this.f25330a, this.f25331b, this.f25332c, this.f25333d.intValue(), null);
            }
            throw new IllegalStateException("Missing required properties:".concat(strQ));
        }

        @Override // androidx.camera.video.n0.a
        public final n0.a b(int i12) {
            this.f25333d = Integer.valueOf(i12);
            return this;
        }

        public b(n0 n0Var, a aVar) {
            this.f25330a = n0Var.e();
            this.f25331b = n0Var.d();
            this.f25332c = n0Var.c();
            this.f25333d = Integer.valueOf(n0Var.b());
        }
    }

    public C20179p() {
        throw null;
    }

    public C20179p(D d12, Range range, Range range2, int i12, a aVar) {
        this.f25326d = d12;
        this.f25327e = range;
        this.f25328f = range2;
        this.f25329g = i12;
    }

    @Override // androidx.camera.video.n0
    public final int b() {
        return this.f25329g;
    }

    @Override // androidx.camera.video.n0
    @j.N
    public final Range<Integer> c() {
        return this.f25328f;
    }

    @Override // androidx.camera.video.n0
    @j.N
    public final Range<Integer> d() {
        return this.f25327e;
    }

    @Override // androidx.camera.video.n0
    @j.N
    public final D e() {
        return this.f25326d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof n0)) {
            return false;
        }
        n0 n0Var = (n0) obj;
        return this.f25326d.equals(n0Var.e()) && this.f25327e.equals(n0Var.d()) && this.f25328f.equals(n0Var.c()) && this.f25329g == n0Var.b();
    }

    @Override // androidx.camera.video.n0
    public final n0.a f() {
        return new b(this, null);
    }

    public final int hashCode() {
        return ((((((this.f25326d.hashCode() ^ 1000003) * 1000003) ^ this.f25327e.hashCode()) * 1000003) ^ this.f25328f.hashCode()) * 1000003) ^ this.f25329g;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("VideoSpec{qualitySelector=");
        sb2.append(this.f25326d);
        sb2.append(", frameRate=");
        sb2.append(this.f25327e);
        sb2.append(", bitrate=");
        sb2.append(this.f25328f);
        sb2.append(", aspectRatio=");
        return AK.c.i(this.f25329g, "}", sb2);
    }
}
