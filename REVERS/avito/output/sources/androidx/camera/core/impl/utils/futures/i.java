package androidx.camera.core.impl.utils.futures;

/* compiled from: ListFuture.java */
/* loaded from: classes.dex */
class i implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ k f24265b;

    public i(k kVar) {
        this.f24265b = kVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        k kVar = this.f24265b;
        kVar.f24270c = null;
        kVar.f24269b = null;
    }
}
