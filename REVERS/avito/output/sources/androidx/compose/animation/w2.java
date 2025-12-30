package androidx.compose.animation;

import androidx.compose.animation.C20380q1;
import kotlin.Metadata;

/* compiled from: SplineBasedFloatDecayAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/w2;", "Landroidx/compose/animation/core/X;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class w2 implements androidx.compose.animation.core.X {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final C20380q1 f26607a;

    public w2(@Y61.k androidx.compose.ui.unit.d dVar) {
        this.f26607a = new C20380q1(x2.f26617a, dVar);
    }

    @Override // androidx.compose.animation.core.X
    /* renamed from: a */
    public final float getF26186a() {
        return 0.0f;
    }

    @Override // androidx.compose.animation.core.X
    public final long b(float f12) {
        return ((long) (Math.exp(this.f26607a.b(f12) / (C20382r1.f26572a - 1.0d)) * 1000.0d)) * 1000000;
    }

    @Override // androidx.compose.animation.core.X
    public final float c(float f12, float f13) {
        double dB2 = this.f26607a.b(f13);
        double d12 = C20382r1.f26572a;
        return (Math.signum(f13) * ((float) (Math.exp((d12 / (d12 - 1.0d)) * dB2) * r0.f26555a * r0.f26557c))) + f12;
    }

    @Override // androidx.compose.animation.core.X
    public final float d(float f12, float f13, long j12) {
        long j13 = j12 / 1000000;
        C20380q1.a aVarA = this.f26607a.a(f13);
        long j14 = aVarA.f26560c;
        float f14 = j14 > 0 ? j13 / j14 : 1.0f;
        float fSignum = Math.signum(aVarA.f26558a) * aVarA.f26559b;
        C20254b.f26001a.getClass();
        return (fSignum * C20254b.a(f14).f26003a) + f12;
    }

    @Override // androidx.compose.animation.core.X
    public final float e(long j12, float f12) {
        long j13 = j12 / 1000000;
        C20380q1.a aVarA = this.f26607a.a(f12);
        long j14 = aVarA.f26560c;
        float f13 = j14 > 0 ? j13 / j14 : 1.0f;
        C20254b.f26001a.getClass();
        return (((Math.signum(aVarA.f26558a) * C20254b.a(f13).f26004b) * aVarA.f26559b) / j14) * 1000.0f;
    }
}
