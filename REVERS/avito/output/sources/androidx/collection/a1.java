package androidx.collection;

import java.util.List;
import kotlin.Metadata;

/* compiled from: ObjectList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class a1 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final Object[] f25678a = new Object[0];

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final N0 f25679b = new N0(0, null);

    public static final void a(int i12, List list) {
        int size = list.size();
        if (i12 < 0 || i12 >= size) {
            K.f.b("Index " + i12 + " is out of bounds. The list has " + size + " elements.");
            throw null;
        }
    }

    public static final void b(int i12, int i13, List list) {
        int size = list.size();
        if (i12 > i13) {
            K.f.a("Indices are out of order. fromIndex (" + i12 + ") is greater than toIndex (" + i13 + ").");
            throw null;
        }
        if (i12 < 0) {
            K.f.b("fromIndex (" + i12 + ") is less than 0.");
            throw null;
        }
        if (i13 <= size) {
            return;
        }
        K.f.b("toIndex (" + i13 + ") is more than than the list size (" + size + ')');
        throw null;
    }
}
