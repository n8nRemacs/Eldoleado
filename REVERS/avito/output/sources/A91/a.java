package A91;

import kotlin.G0;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class a extends N implements Y41.p {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.b f138l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public a(ru.mts.biometry.sdk.base.b bVar) {
        super(2);
        this.f138l = bVar;
    }

    @Override // Y41.p
    public final Object invoke(Object obj, Object obj2) {
        this.f138l.c5(((Number) obj).intValue(), ((Number) obj2).intValue());
        return G0.f406611a;
    }
}
