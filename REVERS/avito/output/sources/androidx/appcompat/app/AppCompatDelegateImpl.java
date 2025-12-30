package androidx.appcompat.app;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.C19929c;
import androidx.appcompat.app.G;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.b;
import androidx.appcompat.view.f;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.C19962m0;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.T;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.C20204c0;
import androidx.collection.o1;
import androidx.core.content.res.i;
import androidx.core.view.A0;
import androidx.core.view.C0;
import androidx.core.view.C22823h0;
import j.InterfaceC42153i;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;
import j.f0;
import j.k0;
import java.lang.Thread;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.WeakHashMap;
import l.C43521a;
import m.C43852a;
import okhttp3.internal.ws.WebSocketProtocol;

@RestrictTo
/* loaded from: classes.dex */
class AppCompatDelegateImpl extends androidx.appcompat.app.p implements h.a, LayoutInflater.Factory2 {

    /* renamed from: j0, reason: collision with root package name */
    public static final o1<String, Integer> f21380j0 = new o1<>();

    /* renamed from: k0, reason: collision with root package name */
    public static final int[] f21381k0 = {R.attr.windowBackground};

    /* renamed from: l0, reason: collision with root package name */
    public static final boolean f21382l0 = !"robolectric".equals(Build.FINGERPRINT);

    /* renamed from: B, reason: collision with root package name */
    public boolean f21384B;

    /* renamed from: C, reason: collision with root package name */
    public ViewGroup f21385C;

    /* renamed from: D, reason: collision with root package name */
    public TextView f21386D;

    /* renamed from: E, reason: collision with root package name */
    public View f21387E;

    /* renamed from: F, reason: collision with root package name */
    public boolean f21388F;

    /* renamed from: G, reason: collision with root package name */
    public boolean f21389G;

    /* renamed from: H, reason: collision with root package name */
    public boolean f21390H;

    /* renamed from: I, reason: collision with root package name */
    public boolean f21391I;

    /* renamed from: J, reason: collision with root package name */
    public boolean f21392J;

    /* renamed from: K, reason: collision with root package name */
    public boolean f21393K;

    /* renamed from: L, reason: collision with root package name */
    public boolean f21394L;

    /* renamed from: M, reason: collision with root package name */
    public boolean f21395M;

    /* renamed from: N, reason: collision with root package name */
    public PanelFeatureState[] f21396N;

    /* renamed from: O, reason: collision with root package name */
    public PanelFeatureState f21397O;

    /* renamed from: P, reason: collision with root package name */
    public boolean f21398P;

    /* renamed from: Q, reason: collision with root package name */
    public boolean f21399Q;

    /* renamed from: R, reason: collision with root package name */
    public boolean f21400R;

    /* renamed from: S, reason: collision with root package name */
    public boolean f21401S;

    /* renamed from: T, reason: collision with root package name */
    public Configuration f21402T;

    /* renamed from: U, reason: collision with root package name */
    public int f21403U;

    /* renamed from: V, reason: collision with root package name */
    public int f21404V;

    /* renamed from: W, reason: collision with root package name */
    public int f21405W;

    /* renamed from: X, reason: collision with root package name */
    public boolean f21406X;

    /* renamed from: Y, reason: collision with root package name */
    public n f21407Y;

    /* renamed from: Z, reason: collision with root package name */
    public l f21408Z;

    /* renamed from: a0, reason: collision with root package name */
    public boolean f21409a0;

    /* renamed from: b0, reason: collision with root package name */
    public int f21410b0;

    /* renamed from: d0, reason: collision with root package name */
    public boolean f21412d0;

    /* renamed from: e0, reason: collision with root package name */
    public Rect f21413e0;

    /* renamed from: f0, reason: collision with root package name */
    public Rect f21414f0;

    /* renamed from: g0, reason: collision with root package name */
    public B f21415g0;

    /* renamed from: h0, reason: collision with root package name */
    public OnBackInvokedDispatcher f21416h0;

    /* renamed from: i0, reason: collision with root package name */
    public OnBackInvokedCallback f21417i0;

    /* renamed from: k, reason: collision with root package name */
    public final Object f21418k;

    /* renamed from: l, reason: collision with root package name */
    public final Context f21419l;

    /* renamed from: m, reason: collision with root package name */
    public Window f21420m;

    /* renamed from: n, reason: collision with root package name */
    public k f21421n;

    /* renamed from: o, reason: collision with root package name */
    public final Object f21422o;

    /* renamed from: p, reason: collision with root package name */
    public AbstractC19927a f21423p;

    /* renamed from: q, reason: collision with root package name */
    public androidx.appcompat.view.g f21424q;

    /* renamed from: r, reason: collision with root package name */
    public CharSequence f21425r;

    /* renamed from: s, reason: collision with root package name */
    public T f21426s;

    /* renamed from: t, reason: collision with root package name */
    public e f21427t;

    /* renamed from: u, reason: collision with root package name */
    public p f21428u;

    /* renamed from: v, reason: collision with root package name */
    public androidx.appcompat.view.b f21429v;

    /* renamed from: w, reason: collision with root package name */
    public ActionBarContextView f21430w;

    /* renamed from: x, reason: collision with root package name */
    public PopupWindow f21431x;

    /* renamed from: y, reason: collision with root package name */
    public Runnable f21432y;

    /* renamed from: z, reason: collision with root package name */
    public A0 f21433z = null;

    /* renamed from: A, reason: collision with root package name */
    public final boolean f21383A = true;

    /* renamed from: c0, reason: collision with root package name */
    public final Runnable f21411c0 = new b();

    public static final class PanelFeatureState {

        /* renamed from: a, reason: collision with root package name */
        public int f21434a;

        /* renamed from: b, reason: collision with root package name */
        public int f21435b;

        /* renamed from: c, reason: collision with root package name */
        public int f21436c;

        /* renamed from: d, reason: collision with root package name */
        public int f21437d;

        /* renamed from: e, reason: collision with root package name */
        public ViewGroup f21438e;

        /* renamed from: f, reason: collision with root package name */
        public View f21439f;

        /* renamed from: g, reason: collision with root package name */
        public View f21440g;

        /* renamed from: h, reason: collision with root package name */
        public androidx.appcompat.view.menu.h f21441h;

        /* renamed from: i, reason: collision with root package name */
        public androidx.appcompat.view.menu.f f21442i;

        /* renamed from: j, reason: collision with root package name */
        public androidx.appcompat.view.d f21443j;

        /* renamed from: k, reason: collision with root package name */
        public boolean f21444k;

        /* renamed from: l, reason: collision with root package name */
        public boolean f21445l;

        /* renamed from: m, reason: collision with root package name */
        public boolean f21446m;

        /* renamed from: n, reason: collision with root package name */
        public boolean f21447n;

        /* renamed from: o, reason: collision with root package name */
        public boolean f21448o;

        /* renamed from: p, reason: collision with root package name */
        public Bundle f21449p;

        @SuppressLint({"BanParcelableUsage"})
        public static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new a();

            /* renamed from: b, reason: collision with root package name */
            public int f21450b;

            /* renamed from: c, reason: collision with root package name */
            public boolean f21451c;

            /* renamed from: d, reason: collision with root package name */
            public Bundle f21452d;

            public class a implements Parcelable.ClassLoaderCreator<SavedState> {
                @Override // android.os.Parcelable.ClassLoaderCreator
                public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.a(parcel, classLoader);
                }

                @Override // android.os.Parcelable.Creator
                public final Object[] newArray(int i12) {
                    return new SavedState[i12];
                }

                @Override // android.os.Parcelable.Creator
                public final Object createFromParcel(Parcel parcel) {
                    return SavedState.a(parcel, null);
                }
            }

