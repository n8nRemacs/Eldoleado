package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import j.N;
import j.P;
import j.X;
import java.util.HashMap;
import java.util.Set;

/* compiled from: CameraCharacteristicsCompat.java */
@X
/* loaded from: classes.dex */
public class n {

    /* renamed from: b, reason: collision with root package name */
    @N
    public final a f23171b;

    /* renamed from: c, reason: collision with root package name */
    @N
    public final String f23172c;

    /* renamed from: a, reason: collision with root package name */
    @j.B
    @N
    public final HashMap f23170a = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    @P
    public B f23173d = null;

    /* compiled from: CameraCharacteristicsCompat.java */
    public interface a {
        @N
        Set<String> a();
    }

    public n(@N CameraCharacteristics cameraCharacteristics, @N String str) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.f23171b = new l(cameraCharacteristics);
        } else {
            this.f23171b = new m(cameraCharacteristics);
        }
        this.f23172c = str;
    }

    @P
    public final <T> T a(@N CameraCharacteristics.Key<T> key) {
        if (key.equals(CameraCharacteristics.SENSOR_ORIENTATION)) {
            return (T) ((m) this.f23171b).f23169a.get(key);
        }
        synchronized (this) {
            try {
                T t12 = (T) this.f23170a.get(key);
                if (t12 != null) {
                    return t12;
                }
                T t13 = (T) ((m) this.f23171b).f23169a.get(key);
                if (t13 != null) {
                    this.f23170a.put(key, t13);
                }
                return t13;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @N
    public final B b() {
        if (this.f23173d == null) {
            try {
                StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) a(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (streamConfigurationMap == null) {
                    throw new IllegalArgumentException("StreamConfigurationMap is null!");
                }
                this.f23173d = new B(streamConfigurationMap, new androidx.camera.camera2.internal.compat.workaround.m(this.f23172c));
            } catch (AssertionError e12) {
                throw new IllegalArgumentException(e12.getMessage());
            }
        }
        return this.f23173d;
    }

    @N
    public final CameraCharacteristics c() {
        return ((m) this.f23171b).f23169a;
    }
}
