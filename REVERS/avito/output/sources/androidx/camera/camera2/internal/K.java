package androidx.camera.camera2.internal;

import D.h;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Range;
import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import j.InterfaceC42164u;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/* compiled from: Camera2CaptureRequestBuilder.java */
@j.X
/* loaded from: classes.dex */
class K {

    /* compiled from: Camera2CaptureRequestBuilder.java */
    @j.X
    public static class a {
        @InterfaceC42164u
        public static CaptureRequest.Builder a(@j.N CameraDevice cameraDevice, @j.N TotalCaptureResult totalCaptureResult) {
            return cameraDevice.createReprocessCaptureRequest(totalCaptureResult);
        }
    }

    @j.S
    public static void a(CaptureRequest.Builder builder, androidx.camera.core.impl.s0 s0Var) {
        D.h hVarA = h.a.c(s0Var).a();
        for (Config.a<?> aVar : hVarA.e()) {
            CaptureRequest.Key key = (CaptureRequest.Key) aVar.d();
            try {
                builder.set(key, hVarA.f(aVar));
            } catch (IllegalArgumentException unused) {
                Objects.toString(key);
                C20140q0.b("Camera2CaptureRequestBuilder");
            }
        }
    }

    @j.P
    public static CaptureRequest b(@j.N androidx.camera.core.impl.H h12, @j.P CameraDevice cameraDevice, @j.N HashMap map) throws CameraAccessException {
        CaptureRequest.Builder builderCreateCaptureRequest;
        androidx.camera.core.impl.r rVar;
        if (cameraDevice == null) {
            return null;
        }
        List listUnmodifiableList = Collections.unmodifiableList(h12.f23968a);
        ArrayList arrayList = new ArrayList();
        Iterator it = listUnmodifiableList.iterator();
        while (it.hasNext()) {
            Surface surface = (Surface) map.get((DeferrableSurface) it.next());
            if (surface == null) {
                throw new IllegalArgumentException("DeferrableSurface not in configuredSurfaceMap");
            }
            arrayList.add(surface);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        int i12 = h12.f23970c;
        if (i12 == 5 && (rVar = h12.f23975h) != null && (rVar.h() instanceof TotalCaptureResult)) {
            C20140q0.d(3, "Camera2CaptureRequestBuilder");
            builderCreateCaptureRequest = a.a(cameraDevice, (TotalCaptureResult) rVar.h());
        } else {
            C20140q0.d(3, "Camera2CaptureRequestBuilder");
            builderCreateCaptureRequest = cameraDevice.createCaptureRequest(i12);
        }
        androidx.camera.core.impl.s0 s0Var = h12.f23969b;
        a(builderCreateCaptureRequest, s0Var);
        D.h hVarA = h.a.c(s0Var).a();
        CaptureRequest.Key key = CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE;
        if (!hVarA.c(androidx.camera.camera2.impl.b.Q(key))) {
            Config.a<Range<Integer>> aVar = androidx.camera.core.impl.H.f23967k;
            Object objF = androidx.camera.core.impl.G0.f23964a;
            try {
                objF = s0Var.f(aVar);
            } catch (IllegalArgumentException unused) {
            }
            Range range = (Range) objF;
            Objects.requireNonNull(range);
            Object objF2 = androidx.camera.core.impl.G0.f23964a;
            if (!range.equals(objF2)) {
                try {
                    objF2 = s0Var.f(androidx.camera.core.impl.H.f23967k);
                } catch (IllegalArgumentException unused2) {
                }
                Range range2 = (Range) objF2;
                Objects.requireNonNull(range2);
                builderCreateCaptureRequest.set(key, range2);
            }
        }
        Config.a<Integer> aVar2 = androidx.camera.core.impl.H.f23965i;
        TreeMap<Config.a<?>, Map<Config.OptionPriority, Object>> treeMap = s0Var.f24173F;
        if (treeMap.containsKey(aVar2)) {
            builderCreateCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION, (Integer) s0Var.f(aVar2));
        }
        Config.a<Integer> aVar3 = androidx.camera.core.impl.H.f23966j;
        if (treeMap.containsKey(aVar3)) {
            builderCreateCaptureRequest.set(CaptureRequest.JPEG_QUALITY, Byte.valueOf(((Integer) s0Var.f(aVar3)).byteValue()));
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            builderCreateCaptureRequest.addTarget((Surface) it2.next());
        }
        builderCreateCaptureRequest.setTag(h12.f23974g);
        return builderCreateCaptureRequest.build();
    }
}
