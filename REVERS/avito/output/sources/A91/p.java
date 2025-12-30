package A91;

import Au.DialogC13081c;
import Ba1.z;
import C40.a;
import Hj.C13996a;
import Nh.C14576a;
import Zh.C19555a;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import com.avito.android.R;
import com.avito.android.ai_assistant.components.placeholder.AiAssistantPlaceholderView;
import com.avito.android.ai_assistant.components.placeholder.a;
import com.avito.android.deep_linking.action_select_link.ActionSelectMenu;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.gsm_call_starter.impl_module.screen.FakeSystemDialerActivity;
import com.avito.android.lib.design.accordion.Accordion;
import com.avito.android.lib.design.banner.Banner;
import com.avito.android.lib.design.list_item.ListItemCheckmark;
import com.avito.android.photo_list_view.re23.image.content.PublishImageContent;
import com.avito.android.photo_list_view.re23.image.content.a;
import com.avito.android.photo_list_view.re23.image.error.PublishImageError;
import com.avito.android.photo_list_view.re23.image.error.a;
import com.avito.android.rating_form.activity.bottom_sheet.RatingFormBottomSheetActivity;
import com.google.crypto.tink.aead.internal.InsecureNonceXChaCha20;
import com.huawei.hms.adapter.internal.AvailableCode;
import dh0.InterfaceC39727a;
import java.util.List;
import kotlin.G0;
import ru.mts.biometry.sdk.feature.document.chooseDocument.searchCountry.u;
import ru.mts.biometry.sdk.feature.documentResult.ui.a0;
import sberid.sdk.auth.view.activity.WebViewActivity;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes9.dex */
public final /* synthetic */ class p implements View.OnClickListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f157b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f158c;

    public /* synthetic */ p(Object obj, int i12) {
        this.f157b = i12;
        this.f158c = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Integer numValueOf;
        ActionSelectMenu.Action action;
        ru.mts.biometry.sdk.base.f fVar;
        int i12 = 0;
        deepLink = null;
        deepLink = null;
        DeepLink deepLink = null;
        Object obj = this.f158c;
        switch (this.f157b) {
            case 0:
                W91.h hVar = ((ru.mts.biometry.sdk.base.p) obj).f436387o0;
                (hVar != null ? hVar : null).a();
                return;
            case 1:
                int i13 = DialogC13081c.f671J;
                DialogC13081c dialogC13081c = (DialogC13081c) obj;
                ViewGroup viewGroup = (ViewGroup) dialogC13081c.findViewById(R.id.actions_container);
                int childCount = viewGroup.getChildCount() - 1;
                if (childCount >= 0) {
                    while (true) {
                        View childAt = viewGroup.getChildAt(i12);
                        ListItemCheckmark listItemCheckmark = childAt instanceof ListItemCheckmark ? (ListItemCheckmark) childAt : null;
                        if (listItemCheckmark != null && listItemCheckmark.isChecked()) {
                            numValueOf = Integer.valueOf(i12);
                        } else if (i12 != childCount) {
                            i12++;
                        }
                    }
                } else {
                    numValueOf = null;
                }
                if (numValueOf != null) {
                    int iIntValue = numValueOf.intValue();
                    List<ActionSelectMenu.Action> list = dialogC13081c.f674G;
                    if (list != null && (action = list.get(iIntValue)) != null) {
                        deepLink = action.getDeepLink();
                    }
                }
                if (deepLink != null) {
                    dialogC13081c.f672E.invoke(deepLink);
                }
                dialogC13081c.dismiss();
                return;
            case 2:
                ((E40.g) obj).f3757d.invoke(a.d.f1912a);
                return;
            case 3:
                int i14 = Hj.c.f7652l;
                Y41.a<G0> aVar = ((C13996a) obj).f7649g;
                if (aVar != null) {
                    aVar.invoke();
                    return;
                }
                return;
            case 4:
                kotlin.reflect.n[] nVarArr = ru.mts.biometry.sdk.feature.document.camera.ui.n.f436424m0;
                P91.e eVar = C91.a.f2018e.f3820f;
                ru.mts.biometry.sdk.feature.document.intro.domain.d dVar = eVar != null ? eVar.f12874b : null;
                int i15 = dVar == null ? -1 : P91.a.f12871a[dVar.ordinal()];
                ru.mts.biometry.sdk.feature.document.camera.ui.n nVar = (ru.mts.biometry.sdk.feature.document.camera.ui.n) obj;
                z.a(nVar, nVar.getResources().getStringArray(i15 != 1 ? i15 != 2 ? i15 != 3 ? i15 != 4 ? R.string.sdk_bio_empty_string : R.array.sdk_bio_drvlic_intro_list_full : R.array.sdk_bio_idCard_intro_list_full : R.array.sdk_bio_russianForeignPassport_intro_list_full : R.array.sdk_bio_passport_intro_list_full));
                return;
            case 5:
                int i16 = AiAssistantPlaceholderView.f88924h;
                Y41.a<G0> aVar2 = ((a.c) obj).f88937e;
                if (aVar2 != null) {
                    aVar2.invoke();
                    return;
                }
                return;
            case 6:
                ((M81.c) obj).f10413b.accept(Integer.valueOf(view.getId()));
                return;
            case 7:
                ((ru.mts.biometry.sdk.feature.document.chooseDocument.searchCountry.l) obj).dismiss();
                return;
            case 8:
                ((u) obj).requireContext();
                z91.s sVar = z91.e.f443908b;
                if (sVar == null) {
                    throw new IllegalArgumentException("DI delegate is null");
                }
                sVar.b().a();
                return;
            case 9:
                C14576a c14576a = (C14576a) obj;
                com.avito.android.beduin_shared.model.utils.a.a(c14576a.f11707f, c14576a.f11706e.getActions());
                return;
            case 10:
                kotlin.reflect.n[] nVarArr2 = ru.mts.biometry.sdk.feature.document.intro.ui.e.f436443i0;
                ru.mts.biometry.sdk.feature.document.intro.ui.e eVar2 = (ru.mts.biometry.sdk.feature.document.intro.ui.e) obj;
                Resources resources = eVar2.getResources();
                kotlin.reflect.n nVar2 = ru.mts.biometry.sdk.feature.document.intro.ui.e.f436443i0[0];
                z.a(eVar2, resources.getStringArray(((Q91.e) ((Q91.f) eVar2.f436444h0.a(eVar2)).f13584E.getValue()).f13583d));
                return;
            case 11:
                int i17 = Accordion.f178177n;
                ((Accordion) obj).a(!r4.f178189m, true);
                return;
            case 12:
                ((T5.c) obj).f15358c.invoke();
                return;
            case 13:
                ru.mts.biometry.sdk.feature.documentResult.ui.o oVar = ((a0) obj).f436449g0;
                if (oVar == null || (fVar = oVar.f436461k0) == null) {
                    return;
                }
                fVar.dismiss();
                return;
            case 14:
                int i18 = FakeSystemDialerActivity.f161248b;
                ((FakeSystemDialerActivity) obj).finish();
                return;
            case 15:
                int i19 = Banner.f178430s;
                ((TU.a) obj).f15675b.invoke();
                return;
            case 16:
                ((UL0.a) obj).f16352E.invoke();
                return;
            case 17:
                RatingFormBottomSheetActivity.a aVar3 = RatingFormBottomSheetActivity.f247948z;
                ((RatingFormBottomSheetActivity) obj).d2().accept(InterfaceC39727a.b.f393997a);
                return;
            case 18:
                int i22 = WebViewActivity.f437756g;
                ((WebViewActivity) obj).finish();
                return;
            case 19:
                W91.h hVar2 = ((ru.mts.biometry.sdk.feature.main.ui.m) obj).f436476h0;
                (hVar2 != null ? hVar2 : null).d(true);
                return;
            case 20:
                kotlin.reflect.n[] nVarArr3 = ru.mts.biometry.sdk.feature.main.ui.r.f436480i0;
                ((ru.mts.biometry.sdk.feature.main.ui.r) obj).requireContext();
                z91.s sVar2 = z91.e.f443908b;
                if (sVar2 == null) {
                    throw new IllegalArgumentException("DI delegate is null");
                }
                sVar2.b().d(true);
                return;
            case 21:
                C19555a c19555a = (C19555a) obj;
                com.avito.android.beduin_shared.model.utils.a.a(c19555a.f20380h, c19555a.f20378f.getOnCloseActions());
                return;
            case 22:
                int i23 = PublishImageContent.f218213m;
                ((a.c) obj).f218231b.invoke();
                return;
            case 23:
                int i24 = PublishImageContent.f218213m;
                ((a.d.C6547a) ((a.d) obj)).f218233b.invoke();
                return;
            case InsecureNonceXChaCha20.NONCE_SIZE_IN_BYTES /* 24 */:
                ((ru.mts.biometry.sdk.feature.passport.navigation.c) obj).requireContext();
                z91.s sVar3 = z91.e.f443908b;
                if (sVar3 == null) {
                    throw new IllegalArgumentException("DI delegate is null");
                }
                sVar3.b().d(true);
                return;
            case AvailableCode.ERROR_ON_ACTIVITY_RESULT /* 25 */:
                int i25 = PublishImageError.f218236j;
                ((a.c) obj).f218252b.invoke();
                return;
            case AvailableCode.ERROR_NO_ACTIVITY /* 26 */:
                int i26 = PublishImageError.f218236j;
                ((a.d) obj).f218253a.invoke();
                return;
            case AvailableCode.USER_IGNORE_PREVIOUS_POPUP /* 27 */:
                ((com.avito.android.advert.badge_details.s) obj).f68818f.accept(G0.f406611a);
                return;
            case AvailableCode.APP_IS_BACKGROUND_OR_LOCKED /* 28 */:
                ((com.avito.android.advert.cpo_program.e) obj).f68872a.invoke();
                return;
            default:
                ((com.avito.android.advert.item.abuse.d) obj).f72524b.d2();
                return;
        }
    }
}
