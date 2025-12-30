package androidx.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;

/* compiled from: IndexBasedArrayIterator.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0000\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/z;", "T", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.z, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20248z<T> implements Iterator<T>, Z41.d {

    /* renamed from: b, reason: collision with root package name */
    public int f25834b;

    /* renamed from: c, reason: collision with root package name */
    public int f25835c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f25836d;

    public AbstractC20248z(int i12) {
        this.f25834b = i12;
    }

    public abstract T a(int i12);

    public abstract void b(int i12);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f25835c < this.f25834b;
    }

    @Override // java.util.Iterator
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T tA2 = a(this.f25835c);
        this.f25835c++;
        this.f25836d = true;
        return tA2;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.f25836d) {
            throw new IllegalStateException("Call next() before removing an element.");
        }
        int i12 = this.f25835c - 1;
        this.f25835c = i12;
        b(i12);
        this.f25834b--;
        this.f25836d = false;
    }
}
