package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: AnimationVectors.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/v;", "Landroidx/compose/animation/core/x;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.v, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20324v extends AbstractC20330x {

    /* renamed from: a, reason: collision with root package name */
    public float f26413a;

    /* renamed from: b, reason: collision with root package name */
    public float f26414b;

    /* renamed from: c, reason: collision with root package name */
    public float f26415c;

    /* renamed from: d, reason: collision with root package name */
    public final int f26416d;

    public C20324v(float f12, float f13, float f14) {
        super(null);
        this.f26413a = f12;
        this.f26414b = f13;
        this.f26415c = f14;
        this.f26416d = 3;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final float a(int i12) {
        if (i12 == 0) {
            return this.f26413a;
        }
        if (i12 == 1) {
            return this.f26414b;
        }
        if (i12 != 2) {
            return 0.0f;
        }
        return this.f26415c;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    /* renamed from: b, reason: from getter */
    public final int getF26416d() {
        return this.f26416d;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final AbstractC20330x c() {
        return new C20324v(0.0f, 0.0f, 0.0f);
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void d() {
        this.f26413a = 0.0f;
        this.f26414b = 0.0f;
        this.f26415c = 0.0f;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void e(float f12, int i12) {
        if (i12 == 0) {
            this.f26413a = f12;
        } else if (i12 == 1) {
            this.f26414b = f12;
        } else {
            if (i12 != 2) {
                return;
            }
            this.f26415c = f12;
        }
    }

    public final boolean equals(@Y61.l Object obj) {
        if (obj instanceof C20324v) {
            C20324v c20324v = (C20324v) obj;
            if (c20324v.f26413a == this.f26413a && c20324v.f26414b == this.f26414b && c20324v.f26415c == this.f26415c) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Float.hashCode(this.f26415c) + androidx.appcompat.app.r.d(this.f26414b, Float.hashCode(this.f26413a) * 31, 31);
    }

    @Y61.k
    public final String toString() {
        return "AnimationVector3D: v1 = " + this.f26413a + ", v2 = " + this.f26414b + ", v3 = " + this.f26415c;
    }
}
