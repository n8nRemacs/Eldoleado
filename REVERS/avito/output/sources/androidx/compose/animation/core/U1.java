package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import androidx.compose.animation.core.B;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0080\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Landroidx/compose/animation/core/U1;", "Landroidx/compose/animation/core/x;", "V", "", "vectorValue", "Landroidx/compose/animation/core/N;", "easing", "Landroidx/compose/animation/core/B;", "arcMode", "<init>", "(Landroidx/compose/animation/core/x;Landroidx/compose/animation/core/N;ILkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class U1<V extends AbstractC20330x> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final V f26151a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final N f26152b;

    /* renamed from: c, reason: collision with root package name */
    public final int f26153c;

    public U1() {
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public U1(AbstractC20330x abstractC20330x, N n12, int i12, C42822w c42822w) {
        this.f26151a = abstractC20330x;
        this.f26152b = n12;
        this.f26153c = i12;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof U1)) {
            return false;
        }
        U1 u12 = (U1) obj;
        if (!kotlin.jvm.internal.L.f(this.f26151a, u12.f26151a) || !kotlin.jvm.internal.L.f(this.f26152b, u12.f26152b)) {
            return false;
        }
        B.a aVar = B.f26018b;
        return this.f26153c == u12.f26153c;
    }

    public final int hashCode() {
        int iHashCode = (this.f26152b.hashCode() + (this.f26151a.hashCode() * 31)) * 31;
        B.a aVar = B.f26018b;
        return Integer.hashCode(this.f26153c) + iHashCode;
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("VectorizedKeyframeSpecElementInfo(vectorValue=");
        sb2.append(this.f26151a);
        sb2.append(", easing=");
        sb2.append(this.f26152b);
        sb2.append(", arcMode=");
        B.a aVar = B.f26018b;
        sb2.append((Object) ("ArcMode(value=" + this.f26153c + ')'));
        sb2.append(')');
        return sb2.toString();
    }
}
