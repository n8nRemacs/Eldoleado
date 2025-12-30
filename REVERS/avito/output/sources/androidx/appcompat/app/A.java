package androidx.appcompat.app;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.p;
import androidx.appcompat.view.b;
import androidx.core.view.C22838q;
import androidx.view.C23489g;
import androidx.view.V0;
import com.avito.android.R;
import j.N;
import j.P;

/* compiled from: AppCompatDialog.java */
/* loaded from: classes.dex */
public class A extends androidx.view.r implements n {

    /* renamed from: e, reason: collision with root package name */
    public p f21314e;

    /* renamed from: f, reason: collision with root package name */
    public final z f21315f;

    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.appcompat.app.z] */
    public A(@N Context context, int i12) {
        int i13;
        if (i12 == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.dialogTheme, typedValue, true);
            i13 = typedValue.resourceId;
        } else {
            i13 = i12;
        }
        super(context, i13);
        this.f21315f = new C22838q.a() { // from class: androidx.appcompat.app.z
            @Override // androidx.core.view.C22838q.a
            public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
                return this.f21580b.p(keyEvent);
            }
        };
        p pVarN = n();
        if (i12 == 0) {
            TypedValue typedValue2 = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.dialogTheme, typedValue2, true);
            i12 = typedValue2.resourceId;
        }
        pVarN.G(i12);
        pVarN.r();
    }

    @Override // androidx.view.r, android.app.Dialog
    public final void addContentView(@N View view, ViewGroup.LayoutParams layoutParams) {
        n().c(view, layoutParams);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        n().s();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return C22838q.b(this.f21315f, getWindow().getDecorView(), this, keyEvent);
    }

    @Override // android.app.Dialog
    @P
    public final <T extends View> T findViewById(@j.D int i12) {
        return (T) n().h(i12);
    }

    @Override // android.app.Dialog
    @RestrictTo
    public final void invalidateOptionsMenu() {
        n().o();
    }

    @N
    public final p n() {
        if (this.f21314e == null) {
            p.d dVar = p.f21559b;
            this.f21314e = new AppCompatDelegateImpl(getContext(), getWindow(), this, this);
        }
        return this.f21314e;
    }

    public final void o() {
        V0.b(getWindow().getDecorView(), this);
        C23489g.b(getWindow().getDecorView(), this);
        getWindow().getDecorView().setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, this);
    }

    @Override // androidx.view.r, android.app.Dialog
    public void onCreate(Bundle bundle) {
        n().n();
        super.onCreate(bundle);
        n().r();
    }

    @Override // androidx.view.r, android.app.Dialog
    public void onStop() {
        super.onStop();
        n().w();
    }

    @Override // androidx.appcompat.app.n
    @P
    public final androidx.appcompat.view.b onWindowStartingSupportActionMode(b.a aVar) {
        return null;
    }

    public final boolean p(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean q() {
        return n().y(1);
    }

    @Override // androidx.view.r, android.app.Dialog
    public void setContentView(@j.I int i12) {
        o();
        n().A(i12);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        n().H(charSequence);
    }

    @Override // androidx.view.r, android.app.Dialog
    public void setContentView(@N View view) {
        o();
        n().B(view);
    }

    @Override // android.app.Dialog
    public void setTitle(int i12) {
        super.setTitle(i12);
        n().H(getContext().getString(i12));
    }

    @Override // androidx.view.r, android.app.Dialog
    public void setContentView(@N View view, ViewGroup.LayoutParams layoutParams) {
        o();
        n().C(view, layoutParams);
    }

    @Override // androidx.appcompat.app.n
    public final void onSupportActionModeFinished(androidx.appcompat.view.b bVar) {
    }

    @Override // androidx.appcompat.app.n
    public final void onSupportActionModeStarted(androidx.appcompat.view.b bVar) {
    }
}
