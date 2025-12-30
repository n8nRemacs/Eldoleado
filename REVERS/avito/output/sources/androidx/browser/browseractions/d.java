package androidx.browser.browseractions;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;

/* compiled from: BrowserActionsFallbackMenuDialog.java */
@Deprecated
/* loaded from: classes.dex */
class d extends Dialog {
    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        throw null;
    }

    @Override // android.app.Dialog
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        throw null;
    }

    @Override // android.app.Dialog
    public final void show() {
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        throw null;
    }
}
