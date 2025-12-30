package androidx.appcompat.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.b;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.view.menu.q;
import androidx.collection.o1;
import java.util.ArrayList;

/* compiled from: SupportActionModeWrapper.java */
@RestrictTo
/* loaded from: classes.dex */
public class f extends ActionMode {

    /* renamed from: a, reason: collision with root package name */
    public final Context f21668a;

    /* renamed from: b, reason: collision with root package name */
    public final b f21669b;

    /* compiled from: SupportActionModeWrapper.java */
    @RestrictTo
    public static class a implements b.a {

        /* renamed from: a, reason: collision with root package name */
        public final ActionMode.Callback f21670a;

        /* renamed from: b, reason: collision with root package name */
        public final Context f21671b;

        /* renamed from: c, reason: collision with root package name */
        public final ArrayList<f> f21672c = new ArrayList<>();

        /* renamed from: d, reason: collision with root package name */
        public final o1<Menu, Menu> f21673d = new o1<>();

        public a(Context context, ActionMode.Callback callback) {
            this.f21671b = context;
            this.f21670a = callback;
        }

        @Override // androidx.appcompat.view.b.a
        public final boolean a(b bVar, MenuItem menuItem) {
            return this.f21670a.onActionItemClicked(d(bVar), new l(this.f21671b, (K0.b) menuItem));
        }

        @Override // androidx.appcompat.view.b.a
        public final void b(b bVar) {
            this.f21670a.onDestroyActionMode(d(bVar));
        }

        @Override // androidx.appcompat.view.b.a
        public final boolean c(b bVar, androidx.appcompat.view.menu.h hVar) {
            f fVarD = d(bVar);
            o1<Menu, Menu> o1Var = this.f21673d;
            Menu qVar = o1Var.get(hVar);
            if (qVar == null) {
                qVar = new q(this.f21671b, hVar);
                o1Var.put(hVar, qVar);
            }
            return this.f21670a.onPrepareActionMode(fVarD, qVar);
        }

        public final f d(b bVar) {
            ArrayList<f> arrayList = this.f21672c;
            int size = arrayList.size();
            for (int i12 = 0; i12 < size; i12++) {
                f fVar = arrayList.get(i12);
                if (fVar != null && fVar.f21669b == bVar) {
                    return fVar;
                }
            }
            f fVar2 = new f(this.f21671b, bVar);
            arrayList.add(fVar2);
            return fVar2;
        }

        public final boolean e(b bVar, androidx.appcompat.view.menu.h hVar) {
            f fVarD = d(bVar);
            o1<Menu, Menu> o1Var = this.f21673d;
            Menu qVar = o1Var.get(hVar);
            if (qVar == null) {
                qVar = new q(this.f21671b, hVar);
                o1Var.put(hVar, qVar);
            }
            return this.f21670a.onCreateActionMode(fVarD, qVar);
        }
    }

    public f(Context context, b bVar) {
        this.f21668a = context;
        this.f21669b = bVar;
    }

    @Override // android.view.ActionMode
    public final void finish() {
        this.f21669b.c();
    }

    @Override // android.view.ActionMode
    public final View getCustomView() {
        return this.f21669b.d();
    }

    @Override // android.view.ActionMode
    public final Menu getMenu() {
        return new q(this.f21668a, this.f21669b.e());
    }

    @Override // android.view.ActionMode
    public final MenuInflater getMenuInflater() {
        return this.f21669b.f();
    }

    @Override // android.view.ActionMode
    public final CharSequence getSubtitle() {
        return this.f21669b.g();
    }

    @Override // android.view.ActionMode
    public final Object getTag() {
        return this.f21669b.f21654b;
    }

    @Override // android.view.ActionMode
    public final CharSequence getTitle() {
        return this.f21669b.h();
    }

    @Override // android.view.ActionMode
    public final boolean getTitleOptionalHint() {
        return this.f21669b.f21655c;
    }

    @Override // android.view.ActionMode
    public final void invalidate() {
        this.f21669b.i();
    }

    @Override // android.view.ActionMode
    public final boolean isTitleOptional() {
        return this.f21669b.j();
    }

    @Override // android.view.ActionMode
    public final void setCustomView(View view) {
        this.f21669b.k(view);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(CharSequence charSequence) {
        this.f21669b.m(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTag(Object obj) {
        this.f21669b.f21654b = obj;
    }

    @Override // android.view.ActionMode
    public final void setTitle(CharSequence charSequence) {
        this.f21669b.o(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTitleOptionalHint(boolean z12) {
        this.f21669b.p(z12);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(int i12) {
        this.f21669b.l(i12);
    }

    @Override // android.view.ActionMode
    public final void setTitle(int i12) {
        this.f21669b.n(i12);
    }
}
