package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.core.CameraUnavailableException;

/* compiled from: CameraUnavailableExceptionHelper.java */
@j.X
/* loaded from: classes.dex */
public final class X {
    @j.N
    public static CameraUnavailableException a(@j.N CameraAccessExceptionCompat cameraAccessExceptionCompat) {
        int i12 = cameraAccessExceptionCompat.f23130b;
        int i13 = 1;
        if (i12 != 1) {
            i13 = 2;
            if (i12 != 2) {
                i13 = 3;
                if (i12 != 3) {
                    i13 = 4;
                    if (i12 != 4) {
                        i13 = 5;
                        if (i12 != 5) {
                            i13 = i12 != 10001 ? 0 : 6;
                        }
                    }
                }
            }
        }
        return new CameraUnavailableException(i13, cameraAccessExceptionCompat);
    }
}
