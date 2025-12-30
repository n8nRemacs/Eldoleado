package a91;

import com.avito.android.remote.model.messenger.context_actions.PlatformActions;
import java.util.List;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.Action;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\u0018\u00002\u00020\u0001B;\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\f\u0010\rR \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\n\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000b\u0010\u0014\u001a\u0004\b\u001a\u0010\u0016¨\u0006\u001b"}, d2 = {"La91/b;", "", "", "Lru/avito/messenger/generated/api/create_item_channel/Action;", "actions", "La91/z;", PlatformActions.EXPANDABLE, "", PlatformActions.HIDE_KEYBOARD, "", "title", "useMultiButtons", "<init>", "(Ljava/util/List;La91/z;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Boolean;)V", "Ljava/util/List;", "a", "()Ljava/util/List;", "La91/z;", "b", "()La91/z;", "Ljava/lang/Boolean;", "c", "()Ljava/lang/Boolean;", "Ljava/lang/String;", "d", "()Ljava/lang/String;", "e", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.b, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19721b {

    @com.google.gson.annotations.c("actions")
    @Y61.k
    private final List<Action> actions;

    @com.google.gson.annotations.c(PlatformActions.EXPANDABLE)
    @Y61.l
    private final C19745z expandable;

    @com.google.gson.annotations.c(PlatformActions.HIDE_KEYBOARD)
    @Y61.l
    private final Boolean hideKeyboard;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    @com.google.gson.annotations.c("useMultiButtons")
    @Y61.l
    private final Boolean useMultiButtons;

    public C19721b(@Y61.k List<Action> list, @Y61.l C19745z c19745z, @Y61.l Boolean bool, @Y61.k String str, @Y61.l Boolean bool2) {
        this.actions = list;
        this.expandable = c19745z;
        this.hideKeyboard = bool;
        this.title = str;
        this.useMultiButtons = bool2;
    }

    @Y61.k
    public final List<Action> a() {
        return this.actions;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final C19745z getExpandable() {
        return this.expandable;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final Boolean getHideKeyboard() {
        return this.hideKeyboard;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Y61.l
    /* renamed from: e, reason: from getter */
    public final Boolean getUseMultiButtons() {
        return this.useMultiButtons;
    }
}
