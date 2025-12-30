package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/F0;", "Landroidx/collection/T;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class F0 extends T {
    public F0() {
        this(0, 1, null);
    }

    public final void a(long j12) {
        int i12 = this.f25610b + 1;
        long[] jArr = this.f25609a;
        if (jArr.length < i12) {
            this.f25609a = Arrays.copyOf(jArr, Math.max(i12, (jArr.length * 3) / 2));
        }
        long[] jArr2 = this.f25609a;
        int i13 = this.f25610b;
        jArr2[i13] = j12;
        this.f25610b = i13 + 1;
    }

    public F0(int i12, int i13, C42822w c42822w) {
        super((i13 & 1) != 0 ? 16 : i12, null);
    }
}
