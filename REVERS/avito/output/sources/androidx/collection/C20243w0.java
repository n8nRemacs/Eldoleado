package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntFloatMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/w0;", "Landroidx/collection/A;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.w0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20243w0 extends A {
    public C20243w0() {
        this(0, 1, null);
    }

    public C20243w0(int i12) {
        long[] jArr;
        super(null);
        if (i12 < 0) {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
        int iE2 = i1.e(i12);
        int iMax = iE2 > 0 ? Math.max(7, i1.d(iE2)) : 0;
        this.f25523d = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25520a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25521b = new int[iMax];
        this.f25522c = new float[iMax];
    }

    public /* synthetic */ C20243w0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }
}
