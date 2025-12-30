package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.params.SessionConfiguration;
import j.N;
import j.X;

/* compiled from: CameraDeviceCompatApi28Impl.java */
@X
/* loaded from: classes.dex */
class s extends r {
    @Override // androidx.camera.camera2.internal.compat.r, androidx.camera.camera2.internal.compat.q, androidx.camera.camera2.internal.compat.t, androidx.camera.camera2.internal.compat.o.a
    public final void a(@N androidx.camera.camera2.internal.compat.params.l lVar) throws CameraAccessException, CameraAccessExceptionCompat {
        SessionConfiguration sessionConfiguration = (SessionConfiguration) lVar.i();
        sessionConfiguration.getClass();
        try {
            this.f23234a.createCaptureSession(sessionConfiguration);
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }
}
