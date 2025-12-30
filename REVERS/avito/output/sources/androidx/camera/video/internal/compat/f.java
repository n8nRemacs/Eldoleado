package androidx.camera.video.internal.compat;

import android.content.Context;
import android.media.AudioRecord;
import android.media.MediaCodecInfo;
import android.util.Range;
import j.InterfaceC42164u;
import j.N;
import j.X;

/* compiled from: Api31Impl.java */
@X
/* loaded from: classes.dex */
public final class f {
    @InterfaceC42164u
    @N
    public static Range<Integer>[] a(@N MediaCodecInfo.AudioCapabilities audioCapabilities) {
        return audioCapabilities.getInputChannelCountRanges();
    }

    @InterfaceC42164u
    public static int b(@N MediaCodecInfo.AudioCapabilities audioCapabilities) {
        return audioCapabilities.getMinInputChannelCount();
    }

    @InterfaceC42164u
    public static void c(@N AudioRecord.Builder builder, @N Context context) {
        builder.setContext(context);
    }
}
