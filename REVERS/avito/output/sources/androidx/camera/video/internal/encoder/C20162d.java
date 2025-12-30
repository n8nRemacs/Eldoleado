package androidx.camera.video.internal.encoder;

import androidx.camera.core.impl.Timebase;
import androidx.camera.video.internal.encoder.AbstractC20159a;

/* compiled from: AutoValue_AudioEncoderConfig.java */
/* renamed from: androidx.camera.video.internal.encoder.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20162d extends AbstractC20159a {

    /* renamed from: a, reason: collision with root package name */
    public final String f25128a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25129b;

    /* renamed from: c, reason: collision with root package name */
    public final Timebase f25130c;

    /* renamed from: d, reason: collision with root package name */
    public final int f25131d;

    /* renamed from: e, reason: collision with root package name */
    public final int f25132e;

    /* renamed from: f, reason: collision with root package name */
    public final int f25133f;

    /* compiled from: AutoValue_AudioEncoderConfig.java */
    /* renamed from: androidx.camera.video.internal.encoder.d$b */
    public static final class b extends AbstractC20159a.AbstractC1539a {

        /* renamed from: a, reason: collision with root package name */
        public String f25134a;

        /* renamed from: b, reason: collision with root package name */
        public Integer f25135b;

        /* renamed from: c, reason: collision with root package name */
        public Timebase f25136c;

        /* renamed from: d, reason: collision with root package name */
        public Integer f25137d;

        /* renamed from: e, reason: collision with root package name */
        public Integer f25138e;

        /* renamed from: f, reason: collision with root package name */
        public Integer f25139f;

        @Override // androidx.camera.video.internal.encoder.AbstractC20159a.AbstractC1539a
        public final AbstractC20159a.AbstractC1539a b(int i12) {
            this.f25137d = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.AbstractC20159a.AbstractC1539a
        public final AbstractC20159a.AbstractC1539a c(int i12) {
            this.f25139f = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.AbstractC20159a.AbstractC1539a
        public final AbstractC20159a.AbstractC1539a d() {
            this.f25136c = Timebase.f24048b;
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.AbstractC20159a.AbstractC1539a
        public final AbstractC20159a.AbstractC1539a e(String str) {
            if (str == null) {
                throw new NullPointerException("Null mimeType");
            }
            this.f25134a = str;
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.AbstractC20159a.AbstractC1539a
        public final AbstractC20159a.AbstractC1539a f(int i12) {
            this.f25135b = Integer.valueOf(i12);
            return this;
        }

        @Override // androidx.camera.video.internal.encoder.AbstractC20159a.AbstractC1539a
        public final AbstractC20159a.AbstractC1539a g(int i12) {
            this.f25138e = Integer.valueOf(i12);
            return this;
        }
    }

    public C20162d(String str, int i12, Timebase timebase, int i13, int i14, int i15, a aVar) {
        this.f25128a = str;
        this.f25129b = i12;
        this.f25130c = timebase;
        this.f25131d = i13;
        this.f25132e = i14;
        this.f25133f = i15;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20171m
    @j.N
    public final String a() {
        return this.f25128a;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20171m
    @j.N
    public final Timebase b() {
        return this.f25130c;
    }

    @Override // androidx.camera.video.internal.encoder.AbstractC20159a
    public final int e() {
        return this.f25131d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractC20159a)) {
            return false;
        }
        AbstractC20159a abstractC20159a = (AbstractC20159a) obj;
        if (this.f25128a.equals(((C20162d) abstractC20159a).f25128a)) {
            if (this.f25129b == abstractC20159a.g() && this.f25130c.equals(((C20162d) abstractC20159a).f25130c) && this.f25131d == abstractC20159a.e() && this.f25132e == abstractC20159a.h() && this.f25133f == abstractC20159a.f()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.camera.video.internal.encoder.AbstractC20159a
    public final int f() {
        return this.f25133f;
    }

    @Override // androidx.camera.video.internal.encoder.AbstractC20159a
    public final int g() {
        return this.f25129b;
    }

    @Override // androidx.camera.video.internal.encoder.AbstractC20159a
    public final int h() {
        return this.f25132e;
    }

    public final int hashCode() {
        return ((((((((((this.f25128a.hashCode() ^ 1000003) * 1000003) ^ this.f25129b) * 1000003) ^ this.f25130c.hashCode()) * 1000003) ^ this.f25131d) * 1000003) ^ this.f25132e) * 1000003) ^ this.f25133f;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AudioEncoderConfig{mimeType=");
        sb2.append(this.f25128a);
        sb2.append(", profile=");
        sb2.append(this.f25129b);
        sb2.append(", inputTimebase=");
        sb2.append(this.f25130c);
        sb2.append(", bitrate=");
        sb2.append(this.f25131d);
        sb2.append(", sampleRate=");
        sb2.append(this.f25132e);
        sb2.append(", channelCount=");
        return AK.c.i(this.f25133f, "}", sb2);
    }
}
