package androidx.compose.animation.core;

import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.channels.InterfaceC43123w;
import kotlinx.coroutines.channels.InterfaceC43125y;

/* compiled from: AnimateAsState.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$3$1", f = "AnimateAsState.kt", i = {0}, l = {418}, m = "invokeSuspend", n = {"$this$LaunchedEffect"}, s = {"L$0"})
/* renamed from: androidx.compose.animation.core.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20280g extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public InterfaceC43125y f26261q;

    /* renamed from: r, reason: collision with root package name */
    public int f26262r;

    /* renamed from: s, reason: collision with root package name */
    public /* synthetic */ Object f26263s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ InterfaceC43123w<Object> f26264t;

    /* renamed from: u, reason: collision with root package name */
    public final /* synthetic */ C20268c<Object, Object> f26265u;

    /* renamed from: v, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22204y1 f26266v;

    /* renamed from: w, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22204y1 f26267w;

    /* compiled from: AnimateAsState.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.animation.core.AnimateAsStateKt$animateValueAsState$3$1$1", f = "AnimateAsState.kt", i = {}, l = {427}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.compose.animation.core.g$a */
    public static final class a extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public int f26268q;

        /* renamed from: r, reason: collision with root package name */
        public final /* synthetic */ Object f26269r;

        /* renamed from: s, reason: collision with root package name */
        public final /* synthetic */ C20268c<Object, Object> f26270s;

        /* renamed from: t, reason: collision with root package name */
        public final /* synthetic */ InterfaceC22204y1 f26271t;

        /* renamed from: u, reason: collision with root package name */
        public final /* synthetic */ InterfaceC22204y1 f26272u;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(Object obj, C20268c c20268c, InterfaceC22204y1 interfaceC22204y1, InterfaceC22204y1 interfaceC22204y12, Continuation continuation) {
            super(2, continuation);
            this.f26269r = obj;
            this.f26270s = c20268c;
            this.f26271t = interfaceC22204y1;
            this.f26272u = interfaceC22204y12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            return new a(this.f26269r, this.f26270s, this.f26271t, this.f26272u, continuation);
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((a) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i12 = this.f26268q;
            C20268c<Object, Object> c20268c = this.f26270s;
            if (i12 == 0) {
                C42729a0.b(obj);
                if (!kotlin.jvm.internal.L.f(this.f26269r, ((C22082i3) c20268c.f26227f).getF42167b())) {
                    R0<Float> r02 = C20283h.f26300a;
                    InterfaceC20307p interfaceC20307p = (InterfaceC20307p) this.f26271t.getF42167b();
                    this.f26268q = 1;
                    if (C20268c.c(this.f26270s, this.f26269r, interfaceC20307p, null, null, this, 12) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                return kotlin.G0.f406611a;
            }
            if (i12 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            C42729a0.b(obj);
            R0<Float> r03 = C20283h.f26300a;
            Y41.l lVar = (Y41.l) this.f26272u.getF42167b();
            if (lVar != null) {
                lVar.invoke(c20268c.f());
            }
            return kotlin.G0.f406611a;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20280g(InterfaceC43123w interfaceC43123w, C20268c c20268c, InterfaceC22204y1 interfaceC22204y1, InterfaceC22204y1 interfaceC22204y12, Continuation continuation) {
        super(2, continuation);
        this.f26264t = interfaceC43123w;
        this.f26265u = c20268c;
        this.f26266v = interfaceC22204y1;
        this.f26267w = interfaceC22204y12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        C20280g c20280g = new C20280g(this.f26264t, this.f26265u, this.f26266v, this.f26267w, continuation);
        c20280g.f26263s = obj;
        return c20280g;
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((C20280g) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0037 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0035 -> B:12:0x0038). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.f26262r
            kotlinx.coroutines.channels.w<java.lang.Object> r2 = r12.f26264t
            r3 = 1
            if (r1 == 0) goto L1f
            if (r1 != r3) goto L17
            kotlinx.coroutines.channels.y r1 = r12.f26261q
            java.lang.Object r4 = r12.f26263s
            kotlinx.coroutines.T r4 = (kotlinx.coroutines.T) r4
            kotlin.C42729a0.b(r13)
            goto L38
        L17:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r0)
            throw r13
        L1f:
            kotlin.C42729a0.b(r13)
            java.lang.Object r13 = r12.f26263s
            kotlinx.coroutines.T r13 = (kotlinx.coroutines.T) r13
            kotlinx.coroutines.channels.y r1 = r2.iterator()
            r4 = r13
        L2b:
            r12.f26263s = r4
            r12.f26261q = r1
            r12.f26262r = r3
            java.lang.Object r13 = r1.a(r12)
            if (r13 != r0) goto L38
            return r0
        L38:
            java.lang.Boolean r13 = (java.lang.Boolean) r13
            boolean r13 = r13.booleanValue()
            if (r13 == 0) goto L64
            java.lang.Object r13 = r1.next()
            java.lang.Object r5 = r2.H()
            java.lang.Object r5 = kotlinx.coroutines.channels.B.c(r5)
            if (r5 != 0) goto L50
            r7 = r13
            goto L51
        L50:
            r7 = r5
        L51:
            androidx.compose.animation.core.g$a r13 = new androidx.compose.animation.core.g$a
            androidx.compose.runtime.y1 r10 = r12.f26267w
            androidx.compose.runtime.y1 r9 = r12.f26266v
            androidx.compose.animation.core.c<java.lang.Object, java.lang.Object> r8 = r12.f26265u
            r11 = 0
            r6 = r13
            r6.<init>(r7, r8, r9, r10, r11)
            r5 = 3
            r6 = 0
            kotlinx.coroutines.C43259k.d(r4, r6, r6, r13, r5)
            goto L2b
        L64:
            kotlin.G0 r13 = kotlin.G0.f406611a
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20280g.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
