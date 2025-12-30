package androidx.camera.core.impl.utils.futures;

import androidx.appcompat.app.r;
import androidx.camera.core.C20140q0;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: ImmediateFuture.java */
@X
/* loaded from: classes.dex */
abstract class g<V> implements D0<V> {

    /* compiled from: ImmediateFuture.java */
    public static class a<V> extends g<V> {

        /* renamed from: b, reason: collision with root package name */
        @N
        public final Throwable f24261b;

        public a(@N Throwable th2) {
            this.f24261b = th2;
        }

        @Override // java.util.concurrent.Future
        @P
        public final V get() throws ExecutionException {
            throw new ExecutionException(this.f24261b);
        }

        @N
        public final String toString() {
            return super.toString() + "[status=FAILURE, cause=[" + this.f24261b + "]]";
        }
    }

    /* compiled from: ImmediateFuture.java */
    public static final class b<V> extends a<V> implements ScheduledFuture<V> {
        @Override // java.lang.Comparable
        public final /* bridge */ /* synthetic */ int compareTo(@N Delayed delayed) {
            return -1;
        }

        @Override // java.util.concurrent.Delayed
        public final long getDelay(@N TimeUnit timeUnit) {
            return 0L;
        }
    }

    /* compiled from: ImmediateFuture.java */
    public static final class c<V> extends g<V> {

        /* renamed from: c, reason: collision with root package name */
        public static final c f24262c = new c(null);

        /* renamed from: b, reason: collision with root package name */
        @P
        public final V f24263b;

        public c(@P V v12) {
            this.f24263b = v12;
        }

        @Override // java.util.concurrent.Future
        @P
        public final V get() {
            return this.f24263b;
        }

        public final String toString() {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(super.toString());
            sb2.append("[status=SUCCESS, result=[");
            return r.o(this.f24263b, "]]", sb2);
        }
    }

    @Override // com.google.common.util.concurrent.D0
    public final void N(@N Runnable runnable, @N Executor executor) {
        executor.getClass();
        try {
            executor.execute(runnable);
        } catch (RuntimeException unused) {
            runnable.toString();
            executor.toString();
            C20140q0.c("ImmediateFuture");
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z12) {
        return false;
    }

    @Override // java.util.concurrent.Future
    @P
    public final V get(long j12, @N TimeUnit timeUnit) {
        timeUnit.getClass();
        return get();
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return true;
    }
}
