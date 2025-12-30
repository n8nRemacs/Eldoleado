package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001\u0082\u0001\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/F;", "", "Landroidx/collection/y0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class F {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public int[] f25544a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    public int f25545b;

    public F(int i12, C42822w c42822w) {
        this.f25544a = i12 == 0 ? M.f25564a : new int[i12];
    }

    public final int a(@j.F int i12) {
        if (i12 >= 0 && i12 < this.f25545b) {
            return this.f25544a[i12];
        }
        K.f.b("Index must be between 0 and size");
        throw null;
    }

    public final int b() {
        int i12 = this.f25545b;
        if (i12 != 0) {
            return this.f25544a[i12 - 1];
        }
        K.f.c("IntList is empty.");
        throw null;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (obj instanceof F) {
            F f12 = (F) obj;
            int i12 = f12.f25545b;
            int i13 = this.f25545b;
            if (i12 == i13) {
                int[] iArr = this.f25544a;
                int[] iArr2 = f12.f25544a;
                kotlin.ranges.l lVarR = kotlin.ranges.s.r(0, i13);
                int i14 = lVarR.f406905b;
                int i15 = lVarR.f406906c;
                if (i14 > i15) {
                    return true;
                }
                while (iArr[i14] == iArr2[i14]) {
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
        int[] iArr = this.f25544a;
        int i12 = this.f25545b;
        int iHashCode = 0;
        for (int i13 = 0; i13 < i12; i13++) {
            iHashCode += Integer.hashCode(iArr[i13]) * 31;
        }
        return iHashCode;
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder();
        sb2.append((CharSequence) "[");
        int[] iArr = this.f25544a;
        int i12 = this.f25545b;
        int i13 = 0;
        while (true) {
            if (i13 >= i12) {
                sb2.append((CharSequence) "]");
                break;
            }
            int i14 = iArr[i13];
            if (i13 == -1) {
                sb2.append((CharSequence) "...");
                break;
            }
            if (i13 != 0) {
                sb2.append((CharSequence) ", ");
            }
            sb2.append(i14);
            i13++;
        }
        return sb2.toString();
    }
}
