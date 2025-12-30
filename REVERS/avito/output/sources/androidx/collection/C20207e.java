package androidx.collection;

import java.util.ConcurrentModificationException;
import kotlin.Metadata;

/* compiled from: ArraySet.kt */
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20207e {
    public static final <E> int a(@Y61.k C20203c<E> c20203c, @Y61.l Object obj, int i12) {
        int i13 = c20203c.f25687d;
        if (i13 == 0) {
            return -1;
        }
        try {
            int iA2 = K.a.a(i13, i12, c20203c.f25685b);
            if (iA2 < 0 || kotlin.jvm.internal.L.f(obj, c20203c.f25686c[iA2])) {
                return iA2;
            }
            int i14 = iA2 + 1;
            while (i14 < i13 && c20203c.f25685b[i14] == i12) {
                if (kotlin.jvm.internal.L.f(obj, c20203c.f25686c[i14])) {
                    return i14;
                }
                i14++;
            }
            for (int i15 = iA2 - 1; i15 >= 0 && c20203c.f25685b[i15] == i12; i15--) {
                if (kotlin.jvm.internal.L.f(obj, c20203c.f25686c[i15])) {
                    return i15;
                }
            }
            return ~i14;
        } catch (IndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }
}
