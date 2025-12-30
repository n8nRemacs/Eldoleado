package androidx.camera.video.internal.utils;

import androidx.camera.core.F;
import j.X;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import okhttp3.internal.http2.Http2;

/* compiled from: DynamicRangeUtil.java */
@X
/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final HashMap f25266a;

    /* renamed from: b, reason: collision with root package name */
    public static final HashMap f25267b;

    /* renamed from: c, reason: collision with root package name */
    public static final HashMap f25268c;

    static {
        HashMap map = new HashMap();
        f25266a = map;
        HashMap map2 = new HashMap();
        f25267b = map2;
        HashMap map3 = new HashMap();
        HashMap map4 = new HashMap();
        f25268c = map4;
        map.put(8, new HashSet(Collections.singletonList(8)));
        map.put(10, new HashSet(Collections.singletonList(10)));
        map.put(0, new HashSet(Arrays.asList(8, 10)));
        map2.put(0, new HashSet(Arrays.asList(0, 1, 2, 3, 4)));
        map2.put(1, new HashSet(Collections.singletonList(0)));
        map2.put(2, new HashSet(Arrays.asList(1, 2, 3, 4)));
        map2.put(3, new HashSet(Collections.singletonList(1)));
        map2.put(4, new HashSet(Collections.singletonList(2)));
        map2.put(5, new HashSet(Collections.singletonList(3)));
        map2.put(6, new HashSet(Collections.singletonList(4)));
        map3.put(0, 1);
        map3.put(1, 3);
        map3.put(2, 4);
        map3.put(3, 5);
        map3.put(4, 6);
        HashMap map5 = new HashMap();
        F f12 = F.f23562d;
        map5.put(f12, 1);
        F f13 = F.f23563e;
        map5.put(f13, 2);
        F f14 = F.f23564f;
        map5.put(f14, 4096);
        F f15 = F.f23565g;
        map5.put(f15, 8192);
        HashMap map6 = new HashMap();
        map6.put(f12, 1);
        map6.put(f13, 2);
        map6.put(f14, 4096);
        map6.put(f15, 8192);
        HashMap map7 = new HashMap();
        map7.put(f12, 1);
        map7.put(f13, 4);
        map7.put(f14, 4096);
        map7.put(f15, Integer.valueOf(Http2.INITIAL_MAX_FRAME_SIZE));
        HashMap map8 = new HashMap();
        map8.put(F.f23566h, 256);
        map8.put(F.f23567i, 512);
        map4.put("video/hevc", map5);
        map4.put("video/av01", map6);
        map4.put("video/x-vnd.on2.vp9", map7);
        map4.put("video/dolby-vision", map8);
    }
}
