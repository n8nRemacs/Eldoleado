package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.InputConfiguration;
import android.os.Handler;
import androidx.camera.camera2.internal.compat.C19986b;
import androidx.camera.camera2.internal.compat.t;
import androidx.camera.core.C20140q0;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: CameraDeviceCompatApi24Impl.java */
@X
/* loaded from: classes.dex */
class r extends q {
    @Override // androidx.camera.camera2.internal.compat.q, androidx.camera.camera2.internal.compat.t, androidx.camera.camera2.internal.compat.o.a
    public void a(@N androidx.camera.camera2.internal.compat.params.l lVar) throws CameraAccessException, CameraAccessExceptionCompat {
        CameraDevice cameraDevice = this.f23234a;
        cameraDevice.getClass();
        lVar.getClass();
        lVar.e().getClass();
        List<androidx.camera.camera2.internal.compat.params.f> listC = lVar.c();
        if (listC == null) {
            throw new IllegalArgumentException("Invalid output configurations");
        }
        if (lVar.a() == null) {
            throw new IllegalArgumentException("Invalid executor");
        }
        cameraDevice.getId();
        Iterator<androidx.camera.camera2.internal.compat.params.f> it = listC.iterator();
        while (it.hasNext()) {
            String strC = it.next().c();
            if (strC != null && !strC.isEmpty()) {
                C20140q0.d(5, "CameraDeviceCompat");
            }
        }
        C19986b.c cVar = new C19986b.c(lVar.a(), lVar.e());
        List<androidx.camera.camera2.internal.compat.params.f> listC2 = lVar.c();
        t.a aVar = (t.a) this.f23235b;
        aVar.getClass();
        androidx.camera.camera2.internal.compat.params.e eVarB = lVar.b();
        Handler handler = aVar.f23236a;
        try {
            if (eVarB != null) {
                InputConfiguration inputConfiguration = (InputConfiguration) eVarB.a();
                inputConfiguration.getClass();
                cameraDevice.createReprocessableCaptureSessionByConfigurations(inputConfiguration, androidx.camera.camera2.internal.compat.params.l.h(listC2), cVar, handler);
            } else {
                if (lVar.d() != 1) {
                    cameraDevice.createCaptureSessionByOutputConfigurations(androidx.camera.camera2.internal.compat.params.l.h(listC2), cVar, handler);
                    return;
                }
                ArrayList arrayList = new ArrayList(listC2.size());
                Iterator<androidx.camera.camera2.internal.compat.params.f> it2 = listC2.iterator();
                while (it2.hasNext()) {
                    arrayList.add(it2.next().d());
                }
                cameraDevice.createConstrainedHighSpeedCaptureSession(arrayList, cVar, handler);
            }
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }
}
