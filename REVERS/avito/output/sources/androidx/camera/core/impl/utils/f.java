package androidx.camera.core.impl.utils;

import android.os.Build;
import android.util.CloseGuard;
import j.N;
import j.X;

/* compiled from: CloseGuardHelper.java */
@X
/* loaded from: classes.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    public final b f24241a;

    /* compiled from: CloseGuardHelper.java */
    @X
    public static final class a implements b {

        /* renamed from: a, reason: collision with root package name */
        public final CloseGuard f24242a = new CloseGuard();

        @Override // androidx.camera.core.impl.utils.f.b
        public final void a(@N String str) {
            this.f24242a.open(str);
        }

        @Override // androidx.camera.core.impl.utils.f.b
        public final void b() {
            this.f24242a.warnIfOpen();
        }

        @Override // androidx.camera.core.impl.utils.f.b
        public final void close() {
            this.f24242a.close();
        }
    }

    /* compiled from: CloseGuardHelper.java */
    public interface b {
        void a(@N String str);

        void b();

        void close();
    }

    public f(b bVar) {
        this.f24241a = bVar;
    }

    @N
    public static f b() {
        return Build.VERSION.SDK_INT >= 30 ? new f(new a()) : new f(new c());
    }

    public final void a() {
        this.f24241a.close();
    }

    public final void c(@N String str) {
        this.f24241a.a(str);
    }

    public final void d() {
        this.f24241a.b();
    }

    /* compiled from: CloseGuardHelper.java */
    public static final class c implements b {
        @Override // androidx.camera.core.impl.utils.f.b
        public final void b() {
        }

        @Override // androidx.camera.core.impl.utils.f.b
        public final void close() {
        }

        @Override // androidx.camera.core.impl.utils.f.b
        public final void a(@N String str) {
        }
    }
}
