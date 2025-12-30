package androidx.camera.extensions;

import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20112t;
import androidx.camera.core.impl.n0;
import j.N;
import j.X;

/* compiled from: ExtensionsConfig.java */
@X
/* loaded from: classes.dex */
class c implements InterfaceC20112t {

    /* compiled from: ExtensionsConfig.java */
    public static final class a implements InterfaceC20112t.a<a> {
        public a() {
            n0.R();
        }
    }

    static {
        Config.a.a(Integer.TYPE, "camerax.extensions.extensionMode");
    }

    @Override // androidx.camera.core.impl.InterfaceC20112t
    @N
    public final androidx.camera.core.impl.X J() {
        return (androidx.camera.core.impl.X) f(InterfaceC20112t.f24175b);
    }

    @Override // androidx.camera.core.impl.x0
    @N
    public final Config getConfig() {
        return null;
    }
}
