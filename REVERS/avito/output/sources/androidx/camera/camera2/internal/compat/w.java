package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.util.ArrayMap;
import androidx.camera.camera2.internal.compat.A;
import androidx.camera.camera2.internal.compat.C19985a;
import j.N;
import j.X;
import j.a0;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: CameraManagerCompat.java */
@X
/* loaded from: classes.dex */
public final class w {

    /* renamed from: a, reason: collision with root package name */
    public final b f23241a;

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public final ArrayMap f23242b = new ArrayMap(4);

    /* compiled from: CameraManagerCompat.java */
    @X
    public static final class a extends CameraManager.AvailabilityCallback {

        /* renamed from: a, reason: collision with root package name */
        public final Executor f23243a;

        /* renamed from: b, reason: collision with root package name */
        public final CameraManager.AvailabilityCallback f23244b;

        /* renamed from: c, reason: collision with root package name */
        public final Object f23245c = new Object();

        /* renamed from: d, reason: collision with root package name */
        @j.B
        public boolean f23246d = false;

        public a(@N Executor executor, @N CameraManager.AvailabilityCallback availabilityCallback) {
            this.f23243a = executor;
            this.f23244b = availabilityCallback;
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        @X
        public final void onCameraAccessPrioritiesChanged() {
            synchronized (this.f23245c) {
                try {
                    if (!this.f23246d) {
                        this.f23243a.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.v
                            @Override // java.lang.Runnable
                            public final void run() {
                                C19985a.e.a(this.f23240b.f23244b);
                            }
                        });
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public final void onCameraAvailable(@N String str) {
            synchronized (this.f23245c) {
                try {
                    if (!this.f23246d) {
                        this.f23243a.execute(new u(this, str, 0));
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public final void onCameraUnavailable(@N String str) {
            synchronized (this.f23245c) {
                try {
                    if (!this.f23246d) {
                        this.f23243a.execute(new u(this, str, 1));
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    /* compiled from: CameraManagerCompat.java */
    public interface b {
        @a0
        void a(@N String str, @N Executor executor, @N CameraDevice.StateCallback stateCallback);

        @N
        CameraCharacteristics b(@N String str);

        @N
        String[] c();

        void d(@N Executor executor, @N CameraManager.AvailabilityCallback availabilityCallback);

        @N
        Set<Set<String>> e();

        void f(@N CameraManager.AvailabilityCallback availabilityCallback);
    }

    public w(b bVar) {
        this.f23241a = bVar;
    }

    @N
    public static w a(@N Context context, @N Handler handler) {
        int i12 = Build.VERSION.SDK_INT;
        return new w(i12 >= 30 ? new z(context, null) : i12 >= 29 ? new y(context, null) : i12 >= 28 ? new x(context, null) : new A(context, new A.a(handler)));
    }

    @N
    public final n b(@N String str) {
        n nVar;
        synchronized (this.f23242b) {
            nVar = (n) this.f23242b.get(str);
            if (nVar == null) {
                try {
                    n nVar2 = new n(this.f23241a.b(str), str);
                    this.f23242b.put(str, nVar2);
                    nVar = nVar2;
                } catch (AssertionError e12) {
                    throw new CameraAccessExceptionCompat(e12.getMessage(), e12);
                }
            }
        }
        return nVar;
    }
}
