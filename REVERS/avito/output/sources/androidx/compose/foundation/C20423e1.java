package androidx.compose.foundation;

import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: Focusable.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.foundation.FocusableNode$onFocusStateChange$1", f = "Focusable.kt", i = {}, l = {214}, m = "invokeSuspend", n = {}, s = {})
/* renamed from: androidx.compose.foundation.e1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20423e1 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f27132q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ C20409a1 f27133r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20423e1(C20409a1 c20409a1, Continuation<? super C20423e1> continuation) {
        super(2, continuation);
        this.f27133r = c20409a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        return new C20423e1(this.f27133r, continuation);
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((C20423e1) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f27132q;
        if (i12 == 0) {
            C42729a0.b(obj);
            this.f27132q = 1;
            if (androidx.compose.ui.relocation.c.a(this.f27133r, null, this) == coroutine_suspended) {
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
