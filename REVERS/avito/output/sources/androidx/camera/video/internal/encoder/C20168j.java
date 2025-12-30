package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.X;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: EncodedDataImpl.java */
@X
/* renamed from: androidx.camera.video.internal.encoder.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20168j implements InterfaceC20167i {

    /* renamed from: b, reason: collision with root package name */
    public final MediaCodec f25167b;

    /* renamed from: c, reason: collision with root package name */
    public final MediaCodec.BufferInfo f25168c;

    /* renamed from: d, reason: collision with root package name */
    public final int f25169d;

    /* renamed from: e, reason: collision with root package name */
    public final ByteBuffer f25170e;

    /* renamed from: f, reason: collision with root package name */
    public final D0<Void> f25171f;

    /* renamed from: g, reason: collision with root package name */
    public final b.a<Void> f25172g;

    /* renamed from: h, reason: collision with root package name */
    public final AtomicBoolean f25173h = new AtomicBoolean(false);

    public C20168j(@j.N MediaCodec mediaCodec, int i12, @j.N MediaCodec.BufferInfo bufferInfo) {
        mediaCodec.getClass();
        this.f25167b = mediaCodec;
        this.f25169d = i12;
        this.f25170e = mediaCodec.getOutputBuffer(i12);
        this.f25168c = bufferInfo;
        AtomicReference atomicReference = new AtomicReference();
        this.f25171f = androidx.concurrent.futures.b.a(new C20165g(atomicReference, 1));
        b.a<Void> aVar = (b.a) atomicReference.get();
        aVar.getClass();
        this.f25172g = aVar;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    @j.N
    public final MediaCodec.BufferInfo C0() {
        return this.f25168c;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    @j.N
    public final ByteBuffer E() {
        if (this.f25173h.get()) {
            throw new IllegalStateException("encoded data is closed.");
        }
        MediaCodec.BufferInfo bufferInfo = this.f25168c;
        int i12 = bufferInfo.offset;
        ByteBuffer byteBuffer = this.f25170e;
        byteBuffer.position(i12);
        byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
        return byteBuffer;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    public final boolean M3() {
        return (this.f25168c.flags & 1) != 0;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    public final long S0() {
        return this.f25168c.presentationTimeUs;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        b.a<Void> aVar = this.f25172g;
        if (this.f25173h.getAndSet(true)) {
            return;
        }
        try {
            this.f25167b.releaseOutputBuffer(this.f25169d, false);
            aVar.b(null);
        } catch (IllegalStateException e12) {
            aVar.d(e12);
        }
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    public final long size() {
        return this.f25168c.size;
    }
}
