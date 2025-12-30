package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0081\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/u2;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class u2 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final kotlin.jvm.internal.N f26596a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.animation.core.V<androidx.compose.ui.unit.q> f26597b;

    /* JADX WARN: Multi-variable type inference failed */
    public u2(@Y61.k Y41.l<? super androidx.compose.ui.unit.u, androidx.compose.ui.unit.q> lVar, @Y61.k androidx.compose.animation.core.V<androidx.compose.ui.unit.q> v12) {
        this.f26596a = (kotlin.jvm.internal.N) lVar;
        this.f26597b = v12;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof u2)) {
            return false;
        }
        u2 u2Var = (u2) obj;
        return this.f26596a.equals(u2Var.f26596a) && this.f26597b.equals(u2Var.f26597b);
    }

    public final int hashCode() {
        return this.f26597b.hashCode() + (this.f26596a.hashCode() * 31);
    }

    @Y61.k
    public final String toString() {
        return "Slide(slideOffset=" + this.f26596a + ", animationSpec=" + this.f26597b + ')';
    }
}
