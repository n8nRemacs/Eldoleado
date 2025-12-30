package androidx.compose.foundation;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.C22061e3;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.InterfaceC22391w;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.unit.C22712b;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: Scroll.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/foundation/b2;", "Landroidx/compose/ui/node/K;", "Landroidx/compose/ui/node/T0;", "Landroidx/compose/ui/v$d;", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class b2 extends v.d implements androidx.compose.ui.node.K, androidx.compose.ui.node.T0 {

    /* renamed from: p, reason: collision with root package name */
    @Y61.k
    public d2 f26956p;

    /* renamed from: q, reason: collision with root package name */
    public boolean f26957q;

    /* compiled from: Scroll.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Float;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a extends kotlin.jvm.internal.N implements Y41.a<Float> {
        public a() {
            super(0);
        }

        @Override // Y41.a
        public final Float invoke() {
            return Float.valueOf(((C22061e3) b2.this.f26956p.f27081a).e());
        }
    }

    /* compiled from: Scroll.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Float;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class b extends kotlin.jvm.internal.N implements Y41.a<Float> {
        public b() {
            super(0);
        }

        @Override // Y41.a
        public final Float invoke() {
            return Float.valueOf(b2.this.f26956p.g());
        }
    }

    /* compiled from: Scroll.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class c extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ int f26961m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f26962n;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(int i12, androidx.compose.ui.layout.K0 k02) {
            super(1);
            this.f26961m = i12;
            this.f26962n = k02;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            K0.a aVar2 = aVar;
            b2 b2Var = b2.this;
            int iE2 = ((C22061e3) b2Var.f26956p.f27081a).e();
            if (iE2 < 0) {
                iE2 = 0;
            }
            int i12 = this.f26961m;
            if (iE2 > i12) {
                iE2 = i12;
            }
            int i13 = -iE2;
            boolean z12 = b2Var.f26957q;
            int i14 = z12 ? 0 : i13;
            if (!z12) {
                i13 = 0;
            }
            c2 c2Var = new c2(i14, i13, this.f26962n);
            aVar2.f40350a = true;
            c2Var.invoke(aVar2);
            aVar2.f40350a = false;
            return kotlin.G0.f406611a;
        }
    }

    @Override // androidx.compose.ui.node.K
    public final int A(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        if (!this.f26957q) {
            i12 = Integer.MAX_VALUE;
        }
        return interfaceC22391w.R(i12);
    }

    @Override // androidx.compose.ui.node.T0
    public final void O(@Y61.k androidx.compose.ui.semantics.F f12) {
        androidx.compose.ui.semantics.C.w(f12);
        androidx.compose.ui.semantics.j jVar = new androidx.compose.ui.semantics.j(new a(), new b(), false);
        if (this.f26957q) {
            androidx.compose.ui.semantics.C.x(f12, jVar);
        } else {
            androidx.compose.ui.semantics.C.n(f12, jVar);
        }
    }

    @Override // androidx.compose.ui.node.K
    @Y61.k
    public final InterfaceC22367j0 f(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        C21080u0.a(j12, this.f26957q ? Orientation.f27425b : Orientation.f27426c);
        androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(C22712b.c(j12, 0, this.f26957q ? C22712b.j(j12) : Integer.MAX_VALUE, 0, this.f26957q ? Integer.MAX_VALUE : C22712b.i(j12), 5));
        int i12 = k0I.f40345b;
        int iJ2 = C22712b.j(j12);
        if (i12 > iJ2) {
            i12 = iJ2;
        }
        int i13 = k0I.f40346c;
        int i14 = C22712b.i(j12);
        if (i13 > i14) {
            i13 = i14;
        }
        int i15 = k0I.f40346c - i13;
        int i16 = k0I.f40345b - i12;
        if (!this.f26957q) {
            i15 = i16;
        }
        this.f26956p.i(i15);
        ((C22061e3) this.f26956p.f27082b).L3(this.f26957q ? i13 : i12);
        return interfaceC22369k0.L0(i12, i13, kotlin.collections.P0.c(), new c(i15, k0I));
    }

    @Override // androidx.compose.ui.node.K
    public final int m(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        if (this.f26957q) {
            i12 = Integer.MAX_VALUE;
        }
        return interfaceC22391w.G(i12);
    }

    @Override // androidx.compose.ui.node.K
    public final int x(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        if (!this.f26957q) {
            i12 = Integer.MAX_VALUE;
        }
        return interfaceC22391w.X(i12);
    }

    @Override // androidx.compose.ui.node.K
    public final int z(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        if (this.f26957q) {
            i12 = Integer.MAX_VALUE;
        }
        return interfaceC22391w.b0(i12);
    }
}
