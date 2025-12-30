package androidx.camera.camera2.internal;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Pair;
import android.util.Range;
import android.util.Size;
import androidx.camera.camera2.internal.L;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.impl.AbstractC20081a;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SurfaceConfig;
import com.adjust.sdk.Constants;
import j.InterfaceC42164u;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: SupportedSurfaceCombination.java */
@j.S
@j.X
/* loaded from: classes.dex */
final class J0 {

    /* renamed from: g, reason: collision with root package name */
    public final String f22993g;

    /* renamed from: h, reason: collision with root package name */
    public final L.a f22994h;

    /* renamed from: i, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.n f22995i;

    /* renamed from: j, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.f f22996j;

    /* renamed from: k, reason: collision with root package name */
    public final int f22997k;

    /* renamed from: l, reason: collision with root package name */
    public final boolean f22998l;

    /* renamed from: m, reason: collision with root package name */
    public final boolean f22999m;

    /* renamed from: n, reason: collision with root package name */
    public final boolean f23000n;

    /* renamed from: o, reason: collision with root package name */
    public final boolean f23001o;

    /* renamed from: p, reason: collision with root package name */
    public final boolean f23002p;

    /* renamed from: q, reason: collision with root package name */
    @j.k0
    public androidx.camera.core.impl.I0 f23003q;

    /* renamed from: s, reason: collision with root package name */
    @j.N
    public final C20013i0 f23005s;

    /* renamed from: v, reason: collision with root package name */
    public final C20015j0 f23008v;

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList f22987a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    public final ArrayList f22988b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    public final ArrayList f22989c = new ArrayList();

    /* renamed from: d, reason: collision with root package name */
    public final HashMap f22990d = new HashMap();

    /* renamed from: e, reason: collision with root package name */
    public final ArrayList f22991e = new ArrayList();

    /* renamed from: f, reason: collision with root package name */
    public final ArrayList f22992f = new ArrayList();

    /* renamed from: r, reason: collision with root package name */
    public final ArrayList f23004r = new ArrayList();

    /* renamed from: t, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.s f23006t = new androidx.camera.camera2.internal.compat.workaround.s();

    /* renamed from: u, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.p f23007u = new androidx.camera.camera2.internal.compat.workaround.p();

    /* compiled from: SupportedSurfaceCombination.java */
    @j.X
    public static class a {
        @InterfaceC42164u
        public static Size[] a(StreamConfigurationMap streamConfigurationMap, int i12) {
            return streamConfigurationMap.getHighResolutionOutputSizes(i12);
        }
    }

    /* compiled from: SupportedSurfaceCombination.java */
    @VY0.c
    public static abstract class b {
        public abstract int a();

        public abstract int b();
    }

