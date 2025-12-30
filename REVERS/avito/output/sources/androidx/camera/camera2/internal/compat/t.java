package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraDevice;
import android.os.Handler;
import androidx.camera.camera2.internal.compat.o;
import j.N;
import j.P;
import j.X;

/* compiled from: CameraDeviceCompatBaseImpl.java */
@X
/* loaded from: classes.dex */
class t implements o.a {

    /* renamed from: a, reason: collision with root package name */
    public final CameraDevice f23234a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f23235b;

    /* compiled from: CameraDeviceCompatBaseImpl.java */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final Handler f23236a;

        public a(@N Handler handler) {
            this.f23236a = handler;
        }
    }

    public t(@N CameraDevice cameraDevice, @P Object obj) {
        cameraDevice.getClass();
        this.f23234a = cameraDevice;
        this.f23235b = obj;
    }

    @Override // androidx.camera.camera2.internal.compat.o.a
    public void a(@N androidx.camera.camera2.internal.compat.params.l lVar) {
        throw null;
    }
}
