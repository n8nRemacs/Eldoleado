package androidx.camera.camera2.impl;

import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureResult;
import androidx.camera.camera2.internal.C20008g;
import androidx.camera.camera2.internal.C20010h;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.r;
import j.N;
import j.P;
import j.X;

/* compiled from: Camera2CameraCaptureResultConverter.java */
@X
/* loaded from: classes.dex */
public final class a {
    @P
    public static CaptureFailure a(@N CameraCaptureFailure cameraCaptureFailure) {
        if (cameraCaptureFailure instanceof C20008g) {
            return ((C20008g) cameraCaptureFailure).f23326a;
        }
        return null;
    }

    @P
    public static CaptureResult b(@P r rVar) {
        if (rVar instanceof C20010h) {
            return ((C20010h) rVar).f23336b;
        }
        return null;
    }
}
