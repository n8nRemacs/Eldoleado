package androidx.collection;

import kotlin.Metadata;

/* compiled from: IntIntPair.kt */
@X41.g
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\b\u0087@\u0018\u00002\u00020\u0001\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/E;", "", "packedValue", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class E {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    public final long f25542a;

    private /* synthetic */ E(long j12) {
        this.f25542a = j12;
    }

    public static final /* synthetic */ E a(long j12) {
        return new E(j12);
    }

    public static long b(int i12, int i13) {
        return (i13 & 4294967295L) | (i12 << 32);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof E) {
            return this.f25542a == ((E) obj).f25542a;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.f25542a);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("(");
        long j12 = this.f25542a;
        sb2.append((int) (j12 >> 32));
        sb2.append(", ");
        return androidx.appcompat.app.r.t(sb2, (int) (j12 & 4294967295L), ')');
    }
}
