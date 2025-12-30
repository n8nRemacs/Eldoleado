package androidx.appcompat.widget;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.l;
import j.InterfaceC42164u;
import j.InterfaceC42165v;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import m.C43852a;

/* loaded from: classes.dex */
public class AppCompatSpinner extends Spinner implements androidx.core.view.Y {

    /* renamed from: j, reason: collision with root package name */
    @j.g0
    @SuppressLint({"ResourceType"})
    public static final int[] f22066j = {R.attr.spinnerMode};

    /* renamed from: b, reason: collision with root package name */
    public final C19955j f22067b;

    /* renamed from: c, reason: collision with root package name */
    public final Context f22068c;

    /* renamed from: d, reason: collision with root package name */
    public final Z f22069d;

    /* renamed from: e, reason: collision with root package name */
    public SpinnerAdapter f22070e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f22071f;

    /* renamed from: g, reason: collision with root package name */
    public final f f22072g;

    /* renamed from: h, reason: collision with root package name */
    public int f22073h;

    /* renamed from: i, reason: collision with root package name */
    public final Rect f22074i;

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: b, reason: collision with root package name */
        public boolean f22075b;

        public class a implements Parcelable.Creator<SavedState> {
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                SavedState savedState = new SavedState(parcel);
                savedState.f22075b = parcel.readByte() != 0;
                return savedState;
            }

