package androidx.camera.core.processing;

import androidx.camera.core.C0;
import androidx.camera.core.K0;
import androidx.camera.core.processing.C;
import androidx.camera.core.processing.z;
import androidx.concurrent.futures.b;
import java.io.IOException;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class x implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24491b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24492c;

    public /* synthetic */ x(Object obj, int i12) {
        this.f24491b = i12;
        this.f24492c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() throws IOException {
        switch (this.f24491b) {
            case 0:
                ((z.a) this.f24492c).b();
                break;
            case 1:
                ((b.a) this.f24492c).d(new Exception("Failed to snapshot: OpenGLRenderer not ready."));
                break;
            case 2:
                ((C0) this.f24492c).close();
                break;
            case 3:
                ((K0) this.f24492c).c();
                break;
            case 4:
                k kVar = (k) this.f24492c;
                kVar.f24452k = true;
                kVar.a();
                break;
            default:
                C.c cVar = ((C) this.f24492c).f24406c;
                if (cVar != null) {
                    for (z zVar : cVar.values()) {
                        zVar.getClass();
                        androidx.camera.core.impl.utils.t.a();
                        zVar.d();
                        zVar.f24509o = true;
                    }
                    break;
                }
                break;
        }
    }
}
