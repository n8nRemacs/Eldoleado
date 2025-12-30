package androidx.camera.video.internal.encoder;

import androidx.camera.video.i0;
import androidx.camera.video.internal.BufferProvider;
import com.google.common.util.concurrent.D0;
import j.X;
import java.util.concurrent.Executor;

/* compiled from: Encoder.java */
@X
/* renamed from: androidx.camera.video.internal.encoder.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20169k {

    /* compiled from: Encoder.java */
    /* renamed from: androidx.camera.video.internal.encoder.k$a */
    public interface a extends b, BufferProvider<E> {
    }

    /* compiled from: Encoder.java */
    /* renamed from: androidx.camera.video.internal.encoder.k$b */
    public interface b {
    }

    /* compiled from: Encoder.java */
    /* renamed from: androidx.camera.video.internal.encoder.k$c */
    public interface c extends b {

        /* compiled from: Encoder.java */
        /* renamed from: androidx.camera.video.internal.encoder.k$c$a */
        public interface a {
        }

        void a(@j.N Executor executor, @j.N i0 i0Var);
    }

    void a(@j.N InterfaceC20170l interfaceC20170l, @j.N Executor executor);

    @j.N
    D0<Void> b();

    int c();

    void d(long j12);

    void e();

    @j.N
    D getEncoderInfo();

    void pause();

    void release();

    void start();
}
