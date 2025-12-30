package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;
import androidx.annotation.RestrictTo;
import androidx.camera.core.ImageProcessingUtil;
import androidx.camera.core.InterfaceC20060g0;
import androidx.camera.core.InterfaceC20120j0;
import j.X;
import java.nio.ByteBuffer;
import java.util.Objects;

/* compiled from: RgbaImageProxy.java */
@RestrictTo
@X
/* loaded from: classes.dex */
public final class G implements InterfaceC20120j0 {

    /* renamed from: b, reason: collision with root package name */
    public final Object f23785b;

    /* renamed from: c, reason: collision with root package name */
    public final int f23786c;

    /* renamed from: d, reason: collision with root package name */
    public final int f23787d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public final Rect f23788e;

    /* renamed from: f, reason: collision with root package name */
    @j.B
    @j.P
    public InterfaceC20120j0.a[] f23789f;

    /* renamed from: g, reason: collision with root package name */
    @j.N
    public final InterfaceC20060g0 f23790g;

    public G(@j.N androidx.camera.core.processing.r<Bitmap> rVar) {
        Bitmap bitmapC = rVar.c();
        Rect rectB = rVar.b();
        int iF2 = rVar.f();
        long jA2 = rVar.a().a();
        androidx.core.util.z.a("Only accept Bitmap with ARGB_8888 format for now.", bitmapC.getConfig() == Bitmap.Config.ARGB_8888);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(bitmapC.getAllocationByteCount());
        ImageProcessingUtil.d(bitmapC, byteBufferAllocateDirect, bitmapC.getRowBytes());
        byteBufferAllocateDirect.rewind();
        int width = bitmapC.getWidth();
        int height = bitmapC.getHeight();
        this.f23785b = new Object();
        this.f23786c = width;
        this.f23787d = height;
        this.f23788e = rectB;
        this.f23790g = new F(jA2, iF2);
        byteBufferAllocateDirect.rewind();
        this.f23789f = new InterfaceC20120j0.a[]{new E(width * 4, byteBufferAllocateDirect)};
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public final Rect V2() {
        Rect rect;
        synchronized (this.f23785b) {
            a();
            rect = this.f23788e;
        }
        return rect;
    }

    public final void a() {
        synchronized (this.f23785b) {
            androidx.core.util.z.g("The image is closed.", this.f23789f != null);
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        synchronized (this.f23785b) {
            a();
            this.f23789f = null;
        }
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public final InterfaceC20120j0.a[] e4() {
        InterfaceC20120j0.a[] aVarArr;
        synchronized (this.f23785b) {
            a();
            InterfaceC20120j0.a[] aVarArr2 = this.f23789f;
            Objects.requireNonNull(aVarArr2);
            aVarArr = aVarArr2;
        }
        return aVarArr;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public final int getHeight() {
        int i12;
        synchronized (this.f23785b) {
            a();
            i12 = this.f23787d;
        }
        return i12;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @androidx.camera.core.G
    @j.P
    public final Image getImage() {
        synchronized (this.f23785b) {
            a();
        }
        return null;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public final int getWidth() {
        int i12;
        synchronized (this.f23785b) {
            a();
            i12 = this.f23786c;
        }
        return i12;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    @j.N
    public final InterfaceC20060g0 r1() {
        InterfaceC20060g0 interfaceC20060g0;
        synchronized (this.f23785b) {
            a();
            interfaceC20060g0 = this.f23790g;
        }
        return interfaceC20060g0;
    }

    @Override // androidx.camera.core.InterfaceC20120j0
    public final int w() {
        synchronized (this.f23785b) {
            a();
        }
        return 1;
    }
}
