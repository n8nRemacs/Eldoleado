package androidx.camera.core.internal.utils;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Rational;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.utils.ExifData;
import androidx.camera.core.impl.utils.l;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import j.F;
import j.N;
import j.P;
import j.X;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

@X
/* loaded from: classes.dex */
public final class ImageUtil {

    public static final class CodecFailedException extends Exception {

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        public static final class FailureType {

            /* renamed from: b, reason: collision with root package name */
            public static final /* synthetic */ FailureType[] f24365b = {new FailureType("ENCODE_FAILED", 0), new FailureType("DECODE_FAILED", 1), new FailureType(GrsBaseInfo.CountryCodeSource.UNKNOWN, 2)};

            /* JADX INFO: Fake field, exist only in values array */
            FailureType EF5;

            public FailureType() {
                throw null;
            }

            public static FailureType valueOf(String str) {
                return (FailureType) Enum.valueOf(FailureType.class, str);
            }

            public static FailureType[] values() {
                return (FailureType[]) f24365b.clone();
            }
        }
    }

    @N
    public static Rational a(@F int i12, @N Rational rational) {
        return (i12 == 90 || i12 == 270) ? rational == null ? rational : new Rational(rational.getDenominator(), rational.getNumerator()) : new Rational(rational.getNumerator(), rational.getDenominator());
    }

    @N
    public static byte[] b(@N InterfaceC20120j0 interfaceC20120j0, @P Rect rect, @F int i12, int i13) throws CodecFailedException {
        if (interfaceC20120j0.w() != 35) {
            throw new IllegalArgumentException("Incorrect image format of the input image proxy: " + interfaceC20120j0.w());
        }
        InterfaceC20120j0.a aVar = interfaceC20120j0.e4()[0];
        InterfaceC20120j0.a aVar2 = interfaceC20120j0.e4()[1];
        InterfaceC20120j0.a aVar3 = interfaceC20120j0.e4()[2];
        ByteBuffer byteBufferV = aVar.v();
        ByteBuffer byteBufferV2 = aVar2.v();
        ByteBuffer byteBufferV3 = aVar3.v();
        byteBufferV.rewind();
        byteBufferV2.rewind();
        byteBufferV3.rewind();
        int iRemaining = byteBufferV.remaining();
        byte[] bArr = new byte[((interfaceC20120j0.getHeight() * interfaceC20120j0.getWidth()) / 2) + iRemaining];
        int width = 0;
        for (int i14 = 0; i14 < interfaceC20120j0.getHeight(); i14++) {
            byteBufferV.get(bArr, width, interfaceC20120j0.getWidth());
            width += interfaceC20120j0.getWidth();
            byteBufferV.position(Math.min(iRemaining, aVar.w() + (byteBufferV.position() - interfaceC20120j0.getWidth())));
        }
        int height = interfaceC20120j0.getHeight() / 2;
        int width2 = interfaceC20120j0.getWidth() / 2;
        int iW2 = aVar3.w();
        int iW3 = aVar2.w();
        int iX2 = aVar3.x();
        int iX3 = aVar2.x();
        byte[] bArr2 = new byte[iW2];
        byte[] bArr3 = new byte[iW3];
        for (int i15 = 0; i15 < height; i15++) {
            byteBufferV3.get(bArr2, 0, Math.min(iW2, byteBufferV3.remaining()));
            byteBufferV2.get(bArr3, 0, Math.min(iW3, byteBufferV2.remaining()));
            int i16 = 0;
            int i17 = 0;
            for (int i18 = 0; i18 < width2; i18++) {
                int i19 = width + 1;
                bArr[width] = bArr2[i16];
                width += 2;
                bArr[i19] = bArr3[i17];
                i16 += iX2;
                i17 += iX3;
            }
        }
        YuvImage yuvImage = new YuvImage(bArr, 17, interfaceC20120j0.getWidth(), interfaceC20120j0.getHeight(), null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (yuvImage.compressToJpeg(rect == null ? new Rect(0, 0, interfaceC20120j0.getWidth(), interfaceC20120j0.getHeight()) : rect, i12, new l(byteArrayOutputStream, ExifData.a(interfaceC20120j0, i13)))) {
            return byteArrayOutputStream.toByteArray();
        }
        throw new CodecFailedException("YuvImage failed to encode jpeg.");
    }
}
