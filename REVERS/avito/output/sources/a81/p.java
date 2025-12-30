package a81;

import b81.InterfaceC25428b;
import e81.InterfaceC39968b;
import java.util.List;
import org.mockito.exceptions.base.MockitoException;

/* compiled from: VerificationDataImpl.java */
/* loaded from: classes7.dex */
public class p implements InterfaceC25428b {

    /* renamed from: a, reason: collision with root package name */
    public final org.mockito.internal.invocation.i f20751a;

    /* renamed from: b, reason: collision with root package name */
    public final org.mockito.internal.stubbing.e f20752b;

    public p(org.mockito.internal.stubbing.e eVar, org.mockito.internal.invocation.i iVar) {
        this.f20752b = eVar;
        this.f20751a = iVar;
        if (Y71.i.a(iVar.f421660b.getMethod())) {
            throw new MockitoException(Y71.l.b("Mockito cannot verify toString()", "toString() is too often used behind of scenes  (i.e. during String concatenation, in IDE debugging views). Verifying it may give inconsistent or hard to understand results. Not to mention that verifying toString() most likely hints awkward design (hard to explain in a short exception message. Trust me...)", "However, it is possible to stub toString(). Stubbing toString() smells a bit funny but there are rare, legitimate use cases."));
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [a81.m, java.lang.Object] */
    public final List<InterfaceC39968b> a() {
        return this.f20752b.f421725d.getAll();
    }
}
