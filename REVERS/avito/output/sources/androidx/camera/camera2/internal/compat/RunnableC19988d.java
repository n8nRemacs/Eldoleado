package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.camera2.internal.compat.C19986b;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.compat.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC19988d implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23142b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C19986b.C1530b f23143c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ CameraCaptureSession f23144d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ CaptureRequest f23145e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ Object f23146f;

    public /* synthetic */ RunnableC19988d(C19986b.C1530b c1530b, CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, Object obj, int i12) {
        this.f23142b = i12;
        this.f23143c = c1530b;
        this.f23144d = cameraCaptureSession;
        this.f23145e = captureRequest;
        this.f23146f = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23142b) {
            case 0:
                this.f23143c.f23133a.onCaptureCompleted(this.f23144d, this.f23145e, (TotalCaptureResult) this.f23146f);
                break;
            case 1:
                this.f23143c.f23133a.onCaptureProgressed(this.f23144d, this.f23145e, (CaptureResult) this.f23146f);
                break;
            default:
                this.f23143c.f23133a.onCaptureFailed(this.f23144d, this.f23145e, (CaptureFailure) this.f23146f);
                break;
        }
    }
}
