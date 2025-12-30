package androidx.camera.core.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.camera.core.C20140q0;
import androidx.camera.core.C20144v;

@j.X
/* loaded from: classes.dex */
public final class CameraValidator {

    public static class CameraIdListIncorrectException extends Exception {
    }

    public static void a(@j.N Context context, @j.N D d12, @j.P C20144v c20144v) throws CameraIdListIncorrectException {
        Integer numC;
        if (c20144v != null) {
            try {
                numC = c20144v.c();
                if (numC == null) {
                    C20140q0.d(5, "CameraValidator");
                    return;
                }
            } catch (IllegalStateException unused) {
                C20140q0.c("CameraValidator");
                return;
            }
        } else {
            numC = null;
        }
        String str = Build.DEVICE;
        C20140q0.d(3, "CameraValidator");
        PackageManager packageManager = context.getPackageManager();
        try {
            if (packageManager.hasSystemFeature("android.hardware.camera") && (c20144v == null || numC.intValue() == 1)) {
                C20144v.f24563c.d(d12.a());
            }
            if (packageManager.hasSystemFeature("android.hardware.camera.front")) {
                if (c20144v == null || numC.intValue() == 0) {
                    C20144v.f24562b.d(d12.a());
                }
            }
        } catch (IllegalArgumentException e12) {
            d12.a().toString();
            C20140q0.b("CameraValidator");
            throw new CameraIdListIncorrectException("Expected camera missing from device.", e12);
        }
    }
}
