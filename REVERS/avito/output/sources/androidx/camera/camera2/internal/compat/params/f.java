package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.os.Build;
import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.camera2.internal.compat.params.h;
import androidx.camera.camera2.internal.compat.params.i;
import j.N;
import j.P;
import j.X;

/* compiled from: OutputConfigurationCompat.java */
@X
/* loaded from: classes.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    public final h f23188a;

    /* compiled from: OutputConfigurationCompat.java */
    public interface a {
        void a(@N Surface surface);

        void b();

        void c(long j12);

        void d(@P String str);

        @P
        String e();

        void f(long j12);

        @P
        Object g();

        @P
        Surface getSurface();
    }

    public f(int i12, @N Surface surface) {
        int i13 = Build.VERSION.SDK_INT;
        if (i13 >= 33) {
            this.f23188a = new j(new OutputConfiguration(i12, surface));
        } else if (i13 >= 28) {
            this.f23188a = new i(new i.a(new OutputConfiguration(i12, surface)));
        } else {
            this.f23188a = new h(new h.a(new OutputConfiguration(i12, surface)));
        }
    }

    public final void a(@N Surface surface) {
        this.f23188a.a(surface);
    }

    public final void b() {
        this.f23188a.b();
    }

    @RestrictTo
    @P
    public final String c() {
        return this.f23188a.e();
    }

    @P
    public final Surface d() {
        return this.f23188a.getSurface();
    }

    public final void e(long j12) {
        this.f23188a.f(j12);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof f)) {
            return false;
        }
        return this.f23188a.equals(((f) obj).f23188a);
    }

    public final void f(@P String str) {
        this.f23188a.d(str);
    }

    public final void g(long j12) {
        this.f23188a.c(j12);
    }

    public final int hashCode() {
        return this.f23188a.hashCode();
    }

    public f(@N h hVar) {
        this.f23188a = hVar;
    }
}
