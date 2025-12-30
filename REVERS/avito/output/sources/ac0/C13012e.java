package Ac0;

import Ac0.C13008a;
import android.content.Context;
import android.content.Intent;
import i.AbstractC41201a;
import kotlin.G0;
import kotlin.Metadata;
import yc0.t;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"LAc0/e;", "Li/a;", "LAc0/a$a;", "Lkotlin/G0;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ac0.e, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C13012e extends AbstractC41201a<C13008a.C0019a, G0> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ n f461b;

    public C13012e(n nVar) {
        this.f461b = nVar;
    }

    @Override // i.AbstractC41201a
    public final Intent createIntent(Context context, C13008a.C0019a c0019a) {
        Intent intentH = this.f461b.f474e.h(c0019a.f459a);
        intentH.setFlags(603979776);
        return intentH;
    }

    @Override // i.AbstractC41201a
    public final G0 parseResult(int i12, Intent intent) {
        String stringExtra;
        String stringExtra2;
        if (i12 == -1 && intent != null && (stringExtra = intent.getStringExtra("extra_result_phone")) != null && (stringExtra2 = intent.getStringExtra("result_message")) != null) {
            this.f461b.f471b.invoke(new t.a(stringExtra, stringExtra2));
        }
        return G0.f406611a;
    }
}
