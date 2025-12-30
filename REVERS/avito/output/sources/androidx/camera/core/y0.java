package androidx.camera.core;

import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.InterfaceC20092f0;
import java.util.concurrent.Executor;

/* compiled from: SafeCloseImageReaderProxy.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public class y0 implements InterfaceC20092f0 {

    /* renamed from: d, reason: collision with root package name */
    @j.B
    public final InterfaceC20092f0 f24592d;

    /* renamed from: e, reason: collision with root package name */
    @j.P
    public final Surface f24593e;

    /* renamed from: f, reason: collision with root package name */
    public androidx.camera.core.imagecapture.N f24594f;

    /* renamed from: a, reason: collision with root package name */
    public final Object f24589a = new Object();

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public int f24590b = 0;

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public boolean f24591c = false;

    /* renamed from: g, reason: collision with root package name */
    public final Z f24595g = new Z(this, 1);

    public y0(@j.N InterfaceC20092f0 interfaceC20092f0) {
        this.f24592d = interfaceC20092f0;
        this.f24593e = interfaceC20092f0.getSurface();
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 a() {
        A0 a02;
        synchronized (this.f24589a) {
            InterfaceC20120j0 interfaceC20120j0A = this.f24592d.a();
            if (interfaceC20120j0A != null) {
                this.f24590b++;
                a02 = new A0(interfaceC20120j0A);
                a02.a(this.f24595g);
            } else {
                a02 = null;
            }
        }
        return a02;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 b() {
        A0 a02;
        synchronized (this.f24589a) {
            InterfaceC20120j0 interfaceC20120j0B = this.f24592d.b();
            if (interfaceC20120j0B != null) {
                this.f24590b++;
                a02 = new A0(interfaceC20120j0B);
                a02.a(this.f24595g);
            } else {
                a02 = null;
            }
        }
        return a02;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void c() {
        synchronized (this.f24589a) {
            this.f24592d.c();
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void close() {
        synchronized (this.f24589a) {
            try {
                Surface surface = this.f24593e;
                if (surface != null) {
                    surface.release();
                }
                this.f24592d.close();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void d(@j.N InterfaceC20092f0.a aVar, @j.N Executor executor) {
        synchronized (this.f24589a) {
            this.f24592d.d(new C20145w(1, this, aVar), executor);
        }
    }

    public final void e() {
        synchronized (this.f24589a) {
            try {
                this.f24591c = true;
                this.f24592d.c();
                if (this.f24590b == 0) {
                    close();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int f() {
        int iF2;
        synchronized (this.f24589a) {
            iF2 = this.f24592d.f();
        }
        return iF2;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int g() {
        int iG2;
        synchronized (this.f24589a) {
            iG2 = this.f24592d.g();
        }
        return iG2;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getHeight() {
        int height;
        synchronized (this.f24589a) {
            height = this.f24592d.getHeight();
        }
        return height;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final Surface getSurface() {
        Surface surface;
        synchronized (this.f24589a) {
            surface = this.f24592d.getSurface();
        }
        return surface;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getWidth() {
        int width;
        synchronized (this.f24589a) {
            width = this.f24592d.getWidth();
        }
        return width;
    }
}
