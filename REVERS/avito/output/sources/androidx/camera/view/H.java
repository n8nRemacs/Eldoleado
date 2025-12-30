package androidx.camera.view;

import android.graphics.SurfaceTexture;
import android.view.TextureView;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.N;
import java.util.Objects;

/* compiled from: TextureViewImplementation.java */
/* loaded from: classes.dex */
class H implements TextureView.SurfaceTextureListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ I f25368b;

    /* compiled from: TextureViewImplementation.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<K0.c> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ SurfaceTexture f25369a;

        public a(SurfaceTexture surfaceTexture) {
            this.f25369a = surfaceTexture;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@N Throwable th2) {
            throw new IllegalStateException("SurfaceReleaseFuture did not complete nicely.", th2);
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(K0.c cVar) {
            androidx.core.util.z.g("Unexpected result from SurfaceRequest. Surface was provided twice.", cVar.a() != 3);
            C20140q0.d(3, "TextureViewImpl");
            this.f25369a.release();
            I i12 = H.this.f25368b;
            if (i12.f25376j != null) {
                i12.f25376j = null;
            }
        }
    }

    public H(I i12) {
        this.f25368b = i12;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(@N SurfaceTexture surfaceTexture, int i12, int i13) {
        C20140q0.d(3, "TextureViewImpl");
        I i14 = this.f25368b;
        i14.f25372f = surfaceTexture;
        if (i14.f25373g == null) {
            i14.h();
            return;
        }
        i14.f25374h.getClass();
        Objects.toString(i14.f25374h);
        C20140q0.d(3, "TextureViewImpl");
        i14.f25374h.f23599k.a();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final boolean onSurfaceTextureDestroyed(@N SurfaceTexture surfaceTexture) {
        I i12 = this.f25368b;
        i12.f25372f = null;
        D0<K0.c> d02 = i12.f25373g;
        if (d02 == null) {
            C20140q0.d(3, "TextureViewImpl");
            return true;
        }
        androidx.camera.core.impl.utils.futures.f.a(d02, new a(surfaceTexture), androidx.core.content.d.getMainExecutor(i12.f25371e.getContext()));
        i12.f25376j = surfaceTexture;
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureSizeChanged(@N SurfaceTexture surfaceTexture, int i12, int i13) {
        C20140q0.d(3, "TextureViewImpl");
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(@N SurfaceTexture surfaceTexture) {
        b.a<Void> andSet = this.f25368b.f25377k.getAndSet(null);
        if (andSet != null) {
            andSet.b(null);
        }
    }
}
