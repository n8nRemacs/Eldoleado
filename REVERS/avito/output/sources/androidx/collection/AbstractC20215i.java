package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: DoubleList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001\u0082\u0001\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/i;", "", "Landroidx/collection/o0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20215i {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public final double[] f25726a;

    public AbstractC20215i(int i12, C42822w c42822w) {
        this.f25726a = i12 == 0 ? C20219k.f25734a : new double[i12];
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof AbstractC20215i)) {
            return false;
        }
        AbstractC20215i abstractC20215i = (AbstractC20215i) obj;
        abstractC20215i.getClass();
        double[] dArr = abstractC20215i.f25726a;
        kotlin.ranges.l lVarR = kotlin.ranges.s.r(0, 0);
        int i12 = lVarR.f406905b;
        int i13 = lVarR.f406906c;
        if (i12 > i13) {
            return true;
        }
        while (this.f25726a[i12] == dArr[i12]) {
            if (i12 == i13) {
                return true;
            }
            i12++;
        }
        return false;
    }

    public final int hashCode() {
        return 0;
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder();
        sb2.append((CharSequence) "[");
        sb2.append((CharSequence) "]");
        return sb2.toString();
    }
}
