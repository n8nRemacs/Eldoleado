package androidx.compose.animation.core;

import androidx.compose.runtime.C22072g3;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.InterfaceC22200x1;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: Transition.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"S", "Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.core.Transition$animateTo$1$1$1", f = "Transition.kt", i = {0, 0}, l = {1192}, m = "invokeSuspend", n = {"$this$launch", "durationScale"}, s = {"L$0", "F$0"})
/* renamed from: androidx.compose.animation.core.j1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20291j1 extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public float f26359q;

    /* renamed from: r, reason: collision with root package name */
    public int f26360r;

    /* renamed from: s, reason: collision with root package name */
    public /* synthetic */ Object f26361s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f26362t;

    /* compiled from: Transition.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"S", "", "it", "Lkotlin/G0;", "invoke", "(J)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.j1$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<Long, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20288i1<Object> f26363l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ float f26364m;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(C20288i1<Object> c20288i1, float f12) {
            super(1);
            this.f26363l = c20288i1;
            this.f26364m = f12;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(Long l12) {
            long jLongValue = l12.longValue();
            C20288i1<Object> c20288i1 = this.f26363l;
            if (!c20288i1.g()) {
                InterfaceC22200x1 interfaceC22200x1 = c20288i1.f26323g;
                C22072g3 c22072g3 = (C22072g3) interfaceC22200x1;
                if (c22072g3.g() == Long.MIN_VALUE) {
                    ((C22072g3) interfaceC22200x1).n(jLongValue);
                    ((C22082i3) c20288i1.f26317a.f26057a).setValue(Boolean.TRUE);
                }
                long jG2 = jLongValue - c22072g3.g();
                float f12 = this.f26364m;
                if (f12 != 0.0f) {
                    jG2 = kotlin.math.b.c(jG2 / f12);
                }
                if (c20288i1.f26318b == null) {
                    ((C22072g3) c20288i1.f26322f).n(jG2);
                }
                c20288i1.h(jG2, f12 == 0.0f);
            }
            return kotlin.G0.f406611a;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20291j1(C20288i1<Object> c20288i1, Continuation<? super C20291j1> continuation) {
        super(2, continuation);
        this.f26362t = c20288i1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        C20291j1 c20291j1 = new C20291j1(this.f26362t, continuation);
        c20291j1.f26361s = obj;
        return c20291j1;
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((C20291j1) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        float fI2;
        kotlinx.coroutines.T t12;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f26360r;
        if (i12 == 0) {
            C42729a0.b(obj);
            kotlinx.coroutines.T t13 = (kotlinx.coroutines.T) this.f26361s;
            fI2 = C20273d1.i(t13.getF401596i());
            t12 = t13;
        } else {
            if (i12 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            fI2 = this.f26359q;
            t12 = (kotlinx.coroutines.T) this.f26361s;
            C42729a0.b(obj);
        }
        while (kotlinx.coroutines.U.e(t12)) {
            a aVar = new a(this.f26362t, fI2);
            this.f26361s = t12;
            this.f26359q = fI2;
            this.f26360r = 1;
            if (androidx.compose.runtime.X0.a(getF411447c()).N(aVar, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return kotlin.G0.f406611a;
    }
}
