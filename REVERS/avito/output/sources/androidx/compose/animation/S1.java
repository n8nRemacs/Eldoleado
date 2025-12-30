package androidx.compose.animation;

import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: SharedTransitionScope.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.SharedTransitionScopeImpl$onStateRemoved$1$1", f = "SharedTransitionScope.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class S1 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ K1 f25961q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S1(K1 k12, Continuation<? super S1> continuation) {
        super(2, continuation);
        this.f25961q = k12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        return new S1(this.f25961q, continuation);
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        ((S1) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        throw null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        C42729a0.b(obj);
        throw null;
    }
}
