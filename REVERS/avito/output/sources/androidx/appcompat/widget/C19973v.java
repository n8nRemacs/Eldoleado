package androidx.appcompat.widget;

import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

/* compiled from: AppCompatHintHelper.java */
/* renamed from: androidx.appcompat.widget.v, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19973v {
    public static void a(InputConnection inputConnection, EditorInfo editorInfo, TextView textView) {
        if (inputConnection == null || editorInfo.hintText != null) {
            return;
        }
        for (ViewParent parent = textView.getParent(); parent instanceof View; parent = parent.getParent()) {
            if (parent instanceof P0) {
                editorInfo.hintText = ((P0) parent).getHint();
                return;
            }
        }
    }
}
