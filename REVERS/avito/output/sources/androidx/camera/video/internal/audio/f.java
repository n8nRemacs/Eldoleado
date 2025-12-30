package androidx.camera.video.internal.audio;

import androidx.camera.video.internal.audio.i;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import androidx.concurrent.futures.b;
import java.util.concurrent.ExecutionException;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class f implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24962b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24963c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24964d;

    public /* synthetic */ f(int i12, Object obj, Object obj2) {
        this.f24962b = i12;
        this.f24963c = obj;
        this.f24964d = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() throws ExecutionException, InterruptedException {
        switch (this.f24962b) {
            case 0:
                i iVar = (i) this.f24963c;
                int iOrdinal = iVar.f24975g.ordinal();
                if (iOrdinal != 0 && iOrdinal != 1) {
                    if (iOrdinal == 2) {
                        throw new AssertionError("AudioSource is released");
                    }
                    return;
                }
                InterfaceC20169k.a aVar = iVar.f24980l;
                InterfaceC20169k.a aVar2 = (InterfaceC20169k.a) this.f24964d;
                if (aVar != aVar2) {
                    iVar.b(aVar2);
                    return;
                }
                return;
            case 1:
                b.a aVar3 = (b.a) this.f24964d;
                i iVar2 = (i) this.f24963c;
                iVar2.getClass();
                try {
                    int iOrdinal2 = iVar2.f24975g.ordinal();
                    if (iOrdinal2 == 0 || iOrdinal2 == 1) {
                        iVar2.b(null);
                        iVar2.f24973e.release();
                        iVar2.f24972d.release();
                        iVar2.d();
                        iVar2.c(i.d.f24995d);
                    }
                    aVar3.b(null);
                    return;
                } catch (Throwable th2) {
                    aVar3.d(th2);
                    return;
                }
            case 2:
                ((i.b) this.f24964d).b(((i) this.f24963c).f24988t);
                return;
            default:
                ((i.b) this.f24963c).onError((Throwable) this.f24964d);
                return;
        }
    }
}
