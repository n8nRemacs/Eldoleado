package androidx.camera.core.processing;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.internal.RunnableC20014j;
import androidx.camera.core.C0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.ImageProcessingUtil;
import androidx.concurrent.futures.b;
import j.N;
import j.P;
import j.X;
import j.k0;
import j.l0;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.C42829l0;

/* compiled from: DefaultSurfaceProcessor.java */
@X
/* loaded from: classes.dex */
public class k implements B, SurfaceTexture.OnFrameAvailableListener {

    /* renamed from: b, reason: collision with root package name */
    public final p f24443b;

    /* renamed from: c, reason: collision with root package name */
    @k0
    public final HandlerThread f24444c;

    /* renamed from: d, reason: collision with root package name */
    public final Executor f24445d;

    /* renamed from: e, reason: collision with root package name */
    @k0
    public final Handler f24446e;

    /* renamed from: f, reason: collision with root package name */
    public final AtomicBoolean f24447f;

    /* renamed from: g, reason: collision with root package name */
    public final float[] f24448g;

    /* renamed from: h, reason: collision with root package name */
    public final float[] f24449h;

    /* renamed from: i, reason: collision with root package name */
    public final LinkedHashMap f24450i;

    /* renamed from: j, reason: collision with root package name */
    public int f24451j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f24452k;

    /* renamed from: l, reason: collision with root package name */
    public final ArrayList f24453l;

    /* compiled from: DefaultSurfaceProcessor.java */
    public static class a {
    }

    /* compiled from: DefaultSurfaceProcessor.java */
    @VY0.c
    public static abstract class b {
        @N
        public abstract b.a<Void> a();

        @j.F
        public abstract int b();

        @j.F
        public abstract int c();
    }

    public k(@N androidx.camera.core.F f12) throws ExecutionException, InterruptedException {
        s sVar = s.f24475a;
        this.f24447f = new AtomicBoolean(false);
        this.f24448g = new float[16];
        this.f24449h = new float[16];
        this.f24450i = new LinkedHashMap();
        this.f24451j = 0;
        this.f24452k = false;
        this.f24453l = new ArrayList();
        HandlerThread handlerThread = new HandlerThread("GL Thread");
        this.f24444c = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.f24446e = handler;
        this.f24445d = androidx.camera.core.impl.utils.executor.c.e(handler);
        this.f24443b = new p();
        try {
            try {
                androidx.concurrent.futures.b.a(new FX0.b(this, f12, sVar, 1)).get();
            } catch (InterruptedException | ExecutionException e12) {
                e = e12;
                e = e instanceof ExecutionException ? e.getCause() : e;
                if (!(e instanceof RuntimeException)) {
                    throw new IllegalStateException("Failed to create DefaultSurfaceProcessor", e);
                }
                throw ((RuntimeException) e);
            }
        } catch (RuntimeException e13) {
            e();
            throw e13;
        }
    }

    @l0
    public final void a() throws IOException {
        if (this.f24452k && this.f24451j == 0) {
            LinkedHashMap linkedHashMap = this.f24450i;
            Iterator it = linkedHashMap.keySet().iterator();
            while (it.hasNext()) {
                ((C0) it.next()).close();
            }
            Iterator it2 = this.f24453l.iterator();
            while (it2.hasNext()) {
                ((b) it2.next()).a().d(new Exception("Failed to snapshot: DefaultSurfaceProcessor is released."));
            }
            linkedHashMap.clear();
            p pVar = this.f24443b;
            if (pVar.f24462a.getAndSet(false)) {
                pVar.c();
                pVar.q();
            }
            this.f24444c.quit();
        }
    }

    public final void b(@N Runnable runnable, @N Runnable runnable2) {
        try {
            this.f24445d.execute(new Me.m(this, runnable2, runnable, 2));
        } catch (RejectedExecutionException unused) {
            C20140q0.d(5, "DefaultSurfaceProcessor");
            runnable2.run();
        }
    }

