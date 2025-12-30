package androidx.camera.core;

import androidx.camera.core.O;
import androidx.camera.core.Y;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class Z implements O.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f23701a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f23702b;

    public /* synthetic */ Z(Object obj, int i12) {
        this.f23701a = i12;
        this.f23702b = obj;
    }

    @Override // androidx.camera.core.O.a
    public final void e(O o12) {
        androidx.camera.core.imagecapture.N n12;
        switch (this.f23701a) {
            case 0:
                Y y12 = ((Y.b) this.f23702b).f23700e.get();
                if (y12 != null) {
                    y12.f23695u.execute(new RunnableC20048a0(y12, 0));
                    return;
                }
                return;
            default:
                y0 y0Var = (y0) this.f23702b;
                synchronized (y0Var.f24589a) {
                    try {
                        int i12 = y0Var.f24590b - 1;
                        y0Var.f24590b = i12;
                        if (y0Var.f24591c && i12 == 0) {
                            y0Var.close();
                        }
                        n12 = y0Var.f24594f;
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                if (n12 != null) {
                    n12.e(o12);
                    return;
                }
                return;
        }
    }
}
