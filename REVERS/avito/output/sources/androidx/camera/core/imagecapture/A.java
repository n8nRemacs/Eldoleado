package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.camera.core.AbstractC20132p0;
import androidx.camera.core.C20056e0;
import androidx.camera.core.C20130o0;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProcessingUtil;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.imagecapture.C20073i;
import androidx.camera.core.imagecapture.C20080p;
import androidx.camera.core.imagecapture.t;
import androidx.camera.core.impl.w0;
import androidx.camera.core.y0;
import androidx.camera.core.z0;
import j.X;
import j.l0;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

/* compiled from: ProcessingNode.java */
@X
/* loaded from: classes.dex */
public class A implements androidx.camera.core.processing.o<a, Void> {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final Executor f23751a;

    /* renamed from: b, reason: collision with root package name */
    public C20070f f23752b;

    /* renamed from: c, reason: collision with root package name */
    public androidx.camera.core.processing.q<b, androidx.camera.core.processing.r<InterfaceC20120j0>> f23753c;

    /* renamed from: d, reason: collision with root package name */
    public androidx.camera.core.processing.q<C20080p.a, androidx.camera.core.processing.r<byte[]>> f23754d;

    /* renamed from: e, reason: collision with root package name */
    public androidx.camera.core.processing.q<C20073i.a, androidx.camera.core.processing.r<byte[]>> f23755e;

    /* renamed from: f, reason: collision with root package name */
    public androidx.camera.core.processing.q<t.a, C20056e0.m> f23756f;

    /* renamed from: g, reason: collision with root package name */
    public androidx.camera.core.processing.q<androidx.camera.core.processing.r<byte[]>, androidx.camera.core.processing.r<Bitmap>> f23757g;

    /* renamed from: h, reason: collision with root package name */
    public v f23758h;

    /* renamed from: i, reason: collision with root package name */
    public u f23759i;

    /* renamed from: j, reason: collision with root package name */
    public final w0 f23760j;

    /* renamed from: k, reason: collision with root package name */
    public final boolean f23761k;

    /* compiled from: ProcessingNode.java */
    @VY0.c
    public static abstract class a {
        public abstract androidx.camera.core.processing.l<b> a();

        public abstract int b();

        public abstract int c();
    }

    /* compiled from: ProcessingNode.java */
    @VY0.c
    public static abstract class b {
        @j.N
        public abstract InterfaceC20120j0 a();

        @j.N
        public abstract B b();
    }

    public A(@j.N Executor executor) {
        w0 w0Var = androidx.camera.core.internal.compat.quirk.b.f24337a;
        if (w0Var.b(androidx.camera.core.internal.compat.quirk.g.class) != null) {
            this.f23751a = androidx.camera.core.impl.utils.executor.c.f(executor);
        } else {
            this.f23751a = executor;
        }
        this.f23760j = w0Var;
        this.f23761k = w0Var.a(androidx.camera.core.internal.compat.quirk.e.class);
    }

    @j.N
    @l0
    public final InterfaceC20120j0 a(@j.N b bVar) {
        B b12 = bVar.b();
        androidx.camera.core.processing.r<InterfaceC20120j0> rVarJ = (androidx.camera.core.processing.r) ((y) this.f23753c).a(bVar);
        if ((rVarJ.e() == 35 || this.f23761k) && this.f23752b.f23827c == 256) {
            androidx.camera.core.processing.r rVar = (androidx.camera.core.processing.r) ((C20080p) this.f23754d).a(new C20068d(rVarJ, b12.f23765d));
            this.f23759i.getClass();
            y0 y0Var = new y0(C20130o0.a(rVar.h().getWidth(), rVar.h().getHeight(), 256, 2));
            InterfaceC20120j0 interfaceC20120j0B = ImageProcessingUtil.b(y0Var, (byte[]) rVar.c());
            y0Var.e();
            Objects.requireNonNull(interfaceC20120j0B);
            androidx.camera.core.impl.utils.i iVarD = rVar.d();
            Objects.requireNonNull(iVarD);
            Rect rectB = rVar.b();
            int iF2 = rVar.f();
            Matrix matrixG = rVar.g();
            androidx.camera.core.impl.r rVarA = rVar.a();
            androidx.camera.core.O o12 = (androidx.camera.core.O) interfaceC20120j0B;
            rVarJ = androidx.camera.core.processing.r.j(interfaceC20120j0B, iVarD, new Size(o12.getWidth(), o12.getHeight()), rectB, iF2, matrixG, rVarA);
        }
        this.f23758h.getClass();
        InterfaceC20120j0 interfaceC20120j0C = rVarJ.c();
        z0 z0Var = new z0(interfaceC20120j0C, rVarJ.h(), AbstractC20132p0.e(interfaceC20120j0C.r1().b(), interfaceC20120j0C.r1().a(), rVarJ.f(), rVarJ.g()));
        z0Var.b(rVarJ.b());
        return z0Var;
    }

