package androidx.compose.animation;

import androidx.compose.ui.graphics.layer.C22267c;
import androidx.compose.ui.layout.InterfaceC22358f;
import androidx.compose.ui.layout.InterfaceC22362h;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.node.C22421l;
import androidx.compose.ui.node.InterfaceC22438u;
import androidx.compose.ui.v;
import kotlin.Metadata;
import l0.n;

/* compiled from: SharedContentNode.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/F1;", "Landroidx/compose/ui/layout/f;", "Landroidx/compose/ui/v$d;", "Landroidx/compose/ui/node/u;", "Landroidx/compose/ui/modifier/k;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class F1 extends v.d implements InterfaceC22358f, InterfaceC22438u, androidx.compose.ui.modifier.k {

    /* renamed from: p, reason: collision with root package name */
    @Y61.l
    public C22267c f25885p;

    /* compiled from: SharedContentNode.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f25886l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ F1 f25887m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(androidx.compose.ui.layout.K0 k02, F1 f12, long j12) {
            super(1);
            this.f25886l = k02;
            this.f25887m = f12;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            K0.a aVar2 = aVar;
            if (aVar2.b() == null) {
                aVar2.e(this.f25886l, 0, 0, 0.0f);
                return kotlin.G0.f406611a;
            }
            this.f25887m.getClass();
            throw null;
        }
    }

    /* compiled from: SharedContentNode.kt */
    @Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Landroidx/compose/ui/layout/A;", "invoke", "()Landroidx/compose/ui/layout/A;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class b extends kotlin.jvm.internal.N implements Y41.a<androidx.compose.ui.layout.A> {
        @Override // Y41.a
        public final androidx.compose.ui.layout.A invoke() {
            throw null;
        }
    }

    /* compiled from: SharedContentNode.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0001\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class c extends kotlin.jvm.internal.N implements Y41.a {
        static {
            new c();
        }

        public c() {
            super(0);
        }

        @Override // Y41.a
        public final /* bridge */ /* synthetic */ Object invoke() {
            return null;
        }
    }

    public F1() {
        throw null;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22358f
    public final boolean M1(long j12) {
        throw null;
    }

    @Override // androidx.compose.ui.modifier.k
    @Y61.k
    public final androidx.compose.ui.modifier.j c1() {
        return null;
    }

    @Override // androidx.compose.ui.v.d
    public final void c2() {
        androidx.compose.ui.modifier.s<M1> sVar = H1.f25901a;
        j1(sVar, null);
        throw null;
    }

    @Override // androidx.compose.ui.v.d
    public final void d2() {
        k2(null);
        throw null;
    }

    @Override // androidx.compose.ui.v.d
    public final void e2() {
        C22267c c22267c = this.f25885p;
        if (c22267c != null) {
            C22421l.h(this).getGraphicsContext().b(c22267c);
        }
        k2(C22421l.h(this).getGraphicsContext().a());
    }

    @Override // androidx.compose.ui.layout.InterfaceC22358f, androidx.compose.ui.node.K
    @Y61.k
    public final InterfaceC22367j0 f(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(j12);
        float f12 = k0I.f40345b;
        float f13 = k0I.f40346c;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f13) & 4294967295L) | (Float.floatToRawIntBits(f12) << 32);
        n.a aVar = l0.n.f413402b;
        return interfaceC22369k0.L0(k0I.f40345b, k0I.f40346c, kotlin.collections.P0.c(), new a(k0I, this, jFloatToRawIntBits));
    }

    public final void k2(C22267c c22267c) {
        if (c22267c != null) {
            throw null;
        }
        C22267c c22267c2 = this.f25885p;
        if (c22267c2 != null) {
            C22421l.h(this).getGraphicsContext().b(c22267c2);
        }
        this.f25885p = c22267c;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22358f
    @Y61.k
    public final InterfaceC22367j0 l1(@Y61.k InterfaceC22362h interfaceC22362h, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        throw null;
    }

    @Override // androidx.compose.ui.node.InterfaceC22438u
    public final void t(@Y61.k androidx.compose.ui.node.P p12) {
        throw null;
    }
}
