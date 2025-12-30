package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Objects;

/* compiled from: OutputConfigurationCompatApi28Impl.java */
@X
/* loaded from: classes.dex */
class i extends h {

    /* compiled from: OutputConfigurationCompatApi28Impl.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @N
        public final OutputConfiguration f23195a;

        /* renamed from: b, reason: collision with root package name */
        public long f23196b = 1;

        public a(@N OutputConfiguration outputConfiguration) {
            this.f23195a = outputConfiguration;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return Objects.equals(this.f23195a, aVar.f23195a) && this.f23196b == aVar.f23196b;
        }

        public final int hashCode() {
            int iHashCode = this.f23195a.hashCode() ^ 31;
            return Long.hashCode(this.f23196b) ^ ((iHashCode << 5) - iHashCode);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.params.h, androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void d(@P String str) {
        ((OutputConfiguration) g()).setPhysicalCameraId(str);
    }

    @Override // androidx.camera.camera2.internal.compat.params.h, androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @P
    public String e() {
        return null;
    }

    @Override // androidx.camera.camera2.internal.compat.params.h, androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void f(long j12) {
        ((a) this.f23197a).f23196b = j12;
    }

    @Override // androidx.camera.camera2.internal.compat.params.h, androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @N
    public Object g() {
        Object obj = this.f23197a;
        z.b(obj instanceof a);
        return ((a) obj).f23195a;
    }
}
