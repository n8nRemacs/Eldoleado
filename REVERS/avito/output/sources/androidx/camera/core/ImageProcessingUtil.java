package androidx.camera.core;

import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageWriter;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.InterfaceC20092f0;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.nio.ByteBuffer;
import java.util.Locale;

@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class ImageProcessingUtil {

    /* renamed from: a, reason: collision with root package name */
    public static int f23579a;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class a {

        /* renamed from: b, reason: collision with root package name */
        public static final a f23580b;

        /* renamed from: c, reason: collision with root package name */
        public static final a f23581c;

        /* renamed from: d, reason: collision with root package name */
        public static final /* synthetic */ a[] f23582d;

        /* JADX INFO: Fake field, exist only in values array */
        a EF0;

        static {
            a aVar = new a(GrsBaseInfo.CountryCodeSource.UNKNOWN, 0);
            a aVar2 = new a("SUCCESS", 1);
            f23580b = aVar2;
            a aVar3 = new a("ERROR_CONVERSION", 2);
            f23581c = aVar3;
            f23582d = new a[]{aVar, aVar2, aVar3};
        }

        public a() {
            throw null;
        }

        public static a valueOf(String str) {
            return (a) Enum.valueOf(a.class, str);
        }

        public static a[] values() {
            return (a[]) f23582d.clone();
        }
    }

    static {
        System.loadLibrary("image_processing_util_jni");
    }

    public static void a(@j.N InterfaceC20120j0 interfaceC20120j0) {
        if (!f(interfaceC20120j0)) {
            C20140q0.b("ImageProcessingUtil");
            return;
        }
        int width = interfaceC20120j0.getWidth();
        int height = interfaceC20120j0.getHeight();
        int iW2 = interfaceC20120j0.e4()[0].w();
        int iW3 = interfaceC20120j0.e4()[1].w();
        int iW4 = interfaceC20120j0.e4()[2].w();
        int iX2 = interfaceC20120j0.e4()[0].x();
        int iX3 = interfaceC20120j0.e4()[1].x();
        int iNativeShiftPixel = nativeShiftPixel(interfaceC20120j0.e4()[0].v(), iW2, interfaceC20120j0.e4()[1].v(), iW3, interfaceC20120j0.e4()[2].v(), iW4, iX2, iX3, width, height, iX2, iX3, iX3);
        a aVar = a.f23581c;
        if ((iNativeShiftPixel != 0 ? aVar : a.f23580b) == aVar) {
            C20140q0.b("ImageProcessingUtil");
        }
    }

    @j.P
    public static InterfaceC20120j0 b(@j.N y0 y0Var, @j.N byte[] bArr) {
        androidx.core.util.z.b(y0Var.f() == 256);
        bArr.getClass();
        Surface surface = y0Var.getSurface();
        surface.getClass();
        if (nativeWriteJpegToSurface(bArr, surface) != 0) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        InterfaceC20120j0 interfaceC20120j0B = y0Var.b();
        if (interfaceC20120j0B == null) {
            C20140q0.b("ImageProcessingUtil");
        }
        return interfaceC20120j0B;
    }

    @j.P
    public static InterfaceC20120j0 c(@j.N InterfaceC20120j0 interfaceC20120j0, @j.N InterfaceC20092f0 interfaceC20092f0, @j.P ByteBuffer byteBuffer, @j.F int i12, boolean z12) {
        String str;
        if (!f(interfaceC20120j0)) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        System.currentTimeMillis();
        if (i12 != 0 && i12 != 90 && i12 != 180 && i12 != 270) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        Surface surface = interfaceC20092f0.getSurface();
        int width = interfaceC20120j0.getWidth();
        int height = interfaceC20120j0.getHeight();
        int iW2 = interfaceC20120j0.e4()[0].w();
        int iW3 = interfaceC20120j0.e4()[1].w();
        int iW4 = interfaceC20120j0.e4()[2].w();
        int iX2 = interfaceC20120j0.e4()[0].x();
        int iX3 = interfaceC20120j0.e4()[1].x();
        int iNativeConvertAndroid420ToABGR = nativeConvertAndroid420ToABGR(interfaceC20120j0.e4()[0].v(), iW2, interfaceC20120j0.e4()[1].v(), iW3, interfaceC20120j0.e4()[2].v(), iW4, iX2, iX3, surface, byteBuffer, width, height, z12 ? iX2 : 0, z12 ? iX3 : 0, z12 ? iX3 : 0, i12);
        a aVar = a.f23581c;
        if ((iNativeConvertAndroid420ToABGR != 0 ? aVar : a.f23580b) == aVar) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        if (Log.isLoggable("MH", 3)) {
            Locale locale = Locale.US;
            System.currentTimeMillis();
            str = "ImageProcessingUtil";
            C20140q0.d(3, str);
            f23579a++;
        } else {
            str = "ImageProcessingUtil";
        }
        InterfaceC20120j0 interfaceC20120j0B = interfaceC20092f0.b();
        if (interfaceC20120j0B == null) {
            C20140q0.b(str);
            return null;
        }
        A0 a02 = new A0(interfaceC20120j0B);
        a02.a(new C20062h0(interfaceC20120j0B, interfaceC20120j0, 0));
        return a02;
    }

    public static void d(@j.N Bitmap bitmap, @j.N ByteBuffer byteBuffer, int i12) {
        nativeCopyBetweenByteBufferAndBitmap(bitmap, byteBuffer, bitmap.getRowBytes(), i12, bitmap.getWidth(), bitmap.getHeight(), false);
    }

    public static void e(@j.N Bitmap bitmap, @j.N ByteBuffer byteBuffer, int i12) {
        nativeCopyBetweenByteBufferAndBitmap(bitmap, byteBuffer, i12, bitmap.getRowBytes(), bitmap.getWidth(), bitmap.getHeight(), true);
    }

    public static boolean f(@j.N InterfaceC20120j0 interfaceC20120j0) {
        return interfaceC20120j0.w() == 35 && interfaceC20120j0.e4().length == 3;
    }

    @j.P
    public static InterfaceC20120j0 g(@j.N InterfaceC20120j0 interfaceC20120j0, @j.N InterfaceC20092f0 interfaceC20092f0, @j.N ImageWriter imageWriter, @j.N ByteBuffer byteBuffer, @j.N ByteBuffer byteBuffer2, @j.N ByteBuffer byteBuffer3, @j.F int i12) {
        a aVar;
        a aVar2;
        if (!f(interfaceC20120j0)) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        if (i12 != 0 && i12 != 90 && i12 != 180 && i12 != 270) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        a aVar3 = a.f23581c;
        if (i12 > 0) {
            int width = interfaceC20120j0.getWidth();
            int height = interfaceC20120j0.getHeight();
            int iW2 = interfaceC20120j0.e4()[0].w();
            int iW3 = interfaceC20120j0.e4()[1].w();
            int iW4 = interfaceC20120j0.e4()[2].w();
            int iX2 = interfaceC20120j0.e4()[1].x();
            Image imageDequeueInputImage = imageWriter.dequeueInputImage();
            if (imageDequeueInputImage == null) {
                aVar2 = aVar3;
            } else {
                aVar2 = aVar3;
                if (nativeRotateYUV(interfaceC20120j0.e4()[0].v(), iW2, interfaceC20120j0.e4()[1].v(), iW3, interfaceC20120j0.e4()[2].v(), iW4, iX2, imageDequeueInputImage.getPlanes()[0].getBuffer(), imageDequeueInputImage.getPlanes()[0].getRowStride(), imageDequeueInputImage.getPlanes()[0].getPixelStride(), imageDequeueInputImage.getPlanes()[1].getBuffer(), imageDequeueInputImage.getPlanes()[1].getRowStride(), imageDequeueInputImage.getPlanes()[1].getPixelStride(), imageDequeueInputImage.getPlanes()[2].getBuffer(), imageDequeueInputImage.getPlanes()[2].getRowStride(), imageDequeueInputImage.getPlanes()[2].getPixelStride(), byteBuffer, byteBuffer2, byteBuffer3, width, height, i12) != 0) {
                    aVar3 = aVar2;
                } else {
                    imageWriter.queueInputImage(imageDequeueInputImage);
                    aVar3 = a.f23580b;
                }
            }
            aVar = aVar2;
        } else {
            aVar = aVar3;
            aVar3 = aVar;
        }
        if (aVar3 == aVar) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        InterfaceC20120j0 interfaceC20120j0B = interfaceC20092f0.b();
        if (interfaceC20120j0B == null) {
            C20140q0.b("ImageProcessingUtil");
            return null;
        }
        A0 a02 = new A0(interfaceC20120j0B);
        a02.a(new C20062h0(interfaceC20120j0B, interfaceC20120j0, 1));
        return a02;
    }

    public static boolean h(@j.N byte[] bArr, @j.N Surface surface) {
        bArr.getClass();
        surface.getClass();
        if (nativeWriteJpegToSurface(bArr, surface) == 0) {
            return true;
        }
        C20140q0.b("ImageProcessingUtil");
        return false;
    }

    private static native int nativeConvertAndroid420ToABGR(@j.N ByteBuffer byteBuffer, int i12, @j.N ByteBuffer byteBuffer2, int i13, @j.N ByteBuffer byteBuffer3, int i14, int i15, int i16, @j.P Surface surface, @j.P ByteBuffer byteBuffer4, int i17, int i18, int i19, int i22, int i23, int i24);

    private static native int nativeCopyBetweenByteBufferAndBitmap(Bitmap bitmap, ByteBuffer byteBuffer, int i12, int i13, int i14, int i15, boolean z12);

    private static native int nativeRotateYUV(@j.N ByteBuffer byteBuffer, int i12, @j.N ByteBuffer byteBuffer2, int i13, @j.N ByteBuffer byteBuffer3, int i14, int i15, @j.N ByteBuffer byteBuffer4, int i16, int i17, @j.N ByteBuffer byteBuffer5, int i18, int i19, @j.N ByteBuffer byteBuffer6, int i22, int i23, @j.N ByteBuffer byteBuffer7, @j.N ByteBuffer byteBuffer8, @j.N ByteBuffer byteBuffer9, int i24, int i25, int i26);

    private static native int nativeShiftPixel(@j.N ByteBuffer byteBuffer, int i12, @j.N ByteBuffer byteBuffer2, int i13, @j.N ByteBuffer byteBuffer3, int i14, int i15, int i16, int i17, int i18, int i19, int i22, int i23);

    private static native int nativeWriteJpegToSurface(@j.N byte[] bArr, @j.N Surface surface);
}
