package androidx.collection;

import java.util.Iterator;
import kotlin.Metadata;

/* compiled from: SparseArray.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/t1;", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class t1 implements Iterator<Object>, Z41.a {

    /* renamed from: b, reason: collision with root package name */
    public int f25812b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ q1<Object> f25813c;

    public t1(q1<Object> q1Var) {
        this.f25813c = q1Var;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f25812b < this.f25813c.j();
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i12 = this.f25812b;
        this.f25812b = i12 + 1;
        return this.f25813c.k(i12);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
