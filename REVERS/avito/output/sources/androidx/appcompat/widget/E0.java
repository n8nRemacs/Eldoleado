package androidx.appcompat.widget;

import androidx.appcompat.widget.Toolbar;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class E0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f22127b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Toolbar f22128c;

    public /* synthetic */ E0(Toolbar toolbar, int i12) {
        this.f22127b = i12;
        this.f22128c = toolbar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f22127b) {
            case 0:
                Toolbar.f fVar = this.f22128c.f22381N;
                androidx.appcompat.view.menu.k kVar = fVar == null ? null : fVar.f22421c;
                if (kVar != null) {
                    kVar.collapseActionView();
                    break;
                }
                break;
            default:
                this.f22128c.n();
                break;
        }
    }
}
