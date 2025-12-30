package Ai;

import AA.a;
import Md.InterfaceC14470a;
import Ob0.C14669b;
import Qb0.InterfaceC14878a;
import RA.a;
import Xc0.InterfaceC16989a;
import Y41.l;
import android.content.DialogInterface;
import androidx.core.os.C22777e;
import androidx.fragment.app.ActivityC22955m;
import androidx.fragment.app.FragmentManager;
import com.avito.android.advert.badge_details.s;
import com.avito.android.advert.item.creditinfo.buzzoola.r;
import com.avito.android.async_phone.impl_module.phone_request_link.confirmation_dialog_fragment.PhoneRequestConfirmationDialogFragment;
import com.avito.android.auction.details.AuctionDetailsSheetActivity;
import com.avito.android.auction.details.q;
import com.avito.android.authorization.auth.AuthFragment;
import com.avito.android.auto_select.confirmation_dialog.AutoSelectConfirmationBottomSheetV2;
import com.avito.android.autoteka.presentation.choosingPurchase.f;
import com.avito.android.beduin.ui.universal.UniversalBeduinFragment;
import com.avito.android.beduin.ui.universal.m;
import com.avito.android.calltracking.CalltrackingFragment;
import com.avito.android.calltracking.InterfaceC29279g;
import com.avito.android.extended_profile_map.w;
import com.avito.android.iac_problems.impl_module.miui_permission.IacMiuiPermissionBottomSheetDialogResult;
import com.avito.android.iac_problems.impl_module.miui_permission.IacMiuiPermissionBottomSheetFragment;
import com.avito.android.iac_problems.impl_module.problems_bottom_sheet.IacProblemBottomSheetDialogResult;
import com.avito.android.iac_problems.impl_module.problems_bottom_sheet.IacProblemsBottomSheetFragment;
import com.avito.android.item_map.view.V;
import com.avito.android.login_suggests_impl.LoginSuggestsActivity;
import com.avito.android.messenger.conversation.mvi.send.Q0;
import com.avito.android.photo_gallery.A;
import com.avito.android.publish.category_edit.CategoryEditSheet;
import com.avito.android.publish.start_publish.DialogC34131f;
import com.avito.android.publish.start_publish.DialogC34133h;
import com.google.crypto.tink.aead.internal.InsecureNonceXChaCha20;
import com.huawei.hms.adapter.internal.AvailableCode;
import iR.C41336a;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.observers.y;
import kotlin.G0;
import kotlin.jvm.internal.N;

