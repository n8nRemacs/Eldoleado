package androidx.camera.video.internal.encoder;

import android.util.Range;
import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.encoder.v;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class p implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25178b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ v f25179c;

    public /* synthetic */ p(v vVar, int i12) {
        this.f25178b = i12;
        this.f25179c = vVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        v vVar = this.f25179c;
        switch (this.f25178b) {
            case 0:
                Range<Long> range = v.f25192D;
                androidx.camera.core.impl.utils.futures.f.a(vVar.f(), new u(vVar), vVar.f25203h);
                return;
            case 1:
                int iOrdinal = vVar.f25215t.ordinal();
                if (iOrdinal == 1) {
                    vVar.k();
                    return;
                } else {
                    if (iOrdinal == 6 || iOrdinal == 8) {
                        throw new IllegalStateException("Encoder is released");
                    }
                    return;
                }
            case 2:
                switch (vVar.f25215t.ordinal()) {
                    case 0:
                    case 1:
                    case 2:
                    case 7:
                        vVar.j();
                        return;
                    case 3:
                    case 4:
                    case 5:
                        vVar.m(v.d.f25233h);
                        return;
                    case 6:
                    case 8:
                        return;
                    default:
                        throw new IllegalStateException("Unknown state: " + vVar.f25215t);
                }
            case 3:
                vVar.f25203h.execute(new p(vVar, 4));
                return;
            case 4:
                if (vVar.f25218w) {
                    C20140q0.d(5, vVar.f25196a);
                    vVar.f25219x = null;
                    vVar.n();
                    vVar.f25218w = false;
                    return;
                }
                return;
            default:
                vVar.f25194B = true;
                if (vVar.f25193A) {
                    vVar.f25200e.stop();
                    vVar.l();
                    return;
                }
                return;
        }
    }
}
