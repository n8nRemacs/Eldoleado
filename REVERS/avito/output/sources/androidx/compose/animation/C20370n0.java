package androidx.compose.animation;

import androidx.compose.runtime.C22082i3;
import androidx.compose.ui.layout.InterfaceC22358f;
import androidx.compose.ui.layout.InterfaceC22362h;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.unit.C22712b;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: AnimateBoundsModifier.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0001\u0018\u00002\u00020\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/n0;", "Landroidx/compose/ui/layout/f;", "Landroidx/compose/ui/v$d;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@InterfaceC20374o1
/* renamed from: androidx.compose.animation.n0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20370n0 extends v.d implements InterfaceC22358f {

    /* renamed from: p, reason: collision with root package name */
    @Y61.k
    public final C20379q0 f26528p = new C20379q0();

    /* compiled from: AnimateBoundsModifier.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.n0$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {
        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            throw null;
        }
    }

    @Override // androidx.compose.ui.layout.InterfaceC22358f
    public final boolean M1(long j12) {
        long jC2 = androidx.compose.ui.unit.v.c(j12);
        C20379q0 c20379q0 = this.f26528p;
        if (c20379q0.f26550a != 9205357640488583168L && !androidx.compose.ui.unit.u.c(androidx.compose.ui.unit.v.b(jC2), androidx.compose.ui.unit.v.b(c20379q0.f26550a))) {
            c20379q0.f26551b = true;
        }
        c20379q0.f26550a = jC2;
        if (c20379q0.f26553d == 9205357640488583168L) {
            c20379q0.f26553d = jC2;
        }
        return !c20379q0.a();
    }

    @Override // androidx.compose.ui.layout.InterfaceC22358f
    public final boolean R0(@Y61.k K0.a aVar, @Y61.k androidx.compose.ui.layout.A a12) {
        Y1();
        this.f26528p.getClass();
        if (aVar.b() == null) {
            return !r2.a();
        }
        throw null;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22358f
    @Y61.k
    public final InterfaceC22367j0 l1(@Y61.k InterfaceC22362h interfaceC22362h, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        C20379q0 c20379q0 = this.f26528p;
        long jG2 = c20379q0.f26553d;
        if (jG2 == 9205357640488583168L) {
            jG2 = androidx.compose.ui.unit.v.c(interfaceC22362h.Z());
        }
        l0.j jVar = c20379q0.a() ? null : (l0.j) ((C22082i3) c20379q0.f26554e).getF42167b();
        if (jVar != null) {
            jG2 = jVar.g();
        }
        androidx.compose.ui.unit.u.a(androidx.compose.ui.unit.v.b(jG2));
        C22712b.a(j12);
        throw null;
    }

    @Override // androidx.compose.ui.v.d
    public final void c2() {
    }
}
