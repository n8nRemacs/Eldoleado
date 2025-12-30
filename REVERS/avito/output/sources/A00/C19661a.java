package a00;

import Y61.k;
import Y61.l;
import com.avito.android.mortgage.document_requirements.model.Document;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.L;
import kotlin.jvm.internal.s0;

/* compiled from: Document.kt */
@s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"_avito_mortgage_impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: a00.a, reason: case insensitive filesystem */
/* loaded from: classes15.dex */
public final class C19661a {
    @l
    public static final Document a(@k String str) {
        Object next;
        Iterator<E> it = Document.f198968f.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (L.f(((Document) next).f198969b, str)) {
                break;
            }
        }
        return (Document) next;
    }
}
