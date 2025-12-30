package androidx.camera.core.impl.utils.futures;

import androidx.concurrent.futures.b;
import androidx.core.util.z;
import j.N;
import java.util.List;

/* compiled from: ListFuture.java */
/* loaded from: classes.dex */
class h implements b.c<List<Object>> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ k f24264b;

    public h(k kVar) {
        this.f24264b = kVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.concurrent.futures.b.c
    public final Object d(@N b.a<List<Object>> aVar) {
        k kVar = this.f24264b;
        z.g("The result can only set once!", kVar.f24274g == null);
        kVar.f24274g = aVar;
        return "ListFuture[" + this + "]";
    }
}
