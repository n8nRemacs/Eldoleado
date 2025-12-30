package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.C19986b;
import java.util.ArrayList;

/* compiled from: SynchronizedCaptureSession.java */
@j.X
/* loaded from: classes.dex */
public interface K0 {
    void a();

    void b();

    @j.N
    a c();

    void close();

    @j.N
    CameraDevice d();

    @j.N
    C19986b g();

    @j.N
    com.google.common.util.concurrent.D0<Void> h();

    void i();

    int j(@j.N ArrayList arrayList, @j.N CameraCaptureSession.CaptureCallback captureCallback);

    int k(@j.N CaptureRequest captureRequest, @j.N CameraCaptureSession.CaptureCallback captureCallback);

    /* compiled from: SynchronizedCaptureSession.java */
    public static abstract class a {
        public void l(@j.N K0 k02) {
        }

        @j.X
        public void m(@j.N K0 k02) {
        }

        public void n(@j.N K0 k02) {
        }

        public void o(@j.N K0 k02) {
        }

        public void p(@j.N K0 k02) {
        }

        public void q(@j.N K0 k02) {
        }

        public void r(@j.N K0 k02) {
        }

        @j.X
        public void s(@j.N K0 k02, @j.N Surface surface) {
        }
    }
}
