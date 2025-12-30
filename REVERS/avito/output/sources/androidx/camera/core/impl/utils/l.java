package androidx.camera.core.impl.utils;

import j.N;
import j.X;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import shark.AndroidResourceIdNames;

/* compiled from: ExifOutputStream.java */
@X
/* loaded from: classes.dex */
public final class l extends FilterOutputStream {

    /* renamed from: h, reason: collision with root package name */
    public static final byte[] f24289h = "Exif\u0000\u0000".getBytes(j.f24282d);

    /* renamed from: b, reason: collision with root package name */
    public final ExifData f24290b;

    /* renamed from: c, reason: collision with root package name */
    public final byte[] f24291c;

    /* renamed from: d, reason: collision with root package name */
    public final ByteBuffer f24292d;

    /* renamed from: e, reason: collision with root package name */
    public int f24293e;

    /* renamed from: f, reason: collision with root package name */
    public int f24294f;

    /* renamed from: g, reason: collision with root package name */
    public int f24295g;

    /* compiled from: ExifOutputStream.java */
    public static final class a {
    }

    public l(@N ByteArrayOutputStream byteArrayOutputStream, @N ExifData exifData) {
        super(new BufferedOutputStream(byteArrayOutputStream, AndroidResourceIdNames.RESOURCE_ID_TYPE_ITERATOR));
        this.f24291c = new byte[1];
        this.f24292d = ByteBuffer.allocate(4);
        this.f24293e = 0;
        this.f24290b = exifData;
    }

    /* JADX WARN: Code restructure failed: missing block: B:127:0x0369, code lost:
    
        if (r5 <= 0) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x036b, code lost:
    
        ((java.io.FilterOutputStream) r18).out.write(r19, r4, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0370, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:?, code lost:
    
        return;
     */
    @Override // java.io.FilterOutputStream, java.io.OutputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void write(@j.N byte[] r19, int r20, int r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 881
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.utils.l.write(byte[], int, int):void");
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final void write(int i12) throws IOException {
        byte[] bArr = this.f24291c;
        bArr[0] = (byte) (i12 & 255);
        write(bArr);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final void write(@N byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }
}
