package a91;

import com.avito.android.remote.model.messenger.message.MessageBody;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\t\u001a\u0004\b\f\u0010\u000bR\u001a\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"La91/m0;", "", "", "lat", MessageBody.Location.LONGITUDE, "", "title", "<init>", "(DDLjava/lang/String;)V", "D", "a", "()D", "b", "Ljava/lang/String;", "c", "()Ljava/lang/String;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class m0 {

    @com.google.gson.annotations.c("lat")
    private final double lat;

    @com.google.gson.annotations.c(MessageBody.Location.LONGITUDE)
    private final double lon;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    public m0(double d12, double d13, @Y61.k String str) {
        this.lat = d12;
        this.lon = d13;
        this.title = str;
    }

    /* renamed from: a, reason: from getter */
    public final double getLat() {
        return this.lat;
    }

    /* renamed from: b, reason: from getter */
    public final double getLon() {
        return this.lon;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
