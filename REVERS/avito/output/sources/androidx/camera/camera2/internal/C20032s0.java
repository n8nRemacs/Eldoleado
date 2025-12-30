package androidx.camera.camera2.internal;

import androidx.camera.core.CameraControl;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.concurrent.futures.b;

/* compiled from: FocusMeteringControl.java */
/* renamed from: androidx.camera.camera2.internal.s0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20032s0 extends AbstractC20109p {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b.a f23433a;

    public C20032s0(b.a aVar) {
        this.f23433a = aVar;
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void a() {
        b.a aVar = this.f23433a;
        if (aVar != null) {
            aVar.d(new CameraControl.OperationCanceledException("Camera is closed"));
        }
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void b(@j.N androidx.camera.core.impl.r rVar) {
        b.a aVar = this.f23433a;
        if (aVar != null) {
            aVar.b(null);
        }
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void c(@j.N CameraCaptureFailure cameraCaptureFailure) {
        b.a aVar = this.f23433a;
        if (aVar != null) {
            aVar.d(new CameraControlInternal.CameraControlException());
        }
    }
}
