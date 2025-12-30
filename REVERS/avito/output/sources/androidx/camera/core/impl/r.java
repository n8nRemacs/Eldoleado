package androidx.camera.core.impl;

import android.hardware.camera2.CaptureResult;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.utils.ExifData;
import java.util.ArrayList;

/* compiled from: CameraCaptureResult.java */
@j.X
/* loaded from: classes.dex */
public interface r {

    /* compiled from: CameraCaptureResult.java */
    public static final class a implements r {
        @Override // androidx.camera.core.impl.r
        public final long a() {
            return -1L;
        }

        @Override // androidx.camera.core.impl.r
        @j.N
        public final J0 b() {
            return J0.f23986b;
        }

        @Override // androidx.camera.core.impl.r
        @j.N
        public final CameraCaptureMetaData.FlashState d() {
            return CameraCaptureMetaData.FlashState.f23903b;
        }

        @Override // androidx.camera.core.impl.r
        @j.N
        public final CameraCaptureMetaData.AfState e() {
            return CameraCaptureMetaData.AfState.f23889b;
        }

        @Override // androidx.camera.core.impl.r
        @j.N
        public final CameraCaptureMetaData.AwbState f() {
            return CameraCaptureMetaData.AwbState.f23897b;
        }

        @Override // androidx.camera.core.impl.r
        @j.N
        public final CameraCaptureMetaData.AeState g() {
            return CameraCaptureMetaData.AeState.f23877b;
        }
    }

    long a();

    @j.N
    J0 b();

    default void c(@j.N ExifData.b bVar) {
        int i12;
        CameraCaptureMetaData.FlashState flashStateD = d();
        if (flashStateD == CameraCaptureMetaData.FlashState.f23903b) {
            return;
        }
        int iOrdinal = flashStateD.ordinal();
        if (iOrdinal == 1) {
            i12 = 32;
        } else if (iOrdinal == 2) {
            i12 = 0;
        } else {
            if (iOrdinal != 3) {
                flashStateD.toString();
                C20140q0.d(5, "ExifData");
                return;
            }
            i12 = 1;
        }
        int i13 = i12 & 1;
        ArrayList arrayList = bVar.f24193a;
        if (i13 == 1) {
            bVar.c("LightSource", String.valueOf(4), arrayList);
        }
        bVar.c("Flash", String.valueOf(i12), arrayList);
    }

    @j.N
    CameraCaptureMetaData.FlashState d();

    @j.N
    CameraCaptureMetaData.AfState e();

    @j.N
    CameraCaptureMetaData.AwbState f();

    @j.N
    CameraCaptureMetaData.AeState g();

    @j.N
    default CaptureResult h() {
        return new a().h();
    }
}
