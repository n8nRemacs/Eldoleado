package androidx.appcompat.view;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.b;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.widget.ActionBarContextView;
import j.N;
import java.lang.ref.WeakReference;

/* compiled from: StandaloneActionMode.java */
@RestrictTo
/* loaded from: classes.dex */
public class e extends b implements h.a {

    /* renamed from: d, reason: collision with root package name */
    public Context f21662d;

    /* renamed from: e, reason: collision with root package name */
    public ActionBarContextView f21663e;

    /* renamed from: f, reason: collision with root package name */
    public b.a f21664f;

    /* renamed from: g, reason: collision with root package name */
    public WeakReference<View> f21665g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f21666h;

    /* renamed from: i, reason: collision with root package name */
    public androidx.appcompat.view.menu.h f21667i;

    @Override // androidx.appcompat.view.menu.h.a
    public final void a(@N androidx.appcompat.view.menu.h hVar) {
        i();
        this.f21663e.i();
    }

    @Override // androidx.appcompat.view.menu.h.a
    public final boolean b(@N androidx.appcompat.view.menu.h hVar, @N MenuItem menuItem) {
        return this.f21664f.a(this, menuItem);
    }

    @Override // androidx.appcompat.view.b
    public final void c() {
        if (this.f21666h) {
            return;
        }
        this.f21666h = true;
        this.f21664f.b(this);
    }

    @Override // androidx.appcompat.view.b
    public final View d() {
        WeakReference<View> weakReference = this.f21665g;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // androidx.appcompat.view.b
    public final androidx.appcompat.view.menu.h e() {
        return this.f21667i;
    }

    @Override // androidx.appcompat.view.b
    public final MenuInflater f() {
        return new g(this.f21663e.getContext());
    }

    @Override // androidx.appcompat.view.b
    public final CharSequence g() {
        return this.f21663e.getSubtitle();
    }

    @Override // androidx.appcompat.view.b
    public final CharSequence h() {
        return this.f21663e.getTitle();
    }

    @Override // androidx.appcompat.view.b
    public final void i() {
        this.f21664f.c(this, this.f21667i);
    }

    @Override // androidx.appcompat.view.b
    public final boolean j() {
        return this.f21663e.f21961t;
    }

    @Override // androidx.appcompat.view.b
    public final void k(View view) {
        this.f21663e.setCustomView(view);
        this.f21665g = view != null ? new WeakReference<>(view) : null;
    }

    @Override // androidx.appcompat.view.b
    public final void l(int i12) {
        m(this.f21662d.getString(i12));
    }

    @Override // androidx.appcompat.view.b
    public final void m(CharSequence charSequence) {
        this.f21663e.setSubtitle(charSequence);
    }

    @Override // androidx.appcompat.view.b
    public final void n(int i12) {
        o(this.f21662d.getString(i12));
    }

    @Override // androidx.appcompat.view.b
    public final void o(CharSequence charSequence) {
        this.f21663e.setTitle(charSequence);
    }

    @Override // androidx.appcompat.view.b
    public final void p(boolean z12) {
        this.f21655c = z12;
        this.f21663e.setTitleOptional(z12);
    }
}
