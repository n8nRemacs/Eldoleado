package androidx.camera.video.internal.encoder;

import VY0.c;
import android.media.MediaFormat;
import androidx.camera.video.internal.encoder.C20162d;
import j.X;
import java.util.Objects;

/* compiled from: AudioEncoderConfig.java */
@VY0.c
@X
/* renamed from: androidx.camera.video.internal.encoder.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20159a implements InterfaceC20171m {

    /* compiled from: AudioEncoderConfig.java */
    @c.a
    /* renamed from: androidx.camera.video.internal.encoder.a$a, reason: collision with other inner class name */
    public static abstract class AbstractC1539a {
        @j.N
        public final AbstractC20159a a() {
            C20162d.b bVar = (C20162d.b) this;
            String strQ = bVar.f25134a == null ? " mimeType" : "";
            if (bVar.f25135b == null) {
                strQ = strQ.concat(" profile");
            }
            if (bVar.f25136c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " inputTimebase");
            }
            if (bVar.f25137d == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " bitrate");
            }
            if (bVar.f25138e == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " sampleRate");
            }
            if (bVar.f25139f == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " channelCount");
            }
            if (!strQ.isEmpty()) {
                throw new IllegalStateException("Missing required properties:".concat(strQ));
            }
            C20162d c20162d = new C20162d(bVar.f25134a, bVar.f25135b.intValue(), bVar.f25136c, bVar.f25137d.intValue(), bVar.f25138e.intValue(), bVar.f25139f.intValue(), null);
            if (Objects.equals(c20162d.f25128a, "audio/mp4a-latm") && c20162d.f25129b == -1) {
                throw new IllegalArgumentException("Encoder mime set to AAC, but no AAC profile was provided.");
            }
            return c20162d;
        }

        @j.N
        public abstract AbstractC1539a b(int i12);

        @j.N
        public abstract AbstractC1539a c(int i12);

        @j.N
        public abstract AbstractC1539a d();

        @j.N
        public abstract AbstractC1539a e(@j.N String str);

        @j.N
        public abstract AbstractC1539a f(int i12);

        @j.N
        public abstract AbstractC1539a g(int i12);
    }

    @j.N
    public static AbstractC1539a d() {
        C20162d.b bVar = new C20162d.b();
        bVar.f25135b = -1;
        return bVar;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20171m
    @j.N
    public final MediaFormat c() {
        int iH2 = h();
        int iF2 = f();
        String str = ((C20162d) this).f25128a;
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(str, iH2, iF2);
        mediaFormatCreateAudioFormat.setInteger("bitrate", e());
        if (g() != -1) {
            if (str.equals("audio/mp4a-latm")) {
                mediaFormatCreateAudioFormat.setInteger("aac-profile", g());
            } else {
                mediaFormatCreateAudioFormat.setInteger("profile", g());
            }
        }
        return mediaFormatCreateAudioFormat;
    }

    public abstract int e();

    public abstract int f();

    public abstract int g();

    public abstract int h();
}
