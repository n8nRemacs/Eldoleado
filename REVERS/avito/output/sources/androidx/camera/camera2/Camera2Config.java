package androidx.camera.camera2;

import A.a;
import A.b;
import A.c;
import androidx.annotation.RestrictTo;
import androidx.camera.core.A;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20117y;
import androidx.camera.core.impl.n0;
import androidx.camera.core.impl.s0;
import j.N;
import j.X;

@X
/* loaded from: classes.dex */
public final class Camera2Config {

    @RestrictTo
    public static final class DefaultProvider implements A.b {
        @Override // androidx.camera.core.A.b
        @N
        public A getCameraXConfig() {
            a aVar = new a();
            b bVar = new b();
            c cVar = new c();
            A.a aVar2 = new A.a();
            Config.a<InterfaceC20117y.a> aVar3 = A.f23539G;
            n0 n0Var = aVar2.f23547a;
            n0Var.K(aVar3, aVar);
            n0Var.K(A.f23540H, bVar);
            n0Var.K(A.f23541I, cVar);
            return new A(s0.Q(n0Var));
        }
    }
}
