package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: Transition.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/C0;", "S", "Landroidx/compose/animation/core/F1;", "a", "b", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class C0<S> extends F1<S> {

    /* renamed from: c, reason: collision with root package name */
    public static final /* synthetic */ int f26044c = 0;

    /* renamed from: b, reason: collision with root package name */
    @Y61.l
    public C20288i1<S> f26045b;

    /* compiled from: Transition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/C0$a;", "", "<init>", "()V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    /* compiled from: Transition.kt */
    @androidx.compose.runtime.internal.P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/C0$b;", "", "<init>", "()V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        @Y61.k
        public final C20318t f26046a = new C20318t(0.0f);

        @Y61.k
        public final String toString() {
            return "progress nanos: 0, animationSpec: null, isComplete: false, value: 0.0, start: " + this.f26046a + ", initialVelocity: null, durationNanos: 0, animationSpecDuration: 0";
        }
    }

    static {
        new a(null);
        new C20318t(0.0f);
        new C20318t(1.0f);
    }

    @Override // androidx.compose.animation.core.F1
    public final S a() {
        throw null;
    }

    @Override // androidx.compose.animation.core.F1
    public final void b(S s5) {
        throw null;
    }

    @Override // androidx.compose.animation.core.F1
    public final void c(@Y61.k C20288i1<S> c20288i1) {
        C20288i1<S> c20288i12 = this.f26045b;
        if (!(c20288i12 == null || kotlin.jvm.internal.L.f(c20288i1, c20288i12))) {
            C20334y0.b("An instance of SeekableTransitionState has been used in different Transitions. Previous instance: " + this.f26045b + ", new instance: " + c20288i1);
        }
        this.f26045b = c20288i1;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, kotlin.C] */
    @Override // androidx.compose.animation.core.F1
    public final void d() {
        this.f26045b = null;
        ((androidx.compose.runtime.snapshots.H) C20300m1.f26369b.getValue()).c(this);
    }
}
