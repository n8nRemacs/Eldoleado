package androidx.camera.core.internal;

import j.X;
import java.io.OutputStream;

/* compiled from: ByteBufferOutputStream.java */
@X
/* loaded from: classes.dex */
public final class b extends OutputStream {
    @Override // java.io.OutputStream
    public final void write(int i12) {
        throw null;
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr, int i12, int i13) {
        int i14;
        bArr.getClass();
        if (i12 < 0 || i12 > bArr.length || i13 < 0 || (i14 = i12 + i13) > bArr.length || i14 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i13 != 0) {
            throw null;
        }
    }
}
