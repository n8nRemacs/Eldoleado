package androidx.arch.core.executor;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RestrictTo;
import j.N;
import j.P;
import j.X;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: DefaultTaskExecutor.java */
@RestrictTo
/* loaded from: classes.dex */
public class c extends d {

    /* renamed from: a, reason: collision with root package name */
    public final Object f22696a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public final ExecutorService f22697b = Executors.newFixedThreadPool(4, new a());

    /* renamed from: c, reason: collision with root package name */
    @P
    public volatile Handler f22698c;

    /* compiled from: DefaultTaskExecutor.java */
    public class a implements ThreadFactory {

        /* renamed from: b, reason: collision with root package name */
        public final AtomicInteger f22699b = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("arch_disk_io_" + this.f22699b.getAndIncrement());
            return thread;
        }
    }

    /* compiled from: DefaultTaskExecutor.java */
    @X
    public static class b {
        @N
        public static Handler a(@N Looper looper) {
            return Handler.createAsync(looper);
        }
    }

    @N
    public static Handler a(@N Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return b.a(looper);
        }
        try {
            return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException unused) {
            return new Handler(looper);
        } catch (InvocationTargetException unused2) {
            return new Handler(looper);
        }
    }
}
