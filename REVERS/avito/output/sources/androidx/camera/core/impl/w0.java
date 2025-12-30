package androidx.camera.core.impl;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: Quirks.java */
@j.X
/* loaded from: classes.dex */
public class w0 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final ArrayList f24307a;

    public w0(@j.N ArrayList arrayList) {
        this.f24307a = new ArrayList(arrayList);
    }

    public final boolean a(@j.N Class<? extends v0> cls) {
        Iterator it = this.f24307a.iterator();
        while (it.hasNext()) {
            if (cls.isAssignableFrom(((v0) it.next()).getClass())) {
                return true;
            }
        }
        return false;
    }

    @j.P
    public final <T extends v0> T b(@j.N Class<T> cls) {
        Iterator it = this.f24307a.iterator();
        while (it.hasNext()) {
            T t12 = (T) it.next();
            if (t12.getClass() == cls) {
                return t12;
            }
        }
        return null;
    }

    @j.N
    public final ArrayList c(@j.N Class cls) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.f24307a.iterator();
        while (it.hasNext()) {
            v0 v0Var = (v0) it.next();
            if (cls.isAssignableFrom(v0Var.getClass())) {
                arrayList.add(v0Var);
            }
        }
        return arrayList;
    }
}
