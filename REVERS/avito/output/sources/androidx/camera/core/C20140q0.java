package androidx.camera.core;

import android.util.Log;
import androidx.annotation.RestrictTo;

/* compiled from: Logger.java */
@RestrictTo
@j.X
/* renamed from: androidx.camera.core.q0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20140q0 {

    /* renamed from: a, reason: collision with root package name */
    public static int f24513a = 3;

    public static void a(@j.N String str) {
        d(3, str);
    }

    public static void b(@j.N String str) {
        d(6, str);
    }

    public static void c(@j.N String str) {
        d(6, str);
    }

    public static boolean d(int i12, @j.N String str) {
        return f24513a <= i12 || Log.isLoggable(str, i12);
    }
}
