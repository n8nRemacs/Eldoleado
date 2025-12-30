package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import java.util.List;

/* compiled from: CameraSelectionOptimizer.java */
@j.X
/* loaded from: classes.dex */
class V {
    public static String a(androidx.camera.camera2.internal.compat.w wVar, Integer num, List<String> list) {
        if (num == null || !list.contains("0") || !list.contains("1")) {
            return null;
        }
        if (num.intValue() == 1) {
            if (((Integer) wVar.b("0").a(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
                return "1";
            }
            return null;
        }
        if (num.intValue() == 0 && ((Integer) wVar.b("1").a(CameraCharacteristics.LENS_FACING)).intValue() == 0) {
            return "0";
        }
        return null;
    }
}
