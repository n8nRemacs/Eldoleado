package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Range;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.Z0;
import androidx.camera.core.CameraControl;
import androidx.concurrent.futures.b;

/* compiled from: AndroidRZoomImpl.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C19981b implements Z0.b {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.n f23106a;

    /* renamed from: b, reason: collision with root package name */
    public final Range<Float> f23107b;

    /* renamed from: d, reason: collision with root package name */
    public b.a<Void> f23109d;

    /* renamed from: c, reason: collision with root package name */
    public float f23108c = 1.0f;

    /* renamed from: e, reason: collision with root package name */
    public float f23110e = 1.0f;

    public C19981b(@j.N androidx.camera.camera2.internal.compat.n nVar) {
        this.f23106a = nVar;
        this.f23107b = (Range) nVar.a(CameraCharacteristics.CONTROL_ZOOM_RATIO_RANGE);
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final void a(@j.N TotalCaptureResult totalCaptureResult) {
        if (this.f23109d != null) {
            CaptureRequest request = totalCaptureResult.getRequest();
            Float f12 = request == null ? null : (Float) request.get(CaptureRequest.CONTROL_ZOOM_RATIO);
            if (f12 == null) {
                return;
            }
            if (this.f23110e == f12.floatValue()) {
                this.f23109d.b(null);
                this.f23109d = null;
            }
        }
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final float getMaxZoom() {
        return ((Float) this.f23107b.getUpper()).floatValue();
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final float getMinZoom() {
        return ((Float) this.f23107b.getLower()).floatValue();
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final void l0() {
        this.f23108c = 1.0f;
        b.a<Void> aVar = this.f23109d;
        if (aVar != null) {
            aVar.d(new CameraControl.OperationCanceledException("Camera is not active."));
            this.f23109d = null;
        }
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final void m0(float f12, @j.N b.a<Void> aVar) {
        this.f23108c = f12;
        b.a<Void> aVar2 = this.f23109d;
        if (aVar2 != null) {
            aVar2.d(new CameraControl.OperationCanceledException("There is a new zoomRatio being set"));
        }
        this.f23110e = this.f23108c;
        this.f23109d = aVar;
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    @j.N
    public final Rect n0() {
        Rect rect = (Rect) this.f23106a.a(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        rect.getClass();
        return rect;
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final void o0(@j.N b.a aVar) {
        aVar.c(CaptureRequest.CONTROL_ZOOM_RATIO, Float.valueOf(this.f23108c));
    }
}
