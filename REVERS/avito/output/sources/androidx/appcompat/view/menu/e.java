package androidx.appcompat.view.menu;

import androidx.appcompat.view.menu.d;

/* compiled from: CascadingMenuPopup.java */
/* loaded from: classes.dex */
class e implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ d.C1518d f21812b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ k f21813c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ h f21814d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ d.c f21815e;

    public e(d.c cVar, d.C1518d c1518d, k kVar, h hVar) {
        this.f21815e = cVar;
        this.f21812b = c1518d;
        this.f21813c = kVar;
        this.f21814d = hVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        d.C1518d c1518d = this.f21812b;
        if (c1518d != null) {
            d.c cVar = this.f21815e;
            d.this.f21781A = true;
            c1518d.f21810b.c(false);
            d.this.f21781A = false;
        }
        k kVar = this.f21813c;
        if (kVar.isEnabled() && kVar.hasSubMenu()) {
            this.f21814d.q(kVar, null, 4);
        }
    }
}
