package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.foundation.layout.C20632w;
import androidx.compose.foundation.layout.C20644z;
import androidx.compose.runtime.A;
import androidx.compose.runtime.C22039c2;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22190v;
import androidx.compose.runtime.InterfaceC22132o;
import androidx.compose.runtime.InterfaceC22137p;
import androidx.compose.runtime.R3;
import androidx.compose.runtime.internal.C22096n;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.layout.InterfaceC22365i0;
import androidx.compose.ui.node.InterfaceC22413h;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.C42745f0;
import okhttp3.internal.http2.Http2;

/* compiled from: Crossfade.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002¨\u0006\u0003²\u0006\u0012\u0010\u0002\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00008\nX\u008a\u0084\u0002"}, d2 = {"T", "", "alpha", "animation_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class F0 {
    @InterfaceC22132o
    @InterfaceC20371n1
    @InterfaceC22137p
    public static final void a(@Y61.k C20288i1 c20288i1, @Y61.l androidx.compose.ui.v vVar, @Y61.l androidx.compose.animation.core.V v12, @Y61.l Y41.l lVar, @Y61.k C22096n c22096n, @Y61.l androidx.compose.runtime.A a12, int i12) {
        Y41.l lVar2;
        boolean z12;
        boolean z13;
        androidx.compose.runtime.B bE2 = a12.E(679005231);
        int i13 = (i12 & 6) == 0 ? (bE2.B(c20288i1) ? 4 : 2) | i12 : i12;
        if ((i12 & 48) == 0) {
            i13 |= bE2.B(vVar) ? 32 : 16;
        }
        if ((i12 & 384) == 0) {
            i13 |= bE2.u(v12) ? 256 : 128;
        }
        int i14 = i13 | 3072;
        if ((i12 & 24576) == 0) {
            i14 |= bE2.u(c22096n) ? Http2.INITIAL_MAX_FRAME_SIZE : 8192;
        }
        if (bE2.p(i14 & 1, (i14 & 9363) != 9362)) {
            lVar2 = C20405z0.f26631l;
            Object objT = bE2.t();
            androidx.compose.runtime.A.f37866a.getClass();
            A.a.C1651a c1651a = A.a.f37868b;
            androidx.compose.animation.core.F1<S> f12 = c20288i1.f26317a;
            Object obj = objT;
            if (objT == c1651a) {
                androidx.compose.runtime.snapshots.D d12 = new androidx.compose.runtime.snapshots.D();
                d12.add(f12.a());
                bE2.H(d12);
                obj = d12;
            }
            androidx.compose.runtime.snapshots.D d13 = (androidx.compose.runtime.snapshots.D) obj;
            Object objT2 = bE2.t();
            if (objT2 == c1651a) {
                objT2 = androidx.collection.i1.b();
                bE2.H(objT2);
            }
            androidx.collection.R0 r02 = (androidx.collection.R0) objT2;
            Object objA = f12.a();
            C22082i3 c22082i3 = (C22082i3) c20288i1.f26320d;
            if (kotlin.jvm.internal.L.f(objA, c22082i3.getF42167b())) {
                bE2.C(860925177);
                if (d13.size() == 1 && kotlin.jvm.internal.L.f(d13.get(0), c22082i3.getF42167b())) {
                    bE2.C(861249809);
                    bE2.X(false);
                } else {
                    bE2.C(861059531);
                    boolean z14 = (i14 & 14) == 4;
                    Object objT3 = bE2.t();
                    if (z14 || objT3 == c1651a) {
                        objT3 = new A0(c20288i1);
                        bE2.H(objT3);
                    }
                    C42745f0.l0((Y41.l) objT3, d13);
                    r02.g();
                    bE2.X(false);
                }
                bE2.X(false);
            } else {
                bE2.C(861255761);
                bE2.X(false);
            }
            if (r02.b(c22082i3.getF42167b())) {
                z12 = false;
                bE2.C(862059281);
                bE2.X(false);
            } else {
                bE2.C(861316428);
                ListIterator listIterator = d13.listIterator();
                int i15 = 0;
                while (true) {
                    if (!listIterator.hasNext()) {
                        i15 = -1;
                        break;
                    }
                    Object next = listIterator.next();
                    lVar2.getClass();
                    if (kotlin.jvm.internal.L.f(next, c22082i3.getF42167b())) {
                        break;
                    } else {
                        i15++;
                    }
                }
                if (i15 == -1) {
                    d13.add(c22082i3.getF42167b());
                } else {
                    d13.set(i15, c22082i3.getF42167b());
                }
                r02.g();
                int size = d13.size();
                for (int i16 = 0; i16 < size; i16++) {
                    Object obj2 = d13.get(i16);
                    r02.m(obj2, androidx.compose.runtime.internal.r.c(-1426421288, new D0(c20288i1, v12, obj2, c22096n), bE2));
                }
                z12 = false;
                bE2.X(false);
            }
            InterfaceC22215f.f39074a.getClass();
            InterfaceC22365i0 interfaceC22365i0D = C20632w.d(InterfaceC22215f.a.f39076b, z12);
            int i17 = bE2.f37888Q;
            androidx.compose.runtime.O1 o1S = bE2.S();
            androidx.compose.ui.v vVarC = androidx.compose.ui.n.c(bE2, vVar);
            InterfaceC22413h.f40789C1.getClass();
            Y41.a<InterfaceC22413h> aVar = InterfaceC22413h.a.f40791b;
            if (bE2.f37890b == null) {
                C22190v.b();
                throw null;
            }
            bE2.A();
            if (bE2.f37887P) {
                bE2.b(aVar);
            } else {
                bE2.d();
            }
            R3.b(InterfaceC22413h.a.f40796g, bE2, interfaceC22365i0D);
            R3.b(InterfaceC22413h.a.f40795f, bE2, o1S);
            Y41.p<InterfaceC22413h, Integer, kotlin.G0> pVar = InterfaceC22413h.a.f40799j;
            if (bE2.f37887P || !kotlin.jvm.internal.L.f(bE2.t(), Integer.valueOf(i17))) {
                AK.c.y(i17, bE2, i17, pVar);
            }
            R3.b(InterfaceC22413h.a.f40793d, bE2, vVarC);
            C20644z c20644z = C20644z.f28804a;
            bE2.C(-187474512);
            int size2 = d13.size();
            for (int i18 = 0; i18 < size2; i18++) {
                Object obj3 = d13.get(i18);
                lVar2.getClass();
                bE2.K(-1081865889, obj3);
                Y41.p pVar2 = (Y41.p) r02.e(obj3);
                if (pVar2 == null) {
                    bE2.C(821932266);
                    z13 = false;
                } else {
                    z13 = false;
                    bE2.C(-1081864713);
                    pVar2.invoke(bE2, 0);
                }
                bE2.X(z13);
                bE2.X(z13);
            }
            bE2.X(false);
            bE2.X(true);
        } else {
            bE2.f();
            lVar2 = lVar;
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z != null) {
            c22039c2Z.f38184d = new E0(c20288i1, vVar, v12, lVar2, c22096n, i12);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
    @androidx.compose.runtime.InterfaceC22132o
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void b(java.lang.Object r15, @Y61.l androidx.compose.ui.v r16, @Y61.l androidx.compose.animation.core.V r17, @Y61.l java.lang.String r18, @Y61.k androidx.compose.runtime.internal.C22096n r19, @Y61.l androidx.compose.runtime.A r20, int r21, int r22) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.F0.b(java.lang.Object, androidx.compose.ui.v, androidx.compose.animation.core.V, java.lang.String, androidx.compose.runtime.internal.n, androidx.compose.runtime.A, int, int):void");
    }
}
