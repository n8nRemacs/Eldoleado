package androidx.camera.core.imagecapture;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.imagecapture.A;
import androidx.camera.core.impl.Config;
import j.X;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

/* compiled from: ProcessingInput2Packet.java */
@X
/* loaded from: classes.dex */
final class y implements androidx.camera.core.processing.q<A.b, androidx.camera.core.processing.r<InterfaceC20120j0>> {
    @j.N
    public final Object a(@j.N Object obj) throws ImageCaptureException {
        androidx.camera.core.impl.utils.i iVar;
        A.b bVar = (A.b) obj;
        InterfaceC20120j0 interfaceC20120j0A = bVar.a();
        B b12 = bVar.b();
        if (interfaceC20120j0A.w() == 256) {
            try {
                ThreadLocal<SimpleDateFormat> threadLocal = androidx.camera.core.impl.utils.i.f24276b;
                ByteBuffer byteBufferV = interfaceC20120j0A.e4()[0].v();
                byteBufferV.rewind();
                byte[] bArr = new byte[byteBufferV.capacity()];
                byteBufferV.get(bArr);
                androidx.camera.core.impl.utils.i iVar2 = new androidx.camera.core.impl.utils.i(new androidx.exifinterface.media.a(new ByteArrayInputStream(bArr)));
                interfaceC20120j0A.e4()[0].v().rewind();
                iVar = iVar2;
            } catch (IOException e12) {
                throw new ImageCaptureException("Failed to extract EXIF data.", e12);
            }
        } else {
            iVar = null;
        }
        r.f23855g.getClass();
        if (((androidx.camera.core.internal.compat.quirk.d) androidx.camera.core.internal.compat.quirk.b.f24337a.b(androidx.camera.core.internal.compat.quirk.d.class)) != null) {
            Config.a<Integer> aVar = androidx.camera.core.impl.H.f23965i;
        } else if (interfaceC20120j0A.w() == 256) {
            androidx.core.util.z.f(iVar, "JPEG image must have exif.");
            Size size = new Size(interfaceC20120j0A.getWidth(), interfaceC20120j0A.getHeight());
            int iC2 = b12.f23764c - iVar.c();
            Size size2 = androidx.camera.core.impl.utils.u.c(((iC2 % 360) + 360) % 360) ? new Size(size.getHeight(), size.getWidth()) : size;
            Matrix matrixA = androidx.camera.core.impl.utils.u.a(new RectF(0.0f, 0.0f, size.getWidth(), size.getHeight()), new RectF(0.0f, 0.0f, size2.getWidth(), size2.getHeight()), iC2, false);
            RectF rectF = new RectF(b12.f23763b);
            matrixA.mapRect(rectF);
            rectF.sort();
            Rect rect = new Rect();
            rectF.round(rect);
            int iC3 = iVar.c();
            Matrix matrix = new Matrix(b12.f23766e);
            matrix.postConcat(matrixA);
            return androidx.camera.core.processing.r.j(interfaceC20120j0A, iVar, size2, rect, iC3, matrix, ((androidx.camera.core.internal.c) interfaceC20120j0A.r1()).f24335a);
        }
        return androidx.camera.core.processing.r.j(interfaceC20120j0A, iVar, new Size(interfaceC20120j0A.getWidth(), interfaceC20120j0A.getHeight()), b12.f23763b, b12.f23764c, b12.f23766e, ((androidx.camera.core.internal.c) interfaceC20120j0A.r1()).f24335a);
    }
}
