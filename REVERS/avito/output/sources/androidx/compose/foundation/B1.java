package androidx.compose.foundation;

import androidx.compose.animation.core.C20268c;
import androidx.compose.animation.core.C20274e;
import androidx.compose.animation.core.C20318t;
import androidx.compose.runtime.C22061e3;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.I3;
import androidx.compose.runtime.InterfaceC22196w1;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.runtime.U2;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.focus.InterfaceC22224i;
import androidx.compose.ui.graphics.InterfaceC22264l0;
import androidx.compose.ui.graphics.drawscope.a;
import androidx.compose.ui.graphics.layer.C22267c;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.InterfaceC22391w;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.node.C22421l;
import androidx.compose.ui.node.InterfaceC22438u;
import androidx.compose.ui.unit.C22712b;
import androidx.compose.ui.unit.C22713c;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.u;
import androidx.compose.ui.v;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.C42822w;
import kotlinx.coroutines.C43259k;

/* compiled from: BasicMarquee.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B7\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\r¢\u0006\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Landroidx/compose/foundation/B1;", "Landroidx/compose/ui/v$d;", "Landroidx/compose/ui/node/K;", "Landroidx/compose/ui/node/u;", "Landroidx/compose/ui/focus/i;", "", "iterations", "Landroidx/compose/foundation/z1;", "animationMode", "delayMillis", "initialDelayMillis", "Landroidx/compose/foundation/F1;", "spacing", "Landroidx/compose/ui/unit/h;", "velocity", "<init>", "(IIIILandroidx/compose/foundation/F1;FLkotlin/jvm/internal/w;)V", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class B1 extends v.d implements androidx.compose.ui.node.K, InterfaceC22438u, InterfaceC22224i {

    /* renamed from: B, reason: collision with root package name */
    @Y61.k
    public final I3 f26636B;

    /* renamed from: p, reason: collision with root package name */
    public int f26637p;

    /* renamed from: q, reason: collision with root package name */
    public int f26638q;

    /* renamed from: r, reason: collision with root package name */
    public int f26639r;

    /* renamed from: s, reason: collision with root package name */
    public float f26640s;

    /* renamed from: w, reason: collision with root package name */
    @Y61.l
    public kotlinx.coroutines.N0 f26644w;

    /* renamed from: x, reason: collision with root package name */
    @Y61.l
    public C22267c f26645x;

    /* renamed from: y, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26646y;

    /* renamed from: z, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26647z;

    /* renamed from: t, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22196w1 f26641t = U2.a(0);

    /* renamed from: u, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22196w1 f26642u = U2.a(0);

    /* renamed from: v, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26643v = C22126m3.g(Boolean.FALSE);

    /* renamed from: A, reason: collision with root package name */
    @Y61.k
    public final C20268c<Float, C20318t> f26635A = C20274e.a(0.0f);

    /* compiled from: BasicMarquee.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class a {
        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                LayoutDirection layoutDirection = LayoutDirection.f42838b;
                iArr[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: BasicMarquee.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class b extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0 f26648l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ B1 f26649m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(androidx.compose.ui.layout.K0 k02, B1 b12) {
            super(1);
            this.f26648l = k02;
            this.f26649m = b12;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            K0.a aVar2 = aVar;
            B1 b12 = this.f26649m;
            K0.a.m(aVar2, this.f26648l, kotlin.math.b.b((-b12.f26635A.f().floatValue()) * b12.k2()), 0, null, 12);
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: BasicMarquee.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.foundation.MarqueeModifierNode$restartAnimation$1", f = "BasicMarquee.kt", i = {}, l = {379, 380}, m = "invokeSuspend", n = {}, s = {})
    public static final class c extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public int f26650q;

        /* renamed from: r, reason: collision with root package name */
        public final /* synthetic */ kotlinx.coroutines.N0 f26651r;

        /* renamed from: s, reason: collision with root package name */
        public final /* synthetic */ B1 f26652s;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(kotlinx.coroutines.N0 n02, B1 b12, Continuation<? super c> continuation) {
            super(2, continuation);
            this.f26651r = n02;
            this.f26652s = b12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            return new c(this.f26651r, this.f26652s, continuation);
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((c) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object objG;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i12 = this.f26650q;
            if (i12 == 0) {
                C42729a0.b(obj);
                kotlinx.coroutines.N0 n02 = this.f26651r;
                if (n02 != null) {
                    this.f26650q = 1;
                    if (((kotlinx.coroutines.V0) n02).A(this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i12 != 1) {
                    if (i12 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    C42729a0.b(obj);
                    return kotlin.G0.f406611a;
                }
                C42729a0.b(obj);
            }
            this.f26650q = 2;
            B1 b12 = this.f26652s;
            if (b12.f26637p <= 0 || (objG = C43259k.g(X0.f26900b, new D1(b12, null), this)) != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                objG = kotlin.G0.f406611a;
            }
            if (objG == coroutine_suspended) {
                return coroutine_suspended;
            }
            return kotlin.G0.f406611a;
        }
    }

    public B1(int i12, int i13, int i14, int i15, F1 f12, float f13, C42822w c42822w) {
        this.f26637p = i12;
        this.f26638q = i14;
        this.f26639r = i15;
        this.f26640s = f13;
        this.f26646y = C22126m3.g(f12);
        this.f26647z = C22126m3.g(C21096z1.a(i13));
        this.f26636B = C22126m3.d(new E1(f12, this));
    }

    @Override // androidx.compose.ui.node.K
    public final int A(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return interfaceC22391w.R(Integer.MAX_VALUE);
    }

    @Override // androidx.compose.ui.v.d
    public final void c2() {
        C22267c c22267c = this.f26645x;
        InterfaceC22264l0 graphicsContext = C22421l.h(this).getGraphicsContext();
        if (c22267c != null) {
            graphicsContext.b(c22267c);
        }
        this.f26645x = graphicsContext.a();
        m2();
    }

    @Override // androidx.compose.ui.v.d
    public final void d2() {
        kotlinx.coroutines.N0 n02 = this.f26644w;
        if (n02 != null) {
            ((kotlinx.coroutines.V0) n02).c(null);
        }
        this.f26644w = null;
        C22267c c22267c = this.f26645x;
        if (c22267c != null) {
            C22421l.h(this).getGraphicsContext().b(c22267c);
            this.f26645x = null;
        }
    }

    @Override // androidx.compose.ui.node.K
    @Y61.k
    public final InterfaceC22367j0 f(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k InterfaceC22363h0 interfaceC22363h0, long j12) {
        androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(C22712b.c(j12, 0, Integer.MAX_VALUE, 0, 0, 13));
        int iG2 = C22713c.g(k0I.f40345b, j12);
        C22061e3 c22061e3 = (C22061e3) this.f26642u;
        c22061e3.L3(iG2);
        ((C22061e3) this.f26641t).L3(k0I.f40345b);
        return interfaceC22369k0.L0(c22061e3.e(), k0I.f40346c, kotlin.collections.P0.c(), new b(k0I, this));
    }

    public final float k2() {
        float fSignum = Math.signum(this.f26640s);
        int iOrdinal = C22421l.g(this).f40609C.ordinal();
        int i12 = 1;
        if (iOrdinal != 0) {
            if (iOrdinal != 1) {
                throw new NoWhenBranchMatchedException();
            }
            i12 = -1;
        }
        return fSignum * i12;
    }

    public final int l2() {
        return ((Number) this.f26636B.getF42167b()).intValue();
    }

    @Override // androidx.compose.ui.node.K
    public final int m(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return 0;
    }

    public final void m2() {
        kotlinx.coroutines.N0 n02 = this.f26644w;
        if (n02 != null) {
            ((kotlinx.coroutines.V0) n02).c(null);
        }
        if (this.f42893o) {
            this.f26644w = C43259k.d(Y1(), null, null, new c(n02, this, null), 3);
        }
    }

    @Override // androidx.compose.ui.node.InterfaceC22438u
    public final void t(@Y61.k androidx.compose.ui.node.P p12) {
        C20268c<Float, C20318t> c20268c = this.f26635A;
        float fFloatValue = c20268c.f().floatValue() * k2();
        float fK2 = k2();
        InterfaceC22196w1 interfaceC22196w1 = this.f26642u;
        InterfaceC22196w1 interfaceC22196w12 = this.f26641t;
        boolean z12 = fK2 != 1.0f ? c20268c.f().floatValue() < ((float) ((C22061e3) interfaceC22196w1).e()) : c20268c.f().floatValue() < ((float) ((C22061e3) interfaceC22196w12).e());
        boolean z13 = k2() != 1.0f ? c20268c.f().floatValue() > ((float) l2()) : c20268c.f().floatValue() > ((float) ((((C22061e3) interfaceC22196w12).e() + l2()) - ((C22061e3) interfaceC22196w1).e()));
        float fE2 = k2() == 1.0f ? ((C22061e3) interfaceC22196w12).e() + l2() : (-((C22061e3) interfaceC22196w12).e()) - l2();
        androidx.compose.ui.graphics.drawscope.a aVar = p12.f40671b;
        float fIntBitsToFloat = Float.intBitsToFloat((int) (aVar.i() & 4294967295L));
        C22267c c22267c = this.f26645x;
        if (c22267c != null) {
            u.a aVar2 = androidx.compose.ui.unit.u.f42871b;
            p12.S((kotlin.math.b.b(fIntBitsToFloat) & 4294967295L) | (((C22061e3) interfaceC22196w12).e() << 32), new C1(p12), c22267c);
        }
        float fE3 = fFloatValue + ((C22061e3) interfaceC22196w1).e();
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (aVar.i() & 4294967295L));
        androidx.compose.ui.graphics.S.f39317b.getClass();
        int i12 = androidx.compose.ui.graphics.S.f39318c;
        a.b bVar = aVar.f39478c;
        long jE2 = bVar.e();
        bVar.a().k();
        try {
            bVar.f39485a.b(fFloatValue, 0.0f, fE3, fIntBitsToFloat2, i12);
            C22267c c22267c2 = this.f26645x;
            if (c22267c2 != null) {
                if (z12) {
                    androidx.compose.ui.graphics.layer.h.a(p12, c22267c2);
                }
                if (z13) {
                    aVar.f39478c.f39485a.f(fE2, 0.0f);
                    try {
                        androidx.compose.ui.graphics.layer.h.a(p12, c22267c2);
                        aVar.f39478c.f39485a.f(-fE2, -0.0f);
                    } finally {
                    }
                }
            } else {
                if (z12) {
                    p12.y1();
                }
                if (z13) {
                    aVar.f39478c.f39485a.f(fE2, 0.0f);
                    try {
                        p12.y1();
                        aVar.f39478c.f39485a.f(-fE2, -0.0f);
                    } finally {
                    }
                }
            }
            H.C(bVar, jE2);
        } catch (Throwable th2) {
            H.C(bVar, jE2);
            throw th2;
        }
    }

    @Override // androidx.compose.ui.focus.InterfaceC22224i
    public final void u(@Y61.k FocusStateImpl focusStateImpl) {
        boolean zB2 = focusStateImpl.b();
        ((C22082i3) this.f26643v).setValue(Boolean.valueOf(zB2));
    }

    @Override // androidx.compose.ui.node.K
    public final int x(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return interfaceC22391w.X(Integer.MAX_VALUE);
    }

    @Override // androidx.compose.ui.node.K
    public final int z(@Y61.k androidx.compose.ui.node.X x12, @Y61.k InterfaceC22391w interfaceC22391w, int i12) {
        return interfaceC22391w.b0(i12);
    }
}
