package Ac0;

import Ac0.C13010c;
import android.content.Context;
import android.content.Intent;
import com.avito.android.edit_carousel.EditCarouselActivity;
import i.AbstractC41201a;
import kotlin.G0;
import kotlin.Metadata;
import yc0.k;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"LAc0/g;", "Li/a;", "LAc0/c$a;", "Lkotlin/G0;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ac0.g, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C13014g extends AbstractC41201a<C13010c.a, G0> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ n f463b;

    public C13014g(n nVar) {
        this.f463b = nVar;
    }

    @Override // i.AbstractC41201a
    public final Intent createIntent(Context context, C13010c.a aVar) {
        Intent intentPutExtra = new Intent(this.f463b.f470a.requireContext(), (Class<?>) EditCarouselActivity.class).putExtra("data", aVar.f460a);
        intentPutExtra.setFlags(603979776);
        return intentPutExtra;
    }

    @Override // i.AbstractC41201a
    public final G0 parseResult(int i12, Intent intent) {
        if (i12 == -1) {
            this.f463b.f471b.invoke(new k.b(false, null, 2, null));
        }
        return G0.f406611a;
    }
}
