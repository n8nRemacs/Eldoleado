package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;

/* compiled from: OutputConfigurationCompatApi33Impl.java */
@X
/* loaded from: classes.dex */
public class j extends i {
    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public final void c(long j12) {
        if (j12 == -1) {
            return;
        }
        ((OutputConfiguration) g()).setStreamUseCase(j12);
    }

    @Override // androidx.camera.camera2.internal.compat.params.i, androidx.camera.camera2.internal.compat.params.h, androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @P
    public final /* bridge */ /* synthetic */ String e() {
        return null;
    }

    @Override // androidx.camera.camera2.internal.compat.params.i, androidx.camera.camera2.internal.compat.params.h, androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public final void f(long j12) {
        ((OutputConfiguration) g()).setDynamicRangeProfile(j12);
    }

    @Override // androidx.camera.camera2.internal.compat.params.i, androidx.camera.camera2.internal.compat.params.h, androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @N
    public final Object g() {
        Object obj = this.f23197a;
        z.b(obj instanceof OutputConfiguration);
        return obj;
    }

    @Override // androidx.camera.camera2.internal.compat.params.k
    public final int hashCode() {
        return this.f23197a.hashCode();
    }
}
