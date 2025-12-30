package androidx.compose.foundation;

import androidx.compose.runtime.C22082i3;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: BasicTooltip.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0001\u001a\u00020\u0000H\u008a@¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lkotlin/G0;", "<anonymous>", "()V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.foundation.BasicTooltipStateImpl$show$cancellableShow$1", f = "BasicTooltip.kt", i = {}, l = {391}, m = "invokeSuspend", n = {}, s = {})
/* renamed from: androidx.compose.foundation.a0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20408a0 extends SuspendLambda implements Y41.l<Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public Object f26934q;

    /* renamed from: r, reason: collision with root package name */
    public int f26935r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ C20411b0 f26936s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20408a0(C20411b0 c20411b0, Continuation<? super C20408a0> continuation) {
        super(1, continuation);
        this.f26936s = c20411b0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Continuation<?> continuation) {
        return new C20408a0(this.f26936s, continuation);
    }

    @Override // Y41.l
    public final Object invoke(Continuation<? super kotlin.G0> continuation) {
        return ((C20408a0) create(continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f26935r;
        if (i12 == 0) {
            C42729a0.b(obj);
            C20411b0 c20411b0 = this.f26936s;
            this.f26935r = 1;
            kotlinx.coroutines.r rVar = new kotlinx.coroutines.r(1, IntrinsicsKt.intercepted(this));
            rVar.p();
            ((C22082i3) c20411b0.f26950b).setValue(Boolean.TRUE);
            c20411b0.f26951c = rVar;
            Object objO = rVar.o();
            if (objO == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(this);
            }
            if (objO == coroutine_suspended) {
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
