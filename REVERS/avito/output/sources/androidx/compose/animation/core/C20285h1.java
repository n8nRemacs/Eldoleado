package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: Animation.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/h1;", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/i;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.h1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20285h1<T, V extends AbstractC20330x> implements InterfaceC20286i<T, V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final K1<V> f26306a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final H1<T, V> f26307b;

    /* renamed from: c, reason: collision with root package name */
    public final T f26308c;

    /* renamed from: d, reason: collision with root package name */
    public final T f26309d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final V f26310e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final V f26311f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public final V f26312g;

    /* renamed from: h, reason: collision with root package name */
    public long f26313h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.l
    public V f26314i;

    public C20285h1(@Y61.k K1<V> k12, @Y61.k H1<T, V> h12, T t12, T t13, @Y61.l V v12) {
        this.f26306a = k12;
        this.f26307b = h12;
        this.f26308c = t13;
        this.f26309d = t12;
        this.f26310e = h12.a().invoke(t12);
        this.f26311f = h12.a().invoke(t13);
        this.f26312g = v12 != null ? (V) C20333y.a(v12) : (V) h12.a().invoke(t12).c();
        this.f26313h = -1L;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    public final boolean a() {
        return this.f26306a.a();
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    /* renamed from: b */
    public final long getF26065h() {
        if (this.f26313h < 0) {
            this.f26313h = this.f26306a.b(this.f26310e, this.f26311f, this.f26312g);
        }
        return this.f26313h;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    @Y61.k
    public final H1<T, V> d() {
        return this.f26307b;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    public final T e(long j12) {
        if (c(j12)) {
            return this.f26308c;
        }
        AbstractC20330x abstractC20330xE = this.f26306a.e(j12, this.f26310e, this.f26311f, this.f26312g);
        int f26428e = abstractC20330xE.getF26404b();
        for (int i12 = 0; i12 < f26428e; i12++) {
            if (Float.isNaN(abstractC20330xE.a(i12))) {
                C20334y0.b("AnimationVector cannot contain a NaN. " + abstractC20330xE + ". Animation: " + this + ", playTimeNanos: " + j12);
            }
        }
        return (T) this.f26307b.b().invoke(abstractC20330xE);
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    public final T f() {
        return this.f26308c;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    @Y61.k
    public final V g(long j12) {
        if (!c(j12)) {
            return (V) this.f26306a.c(j12, this.f26310e, this.f26311f, this.f26312g);
        }
        V v12 = this.f26314i;
        if (v12 != null) {
            return v12;
        }
        V v13 = (V) this.f26306a.d(this.f26310e, this.f26311f, this.f26312g);
        this.f26314i = v13;
        return v13;
    }

    @Y61.k
    public final String toString() {
        return "TargetBasedAnimation: " + this.f26309d + " -> " + this.f26308c + ",initial velocity: " + this.f26312g + ", duration: " + (getF26065h() / 1000000) + " ms,animationSpec: " + this.f26306a;
    }

    public /* synthetic */ C20285h1(K1 k12, H1 h12, Object obj, Object obj2, AbstractC20330x abstractC20330x, int i12, C42822w c42822w) {
        this((K1<AbstractC20330x>) k12, (H1<Object, AbstractC20330x>) h12, obj, obj2, (i12 & 16) != 0 ? null : abstractC20330x);
    }

    public /* synthetic */ C20285h1(InterfaceC20307p interfaceC20307p, H1 h12, Object obj, Object obj2, AbstractC20330x abstractC20330x, int i12, C42822w c42822w) {
        this((InterfaceC20307p<Object>) interfaceC20307p, (H1<Object, AbstractC20330x>) h12, obj, obj2, (i12 & 16) != 0 ? null : abstractC20330x);
    }

    public C20285h1(@Y61.k InterfaceC20307p<T> interfaceC20307p, @Y61.k H1<T, V> h12, T t12, T t13, @Y61.l V v12) {
        this(interfaceC20307p.a(h12), h12, t12, t13, v12);
    }
}
