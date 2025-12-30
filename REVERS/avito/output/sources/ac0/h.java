package Ac0;

import Ac0.p;
import android.content.Context;
import android.content.Intent;
import com.avito.android.extended_profile_image_edit.ExtendedProfileImageEditConfig;
import i.AbstractC41201a;
import kotlin.G0;
import kotlin.Metadata;
import yc0.g;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"LAc0/h;", "Li/a;", "LAc0/p$a;", "Lkotlin/G0;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public final class h extends AbstractC41201a<p.a, G0> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ n f464b;

    public h(n nVar) {
        this.f464b = nVar;
    }

    @Override // i.AbstractC41201a
    public final Intent createIntent(Context context, p.a aVar) {
        p.a aVar2 = aVar;
        return this.f464b.f479j.a(new ExtendedProfileImageEditConfig(aVar2.f503a, aVar2.f504b, aVar2.f505c, false, aVar2.f506d, aVar2.f507e, null, null, 200, null));
    }

    @Override // i.AbstractC41201a
    public final G0 parseResult(int i12, Intent intent) {
        String stringExtra;
        if (i12 == -1 && intent != null && (stringExtra = intent.getStringExtra("image_id")) != null) {
            this.f464b.f471b.invoke(new g.c(stringExtra));
        }
        return G0.f406611a;
    }
}
