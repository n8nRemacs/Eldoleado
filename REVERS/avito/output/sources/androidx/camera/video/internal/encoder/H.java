package androidx.camera.video.internal.encoder;

import android.os.SystemClock;
import j.X;
import java.util.concurrent.TimeUnit;

/* compiled from: SystemTimeProvider.java */
@X
/* loaded from: classes.dex */
public class H implements I {
    public final long a() {
        return TimeUnit.NANOSECONDS.toMicros(SystemClock.elapsedRealtimeNanos());
    }

    public final long b() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }
}
