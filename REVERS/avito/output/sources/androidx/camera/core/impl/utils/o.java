package androidx.camera.core.impl.utils;

import android.os.Handler;
import android.os.Looper;
import androidx.core.os.C22783k;
import j.N;
import j.X;

/* compiled from: MainThreadAsyncHandler.java */
@X
/* loaded from: classes.dex */
public final class o {

    /* renamed from: a, reason: collision with root package name */
    public static volatile Handler f24302a;

    @N
    public static Handler a() {
        if (f24302a != null) {
            return f24302a;
        }
        synchronized (o.class) {
            try {
                if (f24302a == null) {
                    f24302a = C22783k.a(Looper.getMainLooper());
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return f24302a;
    }
}
