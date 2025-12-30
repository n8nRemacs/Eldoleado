package androidx.camera.video;

import androidx.camera.core.C20140q0;
import androidx.camera.video.VideoOutput;
import com.google.common.util.concurrent.D0;
import java.util.concurrent.CancellationException;

/* compiled from: VideoCapture.java */
/* loaded from: classes.dex */
class d0 implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ D0 f24899a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ boolean f24900b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ b0 f24901c;

    public d0(b0 b0Var, D0 d02, boolean z12) {
        this.f24901c = b0Var;
        this.f24899a = d02;
        this.f24900b = z12;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        if (th2 instanceof CancellationException) {
            return;
        }
        C20140q0.c("VideoCapture");
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P Void r32) {
        VideoOutput.SourceState sourceState;
        b0 b0Var = this.f24901c;
        if (this.f24899a != b0Var.f24868r || (sourceState = b0Var.f24870t) == VideoOutput.SourceState.f24839d) {
            return;
        }
        VideoOutput.SourceState sourceState2 = this.f24900b ? VideoOutput.SourceState.f24837b : VideoOutput.SourceState.f24838c;
        if (sourceState2 != sourceState) {
            b0Var.f24870t = sourceState2;
            b0Var.J().e(sourceState2);
        }
    }
}
