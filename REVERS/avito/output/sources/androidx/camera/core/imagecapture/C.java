package androidx.camera.core.imagecapture;

import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.InterfaceC20092f0;
import androidx.concurrent.futures.b;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class C implements b.c, InterfaceC20092f0.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23771b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23772c;

    public /* synthetic */ C(Object obj, int i12) {
        this.f23771b = i12;
        this.f23772c = obj;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0.a
    public void a(InterfaceC20092f0 interfaceC20092f0) {
        boolean z12;
        C20079o c20079o = (C20079o) this.f23772c;
        boolean z13 = false;
        try {
            InterfaceC20120j0 interfaceC20120j0B = interfaceC20092f0.b();
            if (interfaceC20120j0B != null) {
                c20079o.b(interfaceC20120j0B);
                return;
            }
            ImageCaptureException imageCaptureException = new ImageCaptureException("Failed to acquire latest image", null);
            androidx.camera.core.impl.utils.t.a();
            B b12 = c20079o.f23846b;
            if (b12 != null) {
                D d12 = b12.f23767f;
                androidx.camera.core.impl.utils.t.a();
                if (d12.f23779g) {
                    return;
                }
                O o12 = d12.f23773a;
                androidx.camera.core.impl.utils.t.a();
                int i12 = o12.f23807a;
                if (i12 > 0) {
                    o12.f23807a = i12 - 1;
                    z12 = true;
                } else {
                    z12 = false;
                }
                if (!z12) {
                    androidx.camera.core.impl.utils.t.a();
                    o12.a().execute(new z(5, o12, imageCaptureException));
                }
                d12.a();
                d12.f23777e.d(imageCaptureException);
                if (z12) {
                    d12.f23774b.d(o12);
                }
            }
        } catch (IllegalStateException e12) {
            ImageCaptureException imageCaptureException2 = new ImageCaptureException("Failed to acquire latest image", e12);
            androidx.camera.core.impl.utils.t.a();
            B b13 = c20079o.f23846b;
            if (b13 != null) {
                D d13 = b13.f23767f;
                androidx.camera.core.impl.utils.t.a();
                if (d13.f23779g) {
                    return;
                }
                O o13 = d13.f23773a;
                androidx.camera.core.impl.utils.t.a();
                int i13 = o13.f23807a;
                if (i13 > 0) {
                    o13.f23807a = i13 - 1;
                    z13 = true;
                }
                if (!z13) {
                    androidx.camera.core.impl.utils.t.a();
                    o13.a().execute(new z(5, o13, imageCaptureException2));
                }
                d13.a();
                d13.f23777e.d(imageCaptureException2);
                if (z13) {
                    d13.f23774b.d(o13);
                }
            }
        }
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        switch (this.f23771b) {
            case 0:
                ((D) this.f23772c).f23777e = aVar;
                return "CaptureCompleteFuture";
            default:
                ((D) this.f23772c).f23778f = aVar;
                return "RequestCompleteFuture";
        }
    }
}
