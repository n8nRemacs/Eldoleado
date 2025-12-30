package androidx.camera.core;

import android.graphics.Matrix;

/* compiled from: AutoValue_ImmutableImageInfo.java */
/* renamed from: androidx.camera.core.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20063i extends AbstractC20132p0 {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.impl.J0 f23747a;

    /* renamed from: b, reason: collision with root package name */
    public final long f23748b;

    /* renamed from: c, reason: collision with root package name */
    public final int f23749c;

    /* renamed from: d, reason: collision with root package name */
    public final Matrix f23750d;

    public C20063i(androidx.camera.core.impl.J0 j02, long j12, int i12, Matrix matrix) {
        if (j02 == null) {
            throw new NullPointerException("Null tagBundle");
        }
        this.f23747a = j02;
        this.f23748b = j12;
        this.f23749c = i12;
        if (matrix == null) {
            throw new NullPointerException("Null sensorToBufferTransformMatrix");
        }
        this.f23750d = matrix;
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final long a() {
        return this.f23748b;
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    @j.N
    public final androidx.camera.core.impl.J0 b() {
        return this.f23747a;
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final int d() {
        return this.f23749c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractC20132p0)) {
            return false;
        }
        AbstractC20132p0 abstractC20132p0 = (AbstractC20132p0) obj;
        C20063i c20063i = (C20063i) abstractC20132p0;
        if (this.f23747a.equals(c20063i.f23747a)) {
            if (this.f23748b == c20063i.f23748b && this.f23749c == c20063i.f23749c && this.f23750d.equals(abstractC20132p0.f())) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.camera.core.AbstractC20132p0
    @j.N
    public final Matrix f() {
        return this.f23750d;
    }

    public final int hashCode() {
        int iHashCode = (this.f23747a.hashCode() ^ 1000003) * 1000003;
        long j12 = this.f23748b;
        return ((((iHashCode ^ ((int) ((j12 >>> 32) ^ j12))) * 1000003) ^ this.f23749c) * 1000003) ^ this.f23750d.hashCode();
    }

    public final String toString() {
        return "ImmutableImageInfo{tagBundle=" + this.f23747a + ", timestamp=" + this.f23748b + ", rotationDegrees=" + this.f23749c + ", sensorToBufferTransformMatrix=" + this.f23750d + "}";
    }
}
