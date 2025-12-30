package Ac0;

import Ac0.q;
import android.content.Context;
import android.content.Intent;
import com.avito.android.photo_picker.PhotoPickerIntentFactory;
import i.AbstractC41201a;
import kotlin.G0;
import kotlin.Metadata;
import yc0.g;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"LAc0/j;", "Li/a;", "LAc0/q$a;", "Lkotlin/G0;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public final class j extends AbstractC41201a<q.a, G0> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ n f466b;

    public j(n nVar) {
        this.f466b = nVar;
    }

    @Override // i.AbstractC41201a
    public final Intent createIntent(Context context, q.a aVar) {
        q.a aVar2 = aVar;
        n nVar = this.f466b;
        PhotoPickerIntentFactory photoPickerIntentFactory = nVar.f476g;
        Context contextRequireContext = nVar.f470a.requireContext();
        PhotoPickerIntentFactory.PhotoPickerMode.ModeAdd modeAdd = new PhotoPickerIntentFactory.PhotoPickerMode.ModeAdd(null, 1, null);
        PhotoPickerIntentFactory.CameraType cameraType = PhotoPickerIntentFactory.CameraType.f218799b;
        Intent intentA = PhotoPickerIntentFactory.a.a(photoPickerIntentFactory, contextRequireContext, aVar2.f508a, nVar.f481l, 0, aVar2.f509b, null, cameraType, modeAdd, false, 256);
        intentA.setFlags(603979776);
        return intentA;
    }

    @Override // i.AbstractC41201a
    public final G0 parseResult(int i12, Intent intent) {
        String stringExtra;
        if (i12 == -1 && intent != null && (stringExtra = intent.getStringExtra("operation_id")) != null) {
            this.f466b.f471b.invoke(new g.d(stringExtra));
        }
        return G0.f406611a;
    }
}
