package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.J0;

/* compiled from: AutoValue_SupportedSurfaceCombination_FeatureSettings.java */
/* renamed from: androidx.camera.camera2.internal.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20004e extends J0.b {

    /* renamed from: a, reason: collision with root package name */
    public final int f23323a;

    /* renamed from: b, reason: collision with root package name */
    public final int f23324b;

    public C20004e(int i12, int i13) {
        this.f23323a = i12;
        this.f23324b = i13;
    }

    @Override // androidx.camera.camera2.internal.J0.b
    public final int a() {
        return this.f23323a;
    }

    @Override // androidx.camera.camera2.internal.J0.b
    public final int b() {
        return this.f23324b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof J0.b)) {
            return false;
        }
        J0.b bVar = (J0.b) obj;
        return this.f23323a == bVar.a() && this.f23324b == bVar.b();
    }

    public final int hashCode() {
        return ((this.f23323a ^ 1000003) * 1000003) ^ this.f23324b;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("FeatureSettings{cameraMode=");
        sb2.append(this.f23323a);
        sb2.append(", requiredMaxBitDepth=");
        return AK.c.i(this.f23324b, "}", sb2);
    }
}
