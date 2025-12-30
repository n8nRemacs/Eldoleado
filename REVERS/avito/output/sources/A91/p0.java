package a91;

import com.avito.android.remote.model.Image;
import com.avito.android.remote.model.messenger.context.ChannelContext;
import java.util.List;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.HeaderTitleButtonNullable;
import ru.avito.messenger.generated.api.create_item_channel.MenuElement;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u0018\u00002\u00020\u0001BU\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0002\u0012\u0006\u0010\u000f\u001a\u00020\u0007¢\u0006\u0004\b\u0010\u0010\u0011R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\n\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\f\u001a\u00020\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\f\u0010\u001e\u001a\u0004\b\u001f\u0010 R\"\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000e\u0010\u0012\u001a\u0004\b!\u0010\u0014R\u001a\u0010\u000f\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000f\u0010\u0018\u001a\u0004\b\"\u0010\u001a¨\u0006#"}, d2 = {"La91/p0;", "", "", "La91/x;", "actions", "La91/h;", ChannelContext.ATTACH_MENU, "", "description", "Lru/avito/messenger/generated/api/create_item_channel/HeaderTitleButtonNullable;", ChannelContext.HEADER_TITLE_BUTTON, "Lcom/avito/android/remote/model/Image;", "image", "Lru/avito/messenger/generated/api/create_item_channel/MenuElement;", ChannelContext.MENU, "name", "<init>", "(Ljava/util/List;La91/h;Ljava/lang/String;Lru/avito/messenger/generated/api/create_item_channel/HeaderTitleButtonNullable;Lcom/avito/android/remote/model/Image;Ljava/util/List;Ljava/lang/String;)V", "Ljava/util/List;", "getActions", "()Ljava/util/List;", "La91/h;", "a", "()La91/h;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "Lru/avito/messenger/generated/api/create_item_channel/HeaderTitleButtonNullable;", "c", "()Lru/avito/messenger/generated/api/create_item_channel/HeaderTitleButtonNullable;", "Lcom/avito/android/remote/model/Image;", "d", "()Lcom/avito/android/remote/model/Image;", "e", "f", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class p0 {

    @com.google.gson.annotations.c("actions")
    @Y61.l
    private final List<C19743x> actions;

    @com.google.gson.annotations.c(ChannelContext.ATTACH_MENU)
    @Y61.l
    private final C19727h attachMenu;

    @com.google.gson.annotations.c("description")
    @Y61.l
    private final String description;

    @com.google.gson.annotations.c(ChannelContext.HEADER_TITLE_BUTTON)
    @Y61.l
    private final HeaderTitleButtonNullable headerTitleButton;

    @com.google.gson.annotations.c("image")
    @Y61.k
    private final Image image;

    @com.google.gson.annotations.c(ChannelContext.MENU)
    @Y61.l
    private final List<MenuElement> menu;

    @com.google.gson.annotations.c("name")
    @Y61.k
    private final String name;

    public p0(@Y61.l List<C19743x> list, @Y61.l C19727h c19727h, @Y61.l String str, @Y61.l HeaderTitleButtonNullable headerTitleButtonNullable, @Y61.k Image image, @Y61.l List<MenuElement> list2, @Y61.k String str2) {
        this.actions = list;
        this.attachMenu = c19727h;
        this.description = str;
        this.headerTitleButton = headerTitleButtonNullable;
        this.image = image;
        this.menu = list2;
        this.name = str2;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final C19727h getAttachMenu() {
        return this.attachMenu;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final HeaderTitleButtonNullable getHeaderTitleButton() {
        return this.headerTitleButton;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final Image getImage() {
        return this.image;
    }

    @Y61.l
    public final List<MenuElement> e() {
        return this.menu;
    }

    @Y61.k
    /* renamed from: f, reason: from getter */
    public final String getName() {
        return this.name;
    }
}
