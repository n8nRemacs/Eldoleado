package androidx.camera.camera2.internal;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import androidx.camera.camera2.internal.C20026p;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.concurrent.futures.b;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20016k implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23355b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23356c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23357d;

    public /* synthetic */ RunnableC20016k(int i12, Object obj, Object obj2) {
        this.f23355b = i12;
        this.f23356c = obj;
        this.f23357d = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj = this.f23357d;
        Object obj2 = this.f23356c;
        switch (this.f23355b) {
            case 0:
                C20026p.a aVar = ((C20026p) obj2).f23410x;
                AbstractC20109p abstractC20109p = (AbstractC20109p) obj;
                aVar.f23411a.remove(abstractC20109p);
                aVar.f23412b.remove(abstractC20109p);
                break;
            case 1:
                int i12 = C20026p.b.f23413c;
                HashSet hashSet = new HashSet();
                HashSet hashSet2 = ((C20026p.b) obj2).f23414a;
                Iterator it = hashSet2.iterator();
                while (it.hasNext()) {
                    C20026p.c cVar = (C20026p.c) it.next();
                    if (cVar.a((TotalCaptureResult) obj)) {
                        hashSet.add(cVar);
                    }
                }
                if (!hashSet.isEmpty()) {
                    hashSet2.removeAll(hashSet);
                    break;
                }
                break;
            case 2:
                C20039w c20039w = (C20039w) obj2;
                A0 a02 = c20039w.f23484t;
                b.a aVar2 = (b.a) obj;
                if (a02 != null) {
                    aVar2.b(Boolean.valueOf(c20039w.f23466b.f(C20039w.v(a02))));
                    break;
                } else {
                    aVar2.b(Boolean.FALSE);
                    break;
                }
            case 3:
                C20039w c20039w2 = (C20039w) obj2;
                c20039w2.getClass();
                String str = (String) obj;
                c20039w2.s(AK.c.s(new StringBuilder("Use case "), str, " INACTIVE"));
                c20039w2.f23466b.j(str);
                c20039w2.K();
                break;
            case 4:
                final C20026p c20026p = (C20026p) obj2;
                final long jW2 = c20026p.w();
                androidx.camera.core.impl.utils.futures.f.i((b.a) obj, androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.camera2.internal.n
                    @Override // androidx.concurrent.futures.b.c
                    public final Object d(final b.a aVar3) {
                        C20026p c20026p2 = c20026p;
                        c20026p2.getClass();
                        final long j12 = jW2;
                        c20026p2.m(new C20026p.c() { // from class: androidx.camera.camera2.internal.o
                            @Override // androidx.camera.camera2.internal.C20026p.c
                            public final boolean a(TotalCaptureResult totalCaptureResult) {
                                if (!C20026p.u(totalCaptureResult, j12)) {
                                    return false;
                                }
                                aVar3.b(null);
                                return true;
                            }
                        });
                        return "waitForSessionUpdateId:" + j12;
                    }
                }));
                break;
            case 5:
                ((AbstractC20109p) obj).b((androidx.camera.core.impl.r) obj2);
                break;
            case 6:
                ((AbstractC20109p) obj).c((CameraCaptureFailure) obj2);
                break;
            default:
                ((Surface) obj2).release();
                ((SurfaceTexture) obj).release();
                break;
        }
    }

    public /* synthetic */ RunnableC20016k(AbstractC20109p abstractC20109p, Object obj, int i12) {
        this.f23355b = i12;
        this.f23357d = abstractC20109p;
        this.f23356c = obj;
    }
}
