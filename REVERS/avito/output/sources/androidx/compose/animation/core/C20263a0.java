package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: FloatAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/a0;", "Landroidx/compose/animation/core/W;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.a0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20263a0 implements W {

    /* renamed from: a, reason: collision with root package name */
    public final float f26190a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final P0 f26191b;

    public C20263a0() {
        this(0.0f, 0.0f, 0.0f, 7, null);
    }

    @Override // androidx.compose.animation.core.W
    public final float c(long j12, float f12, float f13, float f14) {
        P0 p02 = this.f26191b;
        p02.f26118a = f13;
        return Float.intBitsToFloat((int) (p02.a(f12, f14, j12 / 1000000) & 4294967295L));
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0215  */
    @Override // androidx.compose.animation.core.W
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final long d(float r33, float r34, float r35) {
        /*
            Method dump skipped, instructions count: 586
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C20263a0.d(float, float, float):long");
    }

    @Override // androidx.compose.animation.core.W
    public final float e(float f12, float f13, float f14) {
        return 0.0f;
    }

    @Override // androidx.compose.animation.core.W
    public final float f(long j12, float f12, float f13, float f14) {
        P0 p02 = this.f26191b;
        p02.f26118a = f13;
        return Float.intBitsToFloat((int) (p02.a(f12, f14, j12 / 1000000) >> 32));
    }

    public C20263a0(float f12, float f13, float f14) {
        this.f26190a = f14;
        P0 p02 = new P0();
        p02.f26118a = 1.0f;
        p02.f26119b = Math.sqrt(50.0d);
        p02.f26120c = 1.0f;
        if (f12 < 0.0f) {
            C20334y0.a("Damping ratio must be non-negative");
        }
        p02.f26120c = f12;
        double d12 = p02.f26119b;
        if (((float) (d12 * d12)) <= 0.0f) {
            C20334y0.a("Spring stiffness constant must be positive.");
        }
        p02.f26119b = Math.sqrt(f13);
        this.f26191b = p02;
    }

    public /* synthetic */ C20263a0(float f12, float f13, float f14, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? 1.0f : f12, (i12 & 2) != 0 ? 1500.0f : f13, (i12 & 4) != 0 ? 0.01f : f14);
    }
}
