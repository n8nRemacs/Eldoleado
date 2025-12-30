package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: AnimationVectors.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/u;", "Landroidx/compose/animation/core/x;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.u, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20321u extends AbstractC20330x {

    /* renamed from: a, reason: collision with root package name */
    public float f26407a;

    /* renamed from: b, reason: collision with root package name */
    public float f26408b;

    /* renamed from: c, reason: collision with root package name */
    public final int f26409c;

    public C20321u(float f12, float f13) {
        super(null);
        this.f26407a = f12;
        this.f26408b = f13;
        this.f26409c = 2;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final float a(int i12) {
        if (i12 == 0) {
            return this.f26407a;
        }
        if (i12 != 1) {
            return 0.0f;
        }
        return this.f26408b;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    /* renamed from: b, reason: from getter */
    public final int getF26409c() {
        return this.f26409c;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final AbstractC20330x c() {
        return new C20321u(0.0f, 0.0f);
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void d() {
        this.f26407a = 0.0f;
        this.f26408b = 0.0f;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void e(float f12, int i12) {
        if (i12 == 0) {
            this.f26407a = f12;
        } else {
            if (i12 != 1) {
                return;
            }
            this.f26408b = f12;
        }
    }

    public final boolean equals(@Y61.l Object obj) {
        if (obj instanceof C20321u) {
            C20321u c20321u = (C20321u) obj;
            if (c20321u.f26407a == this.f26407a && c20321u.f26408b == this.f26408b) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Float.hashCode(this.f26408b) + (Float.hashCode(this.f26407a) * 31);
    }

    @Y61.k
    public final String toString() {
        return "AnimationVector2D: v1 = " + this.f26407a + ", v2 = " + this.f26408b;
    }
}
