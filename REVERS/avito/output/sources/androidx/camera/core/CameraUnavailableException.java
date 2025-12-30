package androidx.camera.core;

import androidx.annotation.RestrictTo;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@j.X
/* loaded from: classes.dex */
public class CameraUnavailableException extends Exception {

    /* renamed from: b, reason: collision with root package name */
    public final int f23557b;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface a {
    }

    public CameraUnavailableException(int i12, @j.P CameraAccessExceptionCompat cameraAccessExceptionCompat) {
        super(cameraAccessExceptionCompat);
        this.f23557b = i12;
    }
}
