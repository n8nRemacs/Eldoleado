package androidx.camera.video.internal.encoder;

import android.media.MediaCodecInfo;
import j.X;
import java.util.Objects;

/* compiled from: EncoderInfoImpl.java */
@X
/* loaded from: classes.dex */
public abstract class D implements C {

    /* renamed from: a, reason: collision with root package name */
    public final MediaCodecInfo.CodecCapabilities f25114a;

    public D(@j.N MediaCodecInfo mediaCodecInfo, @j.N String str) throws InvalidConfigException {
        try {
            MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
            Objects.requireNonNull(capabilitiesForType);
            this.f25114a = capabilitiesForType;
        } catch (RuntimeException e12) {
            throw new InvalidConfigException(androidx.camera.camera2.internal.G.f("Unable to get CodecCapabilities for mime: ", str), e12);
        }
    }
}
