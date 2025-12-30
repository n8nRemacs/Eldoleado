package androidx.camera.video.internal.audio;

import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.audio.AudioStream;
import androidx.camera.video.internal.audio.i;
import androidx.camera.video.internal.encoder.E;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import androidx.core.util.z;
import com.google.common.util.concurrent.D0;
import j.N;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: AudioSource.java */
/* loaded from: classes.dex */
class h implements androidx.camera.core.impl.utils.futures.c<E> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20169k.a f24967a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ i f24968b;

    public h(i iVar, InterfaceC20169k.a aVar) {
        this.f24968b = iVar;
        this.f24967a = aVar;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@N Throwable th2) {
        i iVar = this.f24968b;
        if (iVar.f24980l != this.f24967a) {
            return;
        }
        C20140q0.d(3, "AudioSource");
        if (th2 instanceof IllegalStateException) {
            return;
        }
        Executor executor = iVar.f24978j;
        i.b bVar = iVar.f24979k;
        if (executor == null || bVar == null) {
            return;
        }
        executor.execute(new f(3, bVar, th2));
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(E e12) {
        E e13 = e12;
        i iVar = this.f24968b;
        if (!iVar.f24977i || iVar.f24980l != this.f24967a) {
            e13.cancel();
            return;
        }
        boolean z12 = iVar.f24983o;
        AudioStream audioStream = iVar.f24972d;
        AudioStream audioStream2 = iVar.f24973e;
        if (z12) {
            z.g(null, iVar.f24984p > 0);
            if (System.nanoTime() - iVar.f24984p >= iVar.f24974f) {
                z.g(null, iVar.f24983o);
                try {
                    audioStream.start();
                    C20140q0.d(3, "AudioSource");
                    audioStream2.stop();
                    iVar.f24983o = false;
                } catch (AudioStream.AudioStreamException unused) {
                    C20140q0.d(5, "AudioSource");
                    iVar.f24984p = System.nanoTime();
                }
            }
        }
        if (iVar.f24983o) {
            audioStream = audioStream2;
        }
        ByteBuffer byteBufferE = e13.E();
        o oVar = (o) audioStream.read(byteBufferE);
        int i12 = oVar.f25020a;
        if (i12 > 0) {
            if (iVar.f24986r) {
                byte[] bArr = iVar.f24987s;
                if (bArr == null || bArr.length < i12) {
                    iVar.f24987s = new byte[i12];
                }
                int iPosition = byteBufferE.position();
                byteBufferE.put(iVar.f24987s, 0, i12);
                byteBufferE.limit(byteBufferE.position()).position(iPosition);
            }
            Executor executor = iVar.f24978j;
            long j12 = oVar.f25021b;
            if (executor != null && j12 - iVar.f24989u >= 200) {
                iVar.f24989u = j12;
                i.b bVar = iVar.f24979k;
                if (iVar.f24990v == 2) {
                    ShortBuffer shortBufferAsShortBuffer = byteBufferE.asShortBuffer();
                    double dMax = 0.0d;
                    while (shortBufferAsShortBuffer.hasRemaining()) {
                        dMax = Math.max(dMax, Math.abs((int) shortBufferAsShortBuffer.get()));
                    }
                    iVar.f24988t = dMax / 32767.0d;
                    if (executor != null && bVar != null) {
                        executor.execute(new f(2, iVar, bVar));
                    }
                }
            }
            byteBufferE.limit(i12 + byteBufferE.position());
            e13.b(TimeUnit.NANOSECONDS.toMicros(j12));
            e13.a();
        } else {
            C20140q0.d(5, "AudioSource");
            e13.cancel();
        }
        InterfaceC20169k.a aVar = iVar.f24980l;
        Objects.requireNonNull(aVar);
        D0<E> d0E = aVar.e();
        androidx.camera.core.impl.utils.futures.c<E> cVar = iVar.f24981m;
        Objects.requireNonNull(cVar);
        androidx.camera.core.impl.utils.futures.f.a(d0E, cVar, iVar.f24969a);
    }
}
