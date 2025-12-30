package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.C20009g0;
import androidx.camera.camera2.internal.C20039w;
import androidx.camera.camera2.internal.D;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.SessionConfig;
import java.util.LinkedHashSet;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.x, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20041x implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23521b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23522c;

    public /* synthetic */ RunnableC20041x(SessionConfig.c cVar, SessionConfig sessionConfig) {
        this.f23521b = 7;
        this.f23522c = cVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23521b) {
            case 0:
                C20039w.g.b bVar = (C20039w.g.b) this.f23522c;
                if (bVar.f23517c) {
                    return;
                }
                androidx.core.util.z.g(null, C20039w.this.f23470f == C20039w.f.f23504h);
                if (C20039w.g.this.c()) {
                    C20039w.this.I(true);
                    return;
                } else {
                    C20039w.this.J(true);
                    return;
                }
            case 1:
                ((D.c.a) this.f23522c).c();
                return;
            case 2:
                int i12 = C20009g0.a.f23333b;
                for (K0 k02 : (LinkedHashSet) this.f23522c) {
                    k02.c().n(k02);
                }
                return;
            case 3:
                int i13 = C0.f22912m;
                C0 c02 = (C0) this.f23522c;
                c02.getClass();
                C20140q0.d(3, "ProcessingCaptureSession");
                c02.f22913a.b();
                throw null;
            case 4:
                N0 n02 = (N0) this.f23522c;
                n02.r(n02);
                return;
            case 5:
                P0.v((P0) this.f23522c);
                return;
            case 6:
                ((AbstractC20109p) this.f23522c).a();
                return;
            case 7:
                ((SessionConfig.c) this.f23522c).onError();
                return;
            default:
                ((androidx.camera.core.y0) this.f23522c).e();
                return;
        }
    }

    public /* synthetic */ RunnableC20041x(Object obj, int i12) {
        this.f23521b = i12;
        this.f23522c = obj;
    }
}
