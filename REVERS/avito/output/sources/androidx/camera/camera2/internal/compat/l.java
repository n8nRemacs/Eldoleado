package androidx.camera.camera2.internal.compat;

import androidx.camera.core.C20140q0;
import j.N;
import j.X;
import java.util.Collections;
import java.util.Set;

/* compiled from: CameraCharacteristicsApi28Impl.java */
@X
/* loaded from: classes.dex */
class l extends m {
    @Override // androidx.camera.camera2.internal.compat.m, androidx.camera.camera2.internal.compat.n.a
    @N
    public final Set<String> a() {
        try {
            return this.f23169a.getPhysicalCameraIds();
        } catch (Exception unused) {
            C20140q0.c("CameraCharacteristicsImpl");
            return Collections.emptySet();
        }
    }
}
