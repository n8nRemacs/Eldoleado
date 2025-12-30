package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.C42729a0;
import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.C42822w;

/* compiled from: Animatable.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\u00020\u0004B1\b\u0017\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Landroidx/compose/animation/core/c;", "T", "Landroidx/compose/animation/core/x;", "V", "", "initialValue", "Landroidx/compose/animation/core/H1;", "typeConverter", "visibilityThreshold", "<init>", "(Ljava/lang/Object;Landroidx/compose/animation/core/H1;Ljava/lang/Object;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20268c<T, V extends AbstractC20330x> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final H1<T, V> f26222a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.l
    public final T f26223b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final String f26224c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final r<T, V> f26225d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26226e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26227f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.l
    public T f26228g;

    /* renamed from: h, reason: collision with root package name */
    @Y61.l
    public T f26229h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.k
    public final C20322u0 f26230i;

    /* renamed from: j, reason: collision with root package name */
    @Y61.k
    public final R0<T> f26231j;

    /* renamed from: k, reason: collision with root package name */
    @Y61.k
    public final V f26232k;

    /* renamed from: l, reason: collision with root package name */
    @Y61.k
    public final V f26233l;

    /* renamed from: m, reason: collision with root package name */
    @Y61.k
    public V f26234m;

    /* renamed from: n, reason: collision with root package name */
    @Y61.k
    public V f26235n;

    /* compiled from: Animatable.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Lkotlin/G0;", "<anonymous>", "()V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.animation.core.Animatable$snapTo$2", f = "Animatable.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.compose.animation.core.c$a */
    public static final class a extends SuspendLambda implements Y41.l<Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public final /* synthetic */ C20268c<T, V> f26236q;

        /* renamed from: r, reason: collision with root package name */
        public final /* synthetic */ T f26237r;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(C20268c<T, V> c20268c, T t12, Continuation<? super a> continuation) {
            super(1, continuation);
            this.f26236q = c20268c;
            this.f26237r = t12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Continuation<?> continuation) {
            return new a(this.f26236q, this.f26237r, continuation);
        }

        @Override // Y41.l
        public final Object invoke(Continuation<? super kotlin.G0> continuation) {
            return ((a) create(continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            C42729a0.b(obj);
            C20268c<T, V> c20268c = this.f26236q;
            C20268c.a(c20268c);
            T tD2 = c20268c.d(this.f26237r);
            ((C22082i3) c20268c.f26225d.f26397c).setValue(tD2);
            ((C22082i3) c20268c.f26227f).setValue(tD2);
            return kotlin.G0.f406611a;
        }
    }

    public C20268c(T t12, @Y61.k H1<T, V> h12, @Y61.l T t13, @Y61.k String str) {
        this.f26222a = h12;
        this.f26223b = t13;
        this.f26224c = str;
        r<T, V> rVar = new r<>(h12, t12, null, 0L, 0L, false, 60, null);
        this.f26225d = rVar;
        this.f26226e = C22126m3.g(Boolean.FALSE);
        this.f26227f = C22126m3.g(t12);
        this.f26230i = new C20322u0();
        this.f26231j = new R0<>(0.0f, 0.0f, t13, 3, null);
        V v12 = rVar.f26398d;
        boolean z12 = v12 instanceof C20318t;
        V v13 = z12 ? C20274e.f26251e : v12 instanceof C20321u ? C20274e.f26252f : v12 instanceof C20324v ? C20274e.f26253g : C20274e.f26254h;
        this.f26232k = v13;
        V v14 = z12 ? C20274e.f26247a : v12 instanceof C20321u ? C20274e.f26248b : v12 instanceof C20324v ? C20274e.f26249c : C20274e.f26250d;
        this.f26233l = v14;
        this.f26234m = v13;
        this.f26235n = v14;
    }

    public static final void a(C20268c c20268c) {
        r<T, V> rVar = c20268c.f26225d;
        rVar.f26398d.d();
        rVar.f26399e = Long.MIN_VALUE;
        ((C22082i3) c20268c.f26226e).setValue(Boolean.FALSE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Object c(C20268c c20268c, Object obj, InterfaceC20307p interfaceC20307p, Float f12, Y41.l lVar, Continuation continuation, int i12) {
        if ((i12 & 2) != 0) {
            interfaceC20307p = c20268c.f26231j;
        }
        InterfaceC20307p interfaceC20307p2 = interfaceC20307p;
        T tInvoke = f12;
        if ((i12 & 4) != 0) {
            tInvoke = c20268c.f26222a.b().invoke(c20268c.f26225d.f26398d);
        }
        T t12 = tInvoke;
        if ((i12 & 8) != 0) {
            lVar = null;
        }
        return c20268c.b(obj, interfaceC20307p2, t12, lVar, continuation);
    }

    @Y61.l
    public final Object b(T t12, @Y61.k InterfaceC20307p<T> interfaceC20307p, T t13, @Y61.l Y41.l<? super C20268c<T, V>, kotlin.G0> lVar, @Y61.k Continuation<? super C20301n<T, V>> continuation) {
        T tF2 = f();
        H1<T, V> h12 = this.f26222a;
        return C20322u0.a(this.f26230i, new C20265b(this, t13, new C20285h1(interfaceC20307p, h12, tF2, t12, h12.a().invoke(t13)), this.f26225d.f26399e, lVar, null), continuation);
    }

    public final T d(T t12) {
        if (kotlin.jvm.internal.L.f(this.f26234m, this.f26232k) && kotlin.jvm.internal.L.f(this.f26235n, this.f26233l)) {
            return t12;
        }
        H1<T, V> h12 = this.f26222a;
        V vInvoke = h12.a().invoke(t12);
        int f26428e = vInvoke.getF26416d();
        boolean z12 = false;
        for (int i12 = 0; i12 < f26428e; i12++) {
            if (vInvoke.a(i12) < this.f26234m.a(i12) || vInvoke.a(i12) > this.f26235n.a(i12)) {
                vInvoke.e(kotlin.ranges.s.f(vInvoke.a(i12), this.f26234m.a(i12), this.f26235n.a(i12)), i12);
                z12 = true;
            }
        }
        return z12 ? h12.b().invoke(vInvoke) : t12;
    }

    @Y61.k
    /* renamed from: e, reason: from getter */
    public final String getF26224c() {
        return this.f26224c;
    }

    public final T f() {
        return (T) ((C22082i3) this.f26225d.f26397c).getF42167b();
    }

    public final boolean g() {
        return ((Boolean) ((C22082i3) this.f26226e).getF42167b()).booleanValue();
    }

    @Y61.l
    public final Object h(T t12, @Y61.k Continuation<? super kotlin.G0> continuation) {
        Object objA = C20322u0.a(this.f26230i, new a(this, t12, null), continuation);
        return objA == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objA : kotlin.G0.f406611a;
    }

    @Y61.l
    public final Object i(@Y61.k SuspendLambda suspendLambda) {
        Object objA = C20322u0.a(this.f26230i, new C20271d(this, null), suspendLambda);
        return objA == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objA : kotlin.G0.f406611a;
    }

    public /* synthetic */ C20268c(Object obj, H1 h12, Object obj2, String str, int i12, C42822w c42822w) {
        this(obj, h12, (i12 & 4) != 0 ? null : obj2, (i12 & 8) != 0 ? "Animatable" : str);
    }

    public /* synthetic */ C20268c(Object obj, H1 h12, Object obj2, int i12, C42822w c42822w) {
        this(obj, h12, (i12 & 4) != 0 ? null : obj2);
    }

    @InterfaceC42830m
    public /* synthetic */ C20268c(Object obj, H1 h12, Object obj2) {
        this(obj, h12, obj2, "Animatable");
    }
}
