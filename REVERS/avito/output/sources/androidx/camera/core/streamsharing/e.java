package androidx.camera.core.streamsharing;

import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20106m0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.n0;
import androidx.camera.core.impl.s0;
import j.N;
import j.X;
import java.util.UUID;

/* compiled from: StreamSharingBuilder.java */
@X
/* loaded from: classes.dex */
class e implements L0.a<d, f, e> {

    /* renamed from: a, reason: collision with root package name */
    public final n0 f24547a;

    public e() {
        this(n0.R());
    }

    @Override // androidx.camera.core.impl.L0.a
    @N
    public final L0 b() {
        return new f(s0.Q(this.f24547a));
    }

    @Override // androidx.camera.core.L
    @N
    public final InterfaceC20106m0 d() {
        return this.f24547a;
    }

    public e(@N n0 n0Var) {
        Object objF;
        this.f24547a = n0Var;
        Object objF2 = null;
        try {
            objF = n0Var.f(androidx.camera.core.internal.i.f24362C);
        } catch (IllegalArgumentException unused) {
            objF = null;
        }
        Class cls = (Class) objF;
        if (cls != null && !cls.equals(d.class)) {
            throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
        }
        Config.a<Class<?>> aVar = androidx.camera.core.internal.i.f24362C;
        n0 n0Var2 = this.f24547a;
        n0Var2.K(aVar, d.class);
        try {
            objF2 = n0Var2.f(androidx.camera.core.internal.i.f24361B);
        } catch (IllegalArgumentException unused2) {
        }
        if (objF2 == null) {
            n0Var2.K(androidx.camera.core.internal.i.f24361B, d.class.getCanonicalName() + "-" + UUID.randomUUID());
        }
    }
}
