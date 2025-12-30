package androidx.camera.core.processing;

import android.graphics.SurfaceTexture;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.C0;
import androidx.camera.core.K0;
import androidx.core.util.InterfaceC22791e;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.processing.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20138f implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24431b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24432c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24433d;

    public /* synthetic */ RunnableC20138f(int i12, Object obj, Object obj2) {
        this.f24431b = i12;
        this.f24432c = obj;
        this.f24433d = obj2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [androidx.camera.core.processing.h] */
    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24431b) {
            case 0:
                final k kVar = (k) this.f24432c;
                Executor executor = kVar.f24445d;
                final C0 c02 = (C0) this.f24433d;
                Surface surfaceN1 = c02.n1(executor, new InterfaceC22791e() { // from class: androidx.camera.core.processing.h
                    @Override // androidx.core.util.InterfaceC22791e
                    public final void accept(Object obj) throws IOException {
                        k kVar2 = kVar;
                        kVar2.getClass();
                        C0 c03 = c02;
                        c03.close();
                        Surface surface = (Surface) kVar2.f24450i.remove(c03);
                        if (surface != null) {
                            p pVar = kVar2.f24443b;
                            pVar.d(true);
                            pVar.c();
                            pVar.r(surface, true);
                        }
                    }
                });
                p pVar = kVar.f24443b;
                pVar.d(true);
                pVar.c();
                HashMap map = pVar.f24463b;
                if (!map.containsKey(surfaceN1)) {
                    map.put(surfaceN1, p.f24461t);
                }
                kVar.f24450i.put(c02, surfaceN1);
                break;
            case 1:
                ((k) this.f24432c).f24453l.add((C20133a) this.f24433d);
                break;
            case 2:
                A a12 = (A) this.f24432c;
                a12.getClass();
                ((InterfaceC22791e) ((AtomicReference) this.f24433d).get()).accept(C0.a.c(a12));
                break;
            default:
                final k kVar2 = (k) this.f24432c;
                kVar2.f24451j++;
                p pVar2 = kVar2.f24443b;
                pVar2.d(true);
                pVar2.c();
                final SurfaceTexture surfaceTexture = new SurfaceTexture(pVar2.f24470i);
                K0 k02 = (K0) this.f24433d;
                Size size = k02.f23590b;
                surfaceTexture.setDefaultBufferSize(size.getWidth(), size.getHeight());
                final Surface surface = new Surface(surfaceTexture);
                k02.a(surface, kVar2.f24445d, new InterfaceC22791e() { // from class: androidx.camera.core.processing.i
                    @Override // androidx.core.util.InterfaceC22791e
                    public final void accept(Object obj) throws IOException {
                        k kVar3 = kVar2;
                        kVar3.getClass();
                        SurfaceTexture surfaceTexture2 = surfaceTexture;
                        surfaceTexture2.setOnFrameAvailableListener(null);
                        surfaceTexture2.release();
                        surface.release();
                        kVar3.f24451j--;
                        kVar3.a();
                    }
                });
                surfaceTexture.setOnFrameAvailableListener(kVar2, kVar2.f24446e);
                break;
        }
    }
}
