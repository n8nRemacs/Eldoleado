package a90;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.camera.video.internal.audio.q;
import androidx.recyclerview.widget.RecyclerView;
import bY.InterfaceC25585b;
import com.avito.android.R;
import com.avito.android.ai_assistant.adapter.message_actions.g;
import com.avito.android.avito_map.AvitoMap;
import com.avito.android.avito_map.AvitoMapPoint;
import com.avito.android.bbl.screens.configure.BblConfigureFragment;
import com.avito.android.checkout.CheckoutFragment;
import com.avito.android.component.advert_contact_bar.A;
import com.avito.android.cpt.activation.CptActivationFragment;
import com.avito.android.cpt.mass_activation.CptMassActivationDialogFragment;
import com.avito.android.cpt.mass_activation.CptMassActivationDialogV2Fragment;
import com.avito.android.extended_profile_ui_components.search_input.ProfileSearchInputView;
import com.avito.android.item_map.view.ItemMapState;
import com.avito.android.item_map.view.V;
import com.avito.android.item_map.view.b0;
import com.avito.android.lf_levels.LfLevelsFragment;
import com.avito.android.lib.deprecated_design.bottom_sheet.BottomSheet;
import com.avito.android.lib.design.chips.Chips;
import com.avito.android.lib.design.input.Input;
import com.avito.android.lib.design.picker.Picker;
import com.avito.android.lib.design.picker.i;
import com.avito.android.lib.design.tab_bar.TabBarLayout;
import com.avito.android.map.view.C31642f;
import com.avito.android.model_card.screen.ui.ModelCardFragment;
import com.avito.android.remote.model.GeoReference;
import com.avito.android.remote.model.seller_addresses.SellerAddressesInfo;
import com.avito.android.success.SuccessFragment;
import com.avito.android.tariff.cpx.configure.levels.TariffCpxConfigureLevelsFragment;
import com.avito.android.tariff.cpx.levels.CpxLevelsFragment;
import com.avito.android.tariff_cpt.configure.forbidden.CptForbiddenFragment;
import com.avito.android.tariff_cpt.configure.levels.TariffCptConfigureLevelsFragment;
import com.avito.android.tariff_cpt.levels.TariffCptLevelsFragment;
import com.avito.android.tariff_lf_constructor.configure.locations.ConstructorConfigureLocationsFragment;
import com.avito.android.tariff_lf_constructor.configure.subcategories.ConstructorConfigureSubCategoryFragment;
import com.avito.android.tariff_lf_publication.count.TariffLfPublicationCountFragment;
import com.avito.android.tariff_lf_publication.region.TariffLfPublicationRegionFragment;
import com.avito.android.trx_promo_goods.screens.configure.TrxPromoGoodsConfigureFragment;
import com.avito.android.trx_promo_goods.screens.status.TrxPromoGoodsStatusFragment;
import com.avito.android.user_adverts.tab_actions.host.UserAdvertsActionsBottomSheetView;
import com.avito.android.util.D6;
import com.avito.android.util.O2;
import com.avito.android.util.y6;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.f;
import com.google.crypto.tink.aead.internal.InsecureNonceXChaCha20;
import com.huawei.hms.adapter.internal.AvailableCode;
import java.util.List;
import kC0.C42568a;
import kotlin.collections.C42745f0;
import kotlin.jvm.internal.L;
import kotlin.reflect.n;
import qK0.C47312b;
import xZ.C49899a;

