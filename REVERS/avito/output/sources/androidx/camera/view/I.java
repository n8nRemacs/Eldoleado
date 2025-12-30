package androidx.camera.view;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: TextureViewImplementation.java */
@X
/* loaded from: classes.dex */
final class I extends u {

    /* renamed from: e, reason: collision with root package name */
    public TextureView f25371e;

    /* renamed from: f, reason: collision with root package name */
    public SurfaceTexture f25372f;

    /* renamed from: g, reason: collision with root package name */
    public D0<K0.c> f25373g;

    /* renamed from: h, reason: collision with root package name */
    public K0 f25374h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f25375i;

    /* renamed from: j, reason: collision with root package name */
    public SurfaceTexture f25376j;

    /* renamed from: k, reason: collision with root package name */
    public AtomicReference<b.a<Void>> f25377k;

    /* renamed from: l, reason: collision with root package name */
    @P
    public n f25378l;

    @Override // androidx.camera.view.u
    @P
    public final View a() {
        return this.f25371e;
    }

    @Override // androidx.camera.view.u
    @P
    public final Bitmap b() {
        TextureView textureView = this.f25371e;
        if (textureView == null || !textureView.isAvailable()) {
            return null;
        }
        return this.f25371e.getBitmap();
    }

    @Override // androidx.camera.view.u
    public final void c() {
        if (!this.f25375i || this.f25376j == null) {
            return;
        }
        SurfaceTexture surfaceTexture = this.f25371e.getSurfaceTexture();
        SurfaceTexture surfaceTexture2 = this.f25376j;
        if (surfaceTexture != surfaceTexture2) {
            this.f25371e.setSurfaceTexture(surfaceTexture2);
            this.f25376j = null;
            this.f25375i = false;
        }
    }

    @Override // androidx.camera.view.u
    public final void d() {
        this.f25375i = true;
    }

    @Override // androidx.camera.view.u
    public final void e(@N K0 k02, @P n nVar) {
        Size size = k02.f23590b;
        this.f25473a = size;
        this.f25378l = nVar;
        FrameLayout frameLayout = this.f25474b;
        size.getClass();
        TextureView textureView = new TextureView(frameLayout.getContext());
        this.f25371e = textureView;
        textureView.setLayoutParams(new FrameLayout.LayoutParams(this.f25473a.getWidth(), this.f25473a.getHeight()));
        this.f25371e.setSurfaceTextureListener(new H(this));
        frameLayout.removeAllViews();
        frameLayout.addView(this.f25371e);
        K0 k03 = this.f25374h;
        if (k03 != null) {
            k03.c();
        }
        this.f25374h = k02;
        Executor mainExecutor = androidx.core.content.d.getMainExecutor(this.f25371e.getContext());
        k02.f23598j.a(new t(1, this, k02), mainExecutor);
        h();
    }

    @Override // androidx.camera.view.u
    @N
    public final D0<Void> g() {
        return androidx.concurrent.futures.b.a(new m(this));
    }

    public final void h() {
        SurfaceTexture surfaceTexture;
        Size size = this.f25473a;
        if (size == null || (surfaceTexture = this.f25372f) == null || this.f25374h == null) {
            return;
        }
        surfaceTexture.setDefaultBufferSize(size.getWidth(), this.f25473a.getHeight());
        final Surface surface = new Surface(this.f25372f);
        final K0 k02 = this.f25374h;
        final D0<K0.c> d0A = androidx.concurrent.futures.b.a(new C20198j(1, this, surface));
        this.f25373g = d0A;
        d0A.N(new Runnable() { // from class: androidx.camera.view.G
            @Override // java.lang.Runnable
            public final void run() {
                I i12 = this.f25364b;
                i12.getClass();
                C20140q0.d(3, "TextureViewImpl");
                n nVar = i12.f25378l;
                if (nVar != null) {
                    nVar.b();
                    i12.f25378l = null;
                }
                surface.release();
                if (i12.f25373g == d0A) {
                    i12.f25373g = null;
                }
                if (i12.f25374h == k02) {
                    i12.f25374h = null;
                }
            }
        }, androidx.core.content.d.getMainExecutor(this.f25371e.getContext()));
        this.f25476d = true;
        f();
    }
}
