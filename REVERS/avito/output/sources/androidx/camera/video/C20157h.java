package androidx.camera.video;

import androidx.camera.video.AbstractC20184v;

/* compiled from: AutoValue_MediaSpec.java */
/* renamed from: androidx.camera.video.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20157h extends AbstractC20184v {

    /* renamed from: a, reason: collision with root package name */
    public final n0 f24919a;

    /* renamed from: b, reason: collision with root package name */
    public final AbstractC20150a f24920b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24921c;

    /* compiled from: AutoValue_MediaSpec.java */
    /* renamed from: androidx.camera.video.h$b */
    public static final class b extends AbstractC20184v.a {

        /* renamed from: a, reason: collision with root package name */
        public n0 f24922a;

        /* renamed from: b, reason: collision with root package name */
        public AbstractC20150a f24923b;

        /* renamed from: c, reason: collision with root package name */
        public Integer f24924c;

        public b() {
        }

        @Override // androidx.camera.video.AbstractC20184v.a
        public final AbstractC20184v a() {
            String strQ = this.f24922a == null ? " videoSpec" : "";
            if (this.f24923b == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " audioSpec");
            }
            if (this.f24924c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " outputFormat");
            }
            if (strQ.isEmpty()) {
                return new C20157h(this.f24922a, this.f24923b, this.f24924c.intValue(), null);
            }
            throw new IllegalStateException("Missing required properties:".concat(strQ));
        }

        @Override // androidx.camera.video.AbstractC20184v.a
        public final n0 b() {
            n0 n0Var = this.f24922a;
            if (n0Var != null) {
                return n0Var;
            }
            throw new IllegalStateException("Property \"videoSpec\" has not been set");
        }

        public b(AbstractC20184v abstractC20184v, a aVar) {
            this.f24922a = abstractC20184v.d();
            this.f24923b = abstractC20184v.b();
            this.f24924c = Integer.valueOf(abstractC20184v.c());
        }
    }

    public C20157h(n0 n0Var, AbstractC20150a abstractC20150a, int i12, a aVar) {
        this.f24919a = n0Var;
        this.f24920b = abstractC20150a;
        this.f24921c = i12;
    }

    @Override // androidx.camera.video.AbstractC20184v
    @j.N
    public final AbstractC20150a b() {
        return this.f24920b;
    }

    @Override // androidx.camera.video.AbstractC20184v
    public final int c() {
        return this.f24921c;
    }

    @Override // androidx.camera.video.AbstractC20184v
    @j.N
    public final n0 d() {
        return this.f24919a;
    }

    @Override // androidx.camera.video.AbstractC20184v
    public final AbstractC20184v.a e() {
        return new b(this, null);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractC20184v)) {
            return false;
        }
        AbstractC20184v abstractC20184v = (AbstractC20184v) obj;
        return this.f24919a.equals(abstractC20184v.d()) && this.f24920b.equals(abstractC20184v.b()) && this.f24921c == abstractC20184v.c();
    }

    public final int hashCode() {
        return ((((this.f24919a.hashCode() ^ 1000003) * 1000003) ^ this.f24920b.hashCode()) * 1000003) ^ this.f24921c;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("MediaSpec{videoSpec=");
        sb2.append(this.f24919a);
        sb2.append(", audioSpec=");
        sb2.append(this.f24920b);
        sb2.append(", outputFormat=");
        return AK.c.i(this.f24921c, "}", sb2);
    }
}
