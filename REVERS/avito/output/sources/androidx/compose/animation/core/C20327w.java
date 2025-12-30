package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: AnimationVectors.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/w;", "Landroidx/compose/animation/core/x;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.w, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20327w extends AbstractC20330x {

    /* renamed from: a, reason: collision with root package name */
    public float f26424a;

    /* renamed from: b, reason: collision with root package name */
    public float f26425b;

    /* renamed from: c, reason: collision with root package name */
    public float f26426c;

    /* renamed from: d, reason: collision with root package name */
    public float f26427d;

    /* renamed from: e, reason: collision with root package name */
    public final int f26428e;

    public C20327w(float f12, float f13, float f14, float f15) {
        super(null);
        this.f26424a = f12;
        this.f26425b = f13;
        this.f26426c = f14;
        this.f26427d = f15;
        this.f26428e = 4;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final float a(int i12) {
        if (i12 == 0) {
            return this.f26424a;
        }
        if (i12 == 1) {
            return this.f26425b;
        }
        if (i12 == 2) {
            return this.f26426c;
        }
        if (i12 != 3) {
            return 0.0f;
        }
        return this.f26427d;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    /* renamed from: b, reason: from getter */
    public final int getF26428e() {
        return this.f26428e;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final AbstractC20330x c() {
        return new C20327w(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void d() {
        this.f26424a = 0.0f;
        this.f26425b = 0.0f;
        this.f26426c = 0.0f;
        this.f26427d = 0.0f;
    }

    @Override // androidx.compose.animation.core.AbstractC20330x
    public final void e(float f12, int i12) {
        if (i12 == 0) {
            this.f26424a = f12;
            return;
        }
        if (i12 == 1) {
            this.f26425b = f12;
        } else if (i12 == 2) {
            this.f26426c = f12;
        } else {
            if (i12 != 3) {
                return;
            }
            this.f26427d = f12;
        }
    }

    public final boolean equals(@Y61.l Object obj) {
        if (obj instanceof C20327w) {
            C20327w c20327w = (C20327w) obj;
            if (c20327w.f26424a == this.f26424a && c20327w.f26425b == this.f26425b && c20327w.f26426c == this.f26426c && c20327w.f26427d == this.f26427d) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Float.hashCode(this.f26427d) + androidx.appcompat.app.r.d(this.f26426c, androidx.appcompat.app.r.d(this.f26425b, Float.hashCode(this.f26424a) * 31, 31), 31);
    }

    @Y61.k
    public final String toString() {
        return "AnimationVector4D: v1 = " + this.f26424a + ", v2 = " + this.f26425b + ", v3 = " + this.f26426c + ", v4 = " + this.f26427d;
    }
}
