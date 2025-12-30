package androidx.appcompat.app;

import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.T;
import androidx.core.view.A0;

/* compiled from: AppCompatDelegateImpl.java */
/* loaded from: classes.dex */
class u implements ContentFrameLayout.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppCompatDelegateImpl f21575a;

    public u(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f21575a = appCompatDelegateImpl;
    }

    @Override // androidx.appcompat.widget.ContentFrameLayout.a
    public final void onDetachedFromWindow() {
        AppCompatDelegateImpl appCompatDelegateImpl = this.f21575a;
        T t12 = appCompatDelegateImpl.f21426s;
        if (t12 != null) {
            t12.i();
        }
        if (appCompatDelegateImpl.f21431x != null) {
            appCompatDelegateImpl.f21420m.getDecorView().removeCallbacks(appCompatDelegateImpl.f21432y);
            if (appCompatDelegateImpl.f21431x.isShowing()) {
                try {
                    appCompatDelegateImpl.f21431x.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            appCompatDelegateImpl.f21431x = null;
        }
        A0 a02 = appCompatDelegateImpl.f21433z;
        if (a02 != null) {
            a02.b();
        }
        androidx.appcompat.view.menu.h hVar = appCompatDelegateImpl.V(0).f21441h;
        if (hVar != null) {
            hVar.c(true);
        }
    }
}
