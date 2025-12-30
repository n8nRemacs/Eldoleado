package androidx.camera.video.internal.audio;

import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.audio.AudioStream;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: SilentAudioStream.java */
@X
/* loaded from: classes.dex */
public class s implements AudioStream {

    /* renamed from: a, reason: collision with root package name */
    public final AtomicBoolean f25043a = new AtomicBoolean(false);

    /* renamed from: b, reason: collision with root package name */
    public final AtomicBoolean f25044b = new AtomicBoolean(false);

    /* renamed from: c, reason: collision with root package name */
    public final int f25045c;

    /* renamed from: d, reason: collision with root package name */
    public final int f25046d;

    /* renamed from: e, reason: collision with root package name */
    @P
    public byte[] f25047e;

    /* renamed from: f, reason: collision with root package name */
    public long f25048f;

    /* renamed from: g, reason: collision with root package name */
    @P
    public AudioStream.a f25049g;

    /* renamed from: h, reason: collision with root package name */
    @P
    public Executor f25050h;

    public s(@N a aVar) {
        this.f25045c = aVar.d();
        this.f25046d = aVar.f();
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void a(@P AudioStream.a aVar, @P Executor executor) {
        z.g("AudioStream can not be started when setCallback.", !this.f25043a.get());
        b();
        this.f25049g = aVar;
        this.f25050h = executor;
    }

    public final void b() {
        z.g("AudioStream has been released.", !this.f25044b.get());
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    @N
    public final AudioStream.b read(@N ByteBuffer byteBuffer) throws InterruptedException {
        b();
        z.g("AudioStream has not been started.", this.f25043a.get());
        long jRemaining = byteBuffer.remaining();
        int i12 = this.f25045c;
        long jB2 = m.b(i12, jRemaining);
        long j12 = i12;
        z.a("bytesPerFrame must be greater than 0.", j12 > 0);
        int i13 = (int) (j12 * jB2);
        if (i13 <= 0) {
            return new o(0, this.f25048f);
        }
        long jA2 = this.f25048f + m.a(this.f25046d, jB2);
        long jNanoTime = jA2 - System.nanoTime();
        if (jNanoTime > 0) {
            try {
                Thread.sleep(TimeUnit.NANOSECONDS.toMillis(jNanoTime));
            } catch (InterruptedException unused) {
                C20140q0.d(5, "SilentAudioStream");
            }
        }
        z.g(null, i13 <= byteBuffer.remaining());
        byte[] bArr = this.f25047e;
        if (bArr == null || bArr.length < i13) {
            this.f25047e = new byte[i13];
        }
        int iPosition = byteBuffer.position();
        byteBuffer.put(this.f25047e, 0, i13).limit(iPosition + i13).position(iPosition);
        o oVar = new o(i13, this.f25048f);
        this.f25048f = jA2;
        return oVar;
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void release() {
        this.f25044b.getAndSet(true);
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void start() {
        b();
        if (this.f25043a.getAndSet(true)) {
            return;
        }
        this.f25048f = System.nanoTime();
        AudioStream.a aVar = this.f25049g;
        Executor executor = this.f25050h;
        if (aVar == null || executor == null) {
            return;
        }
        executor.execute(new b(aVar, 1));
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void stop() {
        b();
        this.f25043a.set(false);
    }
}
