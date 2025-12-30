package androidx.collection;

import kotlin.Metadata;

/* compiled from: FloatFloatPair.kt */
@X41.g
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\b\u0087@\u0018\u00002\u00020\u0001\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/o;", "", "packedValue", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20227o {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    public final long f25787a;

    public final boolean equals(Object obj) {
        if (obj instanceof C20227o) {
            return this.f25787a == ((C20227o) obj).f25787a;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.f25787a);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("(");
        long j12 = this.f25787a;
        sb2.append(Float.intBitsToFloat((int) (j12 >> 32)));
        sb2.append(", ");
        sb2.append(Float.intBitsToFloat((int) (j12 & 4294967295L)));
        sb2.append(')');
        return sb2.toString();
    }
}
