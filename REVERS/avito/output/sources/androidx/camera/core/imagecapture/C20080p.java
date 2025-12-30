package androidx.camera.core.imagecapture;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.w0;
import androidx.camera.core.internal.utils.ImageUtil;
import j.X;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: Image2JpegBytes.java */
@X
/* renamed from: androidx.camera.core.imagecapture.p, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20080p implements androidx.camera.core.processing.q<a, androidx.camera.core.processing.r<byte[]>> {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.internal.compat.workaround.d f23854a;

    /* compiled from: Image2JpegBytes.java */
    @VY0.c
    /* renamed from: androidx.camera.core.imagecapture.p$a */
    public static abstract class a {
        public abstract int a();

        public abstract androidx.camera.core.processing.r<InterfaceC20120j0> b();
    }

    public C20080p(@j.N w0 w0Var) {
        this.f23854a = new androidx.camera.core.internal.compat.workaround.d(w0Var);
    }

    public static androidx.camera.core.processing.r c(@j.N a aVar) throws ImageCaptureException {
        androidx.camera.core.processing.r<InterfaceC20120j0> rVarB = aVar.b();
        InterfaceC20120j0 interfaceC20120j0C = rVarB.c();
        Rect rectB = rVarB.b();
        try {
            byte[] bArrB = ImageUtil.b(interfaceC20120j0C, rectB, aVar.a(), rVarB.f());
            try {
                androidx.camera.core.impl.utils.i iVar = new androidx.camera.core.impl.utils.i(new androidx.exifinterface.media.a(new ByteArrayInputStream(bArrB)));
                Size size = new Size(rectB.width(), rectB.height());
                Rect rect = new Rect(0, 0, rectB.width(), rectB.height());
                int iF2 = rVarB.f();
                Matrix matrixG = rVarB.g();
                RectF rectF = androidx.camera.core.impl.utils.u.f24303a;
                Matrix matrix = new Matrix(matrixG);
                matrix.postTranslate(-rectB.left, -rectB.top);
                return androidx.camera.core.processing.r.k(bArrB, iVar, size, rect, iF2, matrix, rVarB.a());
            } catch (IOException e12) {
                throw new ImageCaptureException("Failed to extract Exif from YUV-generated JPEG", e12);
            }
        } catch (ImageUtil.CodecFailedException e13) {
            throw new ImageCaptureException("Failed to encode the image to JPEG.", e13);
        }
    }

    @j.N
    public final Object a(@j.N Object obj) throws Exception {
        androidx.camera.core.processing.r<byte[]> rVarC;
        a aVar = (a) obj;
        try {
            int iE2 = aVar.b().e();
            if (iE2 == 35) {
                rVarC = c(aVar);
            } else {
                if (iE2 != 256) {
                    throw new IllegalArgumentException("Unexpected format: " + iE2);
                }
                rVarC = b(aVar);
            }
            return rVarC;
        } finally {
            aVar.b().c().close();
        }
    }

    public final androidx.camera.core.processing.r<byte[]> b(@j.N a aVar) {
        byte[] bArrCopyOfRange;
        byte b12;
        androidx.camera.core.processing.r<InterfaceC20120j0> rVarB = aVar.b();
        InterfaceC20120j0 interfaceC20120j0C = rVarB.c();
        int i12 = 0;
        if (this.f23854a.f24343a == null) {
            ByteBuffer byteBufferV = interfaceC20120j0C.e4()[0].v();
            bArrCopyOfRange = new byte[byteBufferV.capacity()];
            byteBufferV.rewind();
            byteBufferV.get(bArrCopyOfRange);
        } else {
            ByteBuffer byteBufferV2 = interfaceC20120j0C.e4()[0].v();
            int iCapacity = byteBufferV2.capacity();
            byte[] bArr = new byte[iCapacity];
            byteBufferV2.rewind();
            byteBufferV2.get(bArr);
            int i13 = 2;
            for (int i14 = 2; i14 + 4 <= iCapacity && (b12 = bArr[i14]) == -1; i14 += (((bArr[i14 + 2] & 255) << 8) | (bArr[i14 + 3] & 255)) + 2) {
                if (b12 == -1 && bArr[i14 + 1] == -38) {
                    break;
                }
            }
            while (true) {
                int i15 = i13 + 1;
                if (i15 > iCapacity) {
                    i12 = -1;
                    break;
                }
                if (bArr[i13] == -1 && bArr[i15] == -40) {
                    i12 = i13;
                    break;
                }
                i13 = i15;
            }
            bArrCopyOfRange = i12 != -1 ? Arrays.copyOfRange(bArr, i12, byteBufferV2.limit()) : bArr;
        }
        byte[] bArr2 = bArrCopyOfRange;
        androidx.camera.core.impl.utils.i iVarD = rVarB.d();
        Objects.requireNonNull(iVarD);
        return androidx.camera.core.processing.r.k(bArr2, iVarD, rVarB.h(), rVarB.b(), rVarB.f(), rVarB.g(), rVarB.a());
    }
}
