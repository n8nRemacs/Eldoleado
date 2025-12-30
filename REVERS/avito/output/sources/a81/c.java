package A81;

import Y41.p;
import androidx.compose.runtime.A;
import androidx.compose.runtime.C22066f2;
import androidx.compose.ui.v;
import kotlin.G0;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class c extends N implements p {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ v f133l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ long f134m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ int f135n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public c(int i12, long j12, v vVar) {
        super(2);
        this.f133l = vVar;
        this.f134m = j12;
        this.f135n = i12;
    }

    @Override // Y41.p
    public final Object invoke(Object obj, Object obj2) {
        ((Number) obj2).intValue();
        int iA2 = C22066f2.a(this.f135n | 1);
        v vVar = this.f133l;
        d.a(iA2, this.f134m, (A) obj, vVar);
        return G0.f406611a;
    }
}
