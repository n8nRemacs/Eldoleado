package androidx.camera.core;

import android.util.Size;
import android.view.Surface;
import androidx.annotation.RestrictTo;
import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

/* compiled from: SurfaceOutput.java */
/* loaded from: classes.dex */
public interface C0 extends Closeable {

    /* compiled from: SurfaceOutput.java */
    @VY0.c
    public static abstract class a {

        /* compiled from: SurfaceOutput.java */
        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        /* renamed from: androidx.camera.core.C0$a$a, reason: collision with other inner class name */
        public @interface InterfaceC1531a {
        }

        @j.N
        @RestrictTo
        public static a c(@j.N C0 c02) {
            return new C20121k(c02);
        }

        public abstract int a();

        @j.N
        public abstract C0 b();
    }

    void X0(@j.N float[] fArr, @j.N float[] fArr2);

    @j.N
    Size getSize();

    @j.N
    Surface n1(@j.N Executor executor, @j.N androidx.camera.core.processing.h hVar);

    @RestrictTo
    default int w() {
        return 34;
    }
}
