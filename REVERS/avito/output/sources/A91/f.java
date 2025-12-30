package A91;

import android.os.Bundle;

/* loaded from: classes9.dex */
public abstract class f {
    public static ru.mts.biometry.sdk.base.p a(g gVar) {
        ru.mts.biometry.sdk.base.p pVar = (ru.mts.biometry.sdk.base.p) ru.mts.biometry.sdk.base.p.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putInt("arg_title", gVar.f142a);
        bundle.putString("arg_title_string", null);
        Integer num = gVar.f143b;
        if (num != null) {
            bundle.putInt("arg_description", num.intValue());
        }
        bundle.putBoolean("arg_show_button", gVar.f145d);
        Integer num2 = gVar.f144c;
        if (num2 != null) {
            bundle.putInt("arg_icon_attr", num2.intValue());
        }
        pVar.setArguments(bundle);
        return pVar;
    }
}