/* compiled from: R8$$SyntheticClass */
/* renamed from: a90.a, reason: case insensitive filesystem */
/* loaded from: classes15.dex */
public final /* synthetic */ class ViewOnLayoutChangeListenerC19701a implements View.OnLayoutChangeListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f20757b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f20758c;

    public /* synthetic */ ViewOnLayoutChangeListenerC19701a(Object obj, int i12) {
        this.f20757b = i12;
        this.f20758c = obj;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19) throws Resources.NotFoundException {
        f fVar;
        int i22;
        int i23;
        int i24;
        Integer numValueOf;
        AvitoMap avitoMap;
        SellerAddressesInfo sellerAddressesInfo;
        SellerAddressesInfo sellerAddressesInfo2;
        SellerAddressesInfo sellerAddressesInfo3;
        SellerAddressesInfo sellerAddressesInfo4;
        SellerAddressesInfo sellerAddressesInfo5;
        SellerAddressesInfo sellerAddressesInfo6;
        SellerAddressesInfo sellerAddressesInfo7;
        AvitoMap avitoMap2;
        List<GeoReference> list;
        int i25 = 6;
        Object obj = this.f20758c;
        switch (this.f20757b) {
            case 0:
                int i26 = i15 - i13;
                if (i26 != i19 - i17) {
                    int id2 = view.getId();
                    b bVar = (b) obj;
                    int id3 = bVar.f20760b.getId();
                    RecyclerView recyclerView = bVar.f20761c;
                    if (id2 != id3) {
                        if (id2 == bVar.f20762d.getId()) {
                            recyclerView.setPaddingRelative(recyclerView.getPaddingStart(), recyclerView.getPaddingTop(), recyclerView.getPaddingEnd(), i26);
                            break;
                        }
                    } else {
                        recyclerView.setPaddingRelative(recyclerView.getPaddingStart(), i26, recyclerView.getPaddingEnd(), recyclerView.getPaddingBottom());
                        break;
                    }
                }
                break;
            case 1:
                int i27 = 0;
                if (i15 - i13 == i19 - i13) {
                    int i28 = com.avito.android.ai_assistant.adapter.message_actions.f.f88821s;
                    break;
                } else {
                    com.avito.android.ai_assistant.adapter.message_actions.f fVar2 = (com.avito.android.ai_assistant.adapter.message_actions.f) obj;
                    FlexboxLayout flexboxLayout = fVar2.f88823c;
                    if (!flexboxLayout.getFlexLines().isEmpty()) {
                        ViewGroup viewGroup = fVar2.f88831k;
                        if (viewGroup.getVisibility() == 0 && (fVar = (f) C42745f0.G(flexboxLayout.getFlexLines())) != null) {
                            int iA2 = fVar.a();
                            int i29 = fVar2.f88832l;
                            if (iA2 == 1 && L.f(flexboxLayout.getChildAt(fVar.f348511o), viewGroup)) {
                                int paddingTop = fVar2.f88833m - flexboxLayout.getPaddingTop();
                                if (paddingTop < 0) {
                                    paddingTop = 0;
                                }
                                int paddingStart = i29 - flexboxLayout.getPaddingStart();
                                if (paddingStart < 0) {
                                    paddingStart = 0;
                                }
                                int paddingEnd = i29 - flexboxLayout.getPaddingEnd();
                                i27 = paddingStart;
                                i23 = paddingTop;
                                i22 = paddingEnd < 0 ? 0 : paddingEnd;
                            } else if (L.f(flexboxLayout.getChildAt(fVar.f348511o), viewGroup)) {
                                int paddingStart2 = i29 - flexboxLayout.getPaddingStart();
                                if (paddingStart2 < 0) {
                                    paddingStart2 = 0;
                                }
                                i23 = 0;
                                i27 = paddingStart2;
                                i22 = 0;
                            } else {
                                i22 = 0;
                                i23 = 0;
                            }
                            if (i23 != viewGroup.getPaddingTop() || i27 != viewGroup.getPaddingStart() || i22 != viewGroup.getPaddingEnd()) {
                                viewGroup.setPaddingRelative(i27, i23, i22, viewGroup.getPaddingBottom());
                                flexboxLayout.removeCallbacks(fVar2.f88838r);
                                g gVar = new g(fVar2);
                                flexboxLayout.postDelayed(gVar, 100L);
                                fVar2.f88838r = gVar;
                                break;
                            }
                        }
                    }
                }
                break;
            case 2:
                BblConfigureFragment.a aVar = BblConfigureFragment.f99091E0;
                if (view.getHeight() > 0) {
                    BblConfigureFragment bblConfigureFragment = (BblConfigureFragment) obj;
                    C49899a c49899a = bblConfigureFragment.f99096D0;
                    if (c49899a != null) {
                        bblConfigureFragment.u5().r0(c49899a);
                    }
                    C49899a c49899a2 = new C49899a(view.getHeight());
                    bblConfigureFragment.u5().l(c49899a2, -1);
                    bblConfigureFragment.f99096D0 = c49899a2;
                    break;
                }
                break;
            case 3:
                D6.f(((com.avito.android.beduin.ui.universal.view.d) obj).f104570n, 0, 0, 0, i15 - i13, 7);
                break;
            case 4:
                CheckoutFragment checkoutFragment = (CheckoutFragment) obj;
                RecyclerView recyclerView2 = checkoutFragment.f118108A0;
                if (recyclerView2 != null && view.getHeight() > 0) {
                    C49899a c49899a3 = checkoutFragment.f118117u0;
                    if (c49899a3 != null) {
                        recyclerView2.r0(c49899a3);
                    }
                    C49899a c49899a4 = new C49899a(view.getHeight());
                    recyclerView2.l(c49899a4, -1);
                    checkoutFragment.f118117u0 = c49899a4;
                    break;
                }
                break;
            case 5:
                A a12 = (A) obj;
                Boolean bool = a12.f125030m;
                if (bool != null) {
                    boolean zBooleanValue = bool.booleanValue();
                    View view2 = a12.f125018a;
                    float height = zBooleanValue ? 0.0f : view2.getHeight() + ((ViewGroup.MarginLayoutParams) view2.getLayoutParams()).bottomMargin;
                    if (height != view2.getTranslationY()) {
                        view2.setTranslationY(height);
                        break;
                    }
                }
                break;
            case 6:
                CptActivationFragment.a aVar2 = CptActivationFragment.f126198B0;
                if (view.getHeight() > 0) {
                    CptActivationFragment cptActivationFragment = (CptActivationFragment) obj;
                    Pr.c cVar = cptActivationFragment.f126199A0;
                    if (cVar != null) {
                        RecyclerView recyclerView3 = cptActivationFragment.f126209w0;
                        if (recyclerView3 == null) {
                            recyclerView3 = null;
                        }
                        recyclerView3.r0(cVar);
                    }
                    Pr.c cVar2 = new Pr.c(view.getHeight());
                    RecyclerView recyclerView4 = cptActivationFragment.f126209w0;
                    (recyclerView4 == null ? null : recyclerView4).l(cVar2, -1);
                    cptActivationFragment.f126199A0 = cVar2;
                    break;
                }
                break;
            case 7:
                CptMassActivationDialogFragment.a aVar3 = CptMassActivationDialogFragment.f126412x0;
                if (view.getHeight() > 0) {
                    int height2 = view.getHeight();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                    int i32 = height2 + (marginLayoutParams != null ? marginLayoutParams.topMargin : 0);
                    CptMassActivationDialogFragment cptMassActivationDialogFragment = (CptMassActivationDialogFragment) obj;
                    Pr.c cVar3 = cptMassActivationDialogFragment.f126427v0;
                    if (cVar3 != null) {
                        RecyclerView recyclerView5 = cptMassActivationDialogFragment.f126421p0;
                        if (recyclerView5 == null) {
                            recyclerView5 = null;
                        }
                        recyclerView5.r0(cVar3);
                    }
                    Pr.c cVar4 = new Pr.c(i32);
                    RecyclerView recyclerView6 = cptMassActivationDialogFragment.f126421p0;
                    (recyclerView6 == null ? null : recyclerView6).l(cVar4, -1);
                    cptMassActivationDialogFragment.f126427v0 = cVar4;
                    break;
                }
                break;
            case 8:
                CptMassActivationDialogV2Fragment.a aVar4 = CptMassActivationDialogV2Fragment.f126446A0;
                if (view.getHeight() > 0) {
                    int height3 = view.getHeight();
                    ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
                    int i33 = height3 + (marginLayoutParams2 != null ? marginLayoutParams2.topMargin : 0);
                    CptMassActivationDialogV2Fragment cptMassActivationDialogV2Fragment = (CptMassActivationDialogV2Fragment) obj;
                    Pr.c cVar5 = cptMassActivationDialogV2Fragment.f126465z0;
                    if (cVar5 != null) {
                        RecyclerView recyclerView7 = cptMassActivationDialogV2Fragment.f126456q0;
                        if (recyclerView7 == null) {
                            recyclerView7 = null;
                        }
                        recyclerView7.r0(cVar5);
                    }
                    Pr.c cVar6 = new Pr.c(i33);
                    RecyclerView recyclerView8 = cptMassActivationDialogV2Fragment.f126456q0;
                    (recyclerView8 == null ? null : recyclerView8).l(cVar6, -1);
                    cptMassActivationDialogV2Fragment.f126465z0 = cVar6;
                    break;
                }
                break;
            case 9:
                int i34 = i15 - i13;
                if (i19 - i17 == i34) {
                    int i35 = com.avito.android.evidence_request.mvi.evidence_details.g.f148989n;
                    break;
                } else {
                    ((com.avito.android.evidence_request.mvi.evidence_details.g) obj).f148996g.setPadding(0, 0, 0, i34);
                    break;
                }
            case 10:
                int i36 = ProfileSearchInputView.f153380D;
                if (view.getWidth() != i18 - i16) {
                    int iB2 = y6.b(12) + view.getWidth();
                    ProfileSearchInputView profileSearchInputView = (ProfileSearchInputView) obj;
                    Input.j(profileSearchInputView.f153390h, 0, iB2, 11);
                    Input.e(profileSearchInputView.f153390h, 0, 0, iB2, 0, 0, 27);
                    profileSearchInputView.post(new q(iB2, i25, profileSearchInputView));
                    break;
                }
                break;
            case 11:
                V v12 = (V) obj;
                View view3 = v12.f173567b;
                int dimensionPixelSize = view3.getResources().getDimensionPixelSize(R.dimen.map_bottom_sheet_recycler_view_margin);
                int dimensionPixelSize2 = view3.getResources().getDimensionPixelSize(R.dimen.map_bottom_sheet_recycler_view_margin_top) + dimensionPixelSize + dimensionPixelSize;
                LinearLayout linearLayout = v12.f173549E;
                boolean zY2 = D6.y(linearLayout);
                BottomSheet bottomSheet = v12.f173547C;
                RecyclerView recyclerView9 = v12.f173551G;
                if (!zY2) {
                    dimensionPixelSize2 += dimensionPixelSize + 30;
                    D6.c(recyclerView9, null, null, null, Integer.valueOf(y6.b(30)), 7);
                    bottomSheet.s2(new BottomSheet.c.a(dimensionPixelSize2));
                }
                boolean z12 = v12.f173561Q;
                if (z12) {
                    int dimensionPixelSize3 = view3.getResources().getDimensionPixelSize(R.dimen.amenity_button_height) + view3.getResources().getDimensionPixelSize(R.dimen.amenity_button_margin_top) + dimensionPixelSize2;
                    View childAt = recyclerView9.getChildAt(1);
                    int height4 = childAt != null ? childAt.getHeight() : 0;
                    dimensionPixelSize2 = height4 > 0 ? view3.getResources().getDimensionPixelSize(R.dimen.item_address_margin_top) + height4 + dimensionPixelSize3 : dimensionPixelSize3;
                    i24 = 2;
                } else {
                    i24 = 0;
                }
                ItemMapState itemMapState = v12.f173579n;
                boolean z13 = itemMapState.f173446s;
                ItemMapState.BottomSheetState bottomSheetState = itemMapState.f173437j;
                if (!z13) {
                    int size = (bottomSheetState == null || (list = bottomSheetState.f173456c) == null) ? 0 : list.size();
                    if (size > 0) {
                        int dimensionPixelSize4 = view3.getResources().getDimensionPixelSize(R.dimen.item_geo_reference_margin_top);
                        int i37 = i24;
                        while (i37 < size + i24) {
                            View childAt2 = recyclerView9.getChildAt(i37);
                            int height5 = childAt2 != null ? childAt2.getHeight() : 0;
                            if (height5 > 0) {
                                dimensionPixelSize2 = height5 + dimensionPixelSize4 + dimensionPixelSize2;
                            }
                            i37++;
                        }
                        i24 = i37;
                    }
                }
                if (!z12) {
                    View childAt3 = recyclerView9.getChildAt(i24);
                    int height6 = childAt3 != null ? childAt3.getHeight() : 0;
                    if (height6 > 0) {
                        dimensionPixelSize2 += view3.getResources().getDimensionPixelSize(R.dimen.item_address_margin_top) + height6;
                    }
                    i24++;
                }
                AvitoMapPoint avitoMapPoint = itemMapState.f173430c;
                AvitoMapPoint avitoMapPoint2 = itemMapState.f173439l;
                if (avitoMapPoint2 == null && avitoMapPoint != null && (avitoMap2 = v12.f173582q) != null) {
                    v12.d(avitoMap2, avitoMapPoint);
                }
                if (O2.a((bottomSheetState == null || (sellerAddressesInfo7 = bottomSheetState.f173457d) == null) ? null : sellerAddressesInfo7.getWorkingHours())) {
                    int paddingTop2 = D6.y(linearLayout) ? linearLayout.getPaddingTop() : 0;
                    float height7 = ((dimensionPixelSize2 + (recyclerView9.getChildAt(i24) != null ? r17.getHeight() : 0)) + paddingTop2) / D6.r(bottomSheet.getF177843b());
                    boolean zA2 = O2.a((bottomSheetState == null || (sellerAddressesInfo6 = bottomSheetState.f173457d) == null) ? null : sellerAddressesInfo6.getDetails());
                    boolean z14 = ((bottomSheetState == null || (sellerAddressesInfo5 = bottomSheetState.f173457d) == null) ? null : sellerAddressesInfo5.getAdditionalInfo()) != null;
                    if (zA2 || z14) {
                        v12.l(bottomSheet, 1.3f * height7);
                        v12.f173563S = Float.valueOf(height7 * 1.0f);
                    }
                    bottomSheet.t2(BottomSheet.NotchVisibility.f177827c);
                    numValueOf = Integer.valueOf(dimensionPixelSize2);
                    dimensionPixelSize2 += y6.b(65);
                    i24++;
                } else {
                    numValueOf = null;
                }
                if (O2.a((bottomSheetState == null || (sellerAddressesInfo4 = bottomSheetState.f173457d) == null) ? null : sellerAddressesInfo4.getDetails())) {
                    View childAt4 = recyclerView9.getChildAt(i24);
                    if (((bottomSheetState == null || (sellerAddressesInfo3 = bottomSheetState.f173457d) == null) ? null : sellerAddressesInfo3.getAdditionalInfo()) == null) {
                        D6.c(childAt4, null, null, null, Integer.valueOf(y6.b(16)), 7);
                    }
                    if (!O2.a((bottomSheetState == null || (sellerAddressesInfo2 = bottomSheetState.f173457d) == null) ? null : sellerAddressesInfo2.getWorkingHours())) {
                        numValueOf = Integer.valueOf(dimensionPixelSize2);
                        dimensionPixelSize2 += y6.b(65);
                    }
                    i24++;
                }
                if (((bottomSheetState == null || (sellerAddressesInfo = bottomSheetState.f173457d) == null) ? null : sellerAddressesInfo.getAdditionalInfo()) != null) {
                    D6.c(recyclerView9.getChildAt(i24), null, null, null, Integer.valueOf(y6.b(16)), 7);
                    boolean zA3 = O2.a(bottomSheetState.f173457d.getWorkingHours());
                    boolean zA4 = O2.a(bottomSheetState.f173457d.getDetails());
                    if (!zA3 && !zA4) {
                        numValueOf = Integer.valueOf(dimensionPixelSize2);
                        dimensionPixelSize2 += y6.b(65);
                    }
                }
                if (itemMapState.f173451x != null) {
                    dimensionPixelSize2 += view3.getResources().getDimensionPixelSize(R.dimen.item_geo_zone_departure_margin_bottom);
                }
                if (z12) {
                    int paddingTop3 = D6.y(linearLayout) ? linearLayout.getPaddingTop() : 0;
                    int iMax = Math.max(b0.f173607b + paddingTop3, dimensionPixelSize2 + b0.f173606a + paddingTop3);
                    int iR2 = D6.r(bottomSheet.getF177843b());
                    v12.l(bottomSheet, iMax < iR2 ? iMax / iR2 : 0.7f);
                    if (avitoMapPoint2 == null && avitoMapPoint != null && (avitoMap = v12.f173582q) != null) {
                        v12.d(avitoMap, avitoMapPoint);
                    }
                } else {
                    if (numValueOf != null) {
                        v12.f173562R = numValueOf;
                    }
                    bottomSheet.s2(new BottomSheet.c.a(dimensionPixelSize2));
                }
                recyclerView9.removeOnLayoutChangeListener(v12.f173548D);
                break;
            case 12:
                LfLevelsFragment lfLevelsFragment = (LfLevelsFragment) obj;
                RecyclerView recyclerView10 = lfLevelsFragment.f175302r0;
                if (recyclerView10 != null && view.getHeight() > 0) {
                    AR.a aVar5 = lfLevelsFragment.f175298n0;
                    if (aVar5 != null) {
                        recyclerView10.r0(aVar5);
                    }
                    AR.a aVar6 = new AR.a(view.getHeight());
                    recyclerView10.l(aVar6, -1);
                    lfLevelsFragment.f175298n0 = aVar6;
                    break;
                }
                break;
            case 13:
                Drawable drawable = ((Chips) obj).f178674J.get(view);
                if (drawable != null) {
                    drawable.setBounds(0, 0, view.getWidth(), view.getHeight());
                    break;
                }
                break;
            case 14:
                int i38 = Picker.f179977g;
                int i39 = i14 - i12;
                i iVar = (i) obj;
                if (i39 > iVar.getMinimumWidth()) {
                    iVar.setMinimumWidth(i39);
                    break;
                }
                break;
            case 15:
                if (i13 != i17 || i15 != i19) {
                    ((C31642f) obj).f185504e.invoke(new InterfaceC25585b.D(i13, i15));
                    break;
                }
            case 16:
                ModelCardFragment.a aVar7 = ModelCardFragment.f198116H0;
                n<Object> nVar = ModelCardFragment.f198117I0[3];
                D6.f((RecyclerView) ((ModelCardFragment) obj).f198118A0.a(), 0, 0, 0, i15 - i13, 7);
                break;
            case 17:
                SuccessFragment.a aVar8 = SuccessFragment.f291545y0;
                if (view.getHeight() > 0) {
                    SuccessFragment successFragment = (SuccessFragment) obj;
                    PV.a aVar9 = successFragment.f291556x0;
                    if (aVar9 != null) {
                        RecyclerView recyclerView11 = successFragment.f291554v0;
                        if (recyclerView11 == null) {
                            recyclerView11 = null;
                        }
                        recyclerView11.r0(aVar9);
                    }
                    PV.a aVar10 = new PV.a(y6.b(0), view.getHeight(), 0, 0, 12, null);
                    RecyclerView recyclerView12 = successFragment.f291554v0;
                    (recyclerView12 == null ? null : recyclerView12).l(aVar10, -1);
                    successFragment.f291556x0 = aVar10;
                    break;
                }
                break;
            case 18:
                TariffCpxConfigureLevelsFragment.a aVar11 = TariffCpxConfigureLevelsFragment.f296048E0;
                if (view.getHeight() > 0) {
                    TariffCpxConfigureLevelsFragment tariffCpxConfigureLevelsFragment = (TariffCpxConfigureLevelsFragment) obj;
                    C49899a c49899a5 = tariffCpxConfigureLevelsFragment.f296053D0;
                    if (c49899a5 != null) {
                        tariffCpxConfigureLevelsFragment.s5().r0(c49899a5);
                    }
                    C49899a c49899a6 = new C49899a(view.getHeight());
                    tariffCpxConfigureLevelsFragment.s5().l(c49899a6, -1);
                    tariffCpxConfigureLevelsFragment.f296053D0 = c49899a6;
                    break;
                }
                break;
            case 19:
                CpxLevelsFragment cpxLevelsFragment = (CpxLevelsFragment) obj;
                RecyclerView recyclerView13 = cpxLevelsFragment.f297017r0;
                if (recyclerView13 != null && view.getHeight() > 0) {
                    C49899a c49899a7 = cpxLevelsFragment.f297011l0;
                    if (c49899a7 != null) {
                        recyclerView13.r0(c49899a7);
                    }
                    C49899a c49899a8 = new C49899a(view.getHeight());
                    recyclerView13.l(c49899a8, -1);
                    cpxLevelsFragment.f297011l0 = c49899a8;
                    break;
                }
                break;
            case 20:
                ViewGroup viewGroup2 = ((CptForbiddenFragment) obj).f297596x0;
                (viewGroup2 == null ? null : viewGroup2).setPadding(0, 0, 0, view.getHeight());
                break;
            case 21:
                TariffCptConfigureLevelsFragment.a aVar12 = TariffCptConfigureLevelsFragment.f297795A0;
                if (view.getHeight() > 0) {
                    TariffCptConfigureLevelsFragment tariffCptConfigureLevelsFragment = (TariffCptConfigureLevelsFragment) obj;
                    C42568a c42568a = tariffCptConfigureLevelsFragment.f297809z0;
                    C47312b c47312b = tariffCptConfigureLevelsFragment.f297808y0;
                    if (c42568a != null) {
                        n<Object> nVar2 = TariffCptConfigureLevelsFragment.f297796B0[6];
                        ((RecyclerView) c47312b.a()).r0(c42568a);
                    }
                    C42568a c42568a2 = new C42568a(view.getHeight());
                    n<Object> nVar3 = TariffCptConfigureLevelsFragment.f297796B0[6];
                    ((RecyclerView) c47312b.a()).l(c42568a2, -1);
                    tariffCptConfigureLevelsFragment.f297809z0 = c42568a2;
                    break;
                }
                break;
            case 22:
                TariffCptLevelsFragment tariffCptLevelsFragment = (TariffCptLevelsFragment) obj;
                RecyclerView recyclerView14 = tariffCptLevelsFragment.f298515r0;
                if (recyclerView14 != null && view.getHeight() > 0) {
                    C42568a c42568a3 = tariffCptLevelsFragment.f298511n0;
                    if (c42568a3 != null) {
                        recyclerView14.r0(c42568a3);
                    }
                    C42568a c42568a4 = new C42568a(view.getHeight());
                    recyclerView14.l(c42568a4, -1);
                    tariffCptLevelsFragment.f298511n0 = c42568a4;
                    break;
                }
                break;
            case 23:
                ConstructorConfigureLocationsFragment.a aVar13 = ConstructorConfigureLocationsFragment.f299851F0;
                if (view.getHeight() > 0) {
                    ConstructorConfigureLocationsFragment constructorConfigureLocationsFragment = (ConstructorConfigureLocationsFragment) obj;
                    C49899a c49899a9 = constructorConfigureLocationsFragment.f299857E0;
                    if (c49899a9 != null) {
                        constructorConfigureLocationsFragment.r5().r0(c49899a9);
                    }
                    C49899a c49899a10 = new C49899a(view.getHeight());
                    constructorConfigureLocationsFragment.r5().l(c49899a10, -1);
                    constructorConfigureLocationsFragment.f299857E0 = c49899a10;
                    break;
                }
                break;
            case InsecureNonceXChaCha20.NONCE_SIZE_IN_BYTES /* 24 */:
                ConstructorConfigureSubCategoryFragment.a aVar14 = ConstructorConfigureSubCategoryFragment.f300415C0;
                if (view.getHeight() > 0) {
                    ConstructorConfigureSubCategoryFragment constructorConfigureSubCategoryFragment = (ConstructorConfigureSubCategoryFragment) obj;
                    C49899a c49899a11 = constructorConfigureSubCategoryFragment.f300418B0;
                    C47312b c47312b2 = constructorConfigureSubCategoryFragment.f300424s0;
                    if (c49899a11 != null) {
                        n<Object> nVar4 = ConstructorConfigureSubCategoryFragment.f300416D0[0];
                        ((RecyclerView) c47312b2.a()).r0(c49899a11);
                    }
                    C49899a c49899a12 = new C49899a(view.getHeight());
                    n<Object> nVar5 = ConstructorConfigureSubCategoryFragment.f300416D0[0];
                    ((RecyclerView) c47312b2.a()).l(c49899a12, -1);
                    constructorConfigureSubCategoryFragment.f300418B0 = c49899a12;
                    break;
                }
                break;
            case AvailableCode.ERROR_ON_ACTIVITY_RESULT /* 25 */:
                TariffLfPublicationCountFragment.a aVar15 = TariffLfPublicationCountFragment.f300720G0;
                if (view.getHeight() > 0) {
                    TariffLfPublicationCountFragment tariffLfPublicationCountFragment = (TariffLfPublicationCountFragment) obj;
                    C49899a c49899a13 = tariffLfPublicationCountFragment.f300726F0;
                    if (c49899a13 != null) {
                        RecyclerView recyclerView15 = tariffLfPublicationCountFragment.f300736w0;
                        if (recyclerView15 == null) {
                            recyclerView15 = null;
                        }
                        recyclerView15.r0(c49899a13);
                    }
                    C49899a c49899a14 = new C49899a(view.getHeight());
                    RecyclerView recyclerView16 = tariffLfPublicationCountFragment.f300736w0;
                    (recyclerView16 == null ? null : recyclerView16).l(c49899a14, -1);
                    tariffLfPublicationCountFragment.f300726F0 = c49899a14;
                    break;
                }
                break;
            case AvailableCode.ERROR_NO_ACTIVITY /* 26 */:
                TariffLfPublicationRegionFragment.a aVar16 = TariffLfPublicationRegionFragment.f301018A0;
                if (view.getHeight() > 0) {
                    TariffLfPublicationRegionFragment tariffLfPublicationRegionFragment = (TariffLfPublicationRegionFragment) obj;
                    C49899a c49899a15 = tariffLfPublicationRegionFragment.f301031z0;
                    if (c49899a15 != null) {
                        RecyclerView recyclerView17 = tariffLfPublicationRegionFragment.f301028w0;
                        if (recyclerView17 == null) {
                            recyclerView17 = null;
                        }
                        recyclerView17.r0(c49899a15);
                    }
                    C49899a c49899a16 = new C49899a(view.getHeight());
                    RecyclerView recyclerView18 = tariffLfPublicationRegionFragment.f301028w0;
                    (recyclerView18 == null ? null : recyclerView18).l(c49899a16, -1);
                    tariffLfPublicationRegionFragment.f301031z0 = c49899a16;
                    break;
                }
                break;
            case AvailableCode.USER_IGNORE_PREVIOUS_POPUP /* 27 */:
                TrxPromoGoodsConfigureFragment.a aVar17 = TrxPromoGoodsConfigureFragment.f303328I0;
                if (view.getHeight() > 0) {
                    TrxPromoGoodsConfigureFragment trxPromoGoodsConfigureFragment = (TrxPromoGoodsConfigureFragment) obj;
                    C49899a c49899a17 = trxPromoGoodsConfigureFragment.f303336H0;
                    if (c49899a17 != null) {
                        RecyclerView recyclerView19 = trxPromoGoodsConfigureFragment.f303349z0;
                        if (recyclerView19 == null) {
                            recyclerView19 = null;
                        }
                        recyclerView19.r0(c49899a17);
                    }
                    C49899a c49899a18 = new C49899a(view.getHeight());
                    RecyclerView recyclerView20 = trxPromoGoodsConfigureFragment.f303349z0;
                    (recyclerView20 == null ? null : recyclerView20).l(c49899a18, -1);
                    trxPromoGoodsConfigureFragment.f303336H0 = c49899a18;
                    break;
                }
                break;
            case AvailableCode.APP_IS_BACKGROUND_OR_LOCKED /* 28 */:
                TrxPromoGoodsStatusFragment.a aVar18 = TrxPromoGoodsStatusFragment.f303747B0;
                ((ViewGroup) obj).setPadding(0, 0, 0, view.getHeight());
                break;
            default:
                TabBarLayout.a aVar19 = TabBarLayout.f180679h;
                UserAdvertsActionsBottomSheetView userAdvertsActionsBottomSheetView = (UserAdvertsActionsBottomSheetView) obj;
                Context context = userAdvertsActionsBottomSheetView.getContext();
                aVar19.getClass();
                userAdvertsActionsBottomSheetView.setPeekHeight(context.getResources().getDimensionPixelSize(R.dimen.tab_bar_content_behind_padding_bottom) + (i15 - i13));
                break;
        }
    }
}
