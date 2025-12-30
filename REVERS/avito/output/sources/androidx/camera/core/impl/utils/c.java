package androidx.camera.core.impl.utils;

import j.X;
import java.io.DataInput;
import java.io.EOFException;
import java.io.InputStream;
import java.nio.ByteOrder;

/* compiled from: ByteOrderedDataInputStream.java */
@X
/* loaded from: classes.dex */
final class c extends InputStream implements DataInput {

    /* renamed from: b, reason: collision with root package name */
    public int f24204b;

    static {
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteOrder byteOrder2 = ByteOrder.BIG_ENDIAN;
    }

    @Override // java.io.InputStream
    public final int available() {
        throw null;
    }

    @Override // java.io.InputStream
    public final void mark(int i12) {
        throw null;
    }

    @Override // java.io.InputStream
    public final int read() {
        this.f24204b++;
        throw null;
    }

    @Override // java.io.DataInput
    public final boolean readBoolean() {
        this.f24204b++;
        throw null;
    }

    @Override // java.io.DataInput
    public final byte readByte() throws EOFException {
        int i12 = this.f24204b + 1;
        this.f24204b = i12;
        if (i12 > 0) {
            throw new EOFException();
        }
        throw null;
    }

    @Override // java.io.DataInput
    public final char readChar() {
        this.f24204b += 2;
        throw null;
    }

    @Override // java.io.DataInput
    public final double readDouble() throws EOFException {
        readLong();
        throw null;
    }

    @Override // java.io.DataInput
    public final float readFloat() throws EOFException {
        readInt();
        throw null;
    }

    @Override // java.io.DataInput
    public final void readFully(byte[] bArr, int i12, int i13) throws EOFException {
        int i14 = this.f24204b + i13;
        this.f24204b = i14;
        if (i14 <= 0) {
            throw null;
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public final int readInt() throws EOFException {
        int i12 = this.f24204b + 4;
        this.f24204b = i12;
        if (i12 > 0) {
            throw new EOFException();
        }
        throw null;
    }

    @Override // java.io.DataInput
    public final String readLine() {
        throw new UnsupportedOperationException("readLine() not implemented.");
    }

    @Override // java.io.DataInput
    public final long readLong() throws EOFException {
        int i12 = this.f24204b + 8;
        this.f24204b = i12;
        if (i12 > 0) {
            throw new EOFException();
        }
        throw null;
    }

    @Override // java.io.DataInput
    public final short readShort() throws EOFException {
        int i12 = this.f24204b + 2;
        this.f24204b = i12;
        if (i12 > 0) {
            throw new EOFException();
        }
        throw null;
    }

    @Override // java.io.DataInput
    public final String readUTF() {
        this.f24204b += 2;
        throw null;
    }

    @Override // java.io.DataInput
    public final int readUnsignedByte() {
        this.f24204b++;
        throw null;
    }

    @Override // java.io.DataInput
    public final int readUnsignedShort() throws EOFException {
        int i12 = this.f24204b + 2;
        this.f24204b = i12;
        if (i12 > 0) {
            throw new EOFException();
        }
        throw null;
    }

    @Override // java.io.DataInput
    public final int skipBytes(int i12) {
        if (Math.min(i12, 0 - this.f24204b) > 0) {
            throw null;
        }
        this.f24204b = this.f24204b;
        return 0;
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i12, int i13) {
        throw null;
    }

    @Override // java.io.DataInput
    public final void readFully(byte[] bArr) throws EOFException {
        int length = this.f24204b + bArr.length;
        this.f24204b = length;
        if (length > 0) {
            throw new EOFException();
        }
        throw null;
    }
}
