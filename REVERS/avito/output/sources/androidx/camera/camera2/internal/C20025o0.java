package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import android.util.Size;
import androidx.camera.camera2.internal.C0;
import androidx.camera.core.C20056e0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.SessionConfig;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.o0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class C20025o0 implements b.c, androidx.camera.core.impl.utils.futures.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23385b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23386c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23387d;

    public /* synthetic */ C20025o0(int i12, Object obj, Object obj2) {
        this.f23385b = i12;
        this.f23386c = obj;
        this.f23387d = obj2;
    }

    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
    public com.google.common.util.concurrent.D0 apply(Object obj) {
        Object obj2 = this.f23387d;
        Object obj3 = this.f23386c;
        List list = (List) obj;
        switch (this.f23385b) {
            case 1:
                int i12 = C0.f22912m;
                C0 c02 = (C0) obj3;
                c02.getClass();
                C20140q0.d(3, "ProcessingCaptureSession");
                if (c02.f22921i == C0.c.f22929e) {
                    return androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("SessionProcessorCaptureSession is closed."));
                }
                SessionConfig sessionConfig = (SessionConfig) obj2;
                if (list.contains(null)) {
                    return androidx.camera.core.impl.utils.futures.f.e(new DeferrableSurface.SurfaceClosedException(sessionConfig.b().get(list.indexOf(null)), "Surface closed"));
                }
                androidx.camera.core.impl.t0 t0VarA = null;
                androidx.camera.core.impl.t0 t0VarA2 = null;
                androidx.camera.core.impl.t0 t0VarA3 = null;
                for (int i13 = 0; i13 < sessionConfig.b().size(); i13++) {
                    DeferrableSurface deferrableSurface = sessionConfig.b().get(i13);
                    boolean zEquals = Objects.equals(deferrableSurface.f23938j, androidx.camera.core.v0.class);
                    int i14 = deferrableSurface.f23937i;
                    Size size = deferrableSurface.f23936h;
                    if (zEquals) {
                        t0VarA = androidx.camera.core.impl.t0.a(deferrableSurface.c().get(), new Size(size.getWidth(), size.getHeight()), i14);
                    } else if (Objects.equals(deferrableSurface.f23938j, C20056e0.class)) {
                        t0VarA2 = androidx.camera.core.impl.t0.a(deferrableSurface.c().get(), new Size(size.getWidth(), size.getHeight()), i14);
                    } else if (Objects.equals(deferrableSurface.f23938j, androidx.camera.core.T.class)) {
                        t0VarA3 = androidx.camera.core.impl.t0.a(deferrableSurface.c().get(), new Size(size.getWidth(), size.getHeight()), i14);
                    }
                }
                c02.f22921i = C0.c.f22927c;
                try {
                    androidx.camera.core.impl.O.b(c02.f22918f);
                    C20140q0.d(5, "ProcessingCaptureSession");
                    try {
                        c02.f22913a.f(c02.f22914b, t0VarA, t0VarA2, t0VarA3);
                        throw null;
                    } catch (Throwable th2) {
                        androidx.camera.core.impl.O.a(c02.f22918f);
                        throw th2;
                    }
                } catch (DeferrableSurface.SurfaceClosedException e12) {
                    return androidx.camera.core.impl.utils.futures.f.e(e12);
                }
            default:
                ((N0) obj3).toString();
                C20140q0.d(3, "SyncCaptureSessionBase");
                if (list.contains(null)) {
                    return androidx.camera.core.impl.utils.futures.f.e(new DeferrableSurface.SurfaceClosedException((DeferrableSurface) ((ArrayList) obj2).get(list.indexOf(null)), "Surface closed"));
                }
                return list.isEmpty() ? androidx.camera.core.impl.utils.futures.f.e(new IllegalArgumentException("Unable to open capture session without surfaces")) : androidx.camera.core.impl.utils.futures.f.g(list);
        }
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        C20034t0 c20034t0 = (C20034t0) this.f23386c;
        c20034t0.getClass();
        c20034t0.f23441b.execute(new RunnableC20020m(c20034t0, aVar, (androidx.camera.core.M) this.f23387d, 1));
        return "startFocusAndMetering";
    }

    public /* synthetic */ C20025o0(C0 c02, SessionConfig sessionConfig, CameraDevice cameraDevice, Q0 q02) {
        this.f23385b = 1;
        this.f23386c = c02;
        this.f23387d = sessionConfig;
    }
}
