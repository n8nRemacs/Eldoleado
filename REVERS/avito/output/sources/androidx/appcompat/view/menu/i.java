package androidx.appcompat.view.menu;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import androidx.appcompat.view.menu.o;
import j.N;

/* compiled from: MenuDialogHelper.java */
/* loaded from: classes.dex */
class i implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, o.a {

    /* renamed from: b, reason: collision with root package name */
    public t f21855b;

    /* renamed from: c, reason: collision with root package name */
    public androidx.appcompat.app.l f21856c;

    /* renamed from: d, reason: collision with root package name */
    public f f21857d;

    @Override // androidx.appcompat.view.menu.o.a
    public final void b(@N h hVar, boolean z12) {
        androidx.appcompat.app.l lVar;
        if ((z12 || hVar == this.f21855b) && (lVar = this.f21856c) != null) {
            lVar.dismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.o.a
    public final boolean c(@N h hVar) {
        return false;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i12) {
        this.f21855b.q((k) this.f21857d.a().getItem(i12), null, 0);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.f21857d.b(this.f21855b, true);
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i12, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        t tVar = this.f21855b;
        if (i12 == 82 || i12 == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.f21856c.getWindow();
                if (window2 != null && (decorView2 = window2.getDecorView()) != null && (keyDispatcherState2 = decorView2.getKeyDispatcherState()) != null) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.f21856c.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                tVar.c(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return tVar.performShortcut(i12, keyEvent, 0);
    }
}
