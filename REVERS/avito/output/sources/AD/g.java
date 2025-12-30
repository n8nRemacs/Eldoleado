package Ad;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;

/* compiled from: InstalledAppsTrackingInteractor.kt */
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAd/g;", "LAd/f;", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class g implements InterfaceC13020f {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final Context f522a;

    @Inject
    public g(@Y61.k Context context) {
        this.f522a = context;
    }

    @Override // Ad.InterfaceC13020f
    @Y61.k
    public final ArrayList a(@Y61.k List list) throws PackageManager.NameNotFoundException {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            try {
                this.f522a.getPackageManager().getPackageInfo((String) obj, 0);
                arrayList.add(obj);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return arrayList;
    }
}
