package androidx.camera.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: UseCaseGroup.java */
@j.X
/* loaded from: classes.dex */
public final class O0 {

    /* renamed from: a, reason: collision with root package name */
    @j.P
    public final P0 f23635a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final ArrayList f23636b;

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public final ArrayList f23637c;

    /* compiled from: UseCaseGroup.java */
    public static final class a {

        /* renamed from: d, reason: collision with root package name */
        public static final List<Integer> f23638d = Arrays.asList(1, 2, 4, 3, 7);

        /* renamed from: a, reason: collision with root package name */
        public P0 f23639a;

        /* renamed from: b, reason: collision with root package name */
        public final ArrayList f23640b = new ArrayList();

        /* renamed from: c, reason: collision with root package name */
        public final ArrayList f23641c = new ArrayList();

        @j.N
        public final void a(@j.N N0 n02) {
            this.f23640b.add(n02);
        }

        @j.N
        public final O0 b() {
            ArrayList arrayList = this.f23640b;
            androidx.core.util.z.a("UseCase must not be empty.", !arrayList.isEmpty());
            ArrayList arrayList2 = this.f23641c;
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ((AbstractC20131p) it.next()).getClass();
                List<Integer> list = f23638d;
                boolean zContains = list.contains(0);
                Locale locale = Locale.US;
                String strA = androidx.camera.core.processing.E.a(0);
                ArrayList arrayList3 = new ArrayList();
                Iterator<Integer> it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(androidx.camera.core.processing.E.a(it2.next().intValue()));
                }
                androidx.core.util.z.a(Q.a("Effects target ", strA, " is not in the supported list ", "[" + String.join(", ", arrayList3) + "]", "."), zContains);
            }
            return new O0(this.f23639a, arrayList, arrayList2);
        }
    }

    public O0(@j.P P0 p02, @j.N ArrayList arrayList, @j.N ArrayList arrayList2) {
        this.f23635a = p02;
        this.f23636b = arrayList;
        this.f23637c = arrayList2;
    }
}
