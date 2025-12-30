package androidx.camera.core.impl;

import androidx.camera.core.impl.C20102k0;
import androidx.camera.core.impl.q0;
import androidx.concurrent.futures.b;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.impl.i0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20098i0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24126b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24127c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24128d;

    public /* synthetic */ RunnableC20098i0(int i12, Object obj, Object obj2) {
        this.f24126b = i12;
        this.f24128d = obj;
        this.f24127c = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj = this.f24127c;
        Object obj2 = this.f24128d;
        switch (this.f24126b) {
            case 0:
                ((C20102k0) obj2).f24145a.removeObserver((C20102k0.a) obj);
                break;
            case 1:
                C20102k0.a aVar = (C20102k0.a) obj;
                if (aVar.f24147b.get()) {
                    C20102k0.b bVar = (C20102k0.b) obj2;
                    bVar.getClass();
                    aVar.f24148c.a(bVar.f24150a);
                    break;
                }
                break;
            case 2:
                q0.a aVar2 = (q0.a) obj;
                K<Object> k12 = K.f23988b;
                K k13 = (K) obj2;
                k13.getClass();
                try {
                    aVar2.a(k13.f23989a.get());
                    break;
                } catch (InterruptedException | ExecutionException e12) {
                    aVar2.onError(e12);
                    return;
                }
            case 3:
                com.google.common.util.concurrent.D0 d02 = (com.google.common.util.concurrent.D0) obj2;
                if (!d02.isDone()) {
                    ((b.a) obj).d(new TimeoutException("Cannot complete surfaceList within 5000"));
                    d02.cancel(true);
                    break;
                }
                break;
            default:
                C20102k0.b bVar2 = (C20102k0.b) ((C20102k0) obj2).f24145a.getValue();
                b.a aVar3 = (b.a) obj;
                if (bVar2 != null) {
                    aVar3.b(bVar2.f24150a);
                    break;
                } else {
                    aVar3.d(new IllegalStateException("Observable has not yet been initialized with a value."));
                    break;
                }
        }
    }

    public /* synthetic */ RunnableC20098i0(C20102k0.a aVar, C20102k0.b bVar) {
        this.f24126b = 1;
        this.f24127c = aVar;
        this.f24128d = bVar;
    }
}
