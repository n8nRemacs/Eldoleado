package androidx.camera.core;

import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.C20096h0;
import androidx.camera.core.impl.CameraInternal;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/* compiled from: CameraSelector.java */
@j.X
/* renamed from: androidx.camera.core.v, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20144v {

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public static final C20144v f24562b;

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public static final C20144v f24563c;

    /* renamed from: a, reason: collision with root package name */
    public LinkedHashSet<InterfaceC20141s> f24564a;

    /* compiled from: CameraSelector.java */
    @j.S
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.core.v$b */
    public @interface b {
    }

    static {
        a aVar = new a();
        aVar.a(0);
        LinkedHashSet<InterfaceC20141s> linkedHashSet = aVar.f24565a;
        C20144v c20144v = new C20144v();
        c20144v.f24564a = linkedHashSet;
        f24562b = c20144v;
        a aVar2 = new a();
        aVar2.a(1);
        LinkedHashSet<InterfaceC20141s> linkedHashSet2 = aVar2.f24565a;
        C20144v c20144v2 = new C20144v();
        c20144v2.f24564a = linkedHashSet2;
        f24563c = c20144v2;
    }

    public C20144v() {
        throw null;
    }

    @j.N
    @RestrictTo
    public final LinkedHashSet<CameraInternal> a(@j.N LinkedHashSet<CameraInternal> linkedHashSet) {
        ArrayList arrayList = new ArrayList();
        Iterator<CameraInternal> it = linkedHashSet.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getCameraInfo());
        }
        List listB = b(arrayList);
        LinkedHashSet<CameraInternal> linkedHashSet2 = new LinkedHashSet<>();
        Iterator<CameraInternal> it2 = linkedHashSet.iterator();
        while (it2.hasNext()) {
            CameraInternal next = it2.next();
            if (listB.contains(next.getCameraInfo())) {
                linkedHashSet2.add(next);
            }
        }
        return linkedHashSet2;
    }

    @j.N
    public final List b(@j.N ArrayList arrayList) {
        List<InterfaceC20142t> arrayList2 = new ArrayList<>(arrayList);
        Iterator<InterfaceC20141s> it = this.f24564a.iterator();
        while (it.hasNext()) {
            arrayList2 = it.next().b(Collections.unmodifiableList(arrayList2));
        }
        arrayList2.retainAll(arrayList);
        return arrayList2;
    }

    @RestrictTo
    @j.P
    public final Integer c() {
        Iterator<InterfaceC20141s> it = this.f24564a.iterator();
        Integer num = null;
        while (it.hasNext()) {
            InterfaceC20141s next = it.next();
            if (next instanceof C20096h0) {
                Integer numValueOf = Integer.valueOf(((C20096h0) next).f24124b);
                if (num == null) {
                    num = numValueOf;
                } else if (!num.equals(numValueOf)) {
                    throw new IllegalStateException("Multiple conflicting lens facing requirements exist.");
                }
            }
        }
        return num;
    }

    @j.N
    @RestrictTo
    public final void d(@j.N LinkedHashSet linkedHashSet) {
        Iterator<CameraInternal> it = a(linkedHashSet).iterator();
        if (!it.hasNext()) {
            throw new IllegalArgumentException("No available camera can be found");
        }
        it.next();
    }

    /* compiled from: CameraSelector.java */
    /* renamed from: androidx.camera.core.v$a */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public final LinkedHashSet<InterfaceC20141s> f24565a;

        public a() {
            this.f24565a = new LinkedHashSet<>();
        }

        @j.N
        public final void a(int i12) {
            androidx.core.util.z.g("The specified lens facing is invalid.", i12 != -1);
            this.f24565a.add(new C20096h0(i12));
        }

        public a(@j.N LinkedHashSet<InterfaceC20141s> linkedHashSet) {
            this.f24565a = new LinkedHashSet<>(linkedHashSet);
        }
    }
}
