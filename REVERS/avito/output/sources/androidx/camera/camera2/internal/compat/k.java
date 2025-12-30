package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import androidx.camera.camera2.internal.compat.C19986b;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: CameraCaptureSessionCompatBaseImpl.java */
@X
/* loaded from: classes.dex */
class k implements C19986b.a {

    /* renamed from: a, reason: collision with root package name */
    public final CameraCaptureSession f23166a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f23167b;

    /* compiled from: CameraCaptureSessionCompatBaseImpl.java */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final Handler f23168a;

        public a(@N Handler handler) {
            this.f23168a = handler;
        }
    }

    public k(@N CameraCaptureSession cameraCaptureSession, @P Object obj) {
        cameraCaptureSession.getClass();
        this.f23166a = cameraCaptureSession;
        this.f23167b = obj;
    }

    @Override // androidx.camera.camera2.internal.compat.C19986b.a
    public int a(@N ArrayList arrayList, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback) {
        return this.f23166a.captureBurst(arrayList, new C19986b.C1530b(executor, captureCallback), ((a) this.f23167b).f23168a);
    }

    @Override // androidx.camera.camera2.internal.compat.C19986b.a
    public int b(@N CaptureRequest captureRequest, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback) {
        return this.f23166a.setRepeatingRequest(captureRequest, new C19986b.C1530b(executor, captureCallback), ((a) this.f23167b).f23168a);
    }
}
