package A91;

import android.os.Bundle;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class h extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.p f146l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public h(ru.mts.biometry.sdk.base.p pVar) {
        super(0);
        this.f146l = pVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        ru.mts.biometry.sdk.base.p pVar = this.f146l;
        Bundle arguments = pVar.getArguments();
        String string = arguments != null ? arguments.getString("arg_title_string") : null;
        if (string != null && string.length() != 0) {
            return string;
        }
        Bundle arguments2 = pVar.getArguments();
        Integer numValueOf = arguments2 != null ? Integer.valueOf(arguments2.getInt("arg_button_title", 0)) : null;
        if (numValueOf != null && numValueOf.intValue() == 0) {
            numValueOf = null;
        }
        if (numValueOf != null) {
            return pVar.getString(numValueOf.intValue());
        }
        return null;
    }
}
