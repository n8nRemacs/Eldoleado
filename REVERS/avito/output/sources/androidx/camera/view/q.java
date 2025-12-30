package androidx.camera.view;

import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.q0;
import androidx.camera.view.PreviewView;
import androidx.view.C23038g0;
import j.K;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: PreviewStreamStateObserver.java */
@X
/* loaded from: classes.dex */
final class q implements q0.a<CameraInternal.State> {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.impl.B f25454a;

    /* renamed from: b, reason: collision with root package name */
    public final C23038g0<PreviewView.StreamState> f25455b;

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public PreviewView.StreamState f25456c;

    /* renamed from: d, reason: collision with root package name */
    public final u f25457d;

    /* renamed from: e, reason: collision with root package name */
    public androidx.camera.core.impl.utils.futures.d f25458e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f25459f = false;

    public q(androidx.camera.core.impl.B b12, C23038g0<PreviewView.StreamState> c23038g0, u uVar) {
        this.f25454a = b12;
        this.f25455b = c23038g0;
        this.f25457d = uVar;
        synchronized (this) {
            this.f25456c = c23038g0.getValue();
        }
    }

    @Override // androidx.camera.core.impl.q0.a
    @K
    public final void a(@P CameraInternal.State state) {
        CameraInternal.State state2 = state;
        CameraInternal.State state3 = CameraInternal.State.CLOSING;
        PreviewView.StreamState streamState = PreviewView.StreamState.f25404b;
        if (state2 == state3 || state2 == CameraInternal.State.CLOSED || state2 == CameraInternal.State.RELEASING || state2 == CameraInternal.State.RELEASED) {
            b(streamState);
            if (this.f25459f) {
                this.f25459f = false;
                androidx.camera.core.impl.utils.futures.d dVar = this.f25458e;
                if (dVar != null) {
                    dVar.cancel(false);
                    this.f25458e = null;
                    return;
                }
                return;
            }
            return;
        }
        if ((state2 == CameraInternal.State.OPENING || state2 == CameraInternal.State.OPEN || state2 == CameraInternal.State.PENDING_OPEN) && !this.f25459f) {
            b(streamState);
            ArrayList arrayList = new ArrayList();
            androidx.camera.core.impl.B b12 = this.f25454a;
            androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(androidx.concurrent.futures.b.a(new n(this, b12, arrayList)));
            m mVar = new m(this);
            Executor executorA = androidx.camera.core.impl.utils.executor.c.a();
            dVarA.getClass();
            androidx.camera.core.impl.utils.futures.d dVar2 = (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.l((androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, mVar, executorA), new C20190b(this, 1), androidx.camera.core.impl.utils.executor.c.a());
            this.f25458e = dVar2;
            androidx.camera.core.impl.utils.futures.f.a(dVar2, new o(this, arrayList, b12), androidx.camera.core.impl.utils.executor.c.a());
            this.f25459f = true;
        }
    }

    public final void b(PreviewView.StreamState streamState) {
        synchronized (this) {
            try {
                if (this.f25456c.equals(streamState)) {
                    return;
                }
                this.f25456c = streamState;
                Objects.toString(streamState);
                C20140q0.d(3, "StreamStateObserver");
                this.f25455b.postValue(streamState);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.q0.a
    @K
    public final void onError(@N Throwable th2) {
        androidx.camera.core.impl.utils.futures.d dVar = this.f25458e;
        if (dVar != null) {
            dVar.cancel(false);
            this.f25458e = null;
        }
        b(PreviewView.StreamState.f25404b);
    }
}
