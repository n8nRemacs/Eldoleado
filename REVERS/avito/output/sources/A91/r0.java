package a91;

import com.avito.android.remote.model.messenger.context.ChannelContext;
import java.util.List;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.MenuElement;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001BE\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0002\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\r\u0010\u000eR\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u000f\u001a\u0004\b\u0015\u0010\u0011R\u001c\u0010\n\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\f\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\f\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"La91/r0;", "", "", "La91/x;", "actions", "La91/h;", ChannelContext.ATTACH_MENU, "Lru/avito/messenger/generated/api/create_item_channel/MenuElement;", ChannelContext.MENU, "La91/k0;", ChannelContext.Item.REPLY_TIME, "La91/m0;", ChannelContext.SHARED_LOCATION, "<init>", "(Ljava/util/List;La91/h;Ljava/util/List;La91/k0;La91/m0;)V", "Ljava/util/List;", "getActions", "()Ljava/util/List;", "La91/h;", "a", "()La91/h;", "b", "La91/k0;", "getReplyTime", "()La91/k0;", "La91/m0;", "c", "()La91/m0;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class r0 {

    @com.google.gson.annotations.c("actions")
    @Y61.l
    private final List<C19743x> actions;

    @com.google.gson.annotations.c(ChannelContext.ATTACH_MENU)
    @Y61.l
    private final C19727h attachMenu;

    @com.google.gson.annotations.c(ChannelContext.MENU)
    @Y61.l
    private final List<MenuElement> menu;

    @com.google.gson.annotations.c(ChannelContext.Item.REPLY_TIME)
    @Y61.l
    private final k0 replyTime;

    @com.google.gson.annotations.c(ChannelContext.SHARED_LOCATION)
    @Y61.l
    private final m0 sharedLocation;

    public r0(@Y61.l List<C19743x> list, @Y61.l C19727h c19727h, @Y61.l List<MenuElement> list2, @Y61.l k0 k0Var, @Y61.l m0 m0Var) {
        this.actions = list;
        this.attachMenu = c19727h;
        this.menu = list2;
        this.replyTime = k0Var;
        this.sharedLocation = m0Var;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final C19727h getAttachMenu() {
        return this.attachMenu;
    }

    @Y61.l
    public final List<MenuElement> b() {
        return this.menu;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final m0 getSharedLocation() {
        return this.sharedLocation;
    }
}
