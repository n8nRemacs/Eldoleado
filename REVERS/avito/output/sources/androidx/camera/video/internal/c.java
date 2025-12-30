package androidx.camera.video.internal;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.text.TextUtils;
import androidx.appcompat.app.r;
import androidx.core.util.z;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* compiled from: DebugUtils.java */
@X
/* loaded from: classes.dex */
public final class c {
    @N
    public static String a(@N String str, @N MediaCodec mediaCodec, @N MediaFormat mediaFormat) {
        StringBuilder sb2 = new StringBuilder();
        try {
            MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodec.getCodecInfo().getCapabilitiesForType(str);
            z.b(capabilitiesForType != null);
            b(sb2, capabilitiesForType, mediaFormat);
        } catch (IllegalArgumentException unused) {
            sb2.append("[" + mediaCodec.getName() + "] does not support mime " + str);
            sb2.append("\n");
        }
        return sb2.toString();
    }

    public static void b(@N StringBuilder sb2, @N MediaCodecInfo.CodecCapabilities codecCapabilities, @N MediaFormat mediaFormat) {
        int integer;
        int integer2;
        boolean z12;
        try {
            sb2.append("[CodecCaps] isFormatSupported = " + codecCapabilities.isFormatSupported(mediaFormat));
            sb2.append("\n");
        } catch (ClassCastException unused) {
            sb2.append("[CodecCaps] isFormatSupported=false");
            sb2.append("\n");
        }
        sb2.append("[CodecCaps] getDefaultFormat = " + codecCapabilities.getDefaultFormat());
        sb2.append("\n");
        int i12 = 0;
        if (codecCapabilities.profileLevels != null) {
            StringBuilder sb3 = new StringBuilder("[");
            ArrayList arrayList = new ArrayList();
            MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = codecCapabilities.profileLevels;
            int length = codecProfileLevelArr.length;
            for (int i13 = 0; i13 < length; i13++) {
                MediaCodecInfo.CodecProfileLevel codecProfileLevel = codecProfileLevelArr[i13];
                arrayList.add(codecProfileLevel == null ? "null" : String.format("{level=%d, profile=%d}", Integer.valueOf(codecProfileLevel.level), Integer.valueOf(codecProfileLevel.profile)));
            }
            sb3.append(TextUtils.join(", ", arrayList));
            sb3.append("]");
            sb2.append("[CodecCaps] profileLevels = " + ((Object) sb3));
            sb2.append("\n");
        }
        if (codecCapabilities.colorFormats != null) {
            sb2.append("[CodecCaps] colorFormats = " + Arrays.toString(codecCapabilities.colorFormats));
            sb2.append("\n");
        }
        MediaCodecInfo.VideoCapabilities videoCapabilities = codecCapabilities.getVideoCapabilities();
        if (videoCapabilities != null) {
            sb2.append("[VideoCaps] getBitrateRange = " + videoCapabilities.getBitrateRange());
            sb2.append("\n");
            sb2.append("[VideoCaps] getSupportedWidths = " + videoCapabilities.getSupportedWidths() + ", getWidthAlignment = " + videoCapabilities.getWidthAlignment());
            sb2.append("\n");
            sb2.append("[VideoCaps] getSupportedHeights = " + videoCapabilities.getSupportedHeights() + ", getHeightAlignment = " + videoCapabilities.getHeightAlignment());
            sb2.append("\n");
            boolean z13 = true;
            try {
                integer = mediaFormat.getInteger("width");
                integer2 = mediaFormat.getInteger("height");
                z.b(integer > 0 && integer2 > 0);
                z12 = true;
            } catch (IllegalArgumentException | NullPointerException unused2) {
                sb2.append("[VideoCaps] mediaFormat does not contain valid width and height");
                sb2.append("\n");
                integer = 0;
                integer2 = 0;
                z12 = false;
            }
            if (z12) {
                try {
                    sb2.append("[VideoCaps] getSupportedHeightsFor " + integer + " = " + videoCapabilities.getSupportedHeightsFor(integer));
                    sb2.append("\n");
                } catch (IllegalArgumentException unused3) {
                    sb2.append("[VideoCaps] could not getSupportedHeightsFor " + integer);
                    sb2.append("\n");
                }
                try {
                    sb2.append("[VideoCaps] getSupportedWidthsFor " + integer2 + " = " + videoCapabilities.getSupportedWidthsFor(integer2));
                    sb2.append("\n");
                } catch (IllegalArgumentException unused4) {
                    sb2.append("[VideoCaps] could not getSupportedWidthsFor " + integer2);
                    sb2.append("\n");
                }
                StringBuilder sbY = r.y(integer, integer2, "[VideoCaps] isSizeSupported for ", "x", " = ");
                sbY.append(videoCapabilities.isSizeSupported(integer, integer2));
                sb2.append(sbY.toString());
                sb2.append("\n");
            }
            sb2.append("[VideoCaps] getSupportedFrameRates = " + videoCapabilities.getSupportedFrameRates());
            sb2.append("\n");
            try {
                int integer3 = mediaFormat.getInteger("frame-rate");
                if (integer3 <= 0) {
                    z13 = false;
                }
                z.b(z13);
                i12 = integer3;
            } catch (IllegalArgumentException | NullPointerException unused5) {
                sb2.append("[VideoCaps] mediaFormat does not contain frame rate");
                sb2.append("\n");
            }
            if (z12) {
                StringBuilder sbY2 = r.y(integer, integer2, "[VideoCaps] getSupportedFrameRatesFor ", "x", " = ");
                sbY2.append(videoCapabilities.getSupportedFrameRatesFor(integer, integer2));
                sb2.append(sbY2.toString());
                sb2.append("\n");
            }
            if (z12 && i12 > 0) {
                StringBuilder sbY3 = r.y(integer, integer2, "[VideoCaps] areSizeAndRateSupported for ", "x", ", ");
                sbY3.append(i12);
                sbY3.append(" = ");
                sbY3.append(videoCapabilities.areSizeAndRateSupported(integer, integer2, i12));
                sb2.append(sbY3.toString());
                sb2.append("\n");
            }
        }
        MediaCodecInfo.AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
        if (audioCapabilities != null) {
            sb2.append("[AudioCaps] getBitrateRange = " + audioCapabilities.getBitrateRange());
            sb2.append("\n");
            sb2.append("[AudioCaps] getMaxInputChannelCount = " + audioCapabilities.getMaxInputChannelCount());
            sb2.append("\n");
            if (Build.VERSION.SDK_INT >= 31) {
                sb2.append("[AudioCaps] getMinInputChannelCount = " + androidx.camera.video.internal.compat.f.b(audioCapabilities));
                sb2.append("\n");
                sb2.append("[AudioCaps] getInputChannelCountRanges = " + Arrays.toString(androidx.camera.video.internal.compat.f.a(audioCapabilities)));
                sb2.append("\n");
            }
            sb2.append("[AudioCaps] getSupportedSampleRateRanges = " + Arrays.toString(audioCapabilities.getSupportedSampleRateRanges()));
            sb2.append("\n");
            sb2.append("[AudioCaps] getSupportedSampleRates = " + Arrays.toString(audioCapabilities.getSupportedSampleRates()));
            sb2.append("\n");
            try {
                int integer4 = mediaFormat.getInteger("sample-rate");
                sb2.append("[AudioCaps] isSampleRateSupported for " + integer4 + " = " + audioCapabilities.isSampleRateSupported(integer4));
                sb2.append("\n");
            } catch (IllegalArgumentException | NullPointerException unused6) {
                sb2.append("[AudioCaps] mediaFormat does not contain sample rate");
                sb2.append("\n");
            }
        }
        MediaCodecInfo.EncoderCapabilities encoderCapabilities = codecCapabilities.getEncoderCapabilities();
        if (encoderCapabilities != null) {
            sb2.append("[EncoderCaps] getComplexityRange = " + encoderCapabilities.getComplexityRange());
            sb2.append("\n");
            if (Build.VERSION.SDK_INT >= 28) {
                sb2.append("[EncoderCaps] getQualityRange = " + androidx.camera.video.internal.compat.d.a(encoderCapabilities));
                sb2.append("\n");
            }
            try {
                sb2.append("[EncoderCaps] isBitrateModeSupported = " + encoderCapabilities.isBitrateModeSupported(mediaFormat.getInteger("bitrate-mode")));
                sb2.append("\n");
            } catch (IllegalArgumentException | NullPointerException unused7) {
                sb2.append("[EncoderCaps] mediaFormat does not contain bitrate mode");
                sb2.append("\n");
            }
        }
    }

    @N
    public static String c(long j12) {
        long millis = TimeUnit.MICROSECONDS.toMillis(j12);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long hours = timeUnit.toHours(millis);
        TimeUnit timeUnit2 = TimeUnit.HOURS;
        long minutes = timeUnit.toMinutes(millis - timeUnit2.toMillis(hours));
        long millis2 = millis - timeUnit2.toMillis(hours);
        TimeUnit timeUnit3 = TimeUnit.MINUTES;
        long seconds = timeUnit.toSeconds(millis2 - timeUnit3.toMillis(minutes));
        return String.format(Locale.US, "%02d:%02d:%02d.%03d", Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds), Long.valueOf(((millis - timeUnit2.toMillis(hours)) - timeUnit3.toMillis(minutes)) - TimeUnit.SECONDS.toMillis(seconds)));
    }
}
