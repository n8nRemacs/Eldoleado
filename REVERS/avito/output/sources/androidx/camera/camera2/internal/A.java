package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.C19985a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: Camera2CaptureCallbacks.java */
@j.X
/* loaded from: classes.dex */
public final class A {

    /* compiled from: Camera2CaptureCallbacks.java */
    @j.X
    public static final class a extends CameraCaptureSession.CaptureCallback {

        /* renamed from: a, reason: collision with root package name */
        public final ArrayList f22899a = new ArrayList();

        public a(List<CameraCaptureSession.CaptureCallback> list) {
            for (CameraCaptureSession.CaptureCallback captureCallback : list) {
                if (!(captureCallback instanceof b)) {
                    this.f22899a.add(captureCallback);
                }
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        @j.X
        public final void onCaptureBufferLost(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N Surface surface, long j12) {
            Iterator it = this.f22899a.iterator();
            while (it.hasNext()) {
                C19985a.c.a((CameraCaptureSession.CaptureCallback) it.next(), cameraCaptureSession, captureRequest, surface, j12);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
            Iterator it = this.f22899a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it.next()).onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureFailure captureFailure) {
            Iterator it = this.f22899a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it.next()).onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureProgressed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureResult captureResult) {
            Iterator it = this.f22899a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it.next()).onCaptureProgressed(cameraCaptureSession, captureRequest, captureResult);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(@j.N CameraCaptureSession cameraCaptureSession, int i12) {
            Iterator it = this.f22899a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it.next()).onCaptureSequenceAborted(cameraCaptureSession, i12);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(@j.N CameraCaptureSession cameraCaptureSession, int i12, long j12) {
            Iterator it = this.f22899a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it.next()).onCaptureSequenceCompleted(cameraCaptureSession, i12, j12);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureStarted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, long j12, long j13) {
            Iterator it = this.f22899a.iterator();
            while (it.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it.next()).onCaptureStarted(cameraCaptureSession, captureRequest, j12, j13);
            }
        }
    }

    /* compiled from: Camera2CaptureCallbacks.java */
    public static final class b extends CameraCaptureSession.CaptureCallback {
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(@j.N CameraCaptureSession cameraCaptureSession, int i12) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureFailure captureFailure) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureProgressed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureResult captureResult) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(@j.N CameraCaptureSession cameraCaptureSession, int i12, long j12) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureBufferLost(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N Surface surface, long j12) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureStarted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, long j12, long j13) {
        }
    }
}
