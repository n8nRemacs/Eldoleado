package androidx.camera.core.impl.utils;

import android.os.Looper;
import androidx.core.util.z;
import j.X;

/* compiled from: Threads.java */
@X
/* loaded from: classes.dex */
public final class t {
    public static void a() {
        z.g("Not in application's main thread", b());
    }

    public static boolean b() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
