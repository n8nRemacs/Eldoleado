package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.CameraCaptureFailure;

/* compiled from: CaptureCallbackAdapter.java */
@j.X
/* loaded from: classes.dex */
final class Y extends CameraCaptureSession.CaptureCallback {

    /* renamed from: a, reason: collision with root package name */
    public final AbstractC20109p f23088a;

    public Y(AbstractC20109p abstractC20109p) {
        if (abstractC20109p == null) {
            throw new NullPointerException("cameraCaptureCallback is null");
        }
        this.f23088a = abstractC20109p;
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
        androidx.camera.core.impl.J0 j02;
        super.onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
        Object tag = captureRequest.getTag();
        if (tag != null) {
            androidx.core.util.z.a("The tagBundle object from the CaptureResult is not a TagBundle object.", tag instanceof androidx.camera.core.impl.J0);
            j02 = (androidx.camera.core.impl.J0) tag;
        } else {
            j02 = androidx.camera.core.impl.J0.f23986b;
        }
        this.f23088a.b(new C20010h(j02, totalCaptureResult));
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureFailed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureFailure captureFailure) {
        super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
        this.f23088a.c(new CameraCaptureFailure());
    }
}