    public J0(@j.N Context context, @j.N String str, @j.N androidx.camera.camera2.internal.compat.w wVar, @j.N L.a aVar) throws CameraUnavailableException {
        List listSingletonList;
        long[] jArr;
        this.f22998l = false;
        this.f22999m = false;
        this.f23000n = false;
        this.f23001o = false;
        this.f23002p = false;
        str.getClass();
        this.f22993g = str;
        aVar.getClass();
        this.f22994h = aVar;
        this.f22996j = new androidx.camera.camera2.internal.compat.workaround.f();
        this.f23005s = C20013i0.b(context);
        try {
            androidx.camera.camera2.internal.compat.n nVarB = wVar.b(str);
            this.f22995i = nVarB;
            Integer num = (Integer) nVarB.a(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            this.f22997k = num != null ? num.intValue() : 2;
            int[] iArr = (int[]) nVarB.a(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
            if (iArr != null) {
                for (int i12 : iArr) {
                    if (i12 == 3) {
                        this.f22998l = true;
                    } else if (i12 == 6) {
                        this.f22999m = true;
                    } else if (Build.VERSION.SDK_INT >= 31 && i12 == 16) {
                        this.f23002p = true;
                    }
                }
            }
            C20015j0 c20015j0 = new C20015j0(this.f22995i);
            this.f23008v = c20015j0;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            androidx.camera.core.impl.H0 h02 = new androidx.camera.core.impl.H0();
            SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.f24041b;
            SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.MAXIMUM;
            androidx.camera.core.impl.H0 h0B = G.b(configType, configSize, h02, arrayList2, h02);
            SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.f24043d;
            androidx.camera.core.impl.H0 h0B2 = G.b(configType2, configSize, h0B, arrayList2, h0B);
            SurfaceConfig.ConfigType configType3 = SurfaceConfig.ConfigType.f24042c;
            androidx.camera.core.impl.H0 h0B3 = G.b(configType3, configSize, h0B2, arrayList2, h0B2);
            SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.PREVIEW;
            G.k(configType, configSize2, h0B3, configType2, configSize);
            androidx.camera.core.impl.H0 h0C = G.c(arrayList2, h0B3);
            G.k(configType3, configSize2, h0C, configType2, configSize);
            androidx.camera.core.impl.H0 h0C2 = G.c(arrayList2, h0C);
            G.k(configType, configSize2, h0C2, configType, configSize2);
            androidx.camera.core.impl.H0 h0C3 = G.c(arrayList2, h0C2);
            G.k(configType, configSize2, h0C3, configType3, configSize2);
            androidx.camera.core.impl.H0 h0C4 = G.c(arrayList2, h0C3);
            G.k(configType, configSize2, h0C4, configType3, configSize2);
            h0C4.a(SurfaceConfig.a(configType2, configSize));
            arrayList2.add(h0C4);
            arrayList.addAll(arrayList2);
            int i13 = this.f22997k;
            SurfaceConfig.ConfigSize configSize3 = SurfaceConfig.ConfigSize.RECORD;
            if (i13 == 0 || i13 == 1 || i13 == 3) {
                ArrayList arrayList3 = new ArrayList();
                androidx.camera.core.impl.H0 h03 = new androidx.camera.core.impl.H0();
                G.k(configType, configSize2, h03, configType, configSize3);
                androidx.camera.core.impl.H0 h0C5 = G.c(arrayList3, h03);
                G.k(configType, configSize2, h0C5, configType3, configSize3);
                androidx.camera.core.impl.H0 h0C6 = G.c(arrayList3, h0C5);
                G.k(configType3, configSize2, h0C6, configType3, configSize3);
                androidx.camera.core.impl.H0 h0C7 = G.c(arrayList3, h0C6);
                G.k(configType, configSize2, h0C7, configType, configSize3);
                androidx.camera.core.impl.H0 h0B4 = G.b(configType2, configSize3, h0C7, arrayList3, h0C7);
                G.k(configType, configSize2, h0B4, configType3, configSize3);
                androidx.camera.core.impl.H0 h0B5 = G.b(configType2, configSize3, h0B4, arrayList3, h0B4);
                G.k(configType3, configSize2, h0B5, configType3, configSize2);
                h0B5.a(SurfaceConfig.a(configType2, configSize));
                arrayList3.add(h0B5);
                arrayList.addAll(arrayList3);
            }
            SurfaceConfig.ConfigSize configSize4 = SurfaceConfig.ConfigSize.VGA;
            if (i13 == 1 || i13 == 3) {
                ArrayList arrayList4 = new ArrayList();
                androidx.camera.core.impl.H0 h04 = new androidx.camera.core.impl.H0();
                G.k(configType, configSize2, h04, configType, configSize);
                androidx.camera.core.impl.H0 h0C8 = G.c(arrayList4, h04);
                G.k(configType, configSize2, h0C8, configType3, configSize);
                androidx.camera.core.impl.H0 h0C9 = G.c(arrayList4, h0C8);
                G.k(configType3, configSize2, h0C9, configType3, configSize);
                androidx.camera.core.impl.H0 h0C10 = G.c(arrayList4, h0C9);
                G.k(configType, configSize2, h0C10, configType, configSize2);
                androidx.camera.core.impl.H0 h0B6 = G.b(configType2, configSize, h0C10, arrayList4, h0C10);
                G.k(configType3, configSize4, h0B6, configType, configSize2);
                androidx.camera.core.impl.H0 h0B7 = G.b(configType3, configSize, h0B6, arrayList4, h0B6);
                G.k(configType3, configSize4, h0B7, configType3, configSize2);
                h0B7.a(SurfaceConfig.a(configType3, configSize));
                arrayList4.add(h0B7);
                arrayList.addAll(arrayList4);
            }
            SurfaceConfig.ConfigType configType4 = SurfaceConfig.ConfigType.f24044e;
            if (this.f22998l) {
                ArrayList arrayList5 = new ArrayList();
                androidx.camera.core.impl.H0 h05 = new androidx.camera.core.impl.H0();
                androidx.camera.core.impl.H0 h0B8 = G.b(configType4, configSize, h05, arrayList5, h05);
                G.k(configType, configSize2, h0B8, configType4, configSize);
                androidx.camera.core.impl.H0 h0C11 = G.c(arrayList5, h0B8);
                G.k(configType3, configSize2, h0C11, configType4, configSize);
                androidx.camera.core.impl.H0 h0C12 = G.c(arrayList5, h0C11);
                G.k(configType, configSize2, h0C12, configType, configSize2);
                androidx.camera.core.impl.H0 h0B9 = G.b(configType4, configSize, h0C12, arrayList5, h0C12);
                G.k(configType, configSize2, h0B9, configType3, configSize2);
                androidx.camera.core.impl.H0 h0B10 = G.b(configType4, configSize, h0B9, arrayList5, h0B9);
                G.k(configType3, configSize2, h0B10, configType3, configSize2);
                androidx.camera.core.impl.H0 h0B11 = G.b(configType4, configSize, h0B10, arrayList5, h0B10);
                G.k(configType, configSize2, h0B11, configType2, configSize);
                androidx.camera.core.impl.H0 h0B12 = G.b(configType4, configSize, h0B11, arrayList5, h0B11);
                G.k(configType3, configSize2, h0B12, configType2, configSize);
                h0B12.a(SurfaceConfig.a(configType4, configSize));
                arrayList5.add(h0B12);
                arrayList.addAll(arrayList5);
            }
            if (this.f22999m && i13 == 0) {
                ArrayList arrayList6 = new ArrayList();
                androidx.camera.core.impl.H0 h06 = new androidx.camera.core.impl.H0();
                G.k(configType, configSize2, h06, configType, configSize);
                androidx.camera.core.impl.H0 h0C13 = G.c(arrayList6, h06);
                G.k(configType, configSize2, h0C13, configType3, configSize);
                androidx.camera.core.impl.H0 h0C14 = G.c(arrayList6, h0C13);
                G.k(configType3, configSize2, h0C14, configType3, configSize);
                arrayList6.add(h0C14);
                arrayList.addAll(arrayList6);
            }
            if (i13 == 3) {
                ArrayList arrayList7 = new ArrayList();
                androidx.camera.core.impl.H0 h07 = new androidx.camera.core.impl.H0();
                G.k(configType, configSize2, h07, configType, configSize4);
                G.k(configType3, configSize, h07, configType4, configSize);
                androidx.camera.core.impl.H0 h0C15 = G.c(arrayList7, h07);
                G.k(configType, configSize2, h0C15, configType, configSize4);
                G.k(configType2, configSize, h0C15, configType4, configSize);
                arrayList7.add(h0C15);
                arrayList.addAll(arrayList7);
            }
            ArrayList arrayList8 = this.f22987a;
            arrayList8.addAll(arrayList);
            if (this.f22996j.f23252a == null) {
                listSingletonList = new ArrayList();
            } else {
                androidx.camera.core.impl.H0 h08 = androidx.camera.camera2.internal.compat.quirk.q.f23219a;
                String str2 = Build.DEVICE;
                boolean z12 = "heroqltevzw".equalsIgnoreCase(str2) || "heroqltetmo".equalsIgnoreCase(str2);
                androidx.camera.core.impl.H0 h09 = androidx.camera.camera2.internal.compat.quirk.q.f23219a;
                if (z12) {
                    ArrayList arrayList9 = new ArrayList();
                    listSingletonList = arrayList9;
                    if (this.f22993g.equals("1")) {
                        arrayList9.add(h09);
                        listSingletonList = arrayList9;
                    }
                } else {
                    String str3 = Build.BRAND;
                    if (!Constants.REFERRER_API_SAMSUNG.equalsIgnoreCase(str3) ? false : androidx.camera.camera2.internal.compat.quirk.q.f23222d.contains(Build.MODEL.toUpperCase(Locale.US))) {
                        ArrayList arrayList10 = new ArrayList();
                        listSingletonList = arrayList10;
                        if (i13 == 0) {
                            arrayList10.add(h09);
                            arrayList10.add(androidx.camera.camera2.internal.compat.quirk.q.f23220b);
                            listSingletonList = arrayList10;
                        }
                    } else {
                        listSingletonList = !Constants.REFERRER_API_GOOGLE.equalsIgnoreCase(str3) ? false : androidx.camera.camera2.internal.compat.quirk.q.f23223e.contains(Build.MODEL.toUpperCase(Locale.US)) ? Collections.singletonList(androidx.camera.camera2.internal.compat.quirk.q.f23221c) : Collections.emptyList();
                    }
                }
            }
            arrayList8.addAll(listSingletonList);
            if (this.f23002p) {
                ArrayList arrayList11 = new ArrayList();
                androidx.camera.core.impl.H0 h010 = new androidx.camera.core.impl.H0();
                SurfaceConfig.ConfigSize configSize5 = SurfaceConfig.ConfigSize.ULTRA_MAXIMUM;
                G.k(configType3, configSize5, h010, configType, configSize2);
                androidx.camera.core.impl.H0 h0B13 = G.b(configType, configSize3, h010, arrayList11, h010);
                G.k(configType2, configSize5, h0B13, configType, configSize2);
                androidx.camera.core.impl.H0 h0B14 = G.b(configType, configSize3, h0B13, arrayList11, h0B13);
                G.k(configType4, configSize5, h0B14, configType, configSize2);
                androidx.camera.core.impl.H0 h0B15 = G.b(configType, configSize3, h0B14, arrayList11, h0B14);
                G.k(configType3, configSize5, h0B15, configType, configSize2);
                androidx.camera.core.impl.H0 h0B16 = G.b(configType2, configSize, h0B15, arrayList11, h0B15);
                G.k(configType2, configSize5, h0B16, configType, configSize2);
                androidx.camera.core.impl.H0 h0B17 = G.b(configType2, configSize, h0B16, arrayList11, h0B16);
                G.k(configType4, configSize5, h0B17, configType, configSize2);
                androidx.camera.core.impl.H0 h0B18 = G.b(configType2, configSize, h0B17, arrayList11, h0B17);
                G.k(configType3, configSize5, h0B18, configType, configSize2);
                androidx.camera.core.impl.H0 h0B19 = G.b(configType3, configSize, h0B18, arrayList11, h0B18);
                G.k(configType2, configSize5, h0B19, configType, configSize2);
                androidx.camera.core.impl.H0 h0B20 = G.b(configType3, configSize, h0B19, arrayList11, h0B19);
                G.k(configType4, configSize5, h0B20, configType, configSize2);
                androidx.camera.core.impl.H0 h0B21 = G.b(configType3, configSize, h0B20, arrayList11, h0B20);
                G.k(configType3, configSize5, h0B21, configType, configSize2);
                androidx.camera.core.impl.H0 h0B22 = G.b(configType4, configSize, h0B21, arrayList11, h0B21);
                G.k(configType2, configSize5, h0B22, configType, configSize2);
                androidx.camera.core.impl.H0 h0B23 = G.b(configType4, configSize, h0B22, arrayList11, h0B22);
                G.k(configType4, configSize5, h0B23, configType, configSize2);
                h0B23.a(SurfaceConfig.a(configType4, configSize));
                arrayList11.add(h0B23);
                this.f22988b.addAll(arrayList11);
            }
            boolean zHasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.camera.concurrent");
            this.f23000n = zHasSystemFeature;
            SurfaceConfig.ConfigSize configSize6 = SurfaceConfig.ConfigSize.s1440p;
            if (zHasSystemFeature) {
                ArrayList arrayList12 = new ArrayList();
                androidx.camera.core.impl.H0 h011 = new androidx.camera.core.impl.H0();
                androidx.camera.core.impl.H0 h0B24 = G.b(configType3, configSize6, h011, arrayList12, h011);
                androidx.camera.core.impl.H0 h0B25 = G.b(configType, configSize6, h0B24, arrayList12, h0B24);
                androidx.camera.core.impl.H0 h0B26 = G.b(configType2, configSize6, h0B25, arrayList12, h0B25);
                SurfaceConfig.ConfigSize configSize7 = SurfaceConfig.ConfigSize.s720p;
                G.k(configType3, configSize7, h0B26, configType2, configSize6);
                androidx.camera.core.impl.H0 h0C16 = G.c(arrayList12, h0B26);
                G.k(configType, configSize7, h0C16, configType2, configSize6);
                androidx.camera.core.impl.H0 h0C17 = G.c(arrayList12, h0C16);
                G.k(configType3, configSize7, h0C17, configType3, configSize6);
                androidx.camera.core.impl.H0 h0C18 = G.c(arrayList12, h0C17);
                G.k(configType3, configSize7, h0C18, configType, configSize6);
                androidx.camera.core.impl.H0 h0C19 = G.c(arrayList12, h0C18);
                G.k(configType, configSize7, h0C19, configType3, configSize6);
                androidx.camera.core.impl.H0 h0C20 = G.c(arrayList12, h0C19);
                G.k(configType, configSize7, h0C20, configType, configSize6);
                arrayList12.add(h0C20);
                this.f22989c.addAll(arrayList12);
            }
            if (c20015j0.f23354c) {
                ArrayList arrayList13 = new ArrayList();
                androidx.camera.core.impl.H0 h012 = new androidx.camera.core.impl.H0();
                androidx.camera.core.impl.H0 h0B27 = G.b(configType, configSize, h012, arrayList13, h012);
                androidx.camera.core.impl.H0 h0B28 = G.b(configType3, configSize, h0B27, arrayList13, h0B27);
                G.k(configType, configSize2, h0B28, configType2, configSize);
                androidx.camera.core.impl.H0 h0C21 = G.c(arrayList13, h0B28);
                G.k(configType, configSize2, h0C21, configType3, configSize);
                androidx.camera.core.impl.H0 h0C22 = G.c(arrayList13, h0C21);
                G.k(configType3, configSize2, h0C22, configType3, configSize);
                androidx.camera.core.impl.H0 h0C23 = G.c(arrayList13, h0C22);
                G.k(configType, configSize2, h0C23, configType, configSize3);
                androidx.camera.core.impl.H0 h0C24 = G.c(arrayList13, h0C23);
                G.k(configType, configSize2, h0C24, configType, configSize3);
                androidx.camera.core.impl.H0 h0B29 = G.b(configType3, configSize3, h0C24, arrayList13, h0C24);
                G.k(configType, configSize2, h0B29, configType, configSize3);
                h0B29.a(SurfaceConfig.a(configType2, configSize3));
                arrayList13.add(h0B29);
                this.f22991e.addAll(arrayList13);
            }
            androidx.camera.camera2.internal.compat.n nVar = this.f22995i;
            Config.a<Long> aVar2 = H0.f22982a;
            int i14 = Build.VERSION.SDK_INT;
            boolean z13 = (i14 < 33 || (jArr = (long[]) nVar.a(CameraCharacteristics.SCALER_AVAILABLE_STREAM_USE_CASES)) == null || jArr.length == 0) ? false : true;
            this.f23001o = z13;
            if (z13 && i14 >= 33) {
                ArrayList arrayList14 = new ArrayList();
                androidx.camera.core.impl.H0 h013 = new androidx.camera.core.impl.H0();
                h013.a(SurfaceConfig.b(configType, configSize6, 4L));
                arrayList14.add(h013);
                androidx.camera.core.impl.H0 h014 = new androidx.camera.core.impl.H0();
                h014.a(SurfaceConfig.b(configType3, configSize6, 4L));
                arrayList14.add(h014);
                androidx.camera.core.impl.H0 h015 = new androidx.camera.core.impl.H0();
                h015.a(SurfaceConfig.b(configType, configSize3, 3L));
                arrayList14.add(h015);
                androidx.camera.core.impl.H0 h016 = new androidx.camera.core.impl.H0();
                h016.a(SurfaceConfig.b(configType3, configSize3, 3L));
                arrayList14.add(h016);
                androidx.camera.core.impl.H0 h017 = new androidx.camera.core.impl.H0();
                h017.a(SurfaceConfig.b(configType2, configSize, 2L));
                arrayList14.add(h017);
                androidx.camera.core.impl.H0 h018 = new androidx.camera.core.impl.H0();
                h018.a(SurfaceConfig.b(configType3, configSize, 2L));
                arrayList14.add(h018);
                androidx.camera.core.impl.H0 h019 = new androidx.camera.core.impl.H0();
                h019.a(SurfaceConfig.b(configType, configSize2, 1L));
                h019.a(SurfaceConfig.b(configType2, configSize, 2L));
                arrayList14.add(h019);
                androidx.camera.core.impl.H0 h020 = new androidx.camera.core.impl.H0();
                h020.a(SurfaceConfig.b(configType, configSize2, 1L));
                h020.a(SurfaceConfig.b(configType3, configSize, 2L));
                arrayList14.add(h020);
                androidx.camera.core.impl.H0 h021 = new androidx.camera.core.impl.H0();
                h021.a(SurfaceConfig.b(configType, configSize2, 1L));
                h021.a(SurfaceConfig.b(configType, configSize3, 3L));
                arrayList14.add(h021);
                androidx.camera.core.impl.H0 h022 = new androidx.camera.core.impl.H0();
                h022.a(SurfaceConfig.b(configType, configSize2, 1L));
                h022.a(SurfaceConfig.b(configType3, configSize3, 3L));
                arrayList14.add(h022);
                androidx.camera.core.impl.H0 h023 = new androidx.camera.core.impl.H0();
                h023.a(SurfaceConfig.b(configType, configSize2, 1L));
                h023.a(SurfaceConfig.b(configType3, configSize2, 1L));
                arrayList14.add(h023);
                androidx.camera.core.impl.H0 h024 = new androidx.camera.core.impl.H0();
                h024.a(SurfaceConfig.b(configType, configSize2, 1L));
                h024.a(SurfaceConfig.b(configType, configSize3, 3L));
                h024.a(SurfaceConfig.b(configType2, configSize3, 2L));
                arrayList14.add(h024);
                androidx.camera.core.impl.H0 h025 = new androidx.camera.core.impl.H0();
                h025.a(SurfaceConfig.b(configType, configSize2, 1L));
                h025.a(SurfaceConfig.b(configType3, configSize3, 3L));
                h025.a(SurfaceConfig.b(configType2, configSize3, 2L));
                arrayList14.add(h025);
                androidx.camera.core.impl.H0 h026 = new androidx.camera.core.impl.H0();
                h026.a(SurfaceConfig.b(configType, configSize2, 1L));
                h026.a(SurfaceConfig.b(configType3, configSize2, 1L));
                h026.a(SurfaceConfig.b(configType2, configSize, 2L));
                arrayList14.add(h026);
                this.f22992f.addAll(arrayList14);
            }
            b();
        } catch (CameraAccessExceptionCompat e12) {
            throw X.a(e12);
        }
    }

    public static Size c(StreamConfigurationMap streamConfigurationMap, int i12, boolean z12) {
        Size[] sizeArrA;
        Size[] outputSizes = i12 == 34 ? streamConfigurationMap.getOutputSizes(SurfaceTexture.class) : streamConfigurationMap.getOutputSizes(i12);
        if (outputSizes == null || outputSizes.length == 0) {
            return null;
        }
        androidx.camera.core.impl.utils.g gVar = new androidx.camera.core.impl.utils.g();
        Size size = (Size) Collections.max(Arrays.asList(outputSizes), gVar);
        Size size2 = androidx.camera.core.internal.utils.c.f24370a;
        if (z12 && (sizeArrA = a.a(streamConfigurationMap, i12)) != null && sizeArrA.length > 0) {
            size2 = (Size) Collections.max(Arrays.asList(sizeArrA), gVar);
        }
        return (Size) Collections.max(Arrays.asList(size, size2), gVar);
    }

    public static int e(Range<Integer> range, Range<Integer> range2) {
        androidx.core.util.z.g("Ranges must not intersect", (range.contains((Range<Integer>) range2.getUpper()) || range.contains((Range<Integer>) range2.getLower())) ? false : true);
        return ((Integer) range.getLower()).intValue() > ((Integer) range2.getUpper()).intValue() ? ((Integer) range.getLower()).intValue() - ((Integer) range2.getUpper()).intValue() : ((Integer) range2.getLower()).intValue() - ((Integer) range.getUpper()).intValue();
    }

    public static int f(Range<Integer> range) {
        return (((Integer) range.getUpper()).intValue() - ((Integer) range.getLower()).intValue()) + 1;
    }

    public final boolean a(@j.N C20004e c20004e, List list) {
        List list2;
        HashMap map = this.f22990d;
        if (map.containsKey(c20004e)) {
            list2 = (List) map.get(c20004e);
        } else {
            ArrayList arrayList = new ArrayList();
            int i12 = c20004e.f23323a;
            int i13 = c20004e.f23324b;
            if (i13 == 8) {
                if (i12 != 1) {
                    ArrayList arrayList2 = this.f22987a;
                    if (i12 != 2) {
                        arrayList.addAll(arrayList2);
                    } else {
                        arrayList.addAll(this.f22988b);
                        arrayList.addAll(arrayList2);
                    }
                } else {
                    arrayList = this.f22989c;
                }
            } else if (i13 == 10 && i12 == 0) {
                arrayList.addAll(this.f22991e);
            }
            map.put(c20004e, arrayList);
            list2 = arrayList;
        }
        Iterator it = list2.iterator();
        boolean z12 = false;
        while (it.hasNext()) {
            z12 = ((androidx.camera.core.impl.H0) it.next()).c(list) != null;
            if (z12) {
                break;
            }
        }
        return z12;
    }

    public final void b() {
        Size size;
        Size size2;
        int i12;
        CamcorderProfile camcorderProfile;
        CamcorderProfile camcorderProfile2;
        Size sizeE = this.f23005s.e();
        try {
            i12 = Integer.parseInt(this.f22993g);
            this.f22994h.getClass();
            camcorderProfile = null;
            camcorderProfile2 = CamcorderProfile.hasProfile(i12, 1) ? CamcorderProfile.get(i12, 1) : null;
        } catch (NumberFormatException unused) {
            Size[] outputSizes = this.f22995i.b().b().getOutputSizes(MediaRecorder.class);
            if (outputSizes != null) {
                Arrays.sort(outputSizes, new androidx.camera.core.impl.utils.g(true));
                int length = outputSizes.length;
                int i13 = 0;
                while (true) {
                    if (i13 >= length) {
                        size = androidx.camera.core.internal.utils.c.f24373d;
                        break;
                    }
                    Size size3 = outputSizes[i13];
                    int width = size3.getWidth();
                    Size size4 = androidx.camera.core.internal.utils.c.f24375f;
                    if (width <= size4.getWidth() && size3.getHeight() <= size4.getHeight()) {
                        size = size3;
                        break;
                    }
                    i13++;
                }
            } else {
                size = androidx.camera.core.internal.utils.c.f24373d;
            }
        }
        if (camcorderProfile2 != null) {
            size2 = new Size(camcorderProfile2.videoFrameWidth, camcorderProfile2.videoFrameHeight);
            this.f23003q = androidx.camera.core.impl.I0.a(androidx.camera.core.internal.utils.c.f24372c, new HashMap(), sizeE, new HashMap(), size2, new HashMap(), new HashMap());
        }
        size = androidx.camera.core.internal.utils.c.f24373d;
        if (CamcorderProfile.hasProfile(i12, 10)) {
            camcorderProfile = CamcorderProfile.get(i12, 10);
        } else if (CamcorderProfile.hasProfile(i12, 8)) {
            camcorderProfile = CamcorderProfile.get(i12, 8);
        } else if (CamcorderProfile.hasProfile(i12, 12)) {
            camcorderProfile = CamcorderProfile.get(i12, 12);
        } else if (CamcorderProfile.hasProfile(i12, 6)) {
            camcorderProfile = CamcorderProfile.get(i12, 6);
        } else if (CamcorderProfile.hasProfile(i12, 5)) {
            camcorderProfile = CamcorderProfile.get(i12, 5);
        } else if (CamcorderProfile.hasProfile(i12, 4)) {
            camcorderProfile = CamcorderProfile.get(i12, 4);
        }
        if (camcorderProfile != null) {
            size = new Size(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        }
        size2 = size;
        this.f23003q = androidx.camera.core.impl.I0.a(androidx.camera.core.internal.utils.c.f24372c, new HashMap(), sizeE, new HashMap(), size2, new HashMap(), new HashMap());
    }

    @j.P
    public final List d(@j.N C20004e c20004e, List list) {
        Config.a<Long> aVar = H0.f22982a;
        if (c20004e.f23323a == 0 && c20004e.f23324b == 8) {
            Iterator it = this.f22992f.iterator();
            while (it.hasNext()) {
                List<SurfaceConfig> listC = ((androidx.camera.core.impl.H0) it.next()).c(list);
                if (listC != null) {
                    return listC;
                }
            }
        }
        return null;
    }

    public final Pair g(int i12, ArrayList arrayList, List list, ArrayList arrayList2, ArrayList arrayList3, int i13, @j.P HashMap map, @j.P HashMap map2) {
        int outputMinFrameDuration;
        ArrayList arrayList4 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AbstractC20081a abstractC20081a = (AbstractC20081a) it.next();
            arrayList4.add(abstractC20081a.g());
            if (map != null) {
                map.put(Integer.valueOf(arrayList4.size() - 1), abstractC20081a);
            }
        }
        for (int i14 = 0; i14 < list.size(); i14++) {
            Size size = (Size) list.get(i14);
            androidx.camera.core.impl.L0 l02 = (androidx.camera.core.impl.L0) arrayList2.get(((Integer) arrayList3.get(i14)).intValue());
            int inputFormat = l02.getInputFormat();
            arrayList4.add(SurfaceConfig.f(i12, inputFormat, size, h(inputFormat)));
            if (map2 != null) {
                map2.put(Integer.valueOf(arrayList4.size() - 1), l02);
            }
            try {
                outputMinFrameDuration = (int) (1.0E9d / ((StreamConfigurationMap) this.f22995i.a(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputMinFrameDuration(l02.getInputFormat(), size));
            } catch (Exception unused) {
                outputMinFrameDuration = 0;
            }
            i13 = Math.min(i13, outputMinFrameDuration);
        }
        return new Pair(arrayList4, Integer.valueOf(i13));
    }

    @j.N
    @j.k0
    public final androidx.camera.core.impl.I0 h(int i12) {
        StreamConfigurationMap streamConfigurationMap;
        ArrayList arrayList = this.f23004r;
        if (!arrayList.contains(Integer.valueOf(i12))) {
            i(this.f23003q.g(), androidx.camera.core.internal.utils.c.f24374e, i12);
            i(this.f23003q.f(), androidx.camera.core.internal.utils.c.f24376g, i12);
            Map<Integer, Size> mapC = this.f23003q.c();
            androidx.camera.camera2.internal.compat.n nVar = this.f22995i;
            Size sizeC = c(nVar.b().b(), i12, true);
            if (sizeC != null) {
                mapC.put(Integer.valueOf(i12), sizeC);
            }
            Map<Integer, Size> mapH = this.f23003q.h();
            if (Build.VERSION.SDK_INT >= 31 && this.f23002p && (streamConfigurationMap = (StreamConfigurationMap) nVar.a(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION)) != null) {
                mapH.put(Integer.valueOf(i12), c(streamConfigurationMap, i12, true));
            }
            arrayList.add(Integer.valueOf(i12));
        }
        return this.f23003q;
    }

    public final void i(@j.N Map<Integer, Size> map, @j.N Size size, int i12) {
        if (this.f23000n) {
            Size sizeC = c(this.f22995i.b().b(), i12, false);
            Integer numValueOf = Integer.valueOf(i12);
            if (sizeC != null) {
                size = (Size) Collections.min(Arrays.asList(size, sizeC), new androidx.camera.core.impl.utils.g());
            }
            map.put(numValueOf, size);
        }
    }
}
