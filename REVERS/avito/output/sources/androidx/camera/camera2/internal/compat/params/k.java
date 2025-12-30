package androidx.camera.camera2.internal.compat.params;

import android.view.Surface;
import androidx.camera.camera2.internal.compat.params.f;
import j.N;
import j.P;
import j.X;
import java.util.Objects;

/* compiled from: OutputConfigurationCompatBaseImpl.java */
@X
/* loaded from: classes.dex */
class k implements f.a {

    /* renamed from: a, reason: collision with root package name */
    public final Object f23197a;

    /* compiled from: OutputConfigurationCompatBaseImpl.java */
    @X
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @P
        public String f23198a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f23199b;

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

    public k(@N Object obj) {
        this.f23197a = obj;
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    public void a(@N Surface surface) {
        if (getSurface() == surface) {
            throw new IllegalStateException("Surface is already added!");
        }
        if (!h()) {
            throw new IllegalStateException("Cannot have 2 surfaces for a non-sharing configuration");
        }
        throw new IllegalArgumentException("Exceeds maximum number of surfaces");
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    public void b() {
        ((a) this.f23197a).f23199b = true;
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    public void d(@P String str) {
        ((a) this.f23197a).f23198a = str;
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    @P
    public String e() {
        return ((a) this.f23197a).f23198a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof k)) {
            return false;
        }
        return Objects.equals(this.f23197a, ((k) obj).f23197a);
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    public void f(long j12) {
        ((a) this.f23197a).getClass();
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    @P
    public Object g() {
        return null;
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    @P
    public Surface getSurface() {
        ((a) this.f23197a).getClass();
        throw null;
    }

    public boolean h() {
        return ((a) this.f23197a).f23199b;
    }

    public int hashCode() {
        return this.f23197a.hashCode();
    }

    @Override // androidx.camera.camera2.internal.compat.params.f.a
    public void c(long j12) {
    }
}
