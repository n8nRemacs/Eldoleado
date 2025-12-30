package Ac0;

import android.content.Context;
import android.content.Intent;
import com.avito.android.extended_profile_selection_create.ExtendedProfileSelectionCreateConfig;
import i.AbstractC41201a;
import kotlin.G0;
import kotlin.Metadata;
import yc0.k;
import yc0.z;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"LAc0/k;", "Li/a;", "Lcom/avito/android/extended_profile_selection_create/ExtendedProfileSelectionCreateConfig;", "Lkotlin/G0;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public final class k extends AbstractC41201a<ExtendedProfileSelectionCreateConfig, G0> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ n f467b;

    public k(n nVar) {
        this.f467b = nVar;
    }

    @Override // i.AbstractC41201a
    public final Intent createIntent(Context context, ExtendedProfileSelectionCreateConfig extendedProfileSelectionCreateConfig) {
        return this.f467b.f478i.a(extendedProfileSelectionCreateConfig);
    }

    @Override // i.AbstractC41201a
    public final G0 parseResult(int i12, Intent intent) {
        if (i12 == -1) {
            n nVar = this.f467b;
            nVar.f471b.invoke(new k.b(false, null, 2, null));
            if (intent != null && intent.getBooleanExtra("ExtendedProfileSelectionCreate_isCreated", false)) {
                nVar.f471b.invoke(z.d.f443358a);
            }
        }
        return G0.f406611a;
    }
}
