package a81;

import e81.InterfaceC39968b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import m81.InterfaceC43929e;

/* compiled from: NoInteractions.java */
/* loaded from: classes7.dex */
public class j implements InterfaceC43929e {
    @Override // m81.InterfaceC43929e
    public final void a(p pVar) {
        List<InterfaceC39968b> listA = pVar.a();
        if (listA.isEmpty()) {
            return;
        }
        Object objV3 = listA.get(0).v3();
        new R71.a();
        String strA = R71.a.a(listA);
        ArrayList arrayList = new ArrayList();
        Iterator<InterfaceC39968b> it = listA.iterator();
        while (it.hasNext()) {
            arrayList.add(((Q71.b) it.next()).j());
        }
        throw new G71.d(Y71.l.b("No interactions wanted here:", new P71.d(), "But found these interactions on mock '" + Y71.h.b(objV3) + "':", Y71.l.a("", "", arrayList), strA));
    }
}
