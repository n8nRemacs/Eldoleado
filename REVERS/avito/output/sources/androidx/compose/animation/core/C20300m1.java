package androidx.compose.animation.core;

import androidx.compose.runtime.A;
import androidx.compose.runtime.C22039c2;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.C22187u0;
import androidx.compose.runtime.InterfaceC22118l0;
import androidx.compose.runtime.InterfaceC22132o;
import androidx.compose.runtime.snapshots.AbstractC22167l;
import java.util.Set;
import kotlin.C42727D;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import okhttp3.internal.http2.Http2;

/* compiled from: Transition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.m1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20300m1 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final Y41.l<C0<?>, kotlin.G0> f26368a = null;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final Object f26369b;

    /* compiled from: Transition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Landroidx/compose/runtime/snapshots/H;", "invoke", "()Landroidx/compose/runtime/snapshots/H;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.m1$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.a<androidx.compose.runtime.snapshots.H> {

        /* renamed from: l, reason: collision with root package name */
        public static final a f26370l = new a();

        public a() {
            super(0);
        }

        @Override // Y41.a
        public final androidx.compose.runtime.snapshots.H invoke() {
            androidx.compose.runtime.snapshots.H h12 = new androidx.compose.runtime.snapshots.H(C20297l1.f26365l);
            AbstractC22167l.a aVar = AbstractC22167l.f38720e;
            Y41.p<Set<? extends Object>, AbstractC22167l, kotlin.G0> pVar = h12.f38630d;
            aVar.getClass();
            h12.f38634h = AbstractC22167l.a.d(pVar);
            return h12;
        }
    }

    /* compiled from: Transition.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\n\u0010\u0001\u001a\u0006\u0012\u0002\b\u00030\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/core/C0;", "it", "Lkotlin/G0;", "invoke", "(Landroidx/compose/animation/core/C0;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.m1$b */
    public static final class b extends kotlin.jvm.internal.N implements Y41.l<C0<?>, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public static final b f26371l = new b();

        public b() {
            super(1);
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(C0<?> c02) {
            throw null;
        }
    }

    /* compiled from: Transition.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.m1$c */
    public static final class c extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<S> f26372l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ C20288i1<S>.d<T, V> f26373m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ T f26374n;

        /* renamed from: o, reason: collision with root package name */
        public final /* synthetic */ T f26375o;

        /* renamed from: p, reason: collision with root package name */
        public final /* synthetic */ V<T> f26376p;

        /* renamed from: q, reason: collision with root package name */
        public final /* synthetic */ int f26377q;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(C20288i1<S> c20288i1, C20288i1<S>.d<T, V> dVar, T t12, T t13, V<T> v12, int i12) {
            super(2);
            this.f26372l = c20288i1;
            this.f26373m = dVar;
            this.f26374n = t12;
            this.f26375o = t13;
            this.f26376p = v12;
            this.f26377q = i12;
        }

        @Override // Y41.p
        public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
            num.intValue();
            int iA2 = C22066f2.a(this.f26377q | 1);
            T t12 = this.f26375o;
            V<T> v12 = this.f26376p;
            C20300m1.a(this.f26372l, this.f26373m, this.f26374n, t12, v12, a12, iA2);
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: Transition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\b\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001\"\b\b\u0002\u0010\u0003*\u00020\u0002*\u00020\u0004H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"S", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/runtime/n0;", "Landroidx/compose/runtime/l0;", "invoke", "(Landroidx/compose/runtime/n0;)Landroidx/compose/runtime/l0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.m1$d */
    public static final class d extends kotlin.jvm.internal.N implements Y41.l<C22128n0, InterfaceC22118l0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<S> f26378l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ C20288i1<S>.a<T, V> f26379m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(C20288i1<S> c20288i1, C20288i1<S>.a<T, V> aVar) {
            super(1);
            this.f26378l = c20288i1;
            this.f26379m = aVar;
        }

        @Override // Y41.l
        public final InterfaceC22118l0 invoke(C22128n0 c22128n0) {
            return new C20335y1(this.f26378l, this.f26379m);
        }
    }

    /* compiled from: Transition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"T", "Landroidx/compose/runtime/n0;", "Landroidx/compose/runtime/l0;", "invoke", "(Landroidx/compose/runtime/n0;)Landroidx/compose/runtime/l0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.m1$e */
    public static final class e extends kotlin.jvm.internal.N implements Y41.l<C22128n0, InterfaceC22118l0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<T> f26380l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public e(C20288i1<T> c20288i1) {
            super(1);
            this.f26380l = c20288i1;
        }

        @Override // Y41.l
        public final InterfaceC22118l0 invoke(C22128n0 c22128n0) {
            return new E1(this.f26380l);
        }
    }

    static {
        b bVar = b.f26371l;
        f26369b = C42727D.b(LazyThreadSafetyMode.f406616d, a.f26370l);
    }

    @InterfaceC22132o
    public static final <S, T, V extends AbstractC20330x> void a(C20288i1<S> c20288i1, C20288i1<S>.d<T, V> dVar, T t12, T t13, V<T> v12, androidx.compose.runtime.A a12, int i12) {
        int i13;
        androidx.compose.runtime.B bE2 = a12.E(867041821);
        if ((i12 & 6) == 0) {
            i13 = (bE2.B(c20288i1) ? 4 : 2) | i12;
        } else {
            i13 = i12;
        }
        if ((i12 & 48) == 0) {
            i13 |= bE2.B(dVar) ? 32 : 16;
        }
        if ((i12 & 384) == 0) {
            i13 |= (i12 & 512) == 0 ? bE2.B(t12) : bE2.u(t12) ? 256 : 128;
        }
        if ((i12 & 3072) == 0) {
            i13 |= (i12 & 4096) == 0 ? bE2.B(t13) : bE2.u(t13) ? 2048 : 1024;
        }
        if ((i12 & 24576) == 0) {
            i13 |= (32768 & i12) == 0 ? bE2.B(v12) : bE2.u(v12) ? Http2.INITIAL_MAX_FRAME_SIZE : 8192;
        }
        if (!bE2.p(i13 & 1, (i13 & 9363) != 9362)) {
            bE2.f();
        } else if (c20288i1.g()) {
            dVar.q(t12, t13, v12);
        } else {
            dVar.r(t13, v12);
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z != null) {
            c22039c2Z.f38184d = new c(c20288i1, dVar, t12, t13, v12, i12);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0061  */
    @androidx.compose.runtime.InterfaceC22132o
    @kotlin.X
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.animation.core.C20288i1 b(@Y61.k androidx.compose.animation.core.C20288i1 r10, androidx.compose.animation.EnterExitState r11, androidx.compose.animation.EnterExitState r12, @Y61.l androidx.compose.runtime.A r13, int r14) {
        /*
            r0 = r14 & 14
            r0 = r0 ^ 6
            r1 = 1
            r2 = 0
            r3 = 4
            if (r0 <= r3) goto Lf
            boolean r4 = r13.B(r10)
            if (r4 != 0) goto L13
        Lf:
            r4 = r14 & 6
            if (r4 != r3) goto L15
        L13:
            r4 = r1
            goto L16
        L15:
            r4 = r2
        L16:
            java.lang.Object r5 = r13.t()
            androidx.compose.runtime.A$a r6 = androidx.compose.runtime.A.f37866a
            if (r4 != 0) goto L25
            r6.getClass()
            androidx.compose.runtime.A$a$a r4 = androidx.compose.runtime.A.a.f37868b
            if (r5 != r4) goto L3f
        L25:
            androidx.compose.animation.core.i1 r5 = new androidx.compose.animation.core.i1
            androidx.compose.animation.core.t0 r4 = new androidx.compose.animation.core.t0
            r4.<init>(r11)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = r10.f26319c
            java.lang.String r9 = " > EnterExitTransition"
            java.lang.String r7 = AK.c.s(r7, r8, r9)
            r5.<init>(r4, r10, r7)
            r13.H(r5)
        L3f:
            androidx.compose.animation.core.i1 r5 = (androidx.compose.animation.core.C20288i1) r5
            if (r0 <= r3) goto L49
            boolean r0 = r13.B(r10)
            if (r0 != 0) goto L4f
        L49:
            r14 = r14 & 6
            if (r14 != r3) goto L4e
            goto L4f
        L4e:
            r1 = r2
        L4f:
            boolean r14 = r13.B(r5)
            r14 = r14 | r1
            java.lang.Object r0 = r13.t()
            if (r14 != 0) goto L61
            r6.getClass()
            androidx.compose.runtime.A$a$a r14 = androidx.compose.runtime.A.a.f37868b
            if (r0 != r14) goto L69
        L61:
            androidx.compose.animation.core.x1 r0 = new androidx.compose.animation.core.x1
            r0.<init>(r10, r5)
            r13.H(r0)
        L69:
            Y41.l r0 = (Y41.l) r0
            androidx.compose.runtime.C22187u0.a(r5, r0, r13)
            boolean r10 = r10.g()
            if (r10 == 0) goto L78
            r5.j(r11, r12)
            goto L84
        L78:
            r5.k(r12)
            androidx.compose.runtime.y1 r10 = r5.f26327k
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            androidx.compose.runtime.i3 r10 = (androidx.compose.runtime.C22082i3) r10
            r10.setValue(r11)
        L84:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20300m1.b(androidx.compose.animation.core.i1, androidx.compose.animation.EnterExitState, androidx.compose.animation.EnterExitState, androidx.compose.runtime.A, int):androidx.compose.animation.core.i1");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0055  */
    /* JADX WARN: Type inference failed for: r10v3, types: [Y41.l, kotlin.jvm.internal.N] */
    /* JADX WARN: Type inference failed for: r7v4, types: [Y41.l, kotlin.jvm.internal.N] */
    /* JADX WARN: Type inference failed for: r9v3, types: [Y41.l, kotlin.jvm.internal.N] */
    @androidx.compose.runtime.InterfaceC22132o
    @Y61.k
    @androidx.annotation.RestrictTo
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <S, T, V extends androidx.compose.animation.core.AbstractC20330x> androidx.compose.animation.core.C20288i1<S>.a<T, V> c(@Y61.k androidx.compose.animation.core.C20288i1<S> r6, @Y61.k androidx.compose.animation.core.H1<T, V> r7, @Y61.l java.lang.String r8, @Y61.l androidx.compose.runtime.A r9, int r10, int r11) {
        /*
            r11 = r11 & 2
            if (r11 == 0) goto L6
            java.lang.String r8 = "DeferredAnimation"
        L6:
            r11 = r10 & 14
            r11 = r11 ^ 6
            r0 = 1
            r1 = 0
            r2 = 4
            if (r11 <= r2) goto L15
            boolean r3 = r9.B(r6)
            if (r3 != 0) goto L19
        L15:
            r3 = r10 & 6
            if (r3 != r2) goto L1b
        L19:
            r3 = r0
            goto L1c
        L1b:
            r3 = r1
        L1c:
            java.lang.Object r4 = r9.t()
            androidx.compose.runtime.A$a r5 = androidx.compose.runtime.A.f37866a
            if (r3 != 0) goto L2b
            r5.getClass()
            androidx.compose.runtime.A$a$a r3 = androidx.compose.runtime.A.a.f37868b
            if (r4 != r3) goto L33
        L2b:
            androidx.compose.animation.core.i1$a r4 = new androidx.compose.animation.core.i1$a
            r4.<init>(r7, r8)
            r9.H(r4)
        L33:
            androidx.compose.animation.core.i1$a r4 = (androidx.compose.animation.core.C20288i1.a) r4
            if (r11 <= r2) goto L3d
            boolean r7 = r9.B(r6)
            if (r7 != 0) goto L43
        L3d:
            r7 = r10 & 6
            if (r7 != r2) goto L42
            goto L43
        L42:
            r0 = r1
        L43:
            boolean r7 = r9.u(r4)
            r7 = r7 | r0
            java.lang.Object r8 = r9.t()
            if (r7 != 0) goto L55
            r5.getClass()
            androidx.compose.runtime.A$a$a r7 = androidx.compose.runtime.A.a.f37868b
            if (r8 != r7) goto L5d
        L55:
            androidx.compose.animation.core.m1$d r8 = new androidx.compose.animation.core.m1$d
            r8.<init>(r6, r4)
            r9.H(r8)
        L5d:
            Y41.l r8 = (Y41.l) r8
            androidx.compose.runtime.C22187u0.a(r4, r8, r9)
            boolean r6 = r6.g()
            if (r6 == 0) goto La3
            androidx.compose.runtime.y1 r6 = r4.f26330b
            androidx.compose.runtime.i3 r6 = (androidx.compose.runtime.C22082i3) r6
            java.lang.Object r6 = r6.getF42167b()
            androidx.compose.animation.core.i1$a$a r6 = (androidx.compose.animation.core.C20288i1.a.C1552a) r6
            if (r6 == 0) goto La3
            kotlin.jvm.internal.N r7 = r6.f26334d
            androidx.compose.animation.core.i1<S> r8 = androidx.compose.animation.core.C20288i1.this
            androidx.compose.animation.core.i1$b r9 = r8.f()
            java.lang.Object r9 = r9.a()
            java.lang.Object r7 = r7.invoke(r9)
            kotlin.jvm.internal.N r9 = r6.f26334d
            androidx.compose.animation.core.i1$b r10 = r8.f()
            java.lang.Object r10 = r10.d()
            java.lang.Object r9 = r9.invoke(r10)
            kotlin.jvm.internal.N r10 = r6.f26333c
            androidx.compose.animation.core.i1$b r8 = r8.f()
            java.lang.Object r8 = r10.invoke(r8)
            androidx.compose.animation.core.V r8 = (androidx.compose.animation.core.V) r8
            androidx.compose.animation.core.i1<S>$d<T, V extends androidx.compose.animation.core.x> r6 = r6.f26332b
            r6.q(r7, r9, r8)
        La3:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20300m1.c(androidx.compose.animation.core.i1, androidx.compose.animation.core.H1, java.lang.String, androidx.compose.runtime.A, int, int):androidx.compose.animation.core.i1$a");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0013  */
    @androidx.compose.runtime.InterfaceC22132o
    @kotlin.X
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.animation.core.C20288i1.d d(@Y61.k androidx.compose.animation.core.C20288i1 r9, java.lang.Object r10, java.lang.Object r11, @Y61.k androidx.compose.animation.core.V r12, @Y61.k androidx.compose.animation.core.H1 r13, @Y61.l androidx.compose.runtime.A r14, int r15) {
        /*
            boolean r15 = r14.B(r9)
            java.lang.Object r0 = r14.t()
            androidx.compose.runtime.A$a r1 = androidx.compose.runtime.A.f37866a
            if (r15 != 0) goto L13
            r1.getClass()
            androidx.compose.runtime.A$a$a r15 = androidx.compose.runtime.A.a.f37868b
            if (r0 != r15) goto L28
        L13:
            androidx.compose.animation.core.i1$d r0 = new androidx.compose.animation.core.i1$d
            Y41.l r15 = r13.a()
            java.lang.Object r15 = r15.invoke(r11)
            androidx.compose.animation.core.x r15 = (androidx.compose.animation.core.AbstractC20330x) r15
            r15.d()
            r0.<init>(r10, r15, r13)
            r14.H(r0)
        L28:
            androidx.compose.animation.core.i1$d r0 = (androidx.compose.animation.core.C20288i1.d) r0
            r8 = 0
            r2 = r9
            r3 = r0
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r14
            a(r2, r3, r4, r5, r6, r7, r8)
            boolean r10 = r14.B(r9)
            boolean r11 = r14.B(r0)
            r10 = r10 | r11
            java.lang.Object r11 = r14.t()
            if (r10 != 0) goto L4a
            r1.getClass()
            androidx.compose.runtime.A$a$a r10 = androidx.compose.runtime.A.a.f37868b
            if (r11 != r10) goto L52
        L4a:
            androidx.compose.animation.core.A1 r11 = new androidx.compose.animation.core.A1
            r11.<init>(r9, r0)
            r14.H(r11)
        L52:
            Y41.l r11 = (Y41.l) r11
            androidx.compose.runtime.C22187u0.a(r0, r11, r14)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20300m1.d(androidx.compose.animation.core.i1, java.lang.Object, java.lang.Object, androidx.compose.animation.core.V, androidx.compose.animation.core.H1, androidx.compose.runtime.A, int):androidx.compose.animation.core.i1$d");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0054  */
    @androidx.compose.runtime.InterfaceC22132o
    @Y61.k
    @kotlin.InterfaceC42830m
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.animation.core.C20288i1 e(@Y61.k androidx.compose.animation.core.C20319t0 r4, @Y61.l java.lang.String r5, @Y61.l androidx.compose.runtime.A r6, int r7) {
        /*
            r0 = r7 & 14
            r0 = r0 ^ 6
            r1 = 4
            r2 = 0
            if (r0 <= r1) goto Le
            boolean r0 = r6.B(r4)
            if (r0 != 0) goto L12
        Le:
            r7 = r7 & 6
            if (r7 != r1) goto L14
        L12:
            r7 = 1
            goto L15
        L14:
            r7 = r2
        L15:
            java.lang.Object r0 = r6.t()
            r1 = 0
            androidx.compose.runtime.A$a r3 = androidx.compose.runtime.A.f37866a
            if (r7 != 0) goto L25
            r3.getClass()
            androidx.compose.runtime.A$a$a r7 = androidx.compose.runtime.A.a.f37868b
            if (r0 != r7) goto L2d
        L25:
            androidx.compose.animation.core.i1 r0 = new androidx.compose.animation.core.i1
            r0.<init>(r4, r1, r5)
            r6.H(r0)
        L2d:
            androidx.compose.animation.core.i1 r0 = (androidx.compose.animation.core.C20288i1) r0
            r5 = 1031290843(0x3d783fdb, float:0.060607772)
            r6.C(r5)
            androidx.compose.runtime.y1 r4 = r4.f26406c
            androidx.compose.runtime.i3 r4 = (androidx.compose.runtime.C22082i3) r4
            java.lang.Object r4 = r4.getF42167b()
            r0.a(r2, r6, r4)
            r6.h()
            boolean r4 = r6.B(r0)
            java.lang.Object r5 = r6.t()
            if (r4 != 0) goto L54
            r3.getClass()
            androidx.compose.runtime.A$a$a r4 = androidx.compose.runtime.A.a.f37868b
            if (r5 != r4) goto L5c
        L54:
            androidx.compose.animation.core.D1 r5 = new androidx.compose.animation.core.D1
            r5.<init>(r0)
            r6.H(r5)
        L5c:
            Y41.l r5 = (Y41.l) r5
            androidx.compose.runtime.C22187u0.a(r0, r5, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20300m1.e(androidx.compose.animation.core.t0, java.lang.String, androidx.compose.runtime.A, int):androidx.compose.animation.core.i1");
    }

    @InterfaceC22132o
    @Y61.k
    public static final <T> C20288i1<T> f(T t12, @Y61.l String str, @Y61.l androidx.compose.runtime.A a12, int i12, int i13) {
        if ((i13 & 2) != 0) {
            str = null;
        }
        Object objT = a12.t();
        androidx.compose.runtime.A.f37866a.getClass();
        A.a.C1651a c1651a = A.a.f37868b;
        if (objT == c1651a) {
            objT = new C20288i1(new C20319t0(t12), null, str);
            a12.H(objT);
        }
        C20288i1<T> c20288i1 = (C20288i1) objT;
        c20288i1.a((i12 & 8) | 48 | (i12 & 14), a12, t12);
        Object objT2 = a12.t();
        if (objT2 == c1651a) {
            objT2 = new e(c20288i1);
            a12.H(objT2);
        }
        C22187u0.a(c20288i1, (Y41.l) objT2, a12);
        return c20288i1;
    }
}
