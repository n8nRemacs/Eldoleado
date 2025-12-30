package androidx.browser.customtabs;

import android.content.ComponentName;
import android.os.RemoteException;
import j.N;

/* compiled from: CustomTabsClient.java */
/* loaded from: classes.dex */
class d extends k {
    @Override // androidx.browser.customtabs.k
    public final void onCustomTabsServiceConnected(@N ComponentName componentName, @N f fVar) {
        fVar.getClass();
        try {
            fVar.f22862a.B2(0L);
        } catch (RemoteException unused) {
        }
        throw null;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
    }
}
