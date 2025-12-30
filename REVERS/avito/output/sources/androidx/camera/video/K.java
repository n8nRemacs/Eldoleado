package androidx.camera.video;

import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.video.O;
import androidx.camera.video.VideoOutput;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class K implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24710b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24711c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24712d;

    public /* synthetic */ K(int i12, Object obj, Object obj2) {
        this.f24710b = i12;
        this.f24711c = obj;
        this.f24712d = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ScheduledFuture<?> scheduledFuture;
        InterfaceC20169k interfaceC20169k;
        switch (this.f24710b) {
            case 0:
                O o12 = (O) this.f24711c;
                VideoOutput.SourceState sourceState = o12.f24747V;
                VideoOutput.SourceState sourceState2 = (VideoOutput.SourceState) this.f24712d;
                o12.f24747V = sourceState2;
                if (sourceState == sourceState2) {
                    Objects.toString(sourceState2);
                    C20140q0.d(3, "Recorder");
                    break;
                } else {
                    Objects.toString(sourceState2);
                    C20140q0.d(3, "Recorder");
                    if (sourceState2 != VideoOutput.SourceState.f24839d) {
                        if (sourceState2 == VideoOutput.SourceState.f24838c && (scheduledFuture = o12.f24748W) != null && scheduledFuture.cancel(false) && (interfaceC20169k = o12.f24728C) != null) {
                            O.q(interfaceC20169k);
                            break;
                        }
                    } else if (o12.f24777y != null) {
                        o12.f24749X = true;
                        O.i iVar = o12.f24766n;
                        if (iVar != null && !iVar.j()) {
                            o12.r(o12.f24766n, 4, null);
                            break;
                        }
                    } else {
                        o12.t(false);
                        break;
                    }
                }
                break;
            case 1:
                ((O.i) this.f24711c).d().accept((m0) this.f24712d);
                break;
            case 2:
                c0 c0Var = (c0) this.f24711c;
                c0Var.getClass();
                ((SessionConfig.b) this.f24712d).o(c0Var);
                break;
            case 3:
                ((l0) this.f24711c).f25297h.f24695b.z((Surface) this.f24712d);
                break;
            case 4:
                ((O) this.f24711c).f24755c.execute(new f0((InterfaceC20169k) this.f24712d, 1));
                break;
            default:
                b0 b0Var = (b0) this.f24711c;
                if (((DeferrableSurface) this.f24712d) == b0Var.f24864n) {
                    b0Var.H();
                    break;
                }
                break;
        }
    }
}
