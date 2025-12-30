package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.C19985a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: CameraBurstCaptureCallback.java */
@j.X
/* loaded from: classes.dex */
class Q extends CameraCaptureSession.CaptureCallback {

    /* renamed from: b, reason: collision with root package name */
    public C19982b0 f23055b = null;

    /* renamed from: a, reason: collision with root package name */
    public final HashMap f23054a = new HashMap();

    /* compiled from: CameraBurstCaptureCallback.java */
    public interface a {
    }

    public final void a(CaptureRequest captureRequest, List<CameraCaptureSession.CaptureCallback> list) {
        HashMap map = this.f23054a;
        List list2 = (List) map.get(captureRequest);
        if (list2 == null) {
            map.put(captureRequest, list);
            return;
        }
        ArrayList arrayList = new ArrayList(list2.size() + list.size());
        arrayList.addAll(list);
        arrayList.addAll(list2);
        map.put(captureRequest, arrayList);
    }

    public final List<CameraCaptureSession.CaptureCallback> b(CaptureRequest captureRequest) {
        List<CameraCaptureSession.CaptureCallback> list = (List) this.f23054a.get(captureRequest);
        return list != null ? list : Collections.emptyList();
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    @j.X
    public final void onCaptureBufferLost(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N Surface surface, long j12) {
        Iterator<CameraCaptureSession.CaptureCallback> it = b(captureRequest).iterator();
        while (it.hasNext()) {
            C19985a.c.a(it.next(), cameraCaptureSession, captureRequest, surface, j12);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
        Iterator<CameraCaptureSession.CaptureCallback> it = b(captureRequest).iterator();
        while (it.hasNext()) {
            it.next().onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureFailed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureFailure captureFailure) {
        Iterator<CameraCaptureSession.CaptureCallback> it = b(captureRequest).iterator();
        while (it.hasNext()) {
            it.next().onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureProgressed(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N CaptureResult captureResult) {
        Iterator<CameraCaptureSession.CaptureCallback> it = b(captureRequest).iterator();
        while (it.hasNext()) {
            it.next().onCaptureProgressed(cameraCaptureSession, captureRequest, captureResult);
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureSequenceAborted(@j.N CameraCaptureSession cameraCaptureSession, int i12) {
        Iterator it = this.f23054a.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((List) it.next()).iterator();
            while (it2.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it2.next()).onCaptureSequenceAborted(cameraCaptureSession, i12);
            }
        }
        C19982b0 c19982b0 = this.f23055b;
        if (c19982b0 != null) {
            c19982b0.a();
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureSequenceCompleted(@j.N CameraCaptureSession cameraCaptureSession, int i12, long j12) {
        Iterator it = this.f23054a.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((List) it.next()).iterator();
            while (it2.hasNext()) {
                ((CameraCaptureSession.CaptureCallback) it2.next()).onCaptureSequenceCompleted(cameraCaptureSession, i12, j12);
            }
        }
        C19982b0 c19982b0 = this.f23055b;
        if (c19982b0 != null) {
            c19982b0.a();
        }
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureStarted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, long j12, long j13) {
        Iterator<CameraCaptureSession.CaptureCallback> it = b(captureRequest).iterator();
        while (it.hasNext()) {
            it.next().onCaptureStarted(cameraCaptureSession, captureRequest, j12, j13);
        }
    }
}
