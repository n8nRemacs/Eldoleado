package androidx.camera.camera2.internal;

import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.concurrent.futures.b;

/* compiled from: Camera2CapturePipeline.java */
/* loaded from: classes.dex */
class H extends AbstractC20109p {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b.a f22981a;

    public H(b.a aVar) {
        this.f22981a = aVar;
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void a() {
        this.f22981a.d(new ImageCaptureException("Capture request is cancelled because camera is closed", null));
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void b(@j.N androidx.camera.core.impl.r rVar) {
        this.f22981a.b(null);
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void c(@j.N CameraCaptureFailure cameraCaptureFailure) {
        this.f22981a.d(new ImageCaptureException("Capture request failed with reason " + CameraCaptureFailure.Reason.f23875b, null));
    }
}
