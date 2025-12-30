package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.DynamicRangeProfiles;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.camera.core.F;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: DynamicRangeConversions.java */
@X
/* loaded from: classes.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public static final HashMap f23180a;

    /* renamed from: b, reason: collision with root package name */
    public static final HashMap f23181b;

    static {
        F f12;
        HashMap map = new HashMap();
        f23180a = map;
        HashMap map2 = new HashMap();
        f23181b = map2;
        F f13 = F.f23562d;
        map.put(1L, f13);
        map2.put(f13, Collections.singletonList(1L));
        map.put(2L, F.f23563e);
        map2.put((F) map.get(2L), Collections.singletonList(2L));
        F f14 = F.f23564f;
        map.put(4L, f14);
        map2.put(f14, Collections.singletonList(4L));
        F f15 = F.f23565g;
        map.put(8L, f15);
        map2.put(f15, Collections.singletonList(8L));
        List listAsList = Arrays.asList(64L, 128L, 16L, 32L);
        Iterator it = listAsList.iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            f12 = F.f23566h;
            if (!zHasNext) {
                break;
            }
            f23180a.put((Long) it.next(), f12);
        }
        f23181b.put(f12, listAsList);
        List listAsList2 = Arrays.asList(1024L, Long.valueOf(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH), 256L, 512L);
        Iterator it2 = listAsList2.iterator();
        while (true) {
            boolean zHasNext2 = it2.hasNext();
            F f16 = F.f23567i;
            if (!zHasNext2) {
                f23181b.put(f16, listAsList2);
                return;
            } else {
                f23180a.put((Long) it2.next(), f16);
            }
        }
    }

    @InterfaceC42164u
    @P
    public static Long a(@N F f12, @N DynamicRangeProfiles dynamicRangeProfiles) {
        List<Long> list = (List) f23181b.get(f12);
        if (list == null) {
            return null;
        }
        Set supportedProfiles = dynamicRangeProfiles.getSupportedProfiles();
        for (Long l12 : list) {
            if (supportedProfiles.contains(l12)) {
                return l12;
            }
        }
        return null;
    }

    @InterfaceC42164u
    @P
    public static F b(long j12) {
        return (F) f23180a.get(Long.valueOf(j12));
    }
}
