package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AbstractC19939b;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.core.view.C22823h0;
import com.avito.android.R;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import l.C43521a;

@RestrictTo
/* loaded from: classes.dex */
public class ActionBarContextView extends AbstractC19939b {

    /* renamed from: j, reason: collision with root package name */
    public CharSequence f21951j;

    /* renamed from: k, reason: collision with root package name */
    public CharSequence f21952k;

    /* renamed from: l, reason: collision with root package name */
    public View f21953l;

    /* renamed from: m, reason: collision with root package name */
    public View f21954m;

    /* renamed from: n, reason: collision with root package name */
    public View f21955n;

    /* renamed from: o, reason: collision with root package name */
    public LinearLayout f21956o;

    /* renamed from: p, reason: collision with root package name */
    public TextView f21957p;

    /* renamed from: q, reason: collision with root package name */
    public TextView f21958q;

    /* renamed from: r, reason: collision with root package name */
    public final int f21959r;

    /* renamed from: s, reason: collision with root package name */
    public final int f21960s;

    /* renamed from: t, reason: collision with root package name */
    public boolean f21961t;

    /* renamed from: u, reason: collision with root package name */
    public final int f21962u;

    public class a implements View.OnClickListener {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ androidx.appcompat.view.b f21963b;

        public a(androidx.appcompat.view.b bVar) {
            this.f21963b = bVar;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f21963b.c();
        }
    }

