package androidx.camera.video;

import android.net.Uri;
import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.core.util.InterfaceC22791e;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class j0 implements InterfaceC22791e {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25283b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25284c;

    public /* synthetic */ j0(Object obj, int i12) {
        this.f25283b = i12;
        this.f25284c = obj;
    }

    @Override // androidx.core.util.InterfaceC22791e
    public final void accept(Object obj) {
        switch (this.f25283b) {
            case 0:
                K0.c cVar = (K0.c) obj;
                l0 l0Var = (l0) this.f25284c;
                cVar.b().hashCode();
                C20140q0.d(3, "VideoEncoderSession");
                Surface surfaceB = cVar.b();
                if (surfaceB != l0Var.f25294e) {
                    surfaceB.release();
                    break;
                } else {
                    l0Var.f25294e = null;
                    l0Var.f25302m.b(l0Var.f25293d);
                    l0Var.a();
                    break;
                }
            default:
                ((O) this.f25284c).f24733H = (Uri) obj;
                break;
        }
    }
}
