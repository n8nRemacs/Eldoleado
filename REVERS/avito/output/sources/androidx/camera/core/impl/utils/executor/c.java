package androidx.camera.core.impl.utils.executor;

import android.os.Handler;
import android.os.Looper;
import j.N;
import j.X;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: CameraXExecutors.java */
@X
/* loaded from: classes.dex */
public final class c {
    @N
    public static Executor a() {
        if (d.f24212b != null) {
            return d.f24212b;
        }
        synchronized (d.class) {
            try {
                if (d.f24212b == null) {
                    d.f24212b = new d();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return d.f24212b;
    }

    @N
    public static Executor b() {
        if (f.f24223c != null) {
            return f.f24223c;
        }
        synchronized (f.class) {
            try {
                if (f.f24223c == null) {
                    f.f24223c = new f();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return f.f24223c;
    }

    @N
    public static Executor c() {
        if (g.f24225c != null) {
            return g.f24225c;
        }
        synchronized (g.class) {
            try {
                if (g.f24225c == null) {
                    g.f24225c = new g();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return g.f24225c;
    }

    @N
    public static ScheduledExecutorService d() {
        if (h.f24228a != null) {
            return h.f24228a;
        }
        synchronized (h.class) {
            try {
                if (h.f24228a == null) {
                    h.f24228a = new e(new Handler(Looper.getMainLooper()));
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return h.f24228a;
    }

    @N
    public static ScheduledExecutorService e(@N Handler handler) {
        return new e(handler);
    }

    @N
    public static Executor f(@N Executor executor) {
        return new i(executor);
    }
}
