package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.core.C20140q0;
import j.N;
import j.X;
import java.nio.BufferUnderflowException;

/* compiled from: FlashAvailabilityChecker.java */
@X
/* loaded from: classes.dex */
public final class g {
    public static boolean a(@N c cVar) {
        Boolean bool;
        try {
            bool = (Boolean) cVar.b(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        } catch (BufferUnderflowException unused) {
            if (C20001l.f23216a.b(androidx.camera.camera2.internal.compat.quirk.r.class) != null) {
                String.format("Device is known to throw an exception while checking flash availability. Flash is not available. [Manufacturer: %s, Model: %s, API Level: %d].", Build.MANUFACTURER, Build.MODEL, Integer.valueOf(Build.VERSION.SDK_INT));
                C20140q0.d(3, "FlashAvailability");
            } else {
                String.format("Exception thrown while checking for flash availability on device not known to throw exceptions during this check. Please file an issue at https://issuetracker.google.com/issues/new?component=618491&template=1257717 with this error message [Manufacturer: %s, Model: %s, API Level: %d].\nFlash is not available.", Build.MANUFACTURER, Build.MODEL, Integer.valueOf(Build.VERSION.SDK_INT));
                C20140q0.c("FlashAvailability");
            }
            bool = Boolean.FALSE;
        }
        if (bool == null) {
            C20140q0.d(5, "FlashAvailability");
        }
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }
}
