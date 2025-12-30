package androidx.collection;

import kotlin.Metadata;

/* compiled from: SparseArrayCompat.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class r1 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final Object f25805a = new Object();

    public static final void a(q1 q1Var) {
        int i12 = q1Var.f25803e;
        int[] iArr = q1Var.f25801c;
        Object[] objArr = q1Var.f25802d;
        int i13 = 0;
        for (int i14 = 0; i14 < i12; i14++) {
            Object obj = objArr[i14];
            if (obj != f25805a) {
                if (i14 != i13) {
                    iArr[i13] = iArr[i14];
                    objArr[i13] = obj;
                    objArr[i14] = null;
                }
                i13++;
            }
        }
        q1Var.f25800b = false;
        q1Var.f25803e = i13;
    }
}
