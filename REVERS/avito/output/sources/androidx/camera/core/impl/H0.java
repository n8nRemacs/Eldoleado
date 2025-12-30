package androidx.camera.core.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: SurfaceCombination.java */
@j.X
/* loaded from: classes.dex */
public final class H0 {

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList f23984a = new ArrayList();

    public static void b(ArrayList arrayList, int i12, int[] iArr, int i13) {
        if (i13 >= iArr.length) {
            arrayList.add((int[]) iArr.clone());
            return;
        }
        for (int i14 = 0; i14 < i12; i14++) {
            int i15 = 0;
            while (true) {
                if (i15 >= i13) {
                    iArr[i13] = i14;
                    b(arrayList, i12, iArr, i13 + 1);
                    break;
                } else if (i14 == iArr[i15]) {
                    break;
                } else {
                    i15++;
                }
            }
        }
    }

    public final void a(@j.N SurfaceConfig surfaceConfig) {
        this.f23984a.add(surfaceConfig);
    }

    @j.P
    public final List<SurfaceConfig> c(@j.N List<SurfaceConfig> list) {
        if (list.isEmpty()) {
            return new ArrayList();
        }
        int size = list.size();
        ArrayList arrayList = this.f23984a;
        if (size != arrayList.size()) {
            return null;
        }
        int size2 = arrayList.size();
        ArrayList arrayList2 = new ArrayList();
        boolean z12 = false;
        b(arrayList2, size2, new int[size2], 0);
        SurfaceConfig[] surfaceConfigArr = new SurfaceConfig[list.size()];
        Iterator it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            int[] iArr = (int[]) it.next();
            boolean z13 = true;
            for (int i12 = 0; i12 < arrayList.size(); i12++) {
                if (iArr[i12] < list.size()) {
                    SurfaceConfig surfaceConfig = (SurfaceConfig) arrayList.get(i12);
                    SurfaceConfig surfaceConfig2 = list.get(iArr[i12]);
                    surfaceConfig.getClass();
                    z13 &= surfaceConfig2.c().f24040b <= surfaceConfig.c().f24040b && surfaceConfig2.d() == surfaceConfig.d();
                    if (!z13) {
                        break;
                    }
                    surfaceConfigArr[iArr[i12]] = (SurfaceConfig) arrayList.get(i12);
                }
            }
            if (z13) {
                z12 = true;
                break;
            }
        }
        if (z12) {
            return Arrays.asList(surfaceConfigArr);
        }
        return null;
    }
}
