package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: FloatList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001\u0082\u0001\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/r;", "", "Landroidx/collection/s0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class r {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public final float[] f25804a;

    public r(int i12, C42822w c42822w) {
        this.f25804a = i12 == 0 ? C20246y.f25833a : new float[i12];
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof r)) {
            return false;
        }
        r rVar = (r) obj;
        rVar.getClass();
        float[] fArr = rVar.f25804a;
        kotlin.ranges.l lVarR = kotlin.ranges.s.r(0, 0);
        int i12 = lVarR.f406905b;
        int i13 = lVarR.f406906c;
        if (i12 > i13) {
            return true;
        }
        while (this.f25804a[i12] == fArr[i12]) {
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
