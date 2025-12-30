package androidx.camera.core.imagecapture;

import androidx.camera.core.ImageCaptureException;

/* compiled from: TakePictureManager.java */
/* loaded from: classes.dex */
class M implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C20076l f23799a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ N f23800b;

    public M(N n12, C20076l c20076l) {
        this.f23800b = n12;
        this.f23799a = c20076l;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        if (this.f23799a.f23840a.f23779g) {
            return;
        }
        boolean z12 = th2 instanceof ImageCaptureException;
        N n12 = this.f23800b;
        if (z12) {
            r rVar = n12.f23803c;
            rVar.getClass();
            androidx.camera.core.impl.utils.t.a();
            rVar.f23861f.f23816i.accept((ImageCaptureException) th2);
        } else {
            r rVar2 = n12.f23803c;
            ImageCaptureException imageCaptureException = new ImageCaptureException("Failed to submit capture request", th2);
            rVar2.getClass();
            androidx.camera.core.impl.utils.t.a();
            rVar2.f23861f.f23816i.accept(imageCaptureException);
        }
        n12.f23802b.b();
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P Void r12) {
        this.f23800b.f23802b.b();
    }
}
