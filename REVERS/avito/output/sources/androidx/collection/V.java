package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongLongMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/V;", "", "<init>", "()V", "Landroidx/collection/G0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class V {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25638a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25639b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25640c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25641d;

    public /* synthetic */ V(C42822w c42822w) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00c7, code lost:
    
        r5 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00d3, code lost:
    
        if (((r2 & ((~r2) << 6)) & (-9187201950435737472L)) == 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00d5, code lost:
    
        r26 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(@Y61.l java.lang.Object r30) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.V.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        long[] jArr = this.f25639b;
        long[] jArr2 = this.f25640c;
        long[] jArr3 = this.f25638a;
        int length = jArr3.length - 2;
        int i12 = 0;
        if (length >= 0) {
            int i13 = 0;
            int iHashCode = 0;
            while (true) {
                long j12 = jArr3[i13];
                if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i14 = 8 - ((~(i13 - length)) >>> 31);
                    for (int i15 = 0; i15 < i14; i15++) {
                        if ((255 & j12) < 128) {
                            int i16 = (i13 << 3) + i15;
                            iHashCode += Long.hashCode(jArr[i16]) ^ Long.hashCode(jArr2[i16]);
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

    public V() {
        this.f25638a = i1.f25727a;
        long[] jArr = C20202b0.f25680a;
        this.f25639b = jArr;
        this.f25640c = jArr;
    }
}
