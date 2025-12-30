package androidx.compose.foundation;

import androidx.compose.foundation.C20527h1;
import androidx.compose.foundation.interaction.d;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.layout.I0;
import androidx.compose.ui.node.AbstractC22425n;
import androidx.compose.ui.node.AbstractC22443w0;
import androidx.compose.ui.node.C22421l;
import androidx.compose.ui.node.C22437t0;
import androidx.compose.ui.node.InterfaceC22415i;
import androidx.compose.ui.node.InterfaceC22442w;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.semantics.C22553a;
import androidx.compose.ui.v;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.l0;
import kotlinx.coroutines.C43259k;
import kotlinx.coroutines.internal.C43238h;
import s0.C47949a;

/* compiled from: Focusable.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u0006:\u0001\u0011B3\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\u0016\b\u0002\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0018\u00010\u000b¢\u0006\u0004\b\u000f\u0010\u0010¨\u0006\u0012"}, d2 = {"Landroidx/compose/foundation/a1;", "Landroidx/compose/ui/node/n;", "Landroidx/compose/ui/node/T0;", "Landroidx/compose/ui/node/w;", "Landroidx/compose/ui/node/i;", "Landroidx/compose/ui/node/D0;", "Landroidx/compose/ui/node/TraversableNode;", "Landroidx/compose/foundation/interaction/m;", "interactionSource", "Landroidx/compose/ui/focus/o0;", "focusability", "Lkotlin/Function1;", "", "Lkotlin/G0;", "onFocusChange", "<init>", "(Landroidx/compose/foundation/interaction/m;ILY41/l;Lkotlin/jvm/internal/w;)V", "a", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.foundation.a1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20409a1 extends AbstractC22425n implements androidx.compose.ui.node.T0, InterfaceC22442w, InterfaceC22415i, androidx.compose.ui.node.D0, TraversableNode {

    /* renamed from: y, reason: collision with root package name */
    @Y61.k
    public static final a f26937y = new a(null);

    /* renamed from: r, reason: collision with root package name */
    @Y61.l
    public androidx.compose.foundation.interaction.m f26938r;

    /* renamed from: s, reason: collision with root package name */
    @Y61.l
    public final Y41.l<Boolean, kotlin.G0> f26939s;

    /* renamed from: t, reason: collision with root package name */
    @Y61.l
    public d.a f26940t;

    /* renamed from: u, reason: collision with root package name */
    @Y61.l
    public I0.a f26941u;

    /* renamed from: v, reason: collision with root package name */
    @Y61.l
    public AbstractC22443w0 f26942v;

    /* renamed from: w, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.ui.focus.g0 f26943w;

    /* renamed from: x, reason: collision with root package name */
    @Y61.l
    public Y41.a<Boolean> f26944x;

    /* compiled from: Focusable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/foundation/a1$a;", "", "<init>", "()V", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.foundation.a1$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    /* compiled from: Focusable.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.foundation.a1$b */
    public static final class b extends kotlin.jvm.internal.N implements Y41.a<Boolean> {
        public b() {
            super(0);
        }

        @Override // Y41.a
        public final Boolean invoke() {
            return Boolean.valueOf(androidx.compose.ui.focus.g0.u0(C20409a1.this.f26943w));
        }
    }

    public C20409a1() {
        throw null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public C20409a1(androidx.compose.foundation.interaction.m mVar, int i12, Y41.l lVar, int i13, C42822w c42822w) {
        if ((i13 & 2) != 0) {
            androidx.compose.ui.focus.o0.f39183b.getClass();
            i12 = androidx.compose.ui.focus.o0.f39184c;
        }
        this(mVar, i12, (i13 & 4) != 0 ? null : lVar, null);
    }

    @Override // androidx.compose.ui.node.D0
    public final void K1() {
        l0.h hVar = new l0.h();
        androidx.compose.ui.node.E0.a(this, new C20426f1(hVar, this));
        androidx.compose.ui.layout.I0 i02 = (androidx.compose.ui.layout.I0) hVar.f406842b;
        if (this.f26943w.q0().a()) {
            I0.a aVar = this.f26941u;
            if (aVar != null) {
                aVar.release();
            }
            this.f26941u = i02 != null ? i02.a() : null;
        }
    }

    @Override // androidx.compose.ui.node.T0
    public final void O(@Y61.k androidx.compose.ui.semantics.F f12) {
        boolean zA2 = this.f26943w.q0().a();
        kotlin.reflect.n<Object>[] nVarArr = androidx.compose.ui.semantics.C.f41719a;
        androidx.compose.ui.semantics.y.f41820a.getClass();
        androidx.compose.ui.semantics.E<Boolean> e12 = androidx.compose.ui.semantics.y.f41831l;
        kotlin.reflect.n<Object> nVar = androidx.compose.ui.semantics.C.f41719a[4];
        Boolean boolValueOf = Boolean.valueOf(zA2);
        e12.getClass();
        f12.a(e12, boolValueOf);
        if (this.f26944x == null) {
            this.f26944x = new b();
        }
        Y41.a<Boolean> aVar = this.f26944x;
        androidx.compose.ui.semantics.k.f41760a.getClass();
        f12.a(androidx.compose.ui.semantics.k.f41782w, new C22553a(null, aVar));
    }

    @Override // androidx.compose.ui.node.InterfaceC22442w
    public final void P(@Y61.k AbstractC22443w0 abstractC22443w0) {
        C20527h1 c20527h1O2;
        this.f26942v = abstractC22443w0;
        if (this.f26943w.q0().a()) {
            if (!abstractC22443w0.j1().f42893o) {
                C20527h1 c20527h1O22 = o2();
                if (c20527h1O22 != null) {
                    c20527h1O22.k2(null);
                    return;
                }
                return;
            }
            AbstractC22443w0 abstractC22443w02 = this.f26942v;
            if (abstractC22443w02 == null || !abstractC22443w02.j1().f42893o || (c20527h1O2 = o2()) == null) {
                return;
            }
            c20527h1O2.k2(this.f26942v);
        }
    }

    @Override // androidx.compose.ui.v.d
    public final void e2() {
        I0.a aVar = this.f26941u;
        if (aVar != null) {
            aVar.release();
        }
        this.f26941u = null;
    }

    public final void n2(androidx.compose.foundation.interaction.m mVar, androidx.compose.foundation.interaction.d dVar) {
        if (!this.f42893o) {
            mVar.a(dVar);
            return;
        }
        kotlinx.coroutines.N0 n02 = (kotlinx.coroutines.N0) ((C43238h) Y1()).f411905b.get(kotlinx.coroutines.N0.f410716u2);
        C43259k.d(Y1(), null, null, new C20412b1(mVar, dVar, n02 != null ? n02.k(new C20415c1(mVar, dVar)) : null, null), 3);
    }

    @Override // androidx.compose.ui.node.TraversableNode
    @Y61.k
    /* renamed from: o0 */
    public final Object getF39003r() {
        return f26937y;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12, types: [androidx.compose.ui.v$d] */
    /* JADX WARN: Type inference failed for: r4v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9, types: [androidx.compose.ui.v$d] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [androidx.compose.runtime.collection.e] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [androidx.compose.runtime.collection.e] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    public final C20527h1 o2() {
        TraversableNode traversableNode;
        C22437t0 c22437t0;
        if (!this.f42893o) {
            return null;
        }
        C20527h1.a aVar = C20527h1.f28124r;
        if (!this.f42880b.f42893o) {
            C47949a.b("visitAncestors called on an unattached node");
        }
        v.d dVar = this.f42880b.f42884f;
        LayoutNode layoutNodeG = C22421l.g(this);
        loop0: while (true) {
            if (layoutNodeG == null) {
                traversableNode = null;
                break;
            }
            if ((layoutNodeG.f40615I.f40878e.f42883e & 262144) != 0) {
                while (dVar != null) {
                    if ((dVar.f42882d & 262144) != 0) {
                        ?? eVar = 0;
                        AbstractC22425n abstractC22425nB = dVar;
                        while (abstractC22425nB != 0) {
                            if (abstractC22425nB instanceof TraversableNode) {
                                traversableNode = (TraversableNode) abstractC22425nB;
                                if (kotlin.jvm.internal.L.f(aVar, traversableNode.getF39003r())) {
                                    break loop0;
                                }
                            } else if ((abstractC22425nB.f42882d & 262144) != 0 && (abstractC22425nB instanceof AbstractC22425n)) {
                                v.d dVar2 = abstractC22425nB.f40861q;
                                int i12 = 0;
                                abstractC22425nB = abstractC22425nB;
                                eVar = eVar;
                                while (dVar2 != null) {
                                    if ((dVar2.f42882d & 262144) != 0) {
                                        i12++;
                                        eVar = eVar;
                                        if (i12 == 1) {
                                            abstractC22425nB = dVar2;
                                        } else {
                                            if (eVar == 0) {
                                                eVar = new androidx.compose.runtime.collection.e(new v.d[16], 0);
                                            }
                                            if (abstractC22425nB != 0) {
                                                eVar.b(abstractC22425nB);
                                                abstractC22425nB = 0;
                                            }
                                            eVar.b(dVar2);
                                        }
                                    }
                                    dVar2 = dVar2.f42885g;
                                    abstractC22425nB = abstractC22425nB;
                                    eVar = eVar;
                                }
                                if (i12 == 1) {
                                }
                            }
                            abstractC22425nB = C22421l.b(eVar);
                        }
                    }
                    dVar = dVar.f42884f;
                }
            }
            layoutNodeG = layoutNodeG.K();
            dVar = (layoutNodeG == null || (c22437t0 = layoutNodeG.f40615I) == null) ? null : c22437t0.f40877d;
        }
        if (traversableNode instanceof C20527h1) {
            return (C20527h1) traversableNode;
        }
        return null;
    }

    public final void p2(@Y61.l androidx.compose.foundation.interaction.m mVar) {
        d.a aVar;
        if (kotlin.jvm.internal.L.f(this.f26938r, mVar)) {
            return;
        }
        androidx.compose.foundation.interaction.m mVar2 = this.f26938r;
        if (mVar2 != null && (aVar = this.f26940t) != null) {
            mVar2.a(new d.b(aVar));
        }
        this.f26940t = null;
        this.f26938r = mVar;
    }

    public C20409a1(androidx.compose.foundation.interaction.m mVar, int i12, Y41.l lVar, C42822w c42822w) {
        this.f26938r = mVar;
        this.f26939s = lVar;
        FocusTargetNode focusTargetNode = new FocusTargetNode(i12, new C20420d1(2, this, C20409a1.class, "onFocusStateChange", "onFocusStateChange(Landroidx/compose/ui/focus/FocusState;Landroidx/compose/ui/focus/FocusState;)V", 0), null, 4, null);
        k2(focusTargetNode);
        this.f26943w = focusTargetNode;
    }
}
