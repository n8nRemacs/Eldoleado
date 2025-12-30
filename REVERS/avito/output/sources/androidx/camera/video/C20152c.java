package androidx.camera.video;

import android.util.Range;
import androidx.camera.video.AbstractC20150a;

/* compiled from: AutoValue_AudioSpec.java */
/* renamed from: androidx.camera.video.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20152c extends AbstractC20150a {

    /* renamed from: c, reason: collision with root package name */
    public final Range<Integer> f24882c;

    /* renamed from: d, reason: collision with root package name */
    public final int f24883d;

    /* renamed from: e, reason: collision with root package name */
    public final int f24884e;

    /* renamed from: f, reason: collision with root package name */
    public final Range<Integer> f24885f;

    /* renamed from: g, reason: collision with root package name */
    public final int f24886g;

    /* compiled from: AutoValue_AudioSpec.java */
    /* renamed from: androidx.camera.video.c$b */
    public static final class b extends AbstractC20150a.AbstractC1537a {

        /* renamed from: a, reason: collision with root package name */
        public Range<Integer> f24887a;

        /* renamed from: b, reason: collision with root package name */
        public Integer f24888b;

        /* renamed from: c, reason: collision with root package name */
        public Integer f24889c;

        /* renamed from: d, reason: collision with root package name */
        public Range<Integer> f24890d;

        /* renamed from: e, reason: collision with root package name */
        public Integer f24891e;

        public b() {
        }

        @Override // androidx.camera.video.AbstractC20150a.AbstractC1537a
        public final AbstractC20150a a() {
            String strQ = this.f24887a == null ? " bitrate" : "";
            if (this.f24888b == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " sourceFormat");
            }
            if (this.f24889c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " source");
            }
            if (this.f24890d == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " sampleRate");
            }
            if (this.f24891e == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " channelCount");
            }
            if (strQ.isEmpty()) {
                return new C20152c(this.f24887a, this.f24888b.intValue(), this.f24889c.intValue(), this.f24890d, this.f24891e.intValue(), null);
            }
            throw new IllegalStateException("Missing required properties:".concat(strQ));
        }

        public b(AbstractC20150a abstractC20150a, a aVar) {
            this.f24887a = abstractC20150a.b();
            this.f24888b = Integer.valueOf(abstractC20150a.f());
            this.f24889c = Integer.valueOf(abstractC20150a.e());
            this.f24890d = abstractC20150a.d();
            this.f24891e = Integer.valueOf(abstractC20150a.c());
        }
    }

    public C20152c() {
        throw null;
    }

    public C20152c(Range range, int i12, int i13, Range range2, int i14, a aVar) {
        this.f24882c = range;
        this.f24883d = i12;
        this.f24884e = i13;
        this.f24885f = range2;
        this.f24886g = i14;
    }

    @Override // androidx.camera.video.AbstractC20150a
    @j.N
    public final Range<Integer> b() {
        return this.f24882c;
    }

    @Override // androidx.camera.video.AbstractC20150a
    public final int c() {
        return this.f24886g;
    }

    @Override // androidx.camera.video.AbstractC20150a
    @j.N
    public final Range<Integer> d() {
        return this.f24885f;
    }

    @Override // androidx.camera.video.AbstractC20150a
    public final int e() {
        return this.f24884e;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractC20150a)) {
            return false;
        }
        AbstractC20150a abstractC20150a = (AbstractC20150a) obj;
        return this.f24882c.equals(abstractC20150a.b()) && this.f24883d == abstractC20150a.f() && this.f24884e == abstractC20150a.e() && this.f24885f.equals(abstractC20150a.d()) && this.f24886g == abstractC20150a.c();
    }

    @Override // androidx.camera.video.AbstractC20150a
    public final int f() {
        return this.f24883d;
    }

    public final int hashCode() {
        return ((((((((this.f24882c.hashCode() ^ 1000003) * 1000003) ^ this.f24883d) * 1000003) ^ this.f24884e) * 1000003) ^ this.f24885f.hashCode()) * 1000003) ^ this.f24886g;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AudioSpec{bitrate=");
        sb2.append(this.f24882c);
        sb2.append(", sourceFormat=");
        sb2.append(this.f24883d);
        sb2.append(", source=");
        sb2.append(this.f24884e);
        sb2.append(", sampleRate=");
        sb2.append(this.f24885f);
        sb2.append(", channelCount=");
        return AK.c.i(this.f24886g, "}", sb2);
    }
}
