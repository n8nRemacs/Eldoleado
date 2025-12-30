package androidx.camera.video.internal;

import j.N;
import j.X;
import java.io.Closeable;
import java.util.Locale;

/* compiled from: SharedByteBuffer.java */
@X
/* loaded from: classes.dex */
public final class e implements Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw null;
    }

    public final void finalize() throws Throwable {
        try {
            throw null;
        } catch (Throwable th2) {
            super.finalize();
            throw th2;
        }
    }

    @N
    public final String toString() {
        return String.format(Locale.US, "SharedByteBuffer[buf: %s, shareId: 0x%x, instanceId:0x%x]", null, 0, Integer.valueOf(System.identityHashCode(this)));
    }
}
