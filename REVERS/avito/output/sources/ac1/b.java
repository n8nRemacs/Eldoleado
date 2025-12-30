package Ac1;

import androidx.compose.foundation.layout.C20585k;
import androidx.compose.foundation.layout.C20588k2;
import androidx.compose.foundation.layout.C20608p2;
import androidx.compose.foundation.layout.H;
import androidx.compose.foundation.layout.I;
import androidx.compose.foundation.layout.K;
import androidx.compose.foundation.shape.o;
import androidx.compose.runtime.A;
import androidx.compose.runtime.B;
import androidx.compose.runtime.C22039c2;
import androidx.compose.runtime.C22190v;
import androidx.compose.runtime.internal.C22096n;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.draw.k;
import androidx.compose.ui.i;
import androidx.compose.ui.layout.M;
import androidx.compose.ui.node.InterfaceC22413h;
import androidx.compose.ui.platform.InterfaceC22544x2;
import androidx.compose.ui.platform.Q0;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.v;
import sberid.sdk.ui.utils.extensions.n;
import w2.AbstractC49426b;

/* loaded from: classes9.dex */
public abstract class b {
    public static final void a(v vVar, A a12, int i12) {
        v vVar2;
        B bE2 = a12.E(1872325665);
        if (((i12 | 6) & 11) == 2 && bE2.c()) {
            bE2.f();
            vVar2 = vVar;
        } else {
            vVar2 = v.f42878y1;
            float f12 = AbstractC49426b.f441184a;
            v vVarT = C20588k2.t(vVar2, f12);
            InterfaceC22215f.f39074a.getClass();
            i.a aVar = InterfaceC22215f.a.f39089o;
            bE2.I(-483455358);
            C20585k.f28659a.getClass();
            I iA2 = H.a(C20585k.f28662d, aVar, bE2, 48);
            bE2.I(-1323940314);
            androidx.compose.ui.unit.d dVar = (androidx.compose.ui.unit.d) bE2.o(Q0.f41199h);
            LayoutDirection layoutDirection = (LayoutDirection) bE2.o(Q0.f41205n);
            InterfaceC22544x2 interfaceC22544x2 = (InterfaceC22544x2) bE2.o(Q0.f41210s);
            InterfaceC22413h.a aVar2 = InterfaceC22413h.f40789C1;
            aVar2.getClass();
            Y41.a<InterfaceC22413h> aVar3 = InterfaceC22413h.a.f40791b;
            C22096n c22096nB = M.b(vVarT);
            if (bE2.f37890b == null) {
                C22190v.b();
                throw null;
            }
            bE2.A();
            if (bE2.f37887P) {
                bE2.b(aVar3);
            } else {
                bE2.d();
            }
            bE2.f37913y = false;
            x31.i.a(c22096nB, x31.h.a(aVar2, bE2, iA2, bE2, dVar, bE2, layoutDirection, bE2, interfaceC22544x2, bE2, bE2), bE2);
            K k12 = K.f28344a;
            v vVarO = C20588k2.o(vVar2, f12);
            float f13 = AbstractC49426b.f441201i0;
            C20608p2.a(bE2, n.a(k.a(vVarO, o.a(f13))));
            C20608p2.a(bE2, C20588k2.f(vVar2, AbstractC49426b.f441190d));
            C20608p2.a(bE2, C20588k2.d(n.a(k.a(C20588k2.f(vVar2, AbstractC49426b.f441202j), o.a(f13))), 1.0f));
            bE2.X(false);
            androidx.compose.foundation.H.A(bE2, true, false, false);
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z == null) {
            return;
        }
        c22039c2Z.f38184d = new a(vVar2, i12);
    }
}
