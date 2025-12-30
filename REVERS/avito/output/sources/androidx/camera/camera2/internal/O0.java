package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.view.Surface;
import androidx.concurrent.futures.b;

/* compiled from: SynchronizedCaptureSessionBaseImpl.java */
/* loaded from: classes.dex */
class O0 extends CameraCaptureSession.StateCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ N0 f23046a;

    public O0(N0 n02) {
        this.f23046a = n02;
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onActive(@j.N CameraCaptureSession cameraCaptureSession) {
        N0 n02 = this.f23046a;
        n02.t(cameraCaptureSession);
        n02.l(n02);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    @j.X
    public final void onCaptureQueueEmpty(@j.N CameraCaptureSession cameraCaptureSession) {
        N0 n02 = this.f23046a;
        n02.t(cameraCaptureSession);
        n02.m(n02);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onClosed(@j.N CameraCaptureSession cameraCaptureSession) {
        N0 n02 = this.f23046a;
        n02.t(cameraCaptureSession);
        n02.n(n02);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigureFailed(@j.N CameraCaptureSession cameraCaptureSession) {
        b.a<Void> aVar;
        try {
            this.f23046a.t(cameraCaptureSession);
            N0 n02 = this.f23046a;
            n02.o(n02);
            synchronized (this.f23046a.f23030a) {
                androidx.core.util.z.f(this.f23046a.f23038i, "OpenCaptureSession completer should not null");
                N0 n03 = this.f23046a;
                aVar = n03.f23038i;
                n03.f23038i = null;
            }
            aVar.d(new IllegalStateException("onConfigureFailed"));
        } catch (Throwable th2) {
            synchronized (this.f23046a.f23030a) {
                androidx.core.util.z.f(this.f23046a.f23038i, "OpenCaptureSession completer should not null");
                N0 n04 = this.f23046a;
                b.a<Void> aVar2 = n04.f23038i;
                n04.f23038i = null;
                aVar2.d(new IllegalStateException("onConfigureFailed"));
                throw th2;
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigured(@j.N CameraCaptureSession cameraCaptureSession) {
        b.a<Void> aVar;
        try {
            this.f23046a.t(cameraCaptureSession);
            N0 n02 = this.f23046a;
            n02.p(n02);
            synchronized (this.f23046a.f23030a) {
                androidx.core.util.z.f(this.f23046a.f23038i, "OpenCaptureSession completer should not null");
                N0 n03 = this.f23046a;
                aVar = n03.f23038i;
                n03.f23038i = null;
            }
            aVar.b(null);
        } catch (Throwable th2) {
            synchronized (this.f23046a.f23030a) {
                androidx.core.util.z.f(this.f23046a.f23038i, "OpenCaptureSession completer should not null");
                N0 n04 = this.f23046a;
                b.a<Void> aVar2 = n04.f23038i;
                n04.f23038i = null;
                aVar2.b(null);
                throw th2;
            }
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onReady(@j.N CameraCaptureSession cameraCaptureSession) {
        N0 n02 = this.f23046a;
        n02.t(cameraCaptureSession);
        n02.q(n02);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    @j.X
    public final void onSurfacePrepared(@j.N CameraCaptureSession cameraCaptureSession, @j.N Surface surface) {
        N0 n02 = this.f23046a;
        n02.t(cameraCaptureSession);
        n02.s(n02, surface);
    }
}
