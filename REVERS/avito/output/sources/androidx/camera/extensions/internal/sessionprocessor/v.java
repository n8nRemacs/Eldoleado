package androidx.camera.extensions.internal.sessionprocessor;

import android.media.Image;
import androidx.camera.core.C20140q0;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.impl.A0;
import androidx.camera.core.impl.C0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.t0;
import j.N;
import j.P;
import j.S;
import j.X;
import java.util.LinkedHashMap;
import java.util.Set;

/* compiled from: SessionProcessorBase.java */
@X
/* loaded from: classes.dex */
abstract class v implements C0 {

    /* compiled from: SessionProcessorBase.java */
    public static class a implements r {

        /* renamed from: a, reason: collision with root package name */
        public int f24648a;

        @Override // androidx.camera.extensions.internal.sessionprocessor.r
        public final boolean a() {
            throw null;
        }

        @Override // androidx.camera.extensions.internal.sessionprocessor.r
        public final boolean b() {
            throw null;
        }

        @Override // androidx.camera.extensions.internal.sessionprocessor.r
        @P
        public final Image get() {
            return null;
        }
    }

    @Override // androidx.camera.core.impl.C0
    public final void b() {
        C20140q0.b("SessionProcessorBase");
        h();
        throw null;
    }

    @Override // androidx.camera.core.impl.C0
    @N
    @A0.a
    public Set<Integer> c() {
        return null;
    }

    @Override // androidx.camera.core.impl.C0
    @N
    @S
    public final SessionConfig f(@N InterfaceC20142t interfaceC20142t, @N t0 t0Var, @N t0 t0Var2, @P t0 t0Var3) {
        D.f fVarA = D.f.a(interfaceC20142t);
        i(fVarA.f2838a.f23525a, fVarA.b(), t0Var, t0Var2, t0Var3);
        throw null;
    }

    public abstract void h();

    @N
    public abstract m i(@N String str, @N LinkedHashMap linkedHashMap, @N t0 t0Var, @N t0 t0Var2, @P t0 t0Var3);
}