            public static SavedState a(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.f21450b = parcel.readInt();
                boolean z12 = parcel.readInt() == 1;
                savedState.f21451c = z12;
                if (z12) {
                    savedState.f21452d = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            @Override // android.os.Parcelable
            public final int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i12) {
                parcel.writeInt(this.f21450b);
                parcel.writeInt(this.f21451c ? 1 : 0);
                if (this.f21451c) {
                    parcel.writeBundle(this.f21452d);
                }
            }
        }
    }

    public class a implements Thread.UncaughtExceptionHandler {
        public a() {
            throw null;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public final void uncaughtException(@N Thread thread, @N Throwable th2) {
            boolean z12 = th2 instanceof Resources.NotFoundException;
            throw null;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl.f21410b0 & 1) != 0) {
                appCompatDelegateImpl.R(0);
            }
            if ((appCompatDelegateImpl.f21410b0 & 4096) != 0) {
                appCompatDelegateImpl.R(108);
            }
            appCompatDelegateImpl.f21409a0 = false;
            appCompatDelegateImpl.f21410b0 = 0;
        }
    }

    public class c implements C19929c.a {
    }

    public interface d {
    }

    public final class e implements o.a {
        public e() {
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final void b(@N androidx.appcompat.view.menu.h hVar, boolean z12) {
            AppCompatDelegateImpl.this.N(hVar);
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final boolean c(@N androidx.appcompat.view.menu.h hVar) {
            Window.Callback callback = AppCompatDelegateImpl.this.f21420m.getCallback();
            if (callback == null) {
                return true;
            }
            callback.onMenuOpened(108, hVar);
            return true;
        }
    }

    public class f implements b.a {

        /* renamed from: a, reason: collision with root package name */
        public final f.a f21455a;

        public class a extends C0 {
            public a() {
            }

            @Override // androidx.core.view.C0, androidx.core.view.B0
            public final void a(View view) {
                f fVar = f.this;
                AppCompatDelegateImpl.this.f21430w.setVisibility(8);
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                PopupWindow popupWindow = appCompatDelegateImpl.f21431x;
                if (popupWindow != null) {
                    popupWindow.dismiss();
                } else if (appCompatDelegateImpl.f21430w.getParent() instanceof View) {
                    C22823h0.A((View) appCompatDelegateImpl.f21430w.getParent());
                }
                appCompatDelegateImpl.f21430w.g();
                appCompatDelegateImpl.f21433z.e(null);
                appCompatDelegateImpl.f21433z = null;
                C22823h0.A(appCompatDelegateImpl.f21385C);
            }
        }

        public f(f.a aVar) {
            this.f21455a = aVar;
        }

        @Override // androidx.appcompat.view.b.a
        public final boolean a(androidx.appcompat.view.b bVar, MenuItem menuItem) {
            return this.f21455a.a(bVar, menuItem);
        }

        /* JADX WARN: Type inference failed for: r0v3, types: [androidx.appcompat.app.n, java.lang.Object] */
        @Override // androidx.appcompat.view.b.a
        public final void b(androidx.appcompat.view.b bVar) {
            this.f21455a.b(bVar);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.f21431x != null) {
                appCompatDelegateImpl.f21420m.getDecorView().removeCallbacks(appCompatDelegateImpl.f21432y);
            }
            if (appCompatDelegateImpl.f21430w != null) {
                A0 a02 = appCompatDelegateImpl.f21433z;
                if (a02 != null) {
                    a02.b();
                }
                A0 a0A = C22823h0.a(appCompatDelegateImpl.f21430w);
                a0A.a(0.0f);
                appCompatDelegateImpl.f21433z = a0A;
                a0A.e(new a());
            }
            ?? r02 = appCompatDelegateImpl.f21422o;
            if (r02 != 0) {
                r02.onSupportActionModeFinished(appCompatDelegateImpl.f21429v);
            }
            appCompatDelegateImpl.f21429v = null;
            C22823h0.A(appCompatDelegateImpl.f21385C);
            appCompatDelegateImpl.e0();
        }

        @Override // androidx.appcompat.view.b.a
        public final boolean c(androidx.appcompat.view.b bVar, androidx.appcompat.view.menu.h hVar) {
            C22823h0.A(AppCompatDelegateImpl.this.f21385C);
            return this.f21455a.c(bVar, hVar);
        }
    }

    @X
    public static class g {
        @InterfaceC42164u
        public static boolean a(PowerManager powerManager) {
            return powerManager.isPowerSaveMode();
        }

        @InterfaceC42164u
        public static String b(Locale locale) {
            return locale.toLanguageTag();
        }
    }

    @X
    public static class h {
        @InterfaceC42164u
        public static void a(@N Configuration configuration, @N Configuration configuration2, @N Configuration configuration3) {
            LocaleList locales = configuration.getLocales();
            LocaleList locales2 = configuration2.getLocales();
            if (locales.equals(locales2)) {
                return;
            }
            configuration3.setLocales(locales2);
            configuration3.locale = configuration2.locale;
        }

        @InterfaceC42164u
        public static androidx.core.os.q b(Configuration configuration) {
            return androidx.core.os.q.a(configuration.getLocales().toLanguageTags());
        }

        @InterfaceC42164u
        public static void c(androidx.core.os.q qVar) {
            LocaleList.setDefault(LocaleList.forLanguageTags(qVar.e()));
        }

        @InterfaceC42164u
        public static void d(Configuration configuration, androidx.core.os.q qVar) {
            configuration.setLocales(LocaleList.forLanguageTags(qVar.e()));
        }
    }

    @X
    public static class i {
    }

    @X
    public static class j {
        @InterfaceC42164u
        public static OnBackInvokedDispatcher a(Activity activity) {
            return activity.getOnBackInvokedDispatcher();
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [android.window.OnBackInvokedCallback, androidx.appcompat.app.y] */
        @InterfaceC42164u
        public static OnBackInvokedCallback b(Object obj, final AppCompatDelegateImpl appCompatDelegateImpl) {
            Objects.requireNonNull(appCompatDelegateImpl);
            ?? r02 = new OnBackInvokedCallback() { // from class: androidx.appcompat.app.y
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                    appCompatDelegateImpl.Z();
                }
            };
            Ad.l.h(obj).registerOnBackInvokedCallback(1000000, r02);
            return r02;
        }

        @InterfaceC42164u
        public static void c(Object obj, Object obj2) {
            Ad.l.h(obj).unregisterOnBackInvokedCallback(Ad.l.f(obj2));
        }
    }

    public class k extends WindowCallbackWrapper {

        /* renamed from: b, reason: collision with root package name */
        public G.e f21458b;

        /* renamed from: c, reason: collision with root package name */
        public boolean f21459c;

        /* renamed from: d, reason: collision with root package name */
        public boolean f21460d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f21461e;

        public k(Window.Callback callback) {
            super(callback);
        }

        public final void b(Window.Callback callback) {
            try {
                this.f21459c = true;
                callback.onContentChanged();
            } finally {
                this.f21459c = false;
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return this.f21460d ? a().dispatchKeyEvent(keyEvent) : AppCompatDelegateImpl.this.Q(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (super.dispatchKeyShortcutEvent(keyEvent)) {
                return true;
            }
            int keyCode = keyEvent.getKeyCode();
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            appCompatDelegateImpl.W();
            AbstractC19927a abstractC19927a = appCompatDelegateImpl.f21423p;
            if (abstractC19927a != null && abstractC19927a.o(keyCode, keyEvent)) {
                return true;
            }
            PanelFeatureState panelFeatureState = appCompatDelegateImpl.f21397O;
            if (panelFeatureState != null && appCompatDelegateImpl.b0(panelFeatureState, keyEvent.getKeyCode(), keyEvent)) {
                PanelFeatureState panelFeatureState2 = appCompatDelegateImpl.f21397O;
                if (panelFeatureState2 == null) {
                    return true;
                }
                panelFeatureState2.f21445l = true;
                return true;
            }
            if (appCompatDelegateImpl.f21397O == null) {
                PanelFeatureState panelFeatureStateV = appCompatDelegateImpl.V(0);
                appCompatDelegateImpl.c0(panelFeatureStateV, keyEvent);
                boolean zB02 = appCompatDelegateImpl.b0(panelFeatureStateV, keyEvent.getKeyCode(), keyEvent);
                panelFeatureStateV.f21444k = false;
                if (zB02) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final void onContentChanged() {
            if (this.f21459c) {
                a().onContentChanged();
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onCreatePanelMenu(int i12, Menu menu) {
            if (i12 != 0 || (menu instanceof androidx.appcompat.view.menu.h)) {
                return super.onCreatePanelMenu(i12, menu);
            }
            return false;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final View onCreatePanelView(int i12) {
            G.e eVar = this.f21458b;
            if (eVar != null) {
                View view = i12 == 0 ? new View(G.this.f21485a.f22165a.getContext()) : null;
                if (view != null) {
                    return view;
                }
            }
            return super.onCreatePanelView(i12);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onMenuOpened(int i12, Menu menu) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            super.onMenuOpened(i12, menu);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (i12 == 108) {
                appCompatDelegateImpl.W();
                AbstractC19927a abstractC19927a = appCompatDelegateImpl.f21423p;
                if (abstractC19927a != null) {
                    abstractC19927a.h(true);
                }
            } else {
                appCompatDelegateImpl.getClass();
            }
            return true;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final void onPanelClosed(int i12, Menu menu) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (this.f21461e) {
                a().onPanelClosed(i12, menu);
                return;
            }
            super.onPanelClosed(i12, menu);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (i12 == 108) {
                appCompatDelegateImpl.W();
                AbstractC19927a abstractC19927a = appCompatDelegateImpl.f21423p;
                if (abstractC19927a != null) {
                    abstractC19927a.h(false);
                    return;
                }
                return;
            }
            if (i12 != 0) {
                appCompatDelegateImpl.getClass();
                return;
            }
            PanelFeatureState panelFeatureStateV = appCompatDelegateImpl.V(i12);
            if (panelFeatureStateV.f21446m) {
                appCompatDelegateImpl.O(panelFeatureStateV, false);
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final boolean onPreparePanel(int i12, View view, Menu menu) {
            androidx.appcompat.view.menu.h hVar = menu instanceof androidx.appcompat.view.menu.h ? (androidx.appcompat.view.menu.h) menu : null;
            if (i12 == 0 && hVar == null) {
                return false;
            }
            if (hVar != null) {
                hVar.f21854x = true;
            }
            G.e eVar = this.f21458b;
            if (eVar != null && i12 == 0) {
                G g12 = G.this;
                if (!g12.f21488d) {
                    g12.f21485a.f22176l = true;
                    g12.f21488d = true;
                }
            }
            boolean zOnPreparePanel = super.onPreparePanel(i12, view, menu);
            if (hVar != null) {
                hVar.f21854x = false;
            }
            return zOnPreparePanel;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @X
        public final void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i12) {
            androidx.appcompat.view.menu.h hVar = AppCompatDelegateImpl.this.V(0).f21441h;
            if (hVar != null) {
                super.onProvideKeyboardShortcuts(list, hVar, i12);
            } else {
                super.onProvideKeyboardShortcuts(list, menu, i12);
            }
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:30:0x0054  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0058  */
        /* JADX WARN: Type inference failed for: r2v0, types: [androidx.appcompat.app.n, java.lang.Object] */
        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @j.X
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback r10, int r11) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
            /*
                Method dump skipped, instructions count: 466
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.k.onWindowStartingActionMode(android.view.ActionMode$Callback, int):android.view.ActionMode");
        }
    }

    public class l extends m {

        /* renamed from: c, reason: collision with root package name */
        public final PowerManager f21463c;

        public l(@N Context context) {
            super();
            this.f21463c = (PowerManager) context.getApplicationContext().getSystemService("power");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.m
        public final IntentFilter b() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.m
        public final int c() {
            return g.a(this.f21463c) ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.m
        public final void d() throws PackageManager.NameNotFoundException {
            AppCompatDelegateImpl.this.J(true, true);
        }
    }

    @k0
    @RestrictTo
    public abstract class m {

        /* renamed from: a, reason: collision with root package name */
        public BroadcastReceiver f21465a;

        public class a extends BroadcastReceiver {
            public a() {
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                m.this.d();
            }
        }

        public m() {
        }

        public final void a() {
            BroadcastReceiver broadcastReceiver = this.f21465a;
            if (broadcastReceiver != null) {
                try {
                    AppCompatDelegateImpl.this.f21419l.unregisterReceiver(broadcastReceiver);
                } catch (IllegalArgumentException unused) {
                }
                this.f21465a = null;
            }
        }

        @P
        public abstract IntentFilter b();

        public abstract int c();

        public abstract void d();

        public final void e() {
            a();
            IntentFilter intentFilterB = b();
            if (intentFilterB.countActions() == 0) {
                return;
            }
            if (this.f21465a == null) {
                this.f21465a = new a();
            }
            AppCompatDelegateImpl.this.f21419l.registerReceiver(this.f21465a, intentFilterB);
        }
    }

    public class n extends m {

        /* renamed from: c, reason: collision with root package name */
        public final I f21468c;

        public n(@N I i12) {
            super();
            this.f21468c = i12;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.m
        public final IntentFilter b() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
        @Override // androidx.appcompat.app.AppCompatDelegateImpl.m
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final int c() {
            /*
                Method dump skipped, instructions count: 231
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.n.c():int");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.m
        public final void d() throws PackageManager.NameNotFoundException {
            AppCompatDelegateImpl.this.J(true, true);
        }
    }

    public class o extends ContentFrameLayout {
        public o(androidx.appcompat.view.d dVar) {
            super(dVar, null);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.Q(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                int x12 = (int) motionEvent.getX();
                int y12 = (int) motionEvent.getY();
                if (x12 < -5 || y12 < -5 || x12 > getWidth() + 5 || y12 > getHeight() + 5) {
                    AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                    appCompatDelegateImpl.O(appCompatDelegateImpl.V(0), true);
                    return true;
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public final void setBackgroundResource(int i12) {
            setBackgroundDrawable(C43852a.a(getContext(), i12));
        }
    }

    public final class p implements o.a {
        public p() {
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final void b(@N androidx.appcompat.view.menu.h hVar, boolean z12) {
            PanelFeatureState panelFeatureState;
            androidx.appcompat.view.menu.h hVarK = hVar.k();
            int i12 = 0;
            boolean z13 = hVarK != hVar;
            if (z13) {
                hVar = hVarK;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            PanelFeatureState[] panelFeatureStateArr = appCompatDelegateImpl.f21396N;
            int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
            while (true) {
                if (i12 < length) {
                    panelFeatureState = panelFeatureStateArr[i12];
                    if (panelFeatureState != null && panelFeatureState.f21441h == hVar) {
                        break;
                    } else {
                        i12++;
                    }
                } else {
                    panelFeatureState = null;
                    break;
                }
            }
            if (panelFeatureState != null) {
                if (!z13) {
                    appCompatDelegateImpl.O(panelFeatureState, z12);
                } else {
                    appCompatDelegateImpl.M(panelFeatureState.f21434a, panelFeatureState, hVarK);
                    appCompatDelegateImpl.O(panelFeatureState, true);
                }
            }
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final boolean c(@N androidx.appcompat.view.menu.h hVar) {
            Window.Callback callback;
            if (hVar != hVar.k()) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.f21390H || (callback = appCompatDelegateImpl.f21420m.getCallback()) == null || appCompatDelegateImpl.f21401S) {
                return true;
            }
            callback.onMenuOpened(108, hVar);
            return true;
        }
    }

    public AppCompatDelegateImpl(Context context, Window window, androidx.appcompat.app.n nVar, Object obj) {
        o1<String, Integer> o1Var;
        Integer num;
        androidx.appcompat.app.m mVar = null;
        this.f21403U = -100;
        this.f21419l = context;
        this.f21422o = nVar;
        this.f21418k = obj;
        if (obj instanceof Dialog) {
            while (true) {
                if (context != null) {
                    if (!(context instanceof androidx.appcompat.app.m)) {
                        if (!(context instanceof ContextWrapper)) {
                            break;
                        } else {
                            context = ((ContextWrapper) context).getBaseContext();
                        }
                    } else {
                        mVar = (androidx.appcompat.app.m) context;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (mVar != null) {
                this.f21403U = mVar.getDelegate().j();
            }
        }
        if (this.f21403U == -100 && (num = (o1Var = f21380j0).get(this.f21418k.getClass().getName())) != null) {
            this.f21403U = num.intValue();
            o1Var.remove(this.f21418k.getClass().getName());
        }
        if (window != null) {
            K(window);
        }
        androidx.appcompat.widget.r.d();
    }

    @P
    public static androidx.core.os.q L(@N Context context) {
        androidx.core.os.q qVar;
        androidx.core.os.q qVarG;
        if (Build.VERSION.SDK_INT >= 33 || (qVar = androidx.appcompat.app.p.f21561d) == null) {
            return null;
        }
        androidx.core.os.q qVarB = h.b(context.getApplicationContext().getResources().getConfiguration());
        if (qVar.c()) {
            qVarG = androidx.core.os.q.f44804b;
        } else {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            int i12 = 0;
            while (i12 < qVarB.d() + qVar.d()) {
                Locale localeB = i12 < qVar.d() ? qVar.b(i12) : qVarB.b(i12 - qVar.d());
                if (localeB != null) {
                    linkedHashSet.add(localeB);
                }
                i12++;
            }
            qVarG = androidx.core.os.q.g(new LocaleList((Locale[]) linkedHashSet.toArray(new Locale[linkedHashSet.size()])));
        }
        return qVarG.c() ? qVarB : qVarG;
    }

    @N
    public static Configuration P(@N Context context, int i12, @P androidx.core.os.q qVar, @P Configuration configuration, boolean z12) {
        int i13 = i12 != 1 ? i12 != 2 ? z12 ? 0 : context.getApplicationContext().getResources().getConfiguration().uiMode & 48 : 32 : 16;
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i13 | (configuration2.uiMode & (-49));
        if (qVar != null) {
            h.d(configuration2, qVar);
        }
        return configuration2;
    }

    @Override // androidx.appcompat.app.p
    public final void A(int i12) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        S();
        ViewGroup viewGroup = (ViewGroup) this.f21385C.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.f21419l).inflate(i12, viewGroup);
        this.f21421n.b(this.f21420m.getCallback());
    }

    @Override // androidx.appcompat.app.p
    public final void B(View view) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        S();
        ViewGroup viewGroup = (ViewGroup) this.f21385C.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.f21421n.b(this.f21420m.getCallback());
    }

    @Override // androidx.appcompat.app.p
    public final void C(View view, ViewGroup.LayoutParams layoutParams) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        S();
        ViewGroup viewGroup = (ViewGroup) this.f21385C.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.f21421n.b(this.f21420m.getCallback());
    }

    @Override // androidx.appcompat.app.p
    public final void E(int i12) throws PackageManager.NameNotFoundException {
        if (this.f21403U != i12) {
            this.f21403U = i12;
            if (this.f21399Q) {
                J(true, true);
            }
        }
    }

    @Override // androidx.appcompat.app.p
    public final void F(Toolbar toolbar) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object obj = this.f21418k;
        if (obj instanceof Activity) {
            W();
            AbstractC19927a abstractC19927a = this.f21423p;
            if (abstractC19927a instanceof J) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            this.f21424q = null;
            if (abstractC19927a != null) {
                abstractC19927a.n();
            }
            this.f21423p = null;
            if (toolbar != null) {
                G g12 = new G(toolbar, obj instanceof Activity ? ((Activity) obj).getTitle() : this.f21425r, this.f21421n);
                this.f21423p = g12;
                this.f21421n.f21458b = g12.f21487c;
                toolbar.setBackInvokedCallbackEnabled(true);
            } else {
                this.f21421n.f21458b = null;
            }
            o();
        }
    }

    @Override // androidx.appcompat.app.p
    public final void G(@f0 int i12) {
        this.f21404V = i12;
    }

    @Override // androidx.appcompat.app.p
    public final void H(CharSequence charSequence) {
        this.f21425r = charSequence;
        T t12 = this.f21426s;
        if (t12 != null) {
            t12.setWindowTitle(charSequence);
            return;
        }
        AbstractC19927a abstractC19927a = this.f21423p;
        if (abstractC19927a != null) {
            abstractC19927a.x(charSequence);
            return;
        }
        TextView textView = this.f21386D;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean J(boolean r17, boolean r18) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 437
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.J(boolean, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void K(@j.N android.view.Window r5) {
        /*
            r4 = this;
            android.view.Window r0 = r4.f21420m
            java.lang.String r1 = "AppCompat has already installed itself into the Window"
            if (r0 != 0) goto L6b
            android.view.Window$Callback r0 = r5.getCallback()
            boolean r2 = r0 instanceof androidx.appcompat.app.AppCompatDelegateImpl.k
            if (r2 != 0) goto L65
            androidx.appcompat.app.AppCompatDelegateImpl$k r1 = new androidx.appcompat.app.AppCompatDelegateImpl$k
            r1.<init>(r0)
            r4.f21421n = r1
            r5.setCallback(r1)
            androidx.appcompat.widget.D0 r0 = new androidx.appcompat.widget.D0
            android.content.Context r1 = r4.f21419l
            r2 = 0
            int[] r3 = androidx.appcompat.app.AppCompatDelegateImpl.f21381k0
            android.content.res.TypedArray r3 = r1.obtainStyledAttributes(r2, r3)
            r0.<init>(r3, r1)
            r1 = 0
            android.graphics.drawable.Drawable r1 = r0.c(r1)
            if (r1 == 0) goto L30
            r5.setBackgroundDrawable(r1)
        L30:
            r0.f()
            r4.f21420m = r5
            int r5 = android.os.Build.VERSION.SDK_INT
            r0 = 33
            if (r5 < r0) goto L64
            android.window.OnBackInvokedDispatcher r5 = r4.f21416h0
            if (r5 != 0) goto L64
            if (r5 == 0) goto L4a
            android.window.OnBackInvokedCallback r0 = r4.f21417i0
            if (r0 == 0) goto L4a
            androidx.appcompat.app.AppCompatDelegateImpl.j.c(r5, r0)
            r4.f21417i0 = r2
        L4a:
            java.lang.Object r5 = r4.f21418k
            boolean r0 = r5 instanceof android.app.Activity
            if (r0 == 0) goto L5f
            android.app.Activity r5 = (android.app.Activity) r5
            android.view.Window r0 = r5.getWindow()
            if (r0 == 0) goto L5f
            android.window.OnBackInvokedDispatcher r5 = androidx.appcompat.app.AppCompatDelegateImpl.j.a(r5)
            r4.f21416h0 = r5
            goto L61
        L5f:
            r4.f21416h0 = r2
        L61:
            r4.e0()
        L64:
            return
        L65:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            r5.<init>(r1)
            throw r5
        L6b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            r5.<init>(r1)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.K(android.view.Window):void");
    }

    public final void M(int i12, PanelFeatureState panelFeatureState, androidx.appcompat.view.menu.h hVar) {
        if (hVar == null) {
            if (panelFeatureState == null && i12 >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.f21396N;
                if (i12 < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i12];
                }
            }
            if (panelFeatureState != null) {
                hVar = panelFeatureState.f21441h;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.f21446m) && !this.f21401S) {
            k kVar = this.f21421n;
            Window.Callback callback = this.f21420m.getCallback();
            kVar.getClass();
            try {
                kVar.f21461e = true;
                callback.onPanelClosed(i12, hVar);
            } finally {
                kVar.f21461e = false;
            }
        }
    }

    public final void N(@N androidx.appcompat.view.menu.h hVar) {
        if (this.f21395M) {
            return;
        }
        this.f21395M = true;
        this.f21426s.i();
        Window.Callback callback = this.f21420m.getCallback();
        if (callback != null && !this.f21401S) {
            callback.onPanelClosed(108, hVar);
        }
        this.f21395M = false;
    }

    public final void O(PanelFeatureState panelFeatureState, boolean z12) {
        ViewGroup viewGroup;
        T t12;
        if (z12 && panelFeatureState.f21434a == 0 && (t12 = this.f21426s) != null && t12.e()) {
            N(panelFeatureState.f21441h);
            return;
        }
        WindowManager windowManager = (WindowManager) this.f21419l.getSystemService("window");
        if (windowManager != null && panelFeatureState.f21446m && (viewGroup = panelFeatureState.f21438e) != null) {
            windowManager.removeView(viewGroup);
            if (z12) {
                M(panelFeatureState.f21434a, panelFeatureState, null);
            }
        }
        panelFeatureState.f21444k = false;
        panelFeatureState.f21445l = false;
        panelFeatureState.f21446m = false;
        panelFeatureState.f21439f = null;
        panelFeatureState.f21447n = true;
        if (this.f21397O == panelFeatureState) {
            this.f21397O = null;
        }
        if (panelFeatureState.f21434a == 0) {
            e0();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean Q(android.view.KeyEvent r7) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 245
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.Q(android.view.KeyEvent):boolean");
    }

    public final void R(int i12) {
        PanelFeatureState panelFeatureStateV = V(i12);
        if (panelFeatureStateV.f21441h != null) {
            Bundle bundle = new Bundle();
            panelFeatureStateV.f21441h.t(bundle);
            if (bundle.size() > 0) {
                panelFeatureStateV.f21449p = bundle;
            }
            panelFeatureStateV.f21441h.w();
            panelFeatureStateV.f21441h.clear();
        }
        panelFeatureStateV.f21448o = true;
        panelFeatureStateV.f21447n = true;
        if ((i12 == 108 || i12 == 0) && this.f21426s != null) {
            PanelFeatureState panelFeatureStateV2 = V(0);
            panelFeatureStateV2.f21444k = false;
            c0(panelFeatureStateV2, null);
        }
    }

    public final void S() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ViewGroup viewGroup;
        if (this.f21384B) {
            return;
        }
        int[] iArr = C43521a.m.f413361j;
        Context context = this.f21419l;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iArr);
        if (!typedArrayObtainStyledAttributes.hasValue(117)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(WebSocketProtocol.PAYLOAD_SHORT, false)) {
            y(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(117, false)) {
            y(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(118, false)) {
            y(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(119, false)) {
            y(10);
        }
        this.f21393K = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        T();
        this.f21420m.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        if (this.f21394L) {
            viewGroup = this.f21392J ? (ViewGroup) layoutInflaterFrom.inflate(com.avito.android.R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(com.avito.android.R.layout.abc_screen_simple, (ViewGroup) null);
        } else if (this.f21393K) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(com.avito.android.R.layout.abc_dialog_title_material, (ViewGroup) null);
            this.f21391I = false;
            this.f21390H = false;
        } else if (this.f21390H) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(com.avito.android.R.attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new androidx.appcompat.view.d(context, typedValue.resourceId) : context).inflate(com.avito.android.R.layout.abc_screen_toolbar, (ViewGroup) null);
            T t12 = (T) viewGroup.findViewById(com.avito.android.R.id.decor_content_parent);
            this.f21426s = t12;
            t12.setWindowCallback(this.f21420m.getCallback());
            if (this.f21391I) {
                this.f21426s.h(109);
            }
            if (this.f21388F) {
                this.f21426s.h(2);
            }
            if (this.f21389G) {
                this.f21426s.h(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            StringBuilder sb2 = new StringBuilder("AppCompat does not support the current theme features: { windowActionBar: ");
            sb2.append(this.f21390H);
            sb2.append(", windowActionBarOverlay: ");
            sb2.append(this.f21391I);
            sb2.append(", android:windowIsFloating: ");
            sb2.append(this.f21393K);
            sb2.append(", windowActionModeOverlay: ");
            sb2.append(this.f21392J);
            sb2.append(", windowNoTitle: ");
            throw new IllegalArgumentException(r.s(" }", sb2, this.f21394L));
        }
        C22823h0.K(viewGroup, new s(this));
        if (this.f21426s == null) {
            this.f21386D = (TextView) viewGroup.findViewById(com.avito.android.R.id.title);
        }
        try {
            Method method = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(viewGroup, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(com.avito.android.R.id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.f21420m.findViewById(R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.f21420m.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new u(this));
        this.f21385C = viewGroup;
        Object obj = this.f21418k;
        CharSequence title = obj instanceof Activity ? ((Activity) obj).getTitle() : this.f21425r;
        if (!TextUtils.isEmpty(title)) {
            T t13 = this.f21426s;
            if (t13 != null) {
                t13.setWindowTitle(title);
            } else {
                AbstractC19927a abstractC19927a = this.f21423p;
                if (abstractC19927a != null) {
                    abstractC19927a.x(title);
                } else {
                    TextView textView = this.f21386D;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
        }
        ContentFrameLayout contentFrameLayout2 = (ContentFrameLayout) this.f21385C.findViewById(R.id.content);
        View decorView = this.f21420m.getDecorView();
        contentFrameLayout2.f22111h.set(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        if (contentFrameLayout2.isLaidOut()) {
            contentFrameLayout2.requestLayout();
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(iArr);
        typedArrayObtainStyledAttributes2.getValue(124, contentFrameLayout2.getMinWidthMajor());
        typedArrayObtainStyledAttributes2.getValue(125, contentFrameLayout2.getMinWidthMinor());
        if (typedArrayObtainStyledAttributes2.hasValue(122)) {
            typedArrayObtainStyledAttributes2.getValue(122, contentFrameLayout2.getFixedWidthMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(123)) {
            typedArrayObtainStyledAttributes2.getValue(123, contentFrameLayout2.getFixedWidthMinor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(120)) {
            typedArrayObtainStyledAttributes2.getValue(120, contentFrameLayout2.getFixedHeightMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(121)) {
            typedArrayObtainStyledAttributes2.getValue(121, contentFrameLayout2.getFixedHeightMinor());
        }
        typedArrayObtainStyledAttributes2.recycle();
        contentFrameLayout2.requestLayout();
        this.f21384B = true;
        PanelFeatureState panelFeatureStateV = V(0);
        if (this.f21401S || panelFeatureStateV.f21441h != null) {
            return;
        }
        X(108);
    }

    public final void T() {
        if (this.f21420m == null) {
            Object obj = this.f21418k;
            if (obj instanceof Activity) {
                K(((Activity) obj).getWindow());
            }
        }
        if (this.f21420m == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    public final m U(@N Context context) {
        if (this.f21407Y == null) {
            if (I.f21504d == null) {
                Context applicationContext = context.getApplicationContext();
                I.f21504d = new I(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
            }
            this.f21407Y = new n(I.f21504d);
        }
        return this.f21407Y;
    }

    public final PanelFeatureState V(int i12) {
        PanelFeatureState[] panelFeatureStateArr = this.f21396N;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i12) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i12 + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.f21396N = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i12];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState();
        panelFeatureState2.f21434a = i12;
        panelFeatureState2.f21447n = false;
        panelFeatureStateArr[i12] = panelFeatureState2;
        return panelFeatureState2;
    }

    public final void W() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        S();
        if (this.f21390H && this.f21423p == null) {
            Object obj = this.f21418k;
            if (obj instanceof Activity) {
                this.f21423p = new J((Activity) obj, this.f21391I);
            } else if (obj instanceof Dialog) {
                this.f21423p = new J((Dialog) obj);
            }
            AbstractC19927a abstractC19927a = this.f21423p;
            if (abstractC19927a != null) {
                abstractC19927a.r(this.f21412d0);
            }
        }
    }

    public final void X(int i12) {
        this.f21410b0 = (1 << i12) | this.f21410b0;
        if (this.f21409a0) {
            return;
        }
        View decorView = this.f21420m.getDecorView();
        Runnable runnable = this.f21411c0;
        WeakHashMap<View, A0> weakHashMap = C22823h0.f45055a;
        decorView.postOnAnimation(runnable);
        this.f21409a0 = true;
    }

    public final int Y(int i12, @N Context context) {
        if (i12 == -100) {
            return -1;
        }
        if (i12 == -1) {
            return i12;
        }
        if (i12 == 0) {
            if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() == 0) {
                return -1;
            }
            return U(context).c();
        }
        if (i12 == 1 || i12 == 2) {
            return i12;
        }
        if (i12 != 3) {
            throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
        }
        if (this.f21408Z == null) {
            this.f21408Z = new l(context);
        }
        return this.f21408Z.c();
    }

    public final boolean Z() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        boolean z12 = this.f21398P;
        this.f21398P = false;
        PanelFeatureState panelFeatureStateV = V(0);
        if (panelFeatureStateV.f21446m) {
            if (!z12) {
                O(panelFeatureStateV, true);
            }
            return true;
        }
        androidx.appcompat.view.b bVar = this.f21429v;
        if (bVar != null) {
            bVar.c();
            return true;
        }
        W();
        AbstractC19927a abstractC19927a = this.f21423p;
        return abstractC19927a != null && abstractC19927a.g();
    }

    @Override // androidx.appcompat.view.menu.h.a
    public final void a(@N androidx.appcompat.view.menu.h hVar) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        T t12 = this.f21426s;
        if (t12 == null || !t12.c() || (ViewConfiguration.get(this.f21419l).hasPermanentMenuKey() && !this.f21426s.b())) {
            PanelFeatureState panelFeatureStateV = V(0);
            panelFeatureStateV.f21447n = true;
            O(panelFeatureStateV, false);
            a0(panelFeatureStateV, null);
            return;
        }
        Window.Callback callback = this.f21420m.getCallback();
        if (this.f21426s.e()) {
            this.f21426s.a();
            if (this.f21401S) {
                return;
            }
            callback.onPanelClosed(108, V(0).f21441h);
            return;
        }
        if (callback == null || this.f21401S) {
            return;
        }
        if (this.f21409a0 && (1 & this.f21410b0) != 0) {
            View decorView = this.f21420m.getDecorView();
            Runnable runnable = this.f21411c0;
            decorView.removeCallbacks(runnable);
            ((b) runnable).run();
        }
        PanelFeatureState panelFeatureStateV2 = V(0);
        androidx.appcompat.view.menu.h hVar2 = panelFeatureStateV2.f21441h;
        if (hVar2 == null || panelFeatureStateV2.f21448o || !callback.onPreparePanel(0, panelFeatureStateV2.f21440g, hVar2)) {
            return;
        }
        callback.onMenuOpened(108, panelFeatureStateV2.f21441h);
        this.f21426s.d();
    }

    /* JADX WARN: Removed duplicated region for block: B:91:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void a0(androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState r18, android.view.KeyEvent r19) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 426
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.a0(androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState, android.view.KeyEvent):void");
    }

    @Override // androidx.appcompat.view.menu.h.a
    public final boolean b(@N androidx.appcompat.view.menu.h hVar, @N MenuItem menuItem) {
        PanelFeatureState panelFeatureState;
        Window.Callback callback = this.f21420m.getCallback();
        if (callback != null && !this.f21401S) {
            androidx.appcompat.view.menu.h hVarK = hVar.k();
            PanelFeatureState[] panelFeatureStateArr = this.f21396N;
            int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
            int i12 = 0;
            while (true) {
                if (i12 < length) {
                    panelFeatureState = panelFeatureStateArr[i12];
                    if (panelFeatureState != null && panelFeatureState.f21441h == hVarK) {
                        break;
                    }
                    i12++;
                } else {
                    panelFeatureState = null;
                    break;
                }
            }
            if (panelFeatureState != null) {
                return callback.onMenuItemSelected(panelFeatureState.f21434a, menuItem);
            }
        }
        return false;
    }

    public final boolean b0(PanelFeatureState panelFeatureState, int i12, KeyEvent keyEvent) {
        androidx.appcompat.view.menu.h hVar;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.f21444k || c0(panelFeatureState, keyEvent)) && (hVar = panelFeatureState.f21441h) != null) {
            return hVar.performShortcut(i12, keyEvent, 1);
        }
        return false;
    }

    @Override // androidx.appcompat.app.p
    public final void c(View view, ViewGroup.LayoutParams layoutParams) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        S();
        ((ViewGroup) this.f21385C.findViewById(R.id.content)).addView(view, layoutParams);
        this.f21421n.b(this.f21420m.getCallback());
    }

    public final boolean c0(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        T t12;
        T t13;
        Resources.Theme themeNewTheme;
        T t14;
        T t15;
        if (this.f21401S) {
            return false;
        }
        if (panelFeatureState.f21444k) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.f21397O;
        if (panelFeatureState2 != null && panelFeatureState2 != panelFeatureState) {
            O(panelFeatureState2, false);
        }
        Window.Callback callback = this.f21420m.getCallback();
        int i12 = panelFeatureState.f21434a;
        if (callback != null) {
            panelFeatureState.f21440g = callback.onCreatePanelView(i12);
        }
        boolean z12 = i12 == 0 || i12 == 108;
        if (z12 && (t15 = this.f21426s) != null) {
            t15.g();
        }
        if (panelFeatureState.f21440g == null && (!z12 || !(this.f21423p instanceof G))) {
            androidx.appcompat.view.menu.h hVar = panelFeatureState.f21441h;
            if (hVar == null || panelFeatureState.f21448o) {
                if (hVar == null) {
                    Context context = this.f21419l;
                    if ((i12 == 0 || i12 == 108) && this.f21426s != null) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = context.getTheme();
                        theme.resolveAttribute(com.avito.android.R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            themeNewTheme = context.getResources().newTheme();
                            themeNewTheme.setTo(theme);
                            themeNewTheme.applyStyle(typedValue.resourceId, true);
                            themeNewTheme.resolveAttribute(com.avito.android.R.attr.actionBarWidgetTheme, typedValue, true);
                        } else {
                            theme.resolveAttribute(com.avito.android.R.attr.actionBarWidgetTheme, typedValue, true);
                            themeNewTheme = null;
                        }
                        if (typedValue.resourceId != 0) {
                            if (themeNewTheme == null) {
                                themeNewTheme = context.getResources().newTheme();
                                themeNewTheme.setTo(theme);
                            }
                            themeNewTheme.applyStyle(typedValue.resourceId, true);
                        }
                        if (themeNewTheme != null) {
                            androidx.appcompat.view.d dVar = new androidx.appcompat.view.d(context, 0);
                            dVar.getTheme().setTo(themeNewTheme);
                            context = dVar;
                        }
                    }
                    androidx.appcompat.view.menu.h hVar2 = new androidx.appcompat.view.menu.h(context);
                    hVar2.f21835e = this;
                    androidx.appcompat.view.menu.h hVar3 = panelFeatureState.f21441h;
                    if (hVar2 != hVar3) {
                        if (hVar3 != null) {
                            hVar3.r(panelFeatureState.f21442i);
                        }
                        panelFeatureState.f21441h = hVar2;
                        androidx.appcompat.view.menu.f fVar = panelFeatureState.f21442i;
                        if (fVar != null) {
                            hVar2.b(fVar, hVar2.f21831a);
                        }
                    }
                    if (panelFeatureState.f21441h == null) {
                        return false;
                    }
                }
                if (z12 && (t13 = this.f21426s) != null) {
                    if (this.f21427t == null) {
                        this.f21427t = new e();
                    }
                    t13.f(panelFeatureState.f21441h, this.f21427t);
                }
                panelFeatureState.f21441h.w();
                if (!callback.onCreatePanelMenu(i12, panelFeatureState.f21441h)) {
                    androidx.appcompat.view.menu.h hVar4 = panelFeatureState.f21441h;
                    if (hVar4 != null) {
                        if (hVar4 != null) {
                            hVar4.r(panelFeatureState.f21442i);
                        }
                        panelFeatureState.f21441h = null;
                    }
                    if (z12 && (t12 = this.f21426s) != null) {
                        t12.f(null, this.f21427t);
                    }
                    return false;
                }
                panelFeatureState.f21448o = false;
            }
            panelFeatureState.f21441h.w();
            Bundle bundle = panelFeatureState.f21449p;
            if (bundle != null) {
                panelFeatureState.f21441h.s(bundle);
                panelFeatureState.f21449p = null;
            }
            if (!callback.onPreparePanel(0, panelFeatureState.f21440g, panelFeatureState.f21441h)) {
                if (z12 && (t14 = this.f21426s) != null) {
                    t14.f(null, this.f21427t);
                }
                panelFeatureState.f21441h.v();
                return false;
            }
            panelFeatureState.f21441h.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
            panelFeatureState.f21441h.v();
        }
        panelFeatureState.f21444k = true;
        panelFeatureState.f21445l = false;
        this.f21397O = panelFeatureState;
        return true;
    }

    @Override // androidx.appcompat.app.p
    public final void d() throws PackageManager.NameNotFoundException {
        androidx.core.os.q qVar;
        Context context = this.f21419l;
        if (androidx.appcompat.app.p.p(context) && (qVar = androidx.appcompat.app.p.f21561d) != null && !qVar.equals(androidx.appcompat.app.p.f21562e)) {
            androidx.appcompat.app.p.f21559b.execute(new androidx.appcompat.app.o(context, 1));
        }
        J(true, true);
    }

    public final void d0() {
        if (this.f21384B) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    @Override // androidx.appcompat.app.p
    public final boolean e() {
        return J(true, true);
    }

    public final void e0() {
        OnBackInvokedCallback onBackInvokedCallback;
        if (Build.VERSION.SDK_INT >= 33) {
            boolean z12 = false;
            if (this.f21416h0 != null && (V(0).f21446m || this.f21429v != null)) {
                z12 = true;
            }
            if (z12 && this.f21417i0 == null) {
                this.f21417i0 = j.b(this.f21416h0, this);
            } else {
                if (z12 || (onBackInvokedCallback = this.f21417i0) == null) {
                    return;
                }
                j.c(this.f21416h0, onBackInvokedCallback);
                this.f21417i0 = null;
            }
        }
    }

    @Override // androidx.appcompat.app.p
    @InterfaceC42153i
    @N
    public final Context f(@N Context context) {
        this.f21399Q = true;
        int i12 = this.f21403U;
        if (i12 == -100) {
            i12 = androidx.appcompat.app.p.f21560c;
        }
        int iY2 = Y(i12, context);
        if (androidx.appcompat.app.p.p(context)) {
            androidx.appcompat.app.p.I(context);
        }
        androidx.core.os.q qVarL = L(context);
        Configuration configuration = null;
        if (context instanceof ContextThemeWrapper) {
            try {
                ((ContextThemeWrapper) context).applyOverrideConfiguration(P(context, iY2, qVarL, null, false));
                return context;
            } catch (IllegalStateException unused) {
            }
        }
        if (context instanceof androidx.appcompat.view.d) {
            try {
                ((androidx.appcompat.view.d) context).a(P(context, iY2, qVarL, null, false));
                return context;
            } catch (IllegalStateException unused2) {
            }
        }
        if (!f21382l0) {
            return context;
        }
        Configuration configuration2 = new Configuration();
        configuration2.uiMode = -1;
        configuration2.fontScale = 0.0f;
        Configuration configuration3 = context.createConfigurationContext(configuration2).getResources().getConfiguration();
        Configuration configuration4 = context.getResources().getConfiguration();
        configuration3.uiMode = configuration4.uiMode;
        if (!configuration3.equals(configuration4)) {
            configuration = new Configuration();
            configuration.fontScale = 0.0f;
            if (configuration3.diff(configuration4) != 0) {
                float f12 = configuration3.fontScale;
                float f13 = configuration4.fontScale;
                if (f12 != f13) {
                    configuration.fontScale = f13;
                }
                int i13 = configuration3.mcc;
                int i14 = configuration4.mcc;
                if (i13 != i14) {
                    configuration.mcc = i14;
                }
                int i15 = configuration3.mnc;
                int i16 = configuration4.mnc;
                if (i15 != i16) {
                    configuration.mnc = i16;
                }
                h.a(configuration3, configuration4, configuration);
                int i17 = configuration3.touchscreen;
                int i18 = configuration4.touchscreen;
                if (i17 != i18) {
                    configuration.touchscreen = i18;
                }
                int i19 = configuration3.keyboard;
                int i22 = configuration4.keyboard;
                if (i19 != i22) {
                    configuration.keyboard = i22;
                }
                int i23 = configuration3.keyboardHidden;
                int i24 = configuration4.keyboardHidden;
                if (i23 != i24) {
                    configuration.keyboardHidden = i24;
                }
                int i25 = configuration3.navigation;
                int i26 = configuration4.navigation;
                if (i25 != i26) {
                    configuration.navigation = i26;
                }
                int i27 = configuration3.navigationHidden;
                int i28 = configuration4.navigationHidden;
                if (i27 != i28) {
                    configuration.navigationHidden = i28;
                }
                int i29 = configuration3.orientation;
                int i32 = configuration4.orientation;
                if (i29 != i32) {
                    configuration.orientation = i32;
                }
                int i33 = configuration3.screenLayout & 15;
                int i34 = configuration4.screenLayout & 15;
                if (i33 != i34) {
                    configuration.screenLayout |= i34;
                }
                int i35 = configuration3.screenLayout & 192;
                int i36 = configuration4.screenLayout & 192;
                if (i35 != i36) {
                    configuration.screenLayout |= i36;
                }
                int i37 = configuration3.screenLayout & 48;
                int i38 = configuration4.screenLayout & 48;
                if (i37 != i38) {
                    configuration.screenLayout |= i38;
                }
                int i39 = configuration3.screenLayout & 768;
                int i42 = configuration4.screenLayout & 768;
                if (i39 != i42) {
                    configuration.screenLayout |= i42;
                }
                int i43 = configuration3.colorMode & 3;
                int i44 = configuration4.colorMode & 3;
                if (i43 != i44) {
                    configuration.colorMode |= i44;
                }
                int i45 = configuration3.colorMode & 12;
                int i46 = configuration4.colorMode & 12;
                if (i45 != i46) {
                    configuration.colorMode |= i46;
                }
                int i47 = configuration3.uiMode & 15;
                int i48 = configuration4.uiMode & 15;
                if (i47 != i48) {
                    configuration.uiMode |= i48;
                }
                int i49 = configuration3.uiMode & 48;
                int i52 = configuration4.uiMode & 48;
                if (i49 != i52) {
                    configuration.uiMode |= i52;
                }
                int i53 = configuration3.screenWidthDp;
                int i54 = configuration4.screenWidthDp;
                if (i53 != i54) {
                    configuration.screenWidthDp = i54;
                }
                int i55 = configuration3.screenHeightDp;
                int i56 = configuration4.screenHeightDp;
                if (i55 != i56) {
                    configuration.screenHeightDp = i56;
                }
                int i57 = configuration3.smallestScreenWidthDp;
                int i58 = configuration4.smallestScreenWidthDp;
                if (i57 != i58) {
                    configuration.smallestScreenWidthDp = i58;
                }
                int i59 = configuration3.densityDpi;
                int i62 = configuration4.densityDpi;
                if (i59 != i62) {
                    configuration.densityDpi = i62;
                }
            }
        }
        Configuration configurationP = P(context, iY2, qVarL, configuration, true);
        androidx.appcompat.view.d dVar = new androidx.appcompat.view.d(context, com.avito.android.R.style.Theme_AppCompat_Empty);
        dVar.a(configurationP);
        try {
            if (context.getTheme() != null) {
                i.g.a(dVar.getTheme());
            }
        } catch (NullPointerException unused3) {
        }
        return dVar;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0077  */
    @Override // androidx.appcompat.app.p
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.view.View g(java.lang.String r10, @j.N android.content.Context r11, @j.N android.util.AttributeSet r12) {
        /*
            Method dump skipped, instructions count: 660
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.g(java.lang.String, android.content.Context, android.util.AttributeSet):android.view.View");
    }

    @Override // androidx.appcompat.app.p
    @P
    public final <T extends View> T h(@j.D int i12) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        S();
        return (T) this.f21420m.findViewById(i12);
    }

    @Override // androidx.appcompat.app.p
    public final Context i() {
        return this.f21419l;
    }

    @Override // androidx.appcompat.app.p
    public final int j() {
        return this.f21403U;
    }

    @Override // androidx.appcompat.app.p
    public final MenuInflater l() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.f21424q == null) {
            W();
            AbstractC19927a abstractC19927a = this.f21423p;
            this.f21424q = new androidx.appcompat.view.g(abstractC19927a != null ? abstractC19927a.j() : this.f21419l);
        }
        return this.f21424q;
    }

    @Override // androidx.appcompat.app.p
    public final AbstractC19927a m() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        W();
        return this.f21423p;
    }

    @Override // androidx.appcompat.app.p
    public final void n() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.f21419l);
        if (layoutInflaterFrom.getFactory() == null) {
            layoutInflaterFrom.setFactory2(this);
        } else {
            boolean z12 = layoutInflaterFrom.getFactory2() instanceof AppCompatDelegateImpl;
        }
    }

    @Override // androidx.appcompat.app.p
    public final void o() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.f21423p != null) {
            W();
            if (this.f21423p.l()) {
                return;
            }
            X(0);
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return g(str, context, attributeSet);
    }

    @Override // androidx.appcompat.app.p
    public final void q(Configuration configuration) throws IllegalAccessException, NoSuchMethodException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.f21390H && this.f21384B) {
            W();
            AbstractC19927a abstractC19927a = this.f21423p;
            if (abstractC19927a != null) {
                abstractC19927a.m();
            }
        }
        androidx.appcompat.widget.r rVarA = androidx.appcompat.widget.r.a();
        Context context = this.f21419l;
        synchronized (rVarA) {
            C19962m0 c19962m0 = rVarA.f22611a;
            synchronized (c19962m0) {
                C20204c0<WeakReference<Drawable.ConstantState>> c20204c0 = c19962m0.f22571b.get(context);
                if (c20204c0 != null) {
                    c20204c0.a();
                }
            }
        }
        this.f21402T = new Configuration(this.f21419l.getResources().getConfiguration());
        J(false, false);
    }

    @Override // androidx.appcompat.app.p
    public final void r() throws PackageManager.NameNotFoundException {
        String strC;
        this.f21399Q = true;
        J(false, true);
        T();
        Object obj = this.f21418k;
        if (obj instanceof Activity) {
            try {
                Activity activity = (Activity) obj;
                try {
                    strC = androidx.core.app.v.c(activity, activity.getComponentName());
                } catch (PackageManager.NameNotFoundException e12) {
                    throw new IllegalArgumentException(e12);
                }
            } catch (IllegalArgumentException unused) {
                strC = null;
            }
            if (strC != null) {
                AbstractC19927a abstractC19927a = this.f21423p;
                if (abstractC19927a == null) {
                    this.f21412d0 = true;
                } else {
                    abstractC19927a.r(true);
                }
            }
            synchronized (androidx.appcompat.app.p.f21566i) {
                androidx.appcompat.app.p.x(this);
                androidx.appcompat.app.p.f21565h.add(new WeakReference<>(this));
            }
        }
        this.f21402T = new Configuration(this.f21419l.getResources().getConfiguration());
        this.f21400R = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d  */
    @Override // androidx.appcompat.app.p
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void s() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.f21418k
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L11
            java.lang.Object r0 = androidx.appcompat.app.p.f21566i
            monitor-enter(r0)
            androidx.appcompat.app.p.x(r3)     // Catch: java.lang.Throwable -> Le
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            goto L11
        Le:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            throw r1
        L11:
            boolean r0 = r3.f21409a0
            if (r0 == 0) goto L20
            android.view.Window r0 = r3.f21420m
            android.view.View r0 = r0.getDecorView()
            java.lang.Runnable r1 = r3.f21411c0
            r0.removeCallbacks(r1)
        L20:
            r0 = 1
            r3.f21401S = r0
            int r0 = r3.f21403U
            r1 = -100
            if (r0 == r1) goto L4d
            java.lang.Object r0 = r3.f21418k
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L4d
            android.app.Activity r0 = (android.app.Activity) r0
            boolean r0 = r0.isChangingConfigurations()
            if (r0 == 0) goto L4d
            androidx.collection.o1<java.lang.String, java.lang.Integer> r0 = androidx.appcompat.app.AppCompatDelegateImpl.f21380j0
            java.lang.Object r1 = r3.f21418k
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            int r2 = r3.f21403U
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            goto L5c
        L4d:
            androidx.collection.o1<java.lang.String, java.lang.Integer> r0 = androidx.appcompat.app.AppCompatDelegateImpl.f21380j0
            java.lang.Object r1 = r3.f21418k
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            r0.remove(r1)
        L5c:
            androidx.appcompat.app.a r0 = r3.f21423p
            if (r0 == 0) goto L63
            r0.n()
        L63:
            androidx.appcompat.app.AppCompatDelegateImpl$n r0 = r3.f21407Y
            if (r0 == 0) goto L6a
            r0.a()
        L6a:
            androidx.appcompat.app.AppCompatDelegateImpl$l r0 = r3.f21408Z
            if (r0 == 0) goto L71
            r0.a()
        L71:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.s():void");
    }

    @Override // androidx.appcompat.app.p
    public final void t() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        S();
    }

    @Override // androidx.appcompat.app.p
    public final void u() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        W();
        AbstractC19927a abstractC19927a = this.f21423p;
        if (abstractC19927a != null) {
            abstractC19927a.v(true);
        }
    }

    @Override // androidx.appcompat.app.p
    public final void v() throws PackageManager.NameNotFoundException {
        J(true, false);
    }

    @Override // androidx.appcompat.app.p
    public final void w() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        W();
        AbstractC19927a abstractC19927a = this.f21423p;
        if (abstractC19927a != null) {
            abstractC19927a.v(false);
        }
    }

    @Override // androidx.appcompat.app.p
    public final boolean y(int i12) {
        if (i12 == 8) {
            i12 = 108;
        } else if (i12 == 9) {
            i12 = 109;
        }
        if (this.f21394L && i12 == 108) {
            return false;
        }
        if (this.f21390H && i12 == 1) {
            this.f21390H = false;
        }
        if (i12 == 1) {
            d0();
            this.f21394L = true;
            return true;
        }
        if (i12 == 2) {
            d0();
            this.f21388F = true;
            return true;
        }
        if (i12 == 5) {
            d0();
            this.f21389G = true;
            return true;
        }
        if (i12 == 10) {
            d0();
            this.f21392J = true;
            return true;
        }
        if (i12 == 108) {
            d0();
            this.f21390H = true;
            return true;
        }
        if (i12 != 109) {
            return this.f21420m.requestFeature(i12);
        }
        d0();
        this.f21391I = true;
        return true;
    }
}
