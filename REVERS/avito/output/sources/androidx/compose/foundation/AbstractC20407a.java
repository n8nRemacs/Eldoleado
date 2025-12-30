package androidx.compose.foundation;

import android.view.KeyEvent;
import androidx.compose.foundation.interaction.g;
import androidx.compose.foundation.interaction.o;
import androidx.compose.ui.input.pointer.C22340s;
import androidx.compose.ui.input.pointer.C22342u;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.node.AbstractC22425n;
import androidx.compose.ui.node.InterfaceC22419k;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.semantics.C22553a;
import androidx.compose.ui.unit.q;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.C42822w;
import kotlinx.coroutines.C43259k;
import l0.g;

/* compiled from: Clickable.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b \u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0001\u0015BE\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\b\u0010\r\u001a\u0004\u0018\u00010\f\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\u0004\b\u0013\u0010\u0014¨\u0006\u0016"}, d2 = {"Landroidx/compose/foundation/a;", "Landroidx/compose/ui/node/n;", "Landroidx/compose/ui/node/P0;", "Landroidx/compose/ui/input/key/g;", "Landroidx/compose/ui/node/T0;", "Landroidx/compose/ui/node/TraversableNode;", "Landroidx/compose/foundation/interaction/m;", "interactionSource", "Landroidx/compose/foundation/v1;", "indicationNodeFactory", "", "enabled", "", "onClickLabel", "Landroidx/compose/ui/semantics/i;", "role", "Lkotlin/Function0;", "Lkotlin/G0;", "onClick", "<init>", "(Landroidx/compose/foundation/interaction/m;Landroidx/compose/foundation/v1;ZLjava/lang/String;Landroidx/compose/ui/semantics/i;LY41/a;Lkotlin/jvm/internal/w;)V", "a", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.foundation.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20407a extends AbstractC22425n implements androidx.compose.ui.node.P0, androidx.compose.ui.input.key.g, androidx.compose.ui.node.T0, TraversableNode {

    /* renamed from: H, reason: collision with root package name */
    @Y61.k
    public static final C1560a f26907H = new C1560a(null);

    /* renamed from: A, reason: collision with root package name */
    @Y61.l
    public o.b f26908A;

    /* renamed from: B, reason: collision with root package name */
    @Y61.l
    public g.a f26909B;

    /* renamed from: C, reason: collision with root package name */
    @Y61.k
    public final androidx.collection.H0<o.b> f26910C;

    /* renamed from: D, reason: collision with root package name */
    public long f26911D;

    /* renamed from: E, reason: collision with root package name */
    @Y61.l
    public androidx.compose.foundation.interaction.m f26912E;

    /* renamed from: F, reason: collision with root package name */
    public boolean f26913F;

    /* renamed from: G, reason: collision with root package name */
    @Y61.k
    public final C1560a f26914G;

    /* renamed from: r, reason: collision with root package name */
    @Y61.l
    public androidx.compose.foundation.interaction.m f26915r;

    /* renamed from: s, reason: collision with root package name */
    @Y61.l
    public InterfaceC21084v1 f26916s;

    /* renamed from: t, reason: collision with root package name */
    @Y61.l
    public String f26917t;

    /* renamed from: u, reason: collision with root package name */
    @Y61.l
    public androidx.compose.ui.semantics.i f26918u;

    /* renamed from: v, reason: collision with root package name */
    public boolean f26919v;

    /* renamed from: w, reason: collision with root package name */
    @Y61.k
    public Y41.a<kotlin.G0> f26920w;

    /* renamed from: x, reason: collision with root package name */
    @Y61.k
    public final C20409a1 f26921x;

    /* renamed from: y, reason: collision with root package name */
    @Y61.l
    public androidx.compose.ui.input.pointer.b0 f26922y;

    /* renamed from: z, reason: collision with root package name */
    @Y61.l
    public InterfaceC22419k f26923z;

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/foundation/a$a;", "", "<init>", "()V", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.foundation.a$a, reason: collision with other inner class name */
    public static final class C1560a {
        public /* synthetic */ C1560a(C42822w c42822w) {
            this();
        }

        public C1560a() {
        }
    }

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.foundation.a$b */
    public static final class b extends kotlin.jvm.internal.N implements Y41.a<Boolean> {
        public b() {
            super(0);
        }

        @Override // Y41.a
        public final Boolean invoke() {
            AbstractC20407a.this.f26920w.invoke();
            return Boolean.TRUE;
        }
    }

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.foundation.AbstractClickableNode$onKeyEvent$1", f = "Clickable.kt", i = {}, l = {1191}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.compose.foundation.a$c */
    public static final class c extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public int f26925q;

        /* renamed from: s, reason: collision with root package name */
        public final /* synthetic */ o.b f26927s;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(o.b bVar, Continuation<? super c> continuation) {
            super(2, continuation);
            this.f26927s = bVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            return AbstractC20407a.this.new c(this.f26927s, continuation);
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((c) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i12 = this.f26925q;
            if (i12 == 0) {
                C42729a0.b(obj);
                androidx.compose.foundation.interaction.m mVar = AbstractC20407a.this.f26915r;
                if (mVar != null) {
                    this.f26925q = 1;
                    if (mVar.b(this.f26927s, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i12 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C42729a0.b(obj);
            }
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.foundation.AbstractClickableNode$onKeyEvent$2", f = "Clickable.kt", i = {}, l = {1202}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.compose.foundation.a$d */
    public static final class d extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public int f26928q;

        /* renamed from: s, reason: collision with root package name */
        public final /* synthetic */ o.b f26930s;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(o.b bVar, Continuation<? super d> continuation) {
            super(2, continuation);
            this.f26930s = bVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            return AbstractC20407a.this.new d(this.f26930s, continuation);
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((d) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i12 = this.f26928q;
            if (i12 == 0) {
                C42729a0.b(obj);
                androidx.compose.foundation.interaction.m mVar = AbstractC20407a.this.f26915r;
                if (mVar != null) {
                    o.c cVar = new o.c(this.f26930s);
                    this.f26928q = 1;
                    if (mVar.b(cVar, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i12 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C42729a0.b(obj);
            }
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.foundation.AbstractClickableNode$onPointerEvent$1", f = "Clickable.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.compose.foundation.a$e */
    public static final class e extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {
        public e(Continuation<? super e> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            return AbstractC20407a.this.new e(continuation);
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((e) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            C42729a0.b(obj);
            AbstractC20407a abstractC20407a = AbstractC20407a.this;
            if (abstractC20407a.f26909B == null) {
                g.a aVar = new g.a();
                androidx.compose.foundation.interaction.m mVar = abstractC20407a.f26915r;
                if (mVar != null) {
                    C43259k.d(abstractC20407a.Y1(), null, null, new C20410b(mVar, aVar, null), 3);
                }
                abstractC20407a.f26909B = aVar;
            }
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lkotlinx/coroutines/T;", "Lkotlin/G0;", "<anonymous>", "(Lkotlinx/coroutines/T;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.compose.foundation.AbstractClickableNode$onPointerEvent$2", f = "Clickable.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.compose.foundation.a$f */
    public static final class f extends SuspendLambda implements Y41.p<kotlinx.coroutines.T, Continuation<? super kotlin.G0>, Object> {
        public f(Continuation<? super f> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            return AbstractC20407a.this.new f(continuation);
        }

        @Override // Y41.p
        public final Object invoke(kotlinx.coroutines.T t12, Continuation<? super kotlin.G0> continuation) {
            return ((f) create(t12, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            C42729a0.b(obj);
            AbstractC20407a abstractC20407a = AbstractC20407a.this;
            g.a aVar = abstractC20407a.f26909B;
            if (aVar != null) {
                g.b bVar = new g.b(aVar);
                androidx.compose.foundation.interaction.m mVar = abstractC20407a.f26915r;
                if (mVar != null) {
                    C43259k.d(abstractC20407a.Y1(), null, null, new C20413c(mVar, bVar, null), 3);
                }
                abstractC20407a.f26909B = null;
            }
            return kotlin.G0.f406611a;
        }
    }

    /* compiled from: Clickable.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\u008a@¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/input/pointer/K;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/input/pointer/K;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.foundation.a$g */
    public static final class g implements PointerInputEventHandler {
        public g() {
        }

        @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
        public final Object invoke(androidx.compose.ui.input.pointer.K k12, Continuation<? super kotlin.G0> continuation) {
            Object objO2 = AbstractC20407a.this.o2(k12, continuation);
            return objO2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objO2 : kotlin.G0.f406611a;
        }
    }

    public AbstractC20407a() {
        throw null;
    }

    public AbstractC20407a(androidx.compose.foundation.interaction.m mVar, InterfaceC21084v1 interfaceC21084v1, boolean z12, String str, androidx.compose.ui.semantics.i iVar, Y41.a aVar, C42822w c42822w) {
        this.f26915r = mVar;
        this.f26916s = interfaceC21084v1;
        this.f26917t = str;
        this.f26918u = iVar;
        this.f26919v = z12;
        this.f26920w = aVar;
        androidx.compose.foundation.interaction.m mVar2 = this.f26915r;
        androidx.compose.ui.focus.o0.f39183b.getClass();
        boolean z13 = false;
        this.f26921x = new C20409a1(mVar2, 0, new C20418d(1, this, AbstractC20407a.class, "onFocusChange", "onFocusChange(Z)V", 0), null);
        this.f26910C = androidx.collection.Z.a();
        l0.g.f413384b.getClass();
        this.f26911D = 0L;
        androidx.compose.foundation.interaction.m mVar3 = this.f26915r;
        this.f26912E = mVar3;
        if (mVar3 == null && this.f26916s != null) {
            z13 = true;
        }
        this.f26913F = z13;
        this.f26914G = f26907H;
    }

    @Override // androidx.compose.ui.node.T0
    public final void O(@Y61.k androidx.compose.ui.semantics.F f12) {
        androidx.compose.ui.semantics.i iVar = this.f26918u;
        if (iVar != null) {
            androidx.compose.ui.semantics.C.s(f12, iVar.f41753a);
        }
        String str = this.f26917t;
        b bVar = new b();
        kotlin.reflect.n<Object>[] nVarArr = androidx.compose.ui.semantics.C.f41719a;
        androidx.compose.ui.semantics.k.f41760a.getClass();
        f12.a(androidx.compose.ui.semantics.k.f41762c, new C22553a(str, bVar));
        if (this.f26919v) {
            this.f26921x.O(f12);
        } else {
            androidx.compose.ui.semantics.C.c(f12);
        }
        n2(f12);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0056  */
    @Override // androidx.compose.ui.input.key.g
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean R1(@Y61.k android.view.KeyEvent r11) {
        /*
            r10 = this;
            r10.q2()
            long r0 = androidx.compose.ui.input.key.e.a(r11)
            boolean r2 = r10.f26919v
            r3 = 3
            r4 = 1
            r5 = 0
            androidx.collection.H0<androidx.compose.foundation.interaction.o$b> r6 = r10.f26910C
            r7 = 0
            if (r2 == 0) goto L56
            int r2 = androidx.compose.ui.input.key.e.b(r11)
            androidx.compose.ui.input.key.d$a r8 = androidx.compose.ui.input.key.d.f40069b
            r8.getClass()
            int r8 = androidx.compose.ui.input.key.d.f40071d
            boolean r2 = androidx.compose.ui.input.key.d.a(r2, r8)
            if (r2 == 0) goto L56
            boolean r2 = androidx.compose.foundation.C21086w0.g(r11)
            if (r2 == 0) goto L56
            boolean r2 = r6.a(r0)
            if (r2 != 0) goto L4a
            androidx.compose.foundation.interaction.o$b r2 = new androidx.compose.foundation.interaction.o$b
            long r8 = r10.f26911D
            r2.<init>(r8, r5)
            r6.h(r0, r2)
            androidx.compose.foundation.interaction.m r0 = r10.f26915r
            if (r0 == 0) goto L48
            kotlinx.coroutines.T r0 = r10.Y1()
            androidx.compose.foundation.a$c r1 = new androidx.compose.foundation.a$c
            r1.<init>(r2, r5)
            kotlinx.coroutines.C43259k.d(r0, r5, r5, r1, r3)
        L48:
            r0 = r4
            goto L4b
        L4a:
            r0 = r7
        L4b:
            boolean r11 = r10.s2(r11)
            if (r11 != 0) goto L8e
            if (r0 == 0) goto L54
            goto L8e
        L54:
            r4 = r7
            goto L8e
        L56:
            boolean r2 = r10.f26919v
            if (r2 == 0) goto L54
            int r2 = androidx.compose.ui.input.key.e.b(r11)
            androidx.compose.ui.input.key.d$a r8 = androidx.compose.ui.input.key.d.f40069b
            r8.getClass()
            int r8 = androidx.compose.ui.input.key.d.f40070c
            boolean r2 = androidx.compose.ui.input.key.d.a(r2, r8)
            if (r2 == 0) goto L54
            boolean r2 = androidx.compose.foundation.C21086w0.g(r11)
            if (r2 == 0) goto L54
            java.lang.Object r0 = r6.g(r0)
            androidx.compose.foundation.interaction.o$b r0 = (androidx.compose.foundation.interaction.o.b) r0
            if (r0 == 0) goto L8c
            androidx.compose.foundation.interaction.m r1 = r10.f26915r
            if (r1 == 0) goto L89
            kotlinx.coroutines.T r1 = r10.Y1()
            androidx.compose.foundation.a$d r2 = new androidx.compose.foundation.a$d
            r2.<init>(r0, r5)
            kotlinx.coroutines.C43259k.d(r1, r5, r5, r2, r3)
        L89:
            r10.t2(r11)
        L8c:
            if (r0 == 0) goto L54
        L8e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AbstractC20407a.R1(android.view.KeyEvent):boolean");
    }

    @Override // androidx.compose.ui.v.d
    /* renamed from: Z1 */
    public final boolean getF40330q() {
        return false;
    }

    @Override // androidx.compose.ui.input.key.g
    public final boolean b0(@Y61.k KeyEvent keyEvent) {
        return false;
    }

    @Override // androidx.compose.ui.v.d
    public final void c2() {
        if (!this.f26913F) {
            q2();
        }
        if (this.f26919v) {
            k2(this.f26921x);
        }
    }

    @Override // androidx.compose.ui.v.d
    public final void d2() {
        p2();
        if (this.f26912E == null) {
            this.f26915r = null;
        }
        InterfaceC22419k interfaceC22419k = this.f26923z;
        if (interfaceC22419k != null) {
            l2(interfaceC22419k);
        }
        this.f26923z = null;
    }

    @Override // androidx.compose.ui.node.TraversableNode
    @Y61.k
    /* renamed from: o0 */
    public final Object getF39003r() {
        return this.f26914G;
    }

    @Y61.l
    public abstract Object o2(@Y61.k androidx.compose.ui.input.pointer.K k12, @Y61.k Continuation<? super kotlin.G0> continuation);

    @Override // androidx.compose.ui.node.P0
    public final void p1() {
        g.a aVar;
        androidx.compose.foundation.interaction.m mVar = this.f26915r;
        if (mVar != null && (aVar = this.f26909B) != null) {
            mVar.a(new g.b(aVar));
        }
        this.f26909B = null;
        androidx.compose.ui.input.pointer.b0 b0Var = this.f26922y;
        if (b0Var != null) {
            b0Var.p1();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void p2() {
        /*
            r17 = this;
            r0 = r17
            androidx.compose.foundation.interaction.m r1 = r0.f26915r
            androidx.collection.H0<androidx.compose.foundation.interaction.o$b> r2 = r0.f26910C
            if (r1 == 0) goto L6a
            androidx.compose.foundation.interaction.o$b r3 = r0.f26908A
            if (r3 == 0) goto L14
            androidx.compose.foundation.interaction.o$a r4 = new androidx.compose.foundation.interaction.o$a
            r4.<init>(r3)
            r1.a(r4)
        L14:
            androidx.compose.foundation.interaction.g$a r3 = r0.f26909B
            if (r3 == 0) goto L20
            androidx.compose.foundation.interaction.g$b r4 = new androidx.compose.foundation.interaction.g$b
            r4.<init>(r3)
            r1.a(r4)
        L20:
            java.lang.Object[] r3 = r2.f25654c
            long[] r4 = r2.f25652a
            int r5 = r4.length
            int r5 = r5 + (-2)
            if (r5 < 0) goto L6a
            r6 = 0
            r7 = r6
        L2b:
            r8 = r4[r7]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L65
            int r10 = r7 - r5
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r6
        L45:
            if (r12 >= r10) goto L63
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.3E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L5f
            int r13 = r7 << 3
            int r13 = r13 + r12
            r13 = r3[r13]
            androidx.compose.foundation.interaction.o$b r13 = (androidx.compose.foundation.interaction.o.b) r13
            androidx.compose.foundation.interaction.o$a r14 = new androidx.compose.foundation.interaction.o$a
            r14.<init>(r13)
            r1.a(r14)
        L5f:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L45
        L63:
            if (r10 != r11) goto L6a
        L65:
            if (r7 == r5) goto L6a
            int r7 = r7 + 1
            goto L2b
        L6a:
            r1 = 0
            r0.f26908A = r1
            r0.f26909B = r1
            r2.c()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AbstractC20407a.p2():void");
    }

    public final void q2() {
        InterfaceC21084v1 interfaceC21084v1;
        if (this.f26923z == null && (interfaceC21084v1 = this.f26916s) != null) {
            if (this.f26915r == null) {
                this.f26915r = androidx.compose.foundation.interaction.l.a();
            }
            this.f26921x.p2(this.f26915r);
            InterfaceC22419k interfaceC22419kA = interfaceC21084v1.a(this.f26915r);
            k2(interfaceC22419kA);
            this.f26923z = interfaceC22419kA;
        }
    }

    @Override // androidx.compose.ui.node.P0
    public final void r0(@Y61.k C22340s c22340s, @Y61.k PointerEventPass pointerEventPass, long j12) {
        long j13 = ((j12 >> 33) << 32) | (((j12 << 32) >> 33) & 4294967295L);
        q.a aVar = androidx.compose.ui.unit.q.f42862b;
        g.a aVar2 = l0.g.f413384b;
        this.f26911D = (Float.floatToRawIntBits((int) (j13 >> 32)) << 32) | (Float.floatToRawIntBits((int) (j13 & 4294967295L)) & 4294967295L);
        q2();
        if (this.f26919v && pointerEventPass == PointerEventPass.f40174c) {
            int i12 = c22340s.f40282e;
            C22342u.f40283b.getClass();
            if (C22342u.a(i12, C22342u.f40287f)) {
                C43259k.d(Y1(), null, null, new e(null), 3);
            } else if (C22342u.a(i12, C22342u.f40288g)) {
                C43259k.d(Y1(), null, null, new f(null), 3);
            }
        }
        if (this.f26922y == null) {
            androidx.compose.ui.input.pointer.g0 g0VarB = androidx.compose.ui.input.pointer.Z.b(new g());
            k2(g0VarB);
            this.f26922y = g0VarB;
        }
        androidx.compose.ui.input.pointer.b0 b0Var = this.f26922y;
        if (b0Var != null) {
            b0Var.r0(c22340s, pointerEventPass, j12);
        }
    }

    public abstract boolean s2(@Y61.k KeyEvent keyEvent);

    public abstract void t2(@Y61.k KeyEvent keyEvent);

    /* JADX WARN: Removed duplicated region for block: B:37:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void u2(@Y61.l androidx.compose.foundation.interaction.m r4, @Y61.l androidx.compose.foundation.InterfaceC21084v1 r5, boolean r6, @Y61.l java.lang.String r7, @Y61.l androidx.compose.ui.semantics.i r8, @Y61.k Y41.a<kotlin.G0> r9) {
        /*
            r3 = this;
            androidx.compose.foundation.interaction.m r0 = r3.f26912E
            boolean r0 = kotlin.jvm.internal.L.f(r0, r4)
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L13
            r3.p2()
            r3.f26912E = r4
            r3.f26915r = r4
            r4 = r2
            goto L14
        L13:
            r4 = r1
        L14:
            androidx.compose.foundation.v1 r0 = r3.f26916s
            boolean r0 = kotlin.jvm.internal.L.f(r0, r5)
            if (r0 != 0) goto L1f
            r3.f26916s = r5
            r4 = r2
        L1f:
            boolean r5 = r3.f26919v
            androidx.compose.foundation.a1 r0 = r3.f26921x
            if (r5 == r6) goto L3a
            if (r6 == 0) goto L2b
            r3.k2(r0)
            goto L31
        L2b:
            r3.l2(r0)
            r3.p2()
        L31:
            androidx.compose.ui.node.LayoutNode r5 = androidx.compose.ui.node.C22421l.g(r3)
            r5.Z()
            r3.f26919v = r6
        L3a:
            java.lang.String r5 = r3.f26917t
            boolean r5 = kotlin.jvm.internal.L.f(r5, r7)
            if (r5 != 0) goto L4b
            r3.f26917t = r7
            androidx.compose.ui.node.LayoutNode r5 = androidx.compose.ui.node.C22421l.g(r3)
            r5.Z()
        L4b:
            androidx.compose.ui.semantics.i r5 = r3.f26918u
            boolean r5 = kotlin.jvm.internal.L.f(r5, r8)
            if (r5 != 0) goto L5c
            r3.f26918u = r8
            androidx.compose.ui.node.LayoutNode r5 = androidx.compose.ui.node.C22421l.g(r3)
            r5.Z()
        L5c:
            r3.f26920w = r9
            boolean r5 = r3.f26913F
            androidx.compose.foundation.interaction.m r6 = r3.f26912E
            if (r6 != 0) goto L6a
            androidx.compose.foundation.v1 r7 = r3.f26916s
            if (r7 == 0) goto L6a
            r7 = r2
            goto L6b
        L6a:
            r7 = r1
        L6b:
            if (r5 == r7) goto L7d
            if (r6 != 0) goto L74
            androidx.compose.foundation.v1 r5 = r3.f26916s
            if (r5 == 0) goto L74
            r1 = r2
        L74:
            r3.f26913F = r1
            if (r1 != 0) goto L7d
            androidx.compose.ui.node.k r5 = r3.f26923z
            if (r5 != 0) goto L7d
            goto L7e
        L7d:
            r2 = r4
        L7e:
            if (r2 == 0) goto L93
            androidx.compose.ui.node.k r4 = r3.f26923z
            if (r4 != 0) goto L88
            boolean r5 = r3.f26913F
            if (r5 != 0) goto L93
        L88:
            if (r4 == 0) goto L8d
            r3.l2(r4)
        L8d:
            r4 = 0
            r3.f26923z = r4
            r3.q2()
        L93:
            androidx.compose.foundation.interaction.m r4 = r3.f26915r
            r0.p2(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AbstractC20407a.u2(androidx.compose.foundation.interaction.m, androidx.compose.foundation.v1, boolean, java.lang.String, androidx.compose.ui.semantics.i, Y41.a):void");
    }

    @Override // androidx.compose.ui.node.T0
    /* renamed from: x1 */
    public final boolean getF41731p() {
        return true;
    }

    public void r2() {
    }

    public void n2(@Y61.k androidx.compose.ui.semantics.F f12) {
    }
}
