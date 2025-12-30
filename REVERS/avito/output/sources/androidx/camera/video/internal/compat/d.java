package androidx.camera.video.internal.compat;

import android.media.MediaCodecInfo;
import android.util.Range;
import j.InterfaceC42164u;
import j.N;
import j.X;

/* compiled from: Api28Impl.java */
@X
/* loaded from: classes.dex */
public final class d {
    @InterfaceC42164u
    @N
    public static Range<Integer> a(@N MediaCodecInfo.EncoderCapabilities encoderCapabilities) {
        return encoderCapabilities.getQualityRange();
    }
}
