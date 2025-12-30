package androidx.camera.core.impl.utils.executor;

import j.N;
import j.X;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: IoExecutor.java */
@X
/* loaded from: classes.dex */
final class g implements Executor {

    /* renamed from: c, reason: collision with root package name */
    public static volatile Executor f24225c;

    /* renamed from: b, reason: collision with root package name */
    public final ExecutorService f24226b = Executors.newFixedThreadPool(2, new a());

    /* compiled from: IoExecutor.java */
    public class a implements ThreadFactory {

        /* renamed from: b, reason: collision with root package name */
        public final AtomicInteger f24227b = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            Locale locale = Locale.US;
            thread.setName("CameraX-camerax_io_" + this.f24227b.getAndIncrement());
            return thread;
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(@N Runnable runnable) {
        this.f24226b.execute(runnable);
    }
}
