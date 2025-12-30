package androidx.camera.core;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.q, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RejectedExecutionHandlerC20139q implements RejectedExecutionHandler {
    @Override // java.util.concurrent.RejectedExecutionHandler
    public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        ThreadFactory threadFactory = r.f24514d;
        C20140q0.b("CameraExecutor");
    }
}
