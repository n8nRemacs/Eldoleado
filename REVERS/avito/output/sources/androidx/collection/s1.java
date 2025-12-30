package androidx.collection;

import kotlin.Metadata;

/* compiled from: SparseArray.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/s1;", "Lkotlin/collections/K0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class s1 extends kotlin.collections.K0 {

    /* renamed from: b, reason: collision with root package name */
    public int f25806b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ q1<Object> f25807c;

    public s1(q1<Object> q1Var) {
        this.f25807c = q1Var;
    }

    @Override // kotlin.collections.K0
    public final int a() {
        int i12 = this.f25806b;
        this.f25806b = i12 + 1;
        return this.f25807c.g(i12);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f25806b < this.f25807c.j();
    }
}
