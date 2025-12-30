package androidx.camera.core.impl.utils;

import androidx.camera.core.C20140q0;
import j.X;

/* compiled from: CameraOrientationUtil.java */
@X
/* loaded from: classes.dex */
public final class e {
    public static int a(int i12, int i13, boolean z12) {
        int i14 = z12 ? ((i13 - i12) + 360) % 360 : (i13 + i12) % 360;
        if (C20140q0.d(3, "CameraOrientationUtil")) {
            StringBuilder sbY = androidx.appcompat.app.r.y(i12, i13, "getRelativeImageRotation: destRotationDegrees=", ", sourceRotationDegrees=", ", isOppositeFacing=");
            sbY.append(z12);
            sbY.append(", result=");
            sbY.append(i14);
            C20140q0.d(3, "CameraOrientationUtil");
        }
        return i14;
    }

    public static int b(int i12) {
        if (i12 == 0) {
            return 0;
        }
        if (i12 == 1) {
            return 90;
        }
        if (i12 == 2) {
            return 180;
        }
        if (i12 == 3) {
            return 270;
        }
        throw new IllegalArgumentException(AK.c.g(i12, "Unsupported surface rotation: "));
    }
}
