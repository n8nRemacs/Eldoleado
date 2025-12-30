package a81;

import e81.InterfaceC39968b;
import java.util.Iterator;
import java.util.List;
import m81.InterfaceC43929e;

/* compiled from: Only.java */
/* loaded from: classes7.dex */
public class l implements InterfaceC43929e {
    @Override // m81.InterfaceC43929e
    public final void a(p pVar) {
        InterfaceC39968b next;
        org.mockito.internal.invocation.i iVar = pVar.f20751a;
        List<InterfaceC39968b> listA = pVar.a();
        List<InterfaceC39968b> listA2 = org.mockito.internal.invocation.j.a(listA, iVar);
        if (listA.size() == 1 || listA2.isEmpty()) {
            if (listA.size() != 1 || listA2.isEmpty()) {
                throw new G71.h(Y71.l.b("Wanted but not invoked:", iVar.toString(), new P71.d(), ""));
            }
            InterfaceC39968b interfaceC39968b = listA2.get(0);
            interfaceC39968b.M1();
            iVar.e(interfaceC39968b);
            return;
        }
        Iterator<InterfaceC39968b> it = listA.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (!next.M()) {
                    break;
                }
            }
        }
        throw Q71.a.g(next, listA);
    }

    public final String toString() {
        return "Wanted invocations count: 1 and no other method invoked";
    }
}
