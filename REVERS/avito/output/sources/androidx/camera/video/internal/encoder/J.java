package androidx.camera.video.internal.encoder;

import VY0.c;
import android.media.MediaFormat;
import android.util.Size;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.internal.encoder.C20163e;
import j.X;

/* compiled from: VideoEncoderConfig.java */
@VY0.c
@X
/* loaded from: classes.dex */
public abstract class J implements InterfaceC20171m {

    /* compiled from: VideoEncoderConfig.java */
    @c.a
    public static abstract class a {
        @j.N
        public abstract J a();

        @j.N
        public abstract a b(int i12);

        @j.N
        public abstract a c(@j.N K k12);

        @j.N
        public abstract a d(int i12);

        @j.N
        public abstract a e(@j.N Timebase timebase);

        @j.N
        public abstract a f(@j.N String str);

        @j.N
        public abstract a g(int i12);

        @j.N
        public abstract a h(@j.N Size size);
    }

    @j.N
    public static a d() {
        C20163e.b bVar = new C20163e.b();
        bVar.f25150b = -1;
        bVar.f25156h = 1;
        bVar.f25153e = 2130708361;
        bVar.c(K.f25123a);
        return bVar;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20171m
    @j.N
    public final MediaFormat c() {
        Size sizeK = k();
        MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(((C20163e) this).f25140a, sizeK.getWidth(), sizeK.getHeight());
        mediaFormatCreateVideoFormat.setInteger("color-format", f());
        mediaFormatCreateVideoFormat.setInteger("bitrate", e());
        mediaFormatCreateVideoFormat.setInteger("frame-rate", h());
        mediaFormatCreateVideoFormat.setInteger("i-frame-interval", i());
        if (j() != -1) {
            mediaFormatCreateVideoFormat.setInteger("profile", j());
        }
        K kG2 = g();
        if (kG2.b() != 0) {
            mediaFormatCreateVideoFormat.setInteger("color-standard", kG2.b());
        }
        if (kG2.c() != 0) {
            mediaFormatCreateVideoFormat.setInteger("color-transfer", kG2.c());
        }
        if (kG2.a() != 0) {
            mediaFormatCreateVideoFormat.setInteger("color-range", kG2.a());
        }
        return mediaFormatCreateVideoFormat;
    }

    public abstract int e();

    public abstract int f();

    @j.N
    public abstract K g();

    public abstract int h();

    public abstract int i();

    public abstract int j();

    @j.N
    public abstract Size k();
}
