package a91;

import com.avito.android.remote.model.messenger.message.MessageBody;
import java.util.List;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0011\u0018\u00002\u00020\u0001B;\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0005¢\u0006\u0004\b\u000b\u0010\fR$\u0010\u0004\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\t\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0013\u001a\u0004\b\u0016\u0010\u0015R\u001a\u0010\n\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0010\u001a\u0004\b\u0017\u0010\u0012¨\u0006\u0018"}, d2 = {"La91/A;", "", "", "La91/Z;", "description", "", MessageBody.Location.KIND, "", "lat", MessageBody.Location.LONGITUDE, "title", "<init>", "(Ljava/util/List;Ljava/lang/String;DDLjava/lang/String;)V", "Ljava/util/List;", "a", "()Ljava/util/List;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "D", "c", "()D", "d", "e", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.A, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19702A {

    @com.google.gson.annotations.c("description")
    @Y61.l
    private final List<Z> description;

    @com.google.gson.annotations.c(MessageBody.Location.KIND)
    @Y61.l
    private final String kind;

    @com.google.gson.annotations.c("lat")
    private final double lat;

    @com.google.gson.annotations.c(MessageBody.Location.LONGITUDE)
    private final double lon;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    public C19702A(@Y61.l List<Z> list, @Y61.l String str, double d12, double d13, @Y61.k String str2) {
        this.description = list;
        this.kind = str;
        this.lat = d12;
        this.lon = d13;
        this.title = str2;
    }

    @Y61.l
    public final List<Z> a() {
        return this.description;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getKind() {
        return this.kind;
    }

    /* renamed from: c, reason: from getter */
    public final double getLat() {
        return this.lat;
    }

    /* renamed from: d, reason: from getter */
    public final double getLon() {
        return this.lon;
    }

    @Y61.k
    /* renamed from: e, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
