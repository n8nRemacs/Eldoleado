package androidx.camera.core.impl.utils.futures;

import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: ChainingListenableFuture.java */
@X
/* loaded from: classes.dex */
class b<I, O> extends d<O> implements Runnable {

    /* renamed from: d, reason: collision with root package name */
    @P
    public androidx.camera.core.impl.utils.futures.a<? super I, ? extends O> f24243d;

    /* renamed from: e, reason: collision with root package name */
    public final LinkedBlockingQueue f24244e = new LinkedBlockingQueue(1);

    /* renamed from: f, reason: collision with root package name */
    public final CountDownLatch f24245f = new CountDownLatch(1);

    /* renamed from: g, reason: collision with root package name */
    @P
    public D0<? extends I> f24246g;

    /* renamed from: h, reason: collision with root package name */
    @P
    public volatile D0<? extends O> f24247h;

    /* compiled from: ChainingListenableFuture.java */
    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ D0 f24248b;

        public a(D0 d02) {
            this.f24248b = d02;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                try {
                    b bVar = b.this;
                    Object objD = f.d(this.f24248b);
                    b.a<V> aVar = bVar.f24251c;
                    if (aVar != 0) {
                        aVar.b(objD);
                    }
                } catch (CancellationException unused) {
                    b.this.cancel(false);
                    b.this.f24247h = null;
                    return;
                } catch (ExecutionException e12) {
                    b bVar2 = b.this;
                    Throwable cause = e12.getCause();
                    b.a<V> aVar2 = bVar2.f24251c;
                    if (aVar2 != 0) {
                        aVar2.d(cause);
                    }
                }
                b.this.f24247h = null;
            } catch (Throwable th2) {
                b.this.f24247h = null;
                throw th2;
            }
        }
    }

    public b(@N androidx.camera.core.impl.utils.futures.a<? super I, ? extends O> aVar, @N D0<? extends I> d02) {
        this.f24243d = aVar;
        d02.getClass();
        this.f24246g = d02;
    }

    public static Object b(@N LinkedBlockingQueue linkedBlockingQueue) {
        Object objTake;
        boolean z12 = false;
        while (true) {
            try {
                objTake = linkedBlockingQueue.take();
                break;
            } catch (InterruptedException unused) {
                z12 = true;
            } catch (Throwable th2) {
                if (z12) {
                    Thread.currentThread().interrupt();
                }
                throw th2;
            }
        }
        if (z12) {
            Thread.currentThread().interrupt();
        }
        return objTake;
    }

    @Override // androidx.camera.core.impl.utils.futures.d, java.util.concurrent.Future
    public final boolean cancel(boolean z12) {
        boolean z13 = false;
        if (!this.f24250b.cancel(z12)) {
            return false;
        }
        while (true) {
            try {
                this.f24244e.put(Boolean.valueOf(z12));
                break;
            } catch (InterruptedException unused) {
                z13 = true;
            } catch (Throwable th2) {
                if (z13) {
                    Thread.currentThread().interrupt();
                }
                throw th2;
            }
        }
        if (z13) {
            Thread.currentThread().interrupt();
        }
        D0<? extends I> d02 = this.f24246g;
        if (d02 != null) {
            d02.cancel(z12);
        }
        D0<? extends O> d03 = this.f24247h;
        if (d03 != null) {
            d03.cancel(z12);
        }
        return true;
    }

    @Override // androidx.camera.core.impl.utils.futures.d, java.util.concurrent.Future
    @P
    public final O get() throws ExecutionException, InterruptedException {
        if (!this.f24250b.isDone()) {
            D0<? extends I> d02 = this.f24246g;
            if (d02 != null) {
                d02.get();
            }
            this.f24245f.await();
            D0<? extends O> d03 = this.f24247h;
            if (d03 != null) {
                d03.get();
            }
        }
        return (O) this.f24250b.get();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.camera.core.impl.utils.futures.a<? super I, ? extends O>, com.google.common.util.concurrent.D0<? extends I>] */
    @Override // java.lang.Runnable
    public final void run() {
        D0<? extends O> d0Apply;
        ?? r02 = (androidx.camera.core.impl.utils.futures.a<? super I, ? extends O>) null;
        try {
            try {
                try {
                    try {
                        try {
                            d0Apply = this.f24243d.apply(f.d(this.f24246g));
                            this.f24247h = d0Apply;
                        } catch (Exception e12) {
                            b.a<V> aVar = this.f24251c;
                            if (aVar != 0) {
                                aVar.d(e12);
                            }
                        }
                    } catch (Error e13) {
                        b.a<V> aVar2 = this.f24251c;
                        if (aVar2 != 0) {
                            aVar2.d(e13);
                        }
                    }
                } finally {
                    this.f24243d = null;
                    this.f24246g = null;
                    this.f24245f.countDown();
                }
            } catch (CancellationException unused) {
                cancel(false);
            } catch (ExecutionException e14) {
                Throwable cause = e14.getCause();
                b.a<V> aVar3 = this.f24251c;
                if (aVar3 != 0) {
                    aVar3.d(cause);
                }
            }
        } catch (UndeclaredThrowableException e15) {
            Throwable cause2 = e15.getCause();
            b.a<V> aVar4 = this.f24251c;
            if (aVar4 != 0) {
                aVar4.d(cause2);
            }
        }
        if (!this.f24250b.isCancelled()) {
            d0Apply.N(new a(d0Apply), androidx.camera.core.impl.utils.executor.c.a());
        } else {
            d0Apply.cancel(((Boolean) b(this.f24244e)).booleanValue());
            this.f24247h = null;
        }
    }

    @Override // androidx.camera.core.impl.utils.futures.d, java.util.concurrent.Future
    @P
    public final O get(long j12, @N TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (!this.f24250b.isDone()) {
            TimeUnit timeUnit2 = TimeUnit.NANOSECONDS;
            if (timeUnit != timeUnit2) {
                j12 = timeUnit2.convert(j12, timeUnit);
                timeUnit = timeUnit2;
            }
            D0<? extends I> d02 = this.f24246g;
            if (d02 != null) {
                long jNanoTime = System.nanoTime();
                d02.get(j12, timeUnit);
                j12 -= Math.max(0L, System.nanoTime() - jNanoTime);
            }
            long jNanoTime2 = System.nanoTime();
            if (this.f24245f.await(j12, timeUnit)) {
                j12 -= Math.max(0L, System.nanoTime() - jNanoTime2);
                D0<? extends O> d03 = this.f24247h;
                if (d03 != null) {
                    d03.get(j12, timeUnit);
                }
            } else {
                throw new TimeoutException();
            }
        }
        return (O) this.f24250b.get(j12, timeUnit);
    }
}
