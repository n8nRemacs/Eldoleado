package A30;

import Y61.k;
import android.app.Application;
import android.content.Intent;
import com.avito.android.notification_center.list.NotificationCenterListActivity;
import com.squareup.anvil.annotations.ContributesBinding;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: NotificationCenterIntentFactoryImpl.kt */
@ContributesBinding
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA30/c;", "LA30/b;", "_avito_notification-center_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final class c implements b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Application f76a;

    @Inject
    public c(@k Application application) {
        this.f76a = application;
    }

    @Override // A30.b
    @k
    public final Intent a() {
        return new Intent(this.f76a, (Class<?>) NotificationCenterListActivity.class);
    }
}
