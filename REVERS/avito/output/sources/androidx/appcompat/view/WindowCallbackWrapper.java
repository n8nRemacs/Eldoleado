package androidx.appcompat.view;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.RestrictTo;
import j.InterfaceC42164u;
import j.X;
import java.util.List;

@RestrictTo
/* loaded from: classes.dex */
public class WindowCallbackWrapper implements Window.Callback {
    final Window.Callback mWrapped;

    @X
    public static class a {
        @InterfaceC42164u
        public static boolean a(Window.Callback callback, SearchEvent searchEvent) {
            return callback.onSearchRequested(searchEvent);
        }

        @InterfaceC42164u
        public static ActionMode b(Window.Callback callback, ActionMode.Callback callback2, int i12) {
            return callback.onWindowStartingActionMode(callback2, i12);
        }
    }

    @X
    public static class b {
        @InterfaceC42164u
        public static void a(Window.Callback callback, List<KeyboardShortcutGroup> list, Menu menu, int i12) {
            callback.onProvideKeyboardShortcuts(list, menu, i12);
        }
    }

    @X
    public static class c {
        @InterfaceC42164u
        public static void a(Window.Callback callback, boolean z12) {
            callback.onPointerCaptureChanged(z12);
        }
    }

    public WindowCallbackWrapper(Window.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Window callback may not be null");
        }
        this.mWrapped = callback;
    }

    public final Window.Callback a() {
        return this.mWrapped;
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.mWrapped.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.mWrapped.dispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.mWrapped.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final void onActionModeFinished(ActionMode actionMode) {
        this.mWrapped.onActionModeFinished(actionMode);
    }

    @Override // android.view.Window.Callback
    public final void onActionModeStarted(ActionMode actionMode) {
        this.mWrapped.onActionModeStarted(actionMode);
    }

    @Override // android.view.Window.Callback
    public final void onAttachedToWindow() {
        this.mWrapped.onAttachedToWindow();
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
        this.mWrapped.onContentChanged();
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i12, Menu menu) {
        return this.mWrapped.onCreatePanelMenu(i12, menu);
    }

    @Override // android.view.Window.Callback
    public View onCreatePanelView(int i12) {
        return this.mWrapped.onCreatePanelView(i12);
    }

    @Override // android.view.Window.Callback
    public final void onDetachedFromWindow() {
        this.mWrapped.onDetachedFromWindow();
    }

    @Override // android.view.Window.Callback
    public final boolean onMenuItemSelected(int i12, MenuItem menuItem) {
        return this.mWrapped.onMenuItemSelected(i12, menuItem);
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i12, Menu menu) {
        return this.mWrapped.onMenuOpened(i12, menu);
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i12, Menu menu) {
        this.mWrapped.onPanelClosed(i12, menu);
    }

    @Override // android.view.Window.Callback
    @X
    public final void onPointerCaptureChanged(boolean z12) {
        c.a(this.mWrapped, z12);
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i12, View view, Menu menu) {
        return this.mWrapped.onPreparePanel(i12, view, menu);
    }

    @Override // android.view.Window.Callback
    @X
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i12) {
        b.a(this.mWrapped, list, menu, i12);
    }

    @Override // android.view.Window.Callback
    @X
    public final boolean onSearchRequested(SearchEvent searchEvent) {
        return a.a(this.mWrapped, searchEvent);
    }

    @Override // android.view.Window.Callback
    public final void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        this.mWrapped.onWindowAttributesChanged(layoutParams);
    }

    @Override // android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z12) {
        this.mWrapped.onWindowFocusChanged(z12);
    }

    @Override // android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.mWrapped.onWindowStartingActionMode(callback);
    }

    @Override // android.view.Window.Callback
    public final boolean onSearchRequested() {
        return this.mWrapped.onSearchRequested();
    }

    @Override // android.view.Window.Callback
    @X
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i12) {
        return a.b(this.mWrapped, callback, i12);
    }
}
