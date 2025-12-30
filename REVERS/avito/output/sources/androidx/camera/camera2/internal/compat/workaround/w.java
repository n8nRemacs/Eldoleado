package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.internal.C;
import androidx.camera.camera2.internal.K0;
import androidx.camera.camera2.internal.compat.quirk.C19998i;
import androidx.camera.core.impl.w0;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: WaitForRepeatingRequestStart.java */
@X
/* loaded from: classes.dex */
public class w {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f23276a;

    /* renamed from: c, reason: collision with root package name */
    @N
    public final D0<Void> f23278c;

    /* renamed from: d, reason: collision with root package name */
    public b.a<Void> f23279d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f23280e;

    /* renamed from: b, reason: collision with root package name */
    public final Object f23277b = new Object();

    /* renamed from: f, reason: collision with root package name */
    public final CameraCaptureSession.CaptureCallback f23281f = new a();

    /* compiled from: WaitForRepeatingRequestStart.java */
    public class a extends CameraCaptureSession.CaptureCallback {
        public a() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(@N CameraCaptureSession cameraCaptureSession, int i12) {
            w wVar = w.this;
            b.a<Void> aVar = wVar.f23279d;
            if (aVar != null) {
                aVar.c();
                wVar.f23279d = null;
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureStarted(@N CameraCaptureSession cameraCaptureSession, @N CaptureRequest captureRequest, long j12, long j13) {
            w wVar = w.this;
            b.a<Void> aVar = wVar.f23279d;
            if (aVar != null) {
                aVar.b(null);
                wVar.f23279d = null;
            }
        }
    }

    /* compiled from: WaitForRepeatingRequestStart.java */
    @FunctionalInterface
    public interface b {
    }

    /* compiled from: WaitForRepeatingRequestStart.java */
    @FunctionalInterface
    public interface c {
    }

    public w(@N w0 w0Var) {
        boolean zA2 = w0Var.a(C19998i.class);
        this.f23276a = zA2;
        if (zA2) {
            this.f23278c = androidx.concurrent.futures.b.a(new Ae0.c(this, 7));
        } else {
            this.f23278c = androidx.camera.core.impl.utils.futures.f.g(null);
        }
    }

    @N
    public static androidx.camera.core.impl.utils.futures.d a(@N CameraDevice cameraDevice, @N androidx.camera.camera2.internal.compat.params.l lVar, @N List list, @N ArrayList arrayList, @N C c12) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((K0) it.next()).h());
        }
        androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(androidx.camera.core.impl.utils.futures.f.k(arrayList2));
        v vVar = new v(c12, cameraDevice, lVar, list, 0);
        Executor executorA = androidx.camera.core.impl.utils.executor.c.a();
        dVarA.getClass();
        return (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, vVar, executorA);
    }
}
