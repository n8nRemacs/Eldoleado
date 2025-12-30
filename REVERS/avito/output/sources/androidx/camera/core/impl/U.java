package androidx.camera.core.impl;

import java.util.HashMap;

/* compiled from: ExtendedCameraConfigProviderStore.java */
@j.X
/* loaded from: classes.dex */
public final class U {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f24051a = new Object();

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public static final HashMap f24052b = new HashMap();

    @j.N
    public static InterfaceC20114v a(@j.N X x12) {
        InterfaceC20114v interfaceC20114v;
        synchronized (f24051a) {
            interfaceC20114v = (InterfaceC20114v) f24052b.get(x12);
        }
        return interfaceC20114v == null ? InterfaceC20114v.f24304a : interfaceC20114v;
    }
}
