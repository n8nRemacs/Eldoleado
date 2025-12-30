package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import j.N;
import j.X;
import j.a0;
import java.util.concurrent.Executor;

/* compiled from: CameraManagerCompatApi29Impl.java */
@X
/* loaded from: classes.dex */
class y extends x {
    @Override // androidx.camera.camera2.internal.compat.x, androidx.camera.camera2.internal.compat.A, androidx.camera.camera2.internal.compat.w.b
    @a0
    public final void a(@N String str, @N Executor executor, @N CameraDevice.StateCallback stateCallback) throws CameraAccessException, CameraAccessExceptionCompat {
        try {
            this.f23121a.openCamera(str, executor, stateCallback);
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.x, androidx.camera.camera2.internal.compat.A, androidx.camera.camera2.internal.compat.w.b
    @N
    public final CameraCharacteristics b(@N String str) throws CameraAccessExceptionCompat {
        try {
            return this.f23121a.getCameraCharacteristics(str);
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }
}
