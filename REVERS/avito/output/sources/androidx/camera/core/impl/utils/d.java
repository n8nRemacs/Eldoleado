package androidx.camera.core.impl.utils;

import j.X;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

/* compiled from: ByteOrderedDataOutputStream.java */
@X
/* loaded from: classes.dex */
class d extends FilterOutputStream {

    /* renamed from: b, reason: collision with root package name */
    public final OutputStream f24205b;

    /* renamed from: c, reason: collision with root package name */
    public ByteOrder f24206c;

    public d(OutputStream outputStream, ByteOrder byteOrder) {
        super(outputStream);
        this.f24205b = outputStream;
        this.f24206c = byteOrder;
    }

    public final void a(int i12) throws IOException {
        ByteOrder byteOrder = this.f24206c;
        ByteOrder byteOrder2 = ByteOrder.LITTLE_ENDIAN;
        OutputStream outputStream = this.f24205b;
        if (byteOrder == byteOrder2) {
            outputStream.write(i12 & 255);
            outputStream.write((i12 >>> 8) & 255);
            outputStream.write((i12 >>> 16) & 255);
            outputStream.write((i12 >>> 24) & 255);
            return;
        }
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            outputStream.write((i12 >>> 24) & 255);
            outputStream.write((i12 >>> 16) & 255);
            outputStream.write((i12 >>> 8) & 255);
            outputStream.write(i12 & 255);
        }
    }

    public final void b(short s5) throws IOException {
        ByteOrder byteOrder = this.f24206c;
        ByteOrder byteOrder2 = ByteOrder.LITTLE_ENDIAN;
        OutputStream outputStream = this.f24205b;
        if (byteOrder == byteOrder2) {
            outputStream.write(s5 & 255);
            outputStream.write((s5 >>> 8) & 255);
        } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
            outputStream.write((s5 >>> 8) & 255);
            outputStream.write(s5 & 255);
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final void write(byte[] bArr) throws IOException {
        this.f24205b.write(bArr);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final void write(byte[] bArr, int i12, int i13) throws IOException {
        this.f24205b.write(bArr, i12, i13);
    }
}
