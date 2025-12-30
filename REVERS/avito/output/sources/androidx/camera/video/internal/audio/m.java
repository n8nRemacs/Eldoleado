package androidx.camera.video.internal.audio;

import androidx.core.util.z;
import j.X;
import java.util.concurrent.TimeUnit;

/* compiled from: AudioUtils.java */
@X
/* loaded from: classes.dex */
public final class m {
    public static long a(int i12, long j12) {
        long j13 = i12;
        z.a("sampleRate must be greater than 0.", j13 > 0);
        return (TimeUnit.SECONDS.toNanos(1L) * j12) / j13;
    }

    public static long b(int i12, long j12) {
        long j13 = i12;
        z.a("bytesPerFrame must be greater than 0.", j13 > 0);
        return j12 / j13;
    }
}
