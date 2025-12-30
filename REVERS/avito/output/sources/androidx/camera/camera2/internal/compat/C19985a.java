package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.OutputConfiguration;
import android.util.Size;
import android.view.Surface;
import j.InterfaceC42164u;
import j.N;
import j.X;

/* compiled from: ApiCompat.java */
/* renamed from: androidx.camera.camera2.internal.compat.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C19985a {

    /* compiled from: ApiCompat.java */
    @X
    /* renamed from: androidx.camera.camera2.internal.compat.a$a, reason: collision with other inner class name */
    public static class C1529a {
        @InterfaceC42164u
        public static void a(@N CameraDevice cameraDevice) {
            cameraDevice.close();
        }
    }

    /* compiled from: ApiCompat.java */
    @X
    /* renamed from: androidx.camera.camera2.internal.compat.a$b */
    public static class b {
        @InterfaceC42164u
        public static void a(@N CameraCaptureSession.StateCallback stateCallback, @N CameraCaptureSession cameraCaptureSession, @N Surface surface) {
            stateCallback.onSurfacePrepared(cameraCaptureSession, surface);
        }
    }

    /* compiled from: ApiCompat.java */
    @X
    /* renamed from: androidx.camera.camera2.internal.compat.a$c */
    public static class c {
        @InterfaceC42164u
        public static void a(@N CameraCaptureSession.CaptureCallback captureCallback, @N CameraCaptureSession cameraCaptureSession, @N CaptureRequest captureRequest, @N Surface surface, long j12) {
            captureCallback.onCaptureBufferLost(cameraCaptureSession, captureRequest, surface, j12);
        }
    }

    /* compiled from: ApiCompat.java */
    @X
    /* renamed from: androidx.camera.camera2.internal.compat.a$d */
    public static class d {
        @InterfaceC42164u
        @N
        public static <T> OutputConfiguration a(@N Size size, @N Class<T> cls) {
            return new OutputConfiguration(size, cls);
        }

        @InterfaceC42164u
        public static void b(@N CameraCaptureSession.StateCallback stateCallback, @N CameraCaptureSession cameraCaptureSession) {
            stateCallback.onCaptureQueueEmpty(cameraCaptureSession);
        }
    }

    /* compiled from: ApiCompat.java */
    @X
    /* renamed from: androidx.camera.camera2.internal.compat.a$e */
    public static class e {
        @InterfaceC42164u
        public static void a(@N CameraManager.AvailabilityCallback availabilityCallback) {
            availabilityCallback.onCameraAccessPrioritiesChanged();
        }
    }
}
