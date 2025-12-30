package aJ;

import Y61.k;
import Y61.l;
import java.util.List;
import kotlin.Metadata;

/* compiled from: Api1HotelByItemIdItemPageOffersGetResponse.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\t\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u000e\u001a\u0004\b\u0017\u0010\u0010R\u001a\u0010\u000b\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000b\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"LaJ/b;", "", "LaJ/a;", "bottomAction", "", "LaJ/g;", "items", "LaJ/c;", "onScrollEvent", "searchOffers", "", "title", "<init>", "(LaJ/a;Ljava/util/List;LaJ/c;LaJ/a;Ljava/lang/String;)V", "LaJ/a;", "a", "()LaJ/a;", "Ljava/util/List;", "b", "()Ljava/util/List;", "LaJ/c;", "c", "()LaJ/c;", "d", "Ljava/lang/String;", "e", "()Ljava/lang/String;", "_avito_hotel_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aJ.b, reason: case insensitive filesystem */
/* loaded from: classes13.dex */
public final class C19778b {

    @com.google.gson.annotations.c("bottomAction")
    @l
    private final C19777a bottomAction;

    @com.google.gson.annotations.c("items")
    @k
    private final List<g> items;

    @com.google.gson.annotations.c("onScrollEvent")
    @l
    private final C19779c onScrollEvent;

    @com.google.gson.annotations.c("searchOffers")
    @k
    private final C19777a searchOffers;

    @com.google.gson.annotations.c("title")
    @k
    private final String title;

    /* JADX WARN: Multi-variable type inference failed */
    public C19778b(@l C19777a c19777a, @k List<? extends g> list, @l C19779c c19779c, @k C19777a c19777a2, @k String str) {
        this.bottomAction = c19777a;
        this.items = list;
        this.onScrollEvent = c19779c;
        this.searchOffers = c19777a2;
        this.title = str;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final C19777a getBottomAction() {
        return this.bottomAction;
    }

    @k
    public final List<g> b() {
        return this.items;
    }

    @l
    /* renamed from: c, reason: from getter */
    public final C19779c getOnScrollEvent() {
        return this.onScrollEvent;
    }

    @k
    /* renamed from: d, reason: from getter */
    public final C19777a getSearchOffers() {
        return this.searchOffers;
    }

    @k
    /* renamed from: e, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
