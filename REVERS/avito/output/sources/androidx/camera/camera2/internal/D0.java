package androidx.camera.camera2.internal;

import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.r;
import java.util.Iterator;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class D0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f22971b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ androidx.camera.core.impl.H f22972c;

    public /* synthetic */ D0(androidx.camera.core.impl.H h12, int i12) {
        this.f22971b = i12;
        this.f22972c = h12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f22971b) {
            case 0:
                Iterator<AbstractC20109p> it = this.f22972c.f23972e.iterator();
                while (it.hasNext()) {
                    it.next().b(new r.a());
                }
                break;
            case 1:
                Iterator<AbstractC20109p> it2 = this.f22972c.f23972e.iterator();
                while (it2.hasNext()) {
                    it2.next().c(new CameraCaptureFailure());
                }
                break;
            case 2:
                Iterator<AbstractC20109p> it3 = this.f22972c.f23972e.iterator();
                while (it3.hasNext()) {
                    it3.next().b(new r.a());
                }
                break;
            default:
                Iterator<AbstractC20109p> it4 = this.f22972c.f23972e.iterator();
                while (it4.hasNext()) {
                    it4.next().c(new CameraCaptureFailure());
                }
                break;
        }
    }
}
