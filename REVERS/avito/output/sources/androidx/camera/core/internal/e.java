package androidx.camera.core.internal;

import androidx.camera.core.Q0;
import j.N;
import j.X;

/* compiled from: ImmutableZoomState.java */
@VY0.c
@X
/* loaded from: classes.dex */
public abstract class e implements Q0 {
    @N
    public static Q0 e() {
        return new a(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @N
    public static Q0 f(@N Q0 q02) {
        return new a(q02.b(), q02.c(), q02.d(), q02.a());
    }
}
