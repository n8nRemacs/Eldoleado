package Ae;

import J81.f;
import J81.s;
import J81.t;
import Y61.k;
import Y61.l;
import com.avito.android.remote.model.ContactAccessService;
import com.avito.android.remote.model.TypedResult;
import e30.InterfaceC39936a;
import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

/* compiled from: AutoContactsApi.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u0002H§@¢\u0006\u0004\b\u0006\u0010\u0007J<\u0010\r\u001a\u00020\f2\b\b\u0001\u0010\u0003\u001a\u00020\u00022\n\b\u0001\u0010\b\u001a\u0004\u0018\u00010\u00022\n\b\u0001\u0010\t\u001a\u0004\u0018\u00010\u00022\b\b\u0003\u0010\u000b\u001a\u00020\nH§@¢\u0006\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"LAe/a;", "", "", "itemId", "Lcom/avito/android/remote/model/TypedResult;", "LYP/a;", "b", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "serviceId", "externalId", "", "extraLine", "Lcom/avito/android/remote/model/ContactAccessService;", "a", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "_avito-discouraged_avito-api_auto-contacts"}, k = 1, mv = {1, 9, 0}, xi = 48)
@InterfaceC39936a
/* renamed from: Ae.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public interface InterfaceC13022a {

    /* compiled from: AutoContactsApi.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ae.a$a, reason: collision with other inner class name */
    public static final class C0020a {
    }

    @f("2/items/{itemId}/contacts/services")
    @l
    @InterfaceC42830m
    Object a(@s("itemId") @k String str, @l @t("serviceId") String str2, @l @t("externalId") String str3, @t("textExtraEol") boolean z12, @k Continuation<? super ContactAccessService> continuation);

    @f("1/auto/contacts/packages/{itemId}")
    @com.avito.android.remote.parse.adapter.stream_gson.b
    @l
    Object b(@s("itemId") @k String str, @k Continuation<? super TypedResult<YP.a>> continuation);
}
