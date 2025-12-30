package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntFloatMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/A;", "", "<init>", "()V", "Landroidx/collection/w0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class A {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25520a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public int[] f25521b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    @Y61.k
    public float[] f25522c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25523d;

    public /* synthetic */ A(C42822w c42822w) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00c6, code lost:
    
        r5 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00d2, code lost:
    
        if (((r2 & ((~r2) << 6)) & (-9187201950435737472L)) == 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00d4, code lost:
    
        r24 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(@Y61.l java.lang.Object r28) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.A.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int[] iArr = this.f25521b;
        float[] fArr = this.f25522c;
        long[] jArr = this.f25520a;
        int length = jArr.length - 2;
        int i12 = 0;
        if (length >= 0) {
            int i13 = 0;
            int iHashCode = 0;
            while (true) {
                long j12 = jArr[i13];
                if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i14 = 8 - ((~(i13 - length)) >>> 31);
                    for (int i15 = 0; i15 < i14; i15++) {
                        if ((255 & j12) < 128) {
                            int i16 = (i13 << 3) + i15;
                            int i17 = iArr[i16];
                            iHashCode += Float.hashCode(fArr[i16]) ^ Integer.hashCode(i17);
                        }
                        j12 >>= 8;
                    }
                    if (i14 != 8) {
                        return iHashCode;
                    }
                }
                if (i13 == length) {
                    i12 = iHashCode;
                    break;
                }
                i13++;
            }
        }
        return i12;
    }

    @Y61.k
    public final String toString() {
        return "{}";
    }

    public A() {
        this.f25520a = i1.f25727a;
        this.f25521b = M.f25564a;
        this.f25522c = C20246y.f25833a;
    }
}
