package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCharacteristics;
import androidx.camera.camera2.internal.compat.n;
import j.N;
import j.X;
import java.util.Collections;
import java.util.Set;

/* compiled from: CameraCharacteristicsBaseImpl.java */
@X
/* loaded from: classes.dex */
class m implements n.a {

    /* renamed from: a, reason: collision with root package name */
    @N
    public final CameraCharacteristics f23169a;

    public m(@N CameraCharacteristics cameraCharacteristics) {
        this.f23169a = cameraCharacteristics;
    }

    @Override // androidx.camera.camera2.internal.compat.n.a
    @N
    public Set<String> a() {
        return Collections.emptySet();
    }
}
