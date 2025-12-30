package androidx.camera.core.impl;

import java.util.concurrent.Executor;

/* compiled from: Observable.java */
@j.X
/* loaded from: classes.dex */
public interface q0<T> {

    /* compiled from: Observable.java */
    public interface a<T> {
        void a(@j.P T t12);

        void onError(@j.N Throwable th2);
    }

    void b(@j.N a<? super T> aVar);

    @j.N
    com.google.common.util.concurrent.D0<T> c();

    void d(@j.N Executor executor, @j.N a<? super T> aVar);
}
