package androidx.compose.animation.core;

import androidx.compose.runtime.C22082i3;
import java.util.concurrent.CancellationException;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.l0;

/* compiled from: Animatable.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/n;", "<anonymous>", "()Landroidx/compose/animation/core/n;"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "androidx.compose.animation.core.Animatable$runAnimation$2", f = "Animatable.kt", i = {0, 0}, l = {305}, m = "invokeSuspend", n = {"endState", "clampingNeeded"}, s = {"L$0", "L$1"})
/* renamed from: androidx.compose.animation.core.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20265b extends SuspendLambda implements Y41.l<Continuation<? super C20301n<Object, AbstractC20330x>>, Object> {

    /* renamed from: q, reason: collision with root package name */
    public r f26203q;

    /* renamed from: r, reason: collision with root package name */
    public l0.a f26204r;

    /* renamed from: s, reason: collision with root package name */
    public int f26205s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ C20268c<Object, AbstractC20330x> f26206t;

    /* renamed from: u, reason: collision with root package name */
    public final /* synthetic */ Object f26207u;

    /* renamed from: v, reason: collision with root package name */
    public final /* synthetic */ C20285h1 f26208v;

    /* renamed from: w, reason: collision with root package name */
    public final /* synthetic */ long f26209w;

    /* renamed from: x, reason: collision with root package name */
    public final /* synthetic */ Y41.l<C20268c<Object, AbstractC20330x>, kotlin.G0> f26210x;

    /* compiled from: Animatable.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0007\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001*\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/o;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/animation/core/o;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.core.b$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<C20304o<Object, AbstractC20330x>, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ C20268c<Object, AbstractC20330x> f26211l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ r<Object, AbstractC20330x> f26212m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ Y41.l<C20268c<Object, AbstractC20330x>, kotlin.G0> f26213n;

        /* renamed from: o, reason: collision with root package name */
        public final /* synthetic */ l0.a f26214o;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public a(C20268c<Object, AbstractC20330x> c20268c, r<Object, AbstractC20330x> rVar, Y41.l<? super C20268c<Object, AbstractC20330x>, kotlin.G0> lVar, l0.a aVar) {
            super(1);
            this.f26211l = c20268c;
            this.f26212m = rVar;
            this.f26213n = lVar;
            this.f26214o = aVar;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(C20304o<Object, AbstractC20330x> c20304o) {
            C20304o<Object, AbstractC20330x> c20304o2 = c20304o;
            C20268c<Object, AbstractC20330x> c20268c = this.f26211l;
            C20273d1.j(c20304o2, c20268c.f26225d);
            C22082i3 c22082i3 = (C22082i3) c20304o2.f26389e;
            Object objD = c20268c.d(c22082i3.getF42167b());
            boolean zF2 = kotlin.jvm.internal.L.f(objD, c22082i3.getF42167b());
            Y41.l<C20268c<Object, AbstractC20330x>, kotlin.G0> lVar = this.f26213n;
            if (!zF2) {
                ((C22082i3) c20268c.f26225d.f26397c).setValue(objD);
                ((C22082i3) this.f26212m.f26397c).setValue(objD);
                if (lVar != null) {
                    lVar.invoke(c20268c);
                }
                c20304o2.a();
                this.f26214o.f406838b = true;
            } else if (lVar != null) {
                lVar.invoke(c20268c);
            }
            return kotlin.G0.f406611a;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20265b(C20268c c20268c, Object obj, C20285h1 c20285h1, long j12, Y41.l lVar, Continuation continuation) {
        super(1, continuation);
        this.f26206t = c20268c;
        this.f26207u = obj;
        this.f26208v = c20285h1;
        this.f26209w = j12;
        this.f26210x = lVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<kotlin.G0> create(Continuation<?> continuation) {
        return new C20265b(this.f26206t, this.f26207u, this.f26208v, this.f26209w, this.f26210x, continuation);
    }

    @Override // Y41.l
    public final Object invoke(Continuation<? super C20301n<Object, AbstractC20330x>> continuation) {
        return ((C20265b) create(continuation)).invokeSuspend(kotlin.G0.f406611a);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        l0.a aVar;
        r rVar;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i12 = this.f26205s;
        C20268c<Object, AbstractC20330x> c20268c = this.f26206t;
        try {
            if (i12 == 0) {
                C42729a0.b(obj);
                c20268c.f26225d.f26398d = (V) c20268c.f26222a.a().invoke(this.f26207u);
                C20285h1 c20285h1 = this.f26208v;
                ((C22082i3) c20268c.f26227f).setValue(c20285h1.f26308c);
                ((C22082i3) c20268c.f26226e).setValue(Boolean.TRUE);
                r<Object, V> rVar2 = c20268c.f26225d;
                r rVar3 = new r(rVar2.f26396b, ((C22082i3) rVar2.f26397c).getF42167b(), C20333y.a(rVar2.f26398d), rVar2.f26399e, Long.MIN_VALUE, rVar2.f26401g);
                l0.a aVar2 = new l0.a();
                long j12 = this.f26209w;
                a aVar3 = new a(c20268c, rVar3, this.f26210x, aVar2);
                this.f26203q = rVar3;
                this.f26204r = aVar2;
                this.f26205s = 1;
                if (C20273d1.b(rVar3, c20285h1, j12, aVar3, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                aVar = aVar2;
                rVar = rVar3;
            } else {
                if (i12 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                aVar = this.f26204r;
                rVar = this.f26203q;
                C42729a0.b(obj);
            }
            AnimationEndReason animationEndReason = aVar.f406838b ? AnimationEndReason.f26014b : AnimationEndReason.f26015c;
            C20268c.a(c20268c);
            return new C20301n(rVar, animationEndReason);
        } catch (CancellationException e12) {
            C20268c.a(c20268c);
            throw e12;
        }
    }
}
