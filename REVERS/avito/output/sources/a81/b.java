package A81;

import androidx.compose.foundation.H;
import androidx.compose.foundation.layout.C20588k2;
import androidx.compose.foundation.layout.C20632w;
import androidx.compose.foundation.layout.C20644z;
import androidx.compose.runtime.A;
import androidx.compose.runtime.B;
import androidx.compose.runtime.C22039c2;
import androidx.compose.runtime.C22190v;
import androidx.compose.runtime.internal.C22096n;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.layout.InterfaceC22365i0;
import androidx.compose.ui.layout.M;
import androidx.compose.ui.node.InterfaceC22413h;
import androidx.compose.ui.platform.InterfaceC22544x2;
import androidx.compose.ui.platform.Q0;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.v;
import x31.h;
import x31.i;

/* loaded from: classes9.dex */
public abstract class b {
    public static final void a(v vVar, long j12, float f12, A a12, int i12) {
        v vVar2;
        v vVar3;
        B bE2 = a12.E(1045124796);
        int i13 = i12 | 6;
        if ((i12 & 112) == 0) {
            i13 |= bE2.n(j12) ? 32 : 16;
        }
        if ((i12 & 896) == 0) {
            i13 |= bE2.k(f12) ? 256 : 128;
        }
        if ((i13 & 731) == 146 && bE2.c()) {
            bE2.f();
            vVar3 = vVar;
        } else {
            bE2.y0();
            if ((i12 & 1) == 0 || bE2.g0()) {
                vVar2 = v.f42878y1;
            } else {
                bE2.f();
                vVar2 = vVar;
            }
            bE2.Y();
            v vVarD = C20588k2.d(C20588k2.j(vVar2, f12), 1.0f);
            bE2.I(733328855);
            InterfaceC22215f.f39074a.getClass();
            InterfaceC22365i0 interfaceC22365i0E = C20632w.e(InterfaceC22215f.a.f39076b, false, bE2, 0);
            bE2.I(-1323940314);
            androidx.compose.ui.unit.d dVar = (androidx.compose.ui.unit.d) bE2.o(Q0.f41199h);
            LayoutDirection layoutDirection = (LayoutDirection) bE2.o(Q0.f41205n);
            InterfaceC22544x2 interfaceC22544x2 = (InterfaceC22544x2) bE2.o(Q0.f41210s);
            InterfaceC22413h.a aVar = InterfaceC22413h.f40789C1;
            aVar.getClass();
            Y41.a<InterfaceC22413h> aVar2 = InterfaceC22413h.a.f40791b;
            C22096n c22096nB = M.b(vVarD);
            if (bE2.f37890b == null) {
                C22190v.b();
                throw null;
            }
            bE2.A();
            if (bE2.f37887P) {
                bE2.b(aVar2);
            } else {
                bE2.d();
            }
            bE2.f37913y = false;
            i.a(c22096nB, h.a(aVar, bE2, interfaceC22365i0E, bE2, dVar, bE2, layoutDirection, bE2, interfaceC22544x2, bE2, bE2), bE2);
            Ec1.b.a(C20644z.f28804a.d(v.f42878y1, InterfaceC22215f.a.f39080f), j12, null, null, 0, null, null, bE2, i13 & 112);
            H.B(bE2, false, true, false, false);
            vVar3 = vVar2;
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z == null) {
            return;
        }
        c22039c2Z.f38184d = new a(vVar3, j12, f12, i12);
    }
}
