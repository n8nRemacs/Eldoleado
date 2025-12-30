package androidx.compose.foundation;

import androidx.compose.foundation.interaction.g;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: Clickable.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.foundation.AbstractClickableNode$emitHoverExit$1$1$1", f = "Clickable.kt", i = {}, l = {1308}, m = "invokeSuspend", n = {}, s = {})
/* renamed from: androidx.compose.foundation.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20413c extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f26963q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.foundation.interaction.m f26964r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ g.b f26965s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20413c(androidx.compose.foundation.interaction.m mVar, g.b bVar, Continuation<? super C20413c> continuation) {
        super(2, continuation);
        this.f26964r = mVar;
        this.f26965s = bVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        return new C20413c(this.f26964r, this.f26965s, continuation);
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((C20413c) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f26963q;
        if (i12 == 0) {
            C42729a0.b(obj);
            this.f26963q = 1;
            if (this.f26964r.b(this.f26965s, this) == coroutine_suspended) {
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
