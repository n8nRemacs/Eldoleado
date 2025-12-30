package androidx.camera.extensions;

import androidx.camera.core.InterfaceC20141s;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.impl.B;
import androidx.core.util.z;
import j.N;
import j.S;
import j.X;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: ExtensionCameraFilter.java */
@X
/* loaded from: classes.dex */
final class a implements InterfaceC20141s {
    @Override // androidx.camera.core.InterfaceC20141s
    @N
    public final androidx.camera.core.impl.X a() {
        return null;
    }

    @Override // androidx.camera.core.InterfaceC20141s
    @N
    @S
    public final List<InterfaceC20142t> b(@N List<InterfaceC20142t> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<InterfaceC20142t> it = list.iterator();
        if (!it.hasNext()) {
            return arrayList;
        }
        InterfaceC20142t next = it.next();
        z.a("The camera info doesn't contain internal implementation.", next instanceof B);
        String str = D.f.a(next).f2838a.f23525a;
        D.f.a(next).b();
        throw null;
    }
}
