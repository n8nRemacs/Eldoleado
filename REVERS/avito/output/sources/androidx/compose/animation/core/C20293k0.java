package androidx.compose.animation.core;

import androidx.compose.animation.core.C20281g0;
import androidx.compose.animation.core.C20281g0.a;
import androidx.compose.runtime.A;
import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.C22187u0;
import androidx.compose.runtime.InterfaceC22132o;
import kotlin.Metadata;

/* compiled from: InfiniteTransition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.k0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20293k0 {
    @InterfaceC22132o
    @Y61.k
    public static final C20281g0.a a(@Y61.k C20281g0 c20281g0, float f12, float f13, @Y61.k C20278f0 c20278f0, @Y61.l String str, @Y61.l androidx.compose.runtime.A a12, int i12, int i13) {
        String str2 = (i13 & 8) != 0 ? "FloatAnimation" : str;
        Float fValueOf = Float.valueOf(f12);
        Float fValueOf2 = Float.valueOf(f13);
        int i14 = kotlin.jvm.internal.A.f406820a;
        int i15 = i12 << 3;
        return b(c20281g0, fValueOf, fValueOf2, J1.f26075a, c20278f0, str2, a12, (i12 & 1022) | (57344 & i15) | (i15 & 458752), 0);
    }

    @InterfaceC22132o
    @Y61.k
    public static final C20281g0.a b(@Y61.k C20281g0 c20281g0, Number number, Number number2, @Y61.k H1 h12, @Y61.k C20278f0 c20278f0, @Y61.l String str, @Y61.l androidx.compose.runtime.A a12, int i12, int i13) {
        Object objT = a12.t();
        androidx.compose.runtime.A.f37866a.getClass();
        A.a.C1651a c1651a = A.a.f37868b;
        if (objT == c1651a) {
            objT = c20281g0.new a(number, number2, h12, c20278f0);
            a12.H(objT);
        }
        C20281g0.a aVar = (C20281g0.a) objT;
        boolean z12 = ((((i12 & 112) ^ 48) > 32 && a12.u(number)) || (i12 & 48) == 32) | ((((i12 & 896) ^ 384) > 256 && a12.u(number2)) || (i12 & 384) == 256) | ((((57344 & i12) ^ 24576) > 16384 && a12.u(c20278f0)) || (i12 & 24576) == 16384);
        Object objT2 = a12.t();
        if (z12 || objT2 == c1651a) {
            objT2 = new C20284h0(number, aVar, number2, c20278f0);
            a12.H(objT2);
        }
        C22128n0 c22128n0 = C22187u0.f38775a;
        a12.w((Y41.a) objT2);
        boolean zU2 = a12.u(c20281g0);
        Object objT3 = a12.t();
        if (zU2 || objT3 == c1651a) {
            objT3 = new C20290j0(c20281g0, aVar);
            a12.H(objT3);
        }
        C22187u0.a(aVar, (Y41.l) objT3, a12);
        return aVar;
    }

    @InterfaceC22132o
    @Y61.k
    public static final C20281g0 c(@Y61.l String str, @Y61.l androidx.compose.runtime.A a12, int i12) {
        if ((i12 & 1) != 0) {
            str = "InfiniteTransition";
        }
        Object objT = a12.t();
        androidx.compose.runtime.A.f37866a.getClass();
        if (objT == A.a.f37868b) {
            objT = new C20281g0(str);
            a12.H(objT);
        }
        C20281g0 c20281g0 = (C20281g0) objT;
        c20281g0.a(a12, 0);
        return c20281g0;
    }
}
