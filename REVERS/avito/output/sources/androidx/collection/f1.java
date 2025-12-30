package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
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

/* compiled from: OrderedScatterSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/f1;", "E", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
class f1<E> implements Set<E>, Z41.a {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final P0 f25704b;

    /* compiled from: OrderedScatterSet.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"E", "Lkotlin/sequences/o;", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.collection.OrderedSetWrapper$iterator$1", f = "OrderedScatterSet.kt", i = {0, 0, 0, 0}, l = {1454}, m = "invokeSuspend", n = {"$this$iterator", "elements$iv", "nodes$iv", "previousNode$iv"}, s = {"L$0", "L$1", "L$2", "I$0"})
    public static final class a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super E>, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public Object[] f25705q;

        /* renamed from: r, reason: collision with root package name */
        public long[] f25706r;

        /* renamed from: s, reason: collision with root package name */
        public int f25707s;

        /* renamed from: t, reason: collision with root package name */
        public int f25708t;

        /* renamed from: u, reason: collision with root package name */
        public /* synthetic */ Object f25709u;

        /* renamed from: v, reason: collision with root package name */
        public final /* synthetic */ f1<E> f25710v;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(f1<E> f1Var, Continuation<? super a> continuation) {
            super(2, continuation);
            this.f25710v = f1Var;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            a aVar = new a(this.f25710v, continuation);
            aVar.f25709u = obj;
            return aVar;
        }

        @Override // Y41.p
        public final Object invoke(Object obj, Continuation<? super kotlin.G0> continuation) {
            return ((a) create((AbstractC43032o) obj, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int i12;
            AbstractC43032o abstractC43032o;
            Object[] objArr;
            long[] jArr;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i13 = this.f25708t;
            if (i13 == 0) {
                C42729a0.b(obj);
                AbstractC43032o abstractC43032o2 = (AbstractC43032o) this.f25709u;
                P0 p02 = this.f25710v.f25704b;
                Object[] objArr2 = p02.f25694b;
                long[] jArr2 = p02.f25695c;
                i12 = p02.f25697e;
                abstractC43032o = abstractC43032o2;
                objArr = objArr2;
                jArr = jArr2;
            } else {
                if (i13 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i12 = this.f25707s;
                jArr = this.f25706r;
                objArr = this.f25705q;
                abstractC43032o = (AbstractC43032o) this.f25709u;
                C42729a0.b(obj);
            }
            while (i12 != Integer.MAX_VALUE) {
                int i14 = (int) ((jArr[i12] >> 31) & 2147483647L);
                Object obj2 = objArr[i12];
                this.f25709u = abstractC43032o;
                this.f25705q = objArr;
                this.f25706r = jArr;
                this.f25707s = i14;
                this.f25708t = 1;
                if (abstractC43032o.a(obj2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                i12 = i14;
            }
            return kotlin.G0.f406611a;
        }
    }

    public f1(@Y61.k P0 p02) {
        this.f25704b = p02;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(E e12) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        return this.f25704b.a(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!this.f25704b.a(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return kotlin.jvm.internal.L.f(this.f25704b, ((f1) obj).f25704b);
    }

    @Override // java.util.Set, java.util.Collection
    public final int hashCode() {
        return this.f25704b.hashCode();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return this.f25704b.f25699g == 0;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    @Y61.k
    public Iterator<E> iterator() {
        return C43033p.v(new a(this, null));
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        return this.f25704b.f25699g;
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        return C42821v.a(this);
    }

    @Y61.k
    public final String toString() {
        return this.f25704b.toString();
    }

    @Override // java.util.Set, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        return (T[]) C42821v.b(this, tArr);
    }
}
