package androidx.camera.core;

import android.graphics.Rect;
import android.util.Size;
import androidx.annotation.RestrictTo;

/* compiled from: SettableImageProxy.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class z0 extends O {

    /* renamed from: e, reason: collision with root package name */
    public final Object f24616e;

    /* renamed from: f, reason: collision with root package name */
    public final InterfaceC20060g0 f24617f;

    /* renamed from: g, reason: collision with root package name */
    @j.B
    @j.P
    public Rect f24618g;

    /* renamed from: h, reason: collision with root package name */
    public final int f24619h;

    /* renamed from: i, reason: collision with root package name */
    public final int f24620i;

    public z0(@j.N InterfaceC20120j0 interfaceC20120j0, @j.P Size size, @j.N InterfaceC20060g0 interfaceC20060g0) {
        super(interfaceC20120j0);
        this.f24616e = new Object();
        if (size == null) {
            this.f24619h = this.f23633c.getWidth();
            this.f24620i = this.f23633c.getHeight();
        } else {
            this.f24619h = size.getWidth();
            this.f24620i = size.getHeight();
        }
        this.f24617f = interfaceC20060g0;
    }

    @Override // androidx.camera.core.O, androidx.camera.core.InterfaceC20120j0
    @j.N
    public final Rect V2() {
        synchronized (this.f24616e) {
            try {
                if (this.f24618g == null) {
                    return new Rect(0, 0, this.f24619h, this.f24620i);
                }
                return new Rect(this.f24618g);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void b(@j.P Rect rect) {
        if (rect != null) {
            Rect rect2 = new Rect(rect);
            if (!rect2.intersect(0, 0, this.f24619h, this.f24620i)) {
                rect2.setEmpty();
            }
            rect = rect2;
        }
        synchronized (this.f24616e) {
            this.f24618g = rect;
        }
    }

    @Override // androidx.camera.core.O, androidx.camera.core.InterfaceC20120j0
    public final int getHeight() {
        return this.f24620i;
    }

    @Override // androidx.camera.core.O, androidx.camera.core.InterfaceC20120j0
    public final int getWidth() {
        return this.f24619h;
    }

    @Override // androidx.camera.core.O, androidx.camera.core.InterfaceC20120j0
    @j.N
    public final InterfaceC20060g0 r1() {
        return this.f24617f;
    }
}
