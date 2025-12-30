package androidx.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: LruCache.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0003*\u00020\u00012\u00020\u0001B\u0011\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\t\u001a\u00020\b2\b\b\u0001\u0010\u0005\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\t\u0010\u0007J\u001a\u0010\u000b\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u000e\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u00002\u0006\u0010\r\u001a\u00028\u0001¢\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\u0010\u0010\u0007J\u0017\u0010\u0011\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u0000¢\u0006\u0004\b\u0011\u0010\fJ\r\u0010\u0012\u001a\u00020\b¢\u0006\u0004\b\u0012\u0010\u0013J\r\u0010\u0014\u001a\u00020\u0004¢\u0006\u0004\b\u0014\u0010\u0015J\r\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0015J\r\u0010\u0016\u001a\u00020\u0004¢\u0006\u0004\b\u0016\u0010\u0015J\r\u0010\u0017\u001a\u00020\u0004¢\u0006\u0004\b\u0017\u0010\u0015J\r\u0010\u0018\u001a\u00020\u0004¢\u0006\u0004\b\u0018\u0010\u0015J\r\u0010\u0019\u001a\u00020\u0004¢\u0006\u0004\b\u0019\u0010\u0015J\r\u0010\u001a\u001a\u00020\u0004¢\u0006\u0004\b\u001a\u0010\u0015J\u0019\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001b¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001f\u001a\u00020\u001eH\u0016¢\u0006\u0004\b\u001f\u0010 ¨\u0006!"}, d2 = {"Landroidx/collection/g0;", "", "K", "V", "", "maxSize", "<init>", "(I)V", "Lkotlin/G0;", "resize", "key", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "value", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "trimToSize", "remove", "evictAll", "()V", "size", "()I", "hitCount", "missCount", "createCount", "putCount", "evictionCount", "", "snapshot", "()Ljava/util/Map;", "", "toString", "()Ljava/lang/String;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.g0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20212g0<K, V> {

    /* renamed from: a, reason: collision with root package name */
    public int f25711a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final K.d<K, V> f25712b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final K.b f25713c;

    /* renamed from: d, reason: collision with root package name */
    public int f25714d;

    /* renamed from: e, reason: collision with root package name */
    public int f25715e;

    /* renamed from: f, reason: collision with root package name */
    public int f25716f;

    /* renamed from: g, reason: collision with root package name */
    public int f25717g;

    /* renamed from: h, reason: collision with root package name */
    public int f25718h;

    /* renamed from: i, reason: collision with root package name */
    public int f25719i;

    public C20212g0(@j.F int i12) {
        this.f25711a = i12;
        if (!(i12 > 0)) {
            K.f.a("maxSize <= 0");
            throw null;
        }
        this.f25712b = new K.d<>(0, 0.75f);
        this.f25713c = new K.b();
    }

    @Y61.l
    public V a(@Y61.k K k12) {
        return null;
    }

    public final int c(K k12, V v12) {
        int iD2 = d(k12, v12);
        if (iD2 >= 0) {
            return iD2;
        }
        throw new IllegalStateException("Negative size: " + k12 + '=' + v12);
    }

    public final int createCount() {
        int i12;
        synchronized (this.f25713c) {
            i12 = this.f25716f;
        }
        return i12;
    }

    public int d(@Y61.k K k12, @Y61.k V v12) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final int evictionCount() {
        int i12;
        synchronized (this.f25713c) {
            i12 = this.f25717g;
        }
        return i12;
    }

    @Y61.l
    public final V get(@Y61.k K key) {
        V vPut;
        synchronized (this.f25713c) {
            V v12 = this.f25712b.f9207a.get(key);
            if (v12 != null) {
                this.f25718h++;
                return v12;
            }
            this.f25719i++;
            V vA2 = a(key);
            if (vA2 == null) {
                return null;
            }
            synchronized (this.f25713c) {
                try {
                    this.f25716f++;
                    vPut = this.f25712b.f9207a.put(key, vA2);
                    if (vPut != null) {
                        this.f25712b.f9207a.put(key, vPut);
                    } else {
                        this.f25714d += c(key, vA2);
                        kotlin.G0 g02 = kotlin.G0.f406611a;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            if (vPut != null) {
                b(key, vA2, vPut);
                return vPut;
            }
            trimToSize(this.f25711a);
            return vA2;
        }
    }

    public final int hitCount() {
        int i12;
        synchronized (this.f25713c) {
            i12 = this.f25718h;
        }
        return i12;
    }

    public final int maxSize() {
        int i12;
        synchronized (this.f25713c) {
            i12 = this.f25711a;
        }
        return i12;
    }

    public final int missCount() {
        int i12;
        synchronized (this.f25713c) {
            i12 = this.f25719i;
        }
        return i12;
    }

    @Y61.l
    public final V put(@Y61.k K key, @Y61.k V value) {
        V vPut;
        synchronized (this.f25713c) {
            try {
                this.f25715e++;
                this.f25714d += c(key, value);
                vPut = this.f25712b.f9207a.put(key, value);
                if (vPut != null) {
                    this.f25714d -= c(key, vPut);
                }
                kotlin.G0 g02 = kotlin.G0.f406611a;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (vPut != null) {
            b(key, vPut, value);
        }
        trimToSize(this.f25711a);
        return vPut;
    }

    public final int putCount() {
        int i12;
        synchronized (this.f25713c) {
            i12 = this.f25715e;
        }
        return i12;
    }

    @Y61.l
    public final V remove(@Y61.k K key) {
        V vRemove;
        synchronized (this.f25713c) {
            try {
                vRemove = this.f25712b.f9207a.remove(key);
                if (vRemove != null) {
                    this.f25714d -= c(key, vRemove);
                }
                kotlin.G0 g02 = kotlin.G0.f406611a;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (vRemove != null) {
            b(key, vRemove, null);
        }
        return vRemove;
    }

    public void resize(@j.F int maxSize) {
        if (!(maxSize > 0)) {
            K.f.a("maxSize <= 0");
            throw null;
        }
        synchronized (this.f25713c) {
            this.f25711a = maxSize;
            kotlin.G0 g02 = kotlin.G0.f406611a;
        }
        trimToSize(maxSize);
    }

    public final int size() {
        int i12;
        synchronized (this.f25713c) {
            i12 = this.f25714d;
        }
        return i12;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Y61.k
    public final Map<K, V> snapshot() {
        LinkedHashMap linkedHashMap;
        synchronized (this.f25713c) {
            linkedHashMap = new LinkedHashMap(this.f25712b.f9207a.entrySet().size());
            Iterator<T> it = this.f25712b.f9207a.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    @Y61.k
    public String toString() {
        String str;
        synchronized (this.f25713c) {
            try {
                int i12 = this.f25718h;
                int i13 = this.f25719i + i12;
                str = "LruCache[maxSize=" + this.f25711a + ",hits=" + this.f25718h + ",misses=" + this.f25719i + ",hitRate=" + (i13 != 0 ? (i12 * 100) / i13 : 0) + "%]";
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0065, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void trimToSize(int r7) {
        /*
            r6 = this;
        L0:
            K.b r0 = r6.f25713c
            monitor-enter(r0)
            int r1 = r6.f25714d     // Catch: java.lang.Throwable -> L17
            r2 = 1
            if (r1 < 0) goto L1b
            K.d<K, V> r1 = r6.f25712b     // Catch: java.lang.Throwable -> L17
            java.util.LinkedHashMap<K, V> r1 = r1.f9207a     // Catch: java.lang.Throwable -> L17
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L17
            if (r1 == 0) goto L19
            int r1 = r6.f25714d     // Catch: java.lang.Throwable -> L17
            if (r1 != 0) goto L1b
            goto L19
        L17:
            r7 = move-exception
            goto L6e
        L19:
            r1 = r2
            goto L1c
        L1b:
            r1 = 0
        L1c:
            if (r1 == 0) goto L66
            int r1 = r6.f25714d     // Catch: java.lang.Throwable -> L17
            if (r1 <= r7) goto L64
            K.d<K, V> r1 = r6.f25712b     // Catch: java.lang.Throwable -> L17
            java.util.LinkedHashMap<K, V> r1 = r1.f9207a     // Catch: java.lang.Throwable -> L17
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L17
            if (r1 == 0) goto L2d
            goto L64
        L2d:
            K.d<K, V> r1 = r6.f25712b     // Catch: java.lang.Throwable -> L17
            java.util.LinkedHashMap<K, V> r1 = r1.f9207a     // Catch: java.lang.Throwable -> L17
            java.util.Set r1 = r1.entrySet()     // Catch: java.lang.Throwable -> L17
            java.lang.Iterable r1 = (java.lang.Iterable) r1     // Catch: java.lang.Throwable -> L17
            java.lang.Object r1 = kotlin.collections.C42745f0.F(r1)     // Catch: java.lang.Throwable -> L17
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: java.lang.Throwable -> L17
            if (r1 != 0) goto L41
            monitor-exit(r0)
            return
        L41:
            java.lang.Object r3 = r1.getKey()     // Catch: java.lang.Throwable -> L17
            java.lang.Object r1 = r1.getValue()     // Catch: java.lang.Throwable -> L17
            K.d<K, V> r4 = r6.f25712b     // Catch: java.lang.Throwable -> L17
            java.util.LinkedHashMap<K, V> r4 = r4.f9207a     // Catch: java.lang.Throwable -> L17
            r4.remove(r3)     // Catch: java.lang.Throwable -> L17
            int r4 = r6.f25714d     // Catch: java.lang.Throwable -> L17
            int r5 = r6.c(r3, r1)     // Catch: java.lang.Throwable -> L17
            int r4 = r4 - r5
            r6.f25714d = r4     // Catch: java.lang.Throwable -> L17
            int r4 = r6.f25717g     // Catch: java.lang.Throwable -> L17
            int r4 = r4 + r2
            r6.f25717g = r4     // Catch: java.lang.Throwable -> L17
            monitor-exit(r0)
            r0 = 0
            r6.b(r3, r1, r0)
            goto L0
        L64:
            monitor-exit(r0)
            return
        L66:
            java.lang.String r7 = "LruCache.sizeOf() is reporting inconsistent results!"
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L17
            r1.<init>(r7)     // Catch: java.lang.Throwable -> L17
            throw r1     // Catch: java.lang.Throwable -> L17
        L6e:
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.C20212g0.trimToSize(int):void");
    }

    public void b(@Y61.k Object obj, @Y61.k Object obj2, @Y61.l Object obj3) {
    }
}
