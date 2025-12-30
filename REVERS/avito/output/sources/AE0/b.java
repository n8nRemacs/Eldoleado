package Ae0;

import Ef.C13481b;
import XO0.d;
import android.view.View;
import bz0.C25740c;
import com.avito.android.actions_sheet.dialog.ActionsSheetLinkDialog;
import com.avito.android.active_orders_common.items.all_orders.k;
import com.avito.android.active_orders_common.items.comfortable_deal.i;
import com.avito.android.favorites.adapter.advert.related_products.RelatedProductsView;
import com.avito.android.lib.design.accordion.Accordion;
import com.avito.android.lib.design.description_list.parameter_line.item.DescriptionParameterItem;
import com.avito.android.photo_list_view.re23.image.content.PublishImageContent;
import com.avito.android.user_adverts.root_screen.adverts_host.publish_button.PublishFabView;
import com.google.crypto.tink.aead.internal.InsecureNonceXChaCha20;
import com.huawei.hms.adapter.internal.AvailableCode;
import ru.avito.component.statistics.StatisticsView;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes16.dex */
public final /* synthetic */ class b implements View.OnClickListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f544b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Y41.a f545c;

    public /* synthetic */ b(int i12, Y41.a aVar) {
        this.f544b = i12;
        this.f545c = aVar;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Y41.a aVar = this.f545c;
        switch (this.f544b) {
            case 0:
                int i12 = com.avito.android.publish.scanner_v2.c.f239339z;
                aVar.invoke();
                break;
            case 1:
                int i13 = com.avito.android.publish.scanner_v2.c.f239339z;
                aVar.invoke();
                break;
            case 2:
                int i14 = com.avito.android.publish.scanner_v2.c.f239339z;
                aVar.invoke();
                break;
            case 3:
                int i15 = com.avito.android.publish.scanner_v2.c.f239339z;
                aVar.invoke();
                break;
            case 4:
                int i16 = com.avito.android.publish.scanner_v2.c.f239339z;
                aVar.invoke();
                break;
            case 5:
                int i17 = com.avito.android.publish.scanner_v2.c.f239339z;
                aVar.invoke();
                break;
            case 6:
                aVar.invoke();
                break;
            case 7:
                aVar.invoke();
                break;
            case 8:
                int i18 = C13481b.f4132e;
                aVar.invoke();
                break;
            case 9:
                aVar.invoke();
                break;
            case 10:
                int i19 = RelatedProductsView.f156464d;
                aVar.invoke();
                break;
            case 11:
                aVar.invoke();
                break;
            case 12:
                int i22 = PublishFabView.f313790e;
                if (aVar != null) {
                    aVar.invoke();
                    break;
                }
                break;
            case 13:
                int i23 = PublishFabView.f313790e;
                aVar.invoke();
                break;
            case 14:
                aVar.invoke();
                break;
            case 15:
                aVar.invoke();
                break;
            case 16:
                aVar.invoke();
                break;
            case 17:
                int i24 = Accordion.f178177n;
                aVar.invoke();
                break;
            case 18:
                aVar.invoke();
                break;
            case 19:
                aVar.invoke();
                break;
            case 20:
                int i25 = StatisticsView.f430567g;
                aVar.invoke();
                break;
            case 21:
                aVar.invoke();
                break;
            case 22:
                d.a aVar2 = XO0.d.f18870d;
                aVar.invoke();
                break;
            case 23:
                int i26 = DescriptionParameterItem.f179078j;
                aVar.invoke();
                break;
            case InsecureNonceXChaCha20.NONCE_SIZE_IN_BYTES /* 24 */:
                int i27 = PublishImageContent.f218213m;
                aVar.invoke();
                break;
            case AvailableCode.ERROR_ON_ACTIVITY_RESULT /* 25 */:
                int i28 = C25740c.f57593k;
                aVar.invoke();
                break;
            case AvailableCode.ERROR_NO_ACTIVITY /* 26 */:
                int i29 = C25740c.f57593k;
                aVar.invoke();
                break;
            case AvailableCode.USER_IGNORE_PREVIOUS_POPUP /* 27 */:
                ActionsSheetLinkDialog.a aVar3 = ActionsSheetLinkDialog.f68198i0;
                aVar.invoke();
                break;
            case AvailableCode.APP_IS_BACKGROUND_OR_LOCKED /* 28 */:
                int i32 = k.f68312f;
                aVar.invoke();
                break;
            default:
                int i33 = i.f68339i;
                if (aVar != null) {
                    aVar.invoke();
                    break;
                }
                break;
        }
    }
}
