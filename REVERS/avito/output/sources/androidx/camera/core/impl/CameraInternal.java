package androidx.camera.core.impl;

import androidx.camera.core.CameraControl;
import androidx.camera.core.InterfaceC20127n;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.N0;
import java.util.ArrayList;

@j.X
/* loaded from: classes.dex */
public interface CameraInternal extends InterfaceC20127n, N0.d {

    public enum State {
        PENDING_OPEN(false),
        OPENING(true),
        OPEN(true),
        CONFIGURED(true),
        CLOSING(true),
        CLOSED(false),
        RELEASING(true),
        RELEASED(false);


        /* renamed from: b, reason: collision with root package name */
        public final boolean f23918b;

        State(boolean z12) {
            this.f23918b = z12;
        }
    }

    @Override // androidx.camera.core.InterfaceC20127n
    @j.N
    default CameraControl a() {
        return l();
    }

    @j.N
    B c();

    default boolean d() {
        return getCameraInfo().b() == 0;
    }

    @j.N
    q0<State> f();

    void g(@j.N ArrayList arrayList);

    @Override // androidx.camera.core.InterfaceC20127n
    @j.N
    default InterfaceC20142t getCameraInfo() {
        return c();
    }

    @j.N
    default InterfaceC20112t h() {
        return C20115w.f24305a;
    }

    void i(@j.N ArrayList arrayList);

    default boolean j() {
        return true;
    }

    @j.N
    CameraControlInternal l();

    default void e(@j.P InterfaceC20112t interfaceC20112t) {
    }

    default void m(boolean z12) {
    }
}
