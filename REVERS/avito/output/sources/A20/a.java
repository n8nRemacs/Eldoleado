package A20;

import J81.o;
import Y61.k;
import Y61.l;
import com.avito.android.multi_message_send.generated.api.api_bulk_messaging_modal_action.ApiBulkMessagingModalActionResponse;
import com.avito.android.multi_message_send.generated.api.api_bulk_messaging_modal_action.BulkMessagingMessagesSentRequest;
import com.avito.android.remote.model.TypedResult;
import e30.InterfaceC39936a;
import e30.InterfaceC39938c;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

/* compiled from: MultiMessageSendApi.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u0002H§@¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\b\b\u0001\u0010\u0003\u001a\u00020\bH§@¢\u0006\u0004\b\n\u0010\u000bJ \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u00042\b\b\u0001\u0010\u0003\u001a\u00020\fH§@¢\u0006\u0004\b\u000e\u0010\u000fJ \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u0010H§@¢\u0006\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"LA20/a;", "", "LB20/b;", "request", "Lcom/avito/android/remote/model/TypedResult;", "LB20/a;", "d", "(LB20/b;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lcom/avito/android/multi_message_send/generated/api/api_bulk_messaging_modal_action/BulkMessagingMessagesSentRequest;", "Lcom/avito/android/multi_message_send/generated/api/api_bulk_messaging_modal_action/ApiBulkMessagingModalActionResponse;", "a", "(Lcom/avito/android/multi_message_send/generated/api/api_bulk_messaging_modal_action/BulkMessagingMessagesSentRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "LC20/b;", "LC20/a;", "c", "(LC20/b;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "LD20/c;", "LD20/a;", "b", "(LD20/c;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "_avito_multi-message-send_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
@InterfaceC39936a
/* loaded from: classes15.dex */
public interface a {
    @o("1/user-habits/bulkMessaging/modal/action")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @InterfaceC39938c
    @l
    Object a(@J81.a @k BulkMessagingMessagesSentRequest bulkMessagingMessagesSentRequest, @k Continuation<? super TypedResult<ApiBulkMessagingModalActionResponse>> continuation);

    @o("1/user-habits/bulkMessanging/get/anchors")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @InterfaceC39938c
    @l
    Object b(@J81.a @k D20.c cVar, @k Continuation<? super TypedResult<D20.a>> continuation);

    @o("1/user-habits/bulkMessanging/banner/get")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @InterfaceC39938c
    @J81.k({"X-Geo-required: true"})
    @l
    Object c(@J81.a @k C20.b bVar, @k Continuation<? super TypedResult<C20.a>> continuation);

    @o("1/user-habits/bulkMessaging/banner/close")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @InterfaceC39938c
    @J81.k({"X-Geo-required: true"})
    @l
    Object d(@J81.a @k B20.b bVar, @k Continuation<? super TypedResult<B20.a>> continuation);
}
