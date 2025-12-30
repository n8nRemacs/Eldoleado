package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.SessionConfig;
import java.util.Collections;

/* compiled from: CaptureSession.java */
/* renamed from: androidx.camera.camera2.internal.e0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20005e0 extends CameraCaptureSession.CaptureCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C20003d0 f23325a;

    public C20005e0(C20003d0 c20003d0) {
        this.f23325a = c20003d0;
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
        synchronized (this.f23325a.f23283a) {
            try {
                SessionConfig sessionConfig = this.f23325a.f23289g;
                if (sessionConfig == null) {
                    return;
                }
                androidx.camera.core.impl.H h12 = sessionConfig.f24017f;
                C20140q0.d(3, "CaptureSession");
                C20003d0 c20003d0 = this.f23325a;
                c20003d0.f23299q.getClass();
                c20003d0.b(Collections.singletonList(androidx.camera.camera2.internal.compat.workaround.t.a(h12)));
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
