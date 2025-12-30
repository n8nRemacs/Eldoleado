package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.I3;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationState.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\b\u0012\u0004\u0012\u00028\u00000\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/r;", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/runtime/I3;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class r<T, V extends AbstractC20330x> implements I3<T> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final H1<T, V> f26396b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26397c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public V f26398d;

    /* renamed from: e, reason: collision with root package name */
    public long f26399e;

    /* renamed from: f, reason: collision with root package name */
    public long f26400f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f26401g;

    public r(@Y61.k H1<T, V> h12, T t12, @Y61.l V v12, long j12, long j13, boolean z12) {
        V vInvoke;
        this.f26396b = h12;
        this.f26397c = C22126m3.g(t12);
        if (v12 != null) {
            vInvoke = (V) C20333y.a(v12);
        } else {
            vInvoke = h12.a().invoke(t12);
            vInvoke.d();
        }
        this.f26398d = vInvoke;
        this.f26399e = j12;
        this.f26400f = j13;
        this.f26401g = z12;
    }

    public final T a() {
        return this.f26396b.b().invoke(this.f26398d);
    }

    @Override // androidx.compose.runtime.I3
    /* renamed from: getValue */
    public final T getF42167b() {
        return (T) ((C22082i3) this.f26397c).getF42167b();
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AnimationState(value=");
        sb2.append(((C22082i3) this.f26397c).getF42167b());
        sb2.append(", velocity=");
        sb2.append(a());
        sb2.append(", isRunning=");
        sb2.append(this.f26401g);
        sb2.append(", lastFrameTimeNanos=");
        sb2.append(this.f26399e);
        sb2.append(", finishedTimeNanos=");
        return androidx.appcompat.app.r.u(sb2, this.f26400f, ')');
    }

    public /* synthetic */ r(H1 h12, Object obj, AbstractC20330x abstractC20330x, long j12, long j13, boolean z12, int i12, C42822w c42822w) {
        this(h12, obj, (i12 & 4) != 0 ? null : abstractC20330x, (i12 & 8) != 0 ? Long.MIN_VALUE : j12, (i12 & 16) != 0 ? Long.MIN_VALUE : j13, (i12 & 32) != 0 ? false : z12);
    }
}
