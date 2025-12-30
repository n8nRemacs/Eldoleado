package androidx.camera.camera2.impl;

import androidx.camera.core.impl.AbstractC20104l0;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: CameraEventCallbacks.java */
@X
/* loaded from: classes.dex */
public final class d extends AbstractC20104l0<c> {

    /* compiled from: CameraEventCallbacks.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public final ArrayList f22898a = new ArrayList();

        public a(List<c> list) {
            Iterator<c> it = list.iterator();
            while (it.hasNext()) {
                this.f22898a.add(it.next());
            }
        }

        @N
        public final ArrayList a() {
            ArrayList arrayList = new ArrayList();
            Iterator it = this.f22898a.iterator();
            while (it.hasNext()) {
                ((c) it.next()).getClass();
            }
            return arrayList;
        }
    }

    @N
    public static d b() {
        d dVar = new d();
        dVar.f24151a.addAll(Arrays.asList(new c[0]));
        return dVar;
    }

    @Override // androidx.camera.core.impl.AbstractC20104l0
    @N
    /* renamed from: a */
    public final d clone() {
        d dVarB = b();
        dVarB.f24151a.addAll(Collections.unmodifiableList(new ArrayList(this.f24151a)));
        return dVarB;
    }
}
