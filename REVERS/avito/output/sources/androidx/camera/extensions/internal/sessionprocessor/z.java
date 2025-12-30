package androidx.camera.extensions.internal.sessionprocessor;

import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.ImageProcessingUtil;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.internal.utils.ImageUtil;
import j.F;
import j.N;
import j.X;

/* compiled from: YuvToJpegConverter.java */
@X
/* loaded from: classes.dex */
class z {

    /* renamed from: a, reason: collision with root package name */
    public final Surface f24653a;

    /* renamed from: c, reason: collision with root package name */
    public volatile int f24655c = 0;

    /* renamed from: b, reason: collision with root package name */
    @F
    public volatile int f24654b = 100;

    /* compiled from: YuvToJpegConverter.java */
    public static class a extends Exception {
    }

    public z(@N Surface surface) {
        this.f24653a = surface;
    }

    public final void a(@N InterfaceC20120j0 interfaceC20120j0) throws Exception {
        boolean zH2 = false;
        androidx.core.util.z.g("Input image is not expected YUV_420_888 image format", interfaceC20120j0.w() == 35);
        try {
            try {
                int i12 = this.f24654b;
                int i13 = this.f24655c;
                Surface surface = this.f24653a;
                int i14 = ImageProcessingUtil.f23579a;
                try {
                    zH2 = ImageProcessingUtil.h(ImageUtil.b(interfaceC20120j0, null, i12, i13), surface);
                } catch (ImageUtil.CodecFailedException unused) {
                    C20140q0.c("ImageProcessingUtil");
                }
                if (zH2) {
                } else {
                    throw new a("Failed to process YUV -> JPEG");
                }
            } catch (Exception e12) {
                C20140q0.c("YuvToJpegConverter");
                throw new a("Failed to process YUV -> JPEG", e12);
            }
        } finally {
            interfaceC20120j0.close();
        }
    }
}
