package aM0;

import J81.d;
import J81.e;
import J81.f;
import J81.o;
import J81.s;
import J81.t;
import J81.u;
import Y61.k;
import bM0.C25529a;
import bM0.C25532d;
import com.avito.android.remote.model.DeepLinkResponse;
import com.avito.android.remote.model.TypedResult;
import e30.InterfaceC39936a;
import io.reactivex.rxjava3.core.I;
import io.reactivex.rxjava3.core.z;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: VasPlanningApi.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J%\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u0002H'¢\u0006\u0004\b\u0007\u0010\bJQ\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u00022\u0014\b\u0001\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\t2\u0014\b\u0001\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\tH'¢\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00050\u00112\b\b\u0001\u0010\u000f\u001a\u00020\u00022\b\b\u0001\u0010\u0010\u001a\u00020\u0002H'¢\u0006\u0004\b\u0013\u0010\u0014J;\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00050\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u00022\u0014\b\u0001\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\tH'¢\u0006\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"LaM0/a;", "", "", "checkoutContext", "Lio/reactivex/rxjava3/core/z;", "Lcom/avito/android/remote/model/TypedResult;", "LbM0/a;", "b", "(Ljava/lang/String;)Lio/reactivex/rxjava3/core/z;", "", "selectedDateTimes", "selectedDateTimesRaw", "Lcom/avito/android/remote/model/DeepLinkResponse;", "d", "(Ljava/lang/String;Ljava/util/Map;Ljava/util/Map;)Lio/reactivex/rxjava3/core/z;", "itemId", "planId", "Lio/reactivex/rxjava3/core/I;", "LbM0/d;", "a", "(Ljava/lang/String;Ljava/lang/String;)Lio/reactivex/rxjava3/core/I;", "plannedServices", "LbM0/e;", "c", "(Ljava/lang/String;Ljava/util/Map;)Lio/reactivex/rxjava3/core/z;", "_avito_vas-planning_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
@InterfaceC39936a
/* renamed from: aM0.a, reason: case insensitive filesystem */
/* loaded from: classes5.dex */
public interface InterfaceC19791a {
    @o("1/profile/item/{itemId}/vas/planner/remove")
    @e
    @k
    I<TypedResult<C25532d>> a(@s("itemId") @k String itemId, @J81.c("planId") @k String planId);

    @f("1/profile/item/vas/planner/view")
    @k
    z<TypedResult<C25529a>> b(@t("checkoutContext") @k String checkoutContext);

    @o("1/profile/item/vas/planner/plan")
    @e
    @k
    z<TypedResult<bM0.e>> c(@J81.c("checkoutContext") @k String checkoutContext, @d @k Map<String, String> plannedServices);

    @f("1/profile/item/vas/planner/context")
    @k
    z<TypedResult<DeepLinkResponse>> d(@t("checkoutContext") @k String checkoutContext, @u @k Map<String, String> selectedDateTimes, @u @k Map<String, String> selectedDateTimesRaw);
}
