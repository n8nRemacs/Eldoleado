package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.sequences.AbstractC43032o;
import kotlin.sequences.C43033p;

/* compiled from: ScatterSet.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/T0;", "E", "Landroidx/collection/l1;", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class T0<E> extends l1<E> implements Set<E>, Z41.h {

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final S0<E> f25611c;

    /* compiled from: ScatterSet.kt */
    @Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0010)\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001¨\u0006\u0002"}, d2 = {"androidx/collection/T0$a", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a implements Iterator<E>, Z41.d {

        /* renamed from: b, reason: collision with root package name */
        public int f25612b = -1;

        /* renamed from: c, reason: collision with root package name */
        public final Iterator<E> f25613c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ T0<E> f25614d;

        /* compiled from: ScatterSet.kt */
        @kotlin.jvm.internal.s0
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"E", "Lkotlin/sequences/o;", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
        @DebugMetadata(c = "androidx.collection.MutableSetWrapper$iterator$1$iterator$1", f = "ScatterSet.kt", i = {0, 0, 0, 0, 0, 0, 0}, l = {1188}, m = "invokeSuspend", n = {"$this$iterator", "m$iv", "lastIndex$iv", "i$iv", "slot$iv", "bitCount$iv", "j$iv"}, s = {"L$0", "L$3", "I$0", "I$1", "J$0", "I$2", "I$3"})
        /* renamed from: androidx.collection.T0$a$a, reason: collision with other inner class name */
        public static final class C1543a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super E>, Continuation<? super kotlin.G0>, Object> {

            /* renamed from: A, reason: collision with root package name */
            public final /* synthetic */ T0<E> f25615A;

            /* renamed from: B, reason: collision with root package name */
            public final /* synthetic */ a f25616B;

            /* renamed from: q, reason: collision with root package name */
            public a f25617q;

            /* renamed from: r, reason: collision with root package name */
            public Object f25618r;

            /* renamed from: s, reason: collision with root package name */
            public long[] f25619s;

            /* renamed from: t, reason: collision with root package name */
            public int f25620t;

            /* renamed from: u, reason: collision with root package name */
            public int f25621u;

            /* renamed from: v, reason: collision with root package name */
            public int f25622v;

            /* renamed from: w, reason: collision with root package name */
            public int f25623w;

            /* renamed from: x, reason: collision with root package name */
            public long f25624x;

            /* renamed from: y, reason: collision with root package name */
            public int f25625y;

            /* renamed from: z, reason: collision with root package name */
            public /* synthetic */ Object f25626z;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C1543a(T0<E> t02, a aVar, Continuation<? super C1543a> continuation) {
                super(2, continuation);
                this.f25615A = t02;
                this.f25616B = aVar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
                C1543a c1543a = new C1543a(this.f25615A, this.f25616B, continuation);
                c1543a.f25626z = obj;
                return c1543a;
            }

            @Override // Y41.p
            public final Object invoke(Object obj, Continuation<? super kotlin.G0> continuation) {
                return ((C1543a) create((AbstractC43032o) obj, continuation)).invokeSuspend(kotlin.G0.f406611a);
            }

            /* JADX WARN: Removed duplicated region for block: B:13:0x0057  */
            /* JADX WARN: Removed duplicated region for block: B:15:0x006c  */
            /* JADX WARN: Removed duplicated region for block: B:21:0x009e  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x00a8  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0055 -> B:23:0x00a6). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0057 -> B:14:0x006a). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0073 -> B:20:0x009b). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0098 -> B:20:0x009b). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r22) {
                /*
                    r21 = this;
                    r0 = r21
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r0.f25625y
                    r3 = 1
                    r5 = 8
                    if (r2 == 0) goto L32
                    if (r2 != r3) goto L2a
                    int r2 = r0.f25623w
                    int r6 = r0.f25622v
                    long r7 = r0.f25624x
                    int r9 = r0.f25621u
                    int r10 = r0.f25620t
                    long[] r11 = r0.f25619s
                    java.lang.Object r12 = r0.f25618r
                    androidx.collection.T0 r12 = (androidx.collection.T0) r12
                    androidx.collection.T0$a r13 = r0.f25617q
                    java.lang.Object r14 = r0.f25626z
                    kotlin.sequences.o r14 = (kotlin.sequences.AbstractC43032o) r14
                    kotlin.C42729a0.b(r22)
                    goto L9b
                L2a:
                    java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                    java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                    r1.<init>(r2)
                    throw r1
                L32:
                    kotlin.C42729a0.b(r22)
                    java.lang.Object r2 = r0.f25626z
                    kotlin.sequences.o r2 = (kotlin.sequences.AbstractC43032o) r2
                    androidx.collection.T0<E> r6 = r0.f25615A
                    androidx.collection.S0<E> r7 = r6.f25611c
                    long[] r7 = r7.f25729a
                    int r8 = r7.length
                    int r8 = r8 + (-2)
                    if (r8 < 0) goto Lab
                    androidx.collection.T0$a r9 = r0.f25616B
                    r10 = 0
                L47:
                    r11 = r7[r10]
                    long r13 = ~r11
                    r15 = 7
                    long r13 = r13 << r15
                    long r13 = r13 & r11
                    r15 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
                    long r13 = r13 & r15
                    int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
                    if (r13 == 0) goto La6
                    int r13 = r10 - r8
                    int r13 = ~r13
                    int r13 = r13 >>> 31
                    int r13 = 8 - r13
                    r14 = r2
                    r2 = 0
                    r19 = r11
                    r12 = r6
                    r11 = r7
                    r6 = r13
                    r13 = r9
                    r9 = r10
                    r10 = r8
                    r7 = r19
                L6a:
                    if (r2 >= r6) goto L9e
                    r15 = 255(0xff, double:1.26E-321)
                    long r15 = r15 & r7
                    r17 = 128(0x80, double:6.3E-322)
                    int r15 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
                    if (r15 >= 0) goto L9b
                    int r15 = r9 << 3
                    int r15 = r15 + r2
                    r13.f25612b = r15
                    androidx.collection.S0<E> r4 = r12.f25611c
                    java.lang.Object[] r4 = r4.f25730b
                    r4 = r4[r15]
                    r0.f25626z = r14
                    r0.f25617q = r13
                    r0.f25618r = r12
                    r0.f25619s = r11
                    r0.f25620t = r10
                    r0.f25621u = r9
                    r0.f25624x = r7
                    r0.f25622v = r6
                    r0.f25623w = r2
                    r0.f25625y = r3
                    java.lang.Object r4 = r14.a(r4, r0)
                    if (r4 != r1) goto L9b
                    return r1
                L9b:
                    long r7 = r7 >> r5
                    int r2 = r2 + r3
                    goto L6a
                L9e:
                    if (r6 != r5) goto Lab
                    r8 = r10
                    r7 = r11
                    r6 = r12
                    r2 = r14
                    r10 = r9
                    r9 = r13
                La6:
                    if (r10 == r8) goto Lab
                    int r10 = r10 + 1
                    goto L47
                Lab:
                    kotlin.G0 r1 = kotlin.G0.f406611a
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.collection.T0.a.C1543a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        public a(T0<E> t02) {
            this.f25614d = t02;
            this.f25613c = C43033p.v(new C1543a(t02, this, null));
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.f25613c.hasNext();
        }

        @Override // java.util.Iterator
        public final E next() {
            return this.f25613c.next();
        }

        @Override // java.util.Iterator
        public final void remove() {
            int i12 = this.f25612b;
            if (i12 != -1) {
                this.f25614d.f25611c.o(i12);
                this.f25612b = -1;
            }
        }
    }

    public T0(@Y61.k S0<E> s02) {
        super(s02);
        this.f25611c = s02;
    }

    @Override // androidx.collection.l1, java.util.Set, java.util.Collection
    public final boolean add(E e12) {
        return this.f25611c.e(e12);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.collection.l1, java.util.Set, java.util.Collection
    public final boolean addAll(@Y61.k Collection<? extends E> collection) {
        S0<E> s02 = this.f25611c;
        int i12 = s02.f25732d;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            s02.m(it.next());
        }
        return i12 != s02.f25732d;
    }

    @Override // androidx.collection.l1, java.util.Set, java.util.Collection
    public final void clear() {
        this.f25611c.g();
    }

    @Override // androidx.collection.l1, java.util.Set, java.util.Collection, java.lang.Iterable
    @Y61.k
    public final Iterator<E> iterator() {
        return new a(this);
    }

    @Override // androidx.collection.l1, java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        return this.f25611c.n(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.collection.l1, java.util.Set, java.util.Collection
    public final boolean removeAll(@Y61.k Collection<? extends Object> collection) {
        S0<E> s02 = this.f25611c;
        int i12 = s02.f25732d;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            s02.k(it.next());
        }
        return i12 != s02.f25732d;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004e  */
    @Override // androidx.collection.l1, java.util.Set, java.util.Collection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean retainAll(@Y61.k java.util.Collection<? extends java.lang.Object> r18) {
        /*
            r17 = this;
            r0 = r17
            androidx.collection.S0<E> r1 = r0.f25611c
            java.lang.Object[] r2 = r1.f25730b
            int r3 = r1.f25732d
            long[] r4 = r1.f25729a
            int r5 = r4.length
            int r5 = r5 + (-2)
            r6 = 0
            if (r5 < 0) goto L53
            r7 = r6
        L11:
            r8 = r4[r7]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L4e
            int r10 = r7 - r5
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r6
        L2b:
            if (r12 >= r10) goto L4c
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.3E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L48
            int r13 = r7 << 3
            int r13 = r13 + r12
            r14 = r18
            java.lang.Iterable r14 = (java.lang.Iterable) r14
            r15 = r2[r13]
            boolean r14 = kotlin.collections.C42745f0.r(r14, r15)
            if (r14 != 0) goto L48
            r1.o(r13)
        L48:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L2b
        L4c:
            if (r10 != r11) goto L53
        L4e:
            if (r7 == r5) goto L53
            int r7 = r7 + 1
            goto L11
        L53:
            int r1 = r1.f25732d
            if (r3 == r1) goto L58
            r6 = 1
        L58:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.T0.retainAll(java.util.Collection):boolean");
    }
}
