package androidx.compose.animation.core;

import androidx.compose.runtime.A;
import androidx.compose.runtime.C22039c2;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.C22187u0;
import androidx.compose.runtime.I3;
import androidx.compose.runtime.InterfaceC22132o;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.l0;

/* compiled from: InfiniteTransition.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/g0;", "", "a", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.g0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20281g0 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.runtime.collection.e<a<?, ?>> f26273a = new androidx.compose.runtime.collection.e<>(new a[16], 0);

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26274b = C22126m3.g(Boolean.FALSE);

    /* renamed from: c, reason: collision with root package name */
    public long f26275c = Long.MIN_VALUE;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26276d = C22126m3.g(Boolean.TRUE);

    /* compiled from: InfiniteTransition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\b\u0012\u0004\u0012\u00028\u00000\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/g0$a;", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/runtime/I3;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.g0$a */
    public final class a<T, V extends AbstractC20330x> implements I3<T> {

        /* renamed from: b, reason: collision with root package name */
        public Number f26277b;

        /* renamed from: c, reason: collision with root package name */
        public Number f26278c;

        /* renamed from: d, reason: collision with root package name */
        @Y61.k
        public final H1<T, V> f26279d;

        /* renamed from: e, reason: collision with root package name */
        @Y61.k
        public final InterfaceC22204y1 f26280e;

        /* renamed from: f, reason: collision with root package name */
        @Y61.k
        public InterfaceC20307p<T> f26281f;

        /* renamed from: g, reason: collision with root package name */
        @Y61.k
        public C20285h1<T, V> f26282g;

        /* renamed from: h, reason: collision with root package name */
        public boolean f26283h;

        /* renamed from: i, reason: collision with root package name */
        public boolean f26284i;

        /* renamed from: j, reason: collision with root package name */
        public long f26285j;

        public a(Number number, Number number2, @Y61.k H1 h12, @Y61.k C20278f0 c20278f0) {
            this.f26277b = number;
            this.f26278c = number2;
            this.f26279d = h12;
            this.f26280e = C22126m3.g(number);
            this.f26281f = c20278f0;
            this.f26282g = new C20285h1<>(c20278f0, h12, this.f26277b, this.f26278c, (AbstractC20330x) null, 16, (C42822w) null);
        }

        @Override // androidx.compose.runtime.I3
        /* renamed from: getValue */
        public final T getF42167b() {
            return (T) ((C22082i3) this.f26280e).getF42167b();
        }
    }

    /* compiled from: InfiniteTransition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.animation.core.InfiniteTransition$run$1$1", f = "InfiniteTransition.kt", i = {0, 0, 1, 1}, l = {172, 193}, m = "invokeSuspend", n = {"$this$LaunchedEffect", "durationScale", "$this$LaunchedEffect", "durationScale"}, s = {"L$0", "L$1", "L$0", "L$1"})
    /* renamed from: androidx.compose.animation.core.g0$b */
    public static final class b extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public l0.e f26287q;

        /* renamed from: r, reason: collision with root package name */
        public int f26288r;

        /* renamed from: s, reason: collision with root package name */
        public /* synthetic */ Object f26289s;

        /* renamed from: t, reason: collision with root package name */
        public final /* synthetic */ InterfaceC22204y1<I3<Long>> f26290t;

        /* renamed from: u, reason: collision with root package name */
        public final /* synthetic */ C20281g0 f26291u;

        /* compiled from: InfiniteTransition.kt */
        @kotlin.jvm.internal.s0
        @Metadata(d1 = {"\u0000\u000e\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"", "it", "Lkotlin/G0;", "invoke", "(J)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
        /* renamed from: androidx.compose.animation.core.g0$b$a */
        public static final class a extends kotlin.jvm.internal.N implements Y41.l<Long, kotlin.G0> {

            /* renamed from: l, reason: collision with root package name */
            public final /* synthetic */ InterfaceC22204y1<I3<Long>> f26292l;

            /* renamed from: m, reason: collision with root package name */
            public final /* synthetic */ C20281g0 f26293m;

            /* renamed from: n, reason: collision with root package name */
            public final /* synthetic */ l0.e f26294n;

            /* renamed from: o, reason: collision with root package name */
            public final /* synthetic */ kotlinx.coroutines.T f26295o;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(InterfaceC22204y1<I3<Long>> interfaceC22204y1, C20281g0 c20281g0, l0.e eVar, kotlinx.coroutines.T t12) {
                super(1);
                this.f26292l = interfaceC22204y1;
                this.f26293m = c20281g0;
                this.f26294n = eVar;
                this.f26295o = t12;
            }

            @Override // Y41.l
            public final kotlin.G0 invoke(Long l12) {
                long jLongValue = l12.longValue();
                I3<Long> f42167b = this.f26292l.getF42167b();
                long jLongValue2 = f42167b != null ? f42167b.getF42167b().longValue() : jLongValue;
                C20281g0 c20281g0 = this.f26293m;
                long j12 = c20281g0.f26275c;
                androidx.compose.runtime.collection.e<a<?, ?>> eVar = c20281g0.f26273a;
                kotlinx.coroutines.T t12 = this.f26295o;
                l0.e eVar2 = this.f26294n;
                if (j12 == Long.MIN_VALUE || eVar2.f406839b != C20273d1.i(t12.getF400145h())) {
                    c20281g0.f26275c = jLongValue;
                    a<?, ?>[] aVarArr = eVar.f38260b;
                    int i12 = eVar.f38262d;
                    for (int i13 = 0; i13 < i12; i13++) {
                        aVarArr[i13].f26284i = true;
                    }
                    eVar2.f406839b = C20273d1.i(t12.getF400145h());
                }
                float f12 = eVar2.f406839b;
                if (f12 == 0.0f) {
                    a<?, ?>[] aVarArr2 = eVar.f38260b;
                    int i14 = eVar.f38262d;
                    for (int i15 = 0; i15 < i14; i15++) {
                        a<?, ?> aVar = aVarArr2[i15];
                        ((C22082i3) aVar.f26280e).setValue(aVar.f26282g.f26308c);
                        aVar.f26284i = true;
                    }
                } else {
                    long j13 = (long) ((jLongValue2 - c20281g0.f26275c) / f12);
                    a<?, ?>[] aVarArr3 = eVar.f38260b;
                    int i16 = eVar.f38262d;
                    boolean z12 = true;
                    for (int i17 = 0; i17 < i16; i17++) {
                        a<?, ?> aVar2 = aVarArr3[i17];
                        if (!aVar2.f26283h) {
                            ((C22082i3) C20281g0.this.f26274b).setValue(Boolean.FALSE);
                            if (aVar2.f26284i) {
                                aVar2.f26284i = false;
                                aVar2.f26285j = j13;
                            }
                            long j14 = j13 - aVar2.f26285j;
                            ((C22082i3) aVar2.f26280e).setValue(aVar2.f26282g.e(j14));
                            aVar2.f26283h = aVar2.f26282g.c(j14);
                        }
                        if (!aVar2.f26283h) {
                            z12 = false;
                        }
                    }
                    ((C22082i3) c20281g0.f26276d).setValue(Boolean.valueOf(!z12));
                }
                return kotlin.G0.f406611a;
            }
        }

        /* compiled from: InfiniteTransition.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Float;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        /* renamed from: androidx.compose.animation.core.g0$b$b, reason: collision with other inner class name */
        public static final class C1551b extends kotlin.jvm.internal.N implements Y41.a<Float> {

            /* renamed from: l, reason: collision with root package name */
            public final /* synthetic */ kotlinx.coroutines.T f26296l;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C1551b(kotlinx.coroutines.T t12) {
                super(0);
                this.f26296l = t12;
            }

            @Override // Y41.a
            public final Float invoke() {
                return Float.valueOf(C20273d1.i(this.f26296l.getF400145h()));
            }
        }

        /* compiled from: InfiniteTransition.kt */
        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "it", ""}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "androidx.compose.animation.core.InfiniteTransition$run$1$1$3", f = "InfiniteTransition.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: androidx.compose.animation.core.g0$b$c */
        public static final class c extends SuspendLambda implements Y41.p<Float, Continuation<? super Boolean>, Object> {

            /* renamed from: q, reason: collision with root package name */
            public /* synthetic */ float f26297q;

            public c() {
                throw null;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
                c cVar = new c(2, continuation);
                cVar.f26297q = ((Number) obj).floatValue();
                return cVar;
            }

            @Override // Y41.p
            public final Object invoke(Float f12, Continuation<? super Boolean> continuation) {
                return ((c) create(Float.valueOf(f12.floatValue()), continuation)).invokeSuspend(kotlin.G0.f406611a);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                C42729a0.b(obj);
                return Boxing.boxBoolean(this.f26297q > 0.0f);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(InterfaceC22204y1<I3<Long>> interfaceC22204y1, C20281g0 c20281g0, Continuation<? super b> continuation) {
            super(2, continuation);
            this.f26290t = interfaceC22204y1;
            this.f26291u = c20281g0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            b bVar = new b(this.f26290t, this.f26291u, continuation);
            bVar.f26289s = obj;
            return bVar;
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((b) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0051 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0059  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0057 -> B:11:0x003c). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0072 -> B:11:0x003c). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.f26288r
                r2 = 1
                r3 = 2
                if (r1 == 0) goto L2c
                if (r1 == r2) goto L21
                if (r1 != r3) goto L19
                kotlin.jvm.internal.l0$e r1 = r7.f26287q
                java.lang.Object r4 = r7.f26289s
                kotlinx.coroutines.T r4 = (kotlinx.coroutines.T) r4
                kotlin.C42729a0.b(r8)
                r8 = r4
                goto L3c
            L19:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L21:
                kotlin.jvm.internal.l0$e r1 = r7.f26287q
                java.lang.Object r4 = r7.f26289s
                kotlinx.coroutines.T r4 = (kotlinx.coroutines.T) r4
                kotlin.C42729a0.b(r8)
                r8 = r4
                goto L52
            L2c:
                kotlin.C42729a0.b(r8)
                java.lang.Object r8 = r7.f26289s
                kotlinx.coroutines.T r8 = (kotlinx.coroutines.T) r8
                kotlin.jvm.internal.l0$e r1 = new kotlin.jvm.internal.l0$e
                r1.<init>()
                r4 = 1065353216(0x3f800000, float:1.0)
                r1.f406839b = r4
            L3c:
                androidx.compose.animation.core.g0$b$a r4 = new androidx.compose.animation.core.g0$b$a
                androidx.compose.runtime.y1<androidx.compose.runtime.I3<java.lang.Long>> r5 = r7.f26290t
                androidx.compose.animation.core.g0 r6 = r7.f26291u
                r4.<init>(r5, r6, r1, r8)
                r7.f26289s = r8
                r7.f26287q = r1
                r7.f26288r = r2
                java.lang.Object r4 = androidx.compose.animation.core.C20275e0.a(r4, r7)
                if (r4 != r0) goto L52
                return r0
            L52:
                float r4 = r1.f406839b
                r5 = 0
                int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                if (r4 != 0) goto L3c
                androidx.compose.animation.core.g0$b$b r4 = new androidx.compose.animation.core.g0$b$b
                r4.<init>(r8)
                kotlinx.coroutines.flow.i r4 = androidx.compose.runtime.C22126m3.n(r4)
                androidx.compose.animation.core.g0$b$c r5 = new androidx.compose.animation.core.g0$b$c
                r6 = 0
                r5.<init>(r3, r6)
                r7.f26289s = r8
                r7.f26287q = r1
                r7.f26288r = r3
                java.lang.Object r4 = kotlinx.coroutines.flow.C43175k.x(r4, r5, r7)
                if (r4 != r0) goto L3c
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20281g0.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: InfiniteTransition.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.g0$c */
    public static final class c extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {
        public c(int i12) {
            super(2);
        }

        @Override // Y41.p
        public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
            num.intValue();
            int iA2 = C22066f2.a(1);
            C20281g0.this.a(a12, iA2);
            return kotlin.G0.f406611a;
        }
    }

    public C20281g0(@Y61.k String str) {
    }

    @InterfaceC22132o
    public final void a(@Y61.l androidx.compose.runtime.A a12, int i12) {
        androidx.compose.runtime.B bE2 = a12.E(-318043801);
        int i13 = (bE2.u(this) ? 4 : 2) | i12;
        if (bE2.p(i13 & 1, (i13 & 3) != 2)) {
            Object objT = bE2.t();
            androidx.compose.runtime.A.f37866a.getClass();
            A.a.C1651a c1651a = A.a.f37868b;
            if (objT == c1651a) {
                objT = C22126m3.g(null);
                bE2.H(objT);
            }
            InterfaceC22204y1 interfaceC22204y1 = (InterfaceC22204y1) objT;
            if (((Boolean) ((C22082i3) this.f26276d).getF42167b()).booleanValue() || ((Boolean) ((C22082i3) this.f26274b).getF42167b()).booleanValue()) {
                bE2.C(1719883733);
                boolean zU2 = bE2.u(this);
                Object objT2 = bE2.t();
                if (zU2 || objT2 == c1651a) {
                    objT2 = new b(interfaceC22204y1, this, null);
                    bE2.H(objT2);
                }
                C22187u0.d((Y41.p) objT2, bE2, this);
                bE2.X(false);
            } else {
                bE2.C(1721270456);
                bE2.X(false);
            }
        } else {
            bE2.f();
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z != null) {
            c22039c2Z.f38184d = new c(i12);
        }
    }
}
