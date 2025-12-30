package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.util.Range;
import j.X;
import java.util.Objects;

/* compiled from: VideoEncoderInfoImpl.java */
@X
/* loaded from: classes.dex */
public class M extends D implements L {

    /* renamed from: b, reason: collision with root package name */
    public final MediaCodecInfo.VideoCapabilities f25127b;

    public M(@j.N MediaCodecInfo mediaCodecInfo, @j.N String str) {
        super(mediaCodecInfo, str);
        MediaCodecInfo.VideoCapabilities videoCapabilities = this.f25114a.getVideoCapabilities();
        Objects.requireNonNull(videoCapabilities);
        this.f25127b = videoCapabilities;
    }

    @j.N
    public static M i(@j.N J j12) {
        MediaCodec mediaCodecA = new androidx.camera.video.internal.workaround.b().a(j12.c());
        MediaCodecInfo codecInfo = mediaCodecA.getCodecInfo();
        mediaCodecA.release();
        return new M(codecInfo, ((C20163e) j12).f25140a);
    }

    @Override // androidx.camera.video.internal.encoder.L
    @j.N
    public final Range<Integer> a(int i12) {
        try {
            return this.f25127b.getSupportedWidthsFor(i12);
        } catch (Throwable th2) {
            if (th2 instanceof IllegalArgumentException) {
                throw th2;
            }
            throw new IllegalArgumentException(th2);
        }
    }

    @Override // androidx.camera.video.internal.encoder.L
    @j.N
    public final Range<Integer> b() {
        return this.f25127b.getBitrateRange();
    }

    @Override // androidx.camera.video.internal.encoder.L
    public final boolean c(int i12, int i13) {
        return this.f25127b.isSizeSupported(i12, i13);
    }

    @Override // androidx.camera.video.internal.encoder.L
    @j.N
    public final Range<Integer> d(int i12) {
        try {
            return this.f25127b.getSupportedHeightsFor(i12);
        } catch (Throwable th2) {
            if (th2 instanceof IllegalArgumentException) {
                throw th2;
            }
            throw new IllegalArgumentException(th2);
        }
    }

    @Override // androidx.camera.video.internal.encoder.L
    @j.N
    public final Range<Integer> e() {
        return this.f25127b.getSupportedWidths();
    }

    @Override // androidx.camera.video.internal.encoder.L
    @j.N
    public final Range<Integer> f() {
        return this.f25127b.getSupportedHeights();
    }

    @Override // androidx.camera.video.internal.encoder.L
    public final int g() {
        return this.f25127b.getHeightAlignment();
    }

    @Override // androidx.camera.video.internal.encoder.L
    public final int h() {
        return this.f25127b.getWidthAlignment();
    }
}
