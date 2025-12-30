package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import androidx.annotation.RestrictTo;
import com.huawei.updatesdk.service.otaupdate.UpdateDialogStatusCode;
import j.N;
import j.P;
import j.X;
import j.k0;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@X
/* loaded from: classes.dex */
public class CameraAccessExceptionCompat extends Exception {

    /* renamed from: c, reason: collision with root package name */
    @k0
    public static final Set<Integer> f23128c = Collections.unmodifiableSet(new HashSet(Arrays.asList(4, 5, 1, 2, 3)));

    /* renamed from: d, reason: collision with root package name */
    @k0
    public static final Set<Integer> f23129d = Collections.unmodifiableSet(new HashSet(Arrays.asList(Integer.valueOf(UpdateDialogStatusCode.DISMISS), Integer.valueOf(UpdateDialogStatusCode.SHOW))));

    /* renamed from: b, reason: collision with root package name */
    public final int f23130b;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface a {
    }

    public CameraAccessExceptionCompat(@P RuntimeException runtimeException) {
        super("Some API 28 devices cannot access the camera when the device is in \"Do Not Disturb\" mode. The camera will not be accessible until \"Do Not Disturb\" mode is disabled.", runtimeException);
        this.f23130b = UpdateDialogStatusCode.DISMISS;
        if (f23128c.contains(Integer.valueOf(UpdateDialogStatusCode.DISMISS))) {
            new CameraAccessException(UpdateDialogStatusCode.DISMISS, null, runtimeException);
        }
    }

    public CameraAccessExceptionCompat(@N CameraAccessException cameraAccessException) {
        super(cameraAccessException.getMessage(), cameraAccessException.getCause());
        this.f23130b = cameraAccessException.getReason();
    }

    public CameraAccessExceptionCompat(@P String str, @P AssertionError assertionError) {
        super(String.format("%s (%d): %s", "CAMERA_CHARACTERISTICS_CREATION_ERROR", Integer.valueOf(UpdateDialogStatusCode.SHOW), str), assertionError);
        this.f23130b = UpdateDialogStatusCode.SHOW;
        if (f23128c.contains(Integer.valueOf(UpdateDialogStatusCode.SHOW))) {
            new CameraAccessException(UpdateDialogStatusCode.SHOW, str, assertionError);
        }
    }
}
