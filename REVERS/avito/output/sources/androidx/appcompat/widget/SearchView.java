package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.K0;
import androidx.core.view.C22823h0;
import androidx.customview.view.AbsSavedState;
import com.avito.android.R;
import j.InterfaceC42164u;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import l.C43521a;
import shark.AndroidResourceIdNames;

/* loaded from: classes.dex */
public class SearchView extends LinearLayoutCompat implements androidx.appcompat.view.c {

    /* renamed from: n0, reason: collision with root package name */
    public static final m f22252n0;

    /* renamed from: A, reason: collision with root package name */
    public final Rect f22253A;

    /* renamed from: B, reason: collision with root package name */
    public final Rect f22254B;

    /* renamed from: C, reason: collision with root package name */
    public final int[] f22255C;

    /* renamed from: D, reason: collision with root package name */
    public final int[] f22256D;

    /* renamed from: E, reason: collision with root package name */
    public final ImageView f22257E;

    /* renamed from: F, reason: collision with root package name */
    public final Drawable f22258F;

    /* renamed from: G, reason: collision with root package name */
    public final int f22259G;

    /* renamed from: H, reason: collision with root package name */
    public final int f22260H;

    /* renamed from: I, reason: collision with root package name */
    public final Intent f22261I;

    /* renamed from: J, reason: collision with root package name */
    public final Intent f22262J;

    /* renamed from: K, reason: collision with root package name */
    public final CharSequence f22263K;

    /* renamed from: L, reason: collision with root package name */
    public k f22264L;

    /* renamed from: M, reason: collision with root package name */
    public j f22265M;

    /* renamed from: N, reason: collision with root package name */
    public View.OnFocusChangeListener f22266N;

    /* renamed from: O, reason: collision with root package name */
    public l f22267O;

    /* renamed from: P, reason: collision with root package name */
    public View.OnClickListener f22268P;

    /* renamed from: Q, reason: collision with root package name */
    public boolean f22269Q;

    /* renamed from: R, reason: collision with root package name */
    public boolean f22270R;

    /* renamed from: S, reason: collision with root package name */
    public androidx.cursoradapter.widget.a f22271S;

    /* renamed from: T, reason: collision with root package name */
    public boolean f22272T;

    /* renamed from: U, reason: collision with root package name */
    public CharSequence f22273U;

    /* renamed from: V, reason: collision with root package name */
    public boolean f22274V;

    /* renamed from: W, reason: collision with root package name */
    public boolean f22275W;

    /* renamed from: a0, reason: collision with root package name */
    public int f22276a0;

    /* renamed from: b0, reason: collision with root package name */
    public boolean f22277b0;

    /* renamed from: c0, reason: collision with root package name */
    public String f22278c0;

    /* renamed from: d0, reason: collision with root package name */
    public CharSequence f22279d0;

    /* renamed from: e0, reason: collision with root package name */
    public boolean f22280e0;

    /* renamed from: f0, reason: collision with root package name */
    public int f22281f0;

    /* renamed from: g0, reason: collision with root package name */
    public SearchableInfo f22282g0;

    /* renamed from: h0, reason: collision with root package name */
    public Bundle f22283h0;

    /* renamed from: i0, reason: collision with root package name */
    public final Runnable f22284i0;

    /* renamed from: j0, reason: collision with root package name */
    public final Runnable f22285j0;

    /* renamed from: k0, reason: collision with root package name */
    public final WeakHashMap<String, Drawable.ConstantState> f22286k0;

    /* renamed from: l0, reason: collision with root package name */
    public final View.OnClickListener f22287l0;

    /* renamed from: m0, reason: collision with root package name */
    public final TextWatcher f22288m0;

    /* renamed from: q, reason: collision with root package name */
    public final SearchAutoComplete f22289q;

    /* renamed from: r, reason: collision with root package name */
    public final View f22290r;

    /* renamed from: s, reason: collision with root package name */
    public final View f22291s;

    /* renamed from: t, reason: collision with root package name */
    public final View f22292t;

    /* renamed from: u, reason: collision with root package name */
    public final ImageView f22293u;

    /* renamed from: v, reason: collision with root package name */
    public final ImageView f22294v;

    /* renamed from: w, reason: collision with root package name */
    public final ImageView f22295w;

    /* renamed from: x, reason: collision with root package name */
    public final ImageView f22296x;

    /* renamed from: y, reason: collision with root package name */
    public final View f22297y;

    /* renamed from: z, reason: collision with root package name */
    public n f22298z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: d, reason: collision with root package name */
        public boolean f22299d;

