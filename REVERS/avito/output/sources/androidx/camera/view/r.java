package androidx.camera.view;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.camera.view.PreviewView;
import j.P;
import j.X;
import j.k0;

/* compiled from: PreviewTransformation.java */
@X
/* loaded from: classes.dex */
final class r {

    /* renamed from: a, reason: collision with root package name */
    public Size f25460a;

    /* renamed from: b, reason: collision with root package name */
    public Rect f25461b;

    /* renamed from: c, reason: collision with root package name */
    public int f25462c;

    /* renamed from: d, reason: collision with root package name */
    public Matrix f25463d;

    /* renamed from: e, reason: collision with root package name */
    public int f25464e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f25465f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f25466g;

    /* renamed from: h, reason: collision with root package name */
    public PreviewView.ScaleType f25467h = PreviewView.ScaleType.FILL_CENTER;

    /* compiled from: PreviewTransformation.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f25468a;

        static {
            int[] iArr = new int[PreviewView.ScaleType.values().length];
            f25468a = iArr;
            try {
                iArr[4] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25468a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25468a[5] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25468a[2] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f25468a[3] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f25468a[0] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @P
    public final Matrix a(Size size, int i12) {
        if (!f()) {
            return null;
        }
        Matrix matrix = new Matrix();
        c(size, i12).invert(matrix);
        Matrix matrix2 = new Matrix();
        matrix2.setRectToRect(new RectF(0.0f, 0.0f, this.f25460a.getWidth(), this.f25460a.getHeight()), new RectF(0.0f, 0.0f, 1.0f, 1.0f), Matrix.ScaleToFit.FILL);
        matrix.postConcat(matrix2);
        return matrix;
    }

    public final Size b() {
        return androidx.camera.core.impl.utils.u.c(this.f25462c) ? new Size(this.f25461b.height(), this.f25461b.width()) : new Size(this.f25461b.width(), this.f25461b.height());
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.graphics.Matrix c(android.util.Size r8, int r9) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.view.r.c(android.util.Size, int):android.graphics.Matrix");
    }

    @k0
    public final Matrix d() {
        androidx.core.util.z.g(null, f());
        RectF rectF = new RectF(0.0f, 0.0f, this.f25460a.getWidth(), this.f25460a.getHeight());
        return androidx.camera.core.impl.utils.u.a(rectF, rectF, !this.f25466g ? this.f25462c : -androidx.camera.core.impl.utils.e.b(this.f25464e), false);
    }

    public final RectF e(Size size, int i12) {
        androidx.core.util.z.g(null, f());
        Matrix matrixC = c(size, i12);
        RectF rectF = new RectF(0.0f, 0.0f, this.f25460a.getWidth(), this.f25460a.getHeight());
        matrixC.mapRect(rectF);
        return rectF;
    }

    public final boolean f() {
        return (this.f25461b == null || this.f25460a == null || !(!this.f25466g || this.f25464e != -1)) ? false : true;
    }
}
