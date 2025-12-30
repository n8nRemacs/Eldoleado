package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCaptureSession;
import androidx.camera.camera2.internal.compat.C19985a;
import androidx.camera.camera2.internal.compat.C19986b;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class h implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23160b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C19986b.c f23161c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ CameraCaptureSession f23162d;

    public /* synthetic */ h(C19986b.c cVar, CameraCaptureSession cameraCaptureSession, int i12) {
        this.f23160b = i12;
        this.f23161c = cVar;
        this.f23162d = cameraCaptureSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23160b) {
            case 0:
                this.f23161c.f23135a.onActive(this.f23162d);
                break;
            case 1:
                this.f23161c.f23135a.onClosed(this.f23162d);
                break;
            case 2:
                C19985a.d.b(this.f23161c.f23135a, this.f23162d);
                break;
            case 3:
                this.f23161c.f23135a.onConfigured(this.f23162d);
                break;
            case 4:
                this.f23161c.f23135a.onReady(this.f23162d);
                break;
            default:
                this.f23161c.f23135a.onConfigureFailed(this.f23162d);
                break;
        }
    }
}
