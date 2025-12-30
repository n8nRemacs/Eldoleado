package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.animation.core.C20310q;
import androidx.compose.animation.core.C20321u;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.I3;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.u;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0003\u0003\u0004\u0005¨\u0006\b²\u0006\u0014\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u00018\n@\nX\u008a\u008e\u0002"}, d2 = {"Landroidx/compose/animation/AnimatedContentTransitionScopeImpl;", "S", "Landroidx/compose/animation/y;", "a", "SizeModifierElement", "b", "", "shouldAnimateSize", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimatedContentTransitionScopeImpl<S> implements InterfaceC20401y<S> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final C20288i1<S> f25841a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public InterfaceC22215f f25842b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f25843c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final androidx.collection.R0<S, I3<androidx.compose.ui.unit.u>> f25844d;

    /* compiled from: AnimatedContent.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00030\u0002¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/AnimatedContentTransitionScopeImpl$SizeModifierElement;", "S", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/animation/AnimatedContentTransitionScopeImpl$b;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SizeModifierElement<S> extends AbstractC22430p0<b<S>> {

        /* renamed from: b, reason: collision with root package name */
        @Y61.l
        public final C20288i1<S>.a<androidx.compose.ui.unit.u, C20321u> f25845b;

        /* renamed from: c, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f25846c;

        /* renamed from: d, reason: collision with root package name */
        @Y61.k
        public final AnimatedContentTransitionScopeImpl<S> f25847d;

        public SizeModifierElement(@Y61.l C20288i1.a aVar, @Y61.k InterfaceC22204y1 interfaceC22204y1, @Y61.k AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl) {
            this.f25845b = aVar;
            this.f25846c = interfaceC22204y1;
            this.f25847d = animatedContentTransitionScopeImpl;
        }

        @Override // androidx.compose.ui.node.AbstractC22430p0
        public final v.d a() {
            b bVar = new b();
            bVar.f25849p = this.f25845b;
            bVar.f25850q = this.f25846c;
            bVar.f25851r = this.f25847d;
            bVar.f25852s = C20389u.f26593a;
            return bVar;
        }

        @Override // androidx.compose.ui.node.AbstractC22430p0
        public final void b(v.d dVar) {
            b bVar = (b) dVar;
            bVar.f25849p = this.f25845b;
            bVar.f25850q = this.f25846c;
            bVar.f25851r = this.f25847d;
        }

        public final boolean equals(@Y61.l Object obj) {
            if (obj instanceof SizeModifierElement) {
                SizeModifierElement sizeModifierElement = (SizeModifierElement) obj;
                if (kotlin.jvm.internal.L.f(sizeModifierElement.f25845b, this.f25845b) && kotlin.jvm.internal.L.f(sizeModifierElement.f25846c, this.f25846c)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            int iHashCode = this.f25847d.hashCode() * 31;
            C20288i1<S>.a<androidx.compose.ui.unit.u, C20321u> aVar = this.f25845b;
            return this.f25846c.hashCode() + ((iHashCode + (aVar != null ? aVar.hashCode() : 0)) * 31);
        }
    }

    /* compiled from: AnimatedContent.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/AnimatedContentTransitionScopeImpl$b;", "S", "Landroidx/compose/animation/u1;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class b<S> extends AbstractC20391u1 {

        /* renamed from: p, reason: collision with root package name */
        @Y61.l
        public C20288i1<S>.a<androidx.compose.ui.unit.u, C20321u> f25849p;

        /* renamed from: q, reason: collision with root package name */
        @Y61.k
        public InterfaceC22204y1 f25850q;

        /* renamed from: r, reason: collision with root package name */
        @Y61.k
        public AnimatedContentTransitionScopeImpl<S> f25851r;

        /* renamed from: s, reason: collision with root package name */
        public long f25852s;

        /* compiled from: AnimatedContent.kt */
        @kotlin.jvm.internal.s0
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0000*\u00020\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"S", "Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
        public static final class a extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

            /* renamed from: l, reason: collision with root package name */
            public final /* synthetic */ b<S> f25853l;

            /* renamed from: m, reason: collision with root package name */
            public final /* synthetic */ androidx.compose.ui.layout.K0 f25854m;

            /* renamed from: n, reason: collision with root package name */
            public final /* synthetic */ long f25855n;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(b<S> bVar, androidx.compose.ui.layout.K0 k02, long j12) {
                super(1);
                this.f25853l = bVar;
                this.f25854m = k02;
                this.f25855n = j12;
            }

            @Override // Y41.l
            public final kotlin.G0 invoke(K0.a aVar) {
                InterfaceC22215f interfaceC22215f = this.f25853l.f25851r.f25842b;
                u.a aVar2 = androidx.compose.ui.unit.u.f42871b;
                K0.a.g(aVar, this.f25854m, interfaceC22215f.a((r0.f40346c & 4294967295L) | (r0.f40345b << 32), this.f25855n, LayoutDirection.f42838b));
                return kotlin.G0.f406611a;
            }
        }

        /* compiled from: AnimatedContent.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"S", "Landroidx/compose/animation/core/i1$b;", "Landroidx/compose/animation/core/V;", "Landroidx/compose/ui/unit/u;", "invoke", "(Landroidx/compose/animation/core/i1$b;)Landroidx/compose/animation/core/V;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
        /* renamed from: androidx.compose.animation.AnimatedContentTransitionScopeImpl$b$b, reason: collision with other inner class name */
        public static final class C1547b extends kotlin.jvm.internal.N implements Y41.l<C20288i1.b<S>, androidx.compose.animation.core.V<androidx.compose.ui.unit.u>> {

            /* renamed from: l, reason: collision with root package name */
            public final /* synthetic */ b<S> f25856l;

            /* renamed from: m, reason: collision with root package name */
            public final /* synthetic */ long f25857m;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C1547b(b<S> bVar, long j12) {
                super(1);
                this.f25856l = bVar;
                this.f25857m = j12;
            }

            @Override // Y41.l
            public final androidx.compose.animation.core.V<androidx.compose.ui.unit.u> invoke(Object obj) {
                long j12;
                androidx.compose.animation.core.V<androidx.compose.ui.unit.u> vB2;
                C20288i1.b bVar = (C20288i1.b) obj;
                Object objA = bVar.a();
                b<S> bVar2 = this.f25856l;
                long j13 = 0;
                if (kotlin.jvm.internal.L.f(objA, bVar2.f25851r.a())) {
                    j12 = androidx.compose.ui.unit.u.c(bVar2.f25852s, C20389u.f26593a) ? this.f25857m : bVar2.f25852s;
                } else {
                    I3 i32 = (I3) bVar2.f25851r.f25844d.e(bVar.a());
                    if (i32 != null) {
                        j12 = ((androidx.compose.ui.unit.u) i32.getF42167b()).f42872a;
                    } else {
                        androidx.compose.ui.unit.u.f42871b.getClass();
                        j12 = 0;
                    }
                }
                I3 i33 = (I3) bVar2.f25851r.f25844d.e(bVar.d());
                if (i33 != null) {
                    j13 = ((androidx.compose.ui.unit.u) i33.getF42167b()).f42872a;
                } else {
                    androidx.compose.ui.unit.u.f42871b.getClass();
                }
                o2 o2Var = (o2) bVar2.f25850q.getF42167b();
                return (o2Var == null || (vB2 = o2Var.b(j12, j13)) == null) ? C20310q.d(400.0f, 5, null) : vB2;
            }
        }

        /* compiled from: AnimatedContent.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u00002\u0006\u0010\u0001\u001a\u00028\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"S", "it", "Landroidx/compose/ui/unit/u;", "invoke-YEO4UFw", "(Ljava/lang/Object;)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
        public static final class c extends kotlin.jvm.internal.N implements Y41.l<S, androidx.compose.ui.unit.u> {

            /* renamed from: l, reason: collision with root package name */
            public final /* synthetic */ b<S> f25858l;

            /* renamed from: m, reason: collision with root package name */
            public final /* synthetic */ long f25859m;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public c(b<S> bVar, long j12) {
                super(1);
                this.f25858l = bVar;
                this.f25859m = j12;
            }

            @Override // Y41.l
            public final androidx.compose.ui.unit.u invoke(Object obj) {
                long j12;
                b<S> bVar = this.f25858l;
                if (kotlin.jvm.internal.L.f(obj, bVar.f25851r.a())) {
                    j12 = androidx.compose.ui.unit.u.c(bVar.f25852s, C20389u.f26593a) ? this.f25859m : bVar.f25852s;
                } else {
                    I3<androidx.compose.ui.unit.u> i3E = bVar.f25851r.f25844d.e(obj);
                    if (i3E != null) {
                        j12 = i3E.getF42167b().f42872a;
                    } else {
                        androidx.compose.ui.unit.u.f42871b.getClass();
                        j12 = 0;
                    }
                }
                return androidx.compose.ui.unit.u.a(j12);
            }
        }

        public b() {
            throw null;
        }

        @Override // androidx.compose.ui.v.d
        public final void e2() {
            this.f25852s = C20389u.f26593a;
        }

        @Override // androidx.compose.ui.node.K
        @Y61.k
        public final InterfaceC22367j0 f(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
            long j13;
            androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(j12);
            if (interfaceC22369k0.I1()) {
                j13 = (k0I.f40345b << 32) | (k0I.f40346c & 4294967295L);
                u.a aVar = androidx.compose.ui.unit.u.f42871b;
            } else {
                C20288i1<S>.a<androidx.compose.ui.unit.u, C20321u> aVar2 = this.f25849p;
                if (aVar2 == null) {
                    j13 = (k0I.f40345b << 32) | (k0I.f40346c & 4294967295L);
                    u.a aVar3 = androidx.compose.ui.unit.u.f42871b;
                    this.f25852s = j13;
                } else {
                    long j14 = (k0I.f40346c & 4294967295L) | (k0I.f40345b << 32);
                    u.a aVar4 = androidx.compose.ui.unit.u.f42871b;
                    C20288i1.a.C1552a c1552aA = aVar2.a(new C1547b(this, j14), new c(this, j14));
                    this.f25851r.getClass();
                    j13 = ((androidx.compose.ui.unit.u) c1552aA.getF42167b()).f42872a;
                    this.f25852s = ((androidx.compose.ui.unit.u) c1552aA.getF42167b()).f42872a;
                }
            }
            return interfaceC22369k0.L0((int) (j13 >> 32), (int) (4294967295L & j13), kotlin.collections.P0.c(), new a(this, k0I, j13));
        }
    }

    public AnimatedContentTransitionScopeImpl(@Y61.k C20288i1 c20288i1, @Y61.k InterfaceC22215f interfaceC22215f) {
        this.f25841a = c20288i1;
        this.f25842b = interfaceC22215f;
        androidx.compose.ui.unit.u.f42871b.getClass();
        this.f25843c = C22126m3.g(androidx.compose.ui.unit.u.a(0L));
        this.f25844d = androidx.collection.i1.b();
    }

    @Override // androidx.compose.animation.core.C20288i1.b
    public final S a() {
        return this.f25841a.f().a();
    }

    @Override // androidx.compose.animation.InterfaceC20401y
    @Y61.k
    public final C20396w0 c(@Y61.k C20396w0 c20396w0, @Y61.l o2 o2Var) {
        c20396w0.f26606d = o2Var;
        return c20396w0;
    }

    @Override // androidx.compose.animation.core.C20288i1.b
    public final S d() {
        return this.f25841a.f().d();
    }

    /* compiled from: AnimatedContent.kt */
    @androidx.compose.runtime.internal.P
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/AnimatedContentTransitionScopeImpl$a;", "Landroidx/compose/ui/layout/H0;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a implements androidx.compose.ui.layout.H0 {

        /* renamed from: b, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f25848b;

        public a(boolean z12) {
            this.f25848b = C22126m3.g(Boolean.valueOf(z12));
        }

        @Override // androidx.compose.ui.layout.H0
        @Y61.k
        public final Object r(@Y61.k androidx.compose.ui.unit.d dVar, @Y61.l Object obj) {
            return this;
        }
    }
}
