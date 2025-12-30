package androidx.camera.lifecycle;

import androidx.camera.core.C20148z;
import androidx.concurrent.futures.b;
import j.N;
import j.P;

/* compiled from: ProcessCameraProvider.java */
/* loaded from: classes.dex */
class h implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b.a f24672a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C20148z f24673b;

    public h(b.a aVar, C20148z c20148z) {
        this.f24672a = aVar;
        this.f24673b = c20148z;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@N Throwable th2) {
        this.f24672a.d(th2);
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@P Void r22) {
        this.f24672a.b(this.f24673b);
    }
}
