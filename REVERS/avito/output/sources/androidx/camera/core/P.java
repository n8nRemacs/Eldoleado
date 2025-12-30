package androidx.camera.core;

import androidx.camera.core.C20056e0;
import androidx.camera.core.T;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.v0;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class P implements SessionConfig.c {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f23642a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f23643b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ androidx.camera.core.impl.G0 f23644c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ N0 f23645d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f23646e;

    public /* synthetic */ P(N0 n02, String str, Object obj, androidx.camera.core.impl.G0 g02, int i12) {
        this.f23642a = i12;
        this.f23645d = n02;
        this.f23643b = str;
        this.f23646e = obj;
        this.f23644c = g02;
    }

    @Override // androidx.camera.core.impl.SessionConfig.c
    public final void onError() {
        androidx.camera.core.impl.G0 g02 = this.f23644c;
        Object obj = this.f23646e;
        String str = this.f23643b;
        N0 n02 = this.f23645d;
        switch (this.f23642a) {
            case 0:
                T.d dVar = T.f23653s;
                T t12 = (T) n02;
                t12.getClass();
                androidx.camera.core.impl.utils.t.a();
                C20094g0 c20094g0 = t12.f23658r;
                if (c20094g0 != null) {
                    c20094g0.a();
                    t12.f23658r = null;
                }
                t12.f23654n.d();
                if (t12.k(str)) {
                    t12.D(t12.F(str, (androidx.camera.core.impl.Y) obj, g02).k());
                    t12.p();
                    break;
                }
                break;
            case 1:
                C20056e0.d dVar2 = C20056e0.f23725w;
                C20056e0 c20056e0 = (C20056e0) n02;
                if (!c20056e0.k(str)) {
                    c20056e0.E(false);
                    break;
                } else {
                    c20056e0.f23733u.c();
                    c20056e0.E(true);
                    SessionConfig.b bVarF = c20056e0.F(str, (androidx.camera.core.impl.Z) obj, g02);
                    c20056e0.f23731s = bVarF;
                    c20056e0.D(bVarF.k());
                    c20056e0.p();
                    androidx.camera.core.imagecapture.N n12 = c20056e0.f23733u;
                    n12.getClass();
                    androidx.camera.core.impl.utils.t.a();
                    n12.f23806f = false;
                    n12.b();
                    break;
                }
            default:
                v0.b bVar = v0.f24566t;
                v0 v0Var = (v0) n02;
                if (v0Var.k(str)) {
                    v0Var.D(v0Var.F(str, (androidx.camera.core.impl.u0) obj, g02).k());
                    v0Var.p();
                    break;
                }
                break;
        }
    }
}
