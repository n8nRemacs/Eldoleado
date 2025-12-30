package Ac0;

import Ac0.C13009b;
import Zd.InterfaceC19542a;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.ActivityC22955m;
import com.avito.android.authorization.AuthSource;
import i.AbstractC41201a;
import kotlin.G0;
import kotlin.Metadata;
import yc0.k;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"LAc0/f;", "Li/a;", "LAc0/b$a;", "Lkotlin/G0;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ac0.f, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C13013f extends AbstractC41201a<C13009b.a, G0> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ n f462b;

    public C13013f(n nVar) {
        this.f462b = nVar;
    }

    @Override // i.AbstractC41201a
    public final Intent createIntent(Context context, C13009b.a aVar) {
        InterfaceC19542a interfaceC19542a = this.f462b.f473d;
        aVar.getClass();
        return interfaceC19542a.a(AuthSource.f92728t);
    }

    @Override // i.AbstractC41201a
    public final G0 parseResult(int i12, Intent intent) {
        n nVar = this.f462b;
        if (i12 == -1) {
            nVar.f471b.invoke(new k.b(false, null, 2, null));
        } else {
            ActivityC22955m activityC22955mL1 = nVar.f470a.l1();
            if (activityC22955mL1 != null) {
                activityC22955mL1.finish();
            }
        }
        return G0.f406611a;
    }
}
