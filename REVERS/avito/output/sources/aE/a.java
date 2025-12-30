package AE;

import J81.e;
import J81.f;
import J81.k;
import J81.o;
import J81.t;
import Y61.l;
import com.avito.android.remote.model.TypedResult;
import e30.InterfaceC39936a;
import e30.InterfaceC39938c;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

/* compiled from: FeesMethodsApi.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u0002H§@¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u0002H§@¢\u0006\u0004\b\t\u0010\u0007J \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\b\b\u0001\u0010\n\u001a\u00020\u0002H§@¢\u0006\u0004\b\f\u0010\u0007¨\u0006\r"}, d2 = {"LAE/a;", "", "", "context", "Lcom/avito/android/remote/model/TypedResult;", "LBE/a;", "b", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "LDE/a;", "a", "checkoutContext", "LCE/a;", "c", "_avito_fees-methods_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
@InterfaceC39936a
/* loaded from: classes13.dex */
public interface a {
    @o("2/fees/applyByPackage")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @InterfaceC39938c
    @e
    @k({"X-Geo-required: true", "X-Employeemode-required: true"})
    @l
    Object a(@J81.c("context") @Y61.k String str, @Y61.k Continuation<? super TypedResult<DE.a>> continuation);

    @f("1/fees/byPackage")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @InterfaceC39938c
    @k({"X-Geo-required: true", "X-Employeemode-required: true"})
    @l
    Object b(@t("context") @Y61.k String str, @Y61.k Continuation<? super TypedResult<BE.a>> continuation);

    @f("21/fees/feesMethods")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @InterfaceC39938c
    @k({"X-Geo-required: true", "X-Employeemode-required: true"})
    @l
    Object c(@t("checkoutContext") @Y61.k String str, @Y61.k Continuation<? super TypedResult<CE.a>> continuation);
}
