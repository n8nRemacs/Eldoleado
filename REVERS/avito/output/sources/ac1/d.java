package Ac1;

import androidx.compose.foundation.layout.C20588k2;
import androidx.compose.foundation.layout.C20608p2;
import androidx.compose.foundation.shape.o;
import androidx.compose.runtime.A;
import androidx.compose.runtime.B;
import androidx.compose.runtime.C22039c2;
import androidx.compose.ui.draw.k;
import androidx.compose.ui.v;
import sberid.sdk.ui.utils.extensions.n;
import w2.AbstractC49426b;

/* loaded from: classes9.dex */
public abstract class d {
    public static final void a(v vVar, A a12, int i12) {
        int i13;
        B bE2 = a12.E(1821477345);
        if ((i12 & 14) == 0) {
            i13 = (bE2.B(vVar) ? 4 : 2) | i12;
        } else {
            i13 = i12;
        }
        if ((i13 & 11) == 2 && bE2.c()) {
            bE2.f();
        } else if (sberid.sdk.ui.utils.extensions.a.b(bE2)) {
            bE2.I(-518045162);
            C20608p2.a(bE2, n.a(k.a(C20588k2.d(C20588k2.f(vVar, AbstractC49426b.f441220s), 1.0f), o.a(AbstractC49426b.f441201i0))));
            bE2.X(false);
        } else {
            bE2.I(-518044913);
            C20608p2.a(bE2, n.a(k.a(C20588k2.t(C20588k2.f(vVar, AbstractC49426b.f441220s), AbstractC49426b.f441222t), o.a(AbstractC49426b.f441201i0))));
            bE2.X(false);
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z == null) {
            return;
        }
        c22039c2Z.f38184d = new c(vVar, i12);
    }
}
