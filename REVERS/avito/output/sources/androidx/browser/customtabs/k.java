package androidx.browser.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.customtabs.b;
import androidx.annotation.RestrictTo;
import j.N;
import j.P;

/* compiled from: CustomTabsServiceConnection.java */
/* loaded from: classes.dex */
public abstract class k implements ServiceConnection {

    @P
    private Context mApplicationContext;

    /* compiled from: CustomTabsServiceConnection.java */
    public class a extends f {
    }

    public abstract void onCustomTabsServiceConnected(@N ComponentName componentName, @N f fVar);

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(@N ComponentName componentName, @N IBinder iBinder) {
        if (this.mApplicationContext == null) {
            throw new IllegalStateException("Custom Tabs Service connected before an applicationcontext has been provided.");
        }
        onCustomTabsServiceConnected(componentName, new a(b.AbstractBinderC1508b.e4(iBinder), componentName));
    }

    @RestrictTo
    public void setApplicationContext(@N Context context) {
        this.mApplicationContext = context;
    }
}
