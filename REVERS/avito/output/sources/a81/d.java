package A81;

import androidx.compose.foundation.layout.C20588k2;
import androidx.compose.foundation.layout.C20608p2;
import androidx.compose.foundation.shape.o;
import androidx.compose.runtime.A;
import androidx.compose.runtime.B;
import androidx.compose.runtime.C22039c2;
import androidx.compose.ui.draw.k;
import androidx.compose.ui.graphics.Y0;
import androidx.compose.ui.v;
import w2.AbstractC49426b;

/* loaded from: classes9.dex */
public abstract class d {
    public static final void a(int i12, long j12, A a12, v vVar) {
        int i13;
        B bE2 = a12.E(898553878);
        if ((i12 & 14) == 0) {
            i13 = (bE2.B(vVar) ? 4 : 2) | i12;
        } else {
            i13 = i12;
        }
        if ((i12 & 112) == 0) {
            i13 |= bE2.n(j12) ? 32 : 16;
        }
        if ((i13 & 91) == 18 && bE2.c()) {
            bE2.f();
        } else {
            bE2.y0();
            if ((i12 & 1) != 0 && !bE2.g0()) {
                bE2.f();
            }
            bE2.Y();
            C20608p2.a(bE2, androidx.compose.foundation.A.b(k.a(C20588k2.f(C20588k2.t(vVar, AbstractC49426b.f441161D), AbstractC49426b.f441159C), o.a(AbstractC49426b.f441207l0)), j12, Y0.f39346a));
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z == null) {
            return;
        }
        c22039c2Z.f38184d = new c(i12, j12, vVar);
    }
}
