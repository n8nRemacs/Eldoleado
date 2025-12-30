package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.view.Surface;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;

/* compiled from: OutputConfigurationCompatApi24Impl.java */
@X
/* loaded from: classes.dex */
class g extends k {

    /* compiled from: OutputConfigurationCompatApi24Impl.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @P
        public String f23189a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f23190b;

        /* renamed from: c, reason: collision with root package name */
        public long f23191c;

        public final boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            ((a) obj).getClass();
            throw null;
        }

        public final int hashCode() {
            throw null;
        }
    }

    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void b() {
        ((a) this.f23197a).f23190b = true;
    }

    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void d(@P String str) {
        ((a) this.f23197a).f23189a = str;
    }

    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @P
    public String e() {
        return ((a) this.f23197a).f23189a;
    }

    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    public void f(long j12) {
        ((a) this.f23197a).f23191c = j12;
    }

    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @N
    public Object g() {
        Object obj = this.f23197a;
        z.b(obj instanceof a);
        ((a) obj).getClass();
        return null;
    }

    @Override // androidx.camera.camera2.internal.compat.params.k, androidx.camera.camera2.internal.compat.params.f.a
    @P
    public Surface getSurface() {
        return ((OutputConfiguration) g()).getSurface();
    }

    @Override // androidx.camera.camera2.internal.compat.params.k
    public boolean h() {
        return ((a) this.f23197a).f23190b;
    }
}
