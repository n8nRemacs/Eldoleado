package androidx.camera.core.impl.utils;

import androidx.camera.core.C20140q0;
import j.N;
import j.X;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: Exif.java */
@X
/* loaded from: classes.dex */
public final class i {

    /* renamed from: b, reason: collision with root package name */
    public static final ThreadLocal<SimpleDateFormat> f24276b = new a();

    /* renamed from: c, reason: collision with root package name */
    public static final ThreadLocal<SimpleDateFormat> f24277c = new b();

    /* renamed from: d, reason: collision with root package name */
    public static final ThreadLocal<SimpleDateFormat> f24278d = new c();

    /* renamed from: e, reason: collision with root package name */
    public static final List<String> f24279e = Arrays.asList("ImageWidth", "ImageLength", "BitsPerSample", "Compression", "PhotometricInterpretation", "Orientation", "SamplesPerPixel", "PlanarConfiguration", "YCbCrSubSampling", "YCbCrPositioning", "XResolution", "YResolution", "ResolutionUnit", "StripOffsets", "RowsPerStrip", "StripByteCounts", "JPEGInterchangeFormat", "JPEGInterchangeFormatLength", "TransferFunction", "WhitePoint", "PrimaryChromaticities", "YCbCrCoefficients", "ReferenceBlackWhite", "DateTime", "ImageDescription", "Make", "Model", "Software", "Artist", "Copyright", "ExifVersion", "FlashpixVersion", "ColorSpace", "Gamma", "PixelXDimension", "PixelYDimension", "ComponentsConfiguration", "CompressedBitsPerPixel", "MakerNote", "UserComment", "RelatedSoundFile", "DateTimeOriginal", "DateTimeDigitized", "OffsetTime", "OffsetTimeOriginal", "OffsetTimeDigitized", "SubSecTime", "SubSecTimeOriginal", "SubSecTimeDigitized", "ExposureTime", "FNumber", "ExposureProgram", "SpectralSensitivity", "PhotographicSensitivity", "OECF", "SensitivityType", "StandardOutputSensitivity", "RecommendedExposureIndex", "ISOSpeed", "ISOSpeedLatitudeyyy", "ISOSpeedLatitudezzz", "ShutterSpeedValue", "ApertureValue", "BrightnessValue", "ExposureBiasValue", "MaxApertureValue", "SubjectDistance", "MeteringMode", "LightSource", "Flash", "SubjectArea", "FocalLength", "FlashEnergy", "SpatialFrequencyResponse", "FocalPlaneXResolution", "FocalPlaneYResolution", "FocalPlaneResolutionUnit", "SubjectLocation", "ExposureIndex", "SensingMethod", "FileSource", "SceneType", "CFAPattern", "CustomRendered", "ExposureMode", "WhiteBalance", "DigitalZoomRatio", "FocalLengthIn35mmFilm", "SceneCaptureType", "GainControl", "Contrast", "Saturation", "Sharpness", "DeviceSettingDescription", "SubjectDistanceRange", "ImageUniqueID", "CameraOwnerName", "BodySerialNumber", "LensSpecification", "LensMake", "LensModel", "LensSerialNumber", "GPSVersionID", "GPSLatitudeRef", "GPSLatitude", "GPSLongitudeRef", "GPSLongitude", "GPSAltitudeRef", "GPSAltitude", "GPSTimeStamp", "GPSSatellites", "GPSStatus", "GPSMeasureMode", "GPSDOP", "GPSSpeedRef", "GPSSpeed", "GPSTrackRef", "GPSTrack", "GPSImgDirectionRef", "GPSImgDirection", "GPSMapDatum", "GPSDestLatitudeRef", "GPSDestLatitude", "GPSDestLongitudeRef", "GPSDestLongitude", "GPSDestBearingRef", "GPSDestBearing", "GPSDestDistanceRef", "GPSDestDistance", "GPSProcessingMethod", "GPSAreaInformation", "GPSDateStamp", "GPSDifferential", "GPSHPositioningError", "InteroperabilityIndex", "ThumbnailImageLength", "ThumbnailImageWidth", "ThumbnailOrientation", "DNGVersion", "DefaultCropSize", "ThumbnailImage", "PreviewImageStart", "PreviewImageLength", "AspectFrame", "SensorBottomBorder", "SensorLeftBorder", "SensorRightBorder", "SensorTopBorder", "ISO", "JpgFromRaw", "Xmp", "NewSubfileType", "SubfileType");

