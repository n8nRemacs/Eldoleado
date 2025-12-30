package Ad;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.media.session.PlaybackStateCompat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;

/* compiled from: InstalledBankAppsTrackingInteractor.kt */
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAd/m;", "LAd/k;", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class m implements k {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final Context f536a;

    @Inject
    public m(@Y61.k Context context) {
        this.f536a = context;
    }

    @Override // Ad.k
    @Y61.k
    public final ArrayList a(@Y61.k List list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(AK.c.k("bank", (String) obj, "://qr.nspk.ru/")));
            int i12 = Build.VERSION.SDK_INT;
            Context context = this.f536a;
            if (!(i12 >= 33 ? context.getPackageManager().queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(PlaybackStateCompat.ACTION_PREPARE_FROM_URI)) : context.getPackageManager().queryIntentActivities(intent, 131072)).isEmpty()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
