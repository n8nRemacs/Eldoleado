package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: CameraCaptureSessionCompatApi28Impl.java */
@X
/* loaded from: classes.dex */
class j extends k {
    @Override // androidx.camera.camera2.internal.compat.k, androidx.camera.camera2.internal.compat.C19986b.a
    public final int a(@N ArrayList arrayList, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback) {
        return this.f23166a.captureBurstRequests(arrayList, executor, captureCallback);
    }

    @Override // androidx.camera.camera2.internal.compat.k, androidx.camera.camera2.internal.compat.C19986b.a
    public final int b(@N CaptureRequest captureRequest, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback) {
        return this.f23166a.setSingleRepeatingRequest(captureRequest, executor, captureCallback);
    }
}
