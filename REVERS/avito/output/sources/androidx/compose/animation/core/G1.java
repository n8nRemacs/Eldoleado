package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/G1;", "T", "Landroidx/compose/animation/core/M;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class G1<T> implements M<T> {

    /* renamed from: a, reason: collision with root package name */
    public final int f26066a;

    /* renamed from: b, reason: collision with root package name */
    public final int f26067b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final N f26068c;

    public G1() {
        this(0, 0, null, 7, null);
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    public final K1 a(H1 h12) {
        return new a2(this.f26066a, this.f26067b, this.f26068c);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof G1)) {
            return false;
        }
        G1 g12 = (G1) obj;
        return g12.f26066a == this.f26066a && g12.f26067b == this.f26067b && kotlin.jvm.internal.L.f(g12.f26068c, this.f26068c);
    }

    public final int hashCode() {
        return ((this.f26068c.hashCode() + (this.f26066a * 31)) * 31) + this.f26067b;
    }

    public G1(int i12, int i13, N n12, int i14, C42822w c42822w) {
        this((i14 & 1) != 0 ? 300 : i12, (i14 & 2) != 0 ? 0 : i13, (i14 & 4) != 0 ? Q.f26121a : n12);
    }

    @Override // androidx.compose.animation.core.M, androidx.compose.animation.core.InterfaceC20307p
    public final P1 a(H1 h12) {
        return new a2(this.f26066a, this.f26067b, this.f26068c);
    }

    public G1(int i12, int i13, @Y61.k N n12) {
        this.f26066a = i12;
        this.f26067b = i13;
        this.f26068c = n12;
    }
}
