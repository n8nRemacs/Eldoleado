package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import android.os.Handler;
import androidx.camera.core.impl.DeferrableSurface;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: SynchronizedCaptureSessionOpener.java */
@j.X
/* loaded from: classes.dex */
final class Q0 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final N0 f23056a;

    /* compiled from: SynchronizedCaptureSessionOpener.java */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final Executor f23057a;

        /* renamed from: b, reason: collision with root package name */
        public final ScheduledExecutorService f23058b;

        /* renamed from: c, reason: collision with root package name */
        public final Handler f23059c;

        /* renamed from: d, reason: collision with root package name */
        public final C20009g0 f23060d;

        /* renamed from: e, reason: collision with root package name */
        public final androidx.camera.core.impl.w0 f23061e;

        /* renamed from: f, reason: collision with root package name */
        public final androidx.camera.core.impl.w0 f23062f;

        /* renamed from: g, reason: collision with root package name */
        public final boolean f23063g;

        public a(@j.N Handler handler, @j.N C20009g0 c20009g0, @j.N androidx.camera.core.impl.w0 w0Var, @j.N androidx.camera.core.impl.w0 w0Var2, @j.N Executor executor, @j.N ScheduledExecutorService scheduledExecutorService) {
            this.f23057a = executor;
            this.f23058b = scheduledExecutorService;
            this.f23059c = handler;
            this.f23060d = c20009g0;
            this.f23061e = w0Var;
            this.f23062f = w0Var2;
            androidx.camera.camera2.internal.compat.workaround.i iVar = new androidx.camera.camera2.internal.compat.workaround.i(w0Var, w0Var2);
            this.f23063g = iVar.f23254a || iVar.f23255b || iVar.f23256c || new androidx.camera.camera2.internal.compat.workaround.w(w0Var).f23276a || new androidx.camera.camera2.internal.compat.workaround.h(w0Var2).f23253a != null;
        }

        @j.N
        public final Q0 a() {
            N0 n02;
            boolean z12 = this.f23063g;
            Executor executor = this.f23057a;
            C20009g0 c20009g0 = this.f23060d;
            ScheduledExecutorService scheduledExecutorService = this.f23058b;
            if (z12) {
                n02 = new P0(this.f23059c, c20009g0, this.f23061e, this.f23062f, executor, scheduledExecutorService);
            } else {
                n02 = new N0(c20009g0, executor, scheduledExecutorService, this.f23059c);
            }
            return new Q0(n02);
        }
    }

    /* compiled from: SynchronizedCaptureSessionOpener.java */
    public interface b {
        @j.N
        com.google.common.util.concurrent.D0<Void> e(@j.N CameraDevice cameraDevice, @j.N androidx.camera.camera2.internal.compat.params.l lVar, @j.N List<DeferrableSurface> list);

        @j.N
        com.google.common.util.concurrent.D0 f(@j.N ArrayList arrayList);

        boolean stop();
    }

    public Q0(@j.N N0 n02) {
        this.f23056a = n02;
    }
}
