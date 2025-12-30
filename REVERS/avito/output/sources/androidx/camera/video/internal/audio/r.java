package androidx.camera.video.internal.audio;

import android.annotation.SuppressLint;
import androidx.camera.camera2.internal.G;
import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.audio.AudioStream;
import androidx.core.util.z;
import j.B;
import j.N;
import j.P;
import j.X;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: BufferedAudioStream.java */
@X
/* loaded from: classes.dex */
public class r implements AudioStream {

    /* renamed from: a, reason: collision with root package name */
    public final AtomicBoolean f25027a = new AtomicBoolean(false);

    /* renamed from: b, reason: collision with root package name */
    public final AtomicBoolean f25028b = new AtomicBoolean(false);

    /* renamed from: c, reason: collision with root package name */
    @B
    public final ConcurrentLinkedQueue f25029c = new ConcurrentLinkedQueue();

    /* renamed from: d, reason: collision with root package name */
    public final Executor f25030d;

    /* renamed from: e, reason: collision with root package name */
    public final Object f25031e;

    /* renamed from: f, reason: collision with root package name */
    @B
    @P
    public a f25032f;

    /* renamed from: g, reason: collision with root package name */
    public final AudioStream f25033g;

    /* renamed from: h, reason: collision with root package name */
    public final int f25034h;

    /* renamed from: i, reason: collision with root package name */
    public final int f25035i;

    /* renamed from: j, reason: collision with root package name */
    public final int f25036j;

    /* renamed from: k, reason: collision with root package name */
    public final AtomicBoolean f25037k;

    /* renamed from: l, reason: collision with root package name */
    public int f25038l;

    /* compiled from: BufferedAudioStream.java */
    @X
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final int f25039a;

        /* renamed from: b, reason: collision with root package name */
        public final int f25040b;

        /* renamed from: c, reason: collision with root package name */
        public final ByteBuffer f25041c;

        /* renamed from: d, reason: collision with root package name */
        public long f25042d;

        public a(@N ByteBuffer byteBuffer, @N AudioStream.b bVar, int i12, int i13) {
            byteBuffer.rewind();
            int iLimit = byteBuffer.limit() - byteBuffer.position();
            if (iLimit != bVar.a()) {
                StringBuilder sbJ = G.j(iLimit, "Byte buffer size is not match with packet info: ", " != ");
                sbJ.append(bVar.a());
                throw new IllegalStateException(sbJ.toString());
            }
            this.f25039a = i12;
            this.f25040b = i13;
            this.f25041c = byteBuffer;
            this.f25042d = bVar.b();
        }

        public final AudioStream.b a(@N ByteBuffer byteBuffer) {
            int iRemaining;
            long j12 = this.f25042d;
            ByteBuffer byteBuffer2 = this.f25041c;
            int iPosition = byteBuffer2.position();
            int iPosition2 = byteBuffer.position();
            if (byteBuffer2.remaining() > byteBuffer.remaining()) {
                iRemaining = byteBuffer.remaining();
                this.f25042d += m.a(this.f25040b, m.b(this.f25039a, iRemaining));
                ByteBuffer byteBufferDuplicate = byteBuffer2.duplicate();
                byteBufferDuplicate.position(iPosition).limit(iPosition + iRemaining);
                byteBuffer.put(byteBufferDuplicate).limit(iPosition2 + iRemaining).position(iPosition2);
            } else {
                iRemaining = byteBuffer2.remaining();
                byteBuffer.put(byteBuffer2).limit(iPosition2 + iRemaining).position(iPosition2);
            }
            byteBuffer2.position(iPosition + iRemaining);
            return new o(iRemaining, j12);
        }
    }

    public r(@N AudioStream audioStream, @N androidx.camera.video.internal.audio.a aVar) {
        androidx.camera.core.impl.utils.executor.b bVar;
        if (androidx.camera.core.impl.utils.executor.b.f24209c != null) {
            bVar = androidx.camera.core.impl.utils.executor.b.f24209c;
        } else {
            synchronized (androidx.camera.core.impl.utils.executor.b.class) {
                try {
                    if (androidx.camera.core.impl.utils.executor.b.f24209c == null) {
                        androidx.camera.core.impl.utils.executor.b.f24209c = new androidx.camera.core.impl.utils.executor.b();
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            bVar = androidx.camera.core.impl.utils.executor.b.f24209c;
        }
        this.f25030d = androidx.camera.core.impl.utils.executor.c.f(bVar);
        this.f25031e = new Object();
        this.f25032f = null;
        this.f25037k = new AtomicBoolean(false);
        this.f25033g = audioStream;
        int iD2 = aVar.d();
        this.f25034h = iD2;
        int iF2 = aVar.f();
        this.f25035i = iF2;
        z.a("mBytesPerFrame must be greater than 0.", ((long) iD2) > 0);
        z.a("mSampleRate must be greater than 0.", ((long) iF2) > 0);
        this.f25036j = 500;
        this.f25038l = iD2 * 1024;
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void a(@P AudioStream.a aVar, @P Executor executor) {
        z.g("AudioStream can not be started when setCallback.", !this.f25027a.get());
        b();
        this.f25030d.execute(new c(this, aVar, executor));
    }

    public final void b() {
        z.g("AudioStream has been released.", !this.f25028b.get());
    }

    public final void c() {
        if (this.f25037k.get()) {
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(this.f25038l);
            a aVar = new a(byteBufferAllocateDirect, this.f25033g.read(byteBufferAllocateDirect), this.f25034h, this.f25035i);
            int i12 = this.f25036j;
            synchronized (this.f25031e) {
                try {
                    this.f25029c.offer(aVar);
                    while (this.f25029c.size() > i12) {
                        this.f25029c.poll();
                        C20140q0.d(5, "BufferedAudioStream");
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            if (this.f25037k.get()) {
                this.f25030d.execute(new p(this, 3));
            }
        }
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    @N
    @SuppressLint({"BanThreadSleep"})
    public final AudioStream.b read(@N ByteBuffer byteBuffer) throws InterruptedException {
        boolean z12;
        b();
        z.g("AudioStream has not been started.", this.f25027a.get());
        this.f25030d.execute(new q(byteBuffer.remaining(), 0, this));
        AudioStream.b oVar = new o(0, 0L);
        do {
            synchronized (this.f25031e) {
                try {
                    a aVar = this.f25032f;
                    this.f25032f = null;
                    if (aVar == null) {
                        aVar = (a) this.f25029c.poll();
                    }
                    if (aVar != null) {
                        oVar = aVar.a(byteBuffer);
                        if (aVar.f25041c.remaining() > 0) {
                            this.f25032f = aVar;
                        }
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            z12 = ((o) oVar).f25020a <= 0 && this.f25027a.get() && !this.f25028b.get();
            if (z12) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException unused) {
                    C20140q0.d(5, "BufferedAudioStream");
                }
            }
        } while (z12);
        return oVar;
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void release() {
        if (this.f25028b.getAndSet(true)) {
            return;
        }
        this.f25030d.execute(new p(this, 1));
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void start() {
        b();
        AtomicBoolean atomicBoolean = this.f25027a;
        if (atomicBoolean.getAndSet(true)) {
            return;
        }
        FutureTask futureTask = new FutureTask(new p(this, 2), null);
        this.f25030d.execute(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException | ExecutionException e12) {
            atomicBoolean.set(false);
            throw new AudioStream.AudioStreamException(e12);
        }
    }

    @Override // androidx.camera.video.internal.audio.AudioStream
    public final void stop() {
        b();
        if (this.f25027a.getAndSet(false)) {
            this.f25030d.execute(new p(this, 0));
        }
    }
}