/* compiled from: R8$$SyntheticClass */
/* renamed from: Ai.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final /* synthetic */ class DialogInterfaceOnDismissListenerC13035a implements DialogInterface.OnDismissListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f584b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f585c;

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ DialogInterfaceOnDismissListenerC13035a(l lVar) {
        this.f584b = 22;
        this.f585c = (N) lVar;
    }

    /* JADX WARN: Type inference failed for: r1v24, types: [Y41.l, kotlin.jvm.internal.N] */
    /* JADX WARN: Type inference failed for: r4v27, types: [Y41.a, kotlin.jvm.internal.N] */
    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Object obj = this.f585c;
        switch (this.f584b) {
            case 0:
                ((C13036b) obj).f587b.finish();
                break;
            case 1:
                C14669b c14669b = (C14669b) obj;
                c14669b.f12407c.invoke(InterfaceC14878a.C0913a.f13863b);
                c14669b.f12408d = null;
                c14669b.f12409e = null;
                c14669b.f12410f = null;
                c14669b.f12411g = null;
                break;
            case 2:
                QA.c cVar = (QA.c) obj;
                cVar.f13589c = null;
                cVar.f13590d = null;
                cVar.f13591e = null;
                cVar.f13588b.invoke(a.e.f14167a);
                break;
            case 3:
                ((s) obj).f68817e.accept(G0.f406611a);
                break;
            case 4:
                com.avito.android.advert.item.creditinfo.buzzoola.d dVar = ((r) obj).f74984m;
                if (dVar != null) {
                    dVar.Md();
                    break;
                }
                break;
            case 5:
                ((com.avito.android.advert.item.fair_price.dialog.e) obj).f75389b = null;
                break;
            case 6:
                ((com.avito.android.advert.item.ownership_cost.dialogs.b) obj).f77885b.e();
                break;
            case 7:
                com.avito.android.advert.item.ownership_cost.dialogs.region_select.b bVar = (com.avito.android.advert.item.ownership_cost.dialogs.region_select.b) obj;
                y yVar = bVar.f77906e;
                if (yVar != null) {
                    DisposableHelper.a(yVar);
                }
                bVar.f77902a.a();
                break;
            case 8:
                PhoneRequestConfirmationDialogFragment.a aVar = PhoneRequestConfirmationDialogFragment.f92153j0;
                C41336a.C11375a.a();
                PhoneRequestConfirmationDialogFragment phoneRequestConfirmationDialogFragment = (PhoneRequestConfirmationDialogFragment) obj;
                FragmentManager parentFragmentManager = phoneRequestConfirmationDialogFragment.getParentFragmentManager();
                String tag = phoneRequestConfirmationDialogFragment.getTag();
                if (tag == null) {
                    tag = "";
                }
                parentFragmentManager.o0(C22777e.a(), tag);
                break;
            case 9:
                int i12 = AuctionDetailsSheetActivity.f92320s;
                ((q) ((AuctionDetailsSheetActivity) obj).f92322n.getValue()).accept(InterfaceC14470a.c.f10913a);
                break;
            case 10:
                AuthFragment.a aVar2 = AuthFragment.f92815O0;
                ((AuthFragment) obj).D5();
                break;
            case 11:
                AutoSelectConfirmationBottomSheetV2.a aVar3 = AutoSelectConfirmationBottomSheetV2.f95869m0;
                AutoSelectConfirmationBottomSheetV2 autoSelectConfirmationBottomSheetV2 = (AutoSelectConfirmationBottomSheetV2) obj;
                autoSelectConfirmationBottomSheetV2.f5(autoSelectConfirmationBottomSheetV2.g5().f95832c.f95923e);
                break;
            case 12:
                ((f) obj).f97115d.invoke();
                break;
            case 13:
                UniversalBeduinFragment.a aVar4 = UniversalBeduinFragment.f104416B0;
                UniversalBeduinFragment universalBeduinFragment = (UniversalBeduinFragment) obj;
                m mVar = universalBeduinFragment.f104418n0;
                m mVar2 = mVar != null ? mVar : null;
                com.avito.android.beduin_shared.model.utils.a.a(mVar2.f104497N, mVar2.f104502S);
                ActivityC22955m activityC22955mL1 = universalBeduinFragment.l1();
                if (activityC22955mL1 != null) {
                    activityC22955mL1.finish();
                    break;
                }
                break;
            case 14:
                InterfaceC29279g interfaceC29279g = ((CalltrackingFragment) obj).f113558q0;
                (interfaceC29279g != null ? interfaceC29279g : null).clear();
                break;
            case 15:
                ((com.avito.android.credits.calculator.q) obj).f128710l = null;
                break;
            case 16:
                w wVar = (w) obj;
                wVar.f151337k = null;
                wVar.f151331e.invoke(a.f.f165a);
                break;
            case 17:
                com.avito.android.extended_profile_ui_components.d dVar2 = (com.avito.android.extended_profile_ui_components.d) obj;
                dVar2.f153326f = null;
                dVar2.f153327g = null;
                dVar2.f153328h = null;
                dVar2.f153329i = null;
                dVar2.f153330j = null;
                dVar2.f153331k = null;
                dVar2.f153332l = null;
                dVar2.f153333m = null;
                dVar2.f153325e.invoke();
                break;
            case 18:
                IacMiuiPermissionBottomSheetFragment.a aVar5 = IacMiuiPermissionBottomSheetFragment.f168779j0;
                ((IacMiuiPermissionBottomSheetFragment) obj).f5(IacMiuiPermissionBottomSheetDialogResult.Closed.f168777c);
                break;
            case 19:
                IacProblemsBottomSheetFragment.a aVar6 = IacProblemsBottomSheetFragment.f168849t0;
                ((IacProblemsBottomSheetFragment) obj).f5(IacProblemBottomSheetDialogResult.f168845b);
                break;
            case 20:
                ((com.avito.android.imv_services_dialog.b) obj).f170754G.invoke();
                break;
            case 21:
                ((V) obj).f173573h.Xa();
                C41336a.C11375a.a();
                break;
            case 22:
                ((N) obj).invoke(dialogInterface);
                break;
            case 23:
                int i13 = LoginSuggestsActivity.f182848s;
                ((LoginSuggestsActivity) obj).finish();
                break;
            case InsecureNonceXChaCha20.NONCE_SIZE_IN_BYTES /* 24 */:
                ((Q0) obj).f194356I.accept(G0.f406611a);
                break;
            case AvailableCode.ERROR_ON_ACTIVITY_RESULT /* 25 */:
                ((A) obj).f216469f.Xa();
                C41336a.C11375a.a();
                break;
            case AvailableCode.ERROR_NO_ACTIVITY /* 26 */:
                com.avito.android.profile_vk_linking.linked_group.a aVar7 = (com.avito.android.profile_vk_linking.linked_group.a) obj;
                aVar7.f231252b.invoke(InterfaceC16989a.c.f18959a);
                aVar7.f231253c = null;
                aVar7.f231254d = null;
                break;
            case AvailableCode.USER_IGNORE_PREVIOUS_POPUP /* 27 */:
                ((CategoryEditSheet) obj).f232333m0.dispose();
                break;
            case AvailableCode.APP_IS_BACKGROUND_OR_LOCKED /* 28 */:
                DialogC34131f dialogC34131f = (DialogC34131f) obj;
                dialogC34131f.f245411L.dispose();
                Y41.a<G0> aVar8 = dialogC34131f.f245404E;
                if (aVar8 != null) {
                    aVar8.invoke();
                    break;
                }
                break;
            default:
                DialogC34133h dialogC34133h = (DialogC34133h) obj;
                dialogC34133h.f245422L.dispose();
                Y41.a<G0> aVar9 = dialogC34133h.f245415E;
                if (aVar9 != null) {
                    aVar9.invoke();
                    break;
                }
                break;
        }
    }

    public /* synthetic */ DialogInterfaceOnDismissListenerC13035a(Object obj, int i12) {
        this.f584b = i12;
        this.f585c = obj;
    }
}
