package A91;

import android.os.Bundle;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class j extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.p f148l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j(ru.mts.biometry.sdk.base.p pVar) {
        super(0);
        this.f148l = pVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        ru.mts.biometry.sdk.base.p pVar = this.f148l;
        Bundle arguments = pVar.getArguments();
        int i12 = arguments != null ? arguments.getInt("arg_details", 0) : 0;
        if (i12 == 0) {
            return null;
        }
        return C42756l.g0(pVar.getResources().getStringArray(i12));
    }
}
