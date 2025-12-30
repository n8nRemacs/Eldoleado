package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.os.Handler;
import androidx.camera.camera2.internal.compat.t;
import j.N;
import j.X;
import java.util.concurrent.Executor;

/* compiled from: CameraDeviceCompat.java */
@X
/* loaded from: classes.dex */
public final class o {

    /* renamed from: a, reason: collision with root package name */
    public final r f23174a;

    /* compiled from: CameraDeviceCompat.java */
    public interface a {
        void a(@N androidx.camera.camera2.internal.compat.params.l lVar);
    }

    /* compiled from: CameraDeviceCompat.java */
    @X
    public static final class b extends CameraDevice.StateCallback {

        /* renamed from: a, reason: collision with root package name */
        public final CameraDevice.StateCallback f23175a;

        /* renamed from: b, reason: collision with root package name */
        public final Executor f23176b;

        public b(@N Executor executor, @N CameraDevice.StateCallback stateCallback) {
            this.f23176b = executor;
            this.f23175a = stateCallback;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(@N CameraDevice cameraDevice) {
            this.f23176b.execute(new p(this, cameraDevice, 0));
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(@N CameraDevice cameraDevice) {
            this.f23176b.execute(new p(this, cameraDevice, 1));
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(@N CameraDevice cameraDevice, int i12) {
            this.f23176b.execute(new g(this, cameraDevice, i12, 1));
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(@N CameraDevice cameraDevice) {
            this.f23176b.execute(new p(this, cameraDevice, 2));
        }
    }

    public o(@N CameraDevice cameraDevice, @N Handler handler) {
        if (Build.VERSION.SDK_INT < 28) {
            this.f23174a = new r(cameraDevice, new t.a(handler));
        } else {
            cameraDevice.getClass();
            this.f23174a = new s(cameraDevice, null);
        }
    }

    public final void a(@N androidx.camera.camera2.internal.compat.params.l lVar) {
        this.f23174a.a(lVar);
    }
}