    @j.N
    @l0
    public final C20056e0.m b(@j.N b bVar) throws Throwable {
        int length;
        byte b12;
        int i12 = this.f23752b.f23827c;
        androidx.core.util.z.a("On-disk capture only support JPEG output format. Output format: " + i12, i12 == 256);
        B b13 = bVar.b();
        androidx.camera.core.processing.r rVarK = (androidx.camera.core.processing.r) ((C20080p) this.f23754d).a(new C20068d((androidx.camera.core.processing.r) ((y) this.f23753c).a(bVar), b13.f23765d));
        if (androidx.camera.core.impl.utils.u.b(rVarK.b(), rVarK.h())) {
            int i13 = b13.f23765d;
            androidx.core.util.z.g(null, rVarK.e() == 256);
            ((s) this.f23757g).getClass();
            Rect rectB = rVarK.b();
            byte[] bArr = (byte[]) rVarK.c();
            try {
                Bitmap bitmapDecodeRegion = BitmapRegionDecoder.newInstance(bArr, 0, bArr.length, false).decodeRegion(rectB, new BitmapFactory.Options());
                androidx.camera.core.impl.utils.i iVarD = rVarK.d();
                Objects.requireNonNull(iVarD);
                Rect rect = new Rect(0, 0, bitmapDecodeRegion.getWidth(), bitmapDecodeRegion.getHeight());
                int iF2 = rVarK.f();
                Matrix matrixG = rVarK.g();
                RectF rectF = androidx.camera.core.impl.utils.u.f24303a;
                Matrix matrix = new Matrix(matrixG);
                matrix.postTranslate(-rectB.left, -rectB.top);
                androidx.camera.core.processing.r<Bitmap> rVarI = androidx.camera.core.processing.r.i(bitmapDecodeRegion, iVarD, rect, iF2, matrix, rVarK.a());
                androidx.camera.core.processing.q<C20073i.a, androidx.camera.core.processing.r<byte[]>> qVar = this.f23755e;
                C20065a c20065a = new C20065a(rVarI, i13);
                ((C20073i) qVar).getClass();
                androidx.camera.core.processing.r<Bitmap> rVarB = c20065a.b();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                rVarB.c().compress(Bitmap.CompressFormat.JPEG, c20065a.a(), byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                androidx.camera.core.impl.utils.i iVarD2 = rVarB.d();
                Objects.requireNonNull(iVarD2);
                rVarK = androidx.camera.core.processing.r.k(byteArray, iVarD2, rVarB.h(), rVarB.b(), rVarB.f(), rVarB.g(), rVarB.a());
            } catch (IOException e12) {
                throw new ImageCaptureException("Failed to decode JPEG.", e12);
            }
        }
        androidx.camera.core.processing.q<t.a, C20056e0.m> qVar2 = this.f23756f;
        C20056e0.l lVar = b13.f23762a;
        Objects.requireNonNull(lVar);
        C20069e c20069e = new C20069e(rVarK, lVar);
        ((t) qVar2).getClass();
        androidx.camera.core.processing.r<byte[]> rVarB2 = c20069e.b();
        C20056e0.l lVarA = c20069e.a();
        try {
            File file = lVarA.f23738a;
            String parent = file.getParent();
            StringBuilder sb2 = new StringBuilder("CameraX");
            sb2.append(UUID.randomUUID().toString());
            String name = file.getName();
            int iLastIndexOf = name.lastIndexOf(46);
            sb2.append(iLastIndexOf >= 0 ? name.substring(iLastIndexOf) : "");
            File file2 = new File(parent, sb2.toString());
            byte[] bArrC = rVarB2.c();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    if (new androidx.camera.core.internal.compat.workaround.c().f24342a) {
                        int i14 = 2;
                        while (i14 + 4 <= bArrC.length && (b12 = bArrC[i14]) == -1) {
                            int i15 = i14 + 2;
                            int i16 = ((bArrC[i15] & 255) << 8) | (bArrC[i14 + 3] & 255);
                            if (b12 == -1 && bArrC[i14 + 1] == -38) {
                                while (true) {
                                    int i17 = i15 + 2;
                                    if (i17 <= bArrC.length) {
                                        if (bArrC[i15] == -1 && bArrC[i15 + 1] == -39) {
                                            length = i17;
                                            break;
                                        }
                                        i15++;
                                    } else {
                                        length = bArrC.length;
                                        break;
                                    }
                                }
                            } else {
                                i14 += i16 + 2;
                            }
                        }
                        length = bArrC.length;
                    } else {
                        length = bArrC.length;
                    }
                    fileOutputStream.write(bArrC, 0, length);
                    fileOutputStream.close();
                    androidx.camera.core.impl.utils.i iVarD3 = rVarB2.d();
                    Objects.requireNonNull(iVarD3);
                    int iF3 = rVarB2.f();
                    try {
                        androidx.camera.core.impl.utils.i iVar = new androidx.camera.core.impl.utils.i(new androidx.exifinterface.media.a(file2.toString()));
                        iVarD3.a(iVar);
                        if (iVar.c() == 0 && iF3 != 0) {
                            iVar.d(iF3);
                        }
                        try {
                            lVarA.f23739b.getClass();
                            iVar.e();
                            try {
                                t.a(file2, lVarA.f23738a);
                                file2.delete();
                                return new C20056e0.m();
                            } catch (IOException unused) {
                                throw new ImageCaptureException("Failed to write to OutputStream.", null);
                            }
                        } catch (Throwable th2) {
                            file2.delete();
                            throw th2;
                        }
                    } catch (IOException e13) {
                        throw new ImageCaptureException("Failed to update Exif data", e13);
                    }
                } finally {
                }
            } catch (IOException e14) {
                throw new ImageCaptureException("Failed to write to temp file", e14);
            }
        } catch (IOException e15) {
            throw new ImageCaptureException("Failed to create temp file.", e15);
        }
    }
}
