package a91;

import java.util.List;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\u0018\u00002\u00020\u0001B3\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\n\u0010\u000bR \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\t\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0012\u001a\u0004\b\u0015\u0010\u0014¨\u0006\u0016"}, d2 = {"La91/Y;", "", "", "La91/A;", "markers", "La91/c0;", "markersRequest", "", "text", "title", "<init>", "(Ljava/util/List;La91/c0;Ljava/lang/String;Ljava/lang/String;)V", "Ljava/util/List;", "a", "()Ljava/util/List;", "La91/c0;", "b", "()La91/c0;", "Ljava/lang/String;", "getText", "()Ljava/lang/String;", "c", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class Y {

    @com.google.gson.annotations.c("markers")
    @Y61.k
    private final List<C19702A> markers;

    @com.google.gson.annotations.c("markersRequest")
    @Y61.l
    private final c0 markersRequest;

    @com.google.gson.annotations.c("text")
    @Y61.l
    private final String text;

    @com.google.gson.annotations.c("title")
    @Y61.l
    private final String title;

    public Y(@Y61.k List<C19702A> list, @Y61.l c0 c0Var, @Y61.l String str, @Y61.l String str2) {
        this.markers = list;
        this.markersRequest = c0Var;
        this.text = str;
        this.title = str2;
    }

    @Y61.k
    public final List<C19702A> a() {
        return this.markers;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final c0 getMarkersRequest() {
        return this.markersRequest;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
