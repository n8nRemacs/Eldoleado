package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.internal.C42821v;
import kotlin.sequences.AbstractC43032o;
import kotlin.sequences.C43033p;

/* compiled from: ScatterMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/C0;", "K", "V", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class C0<K, V> implements Set<K>, Z41.h {

    /* compiled from: ScatterMap.kt */
    @Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0010)\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001¨\u0006\u0002"}, d2 = {"androidx/collection/C0$a", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a implements Iterator<K>, Z41.d {

        /* renamed from: b, reason: collision with root package name */
        public final Iterator<Integer> f25531b;

        /* renamed from: c, reason: collision with root package name */
        public int f25532c = -1;

        /* compiled from: ScatterMap.kt */
        @kotlin.jvm.internal.s0
        @Metadata(d1 = {"\u0000\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"K", "V", "Lkotlin/sequences/o;", "", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
        @DebugMetadata(c = "androidx.collection.MutableKeys$iterator$1$iterator$1", f = "ScatterMap.kt", i = {0, 0, 0, 0, 0, 0, 0}, l = {1622}, m = "invokeSuspend", n = {"$this$iterator", "m$iv", "lastIndex$iv", "i$iv", "slot$iv", "bitCount$iv", "j$iv"}, s = {"L$0", "L$1", "I$0", "I$1", "J$0", "I$2", "I$3"})
        /* renamed from: androidx.collection.C0$a$a, reason: collision with other inner class name */
        public static final class C1541a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super Integer>, Continuation<? super kotlin.G0>, Object> {

            /* renamed from: q, reason: collision with root package name */
            public long[] f25533q;

            /* renamed from: r, reason: collision with root package name */
            public int f25534r;

            /* renamed from: s, reason: collision with root package name */
            public int f25535s;

            /* renamed from: t, reason: collision with root package name */
            public int f25536t;

            /* renamed from: u, reason: collision with root package name */
            public int f25537u;

            /* renamed from: v, reason: collision with root package name */
            public long f25538v;

            /* renamed from: w, reason: collision with root package name */
            public int f25539w;

            /* renamed from: x, reason: collision with root package name */
            public /* synthetic */ Object f25540x;

            /* renamed from: y, reason: collision with root package name */
            public final /* synthetic */ C0<K, V> f25541y;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C1541a(C0<K, V> c02, Continuation<? super C1541a> continuation) {
                super(2, continuation);
                this.f25541y = c02;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
                C1541a c1541a = new C1541a(this.f25541y, continuation);
                c1541a.f25540x = obj;
                return c1541a;
            }

            @Override // Y41.p
            public final Object invoke(AbstractC43032o<? super Integer> abstractC43032o, Continuation<? super kotlin.G0> continuation) {
                return ((C1541a) create(abstractC43032o, continuation)).invokeSuspend(kotlin.G0.f406611a);
            }

            /* JADX WARN: Code restructure failed: missing block: B:18:0x0061, code lost:
            
                r3 = 8 - ((~(r6 - r7)) >>> 31);
                r1 = 0;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r15) {
                /*
                    r14 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r1 = r14.f25539w
                    if (r1 == 0) goto L75
                    r2 = 1
                    if (r1 != r2) goto L6d
                    int r1 = r14.f25537u
                    int r3 = r14.f25536t
                    long r4 = r14.f25538v
                    int r6 = r14.f25535s
                    int r7 = r14.f25534r
                    long[] r8 = r14.f25533q
                    java.lang.Object r9 = r14.f25540x
                    kotlin.sequences.o r9 = (kotlin.sequences.AbstractC43032o) r9
                    kotlin.C42729a0.b(r15)
                L1e:
                    r15 = 8
                    long r4 = r4 >> r15
                    int r1 = r1 + r2
                L22:
                    if (r1 >= r3) goto L4b
                    r10 = 255(0xff, double:1.26E-321)
                    long r10 = r10 & r4
                    r12 = 128(0x80, double:6.3E-322)
                    int r15 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                    if (r15 >= 0) goto L1e
                    int r15 = r6 << 3
                    int r15 = r15 + r1
                    java.lang.Integer r15 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r15)
                    r14.f25540x = r9
                    r14.f25533q = r8
                    r14.f25534r = r7
                    r14.f25535s = r6
                    r14.f25538v = r4
                    r14.f25536t = r3
                    r14.f25537u = r1
                    r14.f25539w = r2
                    java.lang.Object r15 = r9.a(r15, r14)
                    if (r15 != r0) goto L1e
                    return r0
                L4b:
                    if (r3 != r15) goto L6a
                L4d:
                    if (r6 == r7) goto L6a
                    int r6 = r6 + 1
                    r4 = r8[r6]
                    long r10 = ~r4
                    r1 = 7
                    long r10 = r10 << r1
                    long r10 = r10 & r4
                    r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
                    long r10 = r10 & r12
                    int r1 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                    if (r1 == 0) goto L4d
                    int r1 = r6 - r7
                    int r1 = ~r1
                    int r1 = r1 >>> 31
                    int r3 = 8 - r1
                    r1 = 0
                    goto L22
                L6a:
                    kotlin.G0 r15 = kotlin.G0.f406611a
                    return r15
                L6d:
                    java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r15.<init>(r0)
                    throw r15
                L75:
                    kotlin.C42729a0.b(r15)
                    java.lang.Object r15 = r14.f25540x
                    kotlin.sequences.o r15 = (kotlin.sequences.AbstractC43032o) r15
                    r15 = 0
                    throw r15
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.collection.C0.a.C1541a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        public a(C0<K, V> c02) {
            this.f25531b = C43033p.v(new C1541a(c02, null));
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.f25531b.hasNext();
        }

        @Override // java.util.Iterator
        public final K next() {
            this.f25532c = this.f25531b.next().intValue();
            throw null;
        }

        @Override // java.util.Iterator
        public final void remove() {
            if (this.f25532c >= 0) {
                throw null;
            }
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(K k12) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(@Y61.k Collection<? extends K> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
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
        it.next();
        throw null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        throw null;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    @Y61.k
    public final Iterator<K> iterator() {
        return new a(this);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        if (obj != null) {
            obj.hashCode();
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
