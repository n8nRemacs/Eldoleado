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

/* compiled from: ScatterSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/l1;", "E", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
class l1<E> implements Set<E>, Z41.a {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final j1<E> f25747b;

    /* compiled from: ScatterSet.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"E", "Lkotlin/sequences/o;", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.collection.SetWrapper$iterator$1", f = "ScatterSet.kt", i = {0, 0, 0, 0, 0, 0, 0, 0}, l = {1153}, m = "invokeSuspend", n = {"$this$iterator", "elements$iv", "m$iv$iv", "lastIndex$iv$iv", "i$iv$iv", "slot$iv$iv", "bitCount$iv$iv", "j$iv$iv"}, s = {"L$0", "L$1", "L$2", "I$0", "I$1", "J$0", "I$2", "I$3"})
    public static final class a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super E>, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public Object[] f25748q;

        /* renamed from: r, reason: collision with root package name */
        public long[] f25749r;

        /* renamed from: s, reason: collision with root package name */
        public int f25750s;

        /* renamed from: t, reason: collision with root package name */
        public int f25751t;

        /* renamed from: u, reason: collision with root package name */
        public int f25752u;

        /* renamed from: v, reason: collision with root package name */
        public int f25753v;

        /* renamed from: w, reason: collision with root package name */
        public long f25754w;

        /* renamed from: x, reason: collision with root package name */
        public int f25755x;

        /* renamed from: y, reason: collision with root package name */
        public /* synthetic */ Object f25756y;

        /* renamed from: z, reason: collision with root package name */
        public final /* synthetic */ l1<E> f25757z;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(l1<E> l1Var, Continuation<? super a> continuation) {
            super(2, continuation);
            this.f25757z = l1Var;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            a aVar = new a(this.f25757z, continuation);
            aVar.f25756y = obj;
            return aVar;
        }

        @Override // Y41.p
        public final Object invoke(Object obj, Continuation<? super kotlin.G0> continuation) {
            return ((a) create((AbstractC43032o) obj, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0066  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0090  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0098  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0051 -> B:23:0x0096). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0053 -> B:14:0x0064). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x006d -> B:20:0x008d). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x008a -> B:20:0x008d). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r21) {
            /*
                r20 = this;
                r0 = r20
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.f25755x
                r3 = 1
                r4 = 0
                r5 = 8
                if (r2 == 0) goto L2e
                if (r2 != r3) goto L26
                int r2 = r0.f25753v
                int r6 = r0.f25752u
                long r7 = r0.f25754w
                int r9 = r0.f25751t
                int r10 = r0.f25750s
                long[] r11 = r0.f25749r
                java.lang.Object[] r12 = r0.f25748q
                java.lang.Object r13 = r0.f25756y
                kotlin.sequences.o r13 = (kotlin.sequences.AbstractC43032o) r13
                kotlin.C42729a0.b(r21)
                goto L8d
            L26:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r1.<init>(r2)
                throw r1
            L2e:
                kotlin.C42729a0.b(r21)
                java.lang.Object r2 = r0.f25756y
                kotlin.sequences.o r2 = (kotlin.sequences.AbstractC43032o) r2
                androidx.collection.l1<E> r6 = r0.f25757z
                androidx.collection.j1<E> r6 = r6.f25747b
                java.lang.Object[] r7 = r6.f25730b
                long[] r6 = r6.f25729a
                int r8 = r6.length
                int r8 = r8 + (-2)
                if (r8 < 0) goto L9b
                r9 = r4
            L43:
                r10 = r6[r9]
                long r12 = ~r10
                r14 = 7
                long r12 = r12 << r14
                long r12 = r12 & r10
                r14 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
                long r12 = r12 & r14
                int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
                if (r12 == 0) goto L96
                int r12 = r9 - r8
                int r12 = ~r12
                int r12 = r12 >>> 31
                int r12 = 8 - r12
                r13 = r2
                r2 = r4
                r18 = r10
                r11 = r6
                r10 = r8
                r6 = r12
                r12 = r7
                r7 = r18
            L64:
                if (r2 >= r6) goto L90
                r14 = 255(0xff, double:1.26E-321)
                long r14 = r14 & r7
                r16 = 128(0x80, double:6.3E-322)
                int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r14 >= 0) goto L8d
                int r14 = r9 << 3
                int r14 = r14 + r2
                r14 = r12[r14]
                r0.f25756y = r13
                r0.f25748q = r12
                r0.f25749r = r11
                r0.f25750s = r10
                r0.f25751t = r9
                r0.f25754w = r7
                r0.f25752u = r6
                r0.f25753v = r2
                r0.f25755x = r3
                java.lang.Object r14 = r13.a(r14, r0)
                if (r14 != r1) goto L8d
                return r1
            L8d:
                long r7 = r7 >> r5
                int r2 = r2 + r3
                goto L64
            L90:
                if (r6 != r5) goto L9b
                r8 = r10
                r6 = r11
                r7 = r12
                r2 = r13
            L96:
                if (r9 == r8) goto L9b
                int r9 = r9 + 1
                goto L43
            L9b:
                kotlin.G0 r1 = kotlin.G0.f406611a
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.collection.l1.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public l1(@Y61.k j1<E> j1Var) {
        this.f25747b = j1Var;
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
        return this.f25747b.b(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!this.f25747b.b(it.next())) {
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
        return kotlin.jvm.internal.L.f(this.f25747b, ((l1) obj).f25747b);
    }

    @Override // java.util.Set, java.util.Collection
    public final int hashCode() {
        return this.f25747b.hashCode();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return this.f25747b.c();
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
        return this.f25747b.f25732d;
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        return C42821v.a(this);
    }

    @Y61.k
    public final String toString() {
        return this.f25747b.toString();
    }

    @Override // java.util.Set, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        return (T[]) C42821v.b(this, tArr);
    }
}
