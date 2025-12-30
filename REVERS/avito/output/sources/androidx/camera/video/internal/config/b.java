package androidx.camera.video.internal.config;

import android.media.AudioRecord;
import android.util.Range;
import android.util.Rational;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.S;
import androidx.camera.video.AbstractC20150a;
import androidx.camera.video.AbstractC20184v;
import androidx.camera.video.internal.config.h;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: AudioConfigUtil.java */
@X
/* loaded from: classes.dex */
public final class b {
    @N
    public static e a(@N AbstractC20184v abstractC20184v, @P androidx.camera.video.internal.f fVar) {
        S.a aVarF;
        String str = abstractC20184v.c() != 1 ? "audio/mp4a-latm" : "audio/vorbis";
        int i12 = (abstractC20184v.c() != 1 ? "audio/mp4a-latm" : "audio/vorbis").equals("audio/mp4a-latm") ? 2 : -1;
        if (fVar == null || fVar.f() == null) {
            aVarF = null;
        } else {
            aVarF = fVar.f();
            String strE = aVarF.e();
            int iF2 = aVarF.f();
            if (Objects.equals(strE, "audio/none")) {
                C20140q0.d(3, "AudioConfigUtil");
            } else if (abstractC20184v.c() == -1) {
                C20140q0.d(3, "AudioConfigUtil");
                str = strE;
                i12 = iF2;
            } else if (str.equals(strE) && i12 == iF2) {
                C20140q0.d(3, "AudioConfigUtil");
                str = strE;
            } else {
                C20140q0.d(3, "AudioConfigUtil");
            }
            aVarF = null;
        }
        h.b bVar = new h.b();
        if (str == null) {
            throw new NullPointerException("Null mimeType");
        }
        bVar.f25079a = str;
        bVar.f25080b = -1;
        bVar.f25080b = Integer.valueOf(i12);
        if (aVarF != null) {
            bVar.f25081c = aVarF;
        }
        String str2 = bVar.f25079a == null ? " mimeType" : "";
        if (str2.isEmpty()) {
            return new h(bVar.f25079a, bVar.f25080b.intValue(), bVar.f25081c, null);
        }
        throw new IllegalStateException("Missing required properties:".concat(str2));
    }

    public static int b(int i12, int i13, int i14, int i15, int i16, Range<Integer> range) {
        Rational rational = new Rational(i13, i14);
        int iDoubleValue = (int) (new Rational(i15, i16).doubleValue() * rational.doubleValue() * i12);
        if (C20140q0.d(3, "AudioConfigUtil")) {
            String.format("Base Bitrate(%dbps) * Channel Count Ratio(%d / %d) * Sample Rate Ratio(%d / %d) = %d", Integer.valueOf(i12), Integer.valueOf(i13), Integer.valueOf(i14), Integer.valueOf(i15), Integer.valueOf(i16), Integer.valueOf(iDoubleValue));
        }
        if (!AbstractC20150a.f24857a.equals(range)) {
            Integer num = (Integer) range.clamp(Integer.valueOf(iDoubleValue));
            iDoubleValue = num.intValue();
            if (C20140q0.d(3, "AudioConfigUtil")) {
                String.format("\nClamped to range %s -> %dbps", range, num);
            }
        }
        C20140q0.d(3, "AudioConfigUtil");
        return iDoubleValue;
    }

    public static int c(@N Range<Integer> range, int i12, int i13, final int i14) {
        ArrayList arrayList = null;
        int i15 = 0;
        int iIntValue = i14;
        while (true) {
            if (range.contains((Range<Integer>) Integer.valueOf(iIntValue))) {
                if (iIntValue > 0 && i12 > 0) {
                    if (AudioRecord.getMinBufferSize(iIntValue, i12 == 1 ? 16 : 12, i13) > 0) {
                        return iIntValue;
                    }
                }
                C20140q0.d(3, "AudioConfigUtil");
            } else {
                range.toString();
                C20140q0.d(3, "AudioConfigUtil");
            }
            if (arrayList == null) {
                C20140q0.d(3, "AudioConfigUtil");
                arrayList = new ArrayList(androidx.camera.video.internal.audio.a.f24951a);
                Collections.sort(arrayList, new Comparator() { // from class: androidx.camera.video.internal.config.a
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int iIntValue2 = ((Integer) obj).intValue();
                        int i16 = i14;
                        int iAbs = Math.abs(iIntValue2 - i16) - Math.abs(((Integer) obj2).intValue() - i16);
                        return (int) (iAbs == 0 ? Math.signum(r4.intValue() - r5.intValue()) : Math.signum(iAbs));
                    }
                });
            }
            if (i15 >= arrayList.size()) {
                C20140q0.d(3, "AudioConfigUtil");
                return 44100;
            }
            iIntValue = ((Integer) arrayList.get(i15)).intValue();
            i15++;
        }
    }
}
