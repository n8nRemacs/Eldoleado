package androidx.collection;

import kotlin.Metadata;

/* compiled from: ScatterMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\b\n\u0002\u0010\t\n\u0002\b\u0004*\f\b\u0000\u0010\u0001\"\u00020\u00002\u00020\u0000*\f\b\u0000\u0010\u0002\"\u00020\u00002\u00020\u0000*\f\b\u0000\u0010\u0003\"\u00020\u00002\u00020\u0000¨\u0006\u0004"}, d2 = {"", "Bitmask", "Group", "StaticBitmask", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class i1 {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public static final long[] f25727a = {-9187201950435737345L, -1};

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final R0 f25728b = new R0(0);

    public static final int a(int i12) {
        if (i12 == 7) {
            return 6;
        }
        return i12 - (i12 / 8);
    }

    @Y61.k
    public static final <K, V> R0<K, V> b() {
        return new R0<>(0, 1, null);
    }

    public static final int c(int i12) {
        if (i12 == 0) {
            return 6;
        }
        return (i12 * 2) + 1;
    }

    public static final int d(int i12) {
        if (i12 > 0) {
            return (-1) >>> Integer.numberOfLeadingZeros(i12);
        }
        return 0;
    }

    public static final int e(int i12) {
        if (i12 == 7) {
            return 8;
        }
        return androidx.appcompat.app.r.f(i12, 1, 7, i12);
    }
}
