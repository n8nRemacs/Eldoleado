package androidx.camera.video.internal.audio;

import j.N;
import j.P;
import j.X;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

@X
/* loaded from: classes.dex */
public interface AudioStream {

    public static class AudioStreamException extends Exception {
    }

    public interface a {
    }

    @VY0.c
    public static abstract class b {
        public abstract int a();

        public abstract long b();
    }

    void a(@P a aVar, @P Executor executor);

    @N
    b read(@N ByteBuffer byteBuffer);

    void release();

    void start();

    void stop();
}
