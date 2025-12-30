package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: AnimationVectors.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/t;", "Landroidx/compose/animation/core/x;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.t, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20318t extends AbstractC20330x {

    /* renamed from: a, reason: collision with root package name */
    public float f26403a;

    /* renamed from: b, reason: collision with root package name */
    public final int f26404b;

    public C20318t(float f12) {
        super(null);
        this.f26403a = f12;
        this.f26404b = 1;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final float a(int i12) {
        if (i12 == 0) {
            return this.f26403a;
        }
        return 0.0f;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    /* renamed from: b, reason: from getter */
    public final int getF26409c() {
        return this.f26404b;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final AbstractC20330x c() {
        return new C20318t(0.0f);
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void d() {
        this.f26403a = 0.0f;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void e(float f12, int i12) {
        if (i12 == 0) {
            this.f26403a = f12;
        }
    }

    public final boolean equals(@Y61.l Object obj) {
        return (obj instanceof C20318t) && ((C20318t) obj).f26403a == this.f26403a;
    }

    public final int hashCode() {
        return Float.hashCode(this.f26403a);
    }

    @Y61.k
    public final String toString() {
        return "AnimationVector1D: value = " + this.f26403a;
    }
}
