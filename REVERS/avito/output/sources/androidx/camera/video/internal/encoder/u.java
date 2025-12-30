package androidx.camera.video.internal.encoder;

/* compiled from: EncoderImpl.java */
/* loaded from: classes.dex */
class u implements androidx.camera.core.impl.utils.futures.c<E> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ v f25191a;

    public u(v vVar) {
        this.f25191a = vVar;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        this.f25191a.g(0, "Unable to acquire InputBuffer.", th2);
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(E e12) {
        E e13 = e12;
        v vVar = this.f25191a;
        e13.b(vVar.f25212q.b());
        e13.c();
        e13.a();
        androidx.camera.core.impl.utils.futures.f.a(e13.d(), new t(this), vVar.f25203h);
    }
}
