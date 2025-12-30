package androidx.compose.animation.core;

import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: Animatable.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Lkotlin/G0;", "<anonymous>", "()V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.core.Animatable$stop$2", f = "Animatable.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* renamed from: androidx.compose.animation.core.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20271d extends SuspendLambda implements Y41.l<Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ C20268c<Object, AbstractC20330x> f26243q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20271d(C20268c<Object, AbstractC20330x> c20268c, Continuation<? super C20271d> continuation) {
        super(1, continuation);
        this.f26243q = c20268c;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Continuation<?> continuation) {
        return new C20271d(this.f26243q, continuation);
    }

    @Override // Y41.l
    public final Object invoke(Continuation<? super kotlin.G0> continuation) {
        return ((C20271d) create(continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        C42729a0.b(obj);
        C20268c.a(this.f26243q);
        return kotlin.G0.f406611a;
    }
}
