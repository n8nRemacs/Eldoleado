package a81;

import b81.InterfaceC25431e;
import m81.InterfaceC43929e;
import org.mockito.exceptions.base.MockitoException;

/* compiled from: Calls.java */
/* renamed from: a81.c, reason: case insensitive filesystem */
/* loaded from: classes7.dex */
public class C19698c implements InterfaceC43929e, InterfaceC25431e {
    @Override // m81.InterfaceC43929e
    public final void a(p pVar) {
        throw new MockitoException("calls is only intended to work with InOrder");
    }

    public final String toString() {
        return "Wanted invocations count (non-greedy): 0";
    }
}
