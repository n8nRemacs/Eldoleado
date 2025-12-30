package androidx.camera.core.impl;

import androidx.camera.core.impl.SurfaceConfig;

/* compiled from: AutoValue_SurfaceConfig.java */
/* renamed from: androidx.camera.core.impl.n, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20107n extends SurfaceConfig {

    /* renamed from: a, reason: collision with root package name */
    public final SurfaceConfig.ConfigType f24160a;

    /* renamed from: b, reason: collision with root package name */
    public final SurfaceConfig.ConfigSize f24161b;

    /* renamed from: c, reason: collision with root package name */
    public final long f24162c;

    public C20107n(SurfaceConfig.ConfigType configType, SurfaceConfig.ConfigSize configSize, long j12) {
        this.f24160a = configType;
        this.f24161b = configSize;
        this.f24162c = j12;
    }

    @Override // androidx.camera.core.impl.SurfaceConfig
    @j.N
    public final SurfaceConfig.ConfigSize c() {
        return this.f24161b;
    }

    @Override // androidx.camera.core.impl.SurfaceConfig
    @j.N
    public final SurfaceConfig.ConfigType d() {
        return this.f24160a;
    }

    @Override // androidx.camera.core.impl.SurfaceConfig
    public final long e() {
        return this.f24162c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SurfaceConfig)) {
            return false;
        }
        SurfaceConfig surfaceConfig = (SurfaceConfig) obj;
        return this.f24160a.equals(surfaceConfig.d()) && this.f24161b.equals(surfaceConfig.c()) && this.f24162c == surfaceConfig.e();
    }

    public final int hashCode() {
        int iHashCode = (((this.f24160a.hashCode() ^ 1000003) * 1000003) ^ this.f24161b.hashCode()) * 1000003;
        long j12 = this.f24162c;
        return iHashCode ^ ((int) ((j12 >>> 32) ^ j12));
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("SurfaceConfig{configType=");
        sb2.append(this.f24160a);
        sb2.append(", configSize=");
        sb2.append(this.f24161b);
        sb2.append(", streamUseCase=");
        return AK.c.j(this.f24162c, "}", sb2);
    }
}
