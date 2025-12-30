package androidx.camera.core.impl.utils.executor;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.N;
import j.X;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: HandlerScheduledExecutorService.java */
@X
/* loaded from: classes.dex */
final class e extends AbstractExecutorService implements ScheduledExecutorService {

    /* renamed from: b, reason: collision with root package name */
    public final Handler f24213b;

    /* compiled from: HandlerScheduledExecutorService.java */
    public class a extends ThreadLocal<ScheduledExecutorService> {
        @Override // java.lang.ThreadLocal
        public final ScheduledExecutorService initialValue() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                return androidx.camera.core.impl.utils.executor.c.d();
            }
            if (Looper.myLooper() != null) {
                return new e(new Handler(Looper.myLooper()));
            }
            return null;
        }
    }

    /* compiled from: HandlerScheduledExecutorService.java */
    public class b implements Callable<Void> {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Runnable f24214b;

        public b(Runnable runnable) {
            this.f24214b = runnable;
        }

        @Override // java.util.concurrent.Callable
        public final Void call() {
            this.f24214b.run();
            return null;
        }
    }

    /* compiled from: HandlerScheduledExecutorService.java */
    public static class c<V> implements RunnableScheduledFuture<V> {

        /* renamed from: b, reason: collision with root package name */
        public final AtomicReference<b.a<V>> f24215b = new AtomicReference<>(null);

        /* renamed from: c, reason: collision with root package name */
        public final long f24216c;

        /* renamed from: d, reason: collision with root package name */
        public final Callable<V> f24217d;

        /* renamed from: e, reason: collision with root package name */
        public final D0<V> f24218e;

        /* compiled from: HandlerScheduledExecutorService.java */
        public class a implements b.c<V> {

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ Handler f24219b;

            /* renamed from: c, reason: collision with root package name */
            public final /* synthetic */ Callable f24220c;

            /* compiled from: HandlerScheduledExecutorService.java */
            /* renamed from: androidx.camera.core.impl.utils.executor.e$c$a$a, reason: collision with other inner class name */
            public class RunnableC1535a implements Runnable {
                public RunnableC1535a() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    a aVar = a.this;
                    if (c.this.f24215b.getAndSet(null) != null) {
                        aVar.f24219b.removeCallbacks(c.this);
                    }
                }
            }

            public a(Handler handler, Callable callable) {
                this.f24219b = handler;
                this.f24220c = callable;
            }

            @Override // androidx.concurrent.futures.b.c
            public final Object d(@N b.a<V> aVar) {
                aVar.a(new RunnableC1535a(), androidx.camera.core.impl.utils.executor.c.a());
                c.this.f24215b.set(aVar);
                return "HandlerScheduledFuture-" + this.f24220c.toString();
            }
        }

        public c(Handler handler, long j12, Callable<V> callable) {
            this.f24216c = j12;
            this.f24217d = callable;
            this.f24218e = androidx.concurrent.futures.b.a(new a(handler, callable));
        }

        @Override // java.util.concurrent.Future
        public final boolean cancel(boolean z12) {
            return this.f24218e.cancel(z12);
        }

        @Override // java.lang.Comparable
        public final int compareTo(Delayed delayed) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            return Long.compare(getDelay(timeUnit), delayed.getDelay(timeUnit));
        }

        @Override // java.util.concurrent.Future
        public final V get() {
            return this.f24218e.get();
        }

        @Override // java.util.concurrent.Delayed
        public final long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.f24216c - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override // java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.f24218e.isCancelled();
        }

        @Override // java.util.concurrent.Future
        public final boolean isDone() {
            return this.f24218e.isDone();
        }

        @Override // java.util.concurrent.RunnableScheduledFuture
        public final boolean isPeriodic() {
            return false;
        }

        @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
        public final void run() {
            b.a andSet = this.f24215b.getAndSet(null);
            if (andSet != null) {
                try {
                    andSet.b(this.f24217d.call());
                } catch (Exception e12) {
                    andSet.d(e12);
                }
            }
        }

        @Override // java.util.concurrent.Future
        public final V get(long j12, @N TimeUnit timeUnit) {
            return this.f24218e.get(j12, timeUnit);
        }
    }

    static {
        new a();
    }

    public e(@N Handler handler) {
        this.f24213b = handler;
    }

    @Override // java.util.concurrent.ExecutorService
    public final boolean awaitTermination(long j12, @N TimeUnit timeUnit) {
        throw new UnsupportedOperationException(e.class.getSimpleName().concat(" cannot be shut down. Use Looper.quitSafely()."));
    }

    @Override // java.util.concurrent.Executor
    public final void execute(@N Runnable runnable) {
        Handler handler = this.f24213b;
        if (handler.post(runnable)) {
            return;
        }
        throw new RejectedExecutionException(handler + " is shutting down");
    }

    @Override // java.util.concurrent.ExecutorService
    public final boolean isShutdown() {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public final boolean isTerminated() {
        return false;
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    public final ScheduledFuture<?> schedule(@N Runnable runnable, long j12, @N TimeUnit timeUnit) {
        return schedule(new b(runnable), j12, timeUnit);
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    @N
    public final ScheduledFuture<?> scheduleAtFixedRate(@N Runnable runnable, long j12, long j13, @N TimeUnit timeUnit) {
        throw new UnsupportedOperationException(e.class.getSimpleName().concat(" does not yet support fixed-rate scheduling."));
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    @N
    public final ScheduledFuture<?> scheduleWithFixedDelay(@N Runnable runnable, long j12, long j13, @N TimeUnit timeUnit) {
        throw new UnsupportedOperationException(e.class.getSimpleName().concat(" does not yet support fixed-delay scheduling."));
    }

    @Override // java.util.concurrent.ExecutorService
    public final void shutdown() {
        throw new UnsupportedOperationException(e.class.getSimpleName().concat(" cannot be shut down. Use Looper.quitSafely()."));
    }

    @Override // java.util.concurrent.ExecutorService
    @N
    public final List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException(e.class.getSimpleName().concat(" cannot be shut down. Use Looper.quitSafely()."));
    }

    @Override // java.util.concurrent.ScheduledExecutorService
    @N
    public final <V> ScheduledFuture<V> schedule(@N Callable<V> callable, long j12, @N TimeUnit timeUnit) {
        long jConvert = TimeUnit.MILLISECONDS.convert(j12, timeUnit) + SystemClock.uptimeMillis();
        Handler handler = this.f24213b;
        c cVar = new c(handler, jConvert, callable);
        if (handler.postAtTime(cVar, jConvert)) {
            return cVar;
        }
        return androidx.camera.core.impl.utils.futures.f.f(new RejectedExecutionException(handler + " is shutting down"));
    }
}
