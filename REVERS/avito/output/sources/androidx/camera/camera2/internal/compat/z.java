package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import j.N;
import j.X;
import java.util.Set;

/* compiled from: CameraManagerCompatApi30Impl.java */
@X
/* loaded from: classes.dex */
class z extends y {
    @Override // androidx.camera.camera2.internal.compat.A, androidx.camera.camera2.internal.compat.w.b
    @N
    public final Set<Set<String>> e() throws CameraAccessExceptionCompat {
        try {
            return this.f23121a.getConcurrentCameraIds();
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }
}
