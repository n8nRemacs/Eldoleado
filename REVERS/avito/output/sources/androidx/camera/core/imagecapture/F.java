package androidx.camera.core.imagecapture;

import androidx.camera.core.InterfaceC20060g0;
import androidx.camera.core.impl.J0;
import androidx.camera.core.impl.utils.ExifData;

/* compiled from: RgbaImageProxy.java */
/* loaded from: classes.dex */
class F implements InterfaceC20060g0 {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ long f23783a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23784b;

    public F(long j12, int i12) {
        this.f23783a = j12;
        this.f23784b = i12;
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final long a() {
        return this.f23783a;
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    @j.N
    public final J0 b() {
        throw new UnsupportedOperationException("Custom ImageProxy does not contain TagBundle");
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final void c(@j.N ExifData.b bVar) {
        throw new UnsupportedOperationException("Custom ImageProxy does not contain Exif data.");
    }

    @Override // androidx.camera.core.InterfaceC20060g0
    public final int d() {
        return this.f23784b;
    }
}
