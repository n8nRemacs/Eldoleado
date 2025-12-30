package androidx.camera.core.impl;

import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.InitializationException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/* compiled from: CameraRepository.java */
@j.X
/* loaded from: classes.dex */
public final class D {

    /* renamed from: a, reason: collision with root package name */
    public final Object f23923a = new Object();

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public final LinkedHashMap f23924b = new LinkedHashMap();

    public D() {
        new HashSet();
    }

    @j.N
    public final LinkedHashSet<CameraInternal> a() {
        LinkedHashSet<CameraInternal> linkedHashSet;
        synchronized (this.f23923a) {
            linkedHashSet = new LinkedHashSet<>((Collection<? extends CameraInternal>) this.f23924b.values());
        }
        return linkedHashSet;
    }

    public final void b(@j.N InterfaceC20117y interfaceC20117y) {
        synchronized (this.f23923a) {
            try {
                for (String str : interfaceC20117y.c()) {
                    C20140q0.d(3, "CameraRepository");
                    this.f23924b.put(str, interfaceC20117y.b(str));
                }
            } catch (CameraUnavailableException e12) {
                throw new InitializationException(e12);
            }
        }
    }
}
