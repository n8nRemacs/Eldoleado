package androidx.camera.core.processing;

import android.graphics.RectF;
import androidx.camera.core.K0;
import androidx.camera.core.processing.C;
import androidx.camera.core.processing.z;
import androidx.concurrent.futures.b;
import java.util.Map;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class y implements b.c, K0.e {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24493b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24494c;

    public /* synthetic */ y(Object obj, int i12) {
        this.f24493b = i12;
        this.f24494c = obj;
    }

    @Override // androidx.camera.core.K0.e
    public void a(K0.d dVar) {
        for (Map.Entry entry : ((Map) this.f24494c).entrySet()) {
            int iC2 = dVar.c() - ((C.d) entry.getKey()).d();
            if (((C.d) entry.getKey()).c()) {
                iC2 = -iC2;
            }
            RectF rectF = androidx.camera.core.impl.utils.u.f24303a;
            ((z) entry.getValue()).h(((iC2 % 360) + 360) % 360, -1);
        }
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        switch (this.f24493b) {
            case 0:
                z.a aVar2 = (z.a) this.f24494c;
                aVar2.f24511p = aVar;
                return "SettableFuture hashCode: " + aVar2.hashCode();
            default:
                ((A) this.f24494c).f24402l = aVar;
                return "SurfaceOutputImpl close future complete";
        }
    }
}