        public class a implements Parcelable.ClassLoaderCreator<SavedState> {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i12) {
                return new SavedState[i12];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f22299d = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public final String toString() {
            StringBuilder sb2 = new StringBuilder("SearchView.SavedState{");
            sb2.append(Integer.toHexString(System.identityHashCode(this)));
            sb2.append(" isIconified=");
            return androidx.appcompat.app.r.s("}", sb2, this.f22299d);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i12) {
            super.writeToParcel(parcel, i12);
            parcel.writeValue(Boolean.valueOf(this.f22299d));
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            SearchView.this.w();
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            androidx.cursoradapter.widget.a aVar = SearchView.this.f22271S;
            if (aVar instanceof w0) {
                aVar.b(null);
            }
        }
    }

    public class d implements View.OnClickListener {
        public d() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            SearchView searchView = SearchView.this;
            ImageView imageView = searchView.f22293u;
            SearchAutoComplete searchAutoComplete = searchView.f22289q;
            if (view == imageView) {
                searchView.A(false);
                searchAutoComplete.requestFocus();
                searchAutoComplete.setImeVisibility(true);
                View.OnClickListener onClickListener = searchView.f22268P;
                if (onClickListener != null) {
                    onClickListener.onClick(searchView);
                    return;
                }
                return;
            }
            if (view == searchView.f22295w) {
                searchView.q();
                return;
            }
            if (view == searchView.f22294v) {
                searchView.u();
                return;
            }
            if (view != searchView.f22296x) {
                if (view == searchAutoComplete) {
                    searchView.p();
                    return;
                }
                return;
            }
            SearchableInfo searchableInfo = searchView.f22282g0;
            if (searchableInfo == null) {
                return;
            }
            try {
                if (!searchableInfo.getVoiceSearchLaunchWebSearch()) {
                    if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                        searchView.getContext().startActivity(searchView.o(searchView.f22262J, searchableInfo));
                    }
                } else {
                    Intent intent = new Intent(searchView.f22261I);
                    ComponentName searchActivity = searchableInfo.getSearchActivity();
                    intent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
                    searchView.getContext().startActivity(intent);
                }
            } catch (ActivityNotFoundException unused) {
            }
        }
    }

    public class e implements View.OnKeyListener {
        public e() {
        }

        @Override // android.view.View.OnKeyListener
        public final boolean onKey(View view, int i12, KeyEvent keyEvent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            SearchView searchView = SearchView.this;
            if (searchView.f22282g0 == null) {
                return false;
            }
            SearchAutoComplete searchAutoComplete = searchView.f22289q;
            if (!searchAutoComplete.isPopupShowing() || searchAutoComplete.getListSelection() == -1) {
                if (TextUtils.getTrimmedLength(searchAutoComplete.getText()) == 0 || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i12 != 66) {
                    return false;
                }
                view.cancelLongPress();
                searchView.getContext().startActivity(searchView.n(null, "android.intent.action.SEARCH", null, searchAutoComplete.getText().toString()));
                return true;
            }
            if (searchView.f22282g0 == null || searchView.f22271S == null || keyEvent.getAction() != 0 || !keyEvent.hasNoModifiers()) {
                return false;
            }
            if (i12 == 66 || i12 == 84 || i12 == 61) {
                return searchView.r(searchAutoComplete.getListSelection());
            }
            if (i12 != 21 && i12 != 22) {
                if (i12 != 19) {
                    return false;
                }
                searchAutoComplete.getListSelection();
                return false;
            }
            searchAutoComplete.setSelection(i12 == 21 ? 0 : searchAutoComplete.length());
            searchAutoComplete.setListSelection(0);
            searchAutoComplete.clearListSelection();
            searchAutoComplete.a();
            return true;
        }
    }

    public class f implements TextView.OnEditorActionListener {
        public f() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i12, KeyEvent keyEvent) {
            SearchView.this.u();
            return true;
        }
    }

    public class g implements AdapterView.OnItemClickListener {
        public g() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public final void onItemClick(AdapterView<?> adapterView, View view, int i12, long j12) {
            SearchView.this.r(i12);
        }
    }

    @j.X
    public static class i {
        @InterfaceC42164u
        public static void a(AutoCompleteTextView autoCompleteTextView) {
            autoCompleteTextView.refreshAutoCompleteResults();
        }

        @InterfaceC42164u
        public static void b(SearchAutoComplete searchAutoComplete, int i12) {
            searchAutoComplete.setInputMethodMode(i12);
        }
    }

    public interface j {
        boolean t0();
    }

    public interface k {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    public interface l {
        boolean a();

        boolean b();
    }

    public static class m {

        /* renamed from: a, reason: collision with root package name */
        public final Method f22313a;

        /* renamed from: b, reason: collision with root package name */
        public final Method f22314b;

        /* renamed from: c, reason: collision with root package name */
        public final Method f22315c;

        @SuppressLint({"DiscouragedPrivateApi", "SoonBlockedPrivateApi"})
        public m() throws NoSuchMethodException, SecurityException {
            this.f22313a = null;
            this.f22314b = null;
            this.f22315c = null;
            a();
            try {
                Method declaredMethod = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.f22313a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            try {
                Method declaredMethod2 = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.f22314b = declaredMethod2;
                declaredMethod2.setAccessible(true);
            } catch (NoSuchMethodException unused2) {
            }
            try {
                Method method = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                this.f22315c = method;
                method.setAccessible(true);
            } catch (NoSuchMethodException unused3) {
            }
        }

        public static void a() {
            if (Build.VERSION.SDK_INT >= 29) {
                throw new UnsupportedClassVersionError("This function can only be used for API Level < 29.");
            }
        }
    }

    public static class n extends TouchDelegate {

        /* renamed from: a, reason: collision with root package name */
        public final View f22316a;

        /* renamed from: b, reason: collision with root package name */
        public final Rect f22317b;

        /* renamed from: c, reason: collision with root package name */
        public final Rect f22318c;

        /* renamed from: d, reason: collision with root package name */
        public final Rect f22319d;

        /* renamed from: e, reason: collision with root package name */
        public final int f22320e;

        /* renamed from: f, reason: collision with root package name */
        public boolean f22321f;

        public n(View view, Rect rect, Rect rect2) {
            super(rect, view);
            int scaledTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.f22320e = scaledTouchSlop;
            Rect rect3 = new Rect();
            this.f22317b = rect3;
            Rect rect4 = new Rect();
            this.f22319d = rect4;
            Rect rect5 = new Rect();
            this.f22318c = rect5;
            rect3.set(rect);
            rect4.set(rect);
            int i12 = -scaledTouchSlop;
            rect4.inset(i12, i12);
            rect5.set(rect2);
            this.f22316a = view;
        }

        @Override // android.view.TouchDelegate
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z12;
            boolean z13;
            int x12 = (int) motionEvent.getX();
            int y12 = (int) motionEvent.getY();
            int action = motionEvent.getAction();
            boolean z14 = true;
            if (action != 0) {
                if (action == 1 || action == 2) {
                    z13 = this.f22321f;
                    if (z13 && !this.f22319d.contains(x12, y12)) {
                        z14 = z13;
                        z12 = false;
                    }
                } else {
                    if (action == 3) {
                        z13 = this.f22321f;
                        this.f22321f = false;
                    }
                    z12 = true;
                    z14 = false;
                }
                z14 = z13;
                z12 = true;
            } else if (this.f22317b.contains(x12, y12)) {
                this.f22321f = true;
                z12 = true;
            } else {
                z12 = true;
                z14 = false;
            }
            if (!z14) {
                return false;
            }
            Rect rect = this.f22318c;
            View view = this.f22316a;
            if (!z12 || rect.contains(x12, y12)) {
                motionEvent.setLocation(x12 - rect.left, y12 - rect.top);
            } else {
                motionEvent.setLocation(view.getWidth() / 2, view.getHeight() / 2);
            }
            return view.dispatchTouchEvent(motionEvent);
        }
    }

    static {
        f22252n0 = Build.VERSION.SDK_INT < 29 ? new m() : null;
    }

    public SearchView(@j.N Context context) {
        super(context, null, R.attr.searchViewStyle);
        this.f22253A = new Rect();
        this.f22254B = new Rect();
        this.f22255C = new int[2];
        this.f22256D = new int[2];
        this.f22284i0 = new b();
        this.f22285j0 = new c();
        this.f22286k0 = new WeakHashMap<>();
        d dVar = new d();
        e eVar = new e();
        f fVar = new f();
        g gVar = new g();
        h hVar = new h();
        a aVar = new a();
        int[] iArr = C43521a.m.f413372u;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, iArr, R.attr.searchViewStyle, 0);
        D0 d02 = new D0(typedArrayObtainStyledAttributes, context);
        C22823h0.B(this, context, iArr, null, typedArrayObtainStyledAttributes, R.attr.searchViewStyle);
        LayoutInflater.from(context).inflate(typedArrayObtainStyledAttributes.getResourceId(17, R.layout.abc_search_view), (ViewGroup) this, true);
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) findViewById(R.id.search_src_text);
        this.f22289q = searchAutoComplete;
        searchAutoComplete.setSearchView(this);
        this.f22290r = findViewById(R.id.search_edit_frame);
        View viewFindViewById = findViewById(R.id.search_plate);
        this.f22291s = viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.submit_area);
        this.f22292t = viewFindViewById2;
        ImageView imageView = (ImageView) findViewById(R.id.search_button);
        this.f22293u = imageView;
        ImageView imageView2 = (ImageView) findViewById(R.id.search_go_btn);
        this.f22294v = imageView2;
        ImageView imageView3 = (ImageView) findViewById(R.id.search_close_btn);
        this.f22295w = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R.id.search_voice_btn);
        this.f22296x = imageView4;
        ImageView imageView5 = (ImageView) findViewById(R.id.search_mag_icon);
        this.f22257E = imageView5;
        viewFindViewById.setBackground(d02.b(18));
        viewFindViewById2.setBackground(d02.b(23));
        imageView.setImageDrawable(d02.b(21));
        imageView2.setImageDrawable(d02.b(13));
        imageView3.setImageDrawable(d02.b(10));
        imageView4.setImageDrawable(d02.b(26));
        imageView5.setImageDrawable(d02.b(21));
        this.f22258F = d02.b(20);
        K0.a.a(imageView, getResources().getString(R.string.abc_searchview_description_search));
        this.f22259G = typedArrayObtainStyledAttributes.getResourceId(24, R.layout.abc_search_dropdown_item_icons_2line);
        this.f22260H = typedArrayObtainStyledAttributes.getResourceId(11, 0);
        imageView.setOnClickListener(dVar);
        imageView3.setOnClickListener(dVar);
        imageView2.setOnClickListener(dVar);
        imageView4.setOnClickListener(dVar);
        searchAutoComplete.setOnClickListener(dVar);
        searchAutoComplete.addTextChangedListener(aVar);
        searchAutoComplete.setOnEditorActionListener(fVar);
        searchAutoComplete.setOnItemClickListener(gVar);
        searchAutoComplete.setOnItemSelectedListener(hVar);
        searchAutoComplete.setOnKeyListener(eVar);
        searchAutoComplete.setOnFocusChangeListener(new r0(this));
        setIconifiedByDefault(typedArrayObtainStyledAttributes.getBoolean(16, true));
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, -1);
        if (dimensionPixelSize != -1) {
            setMaxWidth(dimensionPixelSize);
        }
        this.f22263K = typedArrayObtainStyledAttributes.getText(12);
        this.f22273U = typedArrayObtainStyledAttributes.getText(19);
        int i12 = typedArrayObtainStyledAttributes.getInt(6, -1);
        if (i12 != -1) {
            setImeOptions(i12);
        }
        int i13 = typedArrayObtainStyledAttributes.getInt(5, -1);
        if (i13 != -1) {
            setInputType(i13);
        }
        setFocusable(typedArrayObtainStyledAttributes.getBoolean(1, true));
        d02.f();
        Intent intent = new Intent("android.speech.action.WEB_SEARCH");
        this.f22261I = intent;
        intent.addFlags(268435456);
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        Intent intent2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.f22262J = intent2;
        intent2.addFlags(268435456);
        View viewFindViewById3 = findViewById(searchAutoComplete.getDropDownAnchor());
        this.f22297y = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.addOnLayoutChangeListener(new s0(this));
        }
        A(this.f22269Q);
        x();
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width);
    }

    private void setQuery(CharSequence charSequence) {
        SearchAutoComplete searchAutoComplete = this.f22289q;
        searchAutoComplete.setText(charSequence);
        searchAutoComplete.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    public final void A(boolean z12) {
        this.f22270R = z12;
        int i12 = 8;
        int i13 = z12 ? 0 : 8;
        boolean zIsEmpty = TextUtils.isEmpty(this.f22289q.getText());
        this.f22293u.setVisibility(i13);
        z(!zIsEmpty);
        this.f22290r.setVisibility(z12 ? 8 : 0);
        ImageView imageView = this.f22257E;
        imageView.setVisibility((imageView.getDrawable() == null || this.f22269Q) ? 8 : 0);
        v();
        if (this.f22277b0 && !this.f22270R && zIsEmpty) {
            this.f22294v.setVisibility(8);
            i12 = 0;
        }
        this.f22296x.setVisibility(i12);
        y();
    }

    @Override // androidx.appcompat.view.c
    public final void a() {
        SearchAutoComplete searchAutoComplete = this.f22289q;
        searchAutoComplete.setText("");
        searchAutoComplete.setSelection(searchAutoComplete.length());
        this.f22279d0 = "";
        clearFocus();
        A(true);
        searchAutoComplete.setImeOptions(this.f22281f0);
        this.f22280e0 = false;
    }

    @Override // androidx.appcompat.view.c
    public final void c() {
        if (this.f22280e0) {
            return;
        }
        this.f22280e0 = true;
        SearchAutoComplete searchAutoComplete = this.f22289q;
        int imeOptions = searchAutoComplete.getImeOptions();
        this.f22281f0 = imeOptions;
        searchAutoComplete.setImeOptions(imeOptions | 33554432);
        searchAutoComplete.setText("");
        setIconified(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void clearFocus() {
        this.f22275W = true;
        super.clearFocus();
        SearchAutoComplete searchAutoComplete = this.f22289q;
        searchAutoComplete.clearFocus();
        searchAutoComplete.setImeVisibility(false);
        this.f22275W = false;
    }

    public int getImeOptions() {
        return this.f22289q.getImeOptions();
    }

    public int getInputType() {
        return this.f22289q.getInputType();
    }

    public int getMaxWidth() {
        return this.f22276a0;
    }

    public CharSequence getQuery() {
        return this.f22289q.getText();
    }

    @j.P
    public CharSequence getQueryHint() {
        CharSequence charSequence = this.f22273U;
        if (charSequence != null) {
            return charSequence;
        }
        SearchableInfo searchableInfo = this.f22282g0;
        return (searchableInfo == null || searchableInfo.getHintId() == 0) ? this.f22263K : getContext().getText(this.f22282g0.getHintId());
    }

    public int getSuggestionCommitIconResId() {
        return this.f22260H;
    }

    public int getSuggestionRowLayout() {
        return this.f22259G;
    }

    public androidx.cursoradapter.widget.a getSuggestionsAdapter() {
        return this.f22271S;
    }

    public final Intent n(Uri uri, String str, String str2, String str3) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.f22279d0);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        Bundle bundle = this.f22283h0;
        if (bundle != null) {
            intent.putExtra("app_data", bundle);
        }
        intent.setComponent(this.f22282g0.getSearchActivity());
        return intent;
    }

    public final Intent o(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent("android.intent.action.SEARCH");
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1107296256);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.f22283h0;
        if (bundle2 != null) {
            bundle.putParcelable("app_data", bundle2);
        }
        Intent intent3 = new Intent(intent);
        Resources resources = getResources();
        String string = searchableInfo.getVoiceLanguageModeId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form";
        String string2 = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string3 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        int voiceMaxResults = searchableInfo.getVoiceMaxResults() != 0 ? searchableInfo.getVoiceMaxResults() : 1;
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", string);
        intent3.putExtra("android.speech.extra.PROMPT", string2);
        intent3.putExtra("android.speech.extra.LANGUAGE", string3);
        intent3.putExtra("android.speech.extra.MAX_RESULTS", voiceMaxResults);
        intent3.putExtra("calling_package", searchActivity != null ? searchActivity.flattenToShortString() : null);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent3;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        removeCallbacks(this.f22284i0);
        post(this.f22285j0);
        super.onDetachedFromWindow();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z12, int i12, int i13, int i14, int i15) {
        super.onLayout(z12, i12, i13, i14, i15);
        if (z12) {
            int[] iArr = this.f22255C;
            SearchAutoComplete searchAutoComplete = this.f22289q;
            searchAutoComplete.getLocationInWindow(iArr);
            int[] iArr2 = this.f22256D;
            getLocationInWindow(iArr2);
            int i16 = iArr[1] - iArr2[1];
            int i17 = iArr[0] - iArr2[0];
            int width = searchAutoComplete.getWidth() + i17;
            int height = searchAutoComplete.getHeight() + i16;
            Rect rect = this.f22253A;
            rect.set(i17, i16, width, height);
            int i18 = rect.left;
            int i19 = rect.right;
            int i22 = i15 - i13;
            Rect rect2 = this.f22254B;
            rect2.set(i18, 0, i19, i22);
            n nVar = this.f22298z;
            if (nVar == null) {
                n nVar2 = new n(searchAutoComplete, rect2, rect);
                this.f22298z = nVar2;
                setTouchDelegate(nVar2);
            } else {
                nVar.f22317b.set(rect2);
                Rect rect3 = nVar.f22319d;
                rect3.set(rect2);
                int i23 = -nVar.f22320e;
                rect3.inset(i23, i23);
                nVar.f22318c.set(rect);
            }
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i12, int i13) {
        int i14;
        if (this.f22270R) {
            super.onMeasure(i12, i13);
            return;
        }
        int mode = View.MeasureSpec.getMode(i12);
        int size = View.MeasureSpec.getSize(i12);
        if (mode == Integer.MIN_VALUE) {
            int i15 = this.f22276a0;
            size = i15 > 0 ? Math.min(i15, size) : Math.min(getPreferredWidth(), size);
        } else if (mode == 0) {
            size = this.f22276a0;
            if (size <= 0) {
                size = getPreferredWidth();
            }
        } else if (mode == 1073741824 && (i14 = this.f22276a0) > 0) {
            size = Math.min(i14, size);
        }
        int mode2 = View.MeasureSpec.getMode(i13);
        int size2 = View.MeasureSpec.getSize(i13);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getPreferredHeight(), size2);
        } else if (mode2 == 0) {
            size2 = getPreferredHeight();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f45355b);
        A(savedState.f22299d);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f22299d = this.f22270R;
        return savedState;
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z12) {
        super.onWindowFocusChanged(z12);
        post(this.f22284i0);
    }

    public final void p() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i12 = Build.VERSION.SDK_INT;
        SearchAutoComplete searchAutoComplete = this.f22289q;
        if (i12 >= 29) {
            i.a(searchAutoComplete);
            return;
        }
        m mVar = f22252n0;
        mVar.getClass();
        m.a();
        Method method = mVar.f22313a;
        if (method != null) {
            try {
                method.invoke(searchAutoComplete, new Object[0]);
            } catch (Exception unused) {
            }
        }
        mVar.getClass();
        m.a();
        Method method2 = mVar.f22314b;
        if (method2 != null) {
            try {
                method2.invoke(searchAutoComplete, new Object[0]);
            } catch (Exception unused2) {
            }
        }
    }

    public final void q() {
        SearchAutoComplete searchAutoComplete = this.f22289q;
        if (!TextUtils.isEmpty(searchAutoComplete.getText())) {
            searchAutoComplete.setText("");
            searchAutoComplete.requestFocus();
            searchAutoComplete.setImeVisibility(true);
        } else if (this.f22269Q) {
            j jVar = this.f22265M;
            if (jVar == null || !jVar.t0()) {
                clearFocus();
                A(true);
            }
        }
    }

    public final boolean r(int i12) {
        String strL;
        l lVar = this.f22267O;
        if (lVar != null && lVar.b()) {
            return false;
        }
        Cursor cursor = this.f22271S.f45341d;
        if (cursor != null && cursor.moveToPosition(i12)) {
            Intent intentN = null;
            try {
                try {
                    int i13 = w0.f22639z;
                    String strL2 = w0.l(cursor, cursor.getColumnIndex("suggest_intent_action"));
                    if (strL2 == null) {
                        strL2 = this.f22282g0.getSuggestIntentAction();
                    }
                    if (strL2 == null) {
                        strL2 = "android.intent.action.SEARCH";
                    }
                    String strL3 = w0.l(cursor, cursor.getColumnIndex("suggest_intent_data"));
                    if (strL3 == null) {
                        strL3 = this.f22282g0.getSuggestIntentData();
                    }
                    if (strL3 != null && (strL = w0.l(cursor, cursor.getColumnIndex("suggest_intent_data_id"))) != null) {
                        strL3 = strL3 + "/" + Uri.encode(strL);
                    }
                    intentN = n(strL3 == null ? null : Uri.parse(strL3), strL2, w0.l(cursor, cursor.getColumnIndex("suggest_intent_extra_data")), w0.l(cursor, cursor.getColumnIndex("suggest_intent_query")));
                } catch (RuntimeException unused) {
                }
            } catch (RuntimeException unused2) {
                cursor.getPosition();
            }
            if (intentN != null) {
                try {
                    getContext().startActivity(intentN);
                } catch (RuntimeException unused3) {
                    intentN.toString();
                }
            }
        }
        SearchAutoComplete searchAutoComplete = this.f22289q;
        searchAutoComplete.setImeVisibility(false);
        searchAutoComplete.dismissDropDown();
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean requestFocus(int i12, Rect rect) {
        if (this.f22275W || !isFocusable()) {
            return false;
        }
        if (this.f22270R) {
            return super.requestFocus(i12, rect);
        }
        boolean zRequestFocus = this.f22289q.requestFocus(i12, rect);
        if (zRequestFocus) {
            A(false);
        }
        return zRequestFocus;
    }

    public final void s(int i12) {
        l lVar = this.f22267O;
        if (lVar == null || !lVar.a()) {
            Editable text = this.f22289q.getText();
            Cursor cursor = this.f22271S.f45341d;
            if (cursor == null) {
                return;
            }
            if (!cursor.moveToPosition(i12)) {
                setQuery(text);
                return;
            }
            String strA = this.f22271S.a(cursor);
            if (strA != null) {
                setQuery(strA);
            } else {
                setQuery(text);
            }
        }
    }

    @RestrictTo
    public void setAppSearchData(Bundle bundle) {
        this.f22283h0 = bundle;
    }

    public void setIconified(boolean z12) {
        if (z12) {
            q();
            return;
        }
        A(false);
        SearchAutoComplete searchAutoComplete = this.f22289q;
        searchAutoComplete.requestFocus();
        searchAutoComplete.setImeVisibility(true);
        View.OnClickListener onClickListener = this.f22268P;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    public void setIconifiedByDefault(boolean z12) {
        if (this.f22269Q == z12) {
            return;
        }
        this.f22269Q = z12;
        A(z12);
        x();
    }

    public void setImeOptions(int i12) {
        this.f22289q.setImeOptions(i12);
    }

    public void setInputType(int i12) {
        this.f22289q.setInputType(i12);
    }

    public void setMaxWidth(int i12) {
        this.f22276a0 = i12;
        requestLayout();
    }

    public void setOnCloseListener(j jVar) {
        this.f22265M = jVar;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.f22266N = onFocusChangeListener;
    }

    public void setOnQueryTextListener(k kVar) {
        this.f22264L = kVar;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.f22268P = onClickListener;
    }

    public void setOnSuggestionListener(l lVar) {
        this.f22267O = lVar;
    }

    public void setQueryHint(@j.P CharSequence charSequence) {
        this.f22273U = charSequence;
        x();
    }

    public void setQueryRefinementEnabled(boolean z12) {
        this.f22274V = z12;
        androidx.cursoradapter.widget.a aVar = this.f22271S;
        if (aVar instanceof w0) {
            ((w0) aVar).f22645r = z12 ? 2 : 1;
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.f22282g0 = searchableInfo;
        Intent intent = null;
        SearchAutoComplete searchAutoComplete = this.f22289q;
        if (searchableInfo != null) {
            searchAutoComplete.setThreshold(searchableInfo.getSuggestThreshold());
            searchAutoComplete.setImeOptions(this.f22282g0.getImeOptions());
            int inputType = this.f22282g0.getInputType();
            if ((inputType & 15) == 1) {
                inputType &= -65537;
                if (this.f22282g0.getSuggestAuthority() != null) {
                    inputType |= 589824;
                }
            }
            searchAutoComplete.setInputType(inputType);
            androidx.cursoradapter.widget.a aVar = this.f22271S;
            if (aVar != null) {
                aVar.b(null);
            }
            if (this.f22282g0.getSuggestAuthority() != null) {
                w0 w0Var = new w0(getContext(), this, this.f22282g0, this.f22286k0);
                this.f22271S = w0Var;
                searchAutoComplete.setAdapter(w0Var);
                ((w0) this.f22271S).f22645r = this.f22274V ? 2 : 1;
            }
            x();
        }
        SearchableInfo searchableInfo2 = this.f22282g0;
        boolean z12 = false;
        if (searchableInfo2 != null && searchableInfo2.getVoiceSearchEnabled()) {
            if (this.f22282g0.getVoiceSearchLaunchWebSearch()) {
                intent = this.f22261I;
            } else if (this.f22282g0.getVoiceSearchLaunchRecognizer()) {
                intent = this.f22262J;
            }
            if (intent != null) {
                z12 = getContext().getPackageManager().resolveActivity(intent, AndroidResourceIdNames.RESOURCE_ID_TYPE_ITERATOR) != null;
            }
        }
        this.f22277b0 = z12;
        if (z12) {
            searchAutoComplete.setPrivateImeOptions("nm");
        }
        A(this.f22270R);
    }

    public void setSubmitButtonEnabled(boolean z12) {
        this.f22272T = z12;
        A(this.f22270R);
    }

    public void setSuggestionsAdapter(androidx.cursoradapter.widget.a aVar) {
        this.f22271S = aVar;
        this.f22289q.setAdapter(aVar);
    }

    public final void t(@j.P CharSequence charSequence) {
        setQuery(charSequence);
    }

    public final void u() {
        SearchAutoComplete searchAutoComplete = this.f22289q;
        Editable text = searchAutoComplete.getText();
        if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
            return;
        }
        k kVar = this.f22264L;
        if (kVar == null || !kVar.onQueryTextSubmit(text.toString())) {
            if (this.f22282g0 != null) {
                getContext().startActivity(n(null, "android.intent.action.SEARCH", null, text.toString()));
            }
            searchAutoComplete.setImeVisibility(false);
            searchAutoComplete.dismissDropDown();
        }
    }

    public final void v() {
        boolean zIsEmpty = TextUtils.isEmpty(this.f22289q.getText());
        int i12 = (!zIsEmpty || (this.f22269Q && !this.f22280e0)) ? 0 : 8;
        ImageView imageView = this.f22295w;
        imageView.setVisibility(i12);
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            drawable.setState(!zIsEmpty ? ViewGroup.ENABLED_STATE_SET : ViewGroup.EMPTY_STATE_SET);
        }
    }

    public final void w() {
        int[] iArr = this.f22289q.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable background = this.f22291s.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.f22292t.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    public final void x() {
        Drawable drawable;
        CharSequence queryHint = getQueryHint();
        if (queryHint == null) {
            queryHint = "";
        }
        boolean z12 = this.f22269Q;
        SearchAutoComplete searchAutoComplete = this.f22289q;
        if (z12 && (drawable = this.f22258F) != null) {
            int textSize = (int) (searchAutoComplete.getTextSize() * 1.25d);
            drawable.setBounds(0, 0, textSize, textSize);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
            spannableStringBuilder.setSpan(new ImageSpan(drawable), 1, 2, 33);
            spannableStringBuilder.append(queryHint);
            queryHint = spannableStringBuilder;
        }
        searchAutoComplete.setHint(queryHint);
    }

    public final void y() {
        this.f22292t.setVisibility(((this.f22272T || this.f22277b0) && !this.f22270R && (this.f22294v.getVisibility() == 0 || this.f22296x.getVisibility() == 0)) ? 0 : 8);
    }

    public final void z(boolean z12) {
        boolean z13 = this.f22272T;
        this.f22294v.setVisibility((!z13 || !(z13 || this.f22277b0) || this.f22270R || !hasFocus() || (!z12 && this.f22277b0)) ? 8 : 0);
    }

    @RestrictTo
    public static class SearchAutoComplete extends C19953i {

        /* renamed from: b, reason: collision with root package name */
        public int f22300b;

        /* renamed from: c, reason: collision with root package name */
        public SearchView f22301c;

        /* renamed from: d, reason: collision with root package name */
        public boolean f22302d;

        /* renamed from: e, reason: collision with root package name */
        public final Runnable f22303e;

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                SearchAutoComplete searchAutoComplete = SearchAutoComplete.this;
                if (searchAutoComplete.f22302d) {
                    ((InputMethodManager) searchAutoComplete.getContext().getSystemService("input_method")).showSoftInput(searchAutoComplete, 0);
                    searchAutoComplete.f22302d = false;
                }
            }
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            super(context, attributeSet, R.attr.autoCompleteTextViewStyle);
            this.f22303e = new a();
            this.f22300b = getThreshold();
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i12 = configuration.screenWidthDp;
            int i13 = configuration.screenHeightDp;
            if (i12 >= 960 && i13 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (i12 < 600) {
                return (i12 < 640 || i13 < 480) ? 160 : 192;
            }
            return 192;
        }

        public final void a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (Build.VERSION.SDK_INT >= 29) {
                i.b(this, 1);
                if (enoughToFilter()) {
                    showDropDown();
                    return;
                }
                return;
            }
            m mVar = SearchView.f22252n0;
            mVar.getClass();
            m.a();
            Method method = mVar.f22315c;
            if (method != null) {
                try {
                    method.invoke(this, Boolean.TRUE);
                } catch (Exception unused) {
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView
        public final boolean enoughToFilter() {
            return this.f22300b <= 0 || super.enoughToFilter();
        }

        @Override // androidx.appcompat.widget.C19953i, android.widget.TextView, android.view.View
        public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.f22302d) {
                Runnable runnable = this.f22303e;
                removeCallbacks(runnable);
                post(runnable);
            }
            return inputConnectionOnCreateInputConnection;
        }

        @Override // android.view.View
        public final void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onFocusChanged(boolean z12, int i12, Rect rect) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super.onFocusChanged(z12, i12, rect);
            SearchView searchView = this.f22301c;
            searchView.A(searchView.f22270R);
            searchView.post(searchView.f22284i0);
            if (searchView.f22289q.hasFocus()) {
                searchView.p();
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final boolean onKeyPreIme(int i12, KeyEvent keyEvent) {
            if (i12 == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.f22301c.clearFocus();
                        setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i12, keyEvent);
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onWindowFocusChanged(boolean z12) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super.onWindowFocusChanged(z12);
            if (z12 && this.f22301c.hasFocus() && getVisibility() == 0) {
                this.f22302d = true;
                Context context = getContext();
                m mVar = SearchView.f22252n0;
                if (context.getResources().getConfiguration().orientation == 2) {
                    a();
                }
            }
        }

        public void setImeVisibility(boolean z12) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            Runnable runnable = this.f22303e;
            if (!z12) {
                this.f22302d = false;
                removeCallbacks(runnable);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (!inputMethodManager.isActive(this)) {
                    this.f22302d = true;
                    return;
                }
                this.f22302d = false;
                removeCallbacks(runnable);
                inputMethodManager.showSoftInput(this, 0);
            }
        }

        public void setSearchView(SearchView searchView) {
            this.f22301c = searchView;
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int i12) {
            super.setThreshold(i12);
            this.f22300b = i12;
        }

        @Override // android.widget.AutoCompleteTextView
        public final void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        public final void replaceText(CharSequence charSequence) {
        }
    }

    public class a implements TextWatcher {
        public a() {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i12, int i13, int i14) {
            SearchView searchView = SearchView.this;
            Editable text = searchView.f22289q.getText();
            searchView.f22279d0 = text;
            boolean zIsEmpty = TextUtils.isEmpty(text);
            searchView.z(!zIsEmpty);
            int i15 = 8;
            if (searchView.f22277b0 && !searchView.f22270R && zIsEmpty) {
                searchView.f22294v.setVisibility(8);
                i15 = 0;
            }
            searchView.f22296x.setVisibility(i15);
            searchView.v();
            searchView.y();
            if (searchView.f22264L != null && !TextUtils.equals(charSequence, searchView.f22278c0)) {
                searchView.f22264L.onQueryTextChange(charSequence.toString());
            }
            searchView.f22278c0 = charSequence.toString();
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i12, int i13, int i14) {
        }
    }

    public class h implements AdapterView.OnItemSelectedListener {
        public h() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onItemSelected(AdapterView<?> adapterView, View view, int i12, long j12) {
            SearchView.this.s(i12);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
}
