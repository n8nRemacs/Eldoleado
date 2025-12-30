package androidx.camera.view;

import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.concurrent.futures.b;
import j.N;

/* compiled from: PreviewStreamStateObserver.java */
/* loaded from: classes.dex */
class p extends AbstractC20109p {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b.a f25452a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20142t f25453b;

    public p(b.a aVar, InterfaceC20142t interfaceC20142t) {
        this.f25452a = aVar;
        this.f25453b = interfaceC20142t;
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void b(@N androidx.camera.core.impl.r rVar) {
        this.f25452a.b(null);
        ((androidx.camera.core.impl.B) this.f25453b).c(this);
    }
}
