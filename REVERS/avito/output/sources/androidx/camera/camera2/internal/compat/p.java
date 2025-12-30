package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraDevice;
import androidx.camera.camera2.internal.compat.o;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class p implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23177b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ o.b f23178c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ CameraDevice f23179d;

    public /* synthetic */ p(o.b bVar, CameraDevice cameraDevice, int i12) {
        this.f23177b = i12;
        this.f23178c = bVar;
        this.f23179d = cameraDevice;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23177b) {
            case 0:
                this.f23178c.f23175a.onClosed(this.f23179d);
                break;
            case 1:
                this.f23178c.f23175a.onDisconnected(this.f23179d);
                break;
            default:
                this.f23178c.f23175a.onOpened(this.f23179d);
                break;
        }
    }
}
