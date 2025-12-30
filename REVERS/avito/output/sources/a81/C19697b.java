package a81;

import e81.InterfaceC39968b;
import java.util.Iterator;
import java.util.List;
import m81.InterfaceC43929e;

/* compiled from: AtMost.java */
/* renamed from: a81.b, reason: case insensitive filesystem */
/* loaded from: classes7.dex */
public class C19697b implements InterfaceC43929e {
    @Override // m81.InterfaceC43929e
    public final void a(p pVar) {
        List<InterfaceC39968b> listA = pVar.a();
        org.mockito.internal.invocation.i iVar = pVar.f20751a;
        List<InterfaceC39968b> listA2 = org.mockito.internal.invocation.j.a(listA, iVar);
        int size = listA2.size();
        if (size > 0) {
            throw new G71.b(Y71.l.b("Wanted at most " + W71.b.a(0) + " but was " + size));
        }
        Iterator<InterfaceC39968b> it = listA2.iterator();
        while (it.hasNext()) {
            if (it.next().M()) {
                it.remove();
            }
        }
        org.mockito.internal.invocation.g.a(listA2, iVar);
    }

    public final String toString() {
        return "Wanted invocations count: at most 0";
    }
}
