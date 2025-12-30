package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ImageWriter;
import androidx.camera.core.impl.InterfaceC20092f0;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

/* compiled from: ImageAnalysisAbstractAnalyzer.java */
@j.X
/* loaded from: classes.dex */
abstract class W implements InterfaceC20092f0.a {

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public S f23675b;

    /* renamed from: c, reason: collision with root package name */
    @j.F
    public volatile int f23676c;

    /* renamed from: d, reason: collision with root package name */
    @j.F
    public volatile int f23677d;

    /* renamed from: f, reason: collision with root package name */
    public volatile boolean f23679f;

    /* renamed from: g, reason: collision with root package name */
    public volatile boolean f23680g;

    /* renamed from: h, reason: collision with root package name */
    @j.B
    public Executor f23681h;

    /* renamed from: i, reason: collision with root package name */
    @j.B
    @j.P
    public y0 f23682i;

    /* renamed from: j, reason: collision with root package name */
    @j.B
    @j.P
    public ImageWriter f23683j;

    /* renamed from: o, reason: collision with root package name */
    @j.B
    @j.k0
    @j.P
    public ByteBuffer f23688o;

    /* renamed from: p, reason: collision with root package name */
    @j.B
    @j.k0
    @j.P
    public ByteBuffer f23689p;

    /* renamed from: q, reason: collision with root package name */
    @j.B
    @j.k0
    @j.P
    public ByteBuffer f23690q;

    /* renamed from: r, reason: collision with root package name */
    @j.B
    @j.k0
    @j.P
    public ByteBuffer f23691r;

    /* renamed from: e, reason: collision with root package name */
    public volatile int f23678e = 1;

    /* renamed from: k, reason: collision with root package name */
    @j.B
    public Rect f23684k = new Rect();

    /* renamed from: l, reason: collision with root package name */
    @j.B
    public Rect f23685l = new Rect();

    /* renamed from: m, reason: collision with root package name */
    @j.B
    public Matrix f23686m = new Matrix();

    /* renamed from: n, reason: collision with root package name */
    @j.B
    public Matrix f23687n = new Matrix();

    /* renamed from: s, reason: collision with root package name */
    public final Object f23692s = new Object();

    /* renamed from: t, reason: collision with root package name */
    public boolean f23693t = true;

    @Override // androidx.camera.core.impl.InterfaceC20092f0.a
    public final void a(@j.N InterfaceC20092f0 interfaceC20092f0) {
        try {
            InterfaceC20120j0 interfaceC20120j0B = b(interfaceC20092f0);
            if (interfaceC20120j0B != null) {
                f(interfaceC20120j0B);
            }
        } catch (IllegalStateException unused) {
            C20140q0.c("ImageAnalysisAnalyzer");
        }
    }

    @j.P
    public abstract InterfaceC20120j0 b(@j.N InterfaceC20092f0 interfaceC20092f0);

    /* JADX WARN: Removed duplicated region for block: B:41:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.common.util.concurrent.D0<java.lang.Void> c(@j.N final androidx.camera.core.InterfaceC20120j0 r15) {
        /*
            Method dump skipped, instructions count: 199
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.W.c(androidx.camera.core.j0):com.google.common.util.concurrent.D0");
    }

    public abstract void d();

    @j.B
    public final void e(@j.N InterfaceC20120j0 interfaceC20120j0) {
        if (this.f23678e != 1) {
            if (this.f23678e == 2 && this.f23688o == null) {
                this.f23688o = ByteBuffer.allocateDirect(interfaceC20120j0.getHeight() * interfaceC20120j0.getWidth() * 4);
                return;
            }
            return;
        }
        if (this.f23689p == null) {
            this.f23689p = ByteBuffer.allocateDirect(interfaceC20120j0.getHeight() * interfaceC20120j0.getWidth());
        }
        this.f23689p.position(0);
        if (this.f23690q == null) {
            this.f23690q = ByteBuffer.allocateDirect((interfaceC20120j0.getHeight() * interfaceC20120j0.getWidth()) / 4);
        }
        this.f23690q.position(0);
        if (this.f23691r == null) {
            this.f23691r = ByteBuffer.allocateDirect((interfaceC20120j0.getHeight() * interfaceC20120j0.getWidth()) / 4);
        }
        this.f23691r.position(0);
    }

    public abstract void f(@j.N InterfaceC20120j0 interfaceC20120j0);

    @j.B
    public final void g(int i12, int i13, int i14, int i15) {
        int i16 = this.f23676c;
        Matrix matrix = new Matrix();
        if (i16 > 0) {
            RectF rectF = new RectF(0.0f, 0.0f, i12, i13);
            RectF rectF2 = androidx.camera.core.impl.utils.u.f24303a;
            Matrix.ScaleToFit scaleToFit = Matrix.ScaleToFit.FILL;
            matrix.setRectToRect(rectF, rectF2, scaleToFit);
            matrix.postRotate(i16);
            RectF rectF3 = new RectF(0.0f, 0.0f, i14, i15);
            Matrix matrix2 = new Matrix();
            matrix2.setRectToRect(rectF2, rectF3, scaleToFit);
            matrix.postConcat(matrix2);
        }
        RectF rectF4 = new RectF(this.f23684k);
        matrix.mapRect(rectF4);
        Rect rect = new Rect();
        rectF4.round(rect);
        this.f23685l = rect;
        this.f23687n.setConcat(this.f23686m, matrix);
    }

    @j.B
    public final void h(@j.N InterfaceC20120j0 interfaceC20120j0, @j.F int i12) {
        y0 y0Var = this.f23682i;
        if (y0Var == null) {
            return;
        }
        y0Var.e();
        int width = interfaceC20120j0.getWidth();
        int height = interfaceC20120j0.getHeight();
        int iF2 = this.f23682i.f();
        int iG2 = this.f23682i.g();
        boolean z12 = i12 == 90 || i12 == 270;
        int i13 = z12 ? height : width;
        if (!z12) {
            width = height;
        }
        this.f23682i = new y0(C20130o0.a(i13, width, iF2, iG2));
        if (this.f23678e == 1) {
            ImageWriter imageWriter = this.f23683j;
            if (imageWriter != null) {
                imageWriter.close();
            }
            this.f23683j = ImageWriter.newInstance(this.f23682i.getSurface(), this.f23682i.g());
        }
    }
}
