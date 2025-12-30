package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import androidx.camera.camera2.internal.compat.o;
import androidx.camera.camera2.internal.compat.w;
import j.N;
import j.P;
import j.X;
import j.a0;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: CameraManagerCompatBaseImpl.java */
@X
/* loaded from: classes.dex */
class A implements w.b {

    /* renamed from: a, reason: collision with root package name */
    public final CameraManager f23121a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f23122b;

    /* compiled from: CameraManagerCompatBaseImpl.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @j.B
        public final HashMap f23123a = new HashMap();

        /* renamed from: b, reason: collision with root package name */
        public final Handler f23124b;

        public a(@N Handler handler) {
            this.f23124b = handler;
        }
    }

    public A(@N Context context, @P Object obj) {
        this.f23121a = (CameraManager) context.getSystemService("camera");
        this.f23122b = obj;
    }

    @Override // androidx.camera.camera2.internal.compat.w.b
    @a0
    public void a(@N String str, @N Executor executor, @N CameraDevice.StateCallback stateCallback) throws CameraAccessException, CameraAccessExceptionCompat {
        executor.getClass();
        stateCallback.getClass();
        try {
            this.f23121a.openCamera(str, new o.b(executor, stateCallback), ((a) this.f23122b).f23124b);
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.w.b
    @N
    public CameraCharacteristics b(@N String str) throws CameraAccessExceptionCompat {
        try {
            return this.f23121a.getCameraCharacteristics(str);
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.w.b
    @N
    public final String[] c() throws CameraAccessExceptionCompat {
        try {
            return this.f23121a.getCameraIdList();
        } catch (CameraAccessException e12) {
            throw new CameraAccessExceptionCompat(e12);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.w.b
    public void d(@N Executor executor, @N CameraManager.AvailabilityCallback availabilityCallback) {
        w.a aVar;
        a aVar2 = (a) this.f23122b;
        synchronized (aVar2.f23123a) {
            try {
                aVar = (w.a) aVar2.f23123a.get(availabilityCallback);
                if (aVar == null) {
                    aVar = new w.a(executor, availabilityCallback);
                    aVar2.f23123a.put(availabilityCallback, aVar);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.f23121a.registerAvailabilityCallback(aVar, aVar2.f23124b);
    }

    @Override // androidx.camera.camera2.internal.compat.w.b
    @N
    public Set<Set<String>> e() {
        return Collections.emptySet();
    }

    @Override // androidx.camera.camera2.internal.compat.w.b
    public void f(@N CameraManager.AvailabilityCallback availabilityCallback) {
        w.a aVar;
        if (availabilityCallback != null) {
            a aVar2 = (a) this.f23122b;
            synchronized (aVar2.f23123a) {
                aVar = (w.a) aVar2.f23123a.remove(availabilityCallback);
            }
        } else {
            aVar = null;
        }
        if (aVar != null) {
            synchronized (aVar.f23245c) {
                aVar.f23246d = true;
            }
        }
        this.f23121a.unregisterAvailabilityCallback(aVar);
    }
}
