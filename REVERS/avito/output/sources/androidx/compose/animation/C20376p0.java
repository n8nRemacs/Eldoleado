package androidx.compose.animation;

import androidx.compose.animation.core.C20268c;
import androidx.compose.animation.core.C20327w;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: AnimateBoundsModifier.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.BoundsTransformDeferredAnimation$animate$1", f = "AnimateBoundsModifier.kt", i = {}, l = {428}, m = "invokeSuspend", n = {}, s = {})
/* renamed from: androidx.compose.animation.p0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20376p0 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ C20268c<l0.j, C20327w> f26539q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ l0.j f26540r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ C20379q0 f26541s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20376p0(C20268c c20268c, l0.j jVar, C20379q0 c20379q0, Continuation continuation) {
        super(2, continuation);
        this.f26539q = c20268c;
        this.f26540r = jVar;
        this.f26541s = c20379q0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        return new C20376p0(this.f26539q, this.f26540r, this.f26541s, continuation);
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((C20376p0) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        C42729a0.b(obj);
        C20379q0 c20379q0 = this.f26541s;
        long j12 = c20379q0.f26553d;
        long j13 = c20379q0.f26552c;
        if ((9223372034707292159L & j13) != 9205357640488583168L && j12 != 9205357640488583168L) {
            l0.k.a(j13, j12);
        }
        throw null;
    }
}
