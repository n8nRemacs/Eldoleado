package androidx.camera.camera2.internal.compat.workaround;

import androidx.camera.camera2.internal.compat.quirk.B;
import androidx.camera.camera2.internal.compat.quirk.C19999j;
import androidx.camera.camera2.internal.compat.quirk.G;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.w0;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ForceCloseDeferrableSurface.java */
@X
/* loaded from: classes.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f23254a;

    /* renamed from: b, reason: collision with root package name */
    public final boolean f23255b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f23256c;

    public i(@N w0 w0Var, @N w0 w0Var2) {
        this.f23254a = w0Var2.a(G.class);
        this.f23255b = w0Var.a(B.class);
        this.f23256c = w0Var.a(C19999j.class);
    }

    public final void a(@P ArrayList arrayList) {
        if ((this.f23254a || this.f23255b || this.f23256c) && arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((DeferrableSurface) it.next()).a();
            }
            C20140q0.d(3, "ForceCloseDeferrableSurface");
        }
    }
}
