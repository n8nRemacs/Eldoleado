package androidx.arch.core.internal;

import androidx.annotation.RestrictTo;
import com.huawei.hms.framework.common.ContainerUtils;
import j.N;
import j.P;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: SafeIterableMap.java */
@RestrictTo
/* loaded from: classes.dex */
public class b<K, V> implements Iterable<Map.Entry<K, V>> {

    /* renamed from: b, reason: collision with root package name */
    public c<K, V> f22701b;

    /* renamed from: c, reason: collision with root package name */
    public c<K, V> f22702c;

    /* renamed from: d, reason: collision with root package name */
    public final WeakHashMap<f<K, V>, Boolean> f22703d = new WeakHashMap<>();

    /* renamed from: e, reason: collision with root package name */
    public int f22704e = 0;

    /* compiled from: SafeIterableMap.java */
    public static class a<K, V> extends e<K, V> {
        public a() {
            throw null;
        }

        @Override // androidx.arch.core.internal.b.e
        public final c<K, V> b(c<K, V> cVar) {
            return cVar.f22708e;
        }

        @Override // androidx.arch.core.internal.b.e
        public final c<K, V> c(c<K, V> cVar) {
            return cVar.f22707d;
        }
    }

    /* compiled from: SafeIterableMap.java */
    /* renamed from: androidx.arch.core.internal.b$b, reason: collision with other inner class name */
    public static class C1520b<K, V> extends e<K, V> {
        public C1520b() {
            throw null;
        }

        @Override // androidx.arch.core.internal.b.e
        public final c<K, V> b(c<K, V> cVar) {
            return cVar.f22707d;
        }

        @Override // androidx.arch.core.internal.b.e
        public final c<K, V> c(c<K, V> cVar) {
            return cVar.f22708e;
        }
    }

    /* compiled from: SafeIterableMap.java */
    public static class c<K, V> implements Map.Entry<K, V> {

        /* renamed from: b, reason: collision with root package name */
        @N
        public final K f22705b;

        /* renamed from: c, reason: collision with root package name */
        @N
        public final V f22706c;

        /* renamed from: d, reason: collision with root package name */
        public c<K, V> f22707d;

        /* renamed from: e, reason: collision with root package name */
        public c<K, V> f22708e;

        public c(@N K k12, @N V v12) {
            this.f22705b = k12;
            this.f22706c = v12;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return this.f22705b.equals(cVar.f22705b) && this.f22706c.equals(cVar.f22706c);
        }

        @Override // java.util.Map.Entry
        @N
        public final K getKey() {
            return this.f22705b;
        }

        @Override // java.util.Map.Entry
        @N
        public final V getValue() {
            return this.f22706c;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            return this.f22705b.hashCode() ^ this.f22706c.hashCode();
        }

        @Override // java.util.Map.Entry
        public final V setValue(V v12) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public final String toString() {
            return this.f22705b + ContainerUtils.KEY_VALUE_DELIMITER + this.f22706c;
        }
    }

    /* compiled from: SafeIterableMap.java */
    @RestrictTo
    public class d extends f<K, V> implements Iterator<Map.Entry<K, V>> {

        /* renamed from: b, reason: collision with root package name */
        public c<K, V> f22709b;

        /* renamed from: c, reason: collision with root package name */
        public boolean f22710c = true;

        public d() {
        }

        @Override // androidx.arch.core.internal.b.f
        public final void a(@N c<K, V> cVar) {
            c<K, V> cVar2 = this.f22709b;
            if (cVar == cVar2) {
                c<K, V> cVar3 = cVar2.f22708e;
                this.f22709b = cVar3;
                this.f22710c = cVar3 == null;
            }
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            if (this.f22710c) {
                return b.this.f22701b != null;
            }
            c<K, V> cVar = this.f22709b;
            return (cVar == null || cVar.f22707d == null) ? false : true;
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (this.f22710c) {
                this.f22710c = false;
                this.f22709b = b.this.f22701b;
            } else {
                c<K, V> cVar = this.f22709b;
                this.f22709b = cVar != null ? cVar.f22707d : null;
            }
            return this.f22709b;
        }
    }

    /* compiled from: SafeIterableMap.java */
    public static abstract class e<K, V> extends f<K, V> implements Iterator<Map.Entry<K, V>> {

        /* renamed from: b, reason: collision with root package name */
        public c<K, V> f22712b;

        /* renamed from: c, reason: collision with root package name */
        public c<K, V> f22713c;

        public e(c<K, V> cVar, c<K, V> cVar2) {
            this.f22712b = cVar2;
            this.f22713c = cVar;
        }

        @Override // androidx.arch.core.internal.b.f
        public final void a(@N c<K, V> cVar) {
            c<K, V> cVarC = null;
            if (this.f22712b == cVar && cVar == this.f22713c) {
                this.f22713c = null;
                this.f22712b = null;
            }
            c<K, V> cVar2 = this.f22712b;
            if (cVar2 == cVar) {
                this.f22712b = b(cVar2);
            }
            c<K, V> cVar3 = this.f22713c;
            if (cVar3 == cVar) {
                c<K, V> cVar4 = this.f22712b;
                if (cVar3 != cVar4 && cVar4 != null) {
                    cVarC = c(cVar3);
                }
                this.f22713c = cVarC;
            }
        }

