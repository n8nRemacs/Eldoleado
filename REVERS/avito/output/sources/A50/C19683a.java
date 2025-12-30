package a50;

import Y61.k;
import com.avito.android.beduin_models.BeduinModel;
import com.avito.android.beduin_shared.model.utils.j;
import com.avito.android.orders.api.model.BeduinOrdersResponse;
import com.avito.android.orders.feature.beduin_orders_list.f;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;
import lj.InterfaceC43779a;

/* compiled from: ComponentsForms.kt */
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"_avito_orders_impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: a50.a, reason: case insensitive filesystem */
/* loaded from: classes15.dex */
public final class C19683a {
    public static final void a(@k f fVar, @k BeduinOrdersResponse beduinOrdersResponse) {
        String topFormId = beduinOrdersResponse.getTopFormId();
        List<BeduinModel> topComponents = beduinOrdersResponse.getTopComponents();
        InterfaceC43779a interfaceC43779a = fVar.f210059a;
        if (topFormId != null) {
            if (topComponents == null) {
                topComponents = C42784z0.f406748b;
            }
            j.b(interfaceC43779a, topFormId, topComponents);
        }
        j.b(fVar.f210060b, beduinOrdersResponse.getMainFormId(), beduinOrdersResponse.getMainComponents());
        String bottomFormId = beduinOrdersResponse.getBottomFormId();
        List<BeduinModel> bottomComponents = beduinOrdersResponse.getBottomComponents();
        if (bottomFormId != null) {
            if (bottomComponents == null) {
                bottomComponents = C42784z0.f406748b;
            }
            j.b(fVar.f210061c, bottomFormId, bottomComponents);
        }
    }
}
