package androidx.camera.core.impl;

import android.os.Handler;
import java.util.concurrent.Executor;

/* compiled from: CameraThreadConfig.java */
@VY0.c
@j.X
/* loaded from: classes.dex */
public abstract class F {
    @j.N
    public static F a(@j.N Executor executor, @j.N Handler handler) {
        return new C20085c(executor, handler);
    }

    @j.N
    public abstract Executor b();

    @j.N
    public abstract Handler c();
}
