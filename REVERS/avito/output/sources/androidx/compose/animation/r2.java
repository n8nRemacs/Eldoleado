package androidx.compose.animation;

import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.InterfaceC22391w;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.unit.C22712b;
import androidx.compose.ui.unit.C22713c;
import androidx.compose.ui.unit.u;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: SkipToLookaheadNode.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/r2;", "Landroidx/compose/ui/node/K;", "Landroidx/compose/ui/v$d;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class r2 extends v.d implements androidx.compose.ui.node.K {

    /* renamed from: p, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26573p;

    /* renamed from: q, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26574q;

    /* renamed from: r, reason: collision with root package name */
    @Y61.l
    public C22712b f26575r;

    /* renamed from: s, reason: collision with root package name */
    public long f26576s = C20355i0.f26469a;

    /* compiled from: SkipToLookaheadNode.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f26578m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ long f26579n;

        /* renamed from: o, reason: collision with root package name */
        public final /* synthetic */ InterfaceC22369k0 f26580o;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(androidx.compose.ui.layout.K0 k02, long j12, InterfaceC22369k0 interfaceC22369k0) {
            super(1);
            this.f26578m = k02;
            this.f26579n = j12;
            this.f26580o = interfaceC22369k0;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            K0.a aVar2 = aVar;
            r2 r2Var = r2.this;
            B1 b12 = (B1) ((C22082i3) r2Var.f26573p).getF42167b();
            if (!((Boolean) ((Y41.a) ((C22082i3) r2Var.f26574q).getF42167b()).invoke()).booleanValue() || b12 == null) {
                aVar2.e(this.f26578m, 0, 0, 0.0f);
                return kotlin.G0.f406611a;
            }
            long j12 = r2Var.f26576s;
            if (((int) (j12 >> 32)) != 0 && ((int) (j12 & 4294967295L)) != 0) {
                androidx.compose.ui.unit.v.c(j12);
                androidx.compose.ui.unit.v.c(this.f26579n);
                throw null;
            }
            long jFloatToRawIntBits = (Float.floatToRawIntBits(1.0f) << 32) | (Float.floatToRawIntBits(1.0f) & 4294967295L);
            int i12 = androidx.compose.ui.layout.S0.f40400b;
            kotlin.math.b.b(Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32)) * ((int) (r2Var.f26576s >> 32)));
            kotlin.math.b.b(Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L)) * ((int) (r2Var.f26576s & 4294967295L)));
            u.a aVar3 = androidx.compose.ui.unit.u.f42871b;
            this.f26580o.getF40524b();
            throw null;
        }
    }

    public r2(@Y61.l B1 b12, @Y61.k Y41.a<Boolean> aVar) {
        this.f26573p = C22126m3.g(b12);
        this.f26574q = C22126m3.g(aVar);
    }

    @Override // androidx.compose.ui.node.K
    public final int A(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return (x12.I1() || !C20355i0.b(this.f26576s)) ? interfaceC22391w.R(i12) : (int) (this.f26576s & 4294967295L);
    }

    @Override // androidx.compose.ui.node.K
    @Y61.k
    public final InterfaceC22367j0 f(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        if (interfaceC22369k0.I1()) {
            this.f26575r = C22712b.a(j12);
        }
        androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(this.f26575r.f42843a);
        long j13 = (k0I.f40345b << 32) | (k0I.f40346c & 4294967295L);
        u.a aVar = androidx.compose.ui.unit.u.f42871b;
        this.f26576s = j13;
        long jD2 = C22713c.d(j12, j13);
        return interfaceC22369k0.L0((int) (jD2 >> 32), (int) (jD2 & 4294967295L), kotlin.collections.P0.c(), new a(k0I, jD2, interfaceC22369k0));
    }

    @Override // androidx.compose.ui.node.K
    public final int m(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return (x12.I1() || !C20355i0.b(this.f26576s)) ? interfaceC22391w.G(i12) : (int) (this.f26576s >> 32);
    }

    @Override // androidx.compose.ui.node.K
    public final int x(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return (x12.I1() || !C20355i0.b(this.f26576s)) ? interfaceC22391w.X(i12) : (int) (this.f26576s & 4294967295L);
    }

    @Override // androidx.compose.ui.node.K
    public final int z(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return (x12.I1() || !C20355i0.b(this.f26576s)) ? interfaceC22391w.b0(i12) : (int) (this.f26576s >> 32);
    }
}
