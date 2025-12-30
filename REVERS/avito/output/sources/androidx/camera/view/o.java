package androidx.camera.view;

import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.impl.AbstractC20109p;
import j.N;
import j.P;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: PreviewStreamStateObserver.java */
/* loaded from: classes.dex */
class o implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ArrayList f25449a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20142t f25450b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ q f25451c;

    public o(q qVar, ArrayList arrayList, InterfaceC20142t interfaceC20142t) {
        this.f25451c = qVar;
        this.f25449a = arrayList;
        this.f25450b = interfaceC20142t;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@N Throwable th2) {
        this.f25451c.f25458e = null;
        ArrayList arrayList = this.f25449a;
        if (arrayList.isEmpty()) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((androidx.camera.core.impl.B) this.f25450b).c((AbstractC20109p) it.next());
        }
        arrayList.clear();
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@P Void r22) {
        this.f25451c.f25458e = null;
    }
}
