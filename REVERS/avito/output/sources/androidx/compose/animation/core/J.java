package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: DecayAnimationSpec.kt */
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class J {
    public static final float a(@Y61.k H<Float> h12, float f12, float f13) {
        int i12 = kotlin.jvm.internal.A.f406820a;
        H1<Float, C20318t> h13 = J1.f26075a;
        return ((C20318t) ((S1) h12.b()).d(new C20318t(f12), new C20318t(f13))).f26403a;
    }

    public static H b() {
        return new I(new Z(1.0f, 0.1f));
    }

    @Y61.k
    public static final <T> H<T> c(@Y61.k X x12) {
        return new I(x12);
    }
}