    public final void c(@N Exception exc) {
        ArrayList arrayList = this.f24453l;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((b) it.next()).a().d(exc);
        }
        arrayList.clear();
    }

    @N
    public final Bitmap d(@N Size size, @N float[] fArr, int i12) {
        float[] fArr2 = new float[16];
        Matrix.setIdentityM(fArr2, 0);
        androidx.camera.core.impl.utils.p.b(fArr2);
        androidx.camera.core.impl.utils.p.a(fArr2, i12);
        Matrix.multiplyMM(fArr2, 0, fArr2, 0, fArr, 0);
        Size sizeF = androidx.camera.core.impl.utils.u.f(size, i12);
        p pVar = this.f24443b;
        pVar.getClass();
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(sizeF.getHeight() * sizeF.getWidth() * 4);
        androidx.core.util.z.a("ByteBuffer capacity is not equal to width * height * 4.", byteBufferAllocateDirect.capacity() == (sizeF.getHeight() * sizeF.getWidth()) * 4);
        androidx.core.util.z.a("ByteBuffer is not direct.", byteBufferAllocateDirect.isDirect());
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        p.b("glGenTextures");
        int i13 = iArr[0];
        GLES20.glActiveTexture(33985);
        p.b("glActiveTexture");
        GLES20.glBindTexture(3553, i13);
        p.b("glBindTexture");
        GLES20.glTexImage2D(3553, 0, 6407, sizeF.getWidth(), sizeF.getHeight(), 0, 6407, 5121, null);
        p.b("glTexImage2D");
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        int[] iArr2 = new int[1];
        GLES20.glGenFramebuffers(1, iArr2, 0);
        p.b("glGenFramebuffers");
        int i14 = iArr2[0];
        GLES20.glBindFramebuffer(36160, i14);
        p.b("glBindFramebuffer");
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, i13, 0);
        p.b("glFramebufferTexture2D");
        GLES20.glActiveTexture(33984);
        p.b("glActiveTexture");
        GLES20.glBindTexture(36197, pVar.f24470i);
        p.b("glBindTexture");
        pVar.f24469h = null;
        GLES20.glViewport(0, 0, sizeF.getWidth(), sizeF.getHeight());
        GLES20.glScissor(0, 0, sizeF.getWidth(), sizeF.getHeight());
        GLES20.glUniformMatrix4fv(pVar.f24472k, 1, false, fArr2, 0);
        p.b("glUniformMatrix4fv");
        GLES20.glDrawArrays(5, 0, 4);
        p.b("glDrawArrays");
        GLES20.glReadPixels(0, 0, sizeF.getWidth(), sizeF.getHeight(), 6408, 5121, byteBufferAllocateDirect);
        p.b("glReadPixels");
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glDeleteTextures(1, new int[]{i13}, 0);
        p.b("glDeleteTextures");
        GLES20.glDeleteFramebuffers(1, new int[]{i14}, 0);
        p.b("glDeleteFramebuffers");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, pVar.f24470i);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(sizeF.getWidth(), sizeF.getHeight(), Bitmap.Config.ARGB_8888);
        byteBufferAllocateDirect.rewind();
        ImageProcessingUtil.e(bitmapCreateBitmap, byteBufferAllocateDirect, sizeF.getWidth() * 4);
        return bitmapCreateBitmap;
    }

    public final void e() {
        if (this.f24447f.getAndSet(true)) {
            return;
        }
        b(new x(this, 4), new RunnableC20014j());
    }

    @l0
    public final void f(@P C42829l0<Surface, Size, float[]> c42829l0) throws IOException {
        ArrayList arrayList = this.f24453l;
        if (arrayList.isEmpty()) {
            return;
        }
        if (c42829l0 == null) {
            c(new Exception("Failed to snapshot: no JPEG Surface."));
            return;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                Iterator it = arrayList.iterator();
                int iC2 = -1;
                int iB2 = -1;
                Bitmap bitmapD = null;
                byte[] byteArray = null;
                while (it.hasNext()) {
                    b bVar = (b) it.next();
                    if (iC2 != bVar.c() || bitmapD == null) {
                        iC2 = bVar.c();
                        if (bitmapD != null) {
                            bitmapD.recycle();
                        }
                        bitmapD = d(c42829l0.f406872c, c42829l0.f406873d, iC2);
                        iB2 = -1;
                    }
                    if (iB2 != bVar.b()) {
                        byteArrayOutputStream.reset();
                        iB2 = bVar.b();
                        bitmapD.compress(Bitmap.CompressFormat.JPEG, iB2, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                    }
                    Surface surface = c42829l0.f406871b;
                    Objects.requireNonNull(byteArray);
                    ImageProcessingUtil.h(byteArray, surface);
                    bVar.a().b(null);
                    it.remove();
                }
                byteArrayOutputStream.close();
            } finally {
            }
        } catch (IOException e12) {
            c(e12);
        }
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public final void onFrameAvailable(@N SurfaceTexture surfaceTexture) throws IOException {
        if (this.f24447f.get()) {
            return;
        }
        surfaceTexture.updateTexImage();
        float[] fArr = this.f24448g;
        surfaceTexture.getTransformMatrix(fArr);
        C42829l0<Surface, Size, float[]> c42829l0 = null;
        for (Map.Entry entry : this.f24450i.entrySet()) {
            Surface surface = (Surface) entry.getValue();
            C0 c02 = (C0) entry.getKey();
            float[] fArr2 = this.f24449h;
            c02.X0(fArr2, fArr);
            if (c02.w() == 34) {
                try {
                    this.f24443b.s(surfaceTexture.getTimestamp(), fArr2, surface);
                } catch (RuntimeException unused) {
                    C20140q0.c("DefaultSurfaceProcessor");
                }
            } else {
                androidx.core.util.z.g("Unsupported format: " + c02.w(), c02.w() == 256);
                androidx.core.util.z.g("Only one JPEG output is supported.", c42829l0 == null);
                c42829l0 = new C42829l0<>(surface, c02.getSize(), (float[]) fArr2.clone());
            }
        }
        try {
            f(c42829l0);
        } catch (RuntimeException e12) {
            c(e12);
        }
    }
}
