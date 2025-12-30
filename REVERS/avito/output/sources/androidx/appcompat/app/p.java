package androidx.appcompat.app;

import android.app.LocaleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.AttributeSet;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppLocalesMetadataHolderService;
import androidx.appcompat.app.p;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.AbstractC20248z;
import androidx.collection.C20203c;
import androidx.core.app.C22754l;
import j.InterfaceC42153i;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;
import j.f0;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: AppCompatDelegate.java */
/* loaded from: classes.dex */
public abstract class p {

    /* renamed from: b, reason: collision with root package name */
    public static final d f21559b = new d(new e());

    /* renamed from: c, reason: collision with root package name */
    public static int f21560c = -100;

    /* renamed from: d, reason: collision with root package name */
    public static androidx.core.os.q f21561d = null;

    /* renamed from: e, reason: collision with root package name */
    public static androidx.core.os.q f21562e = null;

    /* renamed from: f, reason: collision with root package name */
    public static Boolean f21563f = null;

    /* renamed from: g, reason: collision with root package name */
    public static boolean f21564g = false;

    /* renamed from: h, reason: collision with root package name */
    public static final C20203c<WeakReference<p>> f21565h = new C20203c<>();

    /* renamed from: i, reason: collision with root package name */
    public static final Object f21566i = new Object();

    /* renamed from: j, reason: collision with root package name */
    public static final Object f21567j = new Object();

    /* compiled from: AppCompatDelegate.java */
    @X
    public static class a {
        @InterfaceC42164u
        public static LocaleList a(String str) {
            return LocaleList.forLanguageTags(str);
        }
    }

    /* compiled from: AppCompatDelegate.java */
    @X
    public static class b {
        @InterfaceC42164u
        public static LocaleList a(Object obj) {
            return ((LocaleManager) obj).getApplicationLocales();
        }

        @InterfaceC42164u
        public static void b(Object obj, LocaleList localeList) {
            ((LocaleManager) obj).setApplicationLocales(localeList);
        }
    }

    /* compiled from: AppCompatDelegate.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface c {
    }

    /* compiled from: AppCompatDelegate.java */
    public static class d implements Executor {

        /* renamed from: b, reason: collision with root package name */
        public final Object f21568b = new Object();

        /* renamed from: c, reason: collision with root package name */
        public final ArrayDeque f21569c = new ArrayDeque();

        /* renamed from: d, reason: collision with root package name */
        public final Executor f21570d;

        /* renamed from: e, reason: collision with root package name */
        public Runnable f21571e;

        public d(Executor executor) {
            this.f21570d = executor;
        }

