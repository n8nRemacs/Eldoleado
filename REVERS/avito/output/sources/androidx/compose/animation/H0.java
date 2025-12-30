package androidx.compose.animation;

import androidx.compose.animation.core.C20310q;
import androidx.compose.animation.core.C20321u;
import androidx.compose.runtime.F3;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.i;
import androidx.compose.ui.unit.q;
import androidx.compose.ui.unit.u;
import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002¨\u0006\u0004²\u0006\u000e\u0010\u0001\u001a\u00020\u00008\n@\nX\u008a\u008e\u0002²\u0006\u000e\u0010\u0003\u001a\u00020\u00028\n@\nX\u008a\u008e\u0002"}, d2 = {"Landroidx/compose/animation/j1;", "activeEnter", "Landroidx/compose/animation/l1;", "activeExit", "animation_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class H0 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final androidx.compose.animation.core.H1<androidx.compose.ui.graphics.r1, C20321u> f25894a = androidx.compose.animation.core.J1.a(a.f25898l, b.f25899l);

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final androidx.compose.animation.core.R0<Float> f25895b = C20310q.d(400.0f, 5, null);

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public static final androidx.compose.animation.core.R0<androidx.compose.ui.unit.q> f25896c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public static final androidx.compose.animation.core.R0<androidx.compose.ui.unit.u> f25897d;

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/ui/graphics/r1;", "it", "Landroidx/compose/animation/core/u;", "invoke-__ExYCQ", "(J)Landroidx/compose/animation/core/u;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<androidx.compose.ui.graphics.r1, C20321u> {

        /* renamed from: l, reason: collision with root package name */
        public static final a f25898l = new a();

        public a() {
            super(1);
        }

        @Override // Y41.l
        public final C20321u invoke(androidx.compose.ui.graphics.r1 r1Var) {
            long j12 = r1Var.f39757a;
            return new C20321u(androidx.compose.ui.graphics.r1.c(j12), androidx.compose.ui.graphics.r1.d(j12));
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/core/u;", "it", "Landroidx/compose/ui/graphics/r1;", "invoke-LIALnN8", "(Landroidx/compose/animation/core/u;)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class b extends kotlin.jvm.internal.N implements Y41.l<C20321u, androidx.compose.ui.graphics.r1> {

        /* renamed from: l, reason: collision with root package name */
        public static final b f25899l = new b();

        public b() {
            super(1);
        }

        @Override // Y41.l
        public final androidx.compose.ui.graphics.r1 invoke(C20321u c20321u) {
            C20321u c20321u2 = c20321u;
            return androidx.compose.ui.graphics.r1.a(androidx.compose.ui.graphics.s1.a(c20321u2.f26407a, c20321u2.f26408b));
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/ui/unit/u;", "it", "Landroidx/compose/ui/unit/q;", "invoke-mHKZG7I", "(J)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class c extends kotlin.jvm.internal.N implements Y41.l<androidx.compose.ui.unit.u, androidx.compose.ui.unit.q> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ kotlin.jvm.internal.N f25900l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public c(Y41.l<? super Integer, Integer> lVar) {
            super(1);
            this.f25900l = (kotlin.jvm.internal.N) lVar;
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [Y41.l, kotlin.jvm.internal.N] */
        @Override // Y41.l
        public final androidx.compose.ui.unit.q invoke(androidx.compose.ui.unit.u uVar) {
            return androidx.compose.ui.unit.q.a((0 << 32) | (4294967295L & ((Number) this.f25900l.invoke(Integer.valueOf((int) (uVar.f42872a & 4294967295L)))).intValue()));
        }
    }

    static {
        q.a aVar = androidx.compose.ui.unit.q.f42862b;
        f25896c = C20310q.d(400.0f, 1, androidx.compose.ui.unit.q.a(androidx.compose.animation.core.b2.a()));
        u.a aVar2 = androidx.compose.ui.unit.u.f42871b;
        f25897d = C20310q.d(400.0f, 1, androidx.compose.ui.unit.u.a(androidx.compose.animation.core.b2.b()));
    }

    public static AbstractC20359j1 a(androidx.compose.animation.core.G1 g12, i.a aVar, int i12) {
        androidx.compose.animation.core.V vD2 = g12;
        if ((i12 & 1) != 0) {
            u.a aVar2 = androidx.compose.ui.unit.u.f42871b;
            vD2 = C20310q.d(400.0f, 1, androidx.compose.ui.unit.u.a(androidx.compose.animation.core.b2.b()));
        }
        if ((i12 & 2) != 0) {
            InterfaceC22215f.f39074a.getClass();
            aVar = InterfaceC22215f.a.f39090p;
        }
        return b(vD2, m(aVar), new S0(R0.f25959l));
    }

    @F3
    @Y61.k
    public static final AbstractC20359j1 b(@Y61.k androidx.compose.animation.core.V v12, @Y61.k androidx.compose.ui.i iVar, @Y61.k Y41.l lVar) {
        return new C20362k1(new y2(null, null, new C20381r0(iVar, lVar, v12, true), null, false, null, 59, null));
    }

    public static AbstractC20359j1 c(androidx.compose.animation.core.G1 g12, int i12) {
        androidx.compose.animation.core.V vD2 = g12;
        if ((i12 & 1) != 0) {
            u.a aVar = androidx.compose.ui.unit.u.f42871b;
            vD2 = C20310q.d(400.0f, 1, androidx.compose.ui.unit.u.a(androidx.compose.animation.core.b2.b()));
        }
        InterfaceC22215f.f39074a.getClass();
        return b(vD2, n(InterfaceC22215f.a.f39087m), new V0(U0.f25975l));
    }

    public static AbstractC20359j1 d(androidx.compose.animation.core.G1 g12, int i12) {
        androidx.compose.animation.core.V vD2 = g12;
        if ((i12 & 1) != 0) {
            vD2 = C20310q.d(400.0f, 5, null);
        }
        return new C20362k1(new y2(new C20377p1((i12 & 2) != 0 ? 0.0f : 0.6f, vD2), null, null, null, false, null, 62, null));
    }

    public static AbstractC20365l1 e(androidx.compose.animation.core.G1 g12, int i12) {
        androidx.compose.animation.core.V vD2 = g12;
        if ((i12 & 1) != 0) {
            vD2 = C20310q.d(400.0f, 5, null);
        }
        return new C20368m1(new y2(new C20377p1(0.0f, vD2), null, null, null, false, null, 62, null));
    }

    public static AbstractC20359j1 f(androidx.compose.animation.core.G1 g12) {
        androidx.compose.ui.graphics.r1.f39755b.getClass();
        return new C20362k1(new y2(null, null, null, new A1(0.92f, androidx.compose.ui.graphics.r1.f39756c, g12, null), false, null, 55, null));
    }

    public static AbstractC20365l1 g(androidx.compose.animation.core.G1 g12, i.a aVar, int i12) {
        androidx.compose.animation.core.V vD2 = g12;
        if ((i12 & 1) != 0) {
            u.a aVar2 = androidx.compose.ui.unit.u.f42871b;
            vD2 = C20310q.d(400.0f, 1, androidx.compose.ui.unit.u.a(androidx.compose.animation.core.b2.b()));
        }
        if ((i12 & 2) != 0) {
            InterfaceC22215f.f39074a.getClass();
            aVar = InterfaceC22215f.a.f39090p;
        }
        return h(vD2, m(aVar), new X0(W0.f25987l));
    }

    @F3
    @Y61.k
    public static final AbstractC20365l1 h(@Y61.k androidx.compose.animation.core.V v12, @Y61.k androidx.compose.ui.i iVar, @Y61.k Y41.l lVar) {
        return new C20368m1(new y2(null, null, new C20381r0(iVar, lVar, v12, true), null, false, null, 59, null));
    }

    public static AbstractC20365l1 i(androidx.compose.animation.core.G1 g12, int i12) {
        androidx.compose.animation.core.V vD2 = g12;
        if ((i12 & 1) != 0) {
            u.a aVar = androidx.compose.ui.unit.u.f42871b;
            vD2 = C20310q.d(400.0f, 1, androidx.compose.ui.unit.u.a(androidx.compose.animation.core.b2.b()));
        }
        InterfaceC22215f.f39074a.getClass();
        return h(vD2, n(InterfaceC22215f.a.f39087m), new C20252a1(Z0.f25999l));
    }

    @F3
    @Y61.k
    public static final AbstractC20359j1 j(@Y61.k Y41.l lVar, @Y61.k androidx.compose.animation.core.V v12) {
        return new C20362k1(new y2(null, new u2(new c(lVar), v12), null, null, false, null, 61, null));
    }

    public static /* synthetic */ AbstractC20359j1 k(Y41.l lVar) {
        q.a aVar = androidx.compose.ui.unit.q.f42862b;
        return j(lVar, C20310q.d(400.0f, 1, androidx.compose.ui.unit.q.a(androidx.compose.animation.core.b2.a())));
    }

    public static AbstractC20365l1 l(Y41.l lVar) {
        q.a aVar = androidx.compose.ui.unit.q.f42862b;
        return new C20368m1(new y2(null, new u2(new C20353h1(lVar), C20310q.d(400.0f, 1, androidx.compose.ui.unit.q.a(androidx.compose.animation.core.b2.a()))), null, null, false, null, 61, null));
    }

    public static final androidx.compose.ui.i m(InterfaceC22215f.b bVar) {
        InterfaceC22215f.f39074a.getClass();
        return kotlin.jvm.internal.L.f(bVar, InterfaceC22215f.a.f39088n) ? InterfaceC22215f.a.f39079e : kotlin.jvm.internal.L.f(bVar, InterfaceC22215f.a.f39090p) ? InterfaceC22215f.a.f39081g : InterfaceC22215f.a.f39080f;
    }

    public static final androidx.compose.ui.i n(i.b bVar) {
        InterfaceC22215f.f39074a.getClass();
        return kotlin.jvm.internal.L.f(bVar, InterfaceC22215f.a.f39085k) ? InterfaceC22215f.a.f39077c : kotlin.jvm.internal.L.f(bVar, InterfaceC22215f.a.f39087m) ? InterfaceC22215f.a.f39083i : InterfaceC22215f.a.f39080f;
    }
}
