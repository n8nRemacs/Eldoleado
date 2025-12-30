package androidx.camera.core;

import android.os.Handler;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20116x;
import androidx.camera.core.impl.InterfaceC20117y;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.internal.i;
import java.util.UUID;
import java.util.concurrent.Executor;

/* compiled from: CameraXConfig.java */
@j.X
/* loaded from: classes.dex */
public final class A implements androidx.camera.core.internal.i<C20148z> {

    /* renamed from: G, reason: collision with root package name */
    public static final Config.a<InterfaceC20117y.a> f23539G = Config.a.a(InterfaceC20117y.a.class, "camerax.core.appConfig.cameraFactoryProvider");

    /* renamed from: H, reason: collision with root package name */
    public static final Config.a<InterfaceC20116x.a> f23540H = Config.a.a(InterfaceC20116x.a.class, "camerax.core.appConfig.deviceSurfaceManagerProvider");

    /* renamed from: I, reason: collision with root package name */
    public static final Config.a<UseCaseConfigFactory.b> f23541I = Config.a.a(UseCaseConfigFactory.b.class, "camerax.core.appConfig.useCaseConfigFactoryProvider");

    /* renamed from: J, reason: collision with root package name */
    public static final Config.a<Executor> f23542J = Config.a.a(Executor.class, "camerax.core.appConfig.cameraExecutor");

    /* renamed from: K, reason: collision with root package name */
    public static final Config.a<Handler> f23543K = Config.a.a(Handler.class, "camerax.core.appConfig.schedulerHandler");

    /* renamed from: L, reason: collision with root package name */
    public static final Config.a<Integer> f23544L = Config.a.a(Integer.TYPE, "camerax.core.appConfig.minimumLoggingLevel");

    /* renamed from: M, reason: collision with root package name */
    public static final Config.a<C20144v> f23545M = Config.a.a(C20144v.class, "camerax.core.appConfig.availableCamerasLimiter");

    /* renamed from: F, reason: collision with root package name */
    public final androidx.camera.core.impl.s0 f23546F;

    /* compiled from: CameraXConfig.java */
    public static final class a implements i.a<C20148z, a> {

        /* renamed from: a, reason: collision with root package name */
        public final androidx.camera.core.impl.n0 f23547a;

        @RestrictTo
        public a() {
            Object objF;
            androidx.camera.core.impl.n0 n0VarR = androidx.camera.core.impl.n0.R();
            this.f23547a = n0VarR;
            Object objF2 = null;
            try {
                objF = n0VarR.f(androidx.camera.core.internal.i.f24362C);
            } catch (IllegalArgumentException unused) {
                objF = null;
            }
            Class cls = (Class) objF;
            if (cls != null && !cls.equals(C20148z.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            Config.a<Class<?>> aVar = androidx.camera.core.internal.i.f24362C;
            androidx.camera.core.impl.n0 n0Var = this.f23547a;
            n0Var.K(aVar, C20148z.class);
            try {
                objF2 = n0Var.f(androidx.camera.core.internal.i.f24361B);
            } catch (IllegalArgumentException unused2) {
            }
            if (objF2 == null) {
                n0Var.K(androidx.camera.core.internal.i.f24361B, C20148z.class.getCanonicalName() + "-" + UUID.randomUUID());
            }
        }
    }

    /* compiled from: CameraXConfig.java */
    public interface b {
        @j.N
        A getCameraXConfig();
    }

    public A(androidx.camera.core.impl.s0 s0Var) {
        this.f23546F = s0Var;
    }

    @Override // androidx.camera.core.impl.x0
    @j.N
    @RestrictTo
    public final Config getConfig() {
        return this.f23546F;
    }
}
