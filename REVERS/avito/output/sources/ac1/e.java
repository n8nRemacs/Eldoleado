package Ac1;

import Y41.p;
import androidx.compose.runtime.A;
import androidx.compose.runtime.C22066f2;
import androidx.compose.ui.v;
import kotlin.G0;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class e extends N implements p {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ v f514l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ int f515m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public e(v vVar, int i12) {
        super(2);
        this.f514l = vVar;
        this.f515m = i12;
    }

    @Override // Y41.p
    public final Object invoke(Object obj, Object obj2) {
        ((Number) obj2).intValue();
        int iA2 = C22066f2.a(this.f515m | 1);
        f.a(this.f514l, (A) obj, iA2);
        return G0.f406611a;
    }
}
