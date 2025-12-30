package Ae0;

import Fc1.C13598e1;
import Lt0.f;
import Nf.InterfaceC14568a;
import Pf.InterfaceC14780a;
import Ui.InterfaceC15522a;
import Z1.e;
import ab.InterfaceC19855a;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import android.os.SystemClock;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.camera2.internal.compat.workaround.w;
import androidx.camera.core.C20148z;
import androidx.camera.core.impl.C20102k0;
import androidx.camera.core.impl.RunnableC20098i0;
import androidx.camera.core.z0;
import androidx.camera.lifecycle.i;
import androidx.concurrent.futures.b;
import androidx.core.os.C22778f;
import androidx.core.view.J0;
import androidx.core.view.M;
import androidx.fragment.app.ActivityC22955m;
import androidx.fragment.app.C;
import androidx.fragment.app.SpecialEffectsController;
import androidx.media3.common.util.s;
import androidx.media3.exoplayer.audio.b;
import androidx.media3.exoplayer.video.h;
import androidx.media3.extractor.AbstractC23186e;
import androidx.media3.extractor.mp4.k;
import androidx.media3.extractor.x;
import androidx.palette.graphics.b;
import androidx.room.rxjava3.EmptyResultSetException;
import androidx.view.O0;
import androidx.work.multiprocess.InterfaceC23620a;
import androidx.work.multiprocess.InterfaceC23629j;
import androidx.work.multiprocess.RemoteListenableDelegatingWorker;
import androidx.work.multiprocess.m;
import androidx.work.multiprocess.parcelable.ParcelableInterruptRequest;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.OnDeferredDeeplinkResponseListener;
import com.avito.android.R;
import com.avito.android.advert_stats.detail.advertdetailstatsmvi.AdvertDetailStatsMVIFragment;
import com.avito.android.analytics_adjust.InterfaceC28534b;
import com.avito.android.analytics_adjust.v;
import com.avito.android.authorization.PlaceholderType;
import com.avito.android.autoteka.presentation.payment.AutotekaPaymentActivity;
import com.avito.android.autoteka.presentation.report.AutotekaReportActivity;
import com.avito.android.beduin.common.action.BeduinOpenLinkAction;
import com.avito.android.beduin.common.component.radio_group.BeduinRadioGroupModel;
import com.avito.android.beduin.common.component.radio_group.Option;
import com.avito.android.beduin.common.form.transforms.SelectedIdTransform;
import com.avito.android.beduin.common.form.transforms.ShowErrorMessageTransform;
import com.avito.android.beduin.common.utils.C28806b;
import com.avito.android.beduin_models.BeduinAction;
import com.avito.android.beduin_shared.model.utils.j;
import com.avito.android.lib.design.button.Button;
import com.avito.android.lib.design.radio.RadioButton;
import com.avito.android.lib.design.radio.RadioGroup;
import com.avito.android.publish.scanner_v2.ScannerOverlay;
import com.avito.android.service_booking_calendar.data.model.Day;
import com.avito.android.service_booking_calendar.view.day.DayItemContent;
import com.avito.android.util.C35974x2;
import com.avito.android.util.L5;
import com.avito.android.util.V2;
import com.avito.android.util.y6;
import com.avito.android.wallet.page.history.details.PaymentHistoryDetailsActivity;
import com.google.android.gms.tasks.InterfaceC37022e;
import com.google.android.gms.tasks.Task;
import com.google.common.base.InterfaceC37276u;
import com.google.common.util.concurrent.D0;
import ft0.InterfaceC40478a;
import io.reactivex.rxjava3.core.K;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.jvm.internal.L;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes16.dex */
public final /* synthetic */ class c implements M, InterfaceC40478a.InterfaceC11180a, InterfaceC40478a.b, Toolbar.h, C, b.c, androidx.camera.core.impl.utils.futures.a, C22778f.a, b.f, h.b.a, AbstractC23186e.d, InterfaceC37276u, IY0.a, io.reactivex.rxjava3.core.M, e.c, InterfaceC23629j, OnDeferredDeeplinkResponseListener, com.avito.android.code_check_public.c, InterfaceC37022e, InterfaceC15522a.InterfaceC1136a, RadioGroup.d {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f546b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f547c;

    public /* synthetic */ c(f fVar, org.threeten.bp.e eVar) {
        this.f546b = 3;
        this.f547c = eVar;
    }

    @Override // androidx.media3.extractor.AbstractC23186e.d
    public long a(long j12) {
        return androidx.media3.common.util.M.l((j12 * r0.f51895e) / 1000000, 0L, ((x) this.f547c).f51900j - 1);
    }

    @Override // com.google.common.base.InterfaceC37276u
    public Object apply(Object obj) {
        k kVar = (k) obj;
        ((androidx.media3.extractor.mp4.e) this.f547c).getClass();
        return kVar;
    }

    @Override // com.avito.android.lib.design.radio.RadioGroup.d
    public void b(RadioButton radioButton) {
        List<BeduinAction> onSelectActions;
        String string = radioButton.getTag().toString();
        com.avito.android.beduin.common.component.radio_group.e eVar = (com.avito.android.beduin.common.component.radio_group.e) this.f547c;
        BeduinRadioGroupModel beduinRadioGroupModel = eVar.f102204i;
        Option optionFindOptionById = beduinRadioGroupModel.findOptionById(string);
        if (optionFindOptionById != null && (onSelectActions = optionFindOptionById.getOnSelectActions()) != null) {
            C28806b.a(eVar.f102203h, onSelectActions);
        }
        j.a(eVar.f102202g, beduinRadioGroupModel.getF100853b(), new SelectedIdTransform(string), new ShowErrorMessageTransform(false));
    }

    @Override // Z1.e.c
    public e c(e.b bVar) {
        e.b.f19822f.getClass();
        e.b.a aVar = new e.b.a((Context) this.f547c);
        aVar.f19829b = bVar.f19824b;
        aVar.f19830c = bVar.f19825c;
        aVar.f19831d = true;
        aVar.f19832e = true;
        return new androidx.sqlite.db.framework.d().c(aVar.a());
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        switch (this.f546b) {
            case 7:
                w wVar = (w) this.f547c;
                wVar.f23279d = aVar;
                return "WaitForRepeatingRequestStart[" + wVar + "]";
            default:
                C20102k0 c20102k0 = (C20102k0) this.f547c;
                c20102k0.getClass();
                androidx.camera.core.impl.utils.executor.c.d().execute(new RunnableC20098i0(4, c20102k0, aVar));
                return c20102k0 + " [fetch@" + SystemClock.uptimeMillis() + "]";
        }
    }

    @Override // ft0.InterfaceC40478a.b
    public String e(org.threeten.bp.e eVar) {
        fu0.d.f396183a.getClass();
        StringBuilder sb2 = new StringBuilder(fu0.d.f396184b.getMonths()[eVar.f421942c - 1]);
        int i12 = ((org.threeten.bp.e) this.f547c).f421941b;
        int i13 = eVar.f421941b;
        if (i13 != i12) {
            sb2.append(" " + i13);
        }
        return sb2.toString();
    }

    @Override // ft0.InterfaceC40478a.InterfaceC11180a
    public DayItemContent f(org.threeten.bp.e eVar) {
        String strB;
        Day day = (Day) ((LinkedHashMap) this.f547c).get(eVar);
        String strValueOf = String.valueOf((int) eVar.f421943d);
        Long lValueOf = null;
        Day.Type typeC = day != null ? day.getType() : null;
        int i12 = typeC == null ? -1 : f.a.f10171a[typeC.ordinal()];
        DayItemContent.EventType eventType = i12 != 1 ? i12 != 2 ? i12 != 3 ? DayItemContent.EventType.f276176b : DayItemContent.EventType.f276177c : DayItemContent.EventType.f276179e : DayItemContent.EventType.f276178d;
        DayItemContent.DayType dayType = day == null ? DayItemContent.DayType.f276172c : L.f(day.getIsDisabled(), Boolean.TRUE) ? DayItemContent.DayType.f276171b : (day.getIsDayOff() || day.getIsFocus()) ? DayItemContent.DayType.f276173d : DayItemContent.DayType.f276172c;
        DayItemContent.HighlightType highlightType = (day != null && day.getIsToday()) ? DayItemContent.HighlightType.f276182b : DayItemContent.HighlightType.f276183c;
        DayItemContent.HighlightType highlightType2 = highlightType;
        boolean zF2 = day != null ? day.getIsFocus() : false;
        if (day != null && (strB = day.getTime()) != null) {
            lValueOf = Long.valueOf(Long.parseLong(strB));
        }
        return new com.avito.android.service_booking_schedule_repetition_impl.mvi.domain.a(strValueOf, eventType, dayType, highlightType2, zF2, lValueOf);
    }

    @Override // Ui.InterfaceC15522a.InterfaceC1136a
    public void g(ActivityC22955m activityC22955m) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(((BeduinOpenLinkAction) this.f547c).getLink()));
        C35974x2.d(intent);
        try {
            activityC22955m.startActivity(intent);
        } catch (Exception e12) {
            V2.f318762a.f(e12);
        }
    }

    @Override // androidx.core.view.M
    public J0 h(View view, J0 j02) {
        Object obj = this.f547c;
        switch (this.f546b) {
            case 0:
                int i12 = com.avito.android.publish.scanner_v2.c.f239339z;
                int i13 = j02.e(7).f44776b;
                int i14 = j02.e(7).f44778d;
                com.avito.android.publish.scanner_v2.c cVar = (com.avito.android.publish.scanner_v2.c) obj;
                View view2 = cVar.f239345g;
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams == null) {
                    throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                }
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.setMargins(marginLayoutParams.leftMargin, i13, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                view2.setLayoutParams(marginLayoutParams);
                Button button = cVar.f239343e;
                ViewGroup.LayoutParams layoutParams2 = button.getLayoutParams();
                if (layoutParams2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                }
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
                marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, y6.b(16) + i14);
                button.setLayoutParams(marginLayoutParams2);
                return J0.f44939b;
            case 1:
                int i15 = ScannerOverlay.f239281I;
                ScannerOverlay scannerOverlay = (ScannerOverlay) obj;
                scannerOverlay.f239308t = j02.e(7).f44776b;
                scannerOverlay.f239309u = j02.e(7).f44778d;
                return J0.f44939b;
            default:
                Sy.b.k((Sy.b) obj, j02);
                return j02;
        }
    }

    @Override // androidx.work.multiprocess.InterfaceC23629j
    public void i(IInterface iInterface, m mVar) {
        int i12 = RemoteListenableDelegatingWorker.f56105e;
        RemoteListenableDelegatingWorker remoteListenableDelegatingWorker = (RemoteListenableDelegatingWorker) this.f547c;
        ((InterfaceC23620a) iInterface).c3(mVar, androidx.work.multiprocess.parcelable.a.a(new ParcelableInterruptRequest(remoteListenableDelegatingWorker.f56107b.f55432a.toString(), remoteListenableDelegatingWorker.getStopReason())));
    }

    @Override // io.reactivex.rxjava3.core.M
    public void j(K k12) {
        Object obj = this.f547c;
        switch (this.f546b) {
            case 16:
                try {
                    k12.onSuccess(((Callable) obj).call());
                    break;
                } catch (EmptyResultSetException e12) {
                    k12.g(e12);
                    return;
                }
            case 20:
                Bitmap bitmap = (Bitmap) obj;
                if (bitmap.getWidth() != 0 && bitmap.getHeight() != 0) {
                    b.c cVar = androidx.palette.graphics.b.f53263e;
                    b.e eVar = new b.C1877b(bitmap).a().f53267d;
                    k12.onSuccess(Integer.valueOf(eVar != null ? eVar.f53277d : -1));
                    break;
                } else {
                    k12.onError(new IllegalArgumentException());
                    break;
                }
                break;
            default:
                Adjust.getGoogleAdId(((v) obj).f91248a, new C13598e1(k12, 4));
                break;
        }
    }

    @Override // androidx.fragment.app.C
    public void k(Bundle bundle, String str) {
        Object obj = this.f547c;
        switch (this.f546b) {
            case 5:
                int i12 = PaymentHistoryDetailsActivity.f327833m;
                ((PaymentHistoryDetailsActivity) obj).finish();
                break;
            case 19:
                AdvertDetailStatsMVIFragment.a aVar = AdvertDetailStatsMVIFragment.f86027D0;
                long j12 = bundle.getLong("requestKeyAdvertDetailStatsDateFrom");
                String string = bundle.getString("requestKeyAdvertDetailStatsTabId");
                if (string == null) {
                    string = "";
                }
                ((AdvertDetailStatsMVIFragment) obj).D5().accept(new InterfaceC19855a.e(j12, string));
                break;
            default:
                AutotekaPaymentActivity.a aVar2 = AutotekaPaymentActivity.f97386y;
                ((AutotekaPaymentActivity) obj).b2().accept(InterfaceC14568a.c.f11686a);
                break;
        }
    }

    @Override // com.avito.android.code_check_public.c
    public Object k0(Context context) {
        switch (this.f546b) {
            case 23:
                return ((com.avito.android.authorization.social_registration.code_check.b) this.f547c).f94588d.a(context.getResources(), PlaceholderType.f92739c);
            default:
                return ((com.avito.android.authorization.start_registration.code_check.a) this.f547c).f94613d.a(context.getResources(), PlaceholderType.f92739c);
        }
    }

    public void l(Display display) {
        h hVar = (h) this.f547c;
        hVar.getClass();
        if (display != null) {
            long refreshRate = (long) (1.0E9d / display.getRefreshRate());
            hVar.f50295k = refreshRate;
            hVar.f50296l = (refreshRate * 80) / 100;
        } else {
            s.g();
            hVar.f50295k = -9223372036854775807L;
            hVar.f50296l = -9223372036854775807L;
        }
    }

    @Override // com.adjust.sdk.OnDeferredDeeplinkResponseListener
    public boolean launchReceivedDeeplink(Uri uri) {
        ((InterfaceC28534b) this.f547c).getClass();
        return true;
    }

    @Override // androidx.core.os.C22778f.a
    public void onCancel() {
        ((SpecialEffectsController.Operation) this.f547c).a();
    }

    @Override // com.google.android.gms.tasks.InterfaceC37022e
    public void onComplete(Task task) {
        ((z0) this.f547c).close();
    }

    @Override // androidx.appcompat.widget.Toolbar.h
    public boolean onMenuItemClick(MenuItem menuItem) {
        Object obj = this.f547c;
        switch (this.f546b) {
            case 4:
                ((M81.c) obj).f10413b.accept(Integer.valueOf(menuItem != null ? menuItem.getItemId() : 0));
                return true;
            default:
                int i12 = AutotekaReportActivity.f97671y;
                int itemId = menuItem.getItemId();
                AutotekaReportActivity autotekaReportActivity = (AutotekaReportActivity) obj;
                O0 o02 = autotekaReportActivity.f97673n;
                if (itemId == R.id.menu_download_pdf) {
                    L5.a(autotekaReportActivity, R.string.report_file_download_started, 0);
                    ((com.avito.android.autoteka.presentation.report.e) o02.getValue()).accept(InterfaceC14780a.C0856a.f13210a);
                    return true;
                }
                if (itemId != R.id.menu_share) {
                    return false;
                }
                ((com.avito.android.autoteka.presentation.report.e) o02.getValue()).accept(InterfaceC14780a.b.f13211a);
                return true;
        }
    }

    public /* synthetic */ c(Object obj, int i12) {
        this.f546b = i12;
        this.f547c = obj;
    }

    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
    public D0 apply(Object obj) {
        i iVar = i.f24674f;
        return ((C20148z) this.f547c).f24607j;
    }

    public /* synthetic */ c(LinkedHashMap linkedHashMap, f fVar) {
        this.f546b = 2;
        this.f547c = linkedHashMap;
    }
}
