package androidx.arch.core.executor;

import android.os.Looper;
import androidx.annotation.RestrictTo;
import j.N;

/* compiled from: ArchTaskExecutor.java */
@RestrictTo
/* loaded from: classes.dex */
public class b extends d {

    /* renamed from: b, reason: collision with root package name */
    public static volatile b f22693b;

    /* renamed from: c, reason: collision with root package name */
    @N
    public static final a f22694c = new a(0);

    /* renamed from: a, reason: collision with root package name */
    @N
    public final c f22695a = new c();

    @N
    public static b a() {
        if (f22693b != null) {
            return f22693b;
        }
        synchronized (b.class) {
            try {
                if (f22693b == null) {
                    f22693b = new b();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return f22693b;
    }

    public final void b(@N Runnable runnable) {
        c cVar = this.f22695a;
        if (cVar.f22698c == null) {
            synchronized (cVar.f22696a) {
                try {
                    if (cVar.f22698c == null) {
                        cVar.f22698c = c.a(Looper.getMainLooper());
                    }
                } finally {
                }
            }
        }
        cVar.f22698c.post(runnable);
    }
}
