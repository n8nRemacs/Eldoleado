package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import j.N;
import j.X;
import j.a0;
import java.util.concurrent.Executor;

/* compiled from: CameraManagerCompatApi28Impl.java */
@X
/* loaded from: classes.dex */
class x extends A {
    public static boolean g(@N RuntimeException runtimeException) {
        StackTraceElement[] stackTrace;
        if (Build.VERSION.SDK_INT == 28) {
            return (!runtimeException.getClass().equals(RuntimeException.class) || (stackTrace = runtimeException.getStackTrace()) == null || stackTrace.length < 0) ? false : "_enableShutterSound".equals(stackTrace[0].getMethodName());
        }
        return false;
    }

    @Override // androidx.camera.camera2.internal.compat.A, androidx.camera.camera2.internal.compat.w.b
    @a0
    public void a(@N String str, @N Executor executor, @N CameraDevice.StateCallback stateCallback) throws CameraAccessException, CameraAccessExceptionCompat {
        try {
            this.f23121a.openCamera(str, executor, stateCallback);
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        } catch (IllegalArgumentException e13) {
        } catch (SecurityException e14) {
            throw e14;
        } catch (RuntimeException e15) {
            if (!g(e15)) {
                throw e15;
            }
            throw new CameraAccessExceptionCompat(e15);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.A, androidx.camera.camera2.internal.compat.w.b
    @N
    public CameraCharacteristics b(@N String str) throws CameraAccessExceptionCompat {
        try {
            return super.b(str);
        } catch (RuntimeException e12) {
            if (g(e12)) {
                throw new CameraAccessExceptionCompat(e12);
            }
            throw e12;
        }
    }

    @Override // androidx.camera.camera2.internal.compat.A, androidx.camera.camera2.internal.compat.w.b
    public final void d(@N Executor executor, @N CameraManager.AvailabilityCallback availabilityCallback) {
        this.f23121a.registerAvailabilityCallback(executor, availabilityCallback);
    }

    @Override // androidx.camera.camera2.internal.compat.A, androidx.camera.camera2.internal.compat.w.b
    public final void f(@N CameraManager.AvailabilityCallback availabilityCallback) {
        this.f23121a.unregisterAvailabilityCallback(availabilityCallback);
    }
}
