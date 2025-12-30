package androidx.camera.core.internal;

import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.x0;
import j.X;
import java.util.concurrent.Executor;

/* compiled from: ThreadConfig.java */
@X
/* loaded from: classes.dex */
public interface j extends x0 {

    /* renamed from: D, reason: collision with root package name */
    public static final Config.a<Executor> f24363D = Config.a.a(Executor.class, "camerax.core.thread.backgroundExecutor");

    /* compiled from: ThreadConfig.java */
    public interface a<B> {
    }
}
