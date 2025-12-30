package androidx.camera.view;

import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.concurrent.futures.b;
import androidx.core.util.InterfaceC22791e;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class F implements InterfaceC22791e {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25362b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25363c;

    public /* synthetic */ F(Object obj, int i12) {
        this.f25362b = i12;
        this.f25363c = obj;
    }

    @Override // androidx.core.util.InterfaceC22791e
    public final void accept(Object obj) {
        switch (this.f25362b) {
            case 0:
                C20140q0.d(3, "SurfaceViewImpl");
                n nVar = (n) this.f25363c;
                if (nVar != null) {
                    nVar.b();
                    break;
                }
                break;
            default:
                ((b.a) this.f25363c).b((K0.c) obj);
                break;
        }
    }
}
