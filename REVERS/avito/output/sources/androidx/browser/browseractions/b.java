package androidx.browser.browseractions;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import j.N;
import java.util.concurrent.Executor;

/* compiled from: BrowserActionsFallbackMenuAdapter.java */
@Deprecated
/* loaded from: classes.dex */
class b extends BaseAdapter {

    /* compiled from: BrowserActionsFallbackMenuAdapter.java */
    public class a implements Runnable {
        public a() {
            throw null;
        }

        @Override // java.lang.Runnable
        public final void run() {
            throw null;
        }
    }

    /* compiled from: BrowserActionsFallbackMenuAdapter.java */
    /* renamed from: androidx.browser.browseractions.b$b, reason: collision with other inner class name */
    public class ExecutorC1524b implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(@N Runnable runnable) {
            runnable.run();
        }
    }

    /* compiled from: BrowserActionsFallbackMenuAdapter.java */
    public static class c {
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        throw null;
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i12) {
        throw null;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i12) {
        return i12;
    }

    @Override // android.widget.Adapter
    public final View getView(int i12, View view, ViewGroup viewGroup) {
        throw null;
    }
}
