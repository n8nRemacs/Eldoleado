package androidx.browser.customtabs;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.RemoteException;
import com.vk.id.internal.auth.AuthActivity;
import com.vk.id.internal.auth.AuthActivity$callback$1;
import j.N;
import j.P;
import java.util.ArrayList;

/* compiled from: CustomTabsClient.java */
/* loaded from: classes.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    public final android.support.customtabs.b f22862a;

    /* renamed from: b, reason: collision with root package name */
    public final ComponentName f22863b;

    public f(android.support.customtabs.b bVar, ComponentName componentName) {
        this.f22862a = bVar;
        this.f22863b = componentName;
    }

    @P
    public static String a(@N AuthActivity authActivity) {
        PackageManager packageManager = authActivity.getPackageManager();
        ArrayList<String> arrayList = new ArrayList();
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://")), 0);
        if (resolveInfoResolveActivity != null) {
            String str = resolveInfoResolveActivity.activityInfo.packageName;
            ArrayList arrayList2 = new ArrayList(arrayList.size() + 1);
            arrayList2.add(str);
            arrayList = arrayList2;
        }
        Intent intent = new Intent("android.support.customtabs.action.CustomTabsService");
        for (String str2 : arrayList) {
            intent.setPackage(str2);
            if (packageManager.resolveService(intent, 0) != null) {
                return str2;
            }
        }
        return null;
    }

    @P
    public final n b(@P AuthActivity$callback$1 authActivity$callback$1) {
        e eVar = new e(authActivity$callback$1);
        android.support.customtabs.b bVar = this.f22862a;
        try {
            if (bVar.J2(eVar)) {
                return new n(bVar, eVar, this.f22863b);
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }
}
