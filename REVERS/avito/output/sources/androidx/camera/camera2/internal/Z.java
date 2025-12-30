package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import androidx.camera.core.impl.AbstractC20109p;

/* compiled from: CaptureCallbackContainer.java */
@j.X
/* loaded from: classes.dex */
final class Z extends AbstractC20109p {

    /* renamed from: a, reason: collision with root package name */
    public final CameraCaptureSession.CaptureCallback f23093a;

    public Z(CameraCaptureSession.CaptureCallback captureCallback) {
        if (captureCallback == null) {
            throw new NullPointerException("captureCallback is null");
        }
        this.f23093a = captureCallback;
    }
}