        public abstract c<K, V> b(c<K, V> cVar);

        public abstract c<K, V> c(c<K, V> cVar);

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.f22713c != null;
        }

        @Override // java.util.Iterator
        public final Object next() {
            c<K, V> cVar = this.f22713c;
            c<K, V> cVar2 = this.f22712b;
            this.f22713c = (cVar == cVar2 || cVar2 == null) ? null : c(cVar);
            return cVar;
        }
    }

    /* compiled from: SafeIterableMap.java */
    @RestrictTo
    public static abstract class f<K, V> {
        public abstract void a(@N c<K, V> cVar);
    }

    @P
    public c<K, V> a(K k12) {
        c<K, V> cVar = this.f22701b;
        while (cVar != null && !cVar.f22705b.equals(k12)) {
            cVar = cVar.f22707d;
        }
        return cVar;
    }

    public V b(@N K k12, @N V v12) {
        c<K, V> cVarA = a(k12);
        if (cVarA != null) {
            return cVarA.f22706c;
        }
        c<K, V> cVar = new c<>(k12, v12);
        this.f22704e++;
        c<K, V> cVar2 = this.f22702c;
        if (cVar2 == null) {
            this.f22701b = cVar;
            this.f22702c = cVar;
            return null;
        }
        cVar2.f22707d = cVar;
        cVar.f22708e = cVar2;
        this.f22702c = cVar;
        return null;
    }

    public V d(@N K k12) {
        c<K, V> cVarA = a(k12);
        if (cVarA == null) {
            return null;
        }
        this.f22704e--;
        WeakHashMap<f<K, V>, Boolean> weakHashMap = this.f22703d;
        if (!weakHashMap.isEmpty()) {
            Iterator<f<K, V>> it = weakHashMap.keySet().iterator();
            while (it.hasNext()) {
                it.next().a(cVarA);
            }
        }
        c<K, V> cVar = cVarA.f22708e;
        if (cVar != null) {
            cVar.f22707d = cVarA.f22707d;
        } else {
            this.f22701b = cVarA.f22707d;
        }
        c<K, V> cVar2 = cVarA.f22707d;
        if (cVar2 != null) {
            cVar2.f22708e = cVar;
        } else {
            this.f22702c = cVar;
        }
        cVarA.f22707d = null;
        cVarA.f22708e = null;
        return cVarA.f22706c;
    }

    @N
    public final Iterator<Map.Entry<K, V>> descendingIterator() {
        C1520b c1520b = new C1520b(this.f22702c, this.f22701b);
        this.f22703d.put(c1520b, Boolean.FALSE);
        return c1520b;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0048, code lost:
    
        if (r3.hasNext() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0050, code lost:
    
        if (((androidx.arch.core.internal.b.e) r7).hasNext() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0054, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:?, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(java.lang.Object r7) {
        /*
            r6 = this;
            r0 = 1
            if (r7 != r6) goto L4
            return r0
        L4:
            boolean r1 = r7 instanceof androidx.arch.core.internal.b
            r2 = 0
            if (r1 != 0) goto La
            return r2
        La:
            androidx.arch.core.internal.b r7 = (androidx.arch.core.internal.b) r7
            int r1 = r6.f22704e
            int r3 = r7.f22704e
            if (r1 == r3) goto L13
            return r2
        L13:
            java.util.Iterator r1 = r6.iterator()
            java.util.Iterator r7 = r7.iterator()
        L1b:
            r3 = r1
            androidx.arch.core.internal.b$e r3 = (androidx.arch.core.internal.b.e) r3
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L44
            r4 = r7
            androidx.arch.core.internal.b$e r4 = (androidx.arch.core.internal.b.e) r4
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L44
            java.lang.Object r3 = r3.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r4.next()
            if (r3 != 0) goto L3b
            if (r4 != 0) goto L43
        L3b:
            if (r3 == 0) goto L1b
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L1b
        L43:
            return r2
        L44:
            boolean r1 = r3.hasNext()
            if (r1 != 0) goto L53
            androidx.arch.core.internal.b$e r7 = (androidx.arch.core.internal.b.e) r7
            boolean r7 = r7.hasNext()
            if (r7 != 0) goto L53
            goto L54
        L53:
            r0 = r2
        L54:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.arch.core.internal.b.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        Iterator<Map.Entry<K, V>> it = iterator();
        int iHashCode = 0;
        while (true) {
            e eVar = (e) it;
            if (!eVar.hasNext()) {
                return iHashCode;
            }
            iHashCode += ((Map.Entry) eVar.next()).hashCode();
        }
    }

    @Override // java.lang.Iterable
    @N
    public final Iterator<Map.Entry<K, V>> iterator() {
        a aVar = new a(this.f22701b, this.f22702c);
        this.f22703d.put(aVar, Boolean.FALSE);
        return aVar;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("[");
        Iterator<Map.Entry<K, V>> it = iterator();
        while (true) {
            e eVar = (e) it;
            if (!eVar.hasNext()) {
                sb2.append("]");
                return sb2.toString();
            }
            sb2.append(((Map.Entry) eVar.next()).toString());
            if (eVar.hasNext()) {
                sb2.append(", ");
            }
        }
    }
}
