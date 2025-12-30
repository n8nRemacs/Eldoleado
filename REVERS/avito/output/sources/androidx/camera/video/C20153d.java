package androidx.camera.video;

import androidx.annotation.RestrictTo;

/* compiled from: AutoValue_AudioStats.java */
/* renamed from: androidx.camera.video.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20153d extends AbstractC20151b {

    /* renamed from: b, reason: collision with root package name */
    public final int f24896b;

    /* renamed from: c, reason: collision with root package name */
    public final double f24897c;

    /* renamed from: d, reason: collision with root package name */
    public final Throwable f24898d;

    public C20153d(int i12, double d12, @j.P Throwable th2) {
        this.f24896b = i12;
        this.f24897c = d12;
        this.f24898d = th2;
    }

    @Override // androidx.camera.video.AbstractC20151b
    @RestrictTo
    public final double a() {
        return this.f24897c;
    }

    @Override // androidx.camera.video.AbstractC20151b
    public final int b() {
        return this.f24896b;
    }

    @Override // androidx.camera.video.AbstractC20151b
    @j.P
    public final Throwable c() {
        return this.f24898d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractC20151b)) {
            return false;
        }
        AbstractC20151b abstractC20151b = (AbstractC20151b) obj;
        if (this.f24896b == abstractC20151b.b() && Double.doubleToLongBits(this.f24897c) == Double.doubleToLongBits(abstractC20151b.a())) {
            Throwable th2 = this.f24898d;
            if (th2 == null) {
                if (abstractC20151b.c() == null) {
                    return true;
                }
            } else if (th2.equals(abstractC20151b.c())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i12 = (this.f24896b ^ 1000003) * 1000003;
        double d12 = this.f24897c;
        int iDoubleToLongBits = (i12 ^ ((int) (Double.doubleToLongBits(d12) ^ (Double.doubleToLongBits(d12) >>> 32)))) * 1000003;
        Throwable th2 = this.f24898d;
        return iDoubleToLongBits ^ (th2 == null ? 0 : th2.hashCode());
    }

    public final String toString() {
        return "AudioStats{audioState=" + this.f24896b + ", audioAmplitudeInternal=" + this.f24897c + ", errorCause=" + this.f24898d + "}";
    }
}
