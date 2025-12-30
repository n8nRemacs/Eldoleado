package androidx.compose.animation.core;

import androidx.compose.animation.core.C20322u0;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: InternalMutatorMutex.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\u00028\u0001\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@"}, d2 = {"T", "R", "Lkotlinx/coroutines/T;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.core.MutatorMutex$mutateWith$2", f = "InternalMutatorMutex.kt", i = {0, 0, 1, 1}, l = {176, 163}, m = "invokeSuspend", n = {"mutator", "$this$withLock_u24default$iv", "mutator", "$this$withLock_u24default$iv"}, s = {"L$0", "L$1", "L$0", "L$1"})
/* renamed from: androidx.compose.animation.core.w0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20328w0 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<Object>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public kotlinx.coroutines.sync.a f26429q;

    /* renamed from: r, reason: collision with root package name */
    public C20322u0 f26430r;

    /* renamed from: s, reason: collision with root package name */
    public int f26431s;

    /* renamed from: t, reason: collision with root package name */
    public /* synthetic */ Object f26432t;

    public C20328w0() {
        throw null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        throw null;
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<Object> continuation) {
        throw null;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.sync.a] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        C20322u0 c20322u0;
        C20322u0.a aVar;
        kotlinx.coroutines.sync.a aVar2;
        AtomicReference<C20322u0.a> atomicReference;
        AtomicReference<C20322u0.a> atomicReference2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        ?? r12 = this.f26431s;
        if (r12 == 0) {
            C42729a0.b(obj);
            throw null;
        }
        try {
            if (r12 != 1) {
                if (r12 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                c20322u0 = this.f26430r;
                aVar2 = this.f26429q;
                aVar = (C20322u0.a) this.f26432t;
                try {
                    C42729a0.b(obj);
                    atomicReference2 = c20322u0.f26410a;
                    while (!atomicReference2.compareAndSet(aVar, null) && atomicReference2.get() == aVar) {
                    }
                    aVar2.d(null);
                    return obj;
                } catch (Throwable th2) {
                    th = th2;
                    atomicReference = c20322u0.f26410a;
                    while (!atomicReference.compareAndSet(aVar, null)) {
                    }
                    throw th;
                }
            }
            Y41.p pVar = (Y41.p) this.f26430r;
            kotlinx.coroutines.sync.a aVar3 = this.f26429q;
            C20322u0.a aVar4 = (C20322u0.a) this.f26432t;
            C42729a0.b(obj);
            try {
                this.f26432t = aVar4;
                this.f26429q = aVar3;
                this.f26430r = null;
                this.f26431s = 2;
                obj = pVar.invoke(null, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                c20322u0 = null;
                aVar2 = aVar3;
                aVar = aVar4;
                atomicReference2 = c20322u0.f26410a;
                while (!atomicReference2.compareAndSet(aVar, null)) {
                }
                aVar2.d(null);
                return obj;
            } catch (Throwable th3) {
                th = th3;
                c20322u0 = null;
                aVar = aVar4;
                atomicReference = c20322u0.f26410a;
                while (!atomicReference.compareAndSet(aVar, null) && atomicReference.get() == aVar) {
                }
                throw th;
            }
        } catch (Throwable th4) {
            r12.d(null);
            throw th4;
        }
    }
}
