package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.C20003d0;
import androidx.camera.camera2.internal.Q;
import androidx.concurrent.futures.b;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.b0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class C19982b0 implements Q.a, b.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C20003d0 f23111b;

    public void a() {
        C20003d0 c20003d0 = this.f23111b;
        synchronized (c20003d0.f23283a) {
            try {
                if (c20003d0.f23294l == C20003d0.d.f23307f) {
                    c20003d0.l(c20003d0.f23289g);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        String str;
        C20003d0 c20003d0 = this.f23111b;
        synchronized (c20003d0.f23283a) {
            androidx.core.util.z.g("Release completer expected to be null", c20003d0.f23296n == null);
            c20003d0.f23296n = aVar;
            str = "Release[session=" + c20003d0 + "]";
        }
        return str;
    }
}
