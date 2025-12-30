package androidx.camera.core.impl.utils;

import j.N;
import j.X;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* compiled from: ExifAttribute.java */
@X
/* loaded from: classes.dex */
final class j {

    /* renamed from: d, reason: collision with root package name */
    public static final Charset f24282d = StandardCharsets.US_ASCII;

    /* renamed from: e, reason: collision with root package name */
    public static final String[] f24283e = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};

    /* renamed from: f, reason: collision with root package name */
    public static final int[] f24284f = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};

    /* renamed from: a, reason: collision with root package name */
    public final int f24285a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24286b;

    /* renamed from: c, reason: collision with root package name */
    public final byte[] f24287c;

    public j(int i12, int i13, byte[] bArr) {
        this.f24285a = i12;
        this.f24286b = i13;
        this.f24287c = bArr;
    }

    @N
    public static j a(long j12, @N ByteOrder byteOrder) {
        return b(new long[]{j12}, byteOrder);
    }

    @N
    public static j b(@N long[] jArr, @N ByteOrder byteOrder) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[f24284f[4] * jArr.length]);
        byteBufferWrap.order(byteOrder);
        for (long j12 : jArr) {
            byteBufferWrap.putInt((int) j12);
        }
        return new j(4, jArr.length, byteBufferWrap.array());
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("(");
        sb2.append(f24283e[this.f24285a]);
        sb2.append(", data length:");
        return AK.c.i(this.f24287c.length, ")", sb2);
    }
}
