package androidx.camera.video.internal.audio;

import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.q0;
import androidx.camera.video.internal.BufferProvider;
import androidx.camera.video.internal.audio.i;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import j.N;
import j.P;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: AudioSource.java */
/* loaded from: classes.dex */
class g implements q0.a<BufferProvider.State> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20169k.a f24965a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ i f24966b;

    public g(i iVar, InterfaceC20169k.a aVar) {
        this.f24966b = iVar;
        this.f24965a = aVar;
    }

    @Override // androidx.camera.core.impl.q0.a
    public final void a(@P BufferProvider.State state) {
        BufferProvider.State state2 = state;
        Objects.requireNonNull(state2);
        i iVar = this.f24966b;
        if (iVar.f24980l == this.f24965a) {
            Objects.toString(iVar.f24976h);
            state2.toString();
            C20140q0.d(3, "AudioSource");
            if (iVar.f24976h != state2) {
                iVar.f24976h = state2;
                iVar.e();
            }
        }
    }

    @Override // androidx.camera.core.impl.q0.a
    public final void onError(@N Throwable th2) {
        i iVar = this.f24966b;
        if (iVar.f24980l == this.f24965a) {
            Executor executor = iVar.f24978j;
            i.b bVar = iVar.f24979k;
            if (executor == null || bVar == null) {
                return;
            }
            executor.execute(new f(3, bVar, th2));
        }
    }
}