        public final void a() {
            synchronized (this.f21568b) {
                try {
                    Runnable runnable = (Runnable) this.f21569c.poll();
                    this.f21571e = runnable;
                    if (runnable != null) {
                        ((e) this.f21570d).execute(runnable);
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        @Override // java.util.concurrent.Executor
        public final void execute(final Runnable runnable) {
            synchronized (this.f21568b) {
                try {
                    this.f21569c.add(new Runnable() { // from class: androidx.appcompat.app.q
                        @Override // java.lang.Runnable
                        public final void run() {
                            Runnable runnable2 = runnable;
                            p.d dVar = this.f21572b;
                            dVar.getClass();
                            try {
                                runnable2.run();
                            } finally {
                                dVar.a();
                            }
                        }
                    });
                    if (this.f21571e == null) {
                        a();
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    /* compiled from: AppCompatDelegate.java */
    public static class e implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    public static void D(int i12) {
        if ((i12 == -1 || i12 == 0 || i12 == 1 || i12 == 2 || i12 == 3) && f21560c != i12) {
            f21560c = i12;
            synchronized (f21566i) {
                try {
                    Iterator<WeakReference<p>> it = f21565h.iterator();
                    while (true) {
                        AbstractC20248z abstractC20248z = (AbstractC20248z) it;
                        if (abstractC20248z.hasNext()) {
                            p pVar = (p) ((WeakReference) abstractC20248z.next()).get();
                            if (pVar != null) {
                                pVar.e();
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    public static void I(Context context) {
        if (p(context)) {
            if (Build.VERSION.SDK_INT >= 33) {
                if (f21564g) {
                    return;
                }
                f21559b.execute(new o(context, 0));
                return;
            }
            synchronized (f21567j) {
                try {
                    androidx.core.os.q qVar = f21561d;
                    if (qVar == null) {
                        if (f21562e == null) {
                            f21562e = androidx.core.os.q.a(C22754l.b(context));
                        }
                        if (f21562e.c()) {
                        } else {
                            f21561d = f21562e;
                        }
                    } else if (!qVar.equals(f21562e)) {
                        androidx.core.os.q qVar2 = f21561d;
                        f21562e = qVar2;
                        C22754l.a(context, qVar2.e());
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    @X
    public static Object k() {
        Context contextI;
        Iterator<WeakReference<p>> it = f21565h.iterator();
        while (true) {
            AbstractC20248z abstractC20248z = (AbstractC20248z) it;
            if (!abstractC20248z.hasNext()) {
                return null;
            }
            p pVar = (p) ((WeakReference) abstractC20248z.next()).get();
            if (pVar != null && (contextI = pVar.i()) != null) {
                return contextI.getSystemService("locale");
            }
        }
    }

    public static boolean p(Context context) {
        if (f21563f == null) {
            try {
                int i12 = AppLocalesMetadataHolderService.f21472b;
                Bundle bundle = context.getPackageManager().getServiceInfo(new ComponentName(context, (Class<?>) AppLocalesMetadataHolderService.class), AppLocalesMetadataHolderService.a.a() | 128).metaData;
                if (bundle != null) {
                    f21563f = Boolean.valueOf(bundle.getBoolean("autoStoreLocales"));
                }
            } catch (PackageManager.NameNotFoundException unused) {
                f21563f = Boolean.FALSE;
            }
        }
        return f21563f.booleanValue();
    }

    public static void x(@N p pVar) {
        synchronized (f21566i) {
            try {
                Iterator<WeakReference<p>> it = f21565h.iterator();
                while (true) {
                    AbstractC20248z abstractC20248z = (AbstractC20248z) it;
                    if (abstractC20248z.hasNext()) {
                        p pVar2 = (p) ((WeakReference) abstractC20248z.next()).get();
                        if (pVar2 == pVar || pVar2 == null) {
                            abstractC20248z.remove();
                        }
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public static void z(@N androidx.core.os.q qVar) {
        if (Build.VERSION.SDK_INT >= 33) {
            Object objK = k();
            if (objK != null) {
                b.b(objK, a.a(qVar.e()));
                return;
            }
            return;
        }
        if (qVar.equals(f21561d)) {
            return;
        }
        synchronized (f21566i) {
            f21561d = qVar;
            Iterator<WeakReference<p>> it = f21565h.iterator();
            while (true) {
                AbstractC20248z abstractC20248z = (AbstractC20248z) it;
                if (abstractC20248z.hasNext()) {
                    p pVar = (p) ((WeakReference) abstractC20248z.next()).get();
                    if (pVar != null) {
                        pVar.d();
                    }
                }
            }
        }
    }

    public abstract void A(@j.I int i12);

    public abstract void B(View view);

    public abstract void C(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void E(int i12);

    public abstract void F(@P Toolbar toolbar);

    public abstract void H(@P CharSequence charSequence);

    public abstract void c(View view, ViewGroup.LayoutParams layoutParams);

    public abstract boolean e();

    public abstract View g(String str, @N Context context, @N AttributeSet attributeSet);

    @P
    public abstract <T extends View> T h(@j.D int i12);

    @P
    public Context i() {
        return null;
    }

    public int j() {
        return -100;
    }

    public abstract MenuInflater l();

    @P
    public abstract AbstractC19927a m();

    public abstract void n();

    public abstract void o();

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return g(str, context, attributeSet);
    }

    public abstract void q(Configuration configuration);

    public abstract void r();

    public abstract void s();

    public abstract void t();

    public abstract void u();

    public abstract void v();

    public abstract void w();

    public abstract boolean y(int i12);

    public void d() {
    }

    public void G(@f0 int i12) {
    }

    @InterfaceC42153i
    @N
    public Context f(@N Context context) {
        return context;
    }
}
