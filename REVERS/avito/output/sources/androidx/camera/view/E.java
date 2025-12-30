package androidx.camera.view;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.camera.view.E;
import com.google.common.util.concurrent.D0;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;
import j.j0;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* compiled from: SurfaceViewImplementation.java */
@X
/* loaded from: classes.dex */
final class E extends u {

    /* renamed from: e, reason: collision with root package name */
    public SurfaceView f25352e;

    /* renamed from: f, reason: collision with root package name */
    public final b f25353f;

    /* compiled from: SurfaceViewImplementation.java */
    @X
    public static class a {
        @InterfaceC42164u
        public static void a(@N SurfaceView surfaceView, @N Bitmap bitmap, @N PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener, @N Handler handler) {
            PixelCopy.request(surfaceView, bitmap, onPixelCopyFinishedListener, handler);
        }
    }

    /* compiled from: SurfaceViewImplementation.java */
    @X
    public class b implements SurfaceHolder.Callback {

        /* renamed from: b, reason: collision with root package name */
        @P
        public Size f25354b;

        /* renamed from: c, reason: collision with root package name */
        @P
        public K0 f25355c;

        /* renamed from: d, reason: collision with root package name */
        @P
        public K0 f25356d;

        /* renamed from: e, reason: collision with root package name */
        @P
        public n f25357e;

        /* renamed from: f, reason: collision with root package name */
        @P
        public Size f25358f;

        /* renamed from: g, reason: collision with root package name */
        public boolean f25359g = false;

        /* renamed from: h, reason: collision with root package name */
        public boolean f25360h = false;

        public b() {
        }

        @j0
        public final boolean a() {
            E e12 = E.this;
            Surface surface = e12.f25352e.getHolder().getSurface();
            if (this.f25359g || this.f25355c == null || !Objects.equals(this.f25354b, this.f25358f)) {
                return false;
            }
            C20140q0.d(3, "SurfaceViewImpl");
            n nVar = this.f25357e;
            K0 k02 = this.f25355c;
            Objects.requireNonNull(k02);
            k02.a(surface, androidx.core.content.d.getMainExecutor(e12.f25352e.getContext()), new F(nVar, 0));
            this.f25359g = true;
            e12.f25476d = true;
            e12.f();
            return true;
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceChanged(@N SurfaceHolder surfaceHolder, int i12, int i13, int i14) {
            C20140q0.d(3, "SurfaceViewImpl");
            this.f25358f = new Size(i13, i14);
            a();
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceCreated(@N SurfaceHolder surfaceHolder) {
            K0 k02;
            C20140q0.d(3, "SurfaceViewImpl");
            if (!this.f25360h || (k02 = this.f25356d) == null) {
                return;
            }
            k02.c();
            k02.f23597i.b(null);
            this.f25356d = null;
            this.f25360h = false;
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceDestroyed(@N SurfaceHolder surfaceHolder) {
            C20140q0.d(3, "SurfaceViewImpl");
            if (this.f25359g) {
                K0 k02 = this.f25355c;
                if (k02 != null) {
                    Objects.toString(k02);
                    C20140q0.d(3, "SurfaceViewImpl");
                    this.f25355c.f23599k.a();
                }
            } else {
                K0 k03 = this.f25355c;
                if (k03 != null) {
                    Objects.toString(k03);
                    C20140q0.d(3, "SurfaceViewImpl");
                    this.f25355c.c();
                }
            }
            this.f25360h = true;
            K0 k04 = this.f25355c;
            if (k04 != null) {
                this.f25356d = k04;
            }
            this.f25359g = false;
            this.f25355c = null;
            this.f25357e = null;
            this.f25358f = null;
            this.f25354b = null;
        }
    }

    public E(@N FrameLayout frameLayout, @N r rVar) {
        super(frameLayout, rVar);
        this.f25353f = new b();
    }

    @Override // androidx.camera.view.u
    @P
    public final View a() {
        return this.f25352e;
    }

    @Override // androidx.camera.view.u
    @P
    @X
    public final Bitmap b() {
        SurfaceView surfaceView = this.f25352e;
        if (surfaceView == null || surfaceView.getHolder().getSurface() == null || !this.f25352e.getHolder().getSurface().isValid()) {
            return null;
        }
        final Semaphore semaphore = new Semaphore(0);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.f25352e.getWidth(), this.f25352e.getHeight(), Bitmap.Config.ARGB_8888);
        HandlerThread handlerThread = new HandlerThread("pixelCopyRequest Thread");
        handlerThread.start();
        a.a(this.f25352e, bitmapCreateBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: androidx.camera.view.B
            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
            public final void onPixelCopyFinished(int i12) {
                if (i12 == 0) {
                    C20140q0.d(3, "SurfaceViewImpl");
                } else {
                    C20140q0.b("SurfaceViewImpl");
                }
                semaphore.release();
            }
        }, new Handler(handlerThread.getLooper()));
        try {
            try {
                if (!semaphore.tryAcquire(1, 100L, TimeUnit.MILLISECONDS)) {
                    C20140q0.b("SurfaceViewImpl");
                }
            } catch (InterruptedException unused) {
                C20140q0.c("SurfaceViewImpl");
            }
            return bitmapCreateBitmap;
        } finally {
            handlerThread.quitSafely();
        }
    }

