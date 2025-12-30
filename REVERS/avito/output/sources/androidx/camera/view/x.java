package androidx.camera.view;

import androidx.camera.core.C20144v;
import androidx.camera.core.InterfaceC20127n;
import androidx.camera.core.N0;
import androidx.camera.core.O0;
import com.avito.android.photo_picker.camera_mvi.CameraFragment;
import j.N;
import j.X;

/* compiled from: ProcessCameraProviderWrapperImpl.java */
@X
/* loaded from: classes.dex */
class x implements w {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.lifecycle.i f25480a;

    public x(androidx.camera.lifecycle.i iVar) {
        this.f25480a = iVar;
    }

    @Override // androidx.camera.view.w
    @N
    public final InterfaceC20127n a(@N CameraFragment cameraFragment, @N C20144v c20144v, @N O0 o02) {
        return this.f25480a.a(cameraFragment, c20144v, o02);
    }

    @Override // androidx.camera.view.w
    public final void b(@N N0... n0Arr) {
        this.f25480a.d(n0Arr);
    }

    @Override // androidx.camera.view.w
    public final boolean c(@N C20144v c20144v) {
        androidx.camera.lifecycle.i iVar = this.f25480a;
        iVar.getClass();
        try {
            c20144v.d(iVar.f24679e.f24598a.a());
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }
}
