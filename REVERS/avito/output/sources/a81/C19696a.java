package a81;

import b81.InterfaceC25431e;
import d81.C39531a;
import e81.InterfaceC39968b;
import java.util.List;
import m81.InterfaceC43929e;

/* compiled from: AtLeast.java */
/* renamed from: a81.a, reason: case insensitive filesystem */
/* loaded from: classes7.dex */
public class C19696a implements InterfaceC25431e, InterfaceC43929e {
    @Override // m81.InterfaceC43929e
    public final void a(p pVar) {
        List<InterfaceC39968b> listA = pVar.a();
        org.mockito.internal.invocation.i iVar = pVar.f20751a;
        List<InterfaceC39968b> listA2 = org.mockito.internal.invocation.j.a(listA, iVar);
        int size = listA2.size();
        if (size >= 0) {
            org.mockito.internal.invocation.g.a(listA2, iVar);
        } else {
            throw new G71.e(Q71.a.b(new C39531a(size), iVar, org.mockito.internal.invocation.j.b(listA2)));
        }
    }

    public final String toString() {
        return "Wanted invocations count: at least 0";
    }
}
