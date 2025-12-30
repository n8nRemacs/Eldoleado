package androidx.appcompat.widget;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: ActivityChooserView.java */
/* renamed from: androidx.appcompat.widget.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19945e extends View.AccessibilityDelegate {
    @Override // android.view.View.AccessibilityDelegate
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        accessibilityNodeInfo.setCanOpenPopup(true);
    }
}
