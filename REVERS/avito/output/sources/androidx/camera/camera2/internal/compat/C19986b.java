package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.os.Handler;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.C19985a;
import androidx.camera.camera2.internal.compat.k;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: CameraCaptureSessionCompat.java */
@X
/* renamed from: androidx.camera.camera2.internal.compat.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C19986b {

    /* renamed from: a, reason: collision with root package name */
    public final k f23132a;

    /* compiled from: CameraCaptureSessionCompat.java */
    /* renamed from: androidx.camera.camera2.internal.compat.b$a */
    public interface a {
        int a(@N ArrayList arrayList, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback);

        int b(@N CaptureRequest captureRequest, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback);
    }

    /* compiled from: CameraCaptureSessionCompat.java */
    @X
    /* renamed from: androidx.camera.camera2.internal.compat.b$b, reason: collision with other inner class name */
    public static final class C1530b extends CameraCaptureSession.CaptureCallback {

        /* renamed from: a, reason: collision with root package name */
        public final CameraCaptureSession.CaptureCallback f23133a;

        /* renamed from: b, reason: collision with root package name */
        public final Executor f23134b;

        public C1530b(@N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback) {
            this.f23134b = executor;
            this.f23133a = captureCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        @X
        public final void onCaptureBufferLost(@N final CameraCaptureSession cameraCaptureSession, @N final CaptureRequest captureRequest, @N final Surface surface, final long j12) {
            this.f23134b.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.f
                @Override // java.lang.Runnable
                public final void run() {
                    C19985a.c.a(this.f23151b.f23133a, cameraCaptureSession, captureRequest, surface, j12);
                }
            });
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(@N CameraCaptureSession cameraCaptureSession, @N CaptureRequest captureRequest, @N TotalCaptureResult totalCaptureResult) {
            this.f23134b.execute(new RunnableC19988d(this, cameraCaptureSession, captureRequest, totalCaptureResult, 0));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(@N CameraCaptureSession cameraCaptureSession, @N CaptureRequest captureRequest, @N CaptureFailure captureFailure) {
            this.f23134b.execute(new RunnableC19988d(this, cameraCaptureSession, captureRequest, captureFailure, 2));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureProgressed(@N CameraCaptureSession cameraCaptureSession, @N CaptureRequest captureRequest, @N CaptureResult captureResult) {
            this.f23134b.execute(new RunnableC19988d(this, cameraCaptureSession, captureRequest, captureResult, 1));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(@N CameraCaptureSession cameraCaptureSession, int i12) {
            this.f23134b.execute(new g(this, cameraCaptureSession, i12, 0));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(@N final CameraCaptureSession cameraCaptureSession, final int i12, final long j12) {
            this.f23134b.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f23147b.f23133a.onCaptureSequenceCompleted(cameraCaptureSession, i12, j12);
                }
            });
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureStarted(@N final CameraCaptureSession cameraCaptureSession, @N final CaptureRequest captureRequest, final long j12, final long j13) {
            this.f23134b.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f23137b.f23133a.onCaptureStarted(cameraCaptureSession, captureRequest, j12, j13);
                }
            });
        }
    }

    /* compiled from: CameraCaptureSessionCompat.java */
    @X
    /* renamed from: androidx.camera.camera2.internal.compat.b$c */
    public static final class c extends CameraCaptureSession.StateCallback {

        /* renamed from: a, reason: collision with root package name */
        public final CameraCaptureSession.StateCallback f23135a;

        /* renamed from: b, reason: collision with root package name */
        public final Executor f23136b;

        public c(@N Executor executor, @N CameraCaptureSession.StateCallback stateCallback) {
            this.f23136b = executor;
            this.f23135a = stateCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onActive(@N CameraCaptureSession cameraCaptureSession) {
            this.f23136b.execute(new h(this, cameraCaptureSession, 0));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        @X
        public final void onCaptureQueueEmpty(@N CameraCaptureSession cameraCaptureSession) {
            this.f23136b.execute(new h(this, cameraCaptureSession, 2));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onClosed(@N CameraCaptureSession cameraCaptureSession) {
            this.f23136b.execute(new h(this, cameraCaptureSession, 1));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onConfigureFailed(@N CameraCaptureSession cameraCaptureSession) {
            this.f23136b.execute(new h(this, cameraCaptureSession, 5));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onConfigured(@N CameraCaptureSession cameraCaptureSession) {
            this.f23136b.execute(new h(this, cameraCaptureSession, 3));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public final void onReady(@N CameraCaptureSession cameraCaptureSession) {
            this.f23136b.execute(new h(this, cameraCaptureSession, 4));
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        @X
        public final void onSurfacePrepared(@N final CameraCaptureSession cameraCaptureSession, @N final Surface surface) {
            this.f23136b.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.i
                @Override // java.lang.Runnable
                public final void run() {
                    C19985a.b.a(this.f23163b.f23135a, cameraCaptureSession, surface);
                }
            });
        }
    }

    public C19986b(@N CameraCaptureSession cameraCaptureSession, @N Handler handler) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.f23132a = new j(cameraCaptureSession, null);
        } else {
            this.f23132a = new k(cameraCaptureSession, new k.a(handler));
        }
    }

    public final int a(@N ArrayList arrayList, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback) {
        return this.f23132a.a(arrayList, executor, captureCallback);
    }

    public final int b(@N CaptureRequest captureRequest, @N Executor executor, @N CameraCaptureSession.CaptureCallback captureCallback) {
        return this.f23132a.b(captureRequest, executor, captureCallback);
    }

    @N
    public final CameraCaptureSession c() {
        return this.f23132a.f23166a;
    }
}
