package Ac0;

import Ac0.C13008a;
import Ac0.C13009b;
import Ac0.C13010c;
import Ac0.p;
import Ac0.q;
import Zd.InterfaceC19542a;
import android.content.Intent;
import androidx.compose.runtime.internal.P;
import androidx.fragment.app.Fragment;
import com.avito.android.N1;
import com.avito.android.authorization.AuthSource;
import com.avito.android.authorization.auth.C28629h;
import com.avito.android.deep_linking.links.auth.LandlinePhoneVerificationLink;
import com.avito.android.deep_linking.links.auth.PhoneVerificationLinkContext;
import com.avito.android.deeplink_handler.handler.b;
import com.avito.android.edit_carousel.entity.CarouselEditorData;
import com.avito.android.extended_profile_image_edit.t;
import com.avito.android.extended_profile_personal_link_edit.PersonalLinkEditConfig;
import com.avito.android.extended_profile_selection_create.ExtendedProfileSelectionCreateConfig;
import com.avito.android.extended_profile_universal_widget_edit.UniversalWidgetCreateArguments;
import com.avito.android.extended_profile_universal_widget_edit.UniversalWidgetEditArguments;
import com.avito.android.photo_picker.PhotoPickerIntentFactory;
import com.avito.android.remote.model.text.AttributedText;
import gF.InterfaceC40577a;
import java.util.List;
import javax.inject.Inject;
import jb0.InterfaceC42346a;
import kotlin.G0;
import kotlin.Metadata;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAc0/n;", "LAc0/d;", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public final class n implements InterfaceC13011d {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final Fragment f470a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final Y41.l<yc0.k, G0> f471b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final InterfaceC40577a f472c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final InterfaceC19542a f473d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final N1 f474e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final com.avito.android.deeplink_handler.handler.composite.a f475f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public final PhotoPickerIntentFactory f476g;

    /* renamed from: h, reason: collision with root package name */
    @Y61.k
    public final com.avito.android.extended_profile_personal_link_edit.e f477h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.k
    public final HA.a f478i;

    /* renamed from: j, reason: collision with root package name */
    @Y61.k
    public final t f479j;

    /* renamed from: k, reason: collision with root package name */
    @Y61.k
    public final com.avito.android.extended_profile_universal_widget_edit.a f480k;

    /* renamed from: l, reason: collision with root package name */
    @Y61.k
    public final String f481l;

    /* renamed from: m, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<q.a> f482m;

    /* renamed from: n, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<p.a> f483n;

    /* renamed from: o, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<C13010c.a> f484o;

    /* renamed from: p, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<C13008a.C0019a> f485p;

    /* renamed from: q, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<C13009b.a> f486q;

    /* renamed from: r, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<PersonalLinkEditConfig> f487r;

    /* renamed from: s, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<ExtendedProfileSelectionCreateConfig> f488s;

    /* renamed from: t, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<UniversalWidgetCreateArguments> f489t;

    /* renamed from: u, reason: collision with root package name */
    @Y61.k
    public final androidx.view.result.h<UniversalWidgetEditArguments> f490u;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject
    public n(@Y61.k Fragment fragment, @Y61.k Y41.l<? super yc0.k, G0> lVar, @Y61.k InterfaceC40577a interfaceC40577a, @Y61.k InterfaceC19542a interfaceC19542a, @Y61.k N1 n12, @Y61.k com.avito.android.deeplink_handler.handler.composite.a aVar, @Y61.k PhotoPickerIntentFactory photoPickerIntentFactory, @Y61.k com.avito.android.extended_profile_personal_link_edit.e eVar, @Y61.k HA.a aVar2, @Y61.k t tVar, @Y61.k com.avito.android.extended_profile_universal_widget_edit.a aVar3, @Y61.k @InterfaceC42346a String str) {
        this.f470a = fragment;
        this.f471b = lVar;
        this.f472c = interfaceC40577a;
        this.f473d = interfaceC19542a;
        this.f474e = n12;
        this.f475f = aVar;
        this.f476g = photoPickerIntentFactory;
        this.f477h = eVar;
        this.f478i = aVar2;
        this.f479j = tVar;
        this.f480k = aVar3;
        this.f481l = str;
        this.f482m = fragment.registerForActivityResult(new j(this), new C28629h(0));
        this.f483n = fragment.registerForActivityResult(new h(this), new C28629h(0));
        this.f484o = fragment.registerForActivityResult(new C13014g(this), new C28629h(0));
        this.f485p = fragment.registerForActivityResult(new C13012e(this), new C28629h(0));
        this.f486q = fragment.registerForActivityResult(new C13013f(this), new C28629h(0));
        this.f487r = fragment.registerForActivityResult(new i(this), new C28629h(0));
        this.f488s = fragment.registerForActivityResult(new k(this), new C28629h(0));
        this.f489t = fragment.registerForActivityResult(new l(this), new C28629h(0));
        this.f490u = fragment.registerForActivityResult(new m(this), new C28629h(0));
    }

    @Override // Ac0.InterfaceC13011d
    public final void a(@Y61.k CarouselEditorData carouselEditorData) {
        this.f484o.b(new C13010c.a(carouselEditorData));
    }

    @Override // Ac0.InterfaceC13011d
    public final void b(@Y61.k ExtendedProfileSelectionCreateConfig extendedProfileSelectionCreateConfig) {
        this.f488s.b(extendedProfileSelectionCreateConfig);
    }

    @Override // Ac0.InterfaceC13011d
    public final void c(@Y61.k UniversalWidgetEditArguments universalWidgetEditArguments) {
        this.f490u.b(universalWidgetEditArguments);
    }

    @Override // Ac0.InterfaceC13011d
    public final void d() {
        AuthSource authSource = AuthSource.f92694c;
        this.f486q.b(new C13009b.a());
    }

    @Override // Ac0.InterfaceC13011d
    public final void e(int i12, @Y61.k List list) {
        Intent intentA = this.f472c.a(i12, list);
        intentA.setFlags(603979776);
        this.f470a.startActivity(intentA);
    }

    @Override // Ac0.InterfaceC13011d
    public final void f(@Y61.k PersonalLinkEditConfig personalLinkEditConfig) {
        this.f487r.b(personalLinkEditConfig);
    }

    @Override // Ac0.InterfaceC13011d
    public final void g(int i12, int i13, @Y61.k String str, @Y61.k String str2, @Y61.k String str3) {
        this.f483n.b(new p.a(i12, i13, str, str2, str3));
    }

    @Override // Ac0.InterfaceC13011d
    public final void h(@Y61.k String str) {
        this.f485p.b(new C13008a.C0019a(str));
    }

    @Override // Ac0.InterfaceC13011d
    public final void i(int i12, @Y61.k String str) {
        this.f482m.b(new q.a(str, i12));
    }

    @Override // Ac0.InterfaceC13011d
    public final void j(@Y61.k AttributedText attributedText, @Y61.k String str, @Y61.k String str2) {
        b.a.a(this.f475f, new LandlinePhoneVerificationLink(new PhoneVerificationLinkContext(0, str, str2, attributedText), true, ""), null, null, 6);
    }

    @Override // Ac0.InterfaceC13011d
    public final void k(@Y61.k UniversalWidgetCreateArguments universalWidgetCreateArguments) {
        this.f489t.b(universalWidgetCreateArguments);
    }
}
