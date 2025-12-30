package androidx.browser.trusted;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.support.customtabs.trusted.a;
import android.support.customtabs.trusted.b;
import androidx.browser.trusted.e;
import androidx.core.app.E;
import com.avito.android.remote.model.notifications_settings.NotificationsSettings;
import j.InterfaceC42151g;
import j.InterfaceC42153i;
import j.K;
import j.N;
import j.P;
import j.a0;
import java.io.IOException;
import java.util.Locale;

/* compiled from: TrustedWebActivityService.java */
/* loaded from: classes.dex */
public abstract class o extends Service {

    /* renamed from: b, reason: collision with root package name */
    public NotificationManager f22885b;

    /* renamed from: c, reason: collision with root package name */
    public int f22886c = -1;

    /* renamed from: d, reason: collision with root package name */
    public final b.AbstractBinderC1512b f22887d = new a();

    /* compiled from: TrustedWebActivityService.java */
    public class a extends b.AbstractBinderC1512b {
        public a() {
        }

        @Override // android.support.customtabs.trusted.b
        public final Bundle G3() {
            e4();
            NotificationManager notificationManager = o.this.f22885b;
            if (notificationManager == null) {
                throw new IllegalStateException("TrustedWebActivityService has not been properly initialized. Did onCreate() call super.onCreate()?");
            }
            StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("android.support.customtabs.trusted.ACTIVE_NOTIFICATIONS", activeNotifications);
            return bundle;
        }

        @Override // android.support.customtabs.trusted.b
        public final int R3() {
            e4();
            return o.this.c();
        }

        @Override // android.support.customtabs.trusted.b
        @a0
        public final Bundle S(Bundle bundle) {
            Notification notificationBuild;
            e4();
            q.a(bundle, "android.support.customtabs.trusted.PLATFORM_TAG");
            q.a(bundle, "android.support.customtabs.trusted.PLATFORM_ID");
            q.a(bundle, "android.support.customtabs.trusted.NOTIFICATION");
            q.a(bundle, "android.support.customtabs.trusted.CHANNEL_NAME");
            String string = bundle.getString("android.support.customtabs.trusted.PLATFORM_TAG");
            int i12 = bundle.getInt("android.support.customtabs.trusted.PLATFORM_ID");
            Notification notification = (Notification) bundle.getParcelable("android.support.customtabs.trusted.NOTIFICATION");
            String string2 = bundle.getString("android.support.customtabs.trusted.CHANNEL_NAME");
            o oVar = o.this;
            if (oVar.f22885b == null) {
                throw new IllegalStateException("TrustedWebActivityService has not been properly initialized. Did onCreate() call super.onCreate()?");
            }
            boolean z12 = false;
            if (new E(oVar).f44481b.areNotificationsEnabled()) {
                String strA = o.a(string2);
                NotificationManager notificationManager = oVar.f22885b;
                notificationManager.createNotificationChannel(new NotificationChannel(strA, string2, 3));
                if (notificationManager.getNotificationChannel(strA).getImportance() == 0) {
                    notificationBuild = null;
                } else {
                    Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(oVar, notification);
                    builderRecoverBuilder.setChannelId(strA);
                    notificationBuild = builderRecoverBuilder.build();
                }
                NotificationChannel notificationChannel = oVar.f22885b.getNotificationChannel(strA);
                if (notificationChannel == null || notificationChannel.getImportance() != 0) {
                    oVar.f22885b.notify(string, i12, notificationBuild);
                    z12 = true;
                }
            }
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("android.support.customtabs.trusted.NOTIFICATION_SUCCESS", z12);
            return bundle2;
        }

