package androidx.compose.animation;

import androidx.compose.animation.core.C20268c;
import androidx.compose.animation.core.C20321u;
import androidx.compose.animation.core.InterfaceC20307p;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.ui.InterfaceC22215f;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.unit.C22713c;
import androidx.compose.ui.unit.u;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlinx.coroutines.C43259k;

/* compiled from: AnimationModifier.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/m2;", "Landroidx/compose/animation/u1;", "a", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class m2 extends AbstractC20391u1 {

    /* renamed from: p, reason: collision with root package name */
    @Y61.k
    public InterfaceC20307p<androidx.compose.ui.unit.u> f26512p;

    /* renamed from: q, reason: collision with root package name */
    @Y61.k
    public InterfaceC22215f f26513q;

    /* renamed from: r, reason: collision with root package name */
    @Y61.l
    public Y41.p<? super androidx.compose.ui.unit.u, ? super androidx.compose.ui.unit.u, kotlin.G0> f26514r;

    /* renamed from: s, reason: collision with root package name */
    public long f26515s;

    /* renamed from: t, reason: collision with root package name */
    public long f26516t;

    /* renamed from: u, reason: collision with root package name */
    public boolean f26517u;

    /* renamed from: v, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26518v;

    /* compiled from: AnimationModifier.kt */
    @androidx.compose.runtime.internal.P
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Landroidx/compose/animation/m2$a;", "", "Landroidx/compose/animation/core/c;", "Landroidx/compose/ui/unit/u;", "Landroidx/compose/animation/core/u;", "anim", "startSize", "<init>", "(Landroidx/compose/animation/core/c;JLkotlin/jvm/internal/w;)V", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class a {

        /* renamed from: a, reason: collision with root package name */
        @Y61.k
        public final C20268c<androidx.compose.ui.unit.u, C20321u> f26519a;

        /* renamed from: b, reason: collision with root package name */
        public long f26520b;

        public a() {
            throw null;
        }

        public a(C20268c c20268c, long j12, C42822w c42822w) {
            this.f26519a = c20268c;
            this.f26520b = j12;
        }

        public final boolean equals(@Y61.l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return kotlin.jvm.internal.L.f(this.f26519a, aVar.f26519a) && androidx.compose.ui.unit.u.c(this.f26520b, aVar.f26520b);
        }

        public final int hashCode() {
            int iHashCode = this.f26519a.hashCode() * 31;
            long j12 = this.f26520b;
            u.a aVar = androidx.compose.ui.unit.u.f42871b;
            return Long.hashCode(j12) + iHashCode;
        }

        @Y61.k
        public final String toString() {
            return "AnimData(anim=" + this.f26519a + ", startSize=" + ((Object) androidx.compose.ui.unit.u.d(this.f26520b)) + ')';
        }
    }

    /* compiled from: AnimationModifier.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class b extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ long f26522m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ int f26523n;

        /* renamed from: o, reason: collision with root package name */
        public final /* synthetic */ int f26524o;

        /* renamed from: p, reason: collision with root package name */
        public final /* synthetic */ InterfaceC22369k0 f26525p;

        /* renamed from: q, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f26526q;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(long j12, int i12, int i13, InterfaceC22369k0 interfaceC22369k0, androidx.compose.ui.layout.K0 k02) {
            super(1);
            this.f26522m = j12;
            this.f26523n = i12;
            this.f26524o = i13;
            this.f26525p = interfaceC22369k0;
            this.f26526q = k02;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            u.a aVar2 = androidx.compose.ui.unit.u.f42871b;
            K0.a.g(aVar, this.f26526q, m2.this.f26513q.a(this.f26522m, (this.f26524o & 4294967295L) | (this.f26523n << 32), this.f26525p.getF40449b()));
            return kotlin.G0.f406611a;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public m2(InterfaceC20307p interfaceC20307p, InterfaceC22215f interfaceC22215f, Y41.p pVar, int i12, C42822w c42822w) {
        if ((i12 & 2) != 0) {
            InterfaceC22215f.f39074a.getClass();
            interfaceC22215f = InterfaceC22215f.a.f39076b;
        }
        this(interfaceC20307p, interfaceC22215f, (i12 & 4) != 0 ? null : pVar);
    }

    @Override // androidx.compose.ui.v.d
    public final void c2() {
        this.f26515s = C20355i0.f26469a;
        this.f26517u = false;
    }

    @Override // androidx.compose.ui.v.d
    public final void e2() {
        ((C22082i3) this.f26518v).setValue(null);
    }

    @Override // androidx.compose.ui.node.K
    @Y61.k
    public final InterfaceC22367j0 f(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        InterfaceC22363h0 interfaceC22363h02;
        long j13;
        androidx.compose.ui.layout.K0 k0I;
        a aVar;
        long jD2;
        char c12;
        a aVar2;
        if (interfaceC22369k0.I1()) {
            this.f26516t = j12;
            this.f26517u = true;
            k0I = interfaceC22363h0.I(j12);
        } else {
            if (this.f26517u) {
                j13 = this.f26516t;
                interfaceC22363h02 = interfaceC22363h0;
            } else {
                interfaceC22363h02 = interfaceC22363h0;
                j13 = j12;
            }
            k0I = interfaceC22363h02.I(j13);
        }
        androidx.compose.ui.layout.K0 k02 = k0I;
        long j14 = (k02.f40346c & 4294967295L) | (k02.f40345b << 32);
        u.a aVar3 = androidx.compose.ui.unit.u.f42871b;
        if (interfaceC22369k0.I1()) {
            this.f26515s = j14;
            c12 = ' ';
            jD2 = j14;
        } else {
            long j15 = C20355i0.b(this.f26515s) ? this.f26515s : j14;
            InterfaceC22204y1 interfaceC22204y1 = this.f26518v;
            a aVar4 = (a) ((C22082i3) interfaceC22204y1).getF42167b();
            if (aVar4 != null) {
                C20268c<androidx.compose.ui.unit.u, C20321u> c20268c = aVar4.f26519a;
                boolean z12 = (androidx.compose.ui.unit.u.c(j15, c20268c.f().f42872a) || c20268c.g()) ? false : true;
                if (!androidx.compose.ui.unit.u.c(j15, ((androidx.compose.ui.unit.u) ((C22082i3) c20268c.f26227f).getF42167b()).f42872a) || z12) {
                    aVar4.f26520b = c20268c.f().f42872a;
                    aVar2 = aVar4;
                    C43259k.d(Y1(), null, null, new n2(aVar4, j15, this, null), 3);
                } else {
                    aVar2 = aVar4;
                }
                aVar = aVar2;
            } else {
                long j16 = 1;
                aVar = new a(new C20268c(androidx.compose.ui.unit.u.a(j15), androidx.compose.animation.core.J1.f26082h, androidx.compose.ui.unit.u.a((j16 << 32) | (j16 & 4294967295L)), null, 8, null), j15, null);
            }
            ((C22082i3) interfaceC22204y1).setValue(aVar);
            jD2 = C22713c.d(j12, aVar.f26519a.f().f42872a);
            c12 = ' ';
        }
        int i12 = (int) (jD2 >> c12);
        int i13 = (int) (jD2 & 4294967295L);
        return interfaceC22369k0.L0(i12, i13, kotlin.collections.P0.c(), new b(j14, i12, i13, interfaceC22369k0, k02));
    }

    public m2(@Y61.k InterfaceC20307p<androidx.compose.ui.unit.u> interfaceC20307p, @Y61.k InterfaceC22215f interfaceC22215f, @Y61.l Y41.p<? super androidx.compose.ui.unit.u, ? super androidx.compose.ui.unit.u, kotlin.G0> pVar) {
        this.f26512p = interfaceC20307p;
        this.f26513q = interfaceC22215f;
        this.f26514r = pVar;
        this.f26515s = C20355i0.f26469a;
        this.f26516t = C22713c.b(0, 0, 15);
        this.f26518v = C22126m3.g(null);
    }
}
