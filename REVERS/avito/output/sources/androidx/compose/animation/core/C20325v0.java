package androidx.compose.animation.core;

import androidx.compose.animation.core.C20322u0;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: InternalMutatorMutex.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {"R", "Lkotlinx/coroutines/T;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.core.MutatorMutex$mutate$2", f = "InternalMutatorMutex.kt", i = {0, 0, 1, 1}, l = {176, 124}, m = "invokeSuspend", n = {"mutator", "$this$withLock_u24default$iv", "mutator", "$this$withLock_u24default$iv"}, s = {"L$0", "L$1", "L$0", "L$1"})
/* renamed from: androidx.compose.animation.core.v0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20325v0 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<Object>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public kotlinx.coroutines.sync.a f26417q;

    /* renamed from: r, reason: collision with root package name */
    public Object f26418r;

    /* renamed from: s, reason: collision with root package name */
    public C20322u0 f26419s;

    /* renamed from: t, reason: collision with root package name */
    public int f26420t;

    /* renamed from: u, reason: collision with root package name */
    public /* synthetic */ Object f26421u;

    /* renamed from: v, reason: collision with root package name */
    public final /* synthetic */ C20322u0 f26422v;

    /* renamed from: w, reason: collision with root package name */
    public final /* synthetic */ SuspendLambda f26423w;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public C20325v0(C20322u0 c20322u0, Y41.l lVar, Continuation continuation) {
        super(2, continuation);
        MutatePriority mutatePriority = MutatePriority.f26109b;
        this.f26422v = c20322u0;
        this.f26423w = (SuspendLambda) lVar;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [Y41.l, kotlin.coroutines.jvm.internal.SuspendLambda] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        ?? r12 = this.f26423w;
        MutatePriority mutatePriority = MutatePriority.f26109b;
        C20325v0 c20325v0 = new C20325v0(this.f26422v, r12, continuation);
        c20325v0.f26421u = obj;
        return c20325v0;
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<Object> continuation) {
        return ((C20325v0) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.sync.a] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [Y41.l] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        C20322u0 c20322u0;
        kotlinx.coroutines.sync.a aVar;
        ?? r32;
        C20322u0.a aVar2;
        kotlinx.coroutines.sync.a aVar3;
        C20322u0.a aVar4;
        C20322u0 c20322u02;
        Throwable th2;
        AtomicReference<C20322u0.a> atomicReference;
        AtomicReference<C20322u0.a> atomicReference2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        ?? r12 = this.f26420t;
        try {
            try {
                if (r12 == 0) {
                    C42729a0.b(obj);
                    kotlinx.coroutines.N0 n02 = (kotlinx.coroutines.N0) ((kotlinx.coroutines.T) this.f26421u).getF398827h().get(kotlinx.coroutines.N0.f410716u2);
                    MutatePriority mutatePriority = MutatePriority.f26109b;
                    C20322u0.a aVar5 = new C20322u0.a(n02);
                    while (true) {
                        c20322u0 = this.f26422v;
                        AtomicReference<C20322u0.a> atomicReference3 = c20322u0.f26410a;
                        C20322u0.a aVar6 = atomicReference3.get();
                        if (aVar6 != null) {
                            MutatePriority mutatePriority2 = MutatePriority.f26109b;
                            if (mutatePriority2.compareTo(mutatePriority2) < 0) {
                                throw new CancellationException("Current mutation had a higher priority");
                            }
                        }
                        while (!atomicReference3.compareAndSet(aVar6, aVar5)) {
                            if (atomicReference3.get() != aVar6) {
                                break;
                            }
                        }
                        if (aVar6 != null) {
                            aVar6.f26412a.c(new MutationInterruptedException());
                        }
                        aVar = c20322u0.f26411b;
                        this.f26421u = aVar5;
                        this.f26417q = aVar;
                        SuspendLambda suspendLambda = this.f26423w;
                        this.f26418r = suspendLambda;
                        this.f26419s = c20322u0;
                        this.f26420t = 1;
                        if (aVar.b(this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        r32 = suspendLambda;
                        aVar2 = aVar5;
                    }
                } else {
                    if (r12 != 1) {
                        if (r12 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        c20322u02 = (C20322u0) this.f26418r;
                        aVar3 = this.f26417q;
                        aVar4 = (C20322u0.a) this.f26421u;
                        try {
                            C42729a0.b(obj);
                            atomicReference2 = c20322u02.f26410a;
                            while (!atomicReference2.compareAndSet(aVar4, null) && atomicReference2.get() == aVar4) {
                            }
                            aVar3.d(null);
                            return obj;
                        } catch (Throwable th3) {
                            th2 = th3;
                            atomicReference = c20322u02.f26410a;
                            while (!atomicReference.compareAndSet(aVar4, null)) {
                            }
                            throw th2;
                        }
                    }
                    C20322u0 c20322u03 = this.f26419s;
                    Y41.l lVar = (Y41.l) this.f26418r;
                    aVar = this.f26417q;
                    aVar2 = (C20322u0.a) this.f26421u;
                    C42729a0.b(obj);
                    c20322u0 = c20322u03;
                    r32 = lVar;
                }
                this.f26421u = aVar2;
                this.f26417q = aVar3;
                this.f26418r = c20322u0;
                this.f26419s = null;
                this.f26420t = 2;
                Object objInvoke = r32.invoke(this);
                if (objInvoke == coroutine_suspended) {
                    return coroutine_suspended;
                }
                c20322u02 = c20322u0;
                obj = objInvoke;
                aVar4 = aVar2;
                atomicReference2 = c20322u02.f26410a;
                while (!atomicReference2.compareAndSet(aVar4, null)) {
                }
                aVar3.d(null);
                return obj;
            } catch (Throwable th4) {
                aVar4 = aVar2;
                c20322u02 = c20322u0;
                th2 = th4;
                atomicReference = c20322u02.f26410a;
                while (!atomicReference.compareAndSet(aVar4, null) && atomicReference.get() == aVar4) {
                }
                throw th2;
            }
            aVar3 = aVar;
        } catch (Throwable th5) {
            r12.d(null);
            throw th5;
        }
    }
}
