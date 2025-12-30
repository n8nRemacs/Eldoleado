package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import android.util.Range;
import android.util.Size;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.v0;
import com.adjust.sdk.Constants;
import j.N;
import j.P;
import j.X;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: ExtraCroppingQuirk.java */
@X
/* loaded from: classes.dex */
public class o implements v0 {

    /* renamed from: a, reason: collision with root package name */
    public static final HashMap f23217a;

    /* compiled from: ExtraCroppingQuirk.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f23218a;

        static {
            int[] iArr = new int[SurfaceConfig.ConfigType.values().length];
            f23218a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f23218a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f23218a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static {
        HashMap map = new HashMap();
        f23217a = map;
        map.put("SM-T580", null);
        map.put("SM-J710MN", new Range(21, 26));
        map.put("SM-A320FL", null);
        map.put("SM-G570M", null);
        map.put("SM-G610F", null);
        map.put("SM-G610M", new Range(21, 26));
    }

    @P
    public static Size c(@N SurfaceConfig.ConfigType configType) {
        if (!d()) {
            return null;
        }
        int iOrdinal = configType.ordinal();
        if (iOrdinal == 0) {
            return new Size(1920, 1080);
        }
        if (iOrdinal == 1) {
            return new Size(1280, 720);
        }
        if (iOrdinal != 2) {
            return null;
        }
        return new Size(3264, 1836);
    }

    public static boolean d() {
        if (!Constants.REFERRER_API_SAMSUNG.equalsIgnoreCase(Build.BRAND)) {
            return false;
        }
        HashMap map = f23217a;
        String str = Build.MODEL;
        Locale locale = Locale.US;
        if (!map.containsKey(str.toUpperCase(locale))) {
            return false;
        }
        Range range = (Range) map.get(str.toUpperCase(locale));
        if (range == null) {
            return true;
        }
        return range.contains((Range) Integer.valueOf(Build.VERSION.SDK_INT));
    }
}
