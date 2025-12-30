package A91;

import android.os.Bundle;
import com.avito.android.R;

/* loaded from: classes9.dex */
public abstract class q {
    public static ru.mts.biometry.sdk.base.r a() {
        ru.mts.biometry.sdk.base.p pVar = (ru.mts.biometry.sdk.base.p) ru.mts.biometry.sdk.base.r.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putInt("arg_title", R.string.sdk_bio_no_cam_permission);
        bundle.putString("arg_title_string", null);
        bundle.putInt("arg_description", R.string.sdk_bio_no_cam_description);
        bundle.putBoolean("arg_show_button", true);
        bundle.putInt("arg_icon_attr", R.attr.sdkBioIcError);
        bundle.putInt("arg_button_title", R.string.sdk_btn_go_to_settings);
        pVar.setArguments(bundle);
        return (ru.mts.biometry.sdk.base.r) pVar;
    }
}
