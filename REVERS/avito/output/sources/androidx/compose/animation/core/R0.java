package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/R0;", "T", "Landroidx/compose/animation/core/V;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class R0<T> implements V<T> {

    /* renamed from: a, reason: collision with root package name */
    public final float f26125a;

    /* renamed from: b, reason: collision with root package name */
    public final float f26126b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public final T f26127c;

    public R0() {
        this(0.0f, 0.0f, null, 7, null);
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    public final K1 a(H1 h12) {
        T t12 = this.f26127c;
        return new Z1(this.f26125a, this.f26126b, t12 == null ? null : (AbstractC20330x) h12.a().invoke(t12));
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof R0)) {
            return false;
        }
        R0 r02 = (R0) obj;
        return r02.f26125a == this.f26125a && r02.f26126b == this.f26126b && kotlin.jvm.internal.L.f(r02.f26127c, this.f26127c);
    }

    public final int hashCode() {
        T t12 = this.f26127c;
        return Float.hashCode(this.f26126b) + androidx.appcompat.app.r.d(this.f26125a, (t12 != null ? t12.hashCode() : 0) * 31, 31);
    }

    public R0(float f12, float f13, @Y61.l T t12) {
        this.f26125a = f12;
        this.f26126b = f13;
        this.f26127c = t12;
    }

    public /* synthetic */ R0(float f12, float f13, Object obj, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? 1.0f : f12, (i12 & 2) != 0 ? 1500.0f : f13, (i12 & 4) != 0 ? null : obj);
    }
}
