package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.util.Size;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.core.C20140q0;
import com.adjust.sdk.Constants;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: StreamConfigurationMapCompat.java */
@X
/* loaded from: classes.dex */
public class B {

    /* renamed from: a, reason: collision with root package name */
    public final C f23125a;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.m f23126b;

    /* renamed from: c, reason: collision with root package name */
    public final HashMap f23127c = new HashMap();

    /* compiled from: StreamConfigurationMapCompat.java */
    public interface a {
    }

    public B(@N StreamConfigurationMap streamConfigurationMap, @N androidx.camera.camera2.internal.compat.workaround.m mVar) {
        new HashMap();
        new HashMap();
        this.f23125a = new C(streamConfigurationMap);
        this.f23126b = mVar;
    }

    @P
    public final Size[] a(int i12) {
        List listEmptyList;
        List arrayList;
        ArrayList arrayList2;
        HashMap map = this.f23127c;
        if (map.containsKey(Integer.valueOf(i12))) {
            if (((Size[]) map.get(Integer.valueOf(i12))) == null) {
                return null;
            }
            return (Size[]) ((Size[]) map.get(Integer.valueOf(i12))).clone();
        }
        Size[] outputSizes = this.f23125a.f23131a.getOutputSizes(i12);
        if (outputSizes == null || outputSizes.length == 0) {
            C20140q0.d(5, "StreamConfigurationMapCompat");
            return outputSizes;
        }
        androidx.camera.camera2.internal.compat.workaround.m mVar = this.f23126b;
        mVar.getClass();
        ArrayList arrayList3 = new ArrayList(Arrays.asList(outputSizes));
        if (mVar.f23259a != null) {
            Size[] sizeArr = (i12 == 34 && "motorola".equalsIgnoreCase(Build.BRAND) && "moto e5 play".equalsIgnoreCase(Build.MODEL)) ? new Size[]{new Size(1920, 1080), new Size(1440, 1080), new Size(1280, 720), new Size(960, 720), new Size(864, 480), new Size(720, 480)} : new Size[0];
            if (sizeArr.length > 0) {
                arrayList3.addAll(Arrays.asList(sizeArr));
            }
        }
        androidx.camera.camera2.internal.compat.workaround.e eVar = mVar.f23260b;
        eVar.getClass();
        if (((androidx.camera.camera2.internal.compat.quirk.n) C20001l.f23216a.b(androidx.camera.camera2.internal.compat.quirk.n.class)) == null) {
            arrayList = new ArrayList();
        } else {
            String str = Build.BRAND;
            boolean z12 = "OnePlus".equalsIgnoreCase(str) && "OnePlus6".equalsIgnoreCase(Build.DEVICE);
            String str2 = eVar.f23251a;
            if (z12) {
                arrayList2 = new ArrayList();
                if (str2.equals("0") && i12 == 256) {
                    arrayList2.add(new Size(4160, 3120));
                    arrayList2.add(new Size(4000, 3000));
                }
            } else if ("OnePlus".equalsIgnoreCase(str) && "OnePlus6T".equalsIgnoreCase(Build.DEVICE)) {
                arrayList2 = new ArrayList();
                if (str2.equals("0") && i12 == 256) {
                    arrayList2.add(new Size(4160, 3120));
                    arrayList2.add(new Size(4000, 3000));
                }
            } else {
                if ("HUAWEI".equalsIgnoreCase(str) && "HWANE".equalsIgnoreCase(Build.DEVICE)) {
                    ArrayList arrayList4 = new ArrayList();
                    listEmptyList = arrayList4;
                    listEmptyList = arrayList4;
                    if (str2.equals("0") && (i12 == 34 || i12 == 35)) {
                        arrayList4.add(new Size(720, 720));
                        arrayList4.add(new Size(Constants.MINIMAL_ERROR_STATUS_CODE, Constants.MINIMAL_ERROR_STATUS_CODE));
                        listEmptyList = arrayList4;
                    }
                } else {
                    if ("SAMSUNG".equalsIgnoreCase(str) && "ON7XELTE".equalsIgnoreCase(Build.DEVICE)) {
                        ArrayList arrayList5 = new ArrayList();
                        listEmptyList = arrayList5;
                        if (!str2.equals("0")) {
                            listEmptyList = arrayList5;
                            listEmptyList = arrayList5;
                            if (str2.equals("1") && (i12 == 34 || i12 == 35)) {
                                arrayList5.add(new Size(3264, 2448));
                                arrayList5.add(new Size(3264, 1836));
                                arrayList5.add(new Size(2448, 2448));
                                arrayList5.add(new Size(1920, 1920));
                                arrayList5.add(new Size(2048, 1536));
                                arrayList5.add(new Size(2048, 1152));
                                arrayList5.add(new Size(1920, 1080));
                                listEmptyList = arrayList5;
                            }
                        } else if (i12 == 34) {
                            arrayList5.add(new Size(4128, 3096));
                            arrayList5.add(new Size(4128, 2322));
                            arrayList5.add(new Size(3088, 3088));
                            arrayList5.add(new Size(3264, 2448));
                            arrayList5.add(new Size(3264, 1836));
                            arrayList5.add(new Size(2048, 1536));
                            arrayList5.add(new Size(2048, 1152));
                            arrayList5.add(new Size(1920, 1080));
                            listEmptyList = arrayList5;
                        } else if (i12 == 35) {
                            arrayList5.add(new Size(4128, 2322));
                            arrayList5.add(new Size(3088, 3088));
                            arrayList5.add(new Size(3264, 2448));
                            arrayList5.add(new Size(3264, 1836));
                            arrayList5.add(new Size(2048, 1536));
                            arrayList5.add(new Size(2048, 1152));
                            arrayList5.add(new Size(1920, 1080));
                            listEmptyList = arrayList5;
                        }
                    } else if ("SAMSUNG".equalsIgnoreCase(str) && "J7XELTE".equalsIgnoreCase(Build.DEVICE)) {
                        ArrayList arrayList6 = new ArrayList();
                        listEmptyList = arrayList6;
                        if (!str2.equals("0")) {
                            listEmptyList = arrayList6;
                            listEmptyList = arrayList6;
                            if (str2.equals("1") && (i12 == 34 || i12 == 35)) {
                                arrayList6.add(new Size(2576, 1932));
                                arrayList6.add(new Size(2560, 1440));
                                arrayList6.add(new Size(1920, 1920));
                                arrayList6.add(new Size(2048, 1536));
                                arrayList6.add(new Size(2048, 1152));
                                arrayList6.add(new Size(1920, 1080));
                                listEmptyList = arrayList6;
                            }
                        } else if (i12 == 34) {
                            arrayList6.add(new Size(4128, 3096));
                            arrayList6.add(new Size(4128, 2322));
                            arrayList6.add(new Size(3088, 3088));
                            arrayList6.add(new Size(3264, 2448));
                            arrayList6.add(new Size(3264, 1836));
                            arrayList6.add(new Size(2048, 1536));
                            arrayList6.add(new Size(2048, 1152));
                            arrayList6.add(new Size(1920, 1080));
                            listEmptyList = arrayList6;
                        } else if (i12 == 35) {
                            arrayList6.add(new Size(2048, 1536));
                            arrayList6.add(new Size(2048, 1152));
                            arrayList6.add(new Size(1920, 1080));
                            listEmptyList = arrayList6;
                        }
                    } else if ("REDMI".equalsIgnoreCase(str) && "joyeuse".equalsIgnoreCase(Build.DEVICE)) {
                        ArrayList arrayList7 = new ArrayList();
                        listEmptyList = arrayList7;
                        if (str2.equals("0")) {
                            listEmptyList = arrayList7;
                            if (i12 == 256) {
                                arrayList7.add(new Size(9280, 6944));
                                listEmptyList = arrayList7;
                            }
                        }
                    } else {
                        C20140q0.d(5, "ExcludedSupportedSizesQuirk");
                        listEmptyList = Collections.emptyList();
                    }
                }
                arrayList = listEmptyList;
            }
            arrayList = arrayList2;
        }
        if (!arrayList.isEmpty()) {
            arrayList3.removeAll(arrayList);
        }
        if (arrayList3.isEmpty()) {
            C20140q0.d(5, "OutputSizesCorrector");
        }
        Size[] sizeArr2 = (Size[]) arrayList3.toArray(new Size[0]);
        map.put(Integer.valueOf(i12), sizeArr2);
        return (Size[]) sizeArr2.clone();
    }

    @N
    public final StreamConfigurationMap b() {
        return this.f23125a.f23131a;
    }
}
