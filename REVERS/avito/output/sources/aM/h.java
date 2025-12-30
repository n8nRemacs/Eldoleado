package AM;

import Y61.l;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: ApiImageScoreV1Request.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\b\u0010\u0018\u00002\u00020\u0001BA\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t¢\u0006\u0004\b\u000b\u0010\fR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\"\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R(\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"LAM/h;", "", "", "categoryID", "", "", "imageIDs", "", "needVerdict", "", "originalImageIDs", "<init>", "(Ljava/lang/Long;Ljava/util/List;Ljava/lang/Boolean;Ljava/util/Map;)V", "Ljava/lang/Long;", "getCategoryID", "()Ljava/lang/Long;", "Ljava/util/List;", "getImageIDs", "()Ljava/util/List;", "Ljava/lang/Boolean;", "getNeedVerdict", "()Ljava/lang/Boolean;", "Ljava/util/Map;", "getOriginalImageIDs", "()Ljava/util/Map;", "_avito_image-perception_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public final class h {

    @com.google.gson.annotations.c("categoryID")
    @l
    private final Long categoryID;

    @com.google.gson.annotations.c("imageIDs")
    @l
    private final List<String> imageIDs;

    @com.google.gson.annotations.c("needVerdict")
    @l
    private final Boolean needVerdict;

    @com.google.gson.annotations.c("originalImageIDs")
    @l
    private final Map<String, String> originalImageIDs;

    public h(@l Long l12, @l List<String> list, @l Boolean bool, @l Map<String, String> map) {
        this.categoryID = l12;
        this.imageIDs = list;
        this.needVerdict = bool;
        this.originalImageIDs = map;
    }
}
