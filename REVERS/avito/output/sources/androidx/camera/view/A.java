package androidx.camera.view;

import androidx.camera.core.C20056e0;
import androidx.camera.core.T;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.camera.video.O;
import androidx.camera.video.b0;
import androidx.camera.view.z;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class A implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25344b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f25345c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f25346d;

    public /* synthetic */ A(int i12, int i13, Object obj) {
        this.f25344b = i13;
        this.f25346d = obj;
        this.f25345c = i12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f25344b) {
            case 0:
                z.c cVar = (z.c) this.f25346d;
                if (cVar.f25489c.get()) {
                    AbstractC20193e abstractC20193e = (AbstractC20193e) cVar.f25487a.f25445b;
                    T t12 = abstractC20193e.f25419e;
                    int i12 = this.f25345c;
                    if (t12.A(i12)) {
                        t12.I();
                    }
                    C20056e0 c20056e0 = abstractC20193e.f25418d;
                    int iD2 = ((InterfaceC20088d0) c20056e0.f23620f).D(0);
                    if (c20056e0.A(i12) && c20056e0.f23730r != null) {
                        c20056e0.f23730r = ImageUtil.a(Math.abs(androidx.camera.core.impl.utils.e.b(i12) - androidx.camera.core.impl.utils.e.b(iD2)), c20056e0.f23730r);
                    }
                    b0<O> b0Var = abstractC20193e.f25420f;
                    if (b0Var.A(i12)) {
                        b0Var.L();
                        break;
                    }
                }
                break;
            default:
                ((C20197i) this.f25346d).f25416b = this.f25345c;
                break;
        }
    }
}
