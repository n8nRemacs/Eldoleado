package androidx.compose.animation.core;

import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: Transition.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"S", "Lkotlin/G0;", "<anonymous>", "()V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.core.SeekableTransitionState$seekTo$3", f = "Transition.kt", i = {}, l = {488}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class I0 extends SuspendLambda implements Y41.l<Continuation<? super kotlin.G0>, Object> {

    /* compiled from: Transition.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"S", "Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.animation.core.SeekableTransitionState$seekTo$3$1", f = "Transition.kt", i = {}, l = {510}, m = "invokeSuspend", n = {}, s = {})
    public static final class a extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

        /* compiled from: Transition.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"S", "Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
        @DebugMetadata(c = "androidx.compose.animation.core.SeekableTransitionState$seekTo$3$1$1", f = "Transition.kt", i = {}, l = {506}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: androidx.compose.animation.core.I0$a$a, reason: collision with other inner class name */
        public static final class C1550a extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

            /* renamed from: q, reason: collision with root package name */
            public int f26072q;

            public C1550a() {
                throw null;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
                throw null;
            }

            @Override // Y41.p
            public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
                throw null;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i12 = this.f26072q;
                if (i12 == 0) {
                    C42729a0.b(obj);
                    this.f26072q = 1;
                    throw null;
                }
                if (i12 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C42729a0.b(obj);
                return kotlin.G0.f406611a;
            }
        }

        public a() {
            throw null;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            throw null;
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            throw null;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            throw null;
        }
    }

    public I0() {
        throw null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Continuation<?> continuation) {
        throw null;
    }

    @Override // Y41.l
    public final Object invoke(Continuation<? super kotlin.G0> continuation) {
        throw null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        C42729a0.b(obj);
        throw null;
    }
}
