package A7;

import Y61.k;
import Y61.l;
import android.net.Uri;
import com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker;
import com.avito.android.remote.model.SerpResultCategoryDetails;
import java.util.Set;
import kotlin.Metadata;

/* compiled from: AdvertDetailsTracker.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA7/b;", "Lcom/avito/android/analytics/screens/tracker/ScreenPerformanceTracker;", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public interface b extends ScreenPerformanceTracker {

    /* compiled from: AdvertDetailsTracker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a {
    }

    void C(@k Set<? extends Uri> set);

    void I(@k String str, boolean z12);

    void J(@k String str, boolean z12);

    void K(long j12);

    void M(@k String str, boolean z12);

    void N(@k String str, boolean z12);

    void k();

    void n(@k Throwable th2);

    void q();

    void u(@k Throwable th2);

    void v(@k String str, @k Throwable th2, boolean z12);

    @k
    com.avito.android.analytics.screens.tracker.degrade.b w();

    void y(@k String str, @k Throwable th2, boolean z12);

    void z(@l SerpResultCategoryDetails serpResultCategoryDetails);
}
