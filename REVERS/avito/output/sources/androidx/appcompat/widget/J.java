package androidx.appcompat.widget;

import androidx.appcompat.widget.AppCompatSpinner;

/* compiled from: AppCompatSpinner.java */
/* loaded from: classes.dex */
class J extends Z {

    /* renamed from: k, reason: collision with root package name */
    public final /* synthetic */ AppCompatSpinner.e f22163k;

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ AppCompatSpinner f22164l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public J(AppCompatSpinner appCompatSpinner, AppCompatSpinner appCompatSpinner2, AppCompatSpinner.e eVar) {
        super(appCompatSpinner2);
        this.f22164l = appCompatSpinner;
        this.f22163k = eVar;
    }

    @Override // androidx.appcompat.widget.Z
    public final androidx.appcompat.view.menu.r c() {
        return this.f22163k;
    }

    @Override // androidx.appcompat.widget.Z
    public final boolean d() {
        AppCompatSpinner appCompatSpinner = this.f22164l;
        if (appCompatSpinner.getInternalPopup().a()) {
            return true;
        }
        appCompatSpinner.f22072g.f(appCompatSpinner.getTextDirection(), appCompatSpinner.getTextAlignment());
        return true;
    }
}
