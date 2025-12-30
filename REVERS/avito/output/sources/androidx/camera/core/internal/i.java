package androidx.camera.core.internal;

import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.x0;
import j.N;
import j.P;
import j.X;

/* compiled from: TargetConfig.java */
@X
/* loaded from: classes.dex */
public interface i<T> extends x0 {

    /* renamed from: B, reason: collision with root package name */
    @N
    @RestrictTo
    public static final Config.a<String> f24361B = Config.a.a(String.class, "camerax.core.target.name");

    /* renamed from: C, reason: collision with root package name */
    @N
    @RestrictTo
    public static final Config.a<Class<?>> f24362C = Config.a.a(Class.class, "camerax.core.target.class");

    /* compiled from: TargetConfig.java */
    public interface a<T, B> {
    }

    @N
    default String I() {
        return (String) f(f24361B);
    }

    @P
    default String j(@P String str) {
        return (String) g(f24361B, str);
    }
}
