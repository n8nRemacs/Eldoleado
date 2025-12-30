package androidx.camera.core;

import android.media.Image;
import android.media.ImageReader;
import android.view.Surface;
import androidx.camera.core.impl.InterfaceC20092f0;
import java.util.concurrent.Executor;

/* compiled from: AndroidImageReaderProxy.java */
@j.X
/* renamed from: androidx.camera.core.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20053d implements InterfaceC20092f0 {

    /* renamed from: a, reason: collision with root package name */
    @j.B
    public final ImageReader f23722a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f23723b = new Object();

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public boolean f23724c = true;

    public C20053d(ImageReader imageReader) {
        this.f23722a = imageReader;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 a() {
        Image imageAcquireNextImage;
        synchronized (this.f23723b) {
            try {
                imageAcquireNextImage = this.f23722a.acquireNextImage();
            } catch (RuntimeException e12) {
                if (!"ImageReaderContext is not initialized".equals(e12.getMessage())) {
                    throw e12;
                }
                imageAcquireNextImage = null;
            }
            if (imageAcquireNextImage == null) {
                return null;
            }
            return new C20047a(imageAcquireNextImage);
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 b() {
        Image imageAcquireLatestImage;
        synchronized (this.f23723b) {
            try {
                imageAcquireLatestImage = this.f23722a.acquireLatestImage();
            } catch (RuntimeException e12) {
                if (!"ImageReaderContext is not initialized".equals(e12.getMessage())) {
                    throw e12;
                }
                imageAcquireLatestImage = null;
            }
            if (imageAcquireLatestImage == null) {
                return null;
            }
            return new C20047a(imageAcquireLatestImage);
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void c() {
        synchronized (this.f23723b) {
            this.f23724c = true;
            this.f23722a.setOnImageAvailableListener(null, null);
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void close() {
        synchronized (this.f23723b) {
            this.f23722a.close();
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void d(@j.N final InterfaceC20092f0.a aVar, @j.N final Executor executor) {
        synchronized (this.f23723b) {
            this.f23724c = false;
            this.f23722a.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() { // from class: androidx.camera.core.b
                @Override // android.media.ImageReader.OnImageAvailableListener
                public final void onImageAvailable(ImageReader imageReader) {
                    C20053d c20053d = this.f23709a;
                    Executor executor2 = executor;
                    InterfaceC20092f0.a aVar2 = aVar;
                    synchronized (c20053d.f23723b) {
                        try {
                            if (!c20053d.f23724c) {
                                executor2.execute(new RunnableC20051c(0, c20053d, aVar2));
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }
            }, androidx.camera.core.impl.utils.o.a());
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int f() {
        int imageFormat;
        synchronized (this.f23723b) {
            imageFormat = this.f23722a.getImageFormat();
        }
        return imageFormat;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int g() {
        int maxImages;
        synchronized (this.f23723b) {
            maxImages = this.f23722a.getMaxImages();
        }
        return maxImages;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getHeight() {
        int height;
        synchronized (this.f23723b) {
            height = this.f23722a.getHeight();
        }
        return height;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final Surface getSurface() {
        Surface surface;
        synchronized (this.f23723b) {
            surface = this.f23722a.getSurface();
        }
        return surface;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getWidth() {
        int width;
        synchronized (this.f23723b) {
            width = this.f23722a.getWidth();
        }
        return width;
    }
}
