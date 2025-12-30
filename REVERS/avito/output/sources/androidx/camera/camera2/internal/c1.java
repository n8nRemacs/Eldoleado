package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.media.ImageWriter;
import android.view.Surface;

/* compiled from: ZslControlImpl.java */
/* loaded from: classes.dex */
class c1 extends CameraCaptureSession.StateCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ d1 f23120a;

    public c1(d1 d1Var) {
        this.f23120a = d1Var;
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigured(@j.N CameraCaptureSession cameraCaptureSession) {
        Surface inputSurface = cameraCaptureSession.getInputSurface();
        if (inputSurface != null) {
            this.f23120a.f23322j = ImageWriter.newInstance(inputSurface, 1);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigureFailed(@j.N CameraCaptureSession cameraCaptureSession) {
    }
}
