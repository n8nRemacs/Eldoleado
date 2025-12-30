package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.view.Surface;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Objects;

/* compiled from: OutputConfigurationCompatApi26Impl.java */
@X
/* loaded from: classes.dex */
class h extends g {

    /* compiled from: OutputConfigurationCompatApi26Impl.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @N
        public final OutputConfiguration f23192a;

        /* renamed from: b, reason: collision with root package name */
        @P
        public String f23193b;

        /* renamed from: c, reason: collision with root package name */
        public long f23194c = 1;

        public a(@N OutputConfiguration outputConfiguration) {
            this.f23192a = outputConfiguration;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return Objects.equals(this.f23192a, aVar.f23192a) && this.f23194c == aVar.f23194c && Objects.equals(this.f23193b, aVar.f23193b);
        }

        public final int hashCode() {
            int iHashCode = this.f23192a.hashCode() ^ 31;
            int i12 = (iHashCode << 5) - iHashCode;
            String str = this.f23193b;
            int iHashCode2 = (str == null ? 0 : str.hashCode()) ^ i12;
            return Long.hashCode(this.f23194c) ^ ((iHashCode2 << 5) - iHashCode2);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void a(@N Surface surface) {
        ((OutputConfiguration) g()).addSurface(surface);
    }

    @Override // androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void b() {
        ((OutputConfiguration) g()).enableSurfaceSharing();
    }

    @Override // androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void d(@P String str) {
        ((a) this.f23197a).f23193b = str;
    }

    @Override // androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @P
    public String e() {
        return ((a) this.f23197a).f23193b;
    }

    @Override // androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void f(long j12) {
        ((a) this.f23197a).f23194c = j12;
    }

    @Override // androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @N
    public Object g() {
        Object obj = this.f23197a;
        z.b(obj instanceof a);
        return ((a) obj).f23192a;
    }

    @Override // androidx.camera.camera2.internal.compat.params.g, androidx.camera.camera2.internal.compat.params.k
    public final boolean h() {
        throw new AssertionError("isSurfaceSharingEnabled() should not be called on API >= 26");
    }
}
