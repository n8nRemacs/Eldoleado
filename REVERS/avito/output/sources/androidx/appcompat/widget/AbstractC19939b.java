package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.avito.android.R;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import l.C43521a;

/* compiled from: AbsActionBarView.java */
/* renamed from: androidx.appcompat.widget.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
abstract class AbstractC19939b extends ViewGroup {

    /* renamed from: b, reason: collision with root package name */
    public final a f22478b;

    /* renamed from: c, reason: collision with root package name */
    public final Context f22479c;

    /* renamed from: d, reason: collision with root package name */
    public ActionMenuView f22480d;

    /* renamed from: e, reason: collision with root package name */
    public ActionMenuPresenter f22481e;

    /* renamed from: f, reason: collision with root package name */
    public int f22482f;

    /* renamed from: g, reason: collision with root package name */
    public androidx.core.view.A0 f22483g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f22484h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f22485i;

    /* compiled from: AbsActionBarView.java */
    /* renamed from: androidx.appcompat.widget.b$a */
    public class a implements androidx.core.view.B0 {

        /* renamed from: a, reason: collision with root package name */
        public boolean f22486a = false;

        /* renamed from: b, reason: collision with root package name */
        public int f22487b;

        public a() {
        }

        @Override // androidx.core.view.B0
        public final void a(View view) {
            if (this.f22486a) {
                return;
            }
            AbstractC19939b abstractC19939b = AbstractC19939b.this;
            abstractC19939b.f22483g = null;
            AbstractC19939b.super.setVisibility(this.f22487b);
        }

        @Override // androidx.core.view.B0
        public final void b(View view) {
            AbstractC19939b.super.setVisibility(0);
            this.f22486a = false;
        }

        @Override // androidx.core.view.B0
        public final void c(View view) {
            this.f22486a = true;
        }
    }

    public AbstractC19939b(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public static int c(View view, int i12, int i13) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i12, BeduinInputModel.MIN_TEXT_VERSION), i13);
        return Math.max(0, i12 - view.getMeasuredWidth());
    }

    public static int d(int i12, int i13, int i14, View view, boolean z12) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int iF2 = androidx.appcompat.app.r.f(i14, measuredHeight, 2, i13);
        if (z12) {
            view.layout(i12 - measuredWidth, iF2, i12, measuredHeight + iF2);
        } else {
            view.layout(i12, iF2, i12 + measuredWidth, measuredHeight + iF2);
        }
        return z12 ? -measuredWidth : measuredWidth;
    }

    public int getAnimatedVisibility() {
        return this.f22483g != null ? this.f22478b.f22487b : getVisibility();
    }

    public int getContentHeight() {
        return this.f22482f;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, C43521a.m.f413352a, R.attr.actionBarStyle, 0);
        setContentHeight(typedArrayObtainStyledAttributes.getLayoutDimension(13, 0));
        typedArrayObtainStyledAttributes.recycle();
        ActionMenuPresenter actionMenuPresenter = this.f22481e;
        if (actionMenuPresenter != null) {
            Context context = actionMenuPresenter.f21771c;
            androidx.appcompat.view.a aVar = new androidx.appcompat.view.a();
            aVar.f21653a = context;
            actionMenuPresenter.f22005q = aVar.a();
            androidx.appcompat.view.menu.h hVar = actionMenuPresenter.f21772d;
            if (hVar != null) {
                hVar.p(true);
            }
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.f22485i = false;
        }
        if (!this.f22485i) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.f22485i = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.f22485i = false;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f22484h = false;
        }
        if (!this.f22484h) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.f22484h = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.f22484h = false;
        }
        return true;
    }

    public void setContentHeight(int i12) {
        this.f22482f = i12;
        requestLayout();
    }

    @Override // android.view.View
    public void setVisibility(int i12) {
        if (i12 != getVisibility()) {
            androidx.core.view.A0 a02 = this.f22483g;
            if (a02 != null) {
                a02.b();
            }
            super.setVisibility(i12);
        }
    }

    public AbstractC19939b(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        this.f22478b = new a();
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true) || typedValue.resourceId == 0) {
            this.f22479c = context;
        } else {
            this.f22479c = new ContextThemeWrapper(context, typedValue.resourceId);
        }
    }
}
