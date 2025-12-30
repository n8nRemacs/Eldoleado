package androidx.camera.core.streamsharing;

import androidx.camera.core.N0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.r;
import j.N;
import java.util.Iterator;

/* compiled from: VirtualCamera.java */
/* loaded from: classes.dex */
class g extends AbstractC20109p {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ h f24550a;

    public g(h hVar) {
        this.f24550a = hVar;
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void b(@N r rVar) {
        Iterator it = this.f24550a.f24551b.iterator();
        while (it.hasNext()) {
            SessionConfig sessionConfig = ((N0) it.next()).f23627m;
            Iterator<AbstractC20109p> it2 = sessionConfig.f24017f.f23972e.iterator();
            while (it2.hasNext()) {
                it2.next().b(new i(rVar, sessionConfig.f24017f.f23974g, -1L));
            }
        }
    }
}
