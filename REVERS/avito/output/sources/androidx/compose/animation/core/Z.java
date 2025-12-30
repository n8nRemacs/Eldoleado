package androidx.compose.animation.core;

import j.InterfaceC42167x;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: FloatDecayAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/Z;", "Landroidx/compose/animation/core/X;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Z implements X {

    /* renamed from: a, reason: collision with root package name */
    public final float f26186a;

    /* renamed from: b, reason: collision with root package name */
    public final float f26187b;

    /* JADX WARN: Illegal instructions before constructor call */
    public Z() {
        float f12 = 0.0f;
        this(f12, f12, 3, null);
    }

    @Override // androidx.compose.animation.core.X
    /* renamed from: a, reason: from getter */
    public final float getF26186a() {
        return this.f26186a;
    }

    @Override // androidx.compose.animation.core.X
    public final long b(float f12) {
        return ((long) ((((float) Math.log(this.f26186a / Math.abs(f12))) * 1000.0f) / this.f26187b)) * 1000000;
    }

    @Override // androidx.compose.animation.core.X
    public final float c(float f12, float f13) {
        if (Math.abs(f13) <= this.f26186a) {
            return f12;
        }
        double dLog = Math.log(Math.abs(r1 / f13));
        float f14 = this.f26187b;
        return ((f13 / f14) * ((float) Math.exp((f14 * ((dLog / f14) * 1000)) / 1000.0f))) + (f12 - (f13 / f14));
    }

    @Override // androidx.compose.animation.core.X
    public final float d(float f12, float f13, long j12) {
        float f14 = this.f26187b;
        return ((f13 / f14) * ((float) Math.exp((f14 * (j12 / 1000000)) / 1000.0f))) + (f12 - (f13 / f14));
    }

    @Override // androidx.compose.animation.core.X
    public final float e(long j12, float f12) {
        return f12 * ((float) Math.exp(((j12 / 1000000) / 1000.0f) * this.f26187b));
    }

    public Z(@InterfaceC42167x float f12, @InterfaceC42167x float f13) {
        this.f26186a = Math.max(1.0E-7f, Math.abs(f13));
        this.f26187b = Math.max(1.0E-4f, f12) * (-4.2f);
    }

    public /* synthetic */ Z(float f12, float f13, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? 1.0f : f12, (i12 & 2) != 0 ? 0.1f : f13);
    }
}
