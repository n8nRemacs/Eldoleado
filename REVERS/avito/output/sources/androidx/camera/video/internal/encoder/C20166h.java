package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.X;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: BufferCopiedEncodedData.java */
@X
/* renamed from: androidx.camera.video.internal.encoder.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20166h implements InterfaceC20167i {

    /* renamed from: b, reason: collision with root package name */
    public final ByteBuffer f25163b;

    /* renamed from: c, reason: collision with root package name */
    public final MediaCodec.BufferInfo f25164c;

    /* renamed from: d, reason: collision with root package name */
    public final D0<Void> f25165d;

    /* renamed from: e, reason: collision with root package name */
    public final b.a<Void> f25166e;

    public C20166h(@j.N InterfaceC20167i interfaceC20167i) {
        MediaCodec.BufferInfo bufferInfoC0 = interfaceC20167i.C0();
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.set(0, bufferInfoC0.size, bufferInfoC0.presentationTimeUs, bufferInfoC0.flags);
        this.f25164c = bufferInfo;
        ByteBuffer byteBufferE = interfaceC20167i.E();
        MediaCodec.BufferInfo bufferInfoC02 = interfaceC20167i.C0();
        byteBufferE.position(bufferInfoC02.offset);
        byteBufferE.limit(bufferInfoC02.offset + bufferInfoC02.size);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bufferInfoC02.size);
        byteBufferAllocate.order(byteBufferE.order());
        byteBufferAllocate.put(byteBufferE);
        byteBufferAllocate.flip();
        this.f25163b = byteBufferAllocate;
        AtomicReference atomicReference = new AtomicReference();
        this.f25165d = androidx.concurrent.futures.b.a(new C20165g(atomicReference, 0));
        b.a<Void> aVar = (b.a) atomicReference.get();
        aVar.getClass();
        this.f25166e = aVar;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    @j.N
    public final MediaCodec.BufferInfo C0() {
        return this.f25164c;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    @j.N
    public final ByteBuffer E() {
        return this.f25163b;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    public final boolean M3() {
        return (this.f25164c.flags & 1) != 0;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    public final long S0() {
        return this.f25164c.presentationTimeUs;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.f25166e.b(null);
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20167i
    public final long size() {
        return this.f25164c.size;
    }
}
