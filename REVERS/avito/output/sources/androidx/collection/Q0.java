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
import kotlin.sequences.AbstractC43032o;
import kotlin.sequences.C43033p;

/* compiled from: OrderedScatterSet.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/Q0;", "E", "Landroidx/collection/f1;", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class Q0<E> extends f1<E> implements Set<E>, Z41.h {

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final P0<E> f25594c;

    /* compiled from: OrderedScatterSet.kt */
    @Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0010)\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001¨\u0006\u0002"}, d2 = {"androidx/collection/Q0$a", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a implements Iterator<E>, Z41.d {

        /* renamed from: b, reason: collision with root package name */
        public int f25595b = -1;

        /* renamed from: c, reason: collision with root package name */
        public final Iterator<E> f25596c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ Q0<E> f25597d;

        /* compiled from: OrderedScatterSet.kt */
        @kotlin.jvm.internal.s0
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"E", "Lkotlin/sequences/o;", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
        @DebugMetadata(c = "androidx.collection.MutableOrderedSetWrapper$iterator$1$iterator$1", f = "OrderedScatterSet.kt", i = {0, 0, 0}, l = {1489}, m = "invokeSuspend", n = {"$this$iterator", "nodes$iv", "previousNode$iv"}, s = {"L$0", "L$3", "I$0"})
        /* renamed from: androidx.collection.Q0$a$a, reason: collision with other inner class name */
        public static final class C1542a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super E>, Continuation<? super kotlin.G0>, Object> {

            /* renamed from: q, reason: collision with root package name */
            public a f25598q;

            /* renamed from: r, reason: collision with root package name */
            public Object f25599r;

            /* renamed from: s, reason: collision with root package name */
            public long[] f25600s;

            /* renamed from: t, reason: collision with root package name */
            public int f25601t;

            /* renamed from: u, reason: collision with root package name */
            public int f25602u;

            /* renamed from: v, reason: collision with root package name */
            public /* synthetic */ Object f25603v;

            /* renamed from: w, reason: collision with root package name */
            public final /* synthetic */ Q0<E> f25604w;

            /* renamed from: x, reason: collision with root package name */
            public final /* synthetic */ a f25605x;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C1542a(Q0<E> q02, a aVar, Continuation<? super C1542a> continuation) {
                super(2, continuation);
                this.f25604w = q02;
                this.f25605x = aVar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
                C1542a c1542a = new C1542a(this.f25604w, this.f25605x, continuation);
                c1542a.f25603v = obj;
                return c1542a;
            }

            @Override // Y41.p
            public final Object invoke(Object obj, Continuation<? super kotlin.G0> continuation) {
                return ((C1542a) create((AbstractC43032o) obj, continuation)).invokeSuspend(kotlin.G0.f406611a);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Q0<E> q02;
                long[] jArr;
                int i12;
                a aVar;
                AbstractC43032o abstractC43032o;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i13 = this.f25602u;
                if (i13 == 0) {
                    C42729a0.b(obj);
                    AbstractC43032o abstractC43032o2 = (AbstractC43032o) this.f25603v;
                    q02 = this.f25604w;
                    P0<E> p02 = q02.f25594c;
                    jArr = p02.f25695c;
                    i12 = p02.f25697e;
                    aVar = this.f25605x;
                    abstractC43032o = abstractC43032o2;
                } else {
                    if (i13 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    int i14 = this.f25601t;
                    long[] jArr2 = this.f25600s;
                    Q0<E> q03 = (Q0) this.f25599r;
                    aVar = this.f25598q;
                    abstractC43032o = (AbstractC43032o) this.f25603v;
                    C42729a0.b(obj);
                    i12 = i14;
                    q02 = q03;
                    jArr = jArr2;
                }
                while (i12 != Integer.MAX_VALUE) {
                    int i15 = (int) ((jArr[i12] >> 31) & 2147483647L);
                    aVar.f25595b = i12;
                    Object obj2 = q02.f25594c.f25694b[i12];
                    this.f25603v = abstractC43032o;
                    this.f25598q = aVar;
                    this.f25599r = q02;
                    this.f25600s = jArr;
                    this.f25601t = i15;
                    this.f25602u = 1;
                    if (abstractC43032o.a(obj2, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    i12 = i15;
                }
                return kotlin.G0.f406611a;
            }
        }

        public a(Q0<E> q02) {
            this.f25597d = q02;
            this.f25596c = C43033p.v(new C1542a(q02, this, null));
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.f25596c.hasNext();
        }

        @Override // java.util.Iterator
        public final E next() {
            return this.f25596c.next();
        }

        @Override // java.util.Iterator
        public final void remove() {
            int i12 = this.f25595b;
            if (i12 != -1) {
                this.f25597d.f25594c.i(i12);
                this.f25595b = -1;
            }
        }
    }

    public Q0(@Y61.k P0<E> p02) {
        super(p02);
        this.f25594c = p02;
    }

    @Override // androidx.collection.f1, java.util.Set, java.util.Collection
    public final boolean add(E e12) {
        return this.f25594c.b(e12);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.collection.f1, java.util.Set, java.util.Collection
    public final boolean addAll(@Y61.k Collection<? extends E> collection) {
        P0<E> p02 = this.f25594c;
        int i12 = p02.f25699g;
        for (Object obj : collection) {
            int iE2 = p02.e(obj);
            p02.f25694b[iE2] = obj;
            long[] jArr = p02.f25695c;
            int i13 = p02.f25696d;
            jArr[iE2] = (i13 & 2147483647L) | 4611686016279904256L;
            if (i13 != Integer.MAX_VALUE) {
                jArr[i13] = ((iE2 & 2147483647L) << 31) | (jArr[i13] & (-4611686016279904257L));
            }
            p02.f25696d = iE2;
            if (p02.f25697e == Integer.MAX_VALUE) {
                p02.f25697e = iE2;
            }
        }
        return i12 != p02.f25699g;
    }

    @Override // androidx.collection.f1, java.util.Set, java.util.Collection
    public final void clear() {
        this.f25594c.d();
    }

    @Override // androidx.collection.f1, java.util.Set, java.util.Collection, java.lang.Iterable
    @Y61.k
    public final Iterator<E> iterator() {
        return new a(this);
    }

    @Override // androidx.collection.f1, java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        return this.f25594c.h(obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x008c, code lost:
    
        if (((r5 & ((~r5) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008e, code lost:
    
        r14 = -1;
     */
    @Override // androidx.collection.f1, java.util.Set, java.util.Collection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean removeAll(@Y61.k java.util.Collection<? extends java.lang.Object> r19) {
        /*
            r18 = this;
            r0 = r19
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r1 = r18
            androidx.collection.P0<E> r2 = r1.f25594c
            int r3 = r2.f25699g
            java.util.Iterator r0 = r0.iterator()
        Le:
            boolean r4 = r0.hasNext()
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L9c
            java.lang.Object r4 = r0.next()
            if (r4 == 0) goto L21
            int r7 = r4.hashCode()
            goto L22
        L21:
            r7 = r6
        L22:
            r8 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r7 = r7 * r8
            int r8 = r7 << 16
            r7 = r7 ^ r8
            r8 = r7 & 127(0x7f, float:1.78E-43)
            int r9 = r2.f25698f
            int r7 = r7 >>> 7
            r7 = r7 & r9
        L30:
            long[] r10 = r2.f25693a
            int r11 = r7 >> 3
            r12 = r7 & 7
            int r12 = r12 << 3
            r13 = r10[r11]
            long r13 = r13 >>> r12
            int r11 = r11 + r5
            r15 = r10[r11]
            int r10 = 64 - r12
            long r10 = r15 << r10
            r15 = r6
            long r5 = (long) r12
            long r5 = -r5
            r12 = 63
            long r5 = r5 >> r12
            long r5 = r5 & r10
            long r5 = r5 | r13
            long r10 = (long) r8
            r12 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r10 = r10 * r12
            long r10 = r10 ^ r5
            long r12 = r10 - r12
            long r10 = ~r10
            long r10 = r10 & r12
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
        L5c:
            r16 = 0
            int r14 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1))
            if (r14 == 0) goto L80
            int r14 = java.lang.Long.numberOfTrailingZeros(r10)
            int r14 = r14 >> 3
            int r14 = r14 + r7
            r14 = r14 & r9
            java.lang.Object[] r12 = r2.f25694b
            r12 = r12[r14]
            boolean r12 = kotlin.jvm.internal.L.f(r12, r4)
            if (r12 == 0) goto L75
            goto L8f
        L75:
            r12 = 1
            long r12 = r10 - r12
            long r10 = r10 & r12
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            goto L5c
        L80:
            long r10 = ~r5
            r12 = 6
            long r10 = r10 << r12
            long r5 = r5 & r10
            r10 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r5 = r5 & r10
            int r5 = (r5 > r16 ? 1 : (r5 == r16 ? 0 : -1))
            if (r5 == 0) goto L96
            r14 = -1
        L8f:
            if (r14 < 0) goto Le
            r2.i(r14)
            goto Le
        L96:
            int r6 = r15 + 8
            int r7 = r7 + r6
            r7 = r7 & r9
            r5 = 1
            goto L30
        L9c:
            int r0 = r2.f25699g
            if (r3 == r0) goto La2
            r5 = 1
            goto La3
        La2:
            r5 = r6
        La3:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.Q0.removeAll(java.util.Collection):boolean");
    }

    @Override // androidx.collection.f1, java.util.Set, java.util.Collection
    public final boolean retainAll(@Y61.k Collection<? extends Object> collection) {
        return this.f25594c.j(collection);
    }
}
