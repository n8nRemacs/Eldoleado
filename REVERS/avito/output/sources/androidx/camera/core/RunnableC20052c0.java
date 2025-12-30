package androidx.camera.core;

import android.os.Looper;
import androidx.camera.core.C20056e0;
import java.util.concurrent.Executor;
import ru.cyberity.cbr.core.domain.camera.CameraX;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.c0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20052c0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C20056e0 f23718b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C20056e0.l f23719c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Executor f23720d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ CameraX.d f23721e;

    public /* synthetic */ RunnableC20052c0(C20056e0 c20056e0, C20056e0.l lVar, Executor executor, CameraX.d dVar) {
        this.f23718b = c20056e0;
        this.f23719c = lVar;
        this.f23720d = executor;
        this.f23721e = dVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        C20056e0.d dVar = C20056e0.f23725w;
        C20056e0.l lVar = this.f23719c;
        CameraX.d dVar2 = this.f23721e;
        C20056e0 c20056e0 = this.f23718b;
        c20056e0.getClass();
        Looper mainLooper = Looper.getMainLooper();
        Looper looperMyLooper = Looper.myLooper();
        Executor executor = this.f23720d;
        if (mainLooper != looperMyLooper) {
            androidx.camera.core.impl.utils.executor.c.d().execute(new RunnableC20052c0(c20056e0, lVar, executor, dVar2));
        } else {
            c20056e0.L(executor, null, dVar2, lVar);
        }
    }
}
