package androidx.camera.core.internal;

import androidx.camera.camera2.internal.G;

/* compiled from: AutoValue_ImmutableZoomState.java */
/* loaded from: classes.dex */
final class a extends e {

    /* renamed from: a, reason: collision with root package name */
    public final float f24331a;

    /* renamed from: b, reason: collision with root package name */
    public final float f24332b;

    /* renamed from: c, reason: collision with root package name */
    public final float f24333c;

    /* renamed from: d, reason: collision with root package name */
    public final float f24334d;

    public a(float f12, float f13, float f14, float f15) {
        this.f24331a = f12;
        this.f24332b = f13;
        this.f24333c = f14;
        this.f24334d = f15;
    }

    @Override // androidx.camera.core.Q0
    public final float a() {
        return this.f24334d;
    }

    @Override // androidx.camera.core.Q0
    public final float b() {
        return this.f24331a;
    }

    @Override // androidx.camera.core.Q0
    public final float c() {
        return this.f24332b;
    }

    @Override // androidx.camera.core.Q0
    public final float d() {
        return this.f24333c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        if (Float.floatToIntBits(this.f24331a) == Float.floatToIntBits(((a) eVar).f24331a)) {
            a aVar = (a) eVar;
            if (Float.floatToIntBits(this.f24332b) == Float.floatToIntBits(aVar.f24332b) && Float.floatToIntBits(this.f24333c) == Float.floatToIntBits(aVar.f24333c) && Float.floatToIntBits(this.f24334d) == Float.floatToIntBits(aVar.f24334d)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((((Float.floatToIntBits(this.f24331a) ^ 1000003) * 1000003) ^ Float.floatToIntBits(this.f24332b)) * 1000003) ^ Float.floatToIntBits(this.f24333c)) * 1000003) ^ Float.floatToIntBits(this.f24334d);
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ImmutableZoomState{zoomRatio=");
        sb2.append(this.f24331a);
        sb2.append(", maxZoomRatio=");
        sb2.append(this.f24332b);
        sb2.append(", minZoomRatio=");
        sb2.append(this.f24333c);
        sb2.append(", linearZoom=");
        return G.d(this.f24334d, "}", sb2);
    }
}
