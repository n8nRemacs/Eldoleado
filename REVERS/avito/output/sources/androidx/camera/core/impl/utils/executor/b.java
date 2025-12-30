package androidx.camera.core.impl.utils.executor;

import j.N;
import j.X;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: AudioExecutor.java */
@X
/* loaded from: classes.dex */
public class b implements Executor {

    /* renamed from: c, reason: collision with root package name */
    public static volatile b f24209c;

    /* renamed from: b, reason: collision with root package name */
    public final ExecutorService f24210b = Executors.newFixedThreadPool(2, new a());

    /* compiled from: AudioExecutor.java */
    public class a implements ThreadFactory {

        /* renamed from: b, reason: collision with root package name */
        public final AtomicInteger f24211b = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(new androidx.camera.core.impl.utils.executor.a(0, runnable));
            Locale locale = Locale.US;
            thread.setName("CameraX-camerax_audio_" + this.f24211b.getAndIncrement());
            return thread;
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(@N Runnable runnable) {
        this.f24210b.execute(runnable);
    }
}
