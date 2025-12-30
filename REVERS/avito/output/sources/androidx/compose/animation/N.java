package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.flow.InterfaceC43160i;
import kotlinx.coroutines.flow.InterfaceC43172j;

/* compiled from: AnimatedVisibility.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"T", "Landroidx/compose/runtime/U1;", "", "Lkotlin/G0;", "<anonymous>", "(Landroidx/compose/runtime/U1;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.AnimatedVisibilityKt$AnimatedEnterExitImpl$shouldDisposeAfterExit$2$1", f = "AnimatedVisibility.kt", i = {}, l = {739}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class N extends SuspendLambda implements Y41.p<androidx.compose.runtime.U1<Boolean>, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f25926q;

    /* renamed from: r, reason: collision with root package name */
    public /* synthetic */ Object f25927r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ C20288i1<EnterExitState> f25928s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22204y1 f25929t;

    /* compiled from: AnimatedVisibility.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "T", "invoke", "()Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a extends kotlin.jvm.internal.N implements Y41.a<Boolean> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<EnterExitState> f25930l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(C20288i1<EnterExitState> c20288i1) {
            super(0);
            this.f25930l = c20288i1;
        }

        @Override // Y41.a
        public final Boolean invoke() {
            C20288i1<EnterExitState> c20288i1 = this.f25930l;
            EnterExitState enterExitStateA = c20288i1.f26317a.a();
            EnterExitState enterExitState = EnterExitState.f25874d;
            return Boolean.valueOf(enterExitStateA == enterExitState && ((C22082i3) c20288i1.f26320d).getF42167b() == enterExitState);
        }
    }

    /* compiled from: AnimatedVisibility.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"T", "", "it", "Lkotlin/G0;", "emit", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class b<T> implements InterfaceC43172j {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.runtime.U1<Boolean> f25931b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ C20288i1<EnterExitState> f25932c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ InterfaceC22204y1 f25933d;

        public b(androidx.compose.runtime.U1 u12, C20288i1 c20288i1, InterfaceC22204y1 interfaceC22204y1) {
            this.f25931b = u12;
            this.f25932c = c20288i1;
            this.f25933d = interfaceC22204y1;
        }

        @Override // kotlinx.coroutines.flow.InterfaceC43172j
        public final Object emit(Object obj, Continuation continuation) {
            boolean zBooleanValue;
            if (((Boolean) obj).booleanValue()) {
                Y41.p pVar = (Y41.p) this.f25933d.getF42167b();
                C20288i1<EnterExitState> c20288i1 = this.f25932c;
                zBooleanValue = ((Boolean) pVar.invoke(c20288i1.f26317a.a(), ((C22082i3) c20288i1.f26320d).getF42167b())).booleanValue();
            } else {
                zBooleanValue = false;
            }
            this.f25931b.setValue(Boxing.boxBoolean(zBooleanValue));
            return kotlin.G0.f406611a;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public N(C20288i1 c20288i1, InterfaceC22204y1 interfaceC22204y1, Continuation continuation) {
        super(2, continuation);
        this.f25928s = c20288i1;
        this.f25929t = interfaceC22204y1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        N n12 = new N(this.f25928s, this.f25929t, continuation);
        n12.f25927r = obj;
        return n12;
    }

    @Override // Y41.p
    public final Object invoke(androidx.compose.runtime.U1<Boolean> u12, Continuation<? super kotlin.G0> continuation) {
        return ((N) create(u12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f25926q;
        if (i12 == 0) {
            C42729a0.b(obj);
            androidx.compose.runtime.U1 u12 = (androidx.compose.runtime.U1) this.f25927r;
            C20288i1<EnterExitState> c20288i1 = this.f25928s;
            InterfaceC43160i interfaceC43160iN = C22126m3.n(new a(c20288i1));
            b bVar = new b(u12, c20288i1, this.f25929t);
            this.f25926q = 1;
            if (interfaceC43160iN.collect(bVar, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i12 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            C42729a0.b(obj);
        }
        return kotlin.G0.f406611a;
    }
}
