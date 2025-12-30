package AM;

import Y61.l;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: ApiImageScoreV1Response.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B-\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002\u0012\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005¢\u0006\u0004\b\t\u0010\nR\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u000b\u001a\u0004\b\f\u0010\rR(\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"LAM/d;", "", "", "LAM/e;", "badge", "", "", "LAM/g;", "verdict", "<init>", "(Ljava/util/List;Ljava/util/Map;)V", "Ljava/util/List;", "a", "()Ljava/util/List;", "Ljava/util/Map;", "getVerdict", "()Ljava/util/Map;", "_avito_image-perception_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public final class d {

    @com.google.gson.annotations.c("badge")
    @l
    private final List<e> badge;

    @com.google.gson.annotations.c("verdict")
    @l
    private final Map<String, g> verdict;

    public d(@l List<e> list, @l Map<String, g> map) {
        this.badge = list;
        this.verdict = map;
    }

    @l
    public final List<e> a() {
        return this.badge;
    }
}
