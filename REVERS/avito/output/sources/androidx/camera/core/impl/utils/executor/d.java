package androidx.camera.core.impl.utils.executor;

import j.X;
import java.util.concurrent.Executor;

/* compiled from: DirectExecutor.java */
@X
/* loaded from: classes.dex */
final class d implements Executor {

    /* renamed from: b, reason: collision with root package name */
    public static volatile d f24212b;

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
