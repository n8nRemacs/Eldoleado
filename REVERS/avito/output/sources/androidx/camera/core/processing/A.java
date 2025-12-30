package androidx.camera.core.processing;

import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.Matrix;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.C0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.CameraInternal;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.InterfaceC42148d;
import j.N;
import j.P;
import j.X;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: SurfaceOutputImpl.java */
@X
/* loaded from: classes.dex */
final class A implements C0 {

    /* renamed from: c, reason: collision with root package name */
    @N
    public final Surface f24393c;

    /* renamed from: d, reason: collision with root package name */
    public final int f24394d;

    /* renamed from: e, reason: collision with root package name */
    @N
    public final Size f24395e;

    /* renamed from: f, reason: collision with root package name */
    @N
    public final float[] f24396f;

    /* renamed from: g, reason: collision with root package name */
    @j.B
    @P
    public h f24397g;

    /* renamed from: h, reason: collision with root package name */
    @j.B
    @P
    public Executor f24398h;

    /* renamed from: k, reason: collision with root package name */
    @N
    public final D0<Void> f24401k;

    /* renamed from: l, reason: collision with root package name */
    public b.a<Void> f24402l;

    /* renamed from: m, reason: collision with root package name */
    @P
    public final CameraInternal f24403m;

    /* renamed from: b, reason: collision with root package name */
    public final Object f24392b = new Object();

    /* renamed from: i, reason: collision with root package name */
    @j.B
    public boolean f24399i = false;

    /* renamed from: j, reason: collision with root package name */
    @j.B
    public boolean f24400j = false;

    public A(@N Surface surface, int i12, @N Size size, @N Size size2, @N Rect rect, int i13, boolean z12, @P CameraInternal cameraInternal) {
        float[] fArr = new float[16];
        this.f24396f = fArr;
        float[] fArr2 = new float[16];
        this.f24393c = surface;
        this.f24394d = i12;
        this.f24395e = size;
        Rect rect2 = new Rect(rect);
        this.f24403m = cameraInternal;
        Matrix.setIdentityM(fArr, 0);
        androidx.camera.core.impl.utils.p.b(fArr);
        androidx.camera.core.impl.utils.p.a(fArr, i13);
        if (z12) {
            Matrix.translateM(fArr, 0, 1.0f, 0.0f, 0.0f);
            Matrix.scaleM(fArr, 0, -1.0f, 1.0f, 1.0f);
        }
        Size sizeF = androidx.camera.core.impl.utils.u.f(size2, i13);
        float f12 = 0;
        android.graphics.Matrix matrixA = androidx.camera.core.impl.utils.u.a(new RectF(f12, f12, size2.getWidth(), size2.getHeight()), new RectF(f12, f12, sizeF.getWidth(), sizeF.getHeight()), i13, z12);
        RectF rectF = new RectF(rect2);
        matrixA.mapRect(rectF);
        float width = rectF.left / sizeF.getWidth();
        float height = ((sizeF.getHeight() - rectF.height()) - rectF.top) / sizeF.getHeight();
        float fWidth = rectF.width() / sizeF.getWidth();
        float fHeight = rectF.height() / sizeF.getHeight();
        Matrix.translateM(fArr, 0, width, height, 0.0f);
        Matrix.scaleM(fArr, 0, fWidth, fHeight, 1.0f);
        Matrix.setIdentityM(fArr2, 0);
        androidx.camera.core.impl.utils.p.b(fArr2);
        if (cameraInternal != null) {
            androidx.core.util.z.g("Camera has no transform.", cameraInternal.j());
            androidx.camera.core.impl.utils.p.a(fArr2, cameraInternal.getCameraInfo().f());
            if (cameraInternal.d()) {
                Matrix.translateM(fArr2, 0, 1.0f, 0.0f, 0.0f);
                Matrix.scaleM(fArr2, 0, -1.0f, 1.0f, 1.0f);
            }
        }
        Matrix.invertM(fArr2, 0, fArr2, 0);
        Matrix.multiplyMM(fArr, 0, fArr2, 0, fArr, 0);
        this.f24401k = androidx.concurrent.futures.b.a(new y(this, 1));
    }

    @Override // androidx.camera.core.C0
    @InterfaceC42148d
    public final void X0(@N float[] fArr, @N float[] fArr2) {
        Matrix.multiplyMM(fArr, 0, fArr2, 0, this.f24396f, 0);
    }

    public final void a() {
        Executor executor;
        h hVar;
        AtomicReference atomicReference = new AtomicReference();
        synchronized (this.f24392b) {
            try {
                if (this.f24398h == null || (hVar = this.f24397g) == null) {
                    this.f24399i = true;
                } else if (!this.f24400j) {
                    atomicReference.set(hVar);
                    executor = this.f24398h;
                    this.f24399i = false;
                }
                executor = null;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (executor != null) {
            try {
                executor.execute(new RunnableC20138f(2, this, atomicReference));
            } catch (RejectedExecutionException unused) {
                C20140q0.d(3, "SurfaceOutputImpl");
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    @InterfaceC42148d
    public final void close() {
        synchronized (this.f24392b) {
            try {
                if (!this.f24400j) {
                    this.f24400j = true;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.f24402l.b(null);
    }

    @Override // androidx.camera.core.C0
    @N
    public final Size getSize() {
        return this.f24395e;
    }

    @Override // androidx.camera.core.C0
    @N
    public final Surface n1(@N Executor executor, @N h hVar) {
        boolean z12;
        synchronized (this.f24392b) {
            this.f24398h = executor;
            this.f24397g = hVar;
            z12 = this.f24399i;
        }
        if (z12) {
            a();
        }
        return this.f24393c;
    }

    @Override // androidx.camera.core.C0
    public final int w() {
        return this.f24394d;
    }
}
