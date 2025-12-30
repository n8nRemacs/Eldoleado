package androidx.compose.animation;

import androidx.compose.animation.N1;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.F3;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.runtime.snapshots.AbstractC22167l;
import java.util.Set;
import kotlin.C42727D;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: SharedTransitionScope.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u00012\u00020\u0002:\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/Q1;", "Landroidx/compose/animation/N1;", "Landroidx/compose/ui/layout/Z;", "b", "c", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@F3
@InterfaceC20374o1
/* loaded from: classes.dex */
public final class Q1 implements N1, androidx.compose.ui.layout.Z {

    /* renamed from: l, reason: collision with root package name */
    @Y61.k
    public static final b f25944l = new b(null);

    /* renamed from: m, reason: collision with root package name */
    @Y61.k
    public static final Object f25945m = C42727D.b(LazyThreadSafetyMode.f406616d, a.f25956l);

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final kotlinx.coroutines.T f25946b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.layout.Z f25947c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f25948d;

    /* renamed from: h, reason: collision with root package name */
    public androidx.compose.ui.layout.A f25952h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.l
    public androidx.compose.ui.layout.A f25953i;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f25949e = C22126m3.g(Boolean.FALSE);

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final Y41.a<kotlin.G0> f25950f = new d();

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public final Y41.l<N1, kotlin.G0> f25951g = new e();

    /* renamed from: j, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.runtime.snapshots.D<InterfaceC20388t1> f25954j = new androidx.compose.runtime.snapshots.D<>();

    /* renamed from: k, reason: collision with root package name */
    @Y61.k
    public final androidx.collection.R0<Object, K1> f25955k = new androidx.collection.R0<>(0, 1, null);

    /* compiled from: SharedTransitionScope.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Landroidx/compose/runtime/snapshots/H;", "invoke", "()Landroidx/compose/runtime/snapshots/H;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class a extends kotlin.jvm.internal.N implements Y41.a<androidx.compose.runtime.snapshots.H> {

        /* renamed from: l, reason: collision with root package name */
        public static final a f25956l = new a();

        public a() {
            super(0);
        }

        @Override // Y41.a
        public final androidx.compose.runtime.snapshots.H invoke() {
            androidx.compose.runtime.snapshots.H h12 = new androidx.compose.runtime.snapshots.H(P1.f25941l);
            AbstractC22167l.a aVar = AbstractC22167l.f38720e;
            Y41.p<Set<? extends Object>, AbstractC22167l, kotlin.G0> pVar = h12.f38630d;
            aVar.getClass();
            h12.f38634h = AbstractC22167l.a.d(pVar);
            return h12;
        }
    }

    /* compiled from: SharedTransitionScope.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/Q1$b;", "", "<init>", "()V", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class b {
        public /* synthetic */ b(C42822w c42822w) {
            this();
        }

        public b() {
        }
    }

    /* compiled from: SharedTransitionScope.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/Q1$c;", "Landroidx/compose/animation/N1$a;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class c implements N1.a {
    }

    /* compiled from: SharedTransitionScope.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lkotlin/G0;", "invoke", "()V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class d extends kotlin.jvm.internal.N implements Y41.a<kotlin.G0> {
        public d() {
            super(0);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x004a  */
        @Override // Y41.a
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final kotlin.G0 invoke() {
            /*
                r15 = this;
                androidx.compose.animation.Q1 r0 = androidx.compose.animation.Q1.this
                androidx.collection.R0<java.lang.Object, androidx.compose.animation.K1> r0 = r0.f25955k
                java.lang.Object[] r1 = r0.f25722b
                java.lang.Object[] r2 = r0.f25723c
                long[] r0 = r0.f25721a
                int r3 = r0.length
                int r3 = r3 + (-2)
                if (r3 < 0) goto L4f
                r4 = 0
                r5 = r4
            L11:
                r6 = r0[r5]
                long r8 = ~r6
                r10 = 7
                long r8 = r8 << r10
                long r8 = r8 & r6
                r10 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
                long r8 = r8 & r10
                int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                if (r8 == 0) goto L4a
                int r8 = r5 - r3
                int r8 = ~r8
                int r8 = r8 >>> 31
                r9 = 8
                int r8 = 8 - r8
                r10 = r4
            L2b:
                if (r10 >= r8) goto L48
                r11 = 255(0xff, double:1.26E-321)
                long r11 = r11 & r6
                r13 = 128(0x80, double:6.3E-322)
                int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
                if (r11 < 0) goto L3a
                long r6 = r6 >> r9
                int r10 = r10 + 1
                goto L2b
            L3a:
                int r0 = r5 << 3
                int r0 = r0 + r10
                r1 = r1[r0]
                r0 = r2[r0]
                androidx.compose.animation.K1 r0 = (androidx.compose.animation.K1) r0
                r0.getClass()
                r0 = 0
                throw r0
            L48:
                if (r8 != r9) goto L4f
            L4a:
                if (r5 == r3) goto L4f
                int r5 = r5 + 1
                goto L11
            L4f:
                kotlin.G0 r0 = kotlin.G0.f406611a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.Q1.d.invoke():java.lang.Object");
        }
    }

    /* compiled from: SharedTransitionScope.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/N1;", "it", "Lkotlin/G0;", "invoke", "(Landroidx/compose/animation/N1;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class e extends kotlin.jvm.internal.N implements Y41.l<N1, kotlin.G0> {
        public e() {
            super(1);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0054  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00b1  */
        /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.Object, kotlin.C] */
        @Override // Y41.l
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final kotlin.G0 invoke(androidx.compose.animation.N1 r24) {
            /*
                Method dump skipped, instructions count: 289
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.Q1.e.invoke(java.lang.Object):java.lang.Object");
        }
    }

    public Q1(@Y61.k androidx.compose.ui.layout.Z z12, @Y61.k kotlinx.coroutines.T t12) {
        this.f25946b = t12;
        this.f25947c = z12;
    }
}
