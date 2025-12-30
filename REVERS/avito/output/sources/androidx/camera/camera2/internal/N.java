package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import androidx.camera.camera2.internal.C20003d0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.y0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: Camera2RequestProcessor.java */
@j.X
/* loaded from: classes.dex */
public class N implements androidx.camera.core.impl.y0 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final C20003d0 f23022a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final List<androidx.camera.core.impl.D0> f23023b;

    /* renamed from: c, reason: collision with root package name */
    public volatile boolean f23024c = false;

    /* renamed from: d, reason: collision with root package name */
    @j.P
    public volatile SessionConfig f23025d;

    /* compiled from: Camera2RequestProcessor.java */
    public class a extends CameraCaptureSession.CaptureCallback {

        /* renamed from: a, reason: collision with root package name */
        public final y0.a f23026a;

        /* renamed from: b, reason: collision with root package name */
        public final y0.b f23027b;

        /* renamed from: c, reason: collision with root package name */
        public final boolean f23028c;

        public a(@j.N y0.b bVar, @j.N y0.a aVar, boolean z12) {
            this.f23026a = aVar;
            this.f23027b = bVar;
            this.f23028c = z12;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureBufferLost(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N Surface surface, long j12) {
            int i12;
            Iterator<androidx.camera.core.impl.D0> it = N.this.f23023b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i12 = -1;
                    break;
                } else if (it.next().c().get() == surface) {
                    i12 = 0;
                    break;
                }
            }
            this.f23026a.onCaptureBufferLost(this.f23027b, j12, i12);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
            this.f23026a.onCaptureCompleted(this.f23027b, new C20010h(totalCaptureResult));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureFailure captureFailure) {
            this.f23026a.onCaptureFailed(this.f23027b, new C20008g(captureFailure));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureProgressed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureResult captureResult) {
            this.f23026a.onCaptureProgressed(this.f23027b, new C20010h(captureResult));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(@j.N CameraCaptureSession cameraCaptureSession, int i12) {
            if (this.f23028c) {
                this.f23026a.onCaptureSequenceAborted(i12);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(@j.N CameraCaptureSession cameraCaptureSession, int i12, long j12) {
            if (this.f23028c) {
                this.f23026a.onCaptureSequenceCompleted(i12, j12);
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureStarted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, long j12, long j13) {
            this.f23026a.onCaptureStarted(this.f23027b, j13, j12);
        }
    }

    public N(@j.N C20003d0 c20003d0, @j.N ArrayList arrayList) {
        androidx.core.util.z.a("CaptureSession state must be OPENED. Current state:" + c20003d0.f23294l, c20003d0.f23294l == C20003d0.d.f23307f);
        this.f23022a = c20003d0;
        this.f23023b = Collections.unmodifiableList(new ArrayList(arrayList));
    }

    @Override // androidx.camera.core.impl.y0
    public final void a() {
        if (this.f23024c) {
            return;
        }
        C20003d0 c20003d0 = this.f23022a;
        synchronized (c20003d0.f23283a) {
            C20003d0.d dVar = c20003d0.f23294l;
            if (dVar != C20003d0.d.f23307f) {
                Objects.toString(dVar);
                C20140q0.b("CaptureSession");
            } else {
                try {
                    c20003d0.f23288f.a();
                } catch (CameraAccessException unused) {
                    C20140q0.c("CaptureSession");
                }
            }
        }
    }

    @Override // androidx.camera.core.impl.y0
    public final void b() {
        if (this.f23024c) {
            return;
        }
        C20003d0 c20003d0 = this.f23022a;
        synchronized (c20003d0.f23283a) {
            C20003d0.d dVar = c20003d0.f23294l;
            if (dVar != C20003d0.d.f23307f) {
                Objects.toString(dVar);
                C20140q0.b("CaptureSession");
            } else {
                try {
                    c20003d0.f23288f.b();
                } catch (CameraAccessException unused) {
                    C20140q0.c("CaptureSession");
                }
            }
        }
    }

    @Override // androidx.camera.core.impl.y0
    public final int c(@j.N y0.b bVar, @j.N y0.a aVar) {
        if (this.f23024c || !g(bVar)) {
            return -1;
        }
        SessionConfig.b bVar2 = new SessionConfig.b();
        bVar2.s(bVar.getTemplateId());
        bVar2.q(bVar.getParameters());
        bVar2.b(new Z(new a(bVar, aVar, true)));
        if (this.f23025d != null) {
            Iterator<AbstractC20109p> it = this.f23025d.f24017f.f23972e.iterator();
            while (it.hasNext()) {
                bVar2.b(it.next());
            }
            androidx.camera.core.impl.J0 j02 = this.f23025d.f24017f.f23974g;
            for (String str : j02.f23987a.keySet()) {
                bVar2.j(j02.f23987a.get(str), str);
            }
        }
        Iterator<Integer> it2 = bVar.getTargetOutputConfigIds().iterator();
        while (it2.hasNext()) {
            bVar2.i(f(it2.next().intValue()), androidx.camera.core.F.f23562d);
        }
        return this.f23022a.l(bVar2.k());
    }

    @Override // androidx.camera.core.impl.y0
    public final int d(@j.N List<y0.b> list, @j.N y0.a aVar) {
        if (this.f23024c) {
            return -1;
        }
        Iterator<y0.b> it = list.iterator();
        while (it.hasNext()) {
            if (!g(it.next())) {
                return -1;
            }
        }
        ArrayList arrayList = new ArrayList();
        boolean z12 = true;
        for (y0.b bVar : list) {
            H.a aVar2 = new H.a();
            aVar2.f23978c = bVar.getTemplateId();
            aVar2.f23977b = androidx.camera.core.impl.n0.S(bVar.getParameters());
            aVar2.b(new Z(new a(bVar, aVar, z12)));
            Iterator<Integer> it2 = bVar.getTargetOutputConfigIds().iterator();
            while (it2.hasNext()) {
                aVar2.d(f(it2.next().intValue()));
            }
            arrayList.add(aVar2.e());
            z12 = false;
        }
        return this.f23022a.k(arrayList);
    }

    @Override // androidx.camera.core.impl.y0
    public final int e(@j.N y0.b bVar, @j.N y0.a aVar) {
        return d(Arrays.asList(bVar), aVar);
    }

    @j.P
    public final androidx.camera.core.impl.D0 f(int i12) {
        for (androidx.camera.core.impl.D0 d02 : this.f23023b) {
            d02.getClass();
            if (i12 == 0) {
                return d02;
            }
        }
        return null;
    }

    public final boolean g(@j.N y0.b bVar) {
        if (bVar.getTargetOutputConfigIds().isEmpty()) {
            C20140q0.b("Camera2RequestProcessor");
            return false;
        }
        Iterator<Integer> it = bVar.getTargetOutputConfigIds().iterator();
        while (it.hasNext()) {
            if (f(it.next().intValue()) == null) {
                C20140q0.b("Camera2RequestProcessor");
                return false;
            }
        }
        return true;
    }
}
