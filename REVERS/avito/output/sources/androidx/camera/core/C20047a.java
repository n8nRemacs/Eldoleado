package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import androidx.camera.core.InterfaceC20120j0;
import java.nio.ByteBuffer;

/* compiled from: AndroidImageProxy.java */
@j.X
/* renamed from: androidx.camera.core.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20047a implements InterfaceC20120j0 {

    /* renamed from: b, reason: collision with root package name */
    public final Image f23703b;

    /* renamed from: c, reason: collision with root package name */
    public final C1532a[] f23704c;

    /* renamed from: d, reason: collision with root package name */
    public final InterfaceC20060g0 f23705d;

    /* compiled from: AndroidImageProxy.java */
    /* renamed from: androidx.camera.core.a$a, reason: collision with other inner class name */
    public static final class C1532a implements InterfaceC20120j0.a {

        /* renamed from: a, reason: collision with root package name */
        public final Image.Plane f23706a;

        public C1532a(Image.Plane plane) {
            this.f23706a = plane;
        }

        @Override // androidx.camera.core.InterfaceC20120j0.a
        @j.N
        public final ByteBuffer v() {
            return this.f23706a.getBuffer();
        }

        @Override // androidx.camera.core.InterfaceC20120j0.a
        public final int w() {
            return this.f23706a.getRowStride();
        }

        @Override // androidx.camera.core.InterfaceC20120j0.a
        public final int x() {
            return this.f23706a.getPixelStride();
        }
    }

    public C20047a(@j.N Image image) {
        this.f23703b = image;
        Image.Plane[] planes = image.getPlanes();
        if (planes != null) {
            this.f23704c = new C1532a[planes.length];
            for (int i12 = 0; i12 < planes.length; i12++) {
                this.f23704c[i12] = new C1532a(planes[i12]);
            }
        } else {
            this.f23704c = new C1532a[0];
        }
        this.f23705d = new C20063i(androidx.camera.core.impl.J0.f23986b, image.getTimestamp(), 0, new Matrix());
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public final Rect V2() {
        return this.f23703b.getCropRect();
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.f23703b.close();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public final InterfaceC20120j0.a[] e4() {
        return this.f23704c;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public final int getHeight() {
        return this.f23703b.getHeight();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @G
    public final Image getImage() {
        return this.f23703b;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public final int getWidth() {
        return this.f23703b.getWidth();
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public final InterfaceC20060g0 r1() {
        return this.f23705d;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public final int w() {
        return this.f23703b.getFormat();
    }
}
