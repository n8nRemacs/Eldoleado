package aJ;

import Y61.k;
import Y61.l;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.remote.model.category_parameters.EntryPointParameter;
import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import kotlin.Metadata;

/* compiled from: Api1HotelByItemIdItemPageOffersGetResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"LaJ/d;", "", "Lcom/avito/android/deep_linking/links/DeepLink;", ContextActionHandler.Link.DEEPLINK, "LaJ/e;", EntryPointParameter.TYPE, "<init>", "(Lcom/avito/android/deep_linking/links/DeepLink;LaJ/e;)V", "Lcom/avito/android/deep_linking/links/DeepLink;", "a", "()Lcom/avito/android/deep_linking/links/DeepLink;", "LaJ/e;", "b", "()LaJ/e;", "_avito_hotel_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aJ.d, reason: case insensitive filesystem */
/* loaded from: classes13.dex */
public final class C19780d {

    @com.google.gson.annotations.c(ContextActionHandler.Link.DEEPLINK)
    @l
    private final DeepLink deepLink;

    @com.google.gson.annotations.c(EntryPointParameter.TYPE)
    @k
    private final e entryPoint;

    public C19780d(@l DeepLink deepLink, @k e eVar) {
        this.deepLink = deepLink;
        this.entryPoint = eVar;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final DeepLink getDeepLink() {
        return this.deepLink;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final e getEntryPoint() {
        return this.entryPoint;
    }
}
