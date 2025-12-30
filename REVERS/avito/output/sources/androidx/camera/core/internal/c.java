package androidx.camera.core.internal;

import androidx.camera.core.InterfaceC20060g0;
import androidx.camera.core.impl.J0;
import androidx.camera.core.impl.r;
import androidx.camera.core.impl.utils.ExifData;
import j.N;
import j.X;

/* compiled from: CameraCaptureResultImageInfo.java */
@X
/* loaded from: classes.dex */
public final class c implements InterfaceC20060g0 {

    /* renamed from: a, reason: collision with root package name */
    public final r f24335a;

    public c(@N r rVar) {
        this.f24335a = rVar;
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final long a() {
        return this.f24335a.a();
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    @N
    public final J0 b() {
        return this.f24335a.b();
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final void c(@N ExifData.b bVar) {
        this.f24335a.c(bVar);
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final int d() {
        return 0;
    }
}
