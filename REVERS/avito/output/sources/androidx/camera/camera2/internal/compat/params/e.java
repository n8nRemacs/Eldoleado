package androidx.camera.camera2.internal.compat.params;

import android.annotation.SuppressLint;
import android.hardware.camera2.params.InputConfiguration;
import android.os.Build;
import j.N;
import j.P;
import j.X;
import j.k0;
import java.util.Objects;

/* compiled from: InputConfigurationCompat.java */
@X
/* loaded from: classes.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    public final a f23186a;

    /* compiled from: InputConfigurationCompat.java */
    @X
    public static class a implements d {

        /* renamed from: a, reason: collision with root package name */
        public final InputConfiguration f23187a;

        public a(@N Object obj) {
            this.f23187a = (InputConfiguration) obj;
        }

        @Override // androidx.camera.camera2.internal.compat.params.e.d
        @P
        public final Object a() {
            return this.f23187a;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof d)) {
                return false;
            }
            return Objects.equals(this.f23187a, ((d) obj).a());
        }

        public final int hashCode() {
            return this.f23187a.hashCode();
        }

        @N
        public final String toString() {
            return this.f23187a.toString();
        }
    }

    /* compiled from: InputConfigurationCompat.java */
    @X
    public static final class b extends a {
    }

    /* compiled from: InputConfigurationCompat.java */
    @k0
    public static final class c implements d {
        @Override // androidx.camera.camera2.internal.compat.params.e.d
        public final Object a() {
            return null;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof c)) {
                return false;
            }
            ((c) obj).getClass();
            return true;
        }

        public final int hashCode() {
            return 29791;
        }

        @N
        @SuppressLint({"DefaultLocale"})
        public final String toString() {
            return String.format("InputConfiguration(w:%d, h:%d, format:%d)", 0, 0, 0);
        }
    }

    /* compiled from: InputConfigurationCompat.java */
    public interface d {
        @P
        Object a();
    }

    public e(@N a aVar) {
        this.f23186a = aVar;
    }

    @P
    public static e b(@P Object obj) {
        if (obj == null) {
            return null;
        }
        return Build.VERSION.SDK_INT >= 31 ? new e(new b(obj)) : new e(new a(obj));
    }

    @P
    public final Object a() {
        return this.f23186a.f23187a;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof e)) {
            return false;
        }
        return this.f23186a.equals(((e) obj).f23186a);
    }

    public final int hashCode() {
        return this.f23186a.hashCode();
    }

    @N
    public final String toString() {
        return this.f23186a.toString();
    }
}
