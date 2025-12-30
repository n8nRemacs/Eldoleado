package androidx.camera.core.impl;

import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.impl.A0;
import java.util.Collections;
import java.util.Set;

/* compiled from: SessionProcessor.java */
@j.X
/* loaded from: classes.dex */
public interface C0 {
    void a();

    void b();

    @j.N
    @A0.a
    default Set<Integer> c() {
        return Collections.emptySet();
    }

    void e(@j.N y0 y0Var);

    @j.N
    SessionConfig f(@j.N InterfaceC20142t interfaceC20142t, @j.N t0 t0Var, @j.N t0 t0Var2, @j.P t0 t0Var3);

    void g(@j.N androidx.camera.camera2.impl.b bVar);

    /* compiled from: SessionProcessor.java */
    public interface a {
        default void a() {
        }

        default void b() {
        }
    }

    default void d(@j.N D.h hVar, @j.N a aVar) {
    }
}
