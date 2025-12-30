package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: ObjectFloatMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/L0;", "K", "Landroidx/collection/V0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class L0<K> extends V0<K> {

    /* renamed from: f, reason: collision with root package name */
    public int f25563f;

    public L0() {
        this(0, 1, null);
    }

    public final void b() {
        this.f25646e = 0;
        long[] jArr = this.f25642a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25642a;
            int i12 = this.f25645d;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        Arrays.fill(this.f25643b, 0, this.f25645d, (Object) null);
        this.f25563f = i1.a(this.f25645d) - this.f25646e;
    }

    public final int c(int i12) {
        int i13 = this.f25645d;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25642a;
            int i16 = i14 >> 3;
            int i17 = (i14 & 7) << 3;
            long j12 = ((jArr[i16 + 1] << (64 - i17)) & ((-i17) >> 63)) | (jArr[i16] >>> i17);
            long j13 = j12 & ((~j12) << 7) & (-9187201950435737472L);
            if (j13 != 0) {
                return (i14 + (Long.numberOfTrailingZeros(j13) >> 3)) & i13;
            }
            i15 += 8;
            i14 = (i14 + i15) & i13;
        }
    }

    public final void d(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25645d = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25642a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25563f = i1.a(this.f25645d) - this.f25646e;
        this.f25643b = new Object[iMax];
        this.f25644c = new float[iMax];
    }

    public /* synthetic */ L0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }

    public L0(int i12) {
        super(null);
        if (i12 >= 0) {
            d(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }
}
