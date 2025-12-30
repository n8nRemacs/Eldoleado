package androidx.camera.core.imagecapture;

import androidx.camera.core.C20056e0;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.imagecapture.A;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class z implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23866b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23867c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23868d;

    public /* synthetic */ z(int i12, Object obj, Object obj2) {
        this.f23866b = i12;
        this.f23867c = obj;
        this.f23868d = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23866b) {
            case 0:
                A a12 = (A) this.f23867c;
                A.b bVar = (A.b) this.f23868d;
                B b12 = bVar.b();
                try {
                    if (bVar.b().f23762a == null) {
                        androidx.camera.core.impl.utils.executor.c.d().execute(new z(1, b12, a12.a(bVar)));
                    } else {
                        androidx.camera.core.impl.utils.executor.c.d().execute(new z(2, b12, a12.b(bVar)));
                    }
                    return;
                } catch (ImageCaptureException e12) {
                    androidx.camera.core.impl.utils.executor.c.d().execute(new z(3, b12, e12));
                    return;
                } catch (OutOfMemoryError e13) {
                    androidx.camera.core.impl.utils.executor.c.d().execute(new z(3, b12, new ImageCaptureException("Processing failed due to low memory.", e13)));
                    return;
                } catch (RuntimeException e14) {
                    androidx.camera.core.impl.utils.executor.c.d().execute(new z(3, b12, new ImageCaptureException("Processing failed.", e14)));
                    return;
                }
            case 1:
                D d12 = ((B) this.f23867c).f23767f;
                androidx.camera.core.impl.utils.t.a();
                if (d12.f23779g) {
                    return;
                }
                androidx.core.util.z.g("onImageCaptured() must be called before onFinalResult()", d12.f23775c.isDone());
                d12.a();
                O o12 = d12.f23773a;
                o12.a().execute(new z(6, o12, (InterfaceC20120j0) this.f23868d));
                return;
            case 2:
                D d13 = ((B) this.f23867c).f23767f;
                androidx.camera.core.impl.utils.t.a();
                if (d13.f23779g) {
                    return;
                }
                androidx.core.util.z.g("onImageCaptured() must be called before onFinalResult()", d13.f23775c.isDone());
                d13.a();
                O o13 = d13.f23773a;
                o13.a().execute(new z(7, o13, (C20056e0.m) this.f23868d));
                return;
            case 3:
                D d14 = ((B) this.f23867c).f23767f;
                ImageCaptureException imageCaptureException = (ImageCaptureException) this.f23868d;
                androidx.camera.core.impl.utils.t.a();
                if (d14.f23779g) {
                    return;
                }
                androidx.core.util.z.g("onImageCaptured() must be called before onFinalResult()", d14.f23775c.isDone());
                d14.a();
                androidx.camera.core.impl.utils.t.a();
                O o14 = d14.f23773a;
                o14.a().execute(new z(5, o14, imageCaptureException));
                return;
            case 4:
                ((N) this.f23867c).f23805e.remove((D) this.f23868d);
                return;
            case 5:
                O o15 = (O) this.f23867c;
                boolean z12 = o15.d() != null;
                boolean z13 = o15.f() != null;
                if (z12 && !z13) {
                    Objects.requireNonNull(o15.d());
                    return;
                } else {
                    if (!z13 || z12) {
                        throw new IllegalStateException("One and only one callback is allowed.");
                    }
                    C20056e0.k kVarF = o15.f();
                    Objects.requireNonNull(kVarF);
                    kVarF.onError((ImageCaptureException) this.f23868d);
                    return;
                }
            case 6:
                C20056e0.j jVarD = ((O) this.f23867c).d();
                Objects.requireNonNull(jVarD);
                InterfaceC20120j0 interfaceC20120j0 = (InterfaceC20120j0) this.f23868d;
                Objects.requireNonNull(interfaceC20120j0);
                jVarD.a(interfaceC20120j0);
                return;
            default:
                C20056e0.k kVarF2 = ((O) this.f23867c).f();
                Objects.requireNonNull(kVarF2);
                C20056e0.m mVar = (C20056e0.m) this.f23868d;
                Objects.requireNonNull(mVar);
                kVarF2.onImageSaved(mVar);
                return;
        }
    }
}