    public ActionBarContextView(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.actionModeStyle);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C43521a.m.f413355d, R.attr.actionModeStyle, 0);
        D0 d02 = new D0(typedArrayObtainStyledAttributes, context);
        setBackground(d02.b(0));
        this.f21959r = typedArrayObtainStyledAttributes.getResourceId(5, 0);
        this.f21960s = typedArrayObtainStyledAttributes.getResourceId(4, 0);
        this.f22482f = typedArrayObtainStyledAttributes.getLayoutDimension(3, 0);
        this.f21962u = typedArrayObtainStyledAttributes.getResourceId(2, R.layout.abc_action_mode_close_item_material);
        d02.f();
    }

    public final void e(androidx.appcompat.view.b bVar) {
        View view = this.f21953l;
        if (view == null) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(this.f21962u, (ViewGroup) this, false);
            this.f21953l = viewInflate;
            addView(viewInflate);
        } else if (view.getParent() == null) {
            addView(this.f21953l);
        }
        View viewFindViewById = this.f21953l.findViewById(R.id.action_mode_close_button);
        this.f21954m = viewFindViewById;
        viewFindViewById.setOnClickListener(new a(bVar));
        androidx.appcompat.view.menu.h hVarE = bVar.e();
        ActionMenuPresenter actionMenuPresenter = this.f22481e;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.m();
            ActionMenuPresenter.a aVar = actionMenuPresenter.f22009u;
            if (aVar != null && aVar.b()) {
                aVar.f21907i.dismiss();
            }
        }
        ActionMenuPresenter actionMenuPresenter2 = new ActionMenuPresenter(getContext());
        this.f22481e = actionMenuPresenter2;
        actionMenuPresenter2.f22001m = true;
        actionMenuPresenter2.f22002n = true;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        hVarE.b(this.f22481e, this.f22479c);
        ActionMenuPresenter actionMenuPresenter3 = this.f22481e;
        androidx.appcompat.view.menu.p pVar = actionMenuPresenter3.f21777i;
        if (pVar == null) {
            androidx.appcompat.view.menu.p pVar2 = (androidx.appcompat.view.menu.p) actionMenuPresenter3.f21773e.inflate(actionMenuPresenter3.f21775g, (ViewGroup) this, false);
            actionMenuPresenter3.f21777i = pVar2;
            pVar2.b(actionMenuPresenter3.f21772d);
            actionMenuPresenter3.c(true);
        }
        androidx.appcompat.view.menu.p pVar3 = actionMenuPresenter3.f21777i;
        if (pVar != pVar3) {
            ((ActionMenuView) pVar3).setPresenter(actionMenuPresenter3);
        }
        ActionMenuView actionMenuView = (ActionMenuView) pVar3;
        this.f22480d = actionMenuView;
        actionMenuView.setBackground(null);
        addView(this.f22480d, layoutParams);
    }

    public final void f() {
        if (this.f21956o == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.abc_action_bar_title_item, this);
            LinearLayout linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.f21956o = linearLayout;
            this.f21957p = (TextView) linearLayout.findViewById(R.id.action_bar_title);
            this.f21958q = (TextView) this.f21956o.findViewById(R.id.action_bar_subtitle);
            int i12 = this.f21959r;
            if (i12 != 0) {
                this.f21957p.setTextAppearance(getContext(), i12);
            }
            int i13 = this.f21960s;
            if (i13 != 0) {
                this.f21958q.setTextAppearance(getContext(), i13);
            }
        }
        this.f21957p.setText(this.f21951j);
        this.f21958q.setText(this.f21952k);
        boolean zIsEmpty = TextUtils.isEmpty(this.f21951j);
        boolean zIsEmpty2 = TextUtils.isEmpty(this.f21952k);
        this.f21958q.setVisibility(!zIsEmpty2 ? 0 : 8);
        this.f21956o.setVisibility((zIsEmpty && zIsEmpty2) ? 8 : 0);
        if (this.f21956o.getParent() == null) {
            addView(this.f21956o);
        }
    }

    public final void g() {
        removeAllViews();
        this.f21955n = null;
        this.f22480d = null;
        this.f22481e = null;
        View view = this.f21954m;
        if (view != null) {
            view.setOnClickListener(null);
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // androidx.appcompat.widget.AbstractC19939b
    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    @Override // androidx.appcompat.widget.AbstractC19939b
    public /* bridge */ /* synthetic */ int getContentHeight() {
        return super.getContentHeight();
    }

    public CharSequence getSubtitle() {
        return this.f21952k;
    }

    public CharSequence getTitle() {
        return this.f21951j;
    }

    public final androidx.core.view.A0 h(int i12, long j12) {
        androidx.core.view.A0 a02 = this.f22483g;
        if (a02 != null) {
            a02.b();
        }
        AbstractC19939b.a aVar = this.f22478b;
        if (i12 != 0) {
            androidx.core.view.A0 a0A = C22823h0.a(this);
            a0A.a(0.0f);
            a0A.c(j12);
            AbstractC19939b.this.f22483g = a0A;
            aVar.f22487b = i12;
            a0A.e(aVar);
            return a0A;
        }
        if (getVisibility() != 0) {
            setAlpha(0.0f);
        }
        androidx.core.view.A0 a0A2 = C22823h0.a(this);
        a0A2.a(1.0f);
        a0A2.c(j12);
        AbstractC19939b.this.f22483g = a0A2;
        aVar.f22487b = i12;
        a0A2.e(aVar);
        return a0A2;
    }

    public final boolean i() {
        ActionMenuPresenter actionMenuPresenter = this.f22481e;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.p();
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.f22481e;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.m();
            ActionMenuPresenter.a aVar = this.f22481e.f22009u;
            if (aVar == null || !aVar.b()) {
                return;
            }
            aVar.f21907i.dismiss();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z12, int i12, int i13, int i14, int i15) {
        boolean z13 = getLayoutDirection() == 1;
        int paddingRight = z13 ? (i14 - i12) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i15 - i13) - getPaddingTop()) - getPaddingBottom();
        View view = this.f21953l;
        if (view != null && view.getVisibility() != 8) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.f21953l.getLayoutParams();
            int i16 = z13 ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i17 = z13 ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int i18 = z13 ? paddingRight - i16 : paddingRight + i16;
            int iD2 = i18 + AbstractC19939b.d(i18, paddingTop, paddingTop2, this.f21953l, z13);
            paddingRight = z13 ? iD2 - i17 : iD2 + i17;
        }
        LinearLayout linearLayout = this.f21956o;
        if (linearLayout != null && this.f21955n == null && linearLayout.getVisibility() != 8) {
            paddingRight += AbstractC19939b.d(paddingRight, paddingTop, paddingTop2, this.f21956o, z13);
        }
        View view2 = this.f21955n;
        if (view2 != null) {
            AbstractC19939b.d(paddingRight, paddingTop, paddingTop2, view2, z13);
        }
        int paddingLeft = z13 ? getPaddingLeft() : (i14 - i12) - getPaddingRight();
        ActionMenuView actionMenuView = this.f22480d;
        if (actionMenuView != null) {
            AbstractC19939b.d(paddingLeft, paddingTop, paddingTop2, actionMenuView, !z13);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i12, int i13) {
        if (View.MeasureSpec.getMode(i12) != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_width=\"match_parent\" (or fill_parent)"));
        }
        if (View.MeasureSpec.getMode(i13) == 0) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_height=\"wrap_content\""));
        }
        int size = View.MeasureSpec.getSize(i12);
        int size2 = this.f22482f;
        if (size2 <= 0) {
            size2 = View.MeasureSpec.getSize(i13);
        }
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int iMin = size2 - paddingBottom;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, BeduinInputModel.MIN_TEXT_VERSION);
        View view = this.f21953l;
        if (view != null) {
            int iC2 = AbstractC19939b.c(view, paddingLeft, iMakeMeasureSpec);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.f21953l.getLayoutParams();
            paddingLeft = iC2 - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
        }
        ActionMenuView actionMenuView = this.f22480d;
        if (actionMenuView != null && actionMenuView.getParent() == this) {
            paddingLeft = AbstractC19939b.c(this.f22480d, paddingLeft, iMakeMeasureSpec);
        }
        LinearLayout linearLayout = this.f21956o;
        if (linearLayout != null && this.f21955n == null) {
            if (this.f21961t) {
                this.f21956o.measure(View.MeasureSpec.makeMeasureSpec(0, 0), iMakeMeasureSpec);
                int measuredWidth = this.f21956o.getMeasuredWidth();
                boolean z12 = measuredWidth <= paddingLeft;
                if (z12) {
                    paddingLeft -= measuredWidth;
                }
                this.f21956o.setVisibility(z12 ? 0 : 8);
            } else {
                paddingLeft = AbstractC19939b.c(linearLayout, paddingLeft, iMakeMeasureSpec);
            }
        }
        View view2 = this.f21955n;
        if (view2 != null) {
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            int i14 = layoutParams.width;
            int i15 = i14 != -2 ? 1073741824 : Integer.MIN_VALUE;
            if (i14 >= 0) {
                paddingLeft = Math.min(i14, paddingLeft);
            }
            int i16 = layoutParams.height;
            int i17 = i16 == -2 ? Integer.MIN_VALUE : 1073741824;
            if (i16 >= 0) {
                iMin = Math.min(i16, iMin);
            }
            this.f21955n.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i15), View.MeasureSpec.makeMeasureSpec(iMin, i17));
        }
        if (this.f22482f > 0) {
            setMeasuredDimension(size, size2);
            return;
        }
        int childCount = getChildCount();
        int i18 = 0;
        for (int i19 = 0; i19 < childCount; i19++) {
            int measuredHeight = getChildAt(i19).getMeasuredHeight() + paddingBottom;
            if (measuredHeight > i18) {
                i18 = measuredHeight;
            }
        }
        setMeasuredDimension(size, i18);
    }

    @Override // androidx.appcompat.widget.AbstractC19939b
    public void setContentHeight(int i12) {
        this.f22482f = i12;
    }

    public void setCustomView(View view) {
        LinearLayout linearLayout;
        View view2 = this.f21955n;
        if (view2 != null) {
            removeView(view2);
        }
        this.f21955n = view;
        if (view != null && (linearLayout = this.f21956o) != null) {
            removeView(linearLayout);
            this.f21956o = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.f21952k = charSequence;
        f();
    }

    public void setTitle(CharSequence charSequence) {
        this.f21951j = charSequence;
        f();
        C22823h0.E(this, charSequence);
    }

    public void setTitleOptional(boolean z12) {
        if (z12 != this.f21961t) {
            requestLayout();
        }
        this.f21961t = z12;
    }

    @Override // androidx.appcompat.widget.AbstractC19939b, android.view.View
    public /* bridge */ /* synthetic */ void setVisibility(int i12) {
        super.setVisibility(i12);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }
}
