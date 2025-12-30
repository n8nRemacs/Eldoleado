package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.internal.C42821v;
import kotlin.sequences.AbstractC43032o;
import kotlin.sequences.C43033p;

/* compiled from: ScatterMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u00028\u00010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/v1;", "K", "V", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class v1<K, V> implements Collection<V>, Z41.a {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final h1<K, V> f25818b;

    /* compiled from: ScatterMap.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"K", "V", "Lkotlin/sequences/o;", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.collection.Values$iterator$1", f = "ScatterMap.kt", i = {0, 0, 0, 0, 0, 0, 0, 0}, l = {1446}, m = "invokeSuspend", n = {"$this$iterator", "v$iv", "m$iv$iv", "lastIndex$iv$iv", "i$iv$iv", "slot$iv$iv", "bitCount$iv$iv", "j$iv$iv"}, s = {"L$0", "L$1", "L$2", "I$0", "I$1", "J$0", "I$2", "I$3"})
    public static final class a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super V>, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public Object[] f25819q;

        /* renamed from: r, reason: collision with root package name */
        public long[] f25820r;

        /* renamed from: s, reason: collision with root package name */
        public int f25821s;

        /* renamed from: t, reason: collision with root package name */
        public int f25822t;

        /* renamed from: u, reason: collision with root package name */
        public int f25823u;

        /* renamed from: v, reason: collision with root package name */
        public int f25824v;

        /* renamed from: w, reason: collision with root package name */
        public long f25825w;

        /* renamed from: x, reason: collision with root package name */
        public int f25826x;

        /* renamed from: y, reason: collision with root package name */
        public /* synthetic */ Object f25827y;

        /* renamed from: z, reason: collision with root package name */
        public final /* synthetic */ v1<K, V> f25828z;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(v1<K, V> v1Var, Continuation<? super a> continuation) {
            super(2, continuation);
            this.f25828z = v1Var;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            a aVar = new a(this.f25828z, continuation);
            aVar.f25827y = obj;
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
                int r2 = r0.f25826x
                r3 = 1
                r4 = 0
                r5 = 8
                if (r2 == 0) goto L2e
                if (r2 != r3) goto L26
                int r2 = r0.f25824v
                int r6 = r0.f25823u
                long r7 = r0.f25825w
                int r9 = r0.f25822t
                int r10 = r0.f25821s
                long[] r11 = r0.f25820r
                java.lang.Object[] r12 = r0.f25819q
                java.lang.Object r13 = r0.f25827y
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
                java.lang.Object r2 = r0.f25827y
                kotlin.sequences.o r2 = (kotlin.sequences.AbstractC43032o) r2
                androidx.collection.v1<K, V> r6 = r0.f25828z
                androidx.collection.h1<K, V> r6 = r6.f25818b
                java.lang.Object[] r7 = r6.f25723c
                long[] r6 = r6.f25721a
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
                r0.f25827y = r13
                r0.f25819q = r12
                r0.f25820r = r11
                r0.f25821s = r10
                r0.f25822t = r9
                r0.f25825w = r7
                r0.f25823u = r6
                r0.f25824v = r2
                r0.f25826x = r3
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
            throw new UnsupportedOperationException("Method not decompiled: androidx.collection.v1.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public v1(@Y61.k h1<K, V> h1Var) {
        this.f25818b = h1Var;
    }

    @Override // java.util.Collection
    public final boolean add(V v12) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection<? extends V> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        return this.f25818b.d(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Collection
    public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
        Collection<? extends Object> collection2 = collection;
        if (collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (!this.f25818b.d(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.f25818b.f();
    }

    @Override // java.util.Collection, java.lang.Iterable
    @Y61.k
    public final Iterator<V> iterator() {
        return C43033p.v(new a(this, null));
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeIf(Predicate<? super V> predicate) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final int size() {
        return this.f25818b.f25725e;
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        return C42821v.a(this);
    }

    @Override // java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        return (T[]) C42821v.b(this, tArr);
    }
}
