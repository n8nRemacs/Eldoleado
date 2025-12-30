package a81;

import b81.InterfaceC25431e;
import e81.InterfaceC39968b;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import m81.InterfaceC43929e;

/* compiled from: Times.java */
/* loaded from: classes7.dex */
public class o implements InterfaceC25431e, InterfaceC43929e {
    @Override // m81.InterfaceC43929e
    public final void a(p pVar) {
        List<InterfaceC39968b> listA = pVar.a();
        org.mockito.internal.invocation.i iVar = pVar.f20751a;
        List<InterfaceC39968b> listA2 = org.mockito.internal.invocation.j.a(listA, iVar);
        int size = listA2.size();
        if (size < 0) {
            throw new G71.e(Q71.a.b(new W71.a(size), iVar, org.mockito.internal.invocation.j.b(listA2)));
        }
        if (size > 0) {
            String string = iVar.toString();
            P71.d dVar = new P71.d();
            StringBuilder sb2 = new StringBuilder();
            for (InterfaceC39968b interfaceC39968b : listA2) {
                e81.f fVarJ = interfaceC39968b.j();
                if (fVarJ != null) {
                    sb2.append(fVarJ);
                    sb2.append(" with arguments: ");
                    sb2.append(Arrays.toString(interfaceC39968b.D()));
                    sb2.append("\n");
                }
            }
            throw new G71.c(Y71.l.b(string, "Never wanted here:", dVar, "But invoked here:", sb2.toString()));
        }
        if (size <= 0) {
            org.mockito.internal.invocation.g.a(listA2, iVar);
            return;
        }
        LinkedList linkedListB = org.mockito.internal.invocation.j.b(listA2);
        throw new G71.f(Y71.l.b(iVar.toString(), "Wanted " + W71.b.a(0) + ":", new P71.d(), "But was " + W71.b.a(size) + ":", Q71.a.a(linkedListB), ""));
    }

    public final String toString() {
        return "Wanted invocations count: 0";
    }
}
