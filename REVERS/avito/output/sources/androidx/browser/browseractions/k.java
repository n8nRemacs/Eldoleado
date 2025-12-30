package androidx.browser.browseractions;

import android.os.AsyncTask;
import androidx.annotation.RestrictTo;
import androidx.core.content.FileProvider;
import java.util.concurrent.TimeUnit;

/* compiled from: BrowserServiceFileProvider.java */
@RestrictTo
@Deprecated
/* loaded from: classes.dex */
public final class k extends FileProvider {

    /* renamed from: b, reason: collision with root package name */
    public static final Object f22821b = new Object();

    /* compiled from: BrowserServiceFileProvider.java */
    public static class a extends AsyncTask<Void, Void, Void> {

        /* renamed from: a, reason: collision with root package name */
        public static final long f22822a;

        /* renamed from: b, reason: collision with root package name */
        public static final long f22823b;

        /* renamed from: c, reason: collision with root package name */
        public static final long f22824c;

        static {
            TimeUnit timeUnit = TimeUnit.DAYS;
            f22822a = timeUnit.toMillis(7L);
            f22823b = timeUnit.toMillis(7L);
            f22824c = timeUnit.toMillis(1L);
        }

        @Override // android.os.AsyncTask
        public final Void doInBackground(Void[] voidArr) {
            throw null;
        }
    }

    /* compiled from: BrowserServiceFileProvider.java */
    public static class b extends AsyncTask<String, Void, Void> {
        @Override // android.os.AsyncTask
        public final Void doInBackground(String[] strArr) {
            throw null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Void r12) {
            new a();
            throw null;
        }
    }
}
