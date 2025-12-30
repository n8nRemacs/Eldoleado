package aJ;

import Y61.k;
import Y61.l;
import com.avito.android.beduin.common.container.promo_block.BeduinPromoBlockModel;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.remote.model.AdvertDetailsBlockIdKt;
import com.avito.android.remote.model.Image;
import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import com.avito.android.remote.model.text.AttributedText;
import com.avito.android.remote.model.text.TooltipAttribute;
import java.util.List;
import kotlin.Metadata;

/* compiled from: Api1HotelByItemIdItemPageOffersGetResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"LaJ/g;", "", "a", "b", "LaJ/g$a;", "LaJ/g$b;", "_avito_hotel_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public interface g {

    /* compiled from: Api1HotelByItemIdItemPageOffersGetResponse.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B/\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\b\u0010\tR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\n\u001a\u0004\b\r\u0010\fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\n\u001a\u0004\b\u000e\u0010\fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"LaJ/g$a;", "LaJ/g;", "", BeduinPromoBlockModel.SERIALIZED_NAME_STYLE, BeduinPromoBlockModel.SERIALIZED_NAME_THEME, "title", "Lcom/avito/android/deep_linking/links/DeepLink;", TooltipAttribute.PARAM_DEEP_LINK, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/avito/android/deep_linking/links/DeepLink;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "getTheme", "b", "Lcom/avito/android/deep_linking/links/DeepLink;", "c", "()Lcom/avito/android/deep_linking/links/DeepLink;", "_avito_hotel_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a implements g {

        @com.google.gson.annotations.c(BeduinPromoBlockModel.SERIALIZED_NAME_STYLE)
        @l
        private final String style;

        @com.google.gson.annotations.c(BeduinPromoBlockModel.SERIALIZED_NAME_THEME)
        @l
        private final String theme;

        @com.google.gson.annotations.c("title")
        @l
        private final String title;

        @com.google.gson.annotations.c(TooltipAttribute.PARAM_DEEP_LINK)
        @l
        private final DeepLink uri;

        public a(@l String str, @l String str2, @l String str3, @l DeepLink deepLink) {
            this.style = str;
            this.theme = str2;
            this.title = str3;
            this.uri = deepLink;
        }

        @l
        /* renamed from: a, reason: from getter */
        public final String getStyle() {
            return this.style;
        }

        @l
        /* renamed from: b, reason: from getter */
        public final String getTitle() {
            return this.title;
        }

        @l
        /* renamed from: c, reason: from getter */
        public final DeepLink getUri() {
            return this.uri;
        }
    }

    /* compiled from: Api1HotelByItemIdItemPageOffersGetResponse.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\r\u001a\u00020\u0004¢\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\f\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\r\u0010\u0013\u001a\u0004\b\u001f\u0010\u0015¨\u0006 "}, d2 = {"LaJ/g$b;", "LaJ/g;", "LaJ/g$a;", "actionButton", "Lcom/avito/android/remote/model/text/AttributedText;", "amenities", "Lcom/avito/android/deep_linking/links/DeepLink;", ContextActionHandler.Link.DEEPLINK, "", "Lcom/avito/android/remote/model/Image;", "images", "LaJ/d;", AdvertDetailsBlockIdKt.BLOCK_ID_CREDIT_CALCULATOR, "price", "<init>", "(LaJ/g$a;Lcom/avito/android/remote/model/text/AttributedText;Lcom/avito/android/deep_linking/links/DeepLink;Ljava/util/List;LaJ/d;Lcom/avito/android/remote/model/text/AttributedText;)V", "LaJ/g$a;", "a", "()LaJ/g$a;", "Lcom/avito/android/remote/model/text/AttributedText;", "b", "()Lcom/avito/android/remote/model/text/AttributedText;", "Lcom/avito/android/deep_linking/links/DeepLink;", "c", "()Lcom/avito/android/deep_linking/links/DeepLink;", "Ljava/util/List;", "d", "()Ljava/util/List;", "LaJ/d;", "e", "()LaJ/d;", "f", "_avito_hotel_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class b implements g {

        @com.google.gson.annotations.c("actionButton")
        @k
        private final a actionButton;

        @com.google.gson.annotations.c("amenities")
        @k
        private final AttributedText amenities;

        @com.google.gson.annotations.c(ContextActionHandler.Link.DEEPLINK)
        @k
        private final DeepLink deepLink;

        @com.google.gson.annotations.c("images")
        @k
        private final List<Image> images;

        @com.google.gson.annotations.c(AdvertDetailsBlockIdKt.BLOCK_ID_CREDIT_CALCULATOR)
        @l
        private final C19780d installments;

        @com.google.gson.annotations.c("price")
        @k
        private final AttributedText price;

        public b(@k a aVar, @k AttributedText attributedText, @k DeepLink deepLink, @k List<Image> list, @l C19780d c19780d, @k AttributedText attributedText2) {
            this.actionButton = aVar;
            this.amenities = attributedText;
            this.deepLink = deepLink;
            this.images = list;
            this.installments = c19780d;
            this.price = attributedText2;
        }

        @k
        /* renamed from: a, reason: from getter */
        public final a getActionButton() {
            return this.actionButton;
        }

        @k
        /* renamed from: b, reason: from getter */
        public final AttributedText getAmenities() {
            return this.amenities;
        }

        @k
        /* renamed from: c, reason: from getter */
        public final DeepLink getDeepLink() {
            return this.deepLink;
        }

        @k
        public final List<Image> d() {
            return this.images;
        }

        @l
        /* renamed from: e, reason: from getter */
        public final C19780d getInstallments() {
            return this.installments;
        }

        @k
        /* renamed from: f, reason: from getter */
        public final AttributedText getPrice() {
            return this.price;
        }
    }
}
