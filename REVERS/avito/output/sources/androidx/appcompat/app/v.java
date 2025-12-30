package androidx.appcompat.app;

import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.A0;
import androidx.core.view.C0;
import androidx.core.view.C22823h0;

/* compiled from: AppCompatDelegateImpl.java */
/* loaded from: classes.dex */
class v implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppCompatDelegateImpl f21576b;

    /* compiled from: AppCompatDelegateImpl.java */
    public class a extends C0 {
        public a() {
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void a(View view) {
            AppCompatDelegateImpl appCompatDelegateImpl = v.this.f21576b;
            appCompatDelegateImpl.f21430w.setAlpha(1.0f);
            appCompatDelegateImpl.f21433z.e(null);
            appCompatDelegateImpl.f21433z = null;
        }

        @Override // androidx.core.view.C0, androidx.core.view.B0
        public final void b(View view) {
            v.this.f21576b.f21430w.setVisibility(0);
        }
    }

    public v(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f21576b = appCompatDelegateImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ViewGroup viewGroup;
        AppCompatDelegateImpl appCompatDelegateImpl = this.f21576b;
        appCompatDelegateImpl.f21431x.showAtLocation(appCompatDelegateImpl.f21430w, 55, 0, 0);
        A0 a02 = appCompatDelegateImpl.f21433z;
        if (a02 != null) {
            a02.b();
        }
        if (!(appCompatDelegateImpl.f21384B && (viewGroup = appCompatDelegateImpl.f21385C) != null && viewGroup.isLaidOut())) {
            appCompatDelegateImpl.f21430w.setAlpha(1.0f);
            appCompatDelegateImpl.f21430w.setVisibility(0);
            return;
        }
        appCompatDelegateImpl.f21430w.setAlpha(0.0f);
        A0 a0A = C22823h0.a(appCompatDelegateImpl.f21430w);
        a0A.a(1.0f);
        appCompatDelegateImpl.f21433z = a0A;
        a0A.e(new a());
    }
}
