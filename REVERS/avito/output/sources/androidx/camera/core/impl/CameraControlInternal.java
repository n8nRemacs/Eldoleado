package androidx.camera.core.impl;

import android.graphics.Rect;
import androidx.camera.core.CameraControl;
import androidx.camera.core.impl.SessionConfig;
import java.util.ArrayList;
import java.util.Collections;

@j.X
/* loaded from: classes.dex */
public interface CameraControlInternal extends CameraControl {

    /* renamed from: a, reason: collision with root package name */
    public static final CameraControlInternal f23908a = new a();

    public static final class CameraControlException extends Exception {
    }

    public interface b {
    }

    @j.N
    Config c();

    @j.N
    com.google.common.util.concurrent.D0 d(@j.N ArrayList arrayList, int i12, int i13);

    void e();

    void h(@j.N Config config);

    @j.N
    Rect j();

    void k(int i12);

    void l(@j.N SessionConfig.b bVar);

    public class a implements CameraControlInternal {
        @Override // androidx.camera.core.CameraControl
        @j.N
        public final com.google.common.util.concurrent.D0<Void> a(float f12) {
            return androidx.camera.core.impl.utils.futures.f.g(null);
        }

        @Override // androidx.camera.core.CameraControl
        @j.N
        public final com.google.common.util.concurrent.D0<Void> b(float f12) {
            return androidx.camera.core.impl.utils.futures.f.g(null);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        @j.N
        public final Config c() {
            return null;
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        @j.N
        public final com.google.common.util.concurrent.D0 d(@j.N ArrayList arrayList, int i12, int i13) {
            return androidx.camera.core.impl.utils.futures.f.g(Collections.emptyList());
        }

        @Override // androidx.camera.core.CameraControl
        @j.N
        public final com.google.common.util.concurrent.D0<Void> f(boolean z12) {
            return androidx.camera.core.impl.utils.futures.f.g(null);
        }

        @Override // androidx.camera.core.CameraControl
        @j.N
        public final com.google.common.util.concurrent.D0<androidx.camera.core.N> g(@j.N androidx.camera.core.M m12) {
            return androidx.camera.core.impl.utils.futures.f.g(new androidx.camera.core.N(false));
        }

        @Override // androidx.camera.core.CameraControl
        @j.N
        public final com.google.common.util.concurrent.D0<Integer> i(int i12) {
            return androidx.camera.core.impl.utils.futures.f.g(0);
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        @j.N
        public final Rect j() {
            return new Rect();
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public final void e() {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public final void h(@j.N Config config) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public final void k(int i12) {
        }

        @Override // androidx.camera.core.impl.CameraControlInternal
        public final void l(@j.N SessionConfig.b bVar) {
        }
    }
}
