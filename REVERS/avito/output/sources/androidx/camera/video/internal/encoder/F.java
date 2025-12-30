package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.X;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: InputBufferImpl.java */
@X
/* loaded from: classes.dex */
class F implements E {

    /* renamed from: a, reason: collision with root package name */
    public final MediaCodec f25115a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25116b;

    /* renamed from: c, reason: collision with root package name */
    public final ByteBuffer f25117c;

    /* renamed from: d, reason: collision with root package name */
    public final D0<Void> f25118d;

    /* renamed from: e, reason: collision with root package name */
    public final b.a<Void> f25119e;

    /* renamed from: f, reason: collision with root package name */
    public final AtomicBoolean f25120f = new AtomicBoolean(false);

    /* renamed from: g, reason: collision with root package name */
    public long f25121g = 0;

    /* renamed from: h, reason: collision with root package name */
    public boolean f25122h = false;

    public F(@j.N MediaCodec mediaCodec, @j.F int i12) {
        mediaCodec.getClass();
        this.f25115a = mediaCodec;
        androidx.core.util.z.d(i12);
        this.f25116b = i12;
        this.f25117c = mediaCodec.getInputBuffer(i12);
        AtomicReference atomicReference = new AtomicReference();
        this.f25118d = androidx.concurrent.futures.b.a(new C20165g(atomicReference, 4));
        b.a<Void> aVar = (b.a) atomicReference.get();
        aVar.getClass();
        this.f25119e = aVar;
    }

    @Override // androidx.camera.video.internal.encoder.E
    @j.N
    public final ByteBuffer E() {
        if (this.f25120f.get()) {
            throw new IllegalStateException("The buffer is submitted or canceled.");
        }
        return this.f25117c;
    }

    @Override // androidx.camera.video.internal.encoder.E
    public final boolean a() throws MediaCodec.CryptoException {
        b.a<Void> aVar = this.f25119e;
        ByteBuffer byteBuffer = this.f25117c;
        if (this.f25120f.getAndSet(true)) {
            return false;
        }
        try {
            this.f25115a.queueInputBuffer(this.f25116b, byteBuffer.position(), byteBuffer.limit(), this.f25121g, this.f25122h ? 4 : 0);
            aVar.b(null);
            return true;
        } catch (IllegalStateException e12) {
            aVar.d(e12);
            return false;
        }
    }

    @Override // androidx.camera.video.internal.encoder.E
    public final void b(long j12) {
        if (this.f25120f.get()) {
            throw new IllegalStateException("The buffer is submitted or canceled.");
        }
        androidx.core.util.z.b(j12 >= 0);
        this.f25121g = j12;
    }

    @Override // androidx.camera.video.internal.encoder.E
    public final void c() {
        if (this.f25120f.get()) {
            throw new IllegalStateException("The buffer is submitted or canceled.");
        }
        this.f25122h = true;
    }

    @Override // androidx.camera.video.internal.encoder.E
    public final boolean cancel() {
        b.a<Void> aVar = this.f25119e;
        if (this.f25120f.getAndSet(true)) {
            return false;
        }
        try {
            this.f25115a.queueInputBuffer(this.f25116b, 0, 0, 0L, 0);
            aVar.b(null);
        } catch (IllegalStateException e12) {
            aVar.d(e12);
        }
        return true;
    }

    @Override // androidx.camera.video.internal.encoder.E
    @j.N
    public final D0<Void> d() {
        return androidx.camera.core.impl.utils.futures.f.h(this.f25118d);
    }
}
