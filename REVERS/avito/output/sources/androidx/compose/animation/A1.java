package androidx.compose.animation;

import androidx.compose.ui.graphics.r1;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: EnterExitTransition.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0081\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/compose/animation/A1;", "", "", "scale", "Landroidx/compose/ui/graphics/r1;", "transformOrigin", "Landroidx/compose/animation/core/V;", "animationSpec", "<init>", "(FJLandroidx/compose/animation/core/V;Lkotlin/jvm/internal/w;)V", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class A1 {

    /* renamed from: a, reason: collision with root package name */
    public final float f25838a;

    /* renamed from: b, reason: collision with root package name */
    public final long f25839b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.animation.core.V<Float> f25840c;

    public A1() {
        throw null;
    }

    public A1(float f12, long j12, androidx.compose.animation.core.V v12, C42822w c42822w) {
        this.f25838a = f12;
        this.f25839b = j12;
        this.f25840c = v12;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof A1)) {
            return false;
        }
        A1 a12 = (A1) obj;
        return Float.compare(this.f25838a, a12.f25838a) == 0 && androidx.compose.ui.graphics.r1.b(this.f25839b, a12.f25839b) && kotlin.jvm.internal.L.f(this.f25840c, a12.f25840c);
    }

    public final int hashCode() {
        int iHashCode = Float.hashCode(this.f25838a) * 31;
        r1.a aVar = androidx.compose.ui.graphics.r1.f39755b;
        return this.f25840c.hashCode() + androidx.appcompat.app.r.g(iHashCode, 31, this.f25839b);
    }

    @Y61.k
    public final String toString() {
        return "Scale(scale=" + this.f25838a + ", transformOrigin=" + ((Object) androidx.compose.ui.graphics.r1.e(this.f25839b)) + ", animationSpec=" + this.f25840c + ')';
    }
}
