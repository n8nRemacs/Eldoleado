package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.utils.ExifData;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.http2.Settings;

/* compiled from: Camera2CameraCaptureResult.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20010h implements androidx.camera.core.impl.r {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.impl.J0 f23335a;

    /* renamed from: b, reason: collision with root package name */
    public final CaptureResult f23336b;

    public C20010h(@j.N androidx.camera.core.impl.J0 j02, @j.N CaptureResult captureResult) {
        this.f23335a = j02;
        this.f23336b = captureResult;
    }

    @Override // androidx.camera.core.impl.r
    public final long a() {
        Long l12 = (Long) this.f23336b.get(CaptureResult.SENSOR_TIMESTAMP);
        if (l12 == null) {
            return -1L;
        }
        return l12.longValue();
    }

    @Override // androidx.camera.core.impl.r
    @j.N
    public final androidx.camera.core.impl.J0 b() {
        return this.f23335a;
    }

    @Override // androidx.camera.core.impl.r
    public final void c(@j.N ExifData.b bVar) {
        super.c(bVar);
        CaptureResult.Key key = CaptureResult.SCALER_CROP_REGION;
        CaptureResult captureResult = this.f23336b;
        Rect rect = (Rect) captureResult.get(key);
        ArrayList arrayList = bVar.f24193a;
        if (rect != null) {
            bVar.c("ImageWidth", String.valueOf(rect.width()), arrayList);
            bVar.c("ImageLength", String.valueOf(rect.height()), arrayList);
        }
        try {
            Integer num = (Integer) captureResult.get(CaptureResult.JPEG_ORIENTATION);
            if (num != null) {
                bVar.e(num.intValue());
            }
        } catch (BufferUnderflowException unused) {
            C20140q0.d(5, "C2CameraCaptureResult");
        }
        if (((Long) captureResult.get(CaptureResult.SENSOR_EXPOSURE_TIME)) != null) {
            bVar.c("ExposureTime", String.valueOf(r0.longValue() / TimeUnit.SECONDS.toNanos(1L)), arrayList);
        }
        Float f12 = (Float) captureResult.get(CaptureResult.LENS_APERTURE);
        if (f12 != null) {
            bVar.c("FNumber", String.valueOf(f12.floatValue()), arrayList);
        }
        Integer numValueOf = (Integer) captureResult.get(CaptureResult.SENSOR_SENSITIVITY);
        if (numValueOf != null) {
            if (((Integer) captureResult.get(CaptureResult.CONTROL_POST_RAW_SENSITIVITY_BOOST)) != null) {
                numValueOf = Integer.valueOf(numValueOf.intValue() * ((int) (r3.intValue() / 100.0f)));
            }
            int iIntValue = numValueOf.intValue();
            bVar.c("SensitivityType", String.valueOf(3), arrayList);
            bVar.c("PhotographicSensitivity", String.valueOf(Math.min(Settings.DEFAULT_INITIAL_WINDOW_SIZE, iIntValue)), arrayList);
        }
        Float f13 = (Float) captureResult.get(CaptureResult.LENS_FOCAL_LENGTH);
        if (f13 != null) {
            bVar.d(f13.floatValue());
        }
        Integer num2 = (Integer) captureResult.get(CaptureResult.CONTROL_AWB_MODE);
        if (num2 != null) {
            ExifData.WhiteBalanceMode whiteBalanceMode = ExifData.WhiteBalanceMode.f24184b;
            if (num2.intValue() == 0) {
                whiteBalanceMode = ExifData.WhiteBalanceMode.f24185c;
            }
            int iOrdinal = whiteBalanceMode.ordinal();
            bVar.c("WhiteBalance", iOrdinal != 0 ? iOrdinal != 1 ? null : String.valueOf(1) : String.valueOf(0), arrayList);
        }
    }

    @Override // androidx.camera.core.impl.r
    @j.N
    public final CameraCaptureMetaData.FlashState d() {
        Integer num = (Integer) this.f23336b.get(CaptureResult.FLASH_STATE);
        CameraCaptureMetaData.FlashState flashState = CameraCaptureMetaData.FlashState.f23903b;
        if (num == null) {
            return flashState;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0 || iIntValue == 1) {
            return CameraCaptureMetaData.FlashState.f23904c;
        }
        if (iIntValue == 2) {
            return CameraCaptureMetaData.FlashState.f23905d;
        }
        if (iIntValue == 3 || iIntValue == 4) {
            return CameraCaptureMetaData.FlashState.f23906e;
        }
        C20140q0.b("C2CameraCaptureResult");
        return flashState;
    }

    @Override // androidx.camera.core.impl.r
    @j.N
    public final CameraCaptureMetaData.AfState e() {
        Integer num = (Integer) this.f23336b.get(CaptureResult.CONTROL_AF_STATE);
        CameraCaptureMetaData.AfState afState = CameraCaptureMetaData.AfState.f23889b;
        if (num == null) {
            return afState;
        }
        switch (num.intValue()) {
            case 0:
                break;
            case 1:
            case 3:
                break;
            case 2:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                C20140q0.b("C2CameraCaptureResult");
                break;
        }
        return afState;
    }

    @Override // androidx.camera.core.impl.r
    @j.N
    public final CameraCaptureMetaData.AwbState f() {
        Integer num = (Integer) this.f23336b.get(CaptureResult.CONTROL_AWB_STATE);
        CameraCaptureMetaData.AwbState awbState = CameraCaptureMetaData.AwbState.f23897b;
        if (num == null) {
            return awbState;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return CameraCaptureMetaData.AwbState.f23898c;
        }
        if (iIntValue == 1) {
            return CameraCaptureMetaData.AwbState.f23899d;
        }
        if (iIntValue == 2) {
            return CameraCaptureMetaData.AwbState.f23900e;
        }
        if (iIntValue == 3) {
            return CameraCaptureMetaData.AwbState.f23901f;
        }
        C20140q0.b("C2CameraCaptureResult");
        return awbState;
    }

    @Override // androidx.camera.core.impl.r
    @j.N
    public final CameraCaptureMetaData.AeState g() {
        Integer num = (Integer) this.f23336b.get(CaptureResult.CONTROL_AE_STATE);
        CameraCaptureMetaData.AeState aeState = CameraCaptureMetaData.AeState.f23877b;
        if (num == null) {
            return aeState;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return CameraCaptureMetaData.AeState.f23878c;
        }
        if (iIntValue != 1) {
            if (iIntValue == 2) {
                return CameraCaptureMetaData.AeState.f23881f;
            }
            if (iIntValue == 3) {
                return CameraCaptureMetaData.AeState.f23882g;
            }
            if (iIntValue == 4) {
                return CameraCaptureMetaData.AeState.f23880e;
            }
            if (iIntValue != 5) {
                C20140q0.b("C2CameraCaptureResult");
                return aeState;
            }
        }
        return CameraCaptureMetaData.AeState.f23879d;
    }

    @Override // androidx.camera.core.impl.r
    @j.N
    public final CaptureResult h() {
        return this.f23336b;
    }

    @j.N
    public final CameraCaptureMetaData.AfMode i() {
        Integer num = (Integer) this.f23336b.get(CaptureResult.CONTROL_AF_MODE);
        CameraCaptureMetaData.AfMode afMode = CameraCaptureMetaData.AfMode.f23884b;
        if (num == null) {
            return afMode;
        }
        int iIntValue = num.intValue();
        if (iIntValue != 0) {
            if (iIntValue == 1 || iIntValue == 2) {
                return CameraCaptureMetaData.AfMode.f23886d;
            }
            if (iIntValue == 3 || iIntValue == 4) {
                return CameraCaptureMetaData.AfMode.f23887e;
            }
            if (iIntValue != 5) {
                C20140q0.b("C2CameraCaptureResult");
                return afMode;
            }
        }
        return CameraCaptureMetaData.AfMode.f23885c;
    }

    public C20010h(@j.N CaptureResult captureResult) {
        this(androidx.camera.core.impl.J0.f23986b, captureResult);
    }
}
