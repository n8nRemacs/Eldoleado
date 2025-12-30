package aJ;

import Y61.l;
import com.avito.android.remote.model.AdvertDetailsBlockIdKt;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: Api1HotelByItemIdItemPageOffersGetResponse.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR(\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\n\u001a\u0004\b\u0010\u0010\f¨\u0006\u0011"}, d2 = {"LaJ/c;", "", "", "id", "", "", AdvertDetailsBlockIdKt.BLOCK_ID_PARAMETERS, "version", "<init>", "(JLjava/util/Map;J)V", "J", "a", "()J", "Ljava/util/Map;", "b", "()Ljava/util/Map;", "c", "_avito_hotel_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aJ.c, reason: case insensitive filesystem */
/* loaded from: classes13.dex */
public final class C19779c {

    @com.google.gson.annotations.c("id")
    private final long id;

    @com.google.gson.annotations.c(AdvertDetailsBlockIdKt.BLOCK_ID_PARAMETERS)
    @l
    private final Map<String, String> parameters;

    @com.google.gson.annotations.c("version")
    private final long version;

    public C19779c(long j12, @l Map<String, String> map, long j13) {
        this.id = j12;
        this.parameters = map;
        this.version = j13;
    }

    /* renamed from: a, reason: from getter */
    public final long getId() {
        return this.id;
    }

    @l
    public final Map<String, String> b() {
        return this.parameters;
    }

    /* renamed from: c, reason: from getter */
    public final long getVersion() {
        return this.version;
    }
}
