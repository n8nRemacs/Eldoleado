package androidx.compose.animation.core;

import androidx.compose.runtime.A;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.C22187u0;
import androidx.compose.runtime.I3;
import androidx.compose.runtime.InterfaceC22132o;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.ui.unit.h;
import androidx.compose.ui.unit.q;
import androidx.compose.ui.unit.u;
import kotlin.Metadata;
import kotlinx.coroutines.channels.InterfaceC43123w;
import l0.g;
import l0.j;
import l0.n;

/* compiled from: AnimateAsState.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002¨\u0006\b²\u0006*\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u00018\nX\u008a\u0084\u0002²\u0006\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u00018\nX\u008a\u0084\u0002"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Lkotlin/Function1;", "Lkotlin/G0;", "listener", "Landroidx/compose/animation/core/p;", "animSpec", "animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20283h {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final R0<Float> f26300a = C20310q.d(0.0f, 7, null);

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final R0<androidx.compose.ui.unit.h> f26301b;

    static {
        h.a aVar = androidx.compose.ui.unit.h.f42849c;
        l0.j jVar = b2.f26220a;
        f26301b = C20310q.d(0.0f, 3, androidx.compose.ui.unit.h.a(0.1f));
        n.a aVar2 = l0.n.f413402b;
        l0.n.a((Float.floatToRawIntBits(0.5f) << 32) | (Float.floatToRawIntBits(0.5f) & 4294967295L));
        g.a aVar3 = l0.g.f413384b;
        l0.g.a((Float.floatToRawIntBits(0.5f) << 32) | (Float.floatToRawIntBits(0.5f) & 4294967295L));
        j.a aVar4 = l0.j.f413388e;
        int i12 = kotlin.jvm.internal.J.f406821a;
        q.a aVar5 = androidx.compose.ui.unit.q.f42862b;
        androidx.compose.ui.unit.q.a(b2.a());
        u.a aVar6 = androidx.compose.ui.unit.u.f42871b;
        androidx.compose.ui.unit.u.a(b2.b());
    }

    @InterfaceC22132o
    @Y61.k
    public static final I3 a(float f12, @Y61.l G1 g12, @Y61.l androidx.compose.runtime.A a12, int i12, int i13) {
        V v12 = g12;
        if ((i13 & 2) != 0) {
            v12 = f26301b;
        }
        return c(androidx.compose.ui.unit.h.a(f12), J1.f26077c, v12, null, "DpAnimation", a12, (i12 << 3) & 896, 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002d  */
    @androidx.compose.runtime.InterfaceC22132o
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.runtime.I3 b(float r10, @Y61.l androidx.compose.animation.core.G1 r11, @Y61.l java.lang.String r12, @Y61.l androidx.compose.runtime.A r13, int r14, int r15) {
        /*
            r0 = r15 & 2
            androidx.compose.animation.core.R0<java.lang.Float> r1 = androidx.compose.animation.core.C20283h.f26300a
            if (r0 == 0) goto L7
            r11 = r1
        L7:
            r15 = r15 & 8
            if (r15 == 0) goto Ld
            java.lang.String r12 = "FloatAnimation"
        Ld:
            r6 = r12
            r12 = 1008981770(0x3c23d70a, float:0.01)
            r15 = 3
            if (r11 != r1) goto L41
            r11 = 1125558999(0x4316aad7, float:150.66734)
            r13.C(r11)
            boolean r11 = r13.k(r12)
            java.lang.Object r0 = r13.t()
            if (r11 != 0) goto L2d
            androidx.compose.runtime.A$a r11 = androidx.compose.runtime.A.f37866a
            r11.getClass()
            androidx.compose.runtime.A$a$a r11 = androidx.compose.runtime.A.a.f37868b
            if (r0 != r11) goto L39
        L2d:
            java.lang.Float r11 = java.lang.Float.valueOf(r12)
            r0 = 0
            androidx.compose.animation.core.R0 r0 = androidx.compose.animation.core.C20310q.d(r0, r15, r11)
            r13.H(r0)
        L39:
            r11 = r0
            androidx.compose.animation.core.R0 r11 = (androidx.compose.animation.core.R0) r11
            r13.h()
        L3f:
            r4 = r11
            goto L4b
        L41:
            r0 = 1125668925(0x4318583d, float:152.34468)
            r13.C(r0)
            r13.h()
            goto L3f
        L4b:
            java.lang.Float r2 = java.lang.Float.valueOf(r10)
            int r10 = kotlin.jvm.internal.A.f406820a
            androidx.compose.animation.core.H1<java.lang.Float, androidx.compose.animation.core.t> r3 = androidx.compose.animation.core.J1.f26075a
            java.lang.Float r5 = java.lang.Float.valueOf(r12)
            int r10 = r14 << 3
            r11 = 57344(0xe000, float:8.0356E-41)
            r8 = r10 & r11
            r9 = 0
            r7 = r13
            androidx.compose.runtime.I3 r10 = c(r2, r3, r4, r5, r6, r7, r8, r9)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20283h.b(float, androidx.compose.animation.core.G1, java.lang.String, androidx.compose.runtime.A, int, int):androidx.compose.runtime.I3");
    }

    @InterfaceC22132o
    @Y61.k
    public static final I3 c(Object obj, @Y61.k H1 h12, @Y61.l InterfaceC20307p interfaceC20307p, @Y61.l Float f12, @Y61.l String str, @Y61.l androidx.compose.runtime.A a12, int i12, int i13) {
        if ((i13 & 8) != 0) {
            f12 = null;
        }
        Object objT = a12.t();
        androidx.compose.runtime.A.f37866a.getClass();
        Object obj2 = A.a.f37868b;
        if (objT == obj2) {
            objT = C22126m3.g(null);
            a12.H(objT);
        }
        InterfaceC22204y1 interfaceC22204y1 = (InterfaceC22204y1) objT;
        Object objT2 = a12.t();
        if (objT2 == obj2) {
            objT2 = new C20268c(obj, h12, f12, str);
            a12.H(objT2);
        }
        C20268c c20268c = (C20268c) objT2;
        InterfaceC22204y1 interfaceC22204y1M = C22126m3.m(null, a12);
        if (f12 != null && (interfaceC20307p instanceof R0)) {
            R0 r02 = (R0) interfaceC20307p;
            if (!kotlin.jvm.internal.L.f(r02.f26127c, f12)) {
                interfaceC20307p = new R0(r02.f26125a, r02.f26126b, f12);
            }
        }
        InterfaceC22204y1 interfaceC22204y1M2 = C22126m3.m(interfaceC20307p, a12);
        Object objT3 = a12.t();
        if (objT3 == obj2) {
            objT3 = kotlinx.coroutines.channels.A.a(-1, null, null, 6);
            a12.H(objT3);
        }
        InterfaceC43123w interfaceC43123w = (InterfaceC43123w) objT3;
        boolean zU2 = a12.u(interfaceC43123w) | a12.u(obj);
        Object objT4 = a12.t();
        if (zU2 || objT4 == obj2) {
            objT4 = new C20277f(interfaceC43123w, obj);
            a12.H(objT4);
        }
        C22128n0 c22128n0 = C22187u0.f38775a;
        a12.w((Y41.a) objT4);
        boolean zU3 = a12.u(interfaceC43123w) | a12.u(c20268c) | a12.B(interfaceC22204y1M2) | a12.B(interfaceC22204y1M);
        Object objT5 = a12.t();
        if (zU3 || objT5 == obj2) {
            objT5 = new C20280g(interfaceC43123w, c20268c, interfaceC22204y1M2, interfaceC22204y1M, null);
            a12.H(objT5);
        }
        C22187u0.d((Y41.p) objT5, a12, interfaceC43123w);
        I3 i32 = (I3) interfaceC22204y1.getF42167b();
        return i32 == null ? c20268c.f26225d : i32;
    }
}
