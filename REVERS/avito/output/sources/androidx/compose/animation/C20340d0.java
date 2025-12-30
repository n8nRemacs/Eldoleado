package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.runtime.A;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.InterfaceC22132o;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.unit.C22712b;
import kotlin.Metadata;

/* compiled from: AnimatedVisibility.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003¨\u0006\u0006²\u0006$\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u00008\nX\u008a\u0084\u0002²\u0006\u0012\u0010\u0005\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00008\nX\u008a\u0084\u0002"}, d2 = {"T", "Lkotlin/Function2;", "Landroidx/compose/animation/EnterExitState;", "", "shouldDisposeBlockUpdated", "shouldDisposeAfterExit", "animation_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.d0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20340d0 {

    /* compiled from: AnimatedVisibility.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\t\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"T", "Landroidx/compose/ui/layout/k0;", "Landroidx/compose/ui/layout/h0;", "measurable", "Landroidx/compose/ui/unit/b;", "constraints", "Landroidx/compose/ui/layout/j0;", "invoke-3p2s80s", "(Landroidx/compose/ui/layout/k0;Landroidx/compose/ui/layout/h0;J)Landroidx/compose/ui/layout/j0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.d0$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.q<InterfaceC22369k0, InterfaceC22363h0, C22712b, InterfaceC22367j0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ Y41.l<T, Boolean> f26441l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ C20288i1<T> f26442m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public a(Y41.l<? super T, Boolean> lVar, C20288i1<T> c20288i1) {
            super(3);
            this.f26441l = lVar;
            this.f26442m = c20288i1;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0039  */
        @Override // Y41.q
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final androidx.compose.ui.layout.InterfaceC22367j0 invoke(androidx.compose.ui.layout.InterfaceC22369k0 r9, androidx.compose.ui.layout.InterfaceC22363h0 r10, androidx.compose.ui.unit.C22712b r11) {
            /*
                r8 = this;
                androidx.compose.ui.layout.k0 r9 = (androidx.compose.ui.layout.InterfaceC22369k0) r9
                androidx.compose.ui.layout.h0 r10 = (androidx.compose.ui.layout.InterfaceC22363h0) r10
                androidx.compose.ui.unit.b r11 = (androidx.compose.ui.unit.C22712b) r11
                long r0 = r11.f42843a
                androidx.compose.ui.layout.K0 r10 = r10.I(r0)
                boolean r11 = r9.I1()
                r0 = 4294967295(0xffffffff, double:2.1219957905E-314)
                r2 = 32
                if (r11 == 0) goto L39
                androidx.compose.animation.core.i1<T> r11 = r8.f26442m
                androidx.compose.runtime.y1 r11 = r11.f26320d
                androidx.compose.runtime.i3 r11 = (androidx.compose.runtime.C22082i3) r11
                java.lang.Object r11 = r11.getF42167b()
                Y41.l<T, java.lang.Boolean> r3 = r8.f26441l
                java.lang.Object r11 = r3.invoke(r11)
                java.lang.Boolean r11 = (java.lang.Boolean) r11
                boolean r11 = r11.booleanValue()
                if (r11 != 0) goto L39
                androidx.compose.ui.unit.u$a r11 = androidx.compose.ui.unit.u.f42871b
                r11.getClass()
                r3 = 0
                goto L45
            L39:
                int r11 = r10.f40345b
                int r3 = r10.f40346c
                long r4 = (long) r11
                long r4 = r4 << r2
                long r6 = (long) r3
                long r6 = r6 & r0
                long r3 = r4 | r6
                androidx.compose.ui.unit.u$a r11 = androidx.compose.ui.unit.u.f42871b
            L45:
                long r5 = r3 >> r2
                int r11 = (int) r5
                long r0 = r0 & r3
                int r0 = (int) r0
                androidx.compose.animation.c0 r1 = new androidx.compose.animation.c0
                r1.<init>(r10)
                androidx.compose.ui.layout.j0 r9 = androidx.compose.ui.layout.InterfaceC22369k0.q1(r9, r11, r0, r1)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.C20340d0.a.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: AnimatedVisibility.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", "", "T", "current", "Landroidx/compose/animation/EnterExitState;", "target", "invoke", "(Landroidx/compose/animation/EnterExitState;Landroidx/compose/animation/EnterExitState;)Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.d0$b */
    public static final class b extends kotlin.jvm.internal.N implements Y41.p<EnterExitState, EnterExitState, Boolean> {

        /* renamed from: l, reason: collision with root package name */
        public static final b f26443l = new b();

        public b() {
            super(2);
        }

        @Override // Y41.p
        public final Boolean invoke(EnterExitState enterExitState, EnterExitState enterExitState2) {
            EnterExitState enterExitState3 = enterExitState2;
            return Boolean.valueOf(enterExitState == enterExitState3 && enterExitState3 == EnterExitState.f25874d);
        }
    }

    /* compiled from: AnimatedVisibility.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.d0$c */
    public static final class c extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<T> f26444l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ Y41.l<T, Boolean> f26445m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.v f26446n;

        /* renamed from: o, reason: collision with root package name */
        public final /* synthetic */ AbstractC20359j1 f26447o;

        /* renamed from: p, reason: collision with root package name */
        public final /* synthetic */ AbstractC20365l1 f26448p;

        /* renamed from: q, reason: collision with root package name */
        public final /* synthetic */ Y41.q<InterfaceC20343e0, androidx.compose.runtime.A, Integer, kotlin.G0> f26449q;

        /* renamed from: r, reason: collision with root package name */
        public final /* synthetic */ int f26450r;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public c(C20288i1<T> c20288i1, Y41.l<? super T, Boolean> lVar, androidx.compose.ui.v vVar, AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1, Y41.q<? super InterfaceC20343e0, ? super androidx.compose.runtime.A, ? super Integer, kotlin.G0> qVar, int i12) {
            super(2);
            this.f26444l = c20288i1;
            this.f26445m = lVar;
            this.f26446n = vVar;
            this.f26447o = abstractC20359j1;
            this.f26448p = abstractC20365l1;
            this.f26449q = qVar;
            this.f26450r = i12;
        }

        @Override // Y41.p
        public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
            num.intValue();
            int iA2 = C22066f2.a(this.f26450r | 1);
            AbstractC20365l1 abstractC20365l1 = this.f26448p;
            Y41.q<InterfaceC20343e0, androidx.compose.runtime.A, Integer, kotlin.G0> qVar = this.f26449q;
            C20340d0.e(this.f26444l, this.f26445m, this.f26446n, this.f26447o, abstractC20365l1, qVar, a12, iA2);
            return kotlin.G0.f406611a;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0178  */
    @androidx.compose.runtime.InterfaceC22132o
    @androidx.compose.runtime.InterfaceC22137p
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void a(@Y61.k androidx.compose.animation.core.C20288i1 r31, @Y61.k Y41.l r32, @Y61.k androidx.compose.ui.v r33, @Y61.k androidx.compose.animation.AbstractC20359j1 r34, @Y61.k androidx.compose.animation.AbstractC20365l1 r35, @Y61.k Y41.p r36, @Y61.k Y41.q r37, @Y61.l androidx.compose.runtime.A r38, int r39) {
        /*
            Method dump skipped, instructions count: 1446
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.C20340d0.a(androidx.compose.animation.core.i1, Y41.l, androidx.compose.ui.v, androidx.compose.animation.j1, androidx.compose.animation.l1, Y41.p, Y41.q, androidx.compose.runtime.A, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    @androidx.compose.runtime.InterfaceC22132o
    @androidx.compose.runtime.InterfaceC22137p
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void b(@Y61.k androidx.compose.foundation.layout.K r18, boolean r19, @Y61.l androidx.compose.ui.v.a r20, @Y61.l androidx.compose.animation.AbstractC20359j1 r21, @Y61.l androidx.compose.animation.AbstractC20365l1 r22, @Y61.l java.lang.String r23, @Y61.k androidx.compose.runtime.internal.C22096n r24, @Y61.l androidx.compose.runtime.A r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.C20340d0.b(androidx.compose.foundation.layout.K, boolean, androidx.compose.ui.v$a, androidx.compose.animation.j1, androidx.compose.animation.l1, java.lang.String, androidx.compose.runtime.internal.n, androidx.compose.runtime.A, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    @androidx.compose.runtime.InterfaceC22132o
    @androidx.compose.runtime.InterfaceC22137p
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void c(@Y61.k androidx.compose.foundation.layout.InterfaceC20572g2 r18, boolean r19, @Y61.l androidx.compose.ui.v.a r20, @Y61.l androidx.compose.animation.AbstractC20359j1 r21, @Y61.l androidx.compose.animation.AbstractC20365l1 r22, @Y61.l java.lang.String r23, @Y61.k androidx.compose.runtime.internal.C22096n r24, @Y61.l androidx.compose.runtime.A r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.C20340d0.c(androidx.compose.foundation.layout.g2, boolean, androidx.compose.ui.v$a, androidx.compose.animation.j1, androidx.compose.animation.l1, java.lang.String, androidx.compose.runtime.internal.n, androidx.compose.runtime.A, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    @androidx.compose.runtime.InterfaceC22132o
    @androidx.compose.runtime.InterfaceC22137p
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void d(boolean r16, @Y61.l androidx.compose.ui.v r17, @Y61.l androidx.compose.animation.AbstractC20359j1 r18, @Y61.l androidx.compose.animation.AbstractC20365l1 r19, @Y61.l java.lang.String r20, @Y61.k androidx.compose.runtime.internal.C22096n r21, @Y61.l androidx.compose.runtime.A r22, int r23, int r24) {
        /*
            Method dump skipped, instructions count: 235
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.C20340d0.d(boolean, androidx.compose.ui.v, androidx.compose.animation.j1, androidx.compose.animation.l1, java.lang.String, androidx.compose.runtime.internal.n, androidx.compose.runtime.A, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x00af  */
    @androidx.compose.runtime.InterfaceC22132o
    @androidx.compose.runtime.InterfaceC22137p
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> void e(@Y61.k androidx.compose.animation.core.C20288i1<T> r17, @Y61.k Y41.l<? super T, java.lang.Boolean> r18, @Y61.k androidx.compose.ui.v r19, @Y61.k androidx.compose.animation.AbstractC20359j1 r20, @Y61.k androidx.compose.animation.AbstractC20365l1 r21, @Y61.k Y41.q<? super androidx.compose.animation.InterfaceC20343e0, ? super androidx.compose.runtime.A, ? super java.lang.Integer, kotlin.G0> r22, @Y61.l androidx.compose.runtime.A r23, int r24) {
        /*
            Method dump skipped, instructions count: 259
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.C20340d0.e(androidx.compose.animation.core.i1, Y41.l, androidx.compose.ui.v, androidx.compose.animation.j1, androidx.compose.animation.l1, Y41.q, androidx.compose.runtime.A, int):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @InterfaceC22132o
    public static final EnterExitState f(C20288i1 c20288i1, Y41.l lVar, Object obj, androidx.compose.runtime.A a12) {
        EnterExitState enterExitState;
        a12.K(-902032957, c20288i1);
        boolean zG2 = c20288i1.g();
        androidx.compose.animation.core.F1<S> f12 = c20288i1.f26317a;
        if (zG2) {
            a12.C(2101770115);
            a12.h();
            enterExitState = ((Boolean) lVar.invoke(obj)).booleanValue() ? EnterExitState.f25873c : ((Boolean) lVar.invoke(f12.a())).booleanValue() ? EnterExitState.f25874d : EnterExitState.f25872b;
        } else {
            a12.C(2102044248);
            Object objT = a12.t();
            androidx.compose.runtime.A.f37866a.getClass();
            if (objT == A.a.f37868b) {
                objT = C22126m3.g(Boolean.FALSE);
                a12.H(objT);
            }
            InterfaceC22204y1 interfaceC22204y1 = (InterfaceC22204y1) objT;
            if (((Boolean) lVar.invoke(f12.a())).booleanValue()) {
                interfaceC22204y1.setValue(Boolean.TRUE);
            }
            enterExitState = ((Boolean) lVar.invoke(obj)).booleanValue() ? EnterExitState.f25873c : ((Boolean) interfaceC22204y1.getF42167b()).booleanValue() ? EnterExitState.f25874d : EnterExitState.f25872b;
            a12.h();
        }
        a12.N();
        return enterExitState;
    }
}
