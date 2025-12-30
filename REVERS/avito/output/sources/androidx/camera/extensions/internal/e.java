package androidx.camera.extensions.internal;

import androidx.camera.core.C20140q0;
import androidx.camera.extensions.impl.ExtensionVersionImpl;
import j.N;
import j.X;
import java.util.Objects;

/* compiled from: ExtensionVersion.java */
@X
/* loaded from: classes.dex */
public abstract class e {

    /* renamed from: a, reason: collision with root package name */
    public static volatile e f24632a;

    /* compiled from: ExtensionVersion.java */
    public static class a extends e {
        @Override // androidx.camera.extensions.internal.e
        public final l a() {
            return null;
        }
    }

    /* compiled from: ExtensionVersion.java */
    public static class b extends e {

        /* renamed from: c, reason: collision with root package name */
        public static ExtensionVersionImpl f24633c;

        /* renamed from: b, reason: collision with root package name */
        public final l f24634b;

        public b() throws NumberFormatException {
            if (f24633c == null) {
                f24633c = new ExtensionVersionImpl();
            }
            l lVarH = l.h(f24633c.checkApiVersion(d.a().d()));
            if (lVarH != null && d.a().b().d() == lVarH.d()) {
                this.f24634b = lVarH;
            }
            Objects.toString(this.f24634b);
            C20140q0.a("ExtenderVersion");
        }

        @Override // androidx.camera.extensions.internal.e
        public final l a() {
            return this.f24634b;
        }
    }

    public static boolean b(@N l lVar) {
        e eVar;
        if (f24632a != null) {
            eVar = f24632a;
        } else {
            synchronized (e.class) {
                if (f24632a == null) {
                    try {
                        f24632a = new b();
                    } catch (NoClassDefFoundError unused) {
                        C20140q0.d(3, "ExtenderVersion");
                        f24632a = new a();
                    }
                }
            }
            eVar = f24632a;
        }
        return eVar.a().a(((androidx.camera.extensions.internal.b) lVar).f24623d, ((androidx.camera.extensions.internal.b) lVar).f24624e) >= 0;
    }

    public abstract l a();
}
