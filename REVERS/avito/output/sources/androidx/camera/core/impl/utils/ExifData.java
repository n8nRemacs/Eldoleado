package androidx.camera.core.impl.utils;

import android.os.Build;
import android.util.Pair;
import androidx.camera.camera2.internal.G;
import androidx.camera.core.C20140q0;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.core.util.z;
import j.N;
import j.X;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import okhttp3.internal.ws.WebSocketProtocol;

@X
/* loaded from: classes.dex */
public class ExifData {

    /* renamed from: c, reason: collision with root package name */
    public static final m[] f24179c;

    /* renamed from: d, reason: collision with root package name */
    public static final m[][] f24180d;

    /* renamed from: e, reason: collision with root package name */
    public static final HashSet<String> f24181e;

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList f24182a;

    /* renamed from: b, reason: collision with root package name */
    public final ByteOrder f24183b;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class WhiteBalanceMode {

        /* renamed from: b, reason: collision with root package name */
        public static final WhiteBalanceMode f24184b;

        /* renamed from: c, reason: collision with root package name */
        public static final WhiteBalanceMode f24185c;

        /* renamed from: d, reason: collision with root package name */
        public static final /* synthetic */ WhiteBalanceMode[] f24186d;

        static {
            WhiteBalanceMode whiteBalanceMode = new WhiteBalanceMode("AUTO", 0);
            f24184b = whiteBalanceMode;
            WhiteBalanceMode whiteBalanceMode2 = new WhiteBalanceMode("MANUAL", 1);
            f24185c = whiteBalanceMode2;
            f24186d = new WhiteBalanceMode[]{whiteBalanceMode, whiteBalanceMode2};
        }

        public WhiteBalanceMode() {
            throw null;
        }

        public static WhiteBalanceMode valueOf(String str) {
            return (WhiteBalanceMode) Enum.valueOf(WhiteBalanceMode.class, str);
        }

        public static WhiteBalanceMode[] values() {
            return (WhiteBalanceMode[]) f24186d.clone();
        }
    }

    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f24187a;

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ int[] f24188b;

