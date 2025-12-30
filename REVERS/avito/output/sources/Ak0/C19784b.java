package aK0;

import Y61.k;
import android.app.Application;
import android.content.Intent;
import com.avito.android.user_stats.extended_user_stats.ExtendedUserStatsActivity;
import com.squareup.anvil.annotations.ContributesBinding;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: UserStatsIntentFactoryImpl.kt */
@ContributesBinding
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaK0/b;", "LaK0/a;", "_avito_user-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aK0.b, reason: case insensitive filesystem */
/* loaded from: classes5.dex */
public final class C19784b implements InterfaceC19783a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Application f20866a;

    @Inject
    public C19784b(@k Application application) {
        this.f20866a = application;
    }

    @Override // aK0.InterfaceC19783a
    @k
    public final Intent a(int i12, long j12, long j13, @k String str, @k String str2, @k List<String> list, @k List<String> list2) {
        return new Intent(this.f20866a, (Class<?>) ExtendedUserStatsActivity.class).putExtra("requestKeyTabsPosition", i12).putExtra("requestKeyStartDate", j12).putExtra("requestKeyEndDate", j13).putExtra("requestKeyMetric", str).putExtra("requestKeyViews", str2).putStringArrayListExtra("requestKeyItemId", new ArrayList<>(list)).putStringArrayListExtra("requestKeySpaces", new ArrayList<>(list2));
    }
}
