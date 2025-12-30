package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import androidx.camera.core.impl.SessionConfig;
import java.util.HashMap;
import java.util.List;

/* compiled from: CaptureSessionInterface.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.f0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
interface InterfaceC20007f0 {
    void a(@j.N HashMap map);

    void b(@j.N List<androidx.camera.core.impl.H> list);

    @j.N
    com.google.common.util.concurrent.D0<Void> c(@j.N SessionConfig sessionConfig, @j.N CameraDevice cameraDevice, @j.N Q0 q02);

    void close();

    @j.P
    SessionConfig d();

    void e(@j.P SessionConfig sessionConfig);

    void f();

    @j.N
    List<androidx.camera.core.impl.H> g();

    @j.N
    com.google.common.util.concurrent.D0 release();
}
