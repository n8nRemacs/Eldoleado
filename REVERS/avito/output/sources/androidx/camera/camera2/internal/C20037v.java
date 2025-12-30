package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import androidx.camera.camera2.internal.compat.C19985a;

/* compiled from: Camera2CameraImpl.java */
/* renamed from: androidx.camera.camera2.internal.v, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20037v implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20007f0 f23461a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C20039w f23462b;

    public C20037v(C20039w c20039w, InterfaceC20007f0 interfaceC20007f0) {
        this.f23462b = c20039w;
        this.f23461a = interfaceC20007f0;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P Void r22) {
        CameraDevice cameraDevice;
        this.f23462b.f23479o.remove(this.f23461a);
        int iOrdinal = this.f23462b.f23470f.ordinal();
        if (iOrdinal != 5) {
            if (iOrdinal != 6) {
                if (iOrdinal != 7) {
                    return;
                }
            } else if (this.f23462b.f23477m == 0) {
                return;
            }
        }
        if (!this.f23462b.x() || (cameraDevice = this.f23462b.f23476l) == null) {
            return;
        }
        C19985a.C1529a.a(cameraDevice);
        this.f23462b.f23476l = null;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
    }
}
