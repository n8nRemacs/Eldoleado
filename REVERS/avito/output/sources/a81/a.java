package A81;

import Y41.p;
import androidx.compose.runtime.A;
import androidx.compose.runtime.C22066f2;
import androidx.compose.ui.v;
import kotlin.G0;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class a extends N implements p {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ v f129l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ long f130m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ float f131n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ int f132o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public a(v vVar, long j12, float f12, int i12) {
        super(2);
        this.f129l = vVar;
        this.f130m = j12;
        this.f131n = f12;
        this.f132o = i12;
    }

    @Override // Y41.p
    public final Object invoke(Object obj, Object obj2) {
        ((Number) obj2).intValue();
        int iA2 = C22066f2.a(this.f132o | 1);
        long j12 = this.f130m;
        float f12 = this.f131n;
        b.a(this.f129l, j12, f12, (A) obj, iA2);
        return G0.f406611a;
    }
}