            @Override // android.os.Parcelable.Creator
            public final SavedState[] newArray(int i12) {
                return new SavedState[i12];
            }
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i12) {
            super.writeToParcel(parcel, i12);
            parcel.writeByte(this.f22075b ? (byte) 1 : (byte) 0);
        }
    }

    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            if (!appCompatSpinner.getInternalPopup().a()) {
                appCompatSpinner.f22072g.f(appCompatSpinner.getTextDirection(), appCompatSpinner.getTextAlignment());
            }
            ViewTreeObserver viewTreeObserver = appCompatSpinner.getViewTreeObserver();
            if (viewTreeObserver != null) {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
            }
        }
    }

    @j.X
    public static final class b {
        @InterfaceC42164u
        public static void a(@j.N ThemedSpinnerAdapter themedSpinnerAdapter, @j.P Resources.Theme theme) {
            if (Objects.equals(themedSpinnerAdapter.getDropDownViewTheme(), theme)) {
                return;
            }
            themedSpinnerAdapter.setDropDownViewTheme(theme);
        }
    }

    public static class d implements ListAdapter, SpinnerAdapter {

        /* renamed from: b, reason: collision with root package name */
        public SpinnerAdapter f22081b;

        /* renamed from: c, reason: collision with root package name */
        public ListAdapter f22082c;

        @Override // android.widget.ListAdapter
        public final boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.f22082c;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            SpinnerAdapter spinnerAdapter = this.f22081b;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        @Override // android.widget.SpinnerAdapter
        public final View getDropDownView(int i12, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.f22081b;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(i12, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i12) {
            SpinnerAdapter spinnerAdapter = this.f22081b;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(i12);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i12) {
            SpinnerAdapter spinnerAdapter = this.f22081b;
            if (spinnerAdapter == null) {
                return -1L;
            }
            return spinnerAdapter.getItemId(i12);
        }

        @Override // android.widget.Adapter
        public final int getItemViewType(int i12) {
            return 0;
        }

        @Override // android.widget.Adapter
        public final View getView(int i12, View view, ViewGroup viewGroup) {
            return getDropDownView(i12, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public final int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.Adapter
        public final boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.f22081b;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        @Override // android.widget.Adapter
        public final boolean isEmpty() {
            return getCount() == 0;
        }

        @Override // android.widget.ListAdapter
        public final boolean isEnabled(int i12) {
            ListAdapter listAdapter = this.f22082c;
            if (listAdapter != null) {
                return listAdapter.isEnabled(i12);
            }
            return true;
        }

        @Override // android.widget.Adapter
        public final void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.f22081b;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        @Override // android.widget.Adapter
        public final void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.f22081b;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    @j.k0
    public class e extends C19948f0 implements f {

        /* renamed from: D, reason: collision with root package name */
        public CharSequence f22083D;

        /* renamed from: E, reason: collision with root package name */
        public ListAdapter f22084E;

        /* renamed from: F, reason: collision with root package name */
        public final Rect f22085F;

        /* renamed from: G, reason: collision with root package name */
        public int f22086G;

        public class a implements AdapterView.OnItemClickListener {
            public a() {
            }

            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView<?> adapterView, View view, int i12, long j12) {
                e eVar = e.this;
                AppCompatSpinner.this.setSelection(i12);
                if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                    AppCompatSpinner.this.performItemClick(view, i12, eVar.f22084E.getItemId(i12));
                }
                eVar.dismiss();
            }
        }

        public class b implements ViewTreeObserver.OnGlobalLayoutListener {
            public b() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                e eVar = e.this;
                AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
                eVar.getClass();
                if (!appCompatSpinner.isAttachedToWindow() || !appCompatSpinner.getGlobalVisibleRect(eVar.f22085F)) {
                    eVar.dismiss();
                } else {
                    eVar.s();
                    eVar.show();
                }
            }
        }

        public class c implements PopupWindow.OnDismissListener {

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ ViewTreeObserver.OnGlobalLayoutListener f22090b;

            public c(ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
                this.f22090b = onGlobalLayoutListener;
            }

            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                if (viewTreeObserver != null) {
                    viewTreeObserver.removeGlobalOnLayoutListener(this.f22090b);
                }
            }
        }

        public e(Context context, AttributeSet attributeSet, int i12) {
            super(context, attributeSet, i12);
            this.f22085F = new Rect();
            this.f22520p = AppCompatSpinner.this;
            r();
            this.f22521q = new a();
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void f(int i12, int i13) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ViewTreeObserver viewTreeObserver;
            PopupWindow popupWindow = this.f22505A;
            boolean zIsShowing = popupWindow.isShowing();
            s();
            this.f22505A.setInputMethodMode(2);
            show();
            W w12 = this.f22508d;
            w12.setChoiceMode(1);
            w12.setTextDirection(i12);
            w12.setTextAlignment(i13);
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            int selectedItemPosition = appCompatSpinner.getSelectedItemPosition();
            W w13 = this.f22508d;
            if (popupWindow.isShowing() && w13 != null) {
                w13.setListSelectionHidden(false);
                w13.setSelection(selectedItemPosition);
                if (w13.getChoiceMode() != 0) {
                    w13.setItemChecked(selectedItemPosition, true);
                }
            }
            if (zIsShowing || (viewTreeObserver = appCompatSpinner.getViewTreeObserver()) == null) {
                return;
            }
            b bVar = new b();
            viewTreeObserver.addOnGlobalLayoutListener(bVar);
            this.f22505A.setOnDismissListener(new c(bVar));
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void i(int i12) {
            this.f22086G = i12;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final CharSequence m() {
            return this.f22083D;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void n(CharSequence charSequence) {
            this.f22083D = charSequence;
        }

        @Override // androidx.appcompat.widget.C19948f0, androidx.appcompat.widget.AppCompatSpinner.f
        public final void o(ListAdapter listAdapter) {
            super.o(listAdapter);
            this.f22084E = listAdapter;
        }

        public final void s() {
            int i12;
            PopupWindow popupWindow = this.f22505A;
            Drawable background = popupWindow.getBackground();
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            if (background != null) {
                background.getPadding(appCompatSpinner.f22074i);
                int layoutDirection = appCompatSpinner.getLayoutDirection();
                Rect rect = appCompatSpinner.f22074i;
                i12 = layoutDirection == 1 ? rect.right : -rect.left;
            } else {
                Rect rect2 = appCompatSpinner.f22074i;
                rect2.right = 0;
                rect2.left = 0;
                i12 = 0;
            }
            int paddingLeft = appCompatSpinner.getPaddingLeft();
            int paddingRight = appCompatSpinner.getPaddingRight();
            int width = appCompatSpinner.getWidth();
            int i13 = appCompatSpinner.f22073h;
            if (i13 == -2) {
                int iA2 = appCompatSpinner.a((SpinnerAdapter) this.f22084E, popupWindow.getBackground());
                int i14 = appCompatSpinner.getContext().getResources().getDisplayMetrics().widthPixels;
                Rect rect3 = appCompatSpinner.f22074i;
                int i15 = (i14 - rect3.left) - rect3.right;
                if (iA2 > i15) {
                    iA2 = i15;
                }
                q(Math.max(iA2, (width - paddingLeft) - paddingRight));
            } else if (i13 == -1) {
                q((width - paddingLeft) - paddingRight);
            } else {
                q(i13);
            }
            this.f22511g = appCompatSpinner.getLayoutDirection() == 1 ? (((width - paddingRight) - this.f22510f) - this.f22086G) + i12 : paddingLeft + this.f22086G + i12;
        }
    }

    @j.k0
    public interface f {
        boolean a();

        void dismiss();

        Drawable e();

        void f(int i12, int i13);

        void g(Drawable drawable);

        void h(int i12);

        void i(int i12);

        int j();

        int k();

        void l(int i12);

        CharSequence m();

        void n(CharSequence charSequence);

        void o(ListAdapter listAdapter);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public AppCompatSpinner(@j.N android.content.Context r12, @j.P android.util.AttributeSet r13, int r14) throws java.lang.Throwable {
        /*
            r11 = this;
            r11.<init>(r12, r13, r14)
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r11.f22074i = r0
            android.content.Context r0 = r11.getContext()
            androidx.appcompat.widget.y0.a(r0, r11)
            int[] r0 = l.C43521a.m.f413373v
            androidx.appcompat.widget.D0 r1 = androidx.appcompat.widget.D0.e(r12, r13, r0, r14)
            androidx.appcompat.widget.j r2 = new androidx.appcompat.widget.j
            r2.<init>(r11)
            r11.f22067b = r2
            r2 = 4
            android.content.res.TypedArray r3 = r1.f22121b
            r4 = 0
            int r2 = r3.getResourceId(r2, r4)
            if (r2 == 0) goto L30
            androidx.appcompat.view.d r5 = new androidx.appcompat.view.d
            r5.<init>(r12, r2)
            r11.f22068c = r5
            goto L32
        L30:
            r11.f22068c = r12
        L32:
            r2 = -1
            r5 = 0
            int[] r6 = androidx.appcompat.widget.AppCompatSpinner.f22066j     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            android.content.res.TypedArray r6 = r12.obtainStyledAttributes(r13, r6, r14, r4)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            boolean r7 = r6.hasValue(r4)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L56
            if (r7 == 0) goto L48
            int r2 = r6.getInt(r4, r4)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L56
            goto L48
        L45:
            r12 = move-exception
            r5 = r6
            goto L50
        L48:
            r6.recycle()
            goto L59
        L4c:
            r12 = move-exception
            goto L50
        L4e:
            r6 = r5
            goto L56
        L50:
            if (r5 == 0) goto L55
            r5.recycle()
        L55:
            throw r12
        L56:
            if (r6 == 0) goto L59
            goto L48
        L59:
            r6 = 2
            r7 = 1
            if (r2 == 0) goto L91
            if (r2 == r7) goto L60
            goto L9e
        L60:
            androidx.appcompat.widget.AppCompatSpinner$e r2 = new androidx.appcompat.widget.AppCompatSpinner$e
            android.content.Context r8 = r11.f22068c
            r2.<init>(r8, r13, r14)
            android.content.Context r8 = r11.f22068c
            androidx.appcompat.widget.D0 r0 = androidx.appcompat.widget.D0.e(r8, r13, r0, r14)
            android.content.res.TypedArray r8 = r0.f22121b
            r9 = 3
            r10 = -2
            int r8 = r8.getLayoutDimension(r9, r10)
            r11.f22073h = r8
            android.graphics.drawable.Drawable r8 = r0.b(r7)
            r2.g(r8)
            java.lang.String r6 = r3.getString(r6)
            r2.f22083D = r6
            r0.f()
            r11.f22072g = r2
            androidx.appcompat.widget.J r0 = new androidx.appcompat.widget.J
            r0.<init>(r11, r11, r2)
            r11.f22069d = r0
            goto L9e
        L91:
            androidx.appcompat.widget.AppCompatSpinner$c r0 = new androidx.appcompat.widget.AppCompatSpinner$c
            r0.<init>()
            r11.f22072g = r0
            java.lang.String r2 = r3.getString(r6)
            r0.f22079d = r2
        L9e:
            java.lang.CharSequence[] r0 = r3.getTextArray(r4)
            if (r0 == 0) goto Lb5
            android.widget.ArrayAdapter r2 = new android.widget.ArrayAdapter
            r3 = 17367048(0x1090008, float:2.5162948E-38)
            r2.<init>(r12, r3, r0)
            r12 = 2131562795(0x7f0d112b, float:1.8751029E38)
            r2.setDropDownViewResource(r12)
            r11.setAdapter(r2)
        Lb5:
            r1.f()
            r11.f22071f = r7
            android.widget.SpinnerAdapter r12 = r11.f22070e
            if (r12 == 0) goto Lc3
            r11.setAdapter(r12)
            r11.f22070e = r5
        Lc3:
            androidx.appcompat.widget.j r12 = r11.f22067b
            r12.d(r13, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatSpinner.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public final int a(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int i12 = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int iMax = Math.max(0, getSelectedItemPosition());
        int iMin = Math.min(spinnerAdapter.getCount(), iMax + 15);
        View view = null;
        int iMax2 = 0;
        for (int iMax3 = Math.max(0, iMax - (15 - (iMin - iMax))); iMax3 < iMin; iMax3++) {
            int itemViewType = spinnerAdapter.getItemViewType(iMax3);
            if (itemViewType != i12) {
                view = null;
                i12 = itemViewType;
            }
            view = spinnerAdapter.getView(iMax3, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            iMax2 = Math.max(iMax2, view.getMeasuredWidth());
        }
        if (drawable == null) {
            return iMax2;
        }
        Rect rect = this.f22074i;
        drawable.getPadding(rect);
        return iMax2 + rect.left + rect.right;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C19955j c19955j = this.f22067b;
        if (c19955j != null) {
            c19955j.a();
        }
    }

    @Override // android.widget.Spinner
    public int getDropDownHorizontalOffset() {
        f fVar = this.f22072g;
        return fVar != null ? fVar.k() : super.getDropDownHorizontalOffset();
    }

    @Override // android.widget.Spinner
    public int getDropDownVerticalOffset() {
        f fVar = this.f22072g;
        return fVar != null ? fVar.j() : super.getDropDownVerticalOffset();
    }

    @Override // android.widget.Spinner
    public int getDropDownWidth() {
        return this.f22072g != null ? this.f22073h : super.getDropDownWidth();
    }

    @j.k0
    public final f getInternalPopup() {
        return this.f22072g;
    }

    @Override // android.widget.Spinner
    public Drawable getPopupBackground() {
        f fVar = this.f22072g;
        return fVar != null ? fVar.e() : super.getPopupBackground();
    }

    @Override // android.widget.Spinner
    public Context getPopupContext() {
        return this.f22068c;
    }

    @Override // android.widget.Spinner
    public CharSequence getPrompt() {
        f fVar = this.f22072g;
        return fVar != null ? fVar.m() : super.getPrompt();
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportBackgroundTintList() {
        C19955j c19955j = this.f22067b;
        if (c19955j != null) {
            return c19955j.b();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C19955j c19955j = this.f22067b;
        if (c19955j != null) {
            return c19955j.c();
        }
        return null;
    }

    @Override // android.widget.Spinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        f fVar = this.f22072g;
        if (fVar == null || !fVar.a()) {
            return;
        }
        fVar.dismiss();
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final void onMeasure(int i12, int i13) {
        super.onMeasure(i12, i13);
        if (this.f22072g == null || View.MeasureSpec.getMode(i12) != Integer.MIN_VALUE) {
            return;
        }
        setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), a(getAdapter(), getBackground())), View.MeasureSpec.getSize(i12)), getMeasuredHeight());
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        ViewTreeObserver viewTreeObserver;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (!savedState.f22075b || (viewTreeObserver = getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.addOnGlobalLayoutListener(new a());
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        f fVar = this.f22072g;
        savedState.f22075b = fVar != null && fVar.a();
        return savedState;
    }

    @Override // android.widget.Spinner, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Z z12 = this.f22069d;
        if (z12 == null || !z12.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.Spinner, android.view.View
    public final boolean performClick() {
        f fVar = this.f22072g;
        if (fVar == null) {
            return super.performClick();
        }
        if (fVar.a()) {
            return true;
        }
        this.f22072g.f(getTextDirection(), getTextAlignment());
        return true;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@j.P Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C19955j c19955j = this.f22067b;
        if (c19955j != null) {
            c19955j.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@InterfaceC42165v int i12) {
        super.setBackgroundResource(i12);
        C19955j c19955j = this.f22067b;
        if (c19955j != null) {
            c19955j.f(i12);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownHorizontalOffset(int i12) {
        f fVar = this.f22072g;
        if (fVar == null) {
            super.setDropDownHorizontalOffset(i12);
        } else {
            fVar.i(i12);
            fVar.l(i12);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownVerticalOffset(int i12) {
        f fVar = this.f22072g;
        if (fVar != null) {
            fVar.h(i12);
        } else {
            super.setDropDownVerticalOffset(i12);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownWidth(int i12) {
        if (this.f22072g != null) {
            this.f22073h = i12;
        } else {
            super.setDropDownWidth(i12);
        }
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundDrawable(Drawable drawable) {
        f fVar = this.f22072g;
        if (fVar != null) {
            fVar.g(drawable);
        } else {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundResource(@InterfaceC42165v int i12) {
        setPopupBackgroundDrawable(C43852a.a(getPopupContext(), i12));
    }

    @Override // android.widget.Spinner
    public void setPrompt(CharSequence charSequence) {
        f fVar = this.f22072g;
        if (fVar != null) {
            fVar.n(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintList(@j.P ColorStateList colorStateList) {
        C19955j c19955j = this.f22067b;
        if (c19955j != null) {
            c19955j.h(colorStateList);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintMode(@j.P PorterDuff.Mode mode) {
        C19955j c19955j = this.f22067b;
        if (c19955j != null) {
            c19955j.i(mode);
        }
    }

    @Override // android.widget.AdapterView
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.f22071f) {
            this.f22070e = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        f fVar = this.f22072g;
        if (fVar != null) {
            Context context = this.f22068c;
            if (context == null) {
                context = getContext();
            }
            Resources.Theme theme = context.getTheme();
            d dVar = new d();
            dVar.f22081b = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                dVar.f22082c = (ListAdapter) spinnerAdapter;
            }
            if (theme != null) {
                if (spinnerAdapter instanceof ThemedSpinnerAdapter) {
                    b.a((ThemedSpinnerAdapter) spinnerAdapter, theme);
                } else if (spinnerAdapter instanceof z0) {
                    z0 z0Var = (z0) spinnerAdapter;
                    if (z0Var.getDropDownViewTheme() == null) {
                        z0Var.c();
                    }
                }
            }
            fVar.o(dVar);
        }
    }

    @j.k0
    public class c implements f, DialogInterface.OnClickListener {

        /* renamed from: b, reason: collision with root package name */
        @j.k0
        public androidx.appcompat.app.l f22077b;

        /* renamed from: c, reason: collision with root package name */
        public ListAdapter f22078c;

        /* renamed from: d, reason: collision with root package name */
        public CharSequence f22079d;

        public c() {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final boolean a() {
            androidx.appcompat.app.l lVar = this.f22077b;
            if (lVar != null) {
                return lVar.isShowing();
            }
            return false;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void dismiss() {
            androidx.appcompat.app.l lVar = this.f22077b;
            if (lVar != null) {
                lVar.dismiss();
                this.f22077b = null;
            }
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final Drawable e() {
            return null;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void f(int i12, int i13) {
            if (this.f22078c == null) {
                return;
            }
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            l.a aVar = new l.a(appCompatSpinner.getPopupContext());
            CharSequence charSequence = this.f22079d;
            if (charSequence != null) {
                aVar.setTitle(charSequence);
            }
            androidx.appcompat.app.l lVarCreate = aVar.setSingleChoiceItems(this.f22078c, appCompatSpinner.getSelectedItemPosition(), this).create();
            this.f22077b = lVarCreate;
            AlertController.RecycleListView recycleListViewR = lVarCreate.r();
            recycleListViewR.setTextDirection(i12);
            recycleListViewR.setTextAlignment(i13);
            this.f22077b.show();
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final int j() {
            return 0;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final int k() {
            return 0;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final CharSequence m() {
            return this.f22079d;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void n(CharSequence charSequence) {
            this.f22079d = charSequence;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void o(ListAdapter listAdapter) {
            this.f22078c = listAdapter;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i12) {
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            appCompatSpinner.setSelection(i12);
            if (appCompatSpinner.getOnItemClickListener() != null) {
                appCompatSpinner.performItemClick(null, i12, this.f22078c.getItemId(i12));
            }
            dismiss();
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void g(Drawable drawable) {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void h(int i12) {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void i(int i12) {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.f
        public final void l(int i12) {
        }
    }
}
