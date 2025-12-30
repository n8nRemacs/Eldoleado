package androidx.compose.foundation;

import androidx.compose.foundation.gestures.InterfaceC20509u1;
import androidx.compose.foundation.interaction.o;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: Clickable.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.foundation.AbstractClickableNode$handlePressInteraction$2$1", f = "Clickable.kt", i = {0, 1, 2}, l = {1263, 1265, 1272, 1273, 1283}, m = "invokeSuspend", n = {"delayJob", "success", "release"}, s = {"L$0", "Z$0", "L$0"})
/* renamed from: androidx.compose.foundation.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20421e extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public boolean f27120q;

    /* renamed from: r, reason: collision with root package name */
    public int f27121r;

    /* renamed from: s, reason: collision with root package name */
    public /* synthetic */ Object f27122s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20509u1 f27123t;

    /* renamed from: u, reason: collision with root package name */
    public final /* synthetic */ long f27124u;

    /* renamed from: v, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.foundation.interaction.m f27125v;

    /* renamed from: w, reason: collision with root package name */
    public final /* synthetic */ AbstractC20407a f27126w;

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.foundation.AbstractClickableNode$handlePressInteraction$2$1$delayJob$1", f = "Clickable.kt", i = {1}, l = {1257, 1260}, m = "invokeSuspend", n = {"press"}, s = {"L$0"})
    /* renamed from: androidx.compose.foundation.e$a */
    public static final class a extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public o.b f27127q;

        /* renamed from: r, reason: collision with root package name */
        public int f27128r;

        /* renamed from: s, reason: collision with root package name */
        public final /* synthetic */ AbstractC20407a f27129s;

        /* renamed from: t, reason: collision with root package name */
        public final /* synthetic */ long f27130t;

        /* renamed from: u, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.foundation.interaction.m f27131u;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(AbstractC20407a abstractC20407a, long j12, androidx.compose.foundation.interaction.m mVar, Continuation<? super a> continuation) {
            super(2, continuation);
            this.f27129s = abstractC20407a;
            this.f27130t = j12;
            this.f27131u = mVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            return new a(this.f27129s, this.f27130t, this.f27131u, continuation);
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((a) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:77:0x0107  */
        /* JADX WARN: Type inference failed for: r12v21 */
        /* JADX WARN: Type inference failed for: r12v6 */
        /* JADX WARN: Type inference failed for: r12v7, types: [androidx.compose.ui.v$d] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r17) {
            /*
                Method dump skipped, instructions count: 304
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.C20421e.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20421e(InterfaceC20509u1 interfaceC20509u1, long j12, androidx.compose.foundation.interaction.m mVar, AbstractC20407a abstractC20407a, Continuation<? super C20421e> continuation) {
        super(2, continuation);
        this.f27123t = interfaceC20509u1;
        this.f27124u = j12;
        this.f27125v = mVar;
        this.f27126w = abstractC20407a;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
        C20421e c20421e = new C20421e(this.f27123t, this.f27124u, this.f27125v, this.f27126w, continuation);
        c20421e.f27122s = obj;
        return c20421e;
    }

    @Override // Y41.p
    public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
        return ((C20421e) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ae A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 209
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.C20421e.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
