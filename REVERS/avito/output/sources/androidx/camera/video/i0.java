package androidx.camera.video;

import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import androidx.camera.video.l0;
import androidx.concurrent.futures.b;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class i0 implements InterfaceC20169k.c.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l0 f24936b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ b.a f24937c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ K0 f24938d;

    public /* synthetic */ i0(l0 l0Var, b.a aVar, K0 k02) {
        this.f24936b = l0Var;
        this.f24937c = aVar;
        this.f24938d = k02;
    }

    public final void a(Surface surface) {
        Executor executor;
        l0 l0Var = this.f24936b;
        int iOrdinal = l0Var.f25298i.ordinal();
        b.a aVar = this.f24937c;
        if (iOrdinal != 0) {
            if (iOrdinal == 1) {
                K0 k02 = this.f24938d;
                if (k02.f23594f.isDone()) {
                    Objects.toString(k02, "EMPTY");
                    C20140q0.d(3, "VideoEncoderSession");
                    aVar.b(null);
                    l0Var.a();
                    return;
                }
                l0Var.f25294e = surface;
                Objects.toString(surface);
                C20140q0.d(3, "VideoEncoderSession");
                k02.a(surface, l0Var.f25291b, new j0(l0Var, 0));
                l0Var.f25298i = l0.b.f25307e;
                aVar.b(l0Var.f25293d);
                return;
            }
            if (iOrdinal != 2) {
                if (iOrdinal == 3) {
                    if (l0Var.f25297h != null && (executor = l0Var.f25296g) != null) {
                        executor.execute(new K(3, l0Var, surface));
                    }
                    Objects.toString(surface);
                    C20140q0.d(5, "VideoEncoderSession");
                    return;
                }
                if (iOrdinal != 4) {
                    throw new IllegalStateException("State " + l0Var.f25298i + " is not handled");
                }
            }
        }
        Objects.toString(l0Var.f25298i);
        C20140q0.d(3, "VideoEncoderSession");
        aVar.b(null);
    }
}
