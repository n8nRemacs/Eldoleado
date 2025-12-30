package androidx.camera.camera2.internal;

import android.os.Build;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.UseCaseConfigFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: StreamUseCaseUtil.java */
@j.X
/* loaded from: classes.dex */
public final class H0 {

    /* renamed from: a, reason: collision with root package name */
    public static final Config.a<Long> f22982a = Config.a.a(Long.TYPE, "camera2.streamSpec.streamUseCase");

    /* renamed from: b, reason: collision with root package name */
    public static final HashMap f22983b;

    /* renamed from: c, reason: collision with root package name */
    public static final HashMap f22984c;

    static {
        HashMap map = new HashMap();
        f22983b = map;
        HashMap map2 = new HashMap();
        f22984c = map2;
        if (Build.VERSION.SDK_INT >= 33) {
            HashSet hashSet = new HashSet();
            UseCaseConfigFactory.CaptureType captureType = UseCaseConfigFactory.CaptureType.f24055c;
            hashSet.add(captureType);
            map.put(4L, hashSet);
            HashSet hashSet2 = new HashSet();
            hashSet2.add(captureType);
            hashSet2.add(UseCaseConfigFactory.CaptureType.f24056d);
            map.put(1L, hashSet2);
            HashSet hashSet3 = new HashSet();
            UseCaseConfigFactory.CaptureType captureType2 = UseCaseConfigFactory.CaptureType.f24054b;
            hashSet3.add(captureType2);
            map.put(2L, hashSet3);
            HashSet hashSet4 = new HashSet();
            UseCaseConfigFactory.CaptureType captureType3 = UseCaseConfigFactory.CaptureType.f24057e;
            hashSet4.add(captureType3);
            map.put(3L, hashSet4);
            HashSet hashSet5 = new HashSet();
            hashSet5.add(captureType);
            hashSet5.add(captureType2);
            hashSet5.add(captureType3);
            map2.put(4L, hashSet5);
            HashSet hashSet6 = new HashSet();
            hashSet6.add(captureType);
            hashSet6.add(captureType3);
            map2.put(3L, hashSet6);
        }
    }

    @j.S
    @j.P
    public static androidx.camera.camera2.impl.b a(Config config, long j12) {
        Config.a<Long> aVar = f22982a;
        if (config.c(aVar) && ((Long) config.f(aVar)).longValue() == j12) {
            return null;
        }
        androidx.camera.core.impl.n0 n0VarS = androidx.camera.core.impl.n0.S(config);
        n0VarS.K(aVar, Long.valueOf(j12));
        return new androidx.camera.camera2.impl.b(n0VarS);
    }

    public static boolean b(UseCaseConfigFactory.CaptureType captureType, long j12, List<UseCaseConfigFactory.CaptureType> list) {
        if (Build.VERSION.SDK_INT < 33) {
            return false;
        }
        if (captureType != UseCaseConfigFactory.CaptureType.f24058f) {
            HashMap map = f22983b;
            return map.containsKey(Long.valueOf(j12)) && ((Set) map.get(Long.valueOf(j12))).contains(captureType);
        }
        HashMap map2 = f22984c;
        if (!map2.containsKey(Long.valueOf(j12))) {
            return false;
        }
        Set set = (Set) map2.get(Long.valueOf(j12));
        if (list.size() != set.size()) {
            return false;
        }
        Iterator<UseCaseConfigFactory.CaptureType> it = list.iterator();
        while (it.hasNext()) {
            if (!set.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean c(Config config, UseCaseConfigFactory.CaptureType captureType) {
        if (((Boolean) config.g(androidx.camera.core.impl.L0.f24004x, Boolean.FALSE)).booleanValue()) {
            return false;
        }
        Config.a<Integer> aVar = androidx.camera.core.impl.Z.f24070G;
        if (!config.c(aVar)) {
            return false;
        }
        int iIntValue = ((Integer) config.f(aVar)).intValue();
        int iOrdinal = captureType.ordinal();
        return iOrdinal != 0 ? iOrdinal != 3 ? false : false : iIntValue == 2;
    }
}
