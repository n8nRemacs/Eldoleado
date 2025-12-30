package androidx.camera.core.internal;

import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.B;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.L0;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: SupportedOutputSizesSorter.java */
@X
/* loaded from: classes.dex */
class g {

    /* renamed from: a, reason: collision with root package name */
    public final B f24351a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24352b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24353c;

    /* renamed from: d, reason: collision with root package name */
    public final Rational f24354d;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f24355e;

    /* renamed from: f, reason: collision with root package name */
    public final h f24356f;

    public g(@N B b12, @P Size size) {
        Rational rational;
        this.f24351a = b12;
        this.f24352b = b12.f();
        this.f24353c = b12.b();
        if (size != null) {
            rational = new Rational(size.getWidth(), size.getHeight());
        } else {
            List<Size> listO = b12.o(256);
            if (listO.isEmpty()) {
                rational = null;
            } else {
                Size size2 = (Size) Collections.max(listO, new androidx.camera.core.impl.utils.g());
                rational = new Rational(size2.getWidth(), size2.getHeight());
            }
        }
        this.f24354d = rational;
        boolean z12 = true;
        if (rational != null && rational.getNumerator() < rational.getDenominator()) {
            z12 = false;
        }
        this.f24355e = z12;
        this.f24356f = new h(b12, rational);
    }

    @N
    public static ArrayList b(@N ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(androidx.camera.core.impl.utils.b.f24198a);
        arrayList2.add(androidx.camera.core.impl.utils.b.f24200c);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Size size = (Size) it.next();
            Rational rational = new Rational(size.getWidth(), size.getHeight());
            if (!arrayList2.contains(rational)) {
                Iterator it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        arrayList2.add(rational);
                        break;
                    }
                    if (androidx.camera.core.impl.utils.b.a(size, (Rational) it2.next(), androidx.camera.core.internal.utils.c.f24372c)) {
                        break;
                    }
                }
            }
        }
        return arrayList2;
    }

    @P
    public static Rational c(int i12, boolean z12) {
        if (i12 != -1) {
            if (i12 == 0) {
                return z12 ? androidx.camera.core.impl.utils.b.f24198a : androidx.camera.core.impl.utils.b.f24199b;
            }
            if (i12 == 1) {
                return z12 ? androidx.camera.core.impl.utils.b.f24200c : androidx.camera.core.impl.utils.b.f24201d;
            }
            C20140q0.b("SupportedOutputSizesCollector");
        }
        return null;
    }

    public static HashMap d(@N ArrayList arrayList) {
        HashMap map = new HashMap();
        Iterator it = b(arrayList).iterator();
        while (it.hasNext()) {
            map.put((Rational) it.next(), new ArrayList());
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Size size = (Size) it2.next();
            for (Rational rational : map.keySet()) {
                if (androidx.camera.core.impl.utils.b.a(size, rational, androidx.camera.core.internal.utils.c.f24372c)) {
                    ((List) map.get(rational)).add(size);
                }
            }
        }
        return map;
    }

    public static void e(@N List<Size> list, @N Size size, boolean z12) {
        ArrayList arrayList = new ArrayList();
        for (int size2 = list.size() - 1; size2 >= 0; size2--) {
            Size size3 = list.get(size2);
            if (size3.getWidth() >= size.getWidth() && size3.getHeight() >= size.getHeight()) {
                break;
            }
            arrayList.add(0, size3);
        }
        list.removeAll(arrayList);
        Collections.reverse(list);
        if (z12) {
            list.addAll(arrayList);
        }
    }

    public static void f(@N List<Size> list, @N Size size, boolean z12) {
        ArrayList arrayList = new ArrayList();
        for (int i12 = 0; i12 < list.size(); i12++) {
            Size size2 = list.get(i12);
            if (size2.getWidth() <= size.getWidth() && size2.getHeight() <= size.getHeight()) {
                break;
            }
            arrayList.add(0, size2);
        }
        list.removeAll(arrayList);
        if (z12) {
            list.addAll(arrayList);
        }
    }

    @N
    public final ArrayList a(@N L0 l02) {
        Size[] sizeArr;
        int inputFormat = l02.getInputFormat();
        List<Pair> listH = ((InterfaceC20088d0) l02).h();
        if (listH != null) {
            for (Pair pair : listH) {
                if (((Integer) pair.first).intValue() == inputFormat) {
                    sizeArr = (Size[]) pair.second;
                    break;
                }
            }
            sizeArr = null;
        } else {
            sizeArr = null;
        }
        List<Size> listAsList = sizeArr != null ? Arrays.asList(sizeArr) : null;
        if (listAsList == null) {
            listAsList = this.f24351a.o(inputFormat);
        }
        ArrayList arrayList = new ArrayList(listAsList);
        Collections.sort(arrayList, new androidx.camera.core.impl.utils.g(true));
        if (arrayList.isEmpty()) {
            C20140q0.d(5, "SupportedOutputSizesCollector");
        }
        return arrayList;
    }
}
