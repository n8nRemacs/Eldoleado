package am;

import J81.f;
import J81.t;
import Y61.k;
import Y61.l;
import com.avito.android.calltracking.remote.model.CallCounterResponse;
import com.avito.android.calltracking.remote.model.CalltrackingAllocationResponse;
import com.avito.android.calltracking.remote.model.CalltrackingResponse;
import com.avito.android.calltracking.remote.model.MessengerRecallContactMethodResultForApi;
import com.avito.android.remote.model.TypedResult;
import com.huawei.hms.api.FailedBinderCallBack;
import e30.InterfaceC39936a;
import io.reactivex.rxjava3.core.z;
import kotlin.G0;
import kotlin.Metadata;

/* compiled from: CalltrackingApi.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J9\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\b\b\u0001\u0010\u0003\u001a\u00020\u00022\b\b\u0001\u0010\u0004\u001a\u00020\u00022\b\b\u0001\u0010\u0006\u001a\u00020\u0005H'¢\u0006\u0004\b\n\u0010\u000bJ%\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\b0\u00072\b\b\u0001\u0010\r\u001a\u00020\fH'¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\b0\u0007H'¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\b0\u0007H'¢\u0006\u0004\b\u0015\u0010\u0013Jg\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\b0\u00072\b\b\u0001\u0010\u0016\u001a\u00020\f2\n\b\u0001\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\b\u0001\u0010\u0019\u001a\u00020\f2\b\b\u0001\u0010\u001a\u001a\u00020\u00172\n\b\u0001\u0010\u001b\u001a\u0004\u0018\u00010\f2\n\b\u0001\u0010\u001c\u001a\u0004\u0018\u00010\u00052\b\b\u0001\u0010\u001d\u001a\u00020\fH'¢\u0006\u0004\b\u001f\u0010 ¨\u0006!"}, d2 = {"Lam/a;", "", "", "count", "skip", "", "onlyMissed", "Lio/reactivex/rxjava3/core/z;", "Lcom/avito/android/remote/model/TypedResult;", "Lcom/avito/android/calltracking/remote/model/CalltrackingResponse;", "b", "(IIZ)Lio/reactivex/rxjava3/core/z;", "", FailedBinderCallBack.CALLER_ID, "Lcom/avito/android/calltracking/remote/model/CalltrackingAllocationResponse;", "a", "(Ljava/lang/String;)Lio/reactivex/rxjava3/core/z;", "Lcom/avito/android/calltracking/remote/model/CallCounterResponse;", "c", "()Lio/reactivex/rxjava3/core/z;", "Lkotlin/G0;", "e", "opponentUserID", "", "itemID", "callInitiator", "buyerPhoneID", "networkType", "micAccess", "chatId", "Lcom/avito/android/calltracking/remote/model/MessengerRecallContactMethodResultForApi;", "d", "(Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;JLjava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;)Lio/reactivex/rxjava3/core/z;", "_avito-discouraged_avito-api_calltracking"}, k = 1, mv = {1, 9, 0}, xi = 48)
@InterfaceC39936a
/* renamed from: am.a, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public interface InterfaceC19907a {
    @f("1/calltracking/allocateNumberForCallbackControl")
    @k
    z<TypedResult<CalltrackingAllocationResponse>> a(@t(FailedBinderCallBack.CALLER_ID) @k String callId);

    @f("1/calltracking/mobilecalls")
    @k
    z<TypedResult<CalltrackingResponse>> b(@t("count") int count, @t("skip") int skip, @t("onlyMissed") boolean onlyMissed);

    @f("1/calltracking/calls/count")
    @k
    z<TypedResult<CallCounterResponse>> c();

    @f("1/messenger/recall/contactMethod")
    @k
    z<TypedResult<MessengerRecallContactMethodResultForApi>> d(@t("opponentUserID") @k String opponentUserID, @l @t("itemID") Long itemID, @t("callInitiator") @k String callInitiator, @t("opponentPhoneID") long buyerPhoneID, @l @t("networkType") String networkType, @l @t("micAccess") Boolean micAccess, @t("chatID") @k String chatId);

    @f("1/calltracking/markAllAsViewed")
    @k
    z<TypedResult<G0>> e();
}
