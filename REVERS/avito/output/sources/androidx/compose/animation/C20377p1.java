package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0081\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/p1;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.p1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* data */ class C20377p1 {

    /* renamed from: a, reason: collision with root package name */
    public final float f26542a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.animation.core.V<Float> f26543b;

    public C20377p1(float f12, @Y61.k androidx.compose.animation.core.V<Float> v12) {
        this.f26542a = f12;
        this.f26543b = v12;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C20377p1)) {
            return false;
        }
        C20377p1 c20377p1 = (C20377p1) obj;
        return Float.compare(this.f26542a, c20377p1.f26542a) == 0 && kotlin.jvm.internal.L.f(this.f26543b, c20377p1.f26543b);
    }

    public final int hashCode() {
        return this.f26543b.hashCode() + (Float.hashCode(this.f26542a) * 31);
    }

    @Y61.k
    public final String toString() {
        return "Fade(alpha=" + this.f26542a + ", animationSpec=" + this.f26543b + ')';
    }
}
