package androidx.camera.camera2.impl;

import D.h;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import androidx.annotation.RestrictTo;
import androidx.camera.core.L;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20106m0;
import androidx.camera.core.impl.n0;
import androidx.camera.core.impl.s0;
import j.N;
import j.S;
import j.X;

/* compiled from: Camera2ImplConfig.java */
@S
@X
/* loaded from: classes.dex */
public final class b extends h {

    /* renamed from: G, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<Integer> f22889G = Config.a.a(Integer.TYPE, "camera2.captureRequest.templateType");

    /* renamed from: H, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<Long> f22890H = Config.a.a(Long.TYPE, "camera2.cameraCaptureSession.streamUseCase");

    /* renamed from: I, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<CameraDevice.StateCallback> f22891I = Config.a.a(CameraDevice.StateCallback.class, "camera2.cameraDevice.stateCallback");

    /* renamed from: J, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<CameraCaptureSession.StateCallback> f22892J = Config.a.a(CameraCaptureSession.StateCallback.class, "camera2.cameraCaptureSession.stateCallback");

    /* renamed from: K, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<CameraCaptureSession.CaptureCallback> f22893K = Config.a.a(CameraCaptureSession.CaptureCallback.class, "camera2.cameraCaptureSession.captureCallback");

    /* renamed from: L, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<d> f22894L = Config.a.a(d.class, "camera2.cameraEvent.callback");

    /* renamed from: M, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<Object> f22895M = Config.a.a(Object.class, "camera2.captureRequest.tag");

    /* renamed from: N, reason: collision with root package name */
    @RestrictTo
    public static final Config.a<String> f22896N = Config.a.a(String.class, "camera2.cameraCaptureSession.physicalCameraId");

    /* compiled from: Camera2ImplConfig.java */
    public static final class a implements L<b> {

        /* renamed from: a, reason: collision with root package name */
        public final n0 f22897a = n0.R();

        @N
        public final b a() {
            return new b(s0.Q(this.f22897a));
        }

        @N
        public final void c(@N CaptureRequest.Key key, @N Object obj) {
            this.f22897a.K(b.Q(key), obj);
        }

        @Override // androidx.camera.core.L
        @N
        public final InterfaceC20106m0 d() {
            throw null;
        }
    }

    /* compiled from: Camera2ImplConfig.java */
    /* renamed from: androidx.camera.camera2.impl.b$b, reason: collision with other inner class name */
    public static final class C1528b<T> {
    }

    @N
    @RestrictTo
    public static Config.a<Object> Q(@N CaptureRequest.Key<?> key) {
        return Config.a.b("camera2.captureRequest.option." + key.getName(), key);
    }
}
