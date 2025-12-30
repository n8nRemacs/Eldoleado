package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: Animation.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/G;", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/i;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class G<T, V extends AbstractC20330x> implements InterfaceC20286i<T, V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final O1<V> f26058a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final H1<T, V> f26059b;

    /* renamed from: c, reason: collision with root package name */
    public final T f26060c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final V f26061d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final V f26062e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final V f26063f;

    /* renamed from: g, reason: collision with root package name */
    public final T f26064g;

    /* renamed from: h, reason: collision with root package name */
    public final long f26065h;

    public G() {
        throw null;
    }

    public G(@Y61.k H<T> h12, @Y61.k H1<T, V> h13, T t12, @Y61.k V v12) {
        O1<V> o1B = h12.b();
        this.f26058a = o1B;
        this.f26059b = h13;
        this.f26060c = t12;
        V vInvoke = h13.a().invoke(t12);
        this.f26061d = vInvoke;
        this.f26062e = (V) C20333y.a(v12);
        S1 s12 = (S1) o1B;
        this.f26064g = (T) h13.b().invoke(s12.d(vInvoke, v12));
        if (s12.f26141c == null) {
            s12.f26141c = (V) vInvoke.c();
        }
        V v13 = s12.f26141c;
        int f26428e = (v13 == null ? null : v13).getF26404b();
        long jMax = 0;
        for (int i12 = 0; i12 < f26428e; i12++) {
            vInvoke.getClass();
            jMax = Math.max(jMax, s12.f26139a.b(v12.a(i12)));
        }
        this.f26065h = jMax;
        V v14 = (V) C20333y.a(s12.c(jMax, vInvoke, v12));
        this.f26063f = v14;
        int f26428e2 = v14.getF26404b();
        for (int i13 = 0; i13 < f26428e2; i13++) {
            V v15 = this.f26063f;
            v15.e(kotlin.ranges.s.f(v15.a(i13), -this.f26058a.getF26143e(), this.f26058a.getF26143e()), i13);
        }
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    public final boolean a() {
        return false;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    /* renamed from: b, reason: from getter */
    public final long getF26065h() {
        return this.f26065h;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    @Y61.k
    public final H1<T, V> d() {
        return this.f26059b;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    public final T e(long j12) {
        if (c(j12)) {
            return this.f26064g;
        }
        return (T) this.f26059b.b().invoke(this.f26058a.b(j12, this.f26061d, this.f26062e));
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    public final T f() {
        return this.f26064g;
    }

    @Override // androidx.compose.animation.core.InterfaceC20286i
    @Y61.k
    public final V g(long j12) {
        if (c(j12)) {
            return this.f26063f;
        }
        return (V) this.f26058a.c(j12, this.f26061d, this.f26062e);
    }
}