    /* renamed from: f, reason: collision with root package name */
    public static final List<String> f24280f = Arrays.asList("ImageWidth", "ImageLength", "PixelXDimension", "PixelYDimension", "Compression", "JPEGInterchangeFormat", "JPEGInterchangeFormatLength", "ThumbnailImageLength", "ThumbnailImageWidth", "ThumbnailOrientation");

    /* renamed from: a, reason: collision with root package name */
    public final androidx.exifinterface.media.a f24281a;

    /* compiled from: Exif.java */
    public class a extends ThreadLocal<SimpleDateFormat> {
        @Override // java.lang.ThreadLocal
        public final SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy:MM:dd", Locale.US);
        }
    }

    /* compiled from: Exif.java */
    public class b extends ThreadLocal<SimpleDateFormat> {
        @Override // java.lang.ThreadLocal
        public final SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss", Locale.US);
        }
    }

    /* compiled from: Exif.java */
    public class c extends ThreadLocal<SimpleDateFormat> {
        @Override // java.lang.ThreadLocal
        public final SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);
        }
    }

    /* compiled from: Exif.java */
    public static final class d {

        /* compiled from: Exif.java */
        public static final class a {
        }
    }

    public i(androidx.exifinterface.media.a aVar) {
        this.f24281a = aVar;
    }

    public final void a(@N i iVar) throws NumberFormatException {
        ArrayList arrayList = new ArrayList(f24279e);
        arrayList.removeAll(f24280f);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            String strC = this.f24281a.c(str);
            androidx.exifinterface.media.a aVar = iVar.f24281a;
            String strC2 = aVar.c(str);
            if (strC != null && !strC.equals(strC2)) {
                aVar.H(str, strC);
            }
        }
    }

    public final int b() {
        return this.f24281a.e(0, "Orientation");
    }

    public final int c() {
        switch (b()) {
            case 3:
            case 4:
                return 180;
            case 5:
                return 270;
            case 6:
            case 7:
                return 90;
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    public final void d(int i12) throws NumberFormatException {
        int i13 = i12 % 90;
        androidx.exifinterface.media.a aVar = this.f24281a;
        if (i13 != 0) {
            Locale locale = Locale.US;
            StringBuilder sb2 = new StringBuilder("Can only rotate in right angles (eg. 0, 90, 180, 270). ");
            sb2.append(i12);
            sb2.append(" is unsupported.");
            C20140q0.d(5, "i");
            aVar.H("Orientation", String.valueOf(0));
            return;
        }
        int i14 = i12 % 360;
        int iB2 = b();
        while (i14 < 0) {
            i14 += 90;
            switch (iB2) {
                case 2:
                    iB2 = 5;
                    break;
                case 3:
                case 8:
                    iB2 = 6;
                    break;
                case 4:
                    iB2 = 7;
                    break;
                case 5:
                    iB2 = 4;
                    break;
                case 6:
                    iB2 = 1;
                    break;
                case 7:
                    iB2 = 2;
                    break;
                default:
                    iB2 = 8;
                    break;
            }
        }
        while (i14 > 0) {
            i14 -= 90;
            switch (iB2) {
                case 2:
                    iB2 = 7;
                    break;
                case 3:
                    iB2 = 8;
                    break;
                case 4:
                    iB2 = 5;
                    break;
                case 5:
                    iB2 = 2;
                    break;
                case 6:
                    iB2 = 3;
                    break;
                case 7:
                    iB2 = 4;
                    break;
                case 8:
                    iB2 = 1;
                    break;
                default:
                    iB2 = 6;
                    break;
            }
        }
        aVar.H("Orientation", String.valueOf(iB2));
    }

    public final void e() throws Throwable {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ThreadLocal<SimpleDateFormat> threadLocal = f24278d;
        String str = threadLocal.get().format(new Date(jCurrentTimeMillis));
        androidx.exifinterface.media.a aVar = this.f24281a;
        aVar.H("DateTime", str);
        try {
            aVar.H("SubSecTime", Long.toString(jCurrentTimeMillis - threadLocal.get().parse(str).getTime()));
        } catch (ParseException unused) {
        }
        aVar.D();
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0171  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String toString() throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 487
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.i.toString():java.lang.String");
    }
}
