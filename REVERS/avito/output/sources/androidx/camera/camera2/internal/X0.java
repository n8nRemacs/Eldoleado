package androidx.camera.camera2.internal;

import androidx.concurrent.futures.b;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class X0 implements b.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23085b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Z0 f23086c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ androidx.camera.core.Q0 f23087d;

    public /* synthetic */ X0(Z0 z02, androidx.camera.core.Q0 q02, int i12) {
        this.f23085b = i12;
        this.f23086c = z02;
        this.f23087d = q02;
    }

    @Override // androidx.concurrent.futures.b.c
    public final Object d(final b.a aVar) {
        switch (this.f23085b) {
            case 0:
                final Z0 z02 = this.f23086c;
                z02.getClass();
                final androidx.camera.core.Q0 q02 = this.f23087d;
                final int i12 = 0;
                z02.f23095b.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Y0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i12) {
                            case 0:
                                z02.b(aVar, q02);
                                break;
                            default:
                                z02.b(aVar, q02);
                                break;
                        }
                    }
                });
                return "setZoomRatio";
            default:
                final Z0 z03 = this.f23086c;
                z03.getClass();
                final androidx.camera.core.Q0 q03 = this.f23087d;
                final int i13 = 1;
                z03.f23095b.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Y0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i13) {
                            case 0:
                                z03.b(aVar, q03);
                                break;
                            default:
                                z03.b(aVar, q03);
                                break;
                        }
                    }
                });
                return "setLinearZoom";
        }
    }
}
