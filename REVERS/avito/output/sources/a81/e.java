package a81;

import e81.InterfaceC39968b;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: DefaultRegisteredInvocations.java */
/* loaded from: classes7.dex */
public class e implements m, Serializable {
    private static final long serialVersionUID = -2674402327380736290L;

    /* renamed from: b, reason: collision with root package name */
    public final LinkedList<InterfaceC39968b> f20749b = new LinkedList<>();

    @Override // a81.m
    public final void a(InterfaceC39968b interfaceC39968b) {
        synchronized (this.f20749b) {
            this.f20749b.add(interfaceC39968b);
        }
    }

    @Override // a81.m
    public final List<InterfaceC39968b> getAll() {
        LinkedList linkedList;
        synchronized (this.f20749b) {
            linkedList = new LinkedList(this.f20749b);
        }
        return (List) linkedList.stream().filter(new C19699d(0)).collect(Collectors.toList());
    }
}
