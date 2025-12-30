package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.C19985a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: CameraCaptureSessionStateCallbacks.java */
@j.X
/* loaded from: classes.dex */
public final class S {

    /* compiled from: CameraCaptureSessionStateCallbacks.java */
    @j.X
    public static final class a extends CameraCaptureSession.StateCallback {

        /* renamed from: a, reason: collision with root package name */
        public final ArrayList f23066a = new ArrayList();

        public a(@j.N List<CameraCaptureSession.StateCallback> list) {
            for (CameraCaptureSession.StateCallback stateCallback : list) {
                if (!(stateCallback instanceof b)) {
                    this.f23066a.add(stateCallback);
                }
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onActive(@j.N CameraCaptureSession cameraCaptureSession) {
            Iterator it = this.f23066a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.StateCallback) it.next()).onActive(cameraCaptureSession);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        @j.X
        public final void onCaptureQueueEmpty(@j.N CameraCaptureSession cameraCaptureSession) {
            Iterator it = this.f23066a.iterator();
            while (it.hasNext()) {
                C19985a.d.b((CameraCaptureSession.StateCallback) it.next(), cameraCaptureSession);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onClosed(@j.N CameraCaptureSession cameraCaptureSession) {
            Iterator it = this.f23066a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.StateCallback) it.next()).onClosed(cameraCaptureSession);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onConfigureFailed(@j.N CameraCaptureSession cameraCaptureSession) {
            Iterator it = this.f23066a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.StateCallback) it.next()).onConfigureFailed(cameraCaptureSession);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onConfigured(@j.N CameraCaptureSession cameraCaptureSession) {
            Iterator it = this.f23066a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.StateCallback) it.next()).onConfigured(cameraCaptureSession);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onReady(@j.N CameraCaptureSession cameraCaptureSession) {
            Iterator it = this.f23066a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.StateCallback) it.next()).onReady(cameraCaptureSession);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        @j.X
        public final void onSurfacePrepared(@j.N CameraCaptureSession cameraCaptureSession, @j.N Surface surface) {
            Iterator it = this.f23066a.iterator();
            while (it.hasNext()) {
                C19985a.b.a((CameraCaptureSession.StateCallback) it.next(), cameraCaptureSession, surface);
            }
        }
    }

    /* compiled from: CameraCaptureSessionStateCallbacks.java */
    public static final class b extends CameraCaptureSession.StateCallback {
        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onActive(@j.N CameraCaptureSession cameraCaptureSession) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onCaptureQueueEmpty(@j.N CameraCaptureSession cameraCaptureSession) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onClosed(@j.N CameraCaptureSession cameraCaptureSession) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onConfigureFailed(@j.N CameraCaptureSession cameraCaptureSession) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onConfigured(@j.N CameraCaptureSession cameraCaptureSession) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onReady(@j.N CameraCaptureSession cameraCaptureSession) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onSurfacePrepared(@j.N CameraCaptureSession cameraCaptureSession, @j.N Surface surface) {
        }
    }
}
