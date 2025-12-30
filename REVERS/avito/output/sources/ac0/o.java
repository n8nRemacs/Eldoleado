package Ac0;

import Zd.InterfaceC19542a;
import androidx.fragment.app.Fragment;
import com.avito.android.N1;
import com.avito.android.extended_profile_image_edit.t;
import com.avito.android.photo_picker.PhotoPickerIntentFactory;
import dagger.internal.x;
import dagger.internal.y;
import gF.InterfaceC40577a;
import javax.inject.Provider;

/* compiled from: ExtendedProfileSettingsRouterImpl_Factory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes16.dex */
public final class o implements dagger.internal.h<n> {

    /* renamed from: a, reason: collision with root package name */
    public final dagger.internal.l f491a;

    /* renamed from: b, reason: collision with root package name */
    public final dagger.internal.l f492b;

    /* renamed from: c, reason: collision with root package name */
    public final Provider<InterfaceC40577a> f493c;

    /* renamed from: d, reason: collision with root package name */
    public final Provider<InterfaceC19542a> f494d;

    /* renamed from: e, reason: collision with root package name */
    public final Provider<N1> f495e;

    /* renamed from: f, reason: collision with root package name */
    public final Provider<com.avito.android.deeplink_handler.handler.composite.a> f496f;

    /* renamed from: g, reason: collision with root package name */
    public final Provider<PhotoPickerIntentFactory> f497g;

    /* renamed from: h, reason: collision with root package name */
    public final Provider<com.avito.android.extended_profile_personal_link_edit.e> f498h;

    /* renamed from: i, reason: collision with root package name */
    public final Provider<HA.a> f499i;

    /* renamed from: j, reason: collision with root package name */
    public final Provider<t> f500j;

    /* renamed from: k, reason: collision with root package name */
    public final Provider<com.avito.android.extended_profile_universal_widget_edit.a> f501k;

    /* renamed from: l, reason: collision with root package name */
    public final Provider<String> f502l;

    public o(dagger.internal.l lVar, dagger.internal.l lVar2, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.f491a = lVar;
        this.f492b = lVar2;
        this.f493c = provider;
        this.f494d = provider2;
        this.f495e = provider3;
        this.f496f = provider4;
        this.f497g = provider5;
        this.f498h = provider6;
        this.f499i = provider7;
        this.f500j = provider8;
        this.f501k = provider9;
        this.f502l = provider10;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.inject.Provider
    public final Object get() {
        return new n((Fragment) this.f491a.f393949a, (Y41.l) this.f492b.f393949a, this.f493c.get(), this.f494d.get(), this.f495e.get(), this.f496f.get(), this.f497g.get(), this.f498h.get(), this.f499i.get(), this.f500j.get(), this.f501k.get(), this.f502l.get());
    }
}
