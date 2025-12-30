package Ac0;

import android.content.Context;
import android.content.Intent;
import com.avito.android.extended_profile_universal_widget_edit.UniversalWidgetEditArguments;
import i.AbstractC41201a;
import kotlin.G0;
import kotlin.Metadata;
import yc0.k;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"LAc0/m;", "Li/a;", "Lcom/avito/android/extended_profile_universal_widget_edit/UniversalWidgetEditArguments;", "Lkotlin/G0;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public final class m extends AbstractC41201a<UniversalWidgetEditArguments, G0> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ n f469b;

    public m(n nVar) {
        this.f469b = nVar;
    }

    @Override // i.AbstractC41201a
    public final Intent createIntent(Context context, UniversalWidgetEditArguments universalWidgetEditArguments) {
        return this.f469b.f480k.a(universalWidgetEditArguments);
    }

    @Override // i.AbstractC41201a
    public final G0 parseResult(int i12, Intent intent) {
        this.f469b.f471b.invoke(new k.b(false, null, 2, null));
        return G0.f406611a;
    }
}
