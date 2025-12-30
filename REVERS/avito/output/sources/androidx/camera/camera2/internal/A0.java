package androidx.camera.camera2.internal;

import android.graphics.SurfaceTexture;
import android.os.Build;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/* compiled from: MeteringRepeatingSession.java */
@j.X
/* loaded from: classes.dex */
class A0 {

    /* renamed from: a, reason: collision with root package name */
    public C20094g0 f22900a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public SessionConfig f22901b;

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public final b f22902c;

    /* renamed from: d, reason: collision with root package name */
    @j.N
    public final Size f22903d;

    /* renamed from: e, reason: collision with root package name */
    @j.P
    public final r f22904e;

    /* compiled from: MeteringRepeatingSession.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Surface f22905a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ SurfaceTexture f22906b;

        public a(Surface surface, SurfaceTexture surfaceTexture) {
            this.f22905a = surface;
            this.f22906b = surfaceTexture;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            throw new IllegalStateException("Future should never fail. Did it get completed by GC?", th2);
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(@j.P Void r12) {
            this.f22905a.release();
            this.f22906b.release();
        }
    }

    /* compiled from: MeteringRepeatingSession.java */
    public static class b implements androidx.camera.core.impl.L0<androidx.camera.core.N0> {

        /* renamed from: F, reason: collision with root package name */
        @j.N
        public final androidx.camera.core.impl.n0 f22907F;

        public b() {
            androidx.camera.core.impl.n0 n0VarR = androidx.camera.core.impl.n0.R();
            n0VarR.K(androidx.camera.core.impl.L0.f23999s, new O());
            this.f22907F = n0VarR;
        }

        @Override // androidx.camera.core.impl.x0
        @j.N
        public final Config getConfig() {
            return this.f22907F;
        }

        @Override // androidx.camera.core.impl.L0
        @j.N
        public final UseCaseConfigFactory.CaptureType z() {
            return UseCaseConfigFactory.CaptureType.f24059g;
        }
    }

    /* compiled from: MeteringRepeatingSession.java */
    public interface c {
    }

    public A0(@j.N androidx.camera.camera2.internal.compat.n nVar, @j.N C20013i0 c20013i0, @j.P r rVar) {
        Size size;
        androidx.camera.camera2.internal.compat.workaround.r rVar2 = new androidx.camera.camera2.internal.compat.workaround.r();
        this.f22902c = new b();
        this.f22904e = rVar;
        Size[] sizeArrA = nVar.b().a(34);
        if (sizeArrA == null) {
            C20140q0.b("MeteringRepeating");
            size = new Size(0, 0);
        } else {
            if (rVar2.f23268a != null && "Huawei".equalsIgnoreCase(Build.BRAND) && "mha-l29".equalsIgnoreCase(Build.MODEL)) {
                ArrayList arrayList = new ArrayList();
                for (Size size2 : sizeArrA) {
                    if (androidx.camera.camera2.internal.compat.workaround.r.f23267c.compare(size2, androidx.camera.camera2.internal.compat.workaround.r.f23266b) >= 0) {
                        arrayList.add(size2);
                    }
                }
                sizeArrA = (Size[]) arrayList.toArray(new Size[0]);
            }
            List listAsList = Arrays.asList(sizeArrA);
            Collections.sort(listAsList, new C20046z0());
            Size sizeE = c20013i0.e();
            long jMin = Math.min(sizeE.getWidth() * sizeE.getHeight(), 307200L);
            int length = sizeArrA.length;
            Size size3 = null;
            int i12 = 0;
            while (true) {
                if (i12 >= length) {
                    break;
                }
                Size size4 = sizeArrA[i12];
                long width = size4.getWidth() * size4.getHeight();
                if (width == jMin) {
                    size = size4;
                    break;
                } else if (width <= jMin) {
                    i12++;
                    size3 = size4;
                } else if (size3 != null) {
                    size = size3;
                }
            }
            size = (Size) listAsList.get(0);
        }
        this.f22903d = size;
        Objects.toString(size);
        C20140q0.d(3, "MeteringRepeating");
        this.f22901b = a();
    }

    @j.N
    public final SessionConfig a() {
        SurfaceTexture surfaceTexture = new SurfaceTexture(0);
        Size size = this.f22903d;
        surfaceTexture.setDefaultBufferSize(size.getWidth(), size.getHeight());
        Surface surface = new Surface(surfaceTexture);
        SessionConfig.b bVarM = SessionConfig.b.m(this.f22902c, size);
        bVarM.s(1);
        C20094g0 c20094g0 = new C20094g0(surface);
        this.f22900a = c20094g0;
        androidx.camera.core.impl.utils.futures.f.a(androidx.camera.core.impl.utils.futures.f.h(c20094g0.f23933e), new a(surface, surfaceTexture), androidx.camera.core.impl.utils.executor.c.a());
        bVarM.i(this.f22900a, androidx.camera.core.F.f23562d);
        bVarM.d(new SessionConfig.c() { // from class: androidx.camera.camera2.internal.y0
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.camera.core.impl.SessionConfig.c
            public final void onError() {
                A0 a02 = this.f23524a;
                a02.f22901b = a02.a();
                r rVar = a02.f22904e;
                if (rVar != null) {
                    C20039w c20039w = rVar.f23429b;
                    c20039w.getClass();
                    try {
                        if (((Boolean) androidx.concurrent.futures.b.a(new r(c20039w)).get()).booleanValue()) {
                            A0 a03 = c20039w.f23484t;
                            SessionConfig sessionConfig = a03.f22901b;
                            c20039w.f23468d.execute(new RunnableC20033t(c20039w, C20039w.v(a03), sessionConfig, a03.f22902c, 0));
                        }
                    } catch (InterruptedException | ExecutionException e12) {
                        throw new RuntimeException("Unable to check if MeteringRepeating is attached.", e12);
                    }
                }
            }
        });
        return bVarM.k();
    }
}
