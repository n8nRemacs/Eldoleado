package androidx.camera.video.internal.config;

import android.util.Range;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.F;
import androidx.camera.core.impl.S;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.AbstractC20184v;
import androidx.camera.video.internal.config.i;
import androidx.camera.video.internal.encoder.J;
import androidx.camera.video.internal.encoder.K;
import androidx.camera.video.n0;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import okhttp3.internal.http2.Http2;

/* compiled from: VideoConfigUtil.java */
@X
/* loaded from: classes.dex */
public final class k {

    /* renamed from: a, reason: collision with root package name */
    public static final HashMap f25088a;

    static {
        HashMap map = new HashMap();
        f25088a = map;
        HashMap map2 = new HashMap();
        K k12 = K.f25123a;
        map2.put(1, k12);
        K k13 = K.f25125c;
        map2.put(2, k13);
        K k14 = K.f25126d;
        map2.put(4096, k14);
        map2.put(8192, k14);
        HashMap map3 = new HashMap();
        map3.put(1, k12);
        map3.put(2, k13);
        map3.put(4096, k14);
        map3.put(8192, k14);
        HashMap map4 = new HashMap();
        map4.put(1, k12);
        map4.put(4, k13);
        map4.put(4096, k14);
        map4.put(Integer.valueOf(Http2.INITIAL_MAX_FRAME_SIZE), k14);
        map4.put(2, k12);
        map4.put(8, k13);
        map4.put(8192, k14);
        map4.put(32768, k14);
        HashMap map5 = new HashMap();
        map5.put(256, k13);
        map5.put(512, K.f25124b);
        map.put("video/hevc", map2);
        map.put("video/av01", map3);
        map.put("video/x-vnd.on2.vp9", map4);
        map.put("video/dolby-vision", map5);
    }

    @N
    public static K a(int i12, @N String str) {
        K k12;
        Map map = (Map) f25088a.get(str);
        if (map != null && (k12 = (K) map.get(Integer.valueOf(i12))) != null) {
            return k12;
        }
        String.format("Unsupported mime type %s or profile level %d. Data space is unspecified.", str, Integer.valueOf(i12));
        C20140q0.d(5, "VideoConfigUtil");
        return K.f25123a;
    }

    @N
    public static J b(@N n nVar, @N Timebase timebase, @N n0 n0Var, @N Size size, @N F f12, @N Range<Integer> range) {
        i iVar = (i) nVar;
        S.c cVar = iVar.f25084c;
        return (J) (cVar != null ? new m(iVar.f25082a, timebase, n0Var, size, cVar, f12, range) : new l(iVar.f25082a, timebase, n0Var, size, f12, range)).get();
    }

    @N
    public static n c(@N AbstractC20184v abstractC20184v, @N F f12, @P androidx.camera.video.internal.f fVar) {
        S.c next;
        z.g("Dynamic range must be a fully specified dynamic range [provided dynamic range: " + f12 + "]", f12.b());
        String str = "video/avc";
        String str2 = abstractC20184v.c() != 1 ? "video/avc" : "video/x-vnd.on2.vp8";
        int i12 = f12.f23568a;
        if (fVar == null) {
            next = null;
        } else {
            Set setEmptySet = (Set) androidx.camera.video.internal.utils.a.f25267b.get(Integer.valueOf(i12));
            if (setEmptySet == null) {
                setEmptySet = Collections.emptySet();
            }
            Set setEmptySet2 = (Set) androidx.camera.video.internal.utils.a.f25266a.get(Integer.valueOf(f12.f23569b));
            if (setEmptySet2 == null) {
                setEmptySet2 = Collections.emptySet();
            }
            Iterator<S.c> it = fVar.d().iterator();
            while (it.hasNext()) {
                next = it.next();
                if (setEmptySet.contains(Integer.valueOf(next.g())) && setEmptySet2.contains(Integer.valueOf(next.b()))) {
                    String strI = next.i();
                    if (str2.equals(strI)) {
                        C20140q0.d(3, "VideoConfigUtil");
                    } else if (abstractC20184v.c() == -1) {
                        f12.toString();
                        C20140q0.d(3, "VideoConfigUtil");
                    }
                    str2 = strI;
                    break;
                }
            }
            next = null;
        }
        if (next == null) {
            if (abstractC20184v.c() == -1) {
                if (i12 != 1) {
                    if (i12 == 3 || i12 == 4 || i12 == 5) {
                        str = "video/hevc";
                    } else {
                        if (i12 != 6) {
                            throw new UnsupportedOperationException("Unsupported dynamic range: " + f12 + "\nNo supported default mime type available.");
                        }
                        str = "video/dolby-vision";
                    }
                }
                str2 = str;
            }
            if (fVar == null) {
                f12.toString();
                C20140q0.d(3, "VideoConfigUtil");
            } else {
                f12.toString();
                C20140q0.d(3, "VideoConfigUtil");
            }
        }
        i.b bVar = new i.b();
        if (str2 == null) {
            throw new NullPointerException("Null mimeType");
        }
        bVar.f25085a = str2;
        bVar.f25086b = -1;
        if (next != null) {
            bVar.f25087c = next;
        }
        String str3 = bVar.f25085a == null ? " mimeType" : "";
        if (str3.isEmpty()) {
            return new i(bVar.f25085a, bVar.f25086b.intValue(), bVar.f25087c, null);
        }
        throw new IllegalStateException("Missing required properties:".concat(str3));
    }

    public static int d(int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i22, @N Range<Integer> range) {
        Rational rational = new Rational(i13, i14);
        Rational rational2 = new Rational(i15, i16);
        Rational rational3 = new Rational(i17, i18);
        int iDoubleValue = (int) (new Rational(i19, i22).doubleValue() * rational3.doubleValue() * rational2.doubleValue() * rational.doubleValue() * i12);
        if (C20140q0.d(3, "VideoConfigUtil")) {
            String.format("Base Bitrate(%dbps) * Bit Depth Ratio (%d / %d) * Frame Rate Ratio(%d / %d) * Width Ratio(%d / %d) * Height Ratio(%d / %d) = %d", Integer.valueOf(i12), Integer.valueOf(i13), Integer.valueOf(i14), Integer.valueOf(i15), Integer.valueOf(i16), Integer.valueOf(i17), Integer.valueOf(i18), Integer.valueOf(i19), Integer.valueOf(i22), Integer.valueOf(iDoubleValue));
        }
        if (!n0.f25321b.equals(range)) {
            Integer num = (Integer) range.clamp(Integer.valueOf(iDoubleValue));
            int iIntValue = num.intValue();
            if (C20140q0.d(3, "VideoConfigUtil")) {
                String.format("\nClamped to range %s -> %dbps", range, num);
            }
            iDoubleValue = iIntValue;
        }
        C20140q0.d(3, "VideoConfigUtil");
        return iDoubleValue;
    }
}
