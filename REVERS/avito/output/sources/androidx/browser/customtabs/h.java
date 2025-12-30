package androidx.browser.customtabs;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.customtabs.a;
import android.text.TextUtils;
import androidx.annotation.RestrictTo;
import androidx.browser.customtabs.b;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* compiled from: CustomTabsIntent.java */
/* loaded from: classes.dex */
public final class h {

    /* renamed from: a, reason: collision with root package name */
    @N
    public final Intent f22864a;

    /* renamed from: b, reason: collision with root package name */
    @P
    public final Bundle f22865b;

    /* compiled from: CustomTabsIntent.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface a {
    }

    /* compiled from: CustomTabsIntent.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface b {
    }

    /* compiled from: CustomTabsIntent.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface c {
    }

    /* compiled from: CustomTabsIntent.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface d {
    }

    /* compiled from: CustomTabsIntent.java */
    @X
    public static class e {
        @InterfaceC42164u
        @P
        public static Locale a(Intent intent) {
            String stringExtra = intent.getStringExtra("androidx.browser.customtabs.extra.TRANSLATE_LANGUAGE_TAG");
            if (stringExtra != null) {
                return Locale.forLanguageTag(stringExtra);
            }
            return null;
        }

        @InterfaceC42164u
        public static void b(Intent intent, Locale locale) {
            intent.putExtra("androidx.browser.customtabs.extra.TRANSLATE_LANGUAGE_TAG", locale.toLanguageTag());
        }
    }

    /* compiled from: CustomTabsIntent.java */
    @X
    public static class f {
        @InterfaceC42164u
        public static ActivityOptions a() {
            return ActivityOptions.makeBasic();
        }
    }

    /* compiled from: CustomTabsIntent.java */
    @X
    public static class g {
        @InterfaceC42164u
        @P
        public static String a() {
            LocaleList adjustedDefault = LocaleList.getAdjustedDefault();
            if (adjustedDefault.size() > 0) {
                return adjustedDefault.get(0).toLanguageTag();
            }
            return null;
        }
    }

    /* compiled from: CustomTabsIntent.java */
    @X
    /* renamed from: androidx.browser.customtabs.h$h, reason: collision with other inner class name */
    public static class C1526h {
        @InterfaceC42164u
        public static void a(ActivityOptions activityOptions, boolean z12) {
            activityOptions.setShareIdentityEnabled(z12);
        }
    }

    /* compiled from: CustomTabsIntent.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface j {
    }

    /* compiled from: CustomTabsIntent.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface k {
    }

    /* compiled from: CustomTabsIntent.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface l {
    }

    public h(@N Intent intent, @P Bundle bundle) {
        this.f22864a = intent;
        this.f22865b = bundle;
    }

    /* compiled from: CustomTabsIntent.java */
    public static final class i {

        /* renamed from: a, reason: collision with root package name */
        public final Intent f22866a;

        /* renamed from: b, reason: collision with root package name */
        public final b.a f22867b;

        /* renamed from: c, reason: collision with root package name */
        @P
        public ActivityOptions f22868c;

        /* renamed from: d, reason: collision with root package name */
        @P
        public Bundle f22869d;

        /* renamed from: e, reason: collision with root package name */
        public int f22870e;

        /* renamed from: f, reason: collision with root package name */
        public final boolean f22871f;

        public i() {
            this.f22866a = new Intent("android.intent.action.VIEW");
            this.f22867b = new b.a();
            this.f22870e = 0;
            this.f22871f = true;
        }

        @N
        public final h a() {
            Intent intent = this.f22866a;
            if (!intent.hasExtra("android.support.customtabs.extra.SESSION")) {
                Bundle bundle = new Bundle();
                bundle.putBinder("android.support.customtabs.extra.SESSION", null);
                intent.putExtras(bundle);
            }
            intent.putExtra("android.support.customtabs.extra.EXTRA_ENABLE_INSTANT_APPS", this.f22871f);
            intent.putExtras(new androidx.browser.customtabs.b(this.f22867b.f22826a).a());
            Bundle bundle2 = this.f22869d;
            if (bundle2 != null) {
                intent.putExtras(bundle2);
            }
            intent.putExtra("androidx.browser.customtabs.extra.SHARE_STATE", this.f22870e);
            int i12 = Build.VERSION.SDK_INT;
            String strA = g.a();
            if (!TextUtils.isEmpty(strA)) {
                Bundle bundleExtra = intent.hasExtra("com.android.browser.headers") ? intent.getBundleExtra("com.android.browser.headers") : new Bundle();
                if (!bundleExtra.containsKey("Accept-Language")) {
                    bundleExtra.putString("Accept-Language", strA);
                    intent.putExtra("com.android.browser.headers", bundleExtra);
                }
            }
            if (i12 >= 34) {
                if (this.f22868c == null) {
                    this.f22868c = f.a();
                }
                C1526h.a(this.f22868c, false);
            }
            ActivityOptions activityOptions = this.f22868c;
            return new h(intent, activityOptions != null ? activityOptions.toBundle() : null);
        }

        @N
        public final void b(boolean z12) {
            this.f22866a.putExtra("android.support.customtabs.extra.TITLE_VISIBILITY", z12 ? 1 : 0);
        }

        public i(@P n nVar) {
            Intent intent = new Intent("android.intent.action.VIEW");
            this.f22866a = intent;
            this.f22867b = new b.a();
            this.f22870e = 0;
            this.f22871f = true;
            if (nVar != null) {
                intent.setPackage(nVar.f22879c.getPackageName());
                a.b bVar = (a.b) nVar.f22878b;
                Bundle bundle = new Bundle();
                bundle.putBinder("android.support.customtabs.extra.SESSION", bVar);
                intent.putExtras(bundle);
            }
        }
    }
}
