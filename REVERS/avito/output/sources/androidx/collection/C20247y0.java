package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/y0;", "Landroidx/collection/F;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.y0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20247y0 extends F {
    public C20247y0() {
        this(0, 1, null);
    }

    public final void c() {
        int i12 = this.f25545b;
        if (i12 < 0) {
            K.f.b("Index must be between 0 and size");
            throw null;
        }
        e(i12 + 1);
        int[] iArr = this.f25544a;
        int i13 = this.f25545b;
        if (i13 != 0) {
            C42756l.k(1, 0, i13, iArr, iArr);
        }
        iArr[0] = 0;
        this.f25545b++;
    }

    public final void d(int i12) {
        e(this.f25545b + 1);
        int[] iArr = this.f25544a;
        int i13 = this.f25545b;
        iArr[i13] = i12;
        this.f25545b = i13 + 1;
    }

    public final void e(int i12) {
        int[] iArr = this.f25544a;
        if (iArr.length < i12) {
            this.f25544a = Arrays.copyOf(iArr, Math.max(i12, (iArr.length * 3) / 2));
        }
    }

    public final int f(@j.F int i12) {
        int i13;
        if (i12 < 0 || i12 >= (i13 = this.f25545b)) {
            K.f.b("Index must be between 0 and size");
            throw null;
        }
        int[] iArr = this.f25544a;
        int i14 = iArr[i12];
        if (i12 != i13 - 1) {
            C42756l.k(i12, i12 + 1, i13, iArr, iArr);
        }
        this.f25545b--;
        return i14;
    }

    public final void g(@j.F int i12, int i13) {
        if (i12 < 0 || i12 >= this.f25545b) {
            K.f.b("Index must be between 0 and size");
            throw null;
        }
        int[] iArr = this.f25544a;
        int i14 = iArr[i12];
        iArr[i12] = i13;
    }

    public C20247y0(int i12, int i13, C42822w c42822w) {
        super((i13 & 1) != 0 ? 16 : i12, null);
    }
}
