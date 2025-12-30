package androidx.camera.video;

import android.util.Range;
import android.util.Rational;
import android.util.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: QualityRatioToResolutionsTable.java */
@j.X
/* loaded from: classes.dex */
class C {

    /* renamed from: b, reason: collision with root package name */
    public static final HashMap f24690b;

    /* renamed from: c, reason: collision with root package name */
    public static final HashMap f24691c;

    /* renamed from: a, reason: collision with root package name */
    public final HashMap f24692a = new HashMap();

    /* compiled from: QualityRatioToResolutionsTable.java */
    @VY0.c
    public static abstract class a {
        public abstract int a();

        @j.N
        public abstract A b();
    }

    static {
        HashMap map = new HashMap();
        f24690b = map;
        map.put(A.f24683d, Range.create(2160, 4319));
        map.put(A.f24682c, Range.create(1080, 1439));
        map.put(A.f24681b, Range.create(720, 1079));
        map.put(A.f24680a, Range.create(241, 719));
        HashMap map2 = new HashMap();
        f24691c = map2;
        map2.put(0, androidx.camera.core.impl.utils.b.f24198a);
        map2.put(1, androidx.camera.core.impl.utils.b.f24200c);
    }

    public C(@j.N HashMap map, @j.N List list) {
        HashMap map2;
        Integer num;
        A a12;
        HashMap map3 = f24690b;
        Iterator it = map3.keySet().iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            map2 = f24691c;
            if (!zHasNext) {
                break;
            }
            A a13 = (A) it.next();
            this.f24692a.put(new C20174k(a13, -1), new ArrayList());
            Iterator it2 = map2.keySet().iterator();
            while (it2.hasNext()) {
                this.f24692a.put(new C20174k(a13, ((Integer) it2.next()).intValue()), new ArrayList());
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            List list2 = (List) this.f24692a.get(new C20174k((A) entry.getKey(), -1));
            Objects.requireNonNull(list2);
            list2.add((Size) entry.getValue());
        }
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            Size size = (Size) it3.next();
            Iterator it4 = map3.entrySet().iterator();
            while (true) {
                num = null;
                if (!it4.hasNext()) {
                    a12 = null;
                    break;
                }
                Map.Entry entry2 = (Map.Entry) it4.next();
                if (((Range) entry2.getValue()).contains((Range) Integer.valueOf(size.getHeight()))) {
                    a12 = (A) entry2.getKey();
                    break;
                }
            }
            if (a12 != null) {
                Iterator it5 = map2.entrySet().iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        break;
                    }
                    Map.Entry entry3 = (Map.Entry) it5.next();
                    if (androidx.camera.core.impl.utils.b.a(size, (Rational) entry3.getValue(), androidx.camera.core.internal.utils.c.f24371b)) {
                        num = (Integer) entry3.getKey();
                        break;
                    }
                }
                if (num != null) {
                    List list3 = (List) this.f24692a.get(new C20174k(a12, num.intValue()));
                    Objects.requireNonNull(list3);
                    list3.add(size);
                }
            }
        }
        for (Map.Entry entry4 : this.f24692a.entrySet()) {
            Size size2 = (Size) map.get(((a) entry4.getKey()).b());
            if (size2 != null) {
                Size size3 = androidx.camera.core.internal.utils.c.f24370a;
                final int height = size2.getHeight() * size2.getWidth();
                Collections.sort((List) entry4.getValue(), new Comparator() { // from class: androidx.camera.video.B
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        HashMap map4 = C.f24690b;
                        int iA2 = androidx.camera.core.internal.utils.c.a((Size) obj);
                        int i12 = height;
                        return Math.abs(iA2 - i12) - Math.abs(androidx.camera.core.internal.utils.c.a((Size) obj2) - i12);
                    }
                });
            }
        }
    }
}
