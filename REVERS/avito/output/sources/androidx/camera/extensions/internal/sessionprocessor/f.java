package androidx.camera.extensions.internal.sessionprocessor;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.core.impl.y0;
import j.N;

/* compiled from: BasicExtenderSessionProcessor.java */
/* loaded from: classes.dex */
class f implements y0.a {
    public f() {
        throw null;
    }

    @Override // androidx.camera.core.impl.y0.a
    public final void onCaptureCompleted(@N y0.b bVar, @N androidx.camera.core.impl.r rVar) {
        CaptureResult captureResultB = androidx.camera.camera2.impl.a.b(rVar);
        androidx.core.util.z.a("Cannot get TotalCaptureResult from the cameraCaptureResult ", captureResultB instanceof TotalCaptureResult);
        throw null;
    }
}
