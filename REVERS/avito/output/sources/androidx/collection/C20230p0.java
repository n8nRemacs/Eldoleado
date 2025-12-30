package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.C42729a0;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.internal.C42821v;
import kotlin.sequences.AbstractC43032o;
import kotlin.sequences.C43033p;

/* compiled from: ScatterMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0003¨\u0006\u0005"}, d2 = {"Landroidx/collection/p0;", "K", "V", "", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.p0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20230p0<K, V> implements Set<Map.Entry<K, V>>, Z41.h {

    /* compiled from: ScatterMap.kt */
    @Metadata(d1 = {"\u0000\u000f\n\u0000\n\u0002\u0010)\n\u0002\u0010'\n\u0000*\u0001\u0000\b\n\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00020\u0001¨\u0006\u0003"}, d2 = {"androidx/collection/p0$a", "", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.collection.p0$a */
    public static final class a implements Iterator<Map.Entry<K, V>>, Z41.d {

        /* renamed from: b, reason: collision with root package name */
        public final Iterator<? extends Map.Entry<K, V>> f25795b;

        /* renamed from: c, reason: collision with root package name */
        public int f25796c = -1;

        /* compiled from: ScatterMap.kt */
        @kotlin.jvm.internal.s0
        @Metadata(d1 = {"\u0000\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010'\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00030\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"K", "V", "Lkotlin/sequences/o;", "", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
        @DebugMetadata(c = "androidx.collection.MutableEntries$iterator$1$1", f = "ScatterMap.kt", i = {0, 0, 0, 0, 0, 0, 0}, l = {1538}, m = "invokeSuspend", n = {"$this$iterator", "m$iv", "lastIndex$iv", "i$iv", "slot$iv", "bitCount$iv", "j$iv"}, s = {"L$0", "L$3", "I$0", "I$1", "J$0", "I$2", "I$3"})
        /* renamed from: androidx.collection.p0$a$a, reason: collision with other inner class name */
        public static final class C1546a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super Map.Entry<K, V>>, Continuation<? super kotlin.G0>, Object> {

            /* renamed from: q, reason: collision with root package name */
            public /* synthetic */ Object f25797q;

            /* renamed from: r, reason: collision with root package name */
            public final /* synthetic */ C20230p0<K, V> f25798r;

            /* renamed from: s, reason: collision with root package name */
            public final /* synthetic */ a f25799s;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C1546a(C20230p0<K, V> c20230p0, a aVar, Continuation<? super C1546a> continuation) {
                super(2, continuation);
                this.f25798r = c20230p0;
                this.f25799s = aVar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
                C1546a c1546a = new C1546a(this.f25798r, this.f25799s, continuation);
                c1546a.f25797q = obj;
                return c1546a;
            }

            @Override // Y41.p
            public final Object invoke(Object obj, Continuation<? super kotlin.G0> continuation) {
                return ((C1546a) create((AbstractC43032o) obj, continuation)).invokeSuspend(kotlin.G0.f406611a);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                C42729a0.b(obj);
                throw null;
            }
        }

        public a(C20230p0<K, V> c20230p0) {
            this.f25795b = C43033p.v(new C1546a(c20230p0, this, null));
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.f25795b.hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            return this.f25795b.next();
        }

        @Override // java.util.Iterator
        public final void remove() {
            if (this.f25796c != -1) {
                throw null;
            }
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(@Y61.k Collection<? extends Map.Entry<K, V>> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        if (!kotlin.jvm.internal.v0.h(obj)) {
            return false;
        }
        ((Map.Entry) obj).getKey();
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
        Collection<? extends Object> collection2 = collection;
        if (collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        if (!it.hasNext()) {
            return true;
        }
        ((Map.Entry) it.next()).getKey();
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        throw null;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    @Y61.k
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new a(this);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        if (!kotlin.jvm.internal.v0.h(obj)) {
            return false;
        }
        Object key = ((Map.Entry) obj).getKey();
        if (key != null) {
            key.hashCode();
        }
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(@Y61.k Collection<? extends Object> collection) {
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(@Y61.k Collection<? extends Object> collection) {
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        return C42821v.a(this);
    }

    @Override // java.util.Set, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        return (T[]) C42821v.b(this, tArr);
    }
}
