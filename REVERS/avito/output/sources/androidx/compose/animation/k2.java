package androidx.compose.animation;

import androidx.compose.runtime.C22039c2;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.C22096n;
import androidx.compose.ui.layout.C22361g0;
import kotlin.Metadata;

/* compiled from: SharedTransitionScope.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class k2 extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C22096n f26504l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public k2(C22096n c22096n, int i12) {
        super(2);
        this.f26504l = c22096n;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(7);
        C22096n c22096n = this.f26504l;
        int i12 = C20257b2.f26005a;
        androidx.compose.runtime.B bE2 = a12.E(-2093217917);
        if (bE2.p(1 & iA2, (iA2 & 3) != 2)) {
            C22361g0.a(androidx.compose.runtime.internal.r.c(-863967934, new j2(c22096n), bE2), bE2, 6);
        } else {
            bE2.f();
        }
        C22039c2 c22039c2Z = bE2.Z();
        if (c22039c2Z != null) {
            c22039c2Z.f38184d = new k2(c22096n, iA2);
        }
        return kotlin.G0.f406611a;
    }
}
