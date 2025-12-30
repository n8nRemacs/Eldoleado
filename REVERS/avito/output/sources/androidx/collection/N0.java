package androidx.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.C42745f0;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42821v;
import kotlin.jvm.internal.C42822w;

/* compiled from: ObjectList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0003\u0003\u0004\u0005¨\u0006\u0006"}, d2 = {"Landroidx/collection/N0;", "E", "Landroidx/collection/Z0;", "a", "b", "c", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class N0<E> extends Z0<E> {

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public b<E> f25577c;

    /* compiled from: ObjectList.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010+\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/N0$a;", "T", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a<T> implements ListIterator<T>, Z41.f {

        /* renamed from: b, reason: collision with root package name */
        @Y61.k
        public final Object f25578b;

        /* renamed from: c, reason: collision with root package name */
        public int f25579c;

        public a(@Y61.k List<T> list, int i12) {
            this.f25578b = list;
            this.f25579c = i12 - 1;
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.ListIterator
        public final void add(T t12) {
            int i12 = this.f25579c + 1;
            this.f25579c = i12;
            this.f25578b.add(i12, t12);
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.ListIterator, java.util.Iterator
        public final boolean hasNext() {
            return this.f25579c < this.f25578b.size() - 1;
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this.f25579c >= 0;
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.ListIterator, java.util.Iterator
        public final T next() {
            int i12 = this.f25579c + 1;
            this.f25579c = i12;
            return (T) this.f25578b.get(i12);
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.f25579c + 1;
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.util.List] */
        @Override // java.util.ListIterator
        public final T previous() {
            int i12 = this.f25579c;
            this.f25579c = i12 - 1;
            return (T) this.f25578b.get(i12);
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.f25579c;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.ListIterator, java.util.Iterator
        public final void remove() {
            this.f25578b.remove(this.f25579c);
            this.f25579c--;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.ListIterator
        public final void set(T t12) {
            this.f25578b.set(this.f25579c, t12);
        }
    }

    /* compiled from: ObjectList.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/N0$b;", "T", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class b<T> implements List<T>, Z41.e {

        /* renamed from: b, reason: collision with root package name */
        @Y61.k
        public final N0<T> f25580b;

        public b(@Y61.k N0<T> n02) {
            this.f25580b = n02;
        }

        @Override // java.util.List
        public final void add(int i12, T t12) {
            int i13;
            N0<T> n02 = this.f25580b;
            if (i12 < 0 || i12 > (i13 = n02.f25660b)) {
                n02.getClass();
                K.f.b("Index " + i12 + " must be in 0.." + n02.f25660b);
                throw null;
            }
            int i14 = i13 + 1;
            Object[] objArr = n02.f25659a;
            if (objArr.length < i14) {
                n02.m(i14, objArr);
            }
            Object[] objArr2 = n02.f25659a;
            int i15 = n02.f25660b;
            if (i12 != i15) {
                C42756l.o(objArr2, i12 + 1, objArr2, i12, i15);
            }
            objArr2[i12] = t12;
            n02.f25660b++;
        }

        @Override // java.util.List
        public final boolean addAll(int i12, @Y61.k Collection<? extends T> collection) {
            N0<T> n02 = this.f25580b;
            if (i12 < 0 || i12 > n02.f25660b) {
                n02.getClass();
                K.f.b("Index " + i12 + " must be in 0.." + n02.f25660b);
                throw null;
            }
            int i13 = 0;
            if (collection.isEmpty()) {
                return false;
            }
            int size = collection.size() + n02.f25660b;
            Object[] objArr = n02.f25659a;
            if (objArr.length < size) {
                n02.m(size, objArr);
            }
            Object[] objArr2 = n02.f25659a;
            if (i12 != n02.f25660b) {
                C42756l.o(objArr2, collection.size() + i12, objArr2, i12, n02.f25660b);
            }
            for (T t12 : collection) {
                int i14 = i13 + 1;
                if (i13 < 0) {
                    C42745f0.H0();
                    throw null;
                }
                objArr2[i13 + i12] = t12;
                i13 = i14;
            }
            n02.f25660b = collection.size() + n02.f25660b;
            return true;
        }

        @Override // java.util.List, java.util.Collection
        public final void clear() {
            this.f25580b.i();
        }

        @Override // java.util.List, java.util.Collection
        public final boolean contains(Object obj) {
            return this.f25580b.c(obj) >= 0;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
            N0<T> n02 = this.f25580b;
            n02.getClass();
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                if (n02.c(it.next()) < 0) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.List
        public final T get(int i12) {
            a1.a(i12, this);
            return this.f25580b.b(i12);
        }

        @Override // java.util.List
        public final int indexOf(Object obj) {
            return this.f25580b.c(obj);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean isEmpty() {
            return this.f25580b.d();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        @Y61.k
        public final Iterator<T> iterator() {
            return new a(this, 0);
        }

        @Override // java.util.List
        public final int lastIndexOf(Object obj) {
            int i12;
            N0<T> n02 = this.f25580b;
            if (obj == null) {
                Object[] objArr = n02.f25659a;
                i12 = n02.f25660b - 1;
                while (-1 < i12) {
                    if (objArr[i12] != null) {
                        i12--;
                    }
                }
                return -1;
            }
            Object[] objArr2 = n02.f25659a;
            i12 = n02.f25660b - 1;
            while (-1 < i12) {
                if (!obj.equals(objArr2[i12])) {
                    i12--;
                }
            }
            return -1;
            return i12;
        }

        @Override // java.util.List
        @Y61.k
        public final ListIterator<T> listIterator() {
            return new a(this, 0);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean remove(Object obj) {
            return this.f25580b.j(obj);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean removeAll(@Y61.k Collection<? extends Object> collection) {
            N0<T> n02 = this.f25580b;
            int i12 = n02.f25660b;
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                n02.j(it.next());
            }
            return i12 != n02.f25660b;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean retainAll(@Y61.k Collection<? extends Object> collection) {
            N0<T> n02 = this.f25580b;
            int i12 = n02.f25660b;
            Object[] objArr = n02.f25659a;
            for (int i13 = i12 - 1; -1 < i13; i13--) {
                if (!collection.contains(objArr[i13])) {
                    n02.k(i13);
                }
            }
            return i12 != n02.f25660b;
        }

        @Override // java.util.List
        public final T set(int i12, T t12) {
            a1.a(i12, this);
            N0<T> n02 = this.f25580b;
            if (i12 < 0 || i12 >= n02.f25660b) {
                n02.e(i12);
                throw null;
            }
            Object[] objArr = n02.f25659a;
            T t13 = (T) objArr[i12];
            objArr[i12] = t12;
            return t13;
        }

        @Override // java.util.List, java.util.Collection
        public final int size() {
            return this.f25580b.f25660b;
        }

        @Override // java.util.List
        @Y61.k
        public final List<T> subList(int i12, int i13) {
            a1.b(i12, i13, this);
            return new c(i12, i13, this);
        }

        @Override // java.util.List, java.util.Collection
        public final Object[] toArray() {
            return C42821v.a(this);
        }

        @Override // java.util.List
        @Y61.k
        public final ListIterator<T> listIterator(int i12) {
            return new a(this, i12);
        }

        @Override // java.util.List
        public final T remove(int i12) {
            a1.a(i12, this);
            return this.f25580b.k(i12);
        }

        @Override // java.util.List, java.util.Collection
        public final <T> T[] toArray(T[] tArr) {
            return (T[]) C42821v.b(this, tArr);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean add(T t12) {
            this.f25580b.f(t12);
            return true;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean addAll(@Y61.k Collection<? extends T> collection) {
            N0<T> n02 = this.f25580b;
            int i12 = n02.f25660b;
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                n02.f(it.next());
            }
            return i12 != n02.f25660b;
        }
    }

    /* compiled from: ObjectList.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/N0$c;", "T", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class c<T> implements List<T>, Z41.e {

        /* renamed from: b, reason: collision with root package name */
        @Y61.k
        public final Object f25581b;

        /* renamed from: c, reason: collision with root package name */
        public final int f25582c;

        /* renamed from: d, reason: collision with root package name */
        public int f25583d;

        public c(int i12, int i13, @Y61.k List list) {
            this.f25581b = list;
            this.f25582c = i12;
            this.f25583d = i13;
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List, java.util.Collection
        public final boolean add(T t12) {
            int i12 = this.f25583d;
            this.f25583d = i12 + 1;
            this.f25581b.add(i12, t12);
            return true;
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List
        public final boolean addAll(int i12, @Y61.k Collection<? extends T> collection) {
            this.f25581b.addAll(i12 + this.f25582c, collection);
            this.f25583d = collection.size() + this.f25583d;
            return collection.size() > 0;
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List, java.util.Collection
        public final void clear() {
            int i12 = this.f25583d - 1;
            int i13 = this.f25582c;
            if (i13 <= i12) {
                while (true) {
                    this.f25581b.remove(i12);
                    if (i12 == i13) {
                        break;
                    } else {
                        i12--;
                    }
                }
            }
            this.f25583d = i13;
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List, java.util.Collection
        public final boolean contains(Object obj) {
            int i12 = this.f25583d;
            for (int i13 = this.f25582c; i13 < i12; i13++) {
                if (kotlin.jvm.internal.L.f(this.f25581b.get(i13), obj)) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List
        public final T get(int i12) {
            a1.a(i12, this);
            return (T) this.f25581b.get(i12 + this.f25582c);
        }

        /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List
        public final int indexOf(Object obj) {
            int i12 = this.f25583d;
            int i13 = this.f25582c;
            for (int i14 = i13; i14 < i12; i14++) {
                if (kotlin.jvm.internal.L.f(this.f25581b.get(i14), obj)) {
                    return i14 - i13;
                }
            }
            return -1;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean isEmpty() {
            return this.f25583d == this.f25582c;
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        @Y61.k
        public final Iterator<T> iterator() {
            return new a(this, 0);
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List
        public final int lastIndexOf(Object obj) {
            int i12 = this.f25583d - 1;
            int i13 = this.f25582c;
            if (i13 > i12) {
                return -1;
            }
            while (!kotlin.jvm.internal.L.f(this.f25581b.get(i12), obj)) {
                if (i12 == i13) {
                    return -1;
                }
                i12--;
            }
            return i12 - i13;
        }

        @Override // java.util.List
        @Y61.k
        public final ListIterator<T> listIterator() {
            return new a(this, 0);
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List, java.util.Collection
        public final boolean remove(Object obj) {
            int i12 = this.f25583d;
            for (int i13 = this.f25582c; i13 < i12; i13++) {
                ?? r22 = this.f25581b;
                if (kotlin.jvm.internal.L.f(r22.get(i13), obj)) {
                    r22.remove(i13);
                    this.f25583d--;
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean removeAll(@Y61.k Collection<? extends Object> collection) {
            int i12 = this.f25583d;
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                remove(it.next());
            }
            return i12 != this.f25583d;
        }

        /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List, java.util.Collection
        public final boolean retainAll(@Y61.k Collection<? extends Object> collection) {
            int i12 = this.f25583d;
            int i13 = i12 - 1;
            int i14 = this.f25582c;
            if (i14 <= i13) {
                while (true) {
                    ?? r32 = this.f25581b;
                    if (!collection.contains(r32.get(i13))) {
                        r32.remove(i13);
                        this.f25583d--;
                    }
                    if (i13 == i14) {
                        break;
                    }
                    i13--;
                }
            }
            return i12 != this.f25583d;
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List
        public final T set(int i12, T t12) {
            a1.a(i12, this);
            return (T) this.f25581b.set(i12 + this.f25582c, t12);
        }

        @Override // java.util.List, java.util.Collection
        public final int size() {
            return this.f25583d - this.f25582c;
        }

        @Override // java.util.List
        @Y61.k
        public final List<T> subList(int i12, int i13) {
            a1.b(i12, i13, this);
            return new c(i12, i13, this);
        }

        @Override // java.util.List, java.util.Collection
        public final Object[] toArray() {
            return C42821v.a(this);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List
        public final void add(int i12, T t12) {
            this.f25581b.add(i12 + this.f25582c, t12);
            this.f25583d++;
        }

        @Override // java.util.List
        @Y61.k
        public final ListIterator<T> listIterator(int i12) {
            return new a(this, i12);
        }

        @Override // java.util.List, java.util.Collection
        public final <T> T[] toArray(T[] tArr) {
            return (T[]) C42821v.b(this, tArr);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List, java.util.Collection
        public final boolean addAll(@Y61.k Collection<? extends T> collection) {
            this.f25581b.addAll(this.f25583d, collection);
            this.f25583d = collection.size() + this.f25583d;
            return collection.size() > 0;
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, java.util.List] */
        @Override // java.util.List
        public final T remove(int i12) {
            a1.a(i12, this);
            this.f25583d--;
            return (T) this.f25581b.remove(i12 + this.f25582c);
        }
    }

    public N0() {
        this(0, 1, null);
    }

    public final void f(Object obj) {
        int i12 = this.f25660b + 1;
        Object[] objArr = this.f25659a;
        if (objArr.length < i12) {
            m(i12, objArr);
        }
        Object[] objArr2 = this.f25659a;
        int i13 = this.f25660b;
        objArr2[i13] = obj;
        this.f25660b = i13 + 1;
    }

    public final void g(@Y61.k List list) {
        if (list.isEmpty()) {
            return;
        }
        int i12 = this.f25660b;
        int size = list.size() + i12;
        Object[] objArr = this.f25659a;
        if (objArr.length < size) {
            m(size, objArr);
        }
        Object[] objArr2 = this.f25659a;
        int size2 = list.size();
        for (int i13 = 0; i13 < size2; i13++) {
            objArr2[i13 + i12] = list.get(i13);
        }
        this.f25660b = list.size() + this.f25660b;
    }

    @Y61.k
    public final List<E> h() {
        b<E> bVar = this.f25577c;
        if (bVar != null) {
            return bVar;
        }
        b<E> bVar2 = new b<>(this);
        this.f25577c = bVar2;
        return bVar2;
    }

    public final void i() {
        Arrays.fill(this.f25659a, 0, this.f25660b, (Object) null);
        this.f25660b = 0;
    }

    public final boolean j(E e12) {
        int iC2 = c(e12);
        if (iC2 < 0) {
            return false;
        }
        k(iC2);
        return true;
    }

    public final E k(@j.F int i12) {
        int i13;
        if (i12 < 0 || i12 >= (i13 = this.f25660b)) {
            e(i12);
            throw null;
        }
        Object[] objArr = this.f25659a;
        E e12 = (E) objArr[i12];
        if (i12 != i13 - 1) {
            C42756l.o(objArr, i12, objArr, i12 + 1, i13);
        }
        int i14 = this.f25660b - 1;
        this.f25660b = i14;
        objArr[i14] = null;
        return e12;
    }

    public final void l(@j.F int i12, @j.F int i13) {
        int i14;
        if (i12 < 0 || i12 > (i14 = this.f25660b) || i13 < 0 || i13 > i14) {
            StringBuilder sbY = androidx.appcompat.app.r.y(i12, i13, "Start (", ") and end (", ") must be in 0..");
            sbY.append(this.f25660b);
            K.f.b(sbY.toString());
            throw null;
        }
        if (i13 < i12) {
            K.f.a("Start (" + i12 + ") is more than end (" + i13 + ')');
            throw null;
        }
        if (i13 != i12) {
            if (i13 < i14) {
                Object[] objArr = this.f25659a;
                C42756l.o(objArr, i12, objArr, i13, i14);
            }
            int i15 = this.f25660b;
            int i16 = i15 - (i13 - i12);
            Arrays.fill(this.f25659a, i16, i15, (Object) null);
            this.f25660b = i16;
        }
    }

    @kotlin.X
    public final void m(int i12, @Y61.k Object[] objArr) {
        int length = objArr.length;
        Object[] objArr2 = new Object[Math.max(i12, (length * 3) / 2)];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        this.f25659a = objArr2;
    }

    public N0(int i12, int i13, C42822w c42822w) {
        super((i13 & 1) != 0 ? 16 : i12, null);
    }
}
