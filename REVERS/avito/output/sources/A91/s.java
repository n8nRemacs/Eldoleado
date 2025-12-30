package A91;

import android.os.Bundle;
import java.util.ArrayList;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.N;
import ru.mts.biometry.sdk.base.v;

/* loaded from: classes9.dex */
public final class s extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ v f159l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public s(v vVar) {
        super(0);
        this.f159l = vVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        Bundle arguments = this.f159l.getArguments();
        ArrayList<String> stringArrayList = arguments != null ? arguments.getStringArrayList("arg_items") : null;
        return stringArrayList == null ? C42784z0.f406748b : stringArrayList;
    }
}