        @Override // android.support.customtabs.trusted.b
        public final Bundle S3(Bundle bundle) {
            NotificationChannel notificationChannel;
            e4();
            q.a(bundle, "android.support.customtabs.trusted.CHANNEL_NAME");
            String string = bundle.getString("android.support.customtabs.trusted.CHANNEL_NAME");
            o oVar = o.this;
            if (oVar.f22885b == null) {
                throw new IllegalStateException("TrustedWebActivityService has not been properly initialized. Did onCreate() call super.onCreate()?");
            }
            boolean z12 = false;
            if (new E(oVar).f44481b.areNotificationsEnabled() && ((notificationChannel = oVar.f22885b.getNotificationChannel(o.a(string))) == null || notificationChannel.getImportance() != 0)) {
                z12 = true;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("android.support.customtabs.trusted.NOTIFICATION_SUCCESS", z12);
            return bundle2;
        }

        @Override // android.support.customtabs.trusted.b
        public final void V3(Bundle bundle) {
            e4();
            q.a(bundle, "android.support.customtabs.trusted.PLATFORM_TAG");
            q.a(bundle, "android.support.customtabs.trusted.PLATFORM_ID");
            String string = bundle.getString("android.support.customtabs.trusted.PLATFORM_TAG");
            int i12 = bundle.getInt("android.support.customtabs.trusted.PLATFORM_ID");
            NotificationManager notificationManager = o.this.f22885b;
            if (notificationManager == null) {
                throw new IllegalStateException("TrustedWebActivityService has not been properly initialized. Did onCreate() call super.onCreate()?");
            }
            notificationManager.cancel(string, i12);
        }

        public final void e4() {
            boolean zA2;
            o oVar = o.this;
            if (oVar.f22886c == -1) {
                String[] packagesForUid = oVar.getPackageManager().getPackagesForUid(Binder.getCallingUid());
                if (packagesForUid == null) {
                    packagesForUid = new String[0];
                }
                g gVarLoad = oVar.b().load();
                PackageManager packageManager = oVar.getPackageManager();
                if (gVarLoad != null) {
                    int length = packagesForUid.length;
                    int i12 = 0;
                    while (true) {
                        if (i12 >= length) {
                            break;
                        }
                        try {
                            zA2 = (Build.VERSION.SDK_INT >= 28 ? new e.a() : new e.b()).a(packageManager, packagesForUid[i12]);
                        } catch (PackageManager.NameNotFoundException | IOException unused) {
                            zA2 = false;
                        }
                        if (zA2) {
                            oVar.f22886c = Binder.getCallingUid();
                            break;
                        }
                        i12++;
                    }
                }
            }
            if (oVar.f22886c != Binder.getCallingUid()) {
                throw new SecurityException("Caller is not verified as Trusted Web Activity provider.");
            }
        }

        @Override // android.support.customtabs.trusted.b
        public final Bundle h3() {
            e4();
            o oVar = o.this;
            int iC2 = oVar.c();
            Bundle bundle = new Bundle();
            if (iC2 != -1) {
                bundle.putParcelable("android.support.customtabs.trusted.SMALL_ICON_BITMAP", BitmapFactory.decodeResource(oVar.getResources(), iC2));
            }
            return bundle;
        }

        @Override // android.support.customtabs.trusted.b
        public final void n2(IBinder iBinder) {
            e4();
            if (iBinder == null) {
                return;
            }
            a.b.e4(iBinder);
        }
    }

    public static String a(String str) {
        return str.toLowerCase(Locale.ROOT).replace(' ', '_') + "_channel_id";
    }

    @N
    @InterfaceC42151g
    public abstract i b();

    @InterfaceC42151g
    public final int c() {
        try {
            Bundle bundle = getPackageManager().getServiceInfo(new ComponentName(this, getClass()), 128).metaData;
            if (bundle == null) {
                return -1;
            }
            return bundle.getInt("android.support.customtabs.trusted.SMALL_ICON", -1);
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    @Override // android.app.Service
    @K
    @P
    public final IBinder onBind(@P Intent intent) {
        return this.f22887d;
    }

    @Override // android.app.Service
    @InterfaceC42153i
    @K
    public final void onCreate() {
        super.onCreate();
        this.f22885b = (NotificationManager) getSystemService(NotificationsSettings.Section.SECTION_PAID_SERVICES);
    }

    @Override // android.app.Service
    @K
    public final boolean onUnbind(@P Intent intent) {
        this.f22886c = -1;
        return super.onUnbind(intent);
    }
}
