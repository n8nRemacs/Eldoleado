package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.animation.core.C20321u;
import androidx.compose.runtime.C22082i3;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.graphics.InterfaceC22276o0;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.unit.C22713c;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.q;
import androidx.compose.ui.unit.u;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: EnterExitTransition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/i1;", "Landroidx/compose/animation/u1;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.i1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20356i1 extends AbstractC20391u1 {

    /* renamed from: A, reason: collision with root package name */
    @Y61.k
    public final Y41.l<C20288i1.b<EnterExitState>, androidx.compose.animation.core.V<androidx.compose.ui.unit.q>> f26470A;

    /* renamed from: p, reason: collision with root package name */
    @Y61.k
    public C20288i1<EnterExitState> f26471p;

    /* renamed from: q, reason: collision with root package name */
    @Y61.l
    public C20288i1<EnterExitState>.a<androidx.compose.ui.unit.u, C20321u> f26472q;

    /* renamed from: r, reason: collision with root package name */
    @Y61.l
    public C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> f26473r;

    /* renamed from: s, reason: collision with root package name */
    @Y61.l
    public C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> f26474s;

    /* renamed from: t, reason: collision with root package name */
    @Y61.k
    public AbstractC20359j1 f26475t;

    /* renamed from: u, reason: collision with root package name */
    @Y61.k
    public AbstractC20365l1 f26476u;

    /* renamed from: v, reason: collision with root package name */
    @Y61.k
    public Y41.a<Boolean> f26477v;

    /* renamed from: w, reason: collision with root package name */
    @Y61.k
    public InterfaceC20385s1 f26478w;

    /* renamed from: x, reason: collision with root package name */
    public long f26479x = C20355i0.f26469a;

    /* renamed from: y, reason: collision with root package name */
    @Y61.l
    public InterfaceC22215f f26480y;

    /* renamed from: z, reason: collision with root package name */
    @Y61.k
    public final Y41.l<C20288i1.b<EnterExitState>, androidx.compose.animation.core.V<androidx.compose.ui.unit.u>> f26481z;

    /* compiled from: EnterExitTransition.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.i1$a */
    public /* synthetic */ class a {
        static {
            int[] iArr = new int[EnterExitState.values().length];
            try {
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                EnterExitState enterExitState = EnterExitState.f25872b;
                iArr[0] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                EnterExitState enterExitState2 = EnterExitState.f25872b;
                iArr[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$b */
    public static final class b extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f26482l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(androidx.compose.ui.layout.K0 k02) {
            super(1);
            this.f26482l = k02;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            aVar.e(this.f26482l, 0, 0, 0.0f);
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$c */
    public static final class c extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f26483l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ long f26484m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ long f26485n;

        /* renamed from: o, reason: collision with root package name */
        public final /* synthetic */ Y41.l<InterfaceC22276o0, kotlin.G0> f26486o;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public c(androidx.compose.ui.layout.K0 k02, long j12, long j13, Y41.l<? super InterfaceC22276o0, kotlin.G0> lVar) {
            super(1);
            this.f26483l = k02;
            this.f26484m = j12;
            this.f26485n = j13;
            this.f26486o = lVar;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            K0.a aVar2 = aVar;
            q.a aVar3 = androidx.compose.ui.unit.q.f42862b;
            long j12 = this.f26484m;
            long j13 = this.f26485n;
            androidx.compose.ui.layout.K0 k02 = this.f26483l;
            aVar2.getClass();
            K0.a.a(k02, aVar2);
            k02.k0(androidx.compose.ui.unit.q.e(((((int) (j12 >> 32)) + ((int) (j13 >> 32))) << 32) | ((((int) (j12 & 4294967295L)) + ((int) (j13 & 4294967295L))) & 4294967295L), k02.f40349f), 0.0f, this.f26486o);
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$d */
    public static final class d extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f26487l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(androidx.compose.ui.layout.K0 k02) {
            super(1);
            this.f26487l = k02;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            aVar.e(this.f26487l, 0, 0, 0.0f);
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/EnterExitState;", "it", "Landroidx/compose/ui/unit/u;", "invoke-YEO4UFw", "(Landroidx/compose/animation/EnterExitState;)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$e */
    public static final class e extends kotlin.jvm.internal.N implements Y41.l<EnterExitState, androidx.compose.ui.unit.u> {

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ long f26489m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public e(long j12) {
            super(1);
            this.f26489m = j12;
        }

        @Override // Y41.l
        public final androidx.compose.ui.unit.u invoke(EnterExitState enterExitState) {
            Y41.l<androidx.compose.ui.unit.u, androidx.compose.ui.unit.u> lVar;
            Y41.l<androidx.compose.ui.unit.u, androidx.compose.ui.unit.u> lVar2;
            C20356i1 c20356i1 = C20356i1.this;
            c20356i1.getClass();
            int iOrdinal = enterExitState.ordinal();
            long j12 = this.f26489m;
            if (iOrdinal == 0) {
                C20381r0 c20381r0 = c20356i1.f26475t.getF26503c().f26627c;
                if (c20381r0 != null && (lVar = c20381r0.f26568b) != null) {
                    j12 = lVar.invoke(androidx.compose.ui.unit.u.a(j12)).f42872a;
                }
            } else if (iOrdinal != 1) {
                if (iOrdinal != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                C20381r0 c20381r02 = c20356i1.f26476u.getF26511d().f26627c;
                if (c20381r02 != null && (lVar2 = c20381r02.f26568b) != null) {
                    j12 = lVar2.invoke(androidx.compose.ui.unit.u.a(j12)).f42872a;
                }
            }
            return androidx.compose.ui.unit.u.a(j12);
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroidx/compose/animation/core/i1$b;", "Landroidx/compose/animation/EnterExitState;", "Landroidx/compose/animation/core/V;", "Landroidx/compose/ui/unit/q;", "invoke", "(Landroidx/compose/animation/core/i1$b;)Landroidx/compose/animation/core/V;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$f */
    public static final class f extends kotlin.jvm.internal.N implements Y41.l<C20288i1.b<EnterExitState>, androidx.compose.animation.core.V<androidx.compose.ui.unit.q>> {

        /* renamed from: l, reason: collision with root package name */
        public static final f f26490l = new f();

        public f() {
            super(1);
        }

        @Override // Y41.l
        public final androidx.compose.animation.core.V<androidx.compose.ui.unit.q> invoke(C20288i1.b<EnterExitState> bVar) {
            return H0.f25896c;
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/EnterExitState;", "it", "Landroidx/compose/ui/unit/q;", "invoke-Bjo55l4", "(Landroidx/compose/animation/EnterExitState;)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$g */
    public static final class g extends kotlin.jvm.internal.N implements Y41.l<EnterExitState, androidx.compose.ui.unit.q> {

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ long f26492m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public g(long j12) {
            super(1);
            this.f26492m = j12;
        }

        @Override // Y41.l
        public final androidx.compose.ui.unit.q invoke(EnterExitState enterExitState) {
            int iOrdinal;
            EnterExitState enterExitState2 = enterExitState;
            C20356i1 c20356i1 = C20356i1.this;
            long jD2 = 0;
            if (c20356i1.f26480y == null || c20356i1.k2() == null || kotlin.jvm.internal.L.f(c20356i1.f26480y, c20356i1.k2()) || (iOrdinal = enterExitState2.ordinal()) == 0 || iOrdinal == 1) {
                androidx.compose.ui.unit.q.f42862b.getClass();
            } else {
                if (iOrdinal != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                C20381r0 c20381r0 = c20356i1.f26476u.getF26511d().f26627c;
                if (c20381r0 != null) {
                    long j12 = this.f26492m;
                    long j13 = c20381r0.f26568b.invoke(androidx.compose.ui.unit.u.a(j12)).f42872a;
                    InterfaceC22215f interfaceC22215fK2 = c20356i1.k2();
                    LayoutDirection layoutDirection = LayoutDirection.f42838b;
                    jD2 = androidx.compose.ui.unit.q.d(interfaceC22215fK2.a(j12, j13, layoutDirection), c20356i1.f26480y.a(j12, j13, layoutDirection));
                } else {
                    androidx.compose.ui.unit.q.f42862b.getClass();
                }
            }
            return androidx.compose.ui.unit.q.a(jD2);
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/EnterExitState;", "it", "Landroidx/compose/ui/unit/q;", "invoke-Bjo55l4", "(Landroidx/compose/animation/EnterExitState;)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$h */
    public static final class h extends kotlin.jvm.internal.N implements Y41.l<EnterExitState, androidx.compose.ui.unit.q> {

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ long f26494m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public h(long j12) {
            super(1);
            this.f26494m = j12;
        }

        /* JADX WARN: Type inference failed for: r0v7, types: [Y41.l, kotlin.jvm.internal.N] */
        /* JADX WARN: Type inference failed for: r1v5, types: [Y41.l, kotlin.jvm.internal.N] */
        @Override // Y41.l
        public final androidx.compose.ui.unit.q invoke(EnterExitState enterExitState) {
            long j12;
            long j13;
            EnterExitState enterExitState2 = enterExitState;
            C20356i1 c20356i1 = C20356i1.this;
            u2 u2Var = c20356i1.f26475t.getF26503c().f26626b;
            long j14 = this.f26494m;
            long j15 = 0;
            if (u2Var != null) {
                j12 = ((androidx.compose.ui.unit.q) u2Var.f26596a.invoke(androidx.compose.ui.unit.u.a(j14))).f42864a;
            } else {
                androidx.compose.ui.unit.q.f42862b.getClass();
                j12 = 0;
            }
            u2 u2Var2 = c20356i1.f26476u.getF26511d().f26626b;
            if (u2Var2 != null) {
                j13 = ((androidx.compose.ui.unit.q) u2Var2.f26596a.invoke(androidx.compose.ui.unit.u.a(j14))).f42864a;
            } else {
                androidx.compose.ui.unit.q.f42862b.getClass();
                j13 = 0;
            }
            int iOrdinal = enterExitState2.ordinal();
            if (iOrdinal == 0) {
                j15 = j12;
            } else if (iOrdinal == 1) {
                androidx.compose.ui.unit.q.f42862b.getClass();
            } else {
                if (iOrdinal != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                j15 = j13;
            }
            return androidx.compose.ui.unit.q.a(j15);
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroidx/compose/animation/core/i1$b;", "Landroidx/compose/animation/EnterExitState;", "Landroidx/compose/animation/core/V;", "Landroidx/compose/ui/unit/u;", "invoke", "(Landroidx/compose/animation/core/i1$b;)Landroidx/compose/animation/core/V;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$i */
    public static final class i extends kotlin.jvm.internal.N implements Y41.l<C20288i1.b<EnterExitState>, androidx.compose.animation.core.V<androidx.compose.ui.unit.u>> {
        public i() {
            super(1);
        }

        @Override // Y41.l
        public final androidx.compose.animation.core.V<androidx.compose.ui.unit.u> invoke(C20288i1.b<EnterExitState> bVar) {
            C20288i1.b<EnterExitState> bVar2 = bVar;
            EnterExitState enterExitState = EnterExitState.f25872b;
            EnterExitState enterExitState2 = EnterExitState.f25873c;
            boolean zB2 = bVar2.b(enterExitState, enterExitState2);
            androidx.compose.animation.core.V<androidx.compose.ui.unit.u> v12 = null;
            C20356i1 c20356i1 = C20356i1.this;
            if (zB2) {
                C20381r0 c20381r0 = c20356i1.f26475t.getF26503c().f26627c;
                if (c20381r0 != null) {
                    v12 = c20381r0.f26569c;
                }
            } else if (bVar2.b(enterExitState2, EnterExitState.f25874d)) {
                C20381r0 c20381r02 = c20356i1.f26476u.getF26511d().f26627c;
                if (c20381r02 != null) {
                    v12 = c20381r02.f26569c;
                }
            } else {
                v12 = H0.f25897d;
            }
            return v12 == null ? H0.f25897d : v12;
        }
    }

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroidx/compose/animation/core/i1$b;", "Landroidx/compose/animation/EnterExitState;", "Landroidx/compose/animation/core/V;", "Landroidx/compose/ui/unit/q;", "invoke", "(Landroidx/compose/animation/core/i1$b;)Landroidx/compose/animation/core/V;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.i1$j */
    public static final class j extends kotlin.jvm.internal.N implements Y41.l<C20288i1.b<EnterExitState>, androidx.compose.animation.core.V<androidx.compose.ui.unit.q>> {
        public j() {
            super(1);
        }

        @Override // Y41.l
        public final androidx.compose.animation.core.V<androidx.compose.ui.unit.q> invoke(C20288i1.b<EnterExitState> bVar) {
            C20288i1.b<EnterExitState> bVar2 = bVar;
            EnterExitState enterExitState = EnterExitState.f25872b;
            EnterExitState enterExitState2 = EnterExitState.f25873c;
            boolean zB2 = bVar2.b(enterExitState, enterExitState2);
            C20356i1 c20356i1 = C20356i1.this;
            if (zB2) {
                u2 u2Var = c20356i1.f26475t.getF26503c().f26626b;
                return u2Var != null ? u2Var.f26597b : H0.f25896c;
            }
            if (!bVar2.b(enterExitState2, EnterExitState.f25874d)) {
                return H0.f25896c;
            }
            u2 u2Var2 = c20356i1.f26476u.getF26511d().f26626b;
            return u2Var2 != null ? u2Var2.f26597b : H0.f25896c;
        }
    }

    public C20356i1(@Y61.k C20288i1<EnterExitState> c20288i1, @Y61.l C20288i1<EnterExitState>.a<androidx.compose.ui.unit.u, C20321u> aVar, @Y61.l C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar2, @Y61.l C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar3, @Y61.k AbstractC20359j1 abstractC20359j1, @Y61.k AbstractC20365l1 abstractC20365l1, @Y61.k Y41.a<Boolean> aVar4, @Y61.k InterfaceC20385s1 interfaceC20385s1) {
        this.f26471p = c20288i1;
        this.f26472q = aVar;
        this.f26473r = aVar2;
        this.f26474s = aVar3;
        this.f26475t = abstractC20359j1;
        this.f26476u = abstractC20365l1;
        this.f26477v = aVar4;
        this.f26478w = interfaceC20385s1;
        C22713c.b(0, 0, 15);
        this.f26481z = new i();
        this.f26470A = new j();
    }

    @Override // androidx.compose.ui.v.d
    public final void c2() {
        this.f26479x = C20355i0.f26469a;
    }

    @Override // androidx.compose.ui.node.K
    @Y61.k
    public final InterfaceC22367j0 f(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        long j13;
        long j14;
        long j15;
        long jA2;
        if (this.f26471p.f26317a.a() == ((C22082i3) this.f26471p.f26320d).getF42167b()) {
            this.f26480y = null;
        } else if (this.f26480y == null) {
            InterfaceC22215f interfaceC22215fK2 = k2();
            if (interfaceC22215fK2 == null) {
                InterfaceC22215f.f39074a.getClass();
                interfaceC22215fK2 = InterfaceC22215f.a.f39076b;
            }
            this.f26480y = interfaceC22215fK2;
        }
        if (interfaceC22369k0.I1()) {
            androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(j12);
            long j16 = (k0I.f40345b << 32) | (k0I.f40346c & 4294967295L);
            u.a aVar = androidx.compose.ui.unit.u.f42871b;
            this.f26479x = j16;
            return interfaceC22369k0.L0((int) (j16 >> 32), (int) (j16 & 4294967295L), kotlin.collections.P0.c(), new b(k0I));
        }
        if (!this.f26477v.invoke().booleanValue()) {
            androidx.compose.ui.layout.K0 k0I2 = interfaceC22363h0.I(j12);
            return interfaceC22369k0.L0(k0I2.f40345b, k0I2.f40346c, kotlin.collections.P0.c(), new d(k0I2));
        }
        Y41.l<InterfaceC22276o0, kotlin.G0> lVarB = this.f26478w.b();
        androidx.compose.ui.layout.K0 k0I3 = interfaceC22363h0.I(j12);
        long j17 = (k0I3.f40345b << 32) | (k0I3.f40346c & 4294967295L);
        u.a aVar2 = androidx.compose.ui.unit.u.f42871b;
        long j18 = C20355i0.b(this.f26479x) ? this.f26479x : j17;
        C20288i1<EnterExitState>.a<androidx.compose.ui.unit.u, C20321u> aVar3 = this.f26472q;
        C20288i1.a.C1552a c1552aA = aVar3 != null ? aVar3.a(this.f26481z, new e(j18)) : null;
        if (c1552aA != null) {
            j17 = ((androidx.compose.ui.unit.u) c1552aA.getF42167b()).f42872a;
        }
        long jD2 = C22713c.d(j12, j17);
        C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar4 = this.f26473r;
        if (aVar4 != null) {
            j13 = ((androidx.compose.ui.unit.q) aVar4.a(f.f26490l, new g(j18)).getF42167b()).f42864a;
        } else {
            androidx.compose.ui.unit.q.f42862b.getClass();
            j13 = 0;
        }
        C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar5 = this.f26474s;
        if (aVar5 != null) {
            j14 = ((androidx.compose.ui.unit.q) aVar5.a(this.f26470A, new h(j18)).getF42167b()).f42864a;
        } else {
            androidx.compose.ui.unit.q.f42862b.getClass();
            j14 = 0;
        }
        InterfaceC22215f interfaceC22215f = this.f26480y;
        if (interfaceC22215f != null) {
            j15 = j14;
            jA2 = interfaceC22215f.a(j18, jD2, LayoutDirection.f42838b);
        } else {
            j15 = j14;
            androidx.compose.ui.unit.q.f42862b.getClass();
            jA2 = 0;
        }
        return interfaceC22369k0.L0((int) (jD2 >> 32), (int) (4294967295L & jD2), kotlin.collections.P0.c(), new c(k0I3, androidx.compose.ui.unit.q.e(jA2, j15), j13, lVarB));
    }

    @Y61.l
    public final InterfaceC22215f k2() {
        InterfaceC22215f interfaceC22215f;
        if (this.f26471p.f().b(EnterExitState.f25872b, EnterExitState.f25873c)) {
            C20381r0 c20381r0 = this.f26475t.getF26503c().f26627c;
            if (c20381r0 == null || (interfaceC22215f = c20381r0.f26567a) == null) {
                C20381r0 c20381r02 = this.f26476u.getF26511d().f26627c;
                if (c20381r02 != null) {
                    return c20381r02.f26567a;
                }
                return null;
            }
        } else {
            C20381r0 c20381r03 = this.f26476u.getF26511d().f26627c;
            if (c20381r03 == null || (interfaceC22215f = c20381r03.f26567a) == null) {
                C20381r0 c20381r04 = this.f26475t.getF26503c().f26627c;
                if (c20381r04 != null) {
                    return c20381r04.f26567a;
                }
                return null;
            }
        }
        return interfaceC22215f;
    }
}
