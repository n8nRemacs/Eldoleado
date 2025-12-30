package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: FloatAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/b0;", "Landroidx/compose/animation/core/W;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.b0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20266b0 implements W {

    /* renamed from: a, reason: collision with root package name */
    public final int f26215a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final N f26216b;

    /* renamed from: c, reason: collision with root package name */
    public final long f26217c;

    /* renamed from: d, reason: collision with root package name */
    public final long f26218d;

    public C20266b0() {
        this(0, 0, null, 7, null);
    }

    @Override // androidx.compose.animation.core.W
    public final float c(long j12, float f12, float f13, float f14) {
        long j13 = j12 - this.f26218d;
        if (j13 < 0) {
            j13 = 0;
        }
        long j14 = this.f26217c;
        long j15 = j13 > j14 ? j14 : j13;
        if (j15 == 0) {
            return f14;
        }
        return (f(j15, f12, f13, f14) - f(j15 - 1000000, f12, f13, f14)) * 1000.0f;
    }

    @Override // androidx.compose.animation.core.W
    public final long d(float f12, float f13, float f14) {
        return this.f26218d + this.f26217c;
    }

    @Override // androidx.compose.animation.core.W
    public final float f(long j12, float f12, float f13, float f14) {
        long j13 = j12 - this.f26218d;
        if (j13 < 0) {
            j13 = 0;
        }
        long j14 = this.f26217c;
        if (j13 > j14) {
            j13 = j14;
        }
        float fA2 = this.f26216b.a(this.f26215a == 0 ? 1.0f : j13 / j14);
        return (f13 * fA2) + ((1 - fA2) * f12);
    }

    public C20266b0(int i12, int i13, N n12, int i14, C42822w c42822w) {
        this((i14 & 1) != 0 ? 300 : i12, (i14 & 2) != 0 ? 0 : i13, (i14 & 4) != 0 ? Q.f26121a : n12);
    }

    public C20266b0(int i12, int i13, @Y61.k N n12) {
        this.f26215a = i12;
        this.f26216b = n12;
        this.f26217c = i12 * 1000000;
        this.f26218d = i13 * 1000000;
    }
}
