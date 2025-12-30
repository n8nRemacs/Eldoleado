package androidx.compose.animation.core;

import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: InfiniteAnimationPolicy.kt */
@Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001H\u008a@"}, d2 = {"<anonymous>", "R"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "androidx.compose.animation.core.InfiniteAnimationPolicyKt$withInfiniteAnimationFrameNanos$2", f = "InfiniteAnimationPolicy.kt", i = {}, l = {32}, m = "invokeSuspend", n = {}, s = {})
/* renamed from: androidx.compose.animation.core.d0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20272d0 extends SuspendLambda implements Y41.l<Continuation<Object>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f26244q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ kotlin.jvm.internal.N f26245r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public C20272d0(Y41.l<? super Long, Object> lVar, Continuation<? super C20272d0> continuation) {
        super(1, continuation);
        this.f26245r = (kotlin.jvm.internal.N) lVar;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [Y41.l, kotlin.jvm.internal.N] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Continuation<?> continuation) {
        return new C20272d0(this.f26245r, continuation);
    }

    @Override // Y41.l
    public final Object invoke(Continuation<Object> continuation) {
        return ((C20272d0) create(continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f26244q;
        if (i12 == 0) {
            C42729a0.b(obj);
            this.f26244q = 1;
            obj = androidx.compose.runtime.X0.a(getF411960f()).N(this.f26245r, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i12 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            C42729a0.b(obj);
        }
        return obj;
    }
}
