package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001\u0082\u0001\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/T;", "", "Landroidx/collection/F0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class T {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25609a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    public int f25610b;

    public T(int i12, C42822w c42822w) {
        this.f25609a = i12 == 0 ? C20202b0.f25680a : new long[i12];
    }

    public final boolean equals(@Y61.l Object obj) {
        if (obj instanceof T) {
            T t12 = (T) obj;
            int i12 = t12.f25610b;
            int i13 = this.f25610b;
            if (i12 == i13) {
                long[] jArr = this.f25609a;
                long[] jArr2 = t12.f25609a;
                kotlin.ranges.l lVarR = kotlin.ranges.s.r(0, i13);
                int i14 = lVarR.f406905b;
                int i15 = lVarR.f406906c;
                if (i14 > i15) {
                    return true;
                }
                while (jArr[i14] == jArr2[i14]) {
                    if (i14 == i15) {
                        return true;
                    }
                    i14++;
                }
                return false;
            }
        }
        return false;
    }

    public final int hashCode() {
        long[] jArr = this.f25609a;
        int i12 = this.f25610b;
        int iHashCode = 0;
        for (int i13 = 0; i13 < i12; i13++) {
            iHashCode += Long.hashCode(jArr[i13]) * 31;
        }
        return iHashCode;
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder();
        sb2.append((CharSequence) "[");
        long[] jArr = this.f25609a;
        int i12 = this.f25610b;
        int i13 = 0;
        while (true) {
            if (i13 >= i12) {
                sb2.append((CharSequence) "]");
                break;
            }
            long j12 = jArr[i13];
            if (i13 == -1) {
                sb2.append((CharSequence) "...");
                break;
            }
            if (i13 != 0) {
                sb2.append((CharSequence) ", ");
            }
            sb2.append(j12);
            i13++;
        }
        return sb2.toString();
    }
}
