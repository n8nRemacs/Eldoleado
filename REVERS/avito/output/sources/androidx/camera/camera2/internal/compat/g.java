package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import androidx.camera.camera2.internal.compat.C19986b;
import androidx.camera.camera2.internal.compat.o;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class g implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23156b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f23157c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23158d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ AutoCloseable f23159e;

    public /* synthetic */ g(Object obj, AutoCloseable autoCloseable, int i12, int i13) {
        this.f23156b = i13;
        this.f23158d = obj;
        this.f23159e = autoCloseable;
        this.f23157c = i12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23156b) {
            case 0:
                ((C19986b.C1530b) this.f23158d).f23133a.onCaptureSequenceAborted((CameraCaptureSession) this.f23159e, this.f23157c);
                break;
            default:
                ((o.b) this.f23158d).f23175a.onError((CameraDevice) this.f23159e, this.f23157c);
                break;
        }
    }
}