        static {
            int[] iArr = new int[WhiteBalanceMode.values().length];
            f24188b = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f24188b[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[CameraCaptureMetaData.FlashState.values().length];
            f24187a = iArr2;
            try {
                iArr2[2] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f24187a[1] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f24187a[3] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static final class b {

        /* renamed from: c, reason: collision with root package name */
        public static final Pattern f24189c = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");

        /* renamed from: d, reason: collision with root package name */
        public static final Pattern f24190d = Pattern.compile("^(\\d{4}):(\\d{2}):(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");

        /* renamed from: e, reason: collision with root package name */
        public static final Pattern f24191e = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");

        /* renamed from: f, reason: collision with root package name */
        public static final ArrayList f24192f = Collections.list(new a());

        /* renamed from: a, reason: collision with root package name */
        public final ArrayList f24193a;

        /* renamed from: b, reason: collision with root package name */
        public final ByteOrder f24194b;

        public class a implements Enumeration<HashMap<String, m>> {

            /* renamed from: b, reason: collision with root package name */
            public int f24195b = 0;

            @Override // java.util.Enumeration
            public final boolean hasMoreElements() {
                int i12 = this.f24195b;
                m[] mVarArr = ExifData.f24179c;
                return i12 < 4;
            }

            @Override // java.util.Enumeration
            public final HashMap<String, m> nextElement() {
                HashMap<String, m> map = new HashMap<>();
                for (m mVar : ExifData.f24180d[this.f24195b]) {
                    map.put(mVar.f24297b, mVar);
                }
                this.f24195b++;
                return map;
            }
        }

        /* renamed from: androidx.camera.core.impl.utils.ExifData$b$b, reason: collision with other inner class name */
        public class C1534b implements Enumeration<Map<String, j>> {

            /* renamed from: b, reason: collision with root package name */
            public int f24196b;

            @Override // java.util.Enumeration
            public final boolean hasMoreElements() {
                int i12 = this.f24196b;
                m[] mVarArr = ExifData.f24179c;
                return i12 < 4;
            }

            @Override // java.util.Enumeration
            public final Map<String, j> nextElement() {
                this.f24196b++;
                return new HashMap();
            }
        }

        public b(@N ByteOrder byteOrder) {
            C1534b c1534b = new C1534b();
            c1534b.f24196b = 0;
            this.f24193a = Collections.list(c1534b);
            this.f24194b = byteOrder;
        }

        public static Pair<Integer, Integer> a(String str) throws NumberFormatException {
            if (str.contains(",")) {
                String[] strArrSplit = str.split(",", -1);
                Pair<Integer, Integer> pairA = a(strArrSplit[0]);
                if (((Integer) pairA.first).intValue() == 2) {
                    return pairA;
                }
                for (int i12 = 1; i12 < strArrSplit.length; i12++) {
                    Pair<Integer, Integer> pairA2 = a(strArrSplit[i12]);
                    int iIntValue = (((Integer) pairA2.first).equals(pairA.first) || ((Integer) pairA2.second).equals(pairA.first)) ? ((Integer) pairA.first).intValue() : -1;
                    int iIntValue2 = (((Integer) pairA.second).intValue() == -1 || !(((Integer) pairA2.first).equals(pairA.second) || ((Integer) pairA2.second).equals(pairA.second))) ? -1 : ((Integer) pairA.second).intValue();
                    if (iIntValue == -1 && iIntValue2 == -1) {
                        return new Pair<>(2, -1);
                    }
                    if (iIntValue == -1) {
                        pairA = new Pair<>(Integer.valueOf(iIntValue2), -1);
                    } else if (iIntValue2 == -1) {
                        pairA = new Pair<>(Integer.valueOf(iIntValue), -1);
                    }
                }
                return pairA;
            }
            if (!str.contains("/")) {
                try {
                    try {
                        long j12 = Long.parseLong(str);
                        return (j12 < 0 || j12 > WebSocketProtocol.PAYLOAD_SHORT_MAX) ? j12 < 0 ? new Pair<>(9, -1) : new Pair<>(4, -1) : new Pair<>(3, 4);
                    } catch (NumberFormatException unused) {
                        return new Pair<>(2, -1);
                    }
                } catch (NumberFormatException unused2) {
                    Double.parseDouble(str);
                    return new Pair<>(12, -1);
                }
            }
            String[] strArrSplit2 = str.split("/", -1);
            if (strArrSplit2.length == 2) {
                try {
                    long j13 = (long) Double.parseDouble(strArrSplit2[0]);
                    long j14 = (long) Double.parseDouble(strArrSplit2[1]);
                    if (j13 >= 0 && j14 >= 0) {
                        if (j13 <= 2147483647L && j14 <= 2147483647L) {
                            return new Pair<>(10, 5);
                        }
                        return new Pair<>(5, -1);
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException unused3) {
                }
            }
            return new Pair<>(2, -1);
        }

        public final void b(@N String str, @N String str2, @N ArrayList arrayList) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((Map) it.next()).containsKey(str)) {
                    return;
                }
            }
            c(str, str2, arrayList);
        }

        /* JADX WARN: Removed duplicated region for block: B:100:0x02ba  */
        /* JADX WARN: Removed duplicated region for block: B:105:0x02e0  */
        /* JADX WARN: Removed duplicated region for block: B:113:0x032a  */
        /* JADX WARN: Removed duplicated region for block: B:114:0x034d  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0150  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x0199  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x0209  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x024e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final void c(@j.N java.lang.String r20, @j.P java.lang.String r21, @j.N java.util.List<java.util.Map<java.lang.String, androidx.camera.core.impl.utils.j>> r22) {
            /*
                Method dump skipped, instructions count: 950
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.ExifData.b.c(java.lang.String, java.lang.String, java.util.List):void");
        }

        @N
        public final void d(float f12) {
            c("FocalLength", new n((long) (f12 * 1000.0f), 1000L).toString(), this.f24193a);
        }

        @N
        public final void e(int i12) {
            int i13;
            if (i12 == 0) {
                i13 = 1;
            } else if (i12 == 90) {
                i13 = 6;
            } else if (i12 == 180) {
                i13 = 3;
            } else if (i12 != 270) {
                C20140q0.d(5, "ExifData");
                i13 = 0;
            } else {
                i13 = 8;
            }
            c("Orientation", String.valueOf(i13), this.f24193a);
        }
    }

    static {
        m[] mVarArr = {new m("ImageWidth", 256, 3, 4), new m("ImageLength", 257, 3, 4), new m("Make", 271, 2), new m("Model", 272, 2), new m("Orientation", 274, 3), new m("XResolution", 282, 5), new m("YResolution", 283, 5), new m("ResolutionUnit", 296, 3), new m("Software", 305, 2), new m("DateTime", 306, 2), new m("YCbCrPositioning", 531, 3), new m("SubIFDPointer", 330, 4), new m("ExifIFDPointer", 34665, 4), new m("GPSInfoIFDPointer", 34853, 4)};
        m[] mVarArr2 = {new m("ExposureTime", 33434, 5), new m("FNumber", 33437, 5), new m("ExposureProgram", 34850, 3), new m("PhotographicSensitivity", 34855, 3), new m("SensitivityType", 34864, 3), new m("ExifVersion", 36864, 2), new m("DateTimeOriginal", 36867, 2), new m("DateTimeDigitized", 36868, 2), new m("ComponentsConfiguration", 37121, 7), new m("ShutterSpeedValue", 37377, 10), new m("ApertureValue", 37378, 5), new m("BrightnessValue", 37379, 10), new m("ExposureBiasValue", 37380, 10), new m("MaxApertureValue", 37381, 5), new m("MeteringMode", 37383, 3), new m("LightSource", 37384, 3), new m("Flash", 37385, 3), new m("FocalLength", 37386, 5), new m("SubSecTime", 37520, 2), new m("SubSecTimeOriginal", 37521, 2), new m("SubSecTimeDigitized", 37522, 2), new m("FlashpixVersion", 40960, 7), new m("ColorSpace", 40961, 3), new m("PixelXDimension", 40962, 3, 4), new m("PixelYDimension", 40963, 3, 4), new m("InteroperabilityIFDPointer", 40965, 4), new m("FocalPlaneResolutionUnit", 41488, 3), new m("SensingMethod", 41495, 3), new m("FileSource", 41728, 7), new m("SceneType", 41729, 7), new m("CustomRendered", 41985, 3), new m("ExposureMode", 41986, 3), new m("WhiteBalance", 41987, 3), new m("SceneCaptureType", 41990, 3), new m("Contrast", 41992, 3), new m("Saturation", 41993, 3), new m("Sharpness", 41994, 3)};
        m[] mVarArr3 = {new m("GPSVersionID", 0, 1), new m("GPSLatitudeRef", 1, 2), new m("GPSLatitude", 2, 5, 10), new m("GPSLongitudeRef", 3, 2), new m("GPSLongitude", 4, 5, 10), new m("GPSAltitudeRef", 5, 1), new m("GPSAltitude", 6, 5), new m("GPSTimeStamp", 7, 5), new m("GPSSpeedRef", 12, 2), new m("GPSTrackRef", 14, 2), new m("GPSImgDirectionRef", 16, 2), new m("GPSDestBearingRef", 23, 2), new m("GPSDestDistanceRef", 25, 2)};
        f24179c = new m[]{new m("SubIFDPointer", 330, 4), new m("ExifIFDPointer", 34665, 4), new m("GPSInfoIFDPointer", 34853, 4), new m("InteroperabilityIFDPointer", 40965, 4)};
        f24180d = new m[][]{mVarArr, mVarArr2, mVarArr3, new m[]{new m("InteroperabilityIndex", 1, 2)}};
        f24181e = new HashSet<>(Arrays.asList("FNumber", "ExposureTime", "GPSTimeStamp"));
    }

    public ExifData(ByteOrder byteOrder, ArrayList arrayList) {
        z.g("Malformed attributes list. Number of IFDs mismatch.", arrayList.size() == 4);
        this.f24183b = byteOrder;
        this.f24182a = arrayList;
    }

    @N
    public static ExifData a(@N InterfaceC20120j0 interfaceC20120j0, int i12) {
        b bVar = new b(ByteOrder.BIG_ENDIAN);
        String strValueOf = String.valueOf(1);
        ArrayList arrayList = bVar.f24193a;
        bVar.c("Orientation", strValueOf, arrayList);
        bVar.c("XResolution", "72/1", arrayList);
        bVar.c("YResolution", "72/1", arrayList);
        bVar.c("ResolutionUnit", String.valueOf(2), arrayList);
        bVar.c("YCbCrPositioning", String.valueOf(1), arrayList);
        bVar.c("Make", Build.MANUFACTURER, arrayList);
        bVar.c("Model", Build.MODEL, arrayList);
        if (interfaceC20120j0.r1() != null) {
            interfaceC20120j0.r1().c(bVar);
        }
        bVar.e(i12);
        bVar.c("ImageWidth", String.valueOf(interfaceC20120j0.getWidth()), arrayList);
        bVar.c("ImageLength", String.valueOf(interfaceC20120j0.getHeight()), arrayList);
        ArrayList list = Collections.list(new k(bVar));
        if (!((Map) list.get(1)).isEmpty()) {
            bVar.b("ExposureProgram", String.valueOf(0), list);
            bVar.b("ExifVersion", "0230", list);
            bVar.b("ComponentsConfiguration", "1,2,3,0", list);
            bVar.b("MeteringMode", String.valueOf(0), list);
            bVar.b("LightSource", String.valueOf(0), list);
            bVar.b("FlashpixVersion", "0100", list);
            bVar.b("FocalPlaneResolutionUnit", String.valueOf(2), list);
            bVar.b("FileSource", String.valueOf(3), list);
            bVar.b("SceneType", String.valueOf(1), list);
            bVar.b("CustomRendered", String.valueOf(0), list);
            bVar.b("SceneCaptureType", String.valueOf(0), list);
            bVar.b("Contrast", String.valueOf(0), list);
            bVar.b("Saturation", String.valueOf(0), list);
            bVar.b("Sharpness", String.valueOf(0), list);
        }
        if (!((Map) list.get(2)).isEmpty()) {
            bVar.b("GPSVersionID", "2300", list);
            bVar.b("GPSSpeedRef", "K", list);
            bVar.b("GPSTrackRef", "T", list);
            bVar.b("GPSImgDirectionRef", "T", list);
            bVar.b("GPSDestBearingRef", "T", list);
            bVar.b("GPSDestDistanceRef", "K", list);
        }
        return new ExifData(bVar.f24194b, list);
    }

    @N
    public final Map<String, j> b(int i12) {
        z.c(i12, 0, G.e(i12, "Invalid IFD index: ", ". Index should be between [0, EXIF_TAGS.length] "), 4);
        return (Map) this.f24182a.get(i12);
    }
}
