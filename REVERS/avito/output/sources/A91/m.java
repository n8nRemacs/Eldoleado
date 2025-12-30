package A91;

import android.os.Bundle;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class m extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.p f151l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m(ru.mts.biometry.sdk.base.p pVar) {
        super(0);
        this.f151l = pVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        ru.mts.biometry.sdk.base.p pVar = this.f151l;
        Bundle arguments = pVar.getArguments();
        Integer numValueOf = arguments != null ? Integer.valueOf(arguments.getInt("arg_secondary_button_title", 0)) : null;
        if (numValueOf != null && numValueOf.intValue() == 0) {
            numValueOf = null;
        }
        if (numValueOf != null) {
            return pVar.getString(numValueOf.intValue());
        }
        return null;
    }
}
