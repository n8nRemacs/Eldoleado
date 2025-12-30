package androidx.camera.camera2.internal.compat.workaround;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.hardware.camera2.CameraDevice;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.a;
import androidx.camera.camera2.internal.C;
import com.avito.android.R;
import com.avito.android.authorization.event.AuthWarningPhoneInOtherAccEvent;
import com.avito.android.code_check.code_confirm.C29386d;
import com.avito.android.deep_linking.links.EmptyDeepLink;
import com.avito.android.lib.design.description_list.parameter_line.item.DescriptionParameterItem;
import com.avito.android.lib.design.toggle.Checkbox;
import com.avito.android.printable_text.PrintableText;
import com.avito.android.remote.model.Action;
import com.avito.android.remote.model.UserDialog;
import com.avito.android.remote.model.text.AttributedText;
import com.avito.android.remote.model.text.FontAttribute;
import com.avito.android.remote.model.text.FontParameter;
import com.avito.android.remote.model.text.FontStyleKt;
import com.avito.android.util.C35835l0;
import com.avito.android.util.I5;
import com.google.android.gms.tasks.InterfaceC37024g;
import com.google.common.util.concurrent.D0;
import java.util.Collections;
import java.util.List;
import kotlin.collections.C42745f0;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.N;
import kotlin.text.C43066x;
import oo0.InterfaceC44834b;
import qt0.C47439c;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class v implements androidx.camera.core.impl.utils.futures.a, com.avito.android.code_check_public.c, a.e, InterfaceC37024g {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23271b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23272c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23273d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f23274e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ Object f23275f;

    public /* synthetic */ v(Object obj, Object obj2, Object obj3, Object obj4, int i12) {
        this.f23271b = i12;
        this.f23272c = obj;
        this.f23273d = obj2;
        this.f23274e = obj3;
        this.f23275f = obj4;
    }

    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
    public D0 apply(Object obj) {
        return ((C) this.f23272c).c((CameraDevice) this.f23273d, (androidx.camera.camera2.internal.compat.params.l) this.f23274e, (List) this.f23275f);
    }

    /* JADX WARN: Type inference failed for: r2v8, types: [Y41.p, kotlin.jvm.internal.N] */
    /* JADX WARN: Type inference failed for: r3v3, types: [Y41.l, kotlin.jvm.internal.N] */
    @Override // androidx.asynclayoutinflater.view.a.e
    public void g(View view, ViewGroup viewGroup) {
        int i12 = com.avito.android.service_booking_common.blueprints.check_price_list.p.f276262h;
        C47439c.a aVar = (C47439c.a) this.f23272c;
        view.setTag(aVar.f429405a);
        DescriptionParameterItem descriptionParameterItem = (DescriptionParameterItem) view.findViewById(R.id.sb_check_price_list_value);
        I5.a(descriptionParameterItem.getLeftTextView(), aVar.f429407c, false);
        I5.a(descriptionParameterItem.getRightTextView(), aVar.f429408d, false);
        descriptionParameterItem.setLeftTextIconDrawable(aVar.f429410f != null ? C35835l0.h(R.attr.ic_help20, descriptionParameterItem.getContext()) : null);
        descriptionParameterItem.setLeftTextIconClickListener(new com.avito.android.service_booking_calendar.flexible.header.toolbar.g((Y41.l) this.f23273d, aVar));
        View viewFindViewById = view.findViewById(R.id.sb_service_check);
        if (viewFindViewById == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.avito.android.lib.design.toggle.Checkbox");
        }
        ((Checkbox) viewFindViewById).setChecked(aVar.f429409e);
        view.setOnClickListener(new com.avito.android.job.cv_info_actualization.ui.items.radio.g((Y41.p) this.f23274e, aVar, (String) this.f23275f));
        if (viewGroup != null) {
            viewGroup.addView(view);
        }
    }

    @Override // com.avito.android.code_check_public.c
    public Object k0(Context context) {
        switch (this.f23271b) {
            case 1:
                AuthWarningPhoneInOtherAccEvent authWarningPhoneInOtherAccEvent = new AuthWarningPhoneInOtherAccEvent(AuthWarningPhoneInOtherAccEvent.Source.f93835e);
                com.avito.android.authorization.auto_recovery.phone_confirm.l lVar = (com.avito.android.authorization.auto_recovery.phone_confirm.l) this.f23272c;
                lVar.f93237a.c(authWarningPhoneInOtherAccEvent);
                CharSequence charSequenceC = lVar.f93238b.c(context, new AttributedText(context.getString(R.string.auto_recovery_code_check_phone_phone_bound_message), Collections.singletonList(new FontAttribute(context.getString(R.string.auto_recovery_code_check_phone_phone_bound_message_placeholder), C43066x.a0(lVar.f93239c.a((String) this.f23273d), " ", " ", false), com.avito.android.advert.item.delivery_suggests.l.u(null, "h5"))), 1));
                if (charSequenceC == null) {
                    charSequenceC = "";
                }
                return new UserDialog(context.getString(R.string.auto_recovery_code_check_phone_phone_bound_title), charSequenceC, C42745f0.U(new Action(context.getString(R.string.auto_recovery_code_check_phone_phone_bound_action), (EmptyDeepLink) this.f23274e, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 65532, null), new Action(context.getString(R.string.auto_recovery_code_check_phone_dialog_back), (EmptyDeepLink) this.f23275f, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 65532, null)), false, 8, null);
            case 2:
                String strK0 = ((PrintableText) this.f23272c).k0((Context) this.f23273d);
                return !C43066x.q(strK0, "{{phone_placeholder}}", false) ? new AttributedText(strK0, C42784z0.f406748b, 0, 4, null) : new AttributedText(strK0, Collections.singletonList(new FontAttribute("phone_placeholder", C43066x.a0(((C29386d) this.f23274e).f118683a.a((String) this.f23275f), " ", " ", false), Collections.singletonList(new FontParameter.StyleParameter(FontStyleKt.BOLD)))), 0);
            default:
                String string = context.getString(R.string.tfa_enable_code_check_code_confirm_dialog_title);
                com.avito.android.safety.tfa_enable_confirm.code_check.interactor.i iVar = (com.avito.android.safety.tfa_enable_confirm.code_check.interactor.i) this.f23272c;
                com.avito.android.util.text.a aVar = iVar.f258170b;
                InterfaceC44834b.C12197b c12197b = (InterfaceC44834b.C12197b) ((InterfaceC44834b) this.f23273d);
                CharSequence charSequenceC2 = aVar.c(context, iVar.f258169a.a(c12197b.getMessage()));
                if (charSequenceC2 == null) {
                    charSequenceC2 = c12197b.getMessage();
                }
                return new UserDialog(string, charSequenceC2, C42745f0.U(new Action(context.getString(R.string.tfa_enable_code_check_code_confirm_dialog_continue), (EmptyDeepLink) this.f23274e, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 65532, null), new Action(context.getString(R.string.tfa_enable_code_check_code_confirm_dialog_back), (EmptyDeepLink) this.f23275f, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 65532, null)), true);
        }
    }

    @Override // com.google.android.gms.tasks.InterfaceC37024g
    public void onSuccess(Object obj) {
        ru.cyberity.cbr.core.domain.f.a((Y41.l) this.f23272c, (Bitmap) this.f23273d, (ru.cyberity.cbr.core.domain.f) this.f23274e, (RectF) this.f23275f, (List) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ v(C47439c.a aVar, Y41.l lVar, Y41.p pVar, String str) {
        this.f23271b = 4;
        this.f23272c = aVar;
        this.f23273d = (N) lVar;
        this.f23274e = (N) pVar;
        this.f23275f = str;
    }
}
