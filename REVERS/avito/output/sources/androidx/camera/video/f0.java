package androidx.camera.video;

import androidx.camera.core.C20140q0;
import androidx.camera.video.O;
import androidx.camera.video.b0;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import java.util.Set;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class f0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24908b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24909c;

    public /* synthetic */ f0(Object obj, int i12) {
        this.f24908b = i12;
        this.f24909c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj = this.f24909c;
        switch (this.f24908b) {
            case 0:
                ((l0) obj).f25300k.b(null);
                break;
            case 1:
                Set<O.j> set = O.f24719b0;
                C20140q0.d(3, "Recorder");
                if (androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.d.class) != null) {
                    O.q((InterfaceC20169k) obj);
                    break;
                }
                break;
            default:
                b0.c cVar = b0.f24861A;
                ((b0) obj).p();
                break;
        }
    }
}
