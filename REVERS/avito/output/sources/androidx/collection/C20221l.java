package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
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
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010&\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0003¨\u0006\u0005"}, d2 = {"Landroidx/collection/l;", "K", "V", "", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.l, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20221l<K, V> implements Set<Map.Entry<? extends K, ? extends V>>, Z41.a {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final h1<K, V> f25736b;

    /* compiled from: ScatterMap.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00030\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"K", "V", "Lkotlin/sequences/o;", "", "Lkotlin/G0;", "<anonymous>", "(Lkotlin/sequences/o;)V"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "androidx.collection.Entries$iterator$1", f = "ScatterMap.kt", i = {0, 0, 0, 0, 0, 0, 0}, l = {1414}, m = "invokeSuspend", n = {"$this$iterator", "m$iv", "lastIndex$iv", "i$iv", "slot$iv", "bitCount$iv", "j$iv"}, s = {"L$0", "L$2", "I$0", "I$1", "J$0", "I$2", "I$3"})
    /* renamed from: androidx.collection.l$a */
    public static final class a extends RestrictedSuspendLambda implements Y41.p<AbstractC43032o<? super Map.Entry<? extends K, ? extends V>>, Continuation<? super kotlin.G0>, Object> {

        /* renamed from: q, reason: collision with root package name */
        public Object f25737q;

        /* renamed from: r, reason: collision with root package name */
        public long[] f25738r;

        /* renamed from: s, reason: collision with root package name */
        public int f25739s;

        /* renamed from: t, reason: collision with root package name */
        public int f25740t;

        /* renamed from: u, reason: collision with root package name */
        public int f25741u;

        /* renamed from: v, reason: collision with root package name */
        public int f25742v;

        /* renamed from: w, reason: collision with root package name */
        public long f25743w;

        /* renamed from: x, reason: collision with root package name */
        public int f25744x;

        /* renamed from: y, reason: collision with root package name */
        public /* synthetic */ Object f25745y;

        /* renamed from: z, reason: collision with root package name */
        public final /* synthetic */ C20221l<K, V> f25746z;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(C20221l<K, V> c20221l, Continuation<? super a> continuation) {
            super(2, continuation);
            this.f25746z = c20221l;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<kotlin.G0> create(Object obj, Continuation<?> continuation) {
            a aVar = new a(this.f25746z, continuation);
            aVar.f25745y = obj;
            return aVar;
        }

        @Override // Y41.p
        public final Object invoke(Object obj, Continuation<? super kotlin.G0> continuation) {
            return ((a) create((AbstractC43032o) obj, continuation)).invokeSuspend(kotlin.G0.f406611a);
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0069  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00a5  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00ad  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00b0  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0053 -> B:14:0x0067). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x009a -> B:20:0x009d). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x00a0 -> B:22:0x00a1). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x00ad -> B:27:0x00ae). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r22) {
            /*
                Method dump skipped, instructions count: 183
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.collection.C20221l.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public C20221l(@Y61.k h1<K, V> h1Var) {
        this.f25736b = h1Var;
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection<? extends Map.Entry<? extends K, ? extends V>> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return kotlin.jvm.internal.L.f(this.f25736b.e(entry.getKey()), entry.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
        Collection<? extends Object> collection2 = collection;
        if (collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!kotlin.jvm.internal.L.f(this.f25736b.e(entry.getKey()), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return this.f25736b.f();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    @Y61.k
    public final Iterator<Map.Entry<K, V>> iterator() {
        return C43033p.v(new a(this, null));
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        return this.f25736b.f25725e;
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
