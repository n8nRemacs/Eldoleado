package androidx.compose.animation.core;

import androidx.collection.C20247y0;
import androidx.compose.animation.core.B;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@T
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B/\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\b¢\u0006\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Landroidx/compose/animation/core/A;", "T", "Landroidx/compose/animation/core/M;", "Landroidx/compose/animation/core/B;", "mode", "", "durationMillis", "delayMillis", "Landroidx/compose/animation/core/N;", "easing", "<init>", "(IIILandroidx/compose/animation/core/N;Lkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class A<T> implements M<T> {

    /* renamed from: a, reason: collision with root package name */
    public final int f26008a;

    /* renamed from: b, reason: collision with root package name */
    public final int f26009b;

    /* renamed from: c, reason: collision with root package name */
    public final int f26010c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final N f26011d;

    public A(int i12, int i13, int i14, N n12, C42822w c42822w) {
        this.f26008a = i12;
        this.f26009b = i13;
        this.f26010c = i14;
        this.f26011d = n12;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof A)) {
            return false;
        }
        A a12 = (A) obj;
        int i12 = a12.f26008a;
        B.a aVar = B.f26018b;
        if (this.f26008a == i12 && this.f26009b == a12.f26009b && this.f26010c == a12.f26010c) {
            return kotlin.jvm.internal.L.f(this.f26011d, a12.f26011d);
        }
        return false;
    }

    public final int hashCode() {
        B.a aVar = B.f26018b;
        return this.f26011d.hashCode() + (((((Integer.hashCode(this.f26008a) * 31) + this.f26009b) * 31) + this.f26010c) * 31);
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    @Y61.k
    public final <V extends AbstractC20330x> P1<V> a(@Y61.k H1<T, V> h12) {
        C20247y0 c20247y0 = androidx.collection.G.f25546a;
        C20247y0 c20247y02 = new C20247y0(2, null);
        c20247y02.d(0);
        c20247y02.d(this.f26009b);
        return new V1(c20247y02, androidx.collection.K.f25558a, this.f26009b, this.f26010c, this.f26011d, this.f26008a, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public A(int i12, int i13, int i14, N n12, int i15, C42822w c42822w) {
        if ((i15 & 1) != 0) {
            B.f26018b.getClass();
            i12 = B.f26019c;
        }
        this(i12, (i15 & 2) != 0 ? 300 : i13, (i15 & 4) != 0 ? 0 : i14, (i15 & 8) != 0 ? Q.f26121a : n12, null);
    }
}
