package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.Z0;
import androidx.camera.core.CameraControl;
import androidx.concurrent.futures.b;

/* compiled from: CropRegionZoomImpl.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.h0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20011h0 implements Z0.b {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.n f23337a;

    /* renamed from: c, reason: collision with root package name */
    public b.a<Void> f23339c;

    /* renamed from: b, reason: collision with root package name */
    public Rect f23338b = null;

    /* renamed from: d, reason: collision with root package name */
    public Rect f23340d = null;

    public C20011h0(@j.N androidx.camera.camera2.internal.compat.n nVar) {
        this.f23337a = nVar;
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final void a(@j.N TotalCaptureResult totalCaptureResult) {
        if (this.f23339c != null) {
            CaptureRequest request = totalCaptureResult.getRequest();
            Rect rect = request == null ? null : (Rect) request.get(CaptureRequest.SCALER_CROP_REGION);
            Rect rect2 = this.f23340d;
            if (rect2 == null || !rect2.equals(rect)) {
                return;
            }
            this.f23339c.b(null);
            this.f23339c = null;
            this.f23340d = null;
        }
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final float getMaxZoom() {
        Float f12 = (Float) this.f23337a.a(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
        if (f12 != null && f12.floatValue() >= 1.0f) {
            return f12.floatValue();
        }
        return 1.0f;
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final float getMinZoom() {
        return 1.0f;
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final void l0() {
        this.f23340d = null;
        this.f23338b = null;
        b.a<Void> aVar = this.f23339c;
        if (aVar != null) {
            aVar.d(new CameraControl.OperationCanceledException("Camera is not active."));
            this.f23339c = null;
        }
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    public final void m0(float f12, @j.N b.a<Void> aVar) {
        ((Rect) this.f23337a.a(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)).getClass();
        float fWidth = r0.width() / f12;
        float fHeight = r0.height() / f12;
        float fWidth2 = (r0.width() - fWidth) / 2.0f;
        float fHeight2 = (r0.height() - fHeight) / 2.0f;
        this.f23338b = new Rect((int) fWidth2, (int) fHeight2, (int) (fWidth2 + fWidth), (int) (fHeight2 + fHeight));
        b.a<Void> aVar2 = this.f23339c;
        if (aVar2 != null) {
            aVar2.d(new CameraControl.OperationCanceledException("There is a new zoomRatio being set"));
        }
        this.f23340d = this.f23338b;
        this.f23339c = aVar;
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    @j.N
    public final Rect n0() {
        Rect rect = this.f23338b;
        if (rect != null) {
            return rect;
        }
        Rect rect2 = (Rect) this.f23337a.a(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        rect2.getClass();
        return rect2;
    }

    @Override // androidx.camera.camera2.internal.Z0.b
    @j.S
    public final void o0(@j.N b.a aVar) {
        Rect rect = this.f23338b;
        if (rect != null) {
            aVar.c(CaptureRequest.SCALER_CROP_REGION, rect);
        }
    }
}
