package androidx.camera.core.impl.utils.executor;

import j.N;
import j.X;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* compiled from: HighPriorityExecutor.java */
@X
/* loaded from: classes.dex */
final class f implements Executor {

    /* renamed from: c, reason: collision with root package name */
    public static volatile Executor f24223c;

    /* renamed from: b, reason: collision with root package name */
    public final ExecutorService f24224b = Executors.newSingleThreadExecutor(new a());

    /* compiled from: HighPriorityExecutor.java */
    public class a implements ThreadFactory {
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setPriority(10);
            thread.setName("CameraX-camerax_high_priority");
            return thread;
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(@N Runnable runnable) {
        this.f24224b.execute(runnable);
    }
}
