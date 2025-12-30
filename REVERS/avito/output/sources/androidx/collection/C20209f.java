package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: CircularArray.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/f;", "E", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20209f<E> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final E[] f25702a;

    @X41.j
    public C20209f() {
        this(0, 1, null);
    }

    public C20209f(int i12, int i13, C42822w c42822w) {
        i12 = (i13 & 1) != 0 ? 8 : i12;
        if (!(i12 >= 1)) {
            K.f.a("capacity must be >= 1");
            throw null;
        }
        if (i12 <= 1073741824) {
            this.f25702a = (E[]) new Object[Integer.bitCount(i12) != 1 ? Integer.highestOneBit(i12 - 1) << 1 : i12];
        } else {
            K.f.a("capacity must be <= 2^30");
            throw null;
        }
    }
}
