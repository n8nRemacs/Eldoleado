package androidx.camera.extensions.internal.sessionprocessor;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.y0;
import j.N;

/* compiled from: BasicExtenderSessionProcessor.java */
/* loaded from: classes.dex */
class g implements y0.a {

    /* renamed from: a, reason: collision with root package name */
    public boolean f24639a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f24640b;

    public g() {
        throw null;
    }

    @Override // androidx.camera.core.impl.y0.a
    public final void onCaptureCompleted(@N y0.b bVar, @N androidx.camera.core.impl.r rVar) {
        CaptureResult captureResultB = androidx.camera.camera2.impl.a.b(rVar);
        androidx.core.util.z.a("Cannot get capture TotalCaptureResult from the cameraCaptureResult ", captureResultB instanceof TotalCaptureResult);
        throw null;
    }

    @Override // androidx.camera.core.impl.y0.a
    public final void onCaptureFailed(@N y0.b bVar, @N CameraCaptureFailure cameraCaptureFailure) {
        if (this.f24639a) {
            return;
        }
        this.f24639a = true;
        throw null;
    }

    @Override // androidx.camera.core.impl.y0.a
    public final void onCaptureSequenceAborted(int i12) {
        throw null;
    }

    @Override // androidx.camera.core.impl.y0.a
    public final void onCaptureStarted(@N y0.b bVar, long j12, long j13) {
        if (this.f24640b) {
            return;
        }
        this.f24640b = true;
        throw null;
    }
}
