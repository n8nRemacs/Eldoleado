package am0;

import Y61.k;
import com.google.gson.annotations.c;
import kotlin.Metadata;

/* compiled from: ProfileItemsShortcutsV4Response.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\f\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"Lam0/b;", "", "", "count", "", "shortcut", "title", "<init>", "(JLjava/lang/String;Ljava/lang/String;)V", "J", "a", "()J", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "c", "_avito-discouraged_avito-api_user-adverts"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: am0.b, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C19911b {

    @c("count")
    private final long count;

    @c("shortcut")
    @k
    private final String shortcut;

    @c("title")
    @k
    private final String title;

    public C19911b(long j12, @k String str, @k String str2) {
        this.count = j12;
        this.shortcut = str;
        this.title = str2;
    }

    /* renamed from: a, reason: from getter */
    public final long getCount() {
        return this.count;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final String getShortcut() {
        return this.shortcut;
    }

    @k
    /* renamed from: c, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
