package androidx.browser.customtabs;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.customtabs.d;
import j.N;
import j.P;

/* compiled from: PostMessageServiceConnection.java */
/* loaded from: classes.dex */
public abstract class u implements s, ServiceConnection {

    /* renamed from: b, reason: collision with root package name */
    @P
    public android.support.customtabs.d f22884b;

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(@N ComponentName componentName, @N IBinder iBinder) {
        this.f22884b = d.b.e4(iBinder);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(@N ComponentName componentName) {
    }
}
