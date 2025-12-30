package A91;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

/* loaded from: classes9.dex */
public abstract class b {
    public static ru.mts.biometry.sdk.base.f a(Fragment fragment, String str) {
        ru.mts.biometry.sdk.base.f fVar = new ru.mts.biometry.sdk.base.f();
        Bundle bundle = new Bundle();
        bundle.putString("arg_title", str);
        fVar.setArguments(bundle);
        fVar.f436378g0 = fragment;
        return fVar;
    }
}
