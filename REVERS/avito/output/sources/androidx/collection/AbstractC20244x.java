package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: FloatSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/x;", "", "<init>", "()V", "Landroidx/collection/v0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.x, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20244x {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25829a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public float[] f25830b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    public int f25831c;

    public /* synthetic */ AbstractC20244x(C42822w c42822w) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00bc, code lost:
    
        r2 = ((~r12) << 6) & r12;
        r12 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c8, code lost:
    
        if ((r2 & (-9187201950435737472L)) == 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ca, code lost:
    
        r21 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(@Y61.l java.lang.Object r26) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.AbstractC20244x.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        float[] fArr = this.f25830b;
        long[] jArr = this.f25829a;
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
                            iHashCode = Float.hashCode(fArr[(i13 << 3) + i15]) + iHashCode;
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d A[PHI: r7
  0x005d: PHI (r7v2 int) = (r7v1 int), (r7v3 int) binds: [B:6:0x0026, B:18:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String toString() {
        /*
            r17 = this;
            r0 = r17
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "["
            r1.append(r2)
            float[] r2 = r0.f25830b
            long[] r3 = r0.f25829a
            int r4 = r3.length
            int r4 = r4 + (-2)
            if (r4 < 0) goto L62
            r5 = 0
            r6 = r5
            r7 = r6
        L18:
            r8 = r3[r6]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L5d
            int r10 = r6 - r4
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r5
        L32:
            if (r12 >= r10) goto L5b
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.3E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L57
            int r13 = r6 << 3
            int r13 = r13 + r12
            r13 = r2[r13]
            r14 = -1
            if (r7 != r14) goto L4b
            java.lang.String r2 = "..."
            r1.append(r2)
            goto L67
        L4b:
            if (r7 == 0) goto L52
            java.lang.String r14 = ", "
            r1.append(r14)
        L52:
            r1.append(r13)
            int r7 = r7 + 1
        L57:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L32
        L5b:
            if (r10 != r11) goto L62
        L5d:
            if (r6 == r4) goto L62
            int r6 = r6 + 1
            goto L18
        L62:
            java.lang.String r2 = "]"
            r1.append(r2)
        L67:
            java.lang.String r1 = r1.toString()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.AbstractC20244x.toString():java.lang.String");
    }

    public AbstractC20244x() {
        this.f25829a = i1.f25727a;
        this.f25830b = C20246y.f25833a;
    }
}
