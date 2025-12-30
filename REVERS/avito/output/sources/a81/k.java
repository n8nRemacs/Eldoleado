package a81;

import b81.InterfaceC25431e;
import e81.InterfaceC39968b;
import java.util.Iterator;
import m81.InterfaceC43929e;

/* compiled from: NoMoreInteractions.java */
/* loaded from: classes7.dex */
public class k implements InterfaceC43929e, InterfaceC25431e {
    @Override // m81.InterfaceC43929e
    public final void a(p pVar) {
        InterfaceC39968b next;
        Iterator<InterfaceC39968b> it = pVar.a().iterator();
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
        if (next != null) {
            throw Q71.a.g(next, pVar.a());
        }
    }
}
