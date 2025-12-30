package androidx.camera.core;

import androidx.camera.core.impl.InterfaceC20117y;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: CameraExecutor.java */
@j.X
/* loaded from: classes.dex */
class r implements Executor {

    /* renamed from: d, reason: collision with root package name */
    public static final ThreadFactory f24514d = new a();

    /* renamed from: b, reason: collision with root package name */
    public final Object f24515b = new Object();

    /* renamed from: c, reason: collision with root package name */
    @j.B
    @j.N
    public ThreadPoolExecutor f24516c;

    /* compiled from: CameraExecutor.java */
    public class a implements ThreadFactory {

        /* renamed from: b, reason: collision with root package name */
        public final AtomicInteger f24517b = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(@j.N Runnable runnable) {
            Thread thread = new Thread(runnable);
            Locale locale = Locale.US;
            thread.setName("CameraX-core_camera_" + this.f24517b.getAndIncrement());
            return thread;
        }
    }

    public r() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), f24514d);
        threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandlerC20139q());
        this.f24516c = threadPoolExecutor;
    }

    public final void a(@j.N InterfaceC20117y interfaceC20117y) {
        ThreadPoolExecutor threadPoolExecutor;
        interfaceC20117y.getClass();
        synchronized (this.f24515b) {
            try {
                if (this.f24516c.isShutdown()) {
                    ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), f24514d);
                    threadPoolExecutor2.setRejectedExecutionHandler(new RejectedExecutionHandlerC20139q());
                    this.f24516c = threadPoolExecutor2;
                }
                threadPoolExecutor = this.f24516c;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        int iMax = Math.max(1, interfaceC20117y.c().size());
        threadPoolExecutor.setMaximumPoolSize(iMax);
        threadPoolExecutor.setCorePoolSize(iMax);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(@j.N Runnable runnable) {
        runnable.getClass();
        synchronized (this.f24515b) {
            this.f24516c.execute(runnable);
        }
    }
}
