package androidx.appcompat.app;

import android.view.View;
import androidx.core.view.C0;
import androidx.core.view.C22823h0;

/* compiled from: AppCompatDelegateImpl.java */
/* loaded from: classes.dex */
class w extends C0 {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppCompatDelegateImpl f21578a;

    public w(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f21578a = appCompatDelegateImpl;
    }

    @Override // androidx.core.view.C0, androidx.core.view.B0
    public final void a(View view) {
        AppCompatDelegateImpl appCompatDelegateImpl = this.f21578a;
        appCompatDelegateImpl.f21430w.setAlpha(1.0f);
        appCompatDelegateImpl.f21433z.e(null);
        appCompatDelegateImpl.f21433z = null;
    }

    @Override // androidx.core.view.C0, androidx.core.view.B0
    public final void b(View view) {
        AppCompatDelegateImpl appCompatDelegateImpl = this.f21578a;
        appCompatDelegateImpl.f21430w.setVisibility(0);
        if (appCompatDelegateImpl.f21430w.getParent() instanceof View) {
            C22823h0.A((View) appCompatDelegateImpl.f21430w.getParent());
        }
    }
}