    @Override // androidx.camera.view.u
    public final void e(@N final K0 k02, @P final n nVar) {
        SurfaceView surfaceView = this.f25352e;
        boolean zEquals = Objects.equals(this.f25473a, k02.f23590b);
        if (surfaceView == null || !zEquals) {
            Size size = k02.f23590b;
            this.f25473a = size;
            FrameLayout frameLayout = this.f25474b;
            size.getClass();
            SurfaceView surfaceView2 = new SurfaceView(frameLayout.getContext());
            this.f25352e = surfaceView2;
            surfaceView2.setLayoutParams(new FrameLayout.LayoutParams(this.f25473a.getWidth(), this.f25473a.getHeight()));
            frameLayout.removeAllViews();
            frameLayout.addView(this.f25352e);
            this.f25352e.getHolder().addCallback(this.f25353f);
        }
        Executor mainExecutor = androidx.core.content.d.getMainExecutor(this.f25352e.getContext());
        k02.f23598j.a(new Runnable() { // from class: androidx.camera.view.C
            @Override // java.lang.Runnable
            public final void run() {
                nVar.b();
            }
        }, mainExecutor);
        this.f25352e.post(new Runnable() { // from class: androidx.camera.view.D
            @Override // java.lang.Runnable
            public final void run() {
                E.b bVar = this.f25349b.f25353f;
                K0 k03 = bVar.f25355c;
                if (k03 != null) {
                    Objects.toString(k03);
                    C20140q0.d(3, "SurfaceViewImpl");
                    bVar.f25355c.c();
                }
                boolean z12 = bVar.f25360h;
                K0 k04 = k02;
                if (z12) {
                    bVar.f25360h = false;
                    k04.c();
                    k04.f23597i.b(null);
                    return;
                }
                bVar.f25355c = k04;
                bVar.f25357e = nVar;
                Size size2 = k04.f23590b;
                bVar.f25354b = size2;
                bVar.f25359g = false;
                if (bVar.a()) {
                    return;
                }
                C20140q0.d(3, "SurfaceViewImpl");
                E.this.f25352e.getHolder().setFixedSize(size2.getWidth(), size2.getHeight());
            }
        });
    }

    @Override // androidx.camera.view.u
    @N
    public final D0<Void> g() {
        return androidx.camera.core.impl.utils.futures.f.g(null);
    }

    @Override // androidx.camera.view.u
    public final void c() {
    }

    @Override // androidx.camera.view.u
    public final void d() {
    }
}
