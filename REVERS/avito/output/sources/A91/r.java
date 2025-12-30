package A91;

import android.os.Bundle;
import java.util.Arrays;
import kotlin.collections.C42745f0;
import ru.mts.biometry.sdk.base.v;

/* loaded from: classes9.dex */
public abstract class r {
    public static v a(String[] strArr) {
        v vVar = new v();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("arg_items", C42745f0.j(Arrays.copyOf(strArr, strArr.length)));
        vVar.setArguments(bundle);
        return vVar;
    }
}
