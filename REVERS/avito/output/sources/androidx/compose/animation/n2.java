package androidx.compose.animation;

import androidx.compose.animation.core.AnimationEndReason;
import androidx.compose.animation.core.C20268c;
import androidx.compose.animation.core.C20301n;
import androidx.compose.animation.core.InterfaceC20307p;
import androidx.compose.animation.m2;
import androidx.compose.runtime.C22082i3;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: AnimationModifier.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.SizeAnimationModifierNode$animateTo$data$1$1", f = "AnimationModifier.kt", i = {}, l = {230}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class n2 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public int f26529q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ m2.a f26530r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ long f26531s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ m2 f26532t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public n2(m2.a aVar, long j12, m2 m2Var, Continuation<? super n2> continuation) {
        super(2, continuation);
        this.f26530r = aVar;
        this.f26531s = j12;
        this.f26532t = m2Var;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        return new n2(this.f26530r, this.f26531s, this.f26532t, continuation);
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((n2) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Y41.p<? super androidx.compose.ui.unit.u, ? super androidx.compose.ui.unit.u, kotlin.G0> pVar;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f26529q;
        m2.a aVar = this.f26530r;
        m2 m2Var = this.f26532t;
        if (i12 == 0) {
            C42729a0.b(obj);
            androidx.compose.ui.unit.u uVarA = androidx.compose.ui.unit.u.a(this.f26531s);
            InterfaceC20307p<androidx.compose.ui.unit.u> interfaceC20307p = m2Var.f26512p;
            this.f26529q = 1;
            obj = C20268c.c(aVar.f26519a, uVarA, interfaceC20307p, null, null, this, 12);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i12 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            C42729a0.b(obj);
        }
        C20301n c20301n = (C20301n) obj;
        if (c20301n.f26382b == AnimationEndReason.f26015c && (pVar = m2Var.f26514r) != null) {
            pVar.invoke(androidx.compose.ui.unit.u.a(aVar.f26520b), ((C22082i3) c20301n.f26381a.f26397c).getF42167b());
        }
        return kotlin.G0.f406611a;
    }
}
