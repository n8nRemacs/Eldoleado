package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: VectorizedAnimationSpec.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class N1 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final int[] f26112a = new int[0];

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final float[] f26113b = new float[0];

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public static final C f26114c = new C(new int[2], new float[2], new float[][]{new float[2], new float[2]});

    public static final long a(@Y61.k P1<?> p12, long j12) {
        long f26185a = j12 - p12.getF26185a();
        long jG2 = p12.g();
        if (f26185a < 0) {
            f26185a = 0;
        }
        return f26185a > jG2 ? jG2 : f26185a;
    }
}
