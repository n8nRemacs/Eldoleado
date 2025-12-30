package an0;

import Y61.k;
import android.content.Context;
import android.content.Intent;
import androidx.compose.runtime.internal.P;
import com.avito.android.review.report.ComfortableDealReviewsActivity;
import com.squareup.anvil.annotations.ContributesBinding;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: SendReportIntentFactoryImpl.kt */
@P
@ContributesBinding
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lan0/b;", "Lan0/a;", "_avito_comfortable-deal-reviews_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: an0.b, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final class C19916b implements InterfaceC19915a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Context f21164a;

    @Inject
    public C19916b(@k Context context) {
        this.f21164a = context;
    }

    @Override // an0.InterfaceC19915a
    @k
    public final Intent getIntent() {
        return new Intent(this.f21164a, (Class<?>) ComfortableDealReviewsActivity.class);
    }
}
