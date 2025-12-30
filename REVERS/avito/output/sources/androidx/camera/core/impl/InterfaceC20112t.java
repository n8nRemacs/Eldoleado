package androidx.camera.core.impl;

import androidx.camera.core.impl.Config;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: CameraConfig.java */
@j.X
/* renamed from: androidx.camera.core.impl.t, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20112t extends x0 {

    /* renamed from: a, reason: collision with root package name */
    public static final Config.a<UseCaseConfigFactory> f24174a = Config.a.a(UseCaseConfigFactory.class, "camerax.core.camera.useCaseConfigFactory");

    /* renamed from: b, reason: collision with root package name */
    public static final Config.a<X> f24175b = Config.a.a(X.class, "camerax.core.camera.compatibilityId");

    /* renamed from: c, reason: collision with root package name */
    public static final Config.a<Integer> f24176c = Config.a.a(Integer.class, "camerax.core.camera.useCaseCombinationRequiredRule");

    /* renamed from: d, reason: collision with root package name */
    public static final Config.a<C0> f24177d = Config.a.a(C0.class, "camerax.core.camera.SessionProcessor");

    /* compiled from: CameraConfig.java */
    /* renamed from: androidx.camera.core.impl.t$a */
    public interface a<B> {
    }

    /* compiled from: CameraConfig.java */
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.camera.core.impl.t$b */
    public @interface b {
    }

    static {
        Config.a.a(Boolean.class, "camerax.core.camera.isZslDisabled");
    }

    @j.N
    default UseCaseConfigFactory B() {
        return (UseCaseConfigFactory) g(f24174a, UseCaseConfigFactory.f24053a);
    }

    @j.N
    X J();

    default int M() {
        return ((Integer) g(f24176c, 0)).intValue();
    }

    @j.P
    default C0 w() {
        return (C0) g(f24177d, null);
    }
}
