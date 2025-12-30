package androidx.compose.foundation;

import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.InterfaceC43268o0;

/* compiled from: Focusable.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.foundation.FocusableNode$emitWithFallback$1", f = "Focusable.kt", i = {}, l = {314}, m = "invokeSuspend", n = {}, s = {})
/* renamed from: androidx.compose.foundation.b1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20412b1 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f26952q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.foundation.interaction.m f26953r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.foundation.interaction.d f26954s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ InterfaceC43268o0 f26955t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20412b1(androidx.compose.foundation.interaction.m mVar, androidx.compose.foundation.interaction.d dVar, InterfaceC43268o0 interfaceC43268o0, Continuation continuation) {
        super(2, continuation);
        this.f26953r = mVar;
        this.f26954s = dVar;
        this.f26955t = interfaceC43268o0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        return new C20412b1(this.f26953r, this.f26954s, this.f26955t, continuation);
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((C20412b1) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f26952q;
        if (i12 == 0) {
            C42729a0.b(obj);
            this.f26952q = 1;
            if (this.f26953r.b(this.f26954s, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i12 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            C42729a0.b(obj);
        }
        InterfaceC43268o0 interfaceC43268o0 = this.f26955t;
        if (interfaceC43268o0 != null) {
            interfaceC43268o0.dispose();
        }
        return kotlin.G0.f406611a;
    }
}
