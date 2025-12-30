package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: Easing.kt */
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Q {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final F f26121a = new F(0.4f, 0.0f, 0.2f, 1.0f);

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final F f26122b = new F(0.0f, 0.0f, 0.2f, 1.0f);

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public static final F f26123c = new F(0.4f, 0.0f, 1.0f, 1.0f);

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public static final O f26124d = new N() { // from class: androidx.compose.animation.core.O

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f26115a = 1;

        @Override // androidx.compose.animation.core.N
        public final float a(float f12) {
            float f13;
            float f14;
            switch (this.f26115a) {
                case 0:
                    F f15 = P.f26116a;
                    if (f12 < 0.36363637f) {
                        return 7.5625f * f12 * f12;
                    }
                    if (f12 < 0.72727275f) {
                        float f16 = f12 - 0.54545456f;
                        f13 = 7.5625f * f16 * f16;
                        f14 = 0.75f;
                    } else if (f12 < 0.90909094f) {
                        float f17 = f12 - 0.8181818f;
                        f13 = 7.5625f * f17 * f17;
                        f14 = 0.9375f;
                    } else {
                        float f18 = f12 - 0.95454544f;
                        f13 = 7.5625f * f18 * f18;
                        f14 = 0.984375f;
                    }
                    return f13 + f14;
                default:
                    F f19 = Q.f26121a;
                    return f12;
            }
        }
    };
}
