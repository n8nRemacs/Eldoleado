package androidx.camera.core;

import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;
import androidx.annotation.RestrictTo;
import androidx.camera.core.A;
import androidx.camera.core.impl.InterfaceC20116x;
import androidx.camera.core.impl.InterfaceC20117y;
import androidx.camera.core.impl.MetadataHolderService;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.core.os.C22783k;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executor;

/* compiled from: CameraX.java */
@j.K
@RestrictTo
@j.X
/* renamed from: androidx.camera.core.z, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20148z {

    /* renamed from: m, reason: collision with root package name */
    public static final Object f24596m = new Object();

    /* renamed from: n, reason: collision with root package name */
    @j.B
    public static final SparseArray<Integer> f24597n = new SparseArray<>();

    /* renamed from: c, reason: collision with root package name */
    public final A f24600c;

    /* renamed from: d, reason: collision with root package name */
    public final Executor f24601d;

    /* renamed from: e, reason: collision with root package name */
    public final Handler f24602e;

    /* renamed from: f, reason: collision with root package name */
    public InterfaceC20117y f24603f;

    /* renamed from: g, reason: collision with root package name */
    public InterfaceC20116x f24604g;

    /* renamed from: h, reason: collision with root package name */
    public UseCaseConfigFactory f24605h;

    /* renamed from: i, reason: collision with root package name */
    public Context f24606i;

    /* renamed from: j, reason: collision with root package name */
    public final com.google.common.util.concurrent.D0<Void> f24607j;

    /* renamed from: k, reason: collision with root package name */
    @j.B
    public b f24608k;

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.impl.D f24598a = new androidx.camera.core.impl.D();

    /* renamed from: b, reason: collision with root package name */
    public final Object f24599b = new Object();

    /* renamed from: l, reason: collision with root package name */
    @j.B
    public final com.google.common.util.concurrent.D0<Void> f24609l = androidx.camera.core.impl.utils.futures.f.g(null);

    /* compiled from: CameraX.java */
    /* renamed from: androidx.camera.core.z$a */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f24610a;

        static {
            int[] iArr = new int[b.values().length];
            f24610a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f24610a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f24610a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f24610a[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f24610a[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: CameraX.java */
    /* renamed from: androidx.camera.core.z$b */
    public static final class b {

        /* renamed from: b, reason: collision with root package name */
        public static final b f24611b;

        /* renamed from: c, reason: collision with root package name */
        public static final b f24612c;

        /* renamed from: d, reason: collision with root package name */
        public static final b f24613d;

        /* renamed from: e, reason: collision with root package name */
        public static final b f24614e;

        /* renamed from: f, reason: collision with root package name */
        public static final /* synthetic */ b[] f24615f;

        static {
            b bVar = new b("UNINITIALIZED", 0);
            f24611b = bVar;
            b bVar2 = new b("INITIALIZING", 1);
            f24612c = bVar2;
            b bVar3 = new b("INITIALIZING_ERROR", 2);
            f24613d = bVar3;
            b bVar4 = new b("INITIALIZED", 3);
            f24614e = bVar4;
            f24615f = new b[]{bVar, bVar2, bVar3, bVar4, new b("SHUTDOWN", 4)};
        }

        public b() {
            throw null;
        }

        public static b valueOf(String str) {
            return (b) Enum.valueOf(b.class, str);
        }

        public static b[] values() {
            return (b[]) f24615f.clone();
        }
    }

    @RestrictTo
    public C20148z(@j.N Context context) {
        A.b bVar;
        String string;
        Object objF;
        Object objF2;
        com.google.common.util.concurrent.D0<Void> d0A;
        this.f24608k = b.f24611b;
        ComponentCallbacks2 componentCallbacks2B = androidx.camera.core.impl.utils.h.b(context);
        if (componentCallbacks2B instanceof A.b) {
            bVar = (A.b) componentCallbacks2B;
        } else {
            try {
                Context contextA = androidx.camera.core.impl.utils.h.a(context);
                Bundle bundle = contextA.getPackageManager().getServiceInfo(new ComponentName(contextA, (Class<?>) MetadataHolderService.class), 640).metaData;
                string = bundle != null ? bundle.getString("androidx.camera.core.impl.MetadataHolderService.DEFAULT_CONFIG_PROVIDER") : null;
            } catch (PackageManager.NameNotFoundException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | NullPointerException | InvocationTargetException unused) {
                C20140q0.c("CameraX");
            }
            if (string == null) {
                C20140q0.b("CameraX");
                bVar = null;
            } else {
                bVar = (A.b) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
        }
        if (bVar == null) {
            throw new IllegalStateException("CameraX is not configured properly. The most likely cause is you did not include a default implementation in your build such as 'camera-camera2'.");
        }
        A cameraXConfig = bVar.getCameraXConfig();
        this.f24600c = cameraXConfig;
        try {
            objF = cameraXConfig.f23546F.f(A.f23542J);
        } catch (IllegalArgumentException unused2) {
            objF = null;
        }
        Executor executor = (Executor) objF;
        try {
            objF2 = this.f24600c.f23546F.f(A.f23543K);
        } catch (IllegalArgumentException unused3) {
            objF2 = null;
        }
        Handler handler = (Handler) objF2;
        this.f24601d = executor == null ? new r() : executor;
        if (handler == null) {
            HandlerThread handlerThread = new HandlerThread("CameraX-scheduler", 10);
            handlerThread.start();
            this.f24602e = C22783k.a(handlerThread.getLooper());
        } else {
            this.f24602e = handler;
        }
        Integer num = (Integer) this.f24600c.g(A.f23544L, null);
        synchronized (f24596m) {
            try {
                if (num != null) {
                    androidx.core.util.z.c(num.intValue(), 3, "minLogLevel", 6);
                    SparseArray<Integer> sparseArray = f24597n;
                    sparseArray.put(num.intValue(), Integer.valueOf(sparseArray.get(num.intValue()) != null ? sparseArray.get(num.intValue()).intValue() + 1 : 1));
                    if (sparseArray.size() == 0 || sparseArray.get(3) != null) {
                        C20140q0.f24513a = 3;
                    } else if (sparseArray.get(4) != null) {
                        C20140q0.f24513a = 4;
                    } else if (sparseArray.get(5) != null) {
                        C20140q0.f24513a = 5;
                    } else if (sparseArray.get(6) != null) {
                        C20140q0.f24513a = 6;
                    }
                }
            } finally {
            }
        }
        synchronized (this.f24599b) {
            androidx.core.util.z.g("CameraX.initInternal() should only be called once per instance", this.f24608k == b.f24611b);
            this.f24608k = b.f24612c;
            d0A = androidx.concurrent.futures.b.a(new C20145w(0, this, context));
        }
        this.f24607j = d0A;
    }

    public final void a() {
        synchronized (this.f24599b) {
            this.f24608k = b.f24614e;
        }
    }
}
