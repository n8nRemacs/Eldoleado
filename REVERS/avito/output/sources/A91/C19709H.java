package a91;

import com.avito.android.remote.model.messenger.context.ChannelContext;
import java.util.List;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.MenuElement;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\u0018\u00002\u00020\u0001Bi\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0002\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0014\u0010\u0015R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u001c\u0010\n\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u001f\u001a\u0004\b \u0010!R\u001a\u0010\f\u001a\u00020\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\f\u0010\"\u001a\u0004\b#\u0010$R\"\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000e\u0010\u0016\u001a\u0004\b%\u0010\u0018R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0010\u0010&\u001a\u0004\b'\u0010(R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u00118\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0012\u0010)\u001a\u0004\b*\u0010+R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0013\u0010\u0019\u001a\u0004\b,\u0010\u001b¨\u0006-"}, d2 = {"La91/H;", "", "", "La91/x;", "actions", "", ChannelContext.Item.ASSISTANT_REPLY_TIME_PLACEHOLDER, "La91/h;", ChannelContext.ATTACH_MENU, "La91/q;", ChannelContext.Item.HEADER_BUTTON, "La91/F;", "item", "Lru/avito/messenger/generated/api/create_item_channel/MenuElement;", ChannelContext.MENU, "La91/d0;", ChannelContext.Item.PLACEHOLDER, "La91/k0;", ChannelContext.Item.REPLY_TIME, ChannelContext.Item.USER_ID, "<init>", "(Ljava/util/List;Ljava/lang/String;La91/h;La91/q;La91/F;Ljava/util/List;La91/d0;La91/k0;Ljava/lang/String;)V", "Ljava/util/List;", "getActions", "()Ljava/util/List;", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "La91/h;", "b", "()La91/h;", "La91/q;", "c", "()La91/q;", "La91/F;", "d", "()La91/F;", "e", "La91/d0;", "f", "()La91/d0;", "La91/k0;", "g", "()La91/k0;", "h", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.H, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19709H {

    @com.google.gson.annotations.c("actions")
    @Y61.k
    private final List<C19743x> actions;

    @com.google.gson.annotations.c(ChannelContext.Item.ASSISTANT_REPLY_TIME_PLACEHOLDER)
    @Y61.l
    private final String assistantReplyTimePlaceholder;

    @com.google.gson.annotations.c(ChannelContext.ATTACH_MENU)
    @Y61.l
    private final C19727h attachMenu;

    @com.google.gson.annotations.c(ChannelContext.Item.HEADER_BUTTON)
    @Y61.l
    private final C19736q headerItemButton;

    @com.google.gson.annotations.c("item")
    @Y61.k
    private final C19707F item;

    @com.google.gson.annotations.c(ChannelContext.MENU)
    @Y61.l
    private final List<MenuElement> menu;

    @com.google.gson.annotations.c(ChannelContext.Item.PLACEHOLDER)
    @Y61.l
    private final d0 placeholder;

    @com.google.gson.annotations.c(ChannelContext.Item.REPLY_TIME)
    @Y61.l
    private final k0 replyTime;

    @com.google.gson.annotations.c(ChannelContext.Item.USER_ID)
    @Y61.l
    private final String userId;

    public C19709H(@Y61.k List<C19743x> list, @Y61.l String str, @Y61.l C19727h c19727h, @Y61.l C19736q c19736q, @Y61.k C19707F c19707f, @Y61.l List<MenuElement> list2, @Y61.l d0 d0Var, @Y61.l k0 k0Var, @Y61.l String str2) {
        this.actions = list;
        this.assistantReplyTimePlaceholder = str;
        this.attachMenu = c19727h;
        this.headerItemButton = c19736q;
        this.item = c19707f;
        this.menu = list2;
        this.placeholder = d0Var;
        this.replyTime = k0Var;
        this.userId = str2;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getAssistantReplyTimePlaceholder() {
        return this.assistantReplyTimePlaceholder;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final C19727h getAttachMenu() {
        return this.attachMenu;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final C19736q getHeaderItemButton() {
        return this.headerItemButton;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final C19707F getItem() {
        return this.item;
    }

    @Y61.l
    public final List<MenuElement> e() {
        return this.menu;
    }

    @Y61.l
    /* renamed from: f, reason: from getter */
    public final d0 getPlaceholder() {
        return this.placeholder;
    }

    @Y61.l
    /* renamed from: g, reason: from getter */
    public final k0 getReplyTime() {
        return this.replyTime;
    }

    @Y61.l
    /* renamed from: h, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }
}
