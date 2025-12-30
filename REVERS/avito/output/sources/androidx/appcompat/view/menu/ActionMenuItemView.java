package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.K0;
import androidx.appcompat.widget.Z;
import java.util.concurrent.ExecutionException;
import l.C43521a;

@RestrictTo
/* loaded from: classes.dex */
public class ActionMenuItemView extends AppCompatTextView implements p.a, View.OnClickListener, ActionMenuView.a {

    /* renamed from: b, reason: collision with root package name */
    public k f21723b;

    /* renamed from: c, reason: collision with root package name */
    public CharSequence f21724c;

    /* renamed from: d, reason: collision with root package name */
    public Drawable f21725d;

    /* renamed from: e, reason: collision with root package name */
    public h.b f21726e;

    /* renamed from: f, reason: collision with root package name */
    public Z f21727f;

    /* renamed from: g, reason: collision with root package name */
    public b f21728g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f21729h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f21730i;

    /* renamed from: j, reason: collision with root package name */
    public final int f21731j;

    /* renamed from: k, reason: collision with root package name */
    public int f21732k;

    /* renamed from: l, reason: collision with root package name */
    public final int f21733l;

    public class a extends Z {
        public a() {
            super(ActionMenuItemView.this);
        }

        @Override // androidx.appcompat.widget.Z
        public final r c() {
            b bVar = ActionMenuItemView.this.f21728g;
            if (bVar != null) {
                return bVar.a();
            }
            return null;
        }

        @Override // androidx.appcompat.widget.Z
        public final boolean d() {
            r rVarC;
            ActionMenuItemView actionMenuItemView = ActionMenuItemView.this;
            h.b bVar = actionMenuItemView.f21726e;
            return bVar != null && bVar.e(actionMenuItemView.f21723b) && (rVarC = c()) != null && rVarC.a();
        }
    }

    public static abstract class b {
        public abstract r a();
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        Resources resources = context.getResources();
        this.f21729h = b();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C43521a.m.f413354c, 0, 0);
        this.f21731j = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.f21733l = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        this.f21732k = -1;
        setSaveEnabled(false);
    }

    @Override // androidx.appcompat.widget.ActionMenuView.a
    public final boolean a() {
        return !TextUtils.isEmpty(getText()) && this.f21723b.getIcon() == null;
    }

    public final boolean b() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i12 = configuration.screenWidthDp;
        return i12 >= 480 || (i12 >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    @Override // androidx.appcompat.view.menu.p.a
    public final void d(k kVar) {
        this.f21723b = kVar;
        setIcon(kVar.getIcon());
        setTitle(kVar.getTitleCondensed());
        setId(kVar.f21861a);
        setVisibility(kVar.isVisible() ? 0 : 8);
        setEnabled(kVar.isEnabled());
        if (kVar.hasSubMenu() && this.f21727f == null) {
            this.f21727f = new a();
        }
    }

    public final void f() {
        boolean z12 = true;
        boolean z13 = !TextUtils.isEmpty(this.f21724c);
        if (this.f21725d != null && ((this.f21723b.f21885y & 4) != 4 || (!this.f21729h && !this.f21730i))) {
            z12 = false;
        }
        boolean z14 = z13 & z12;
        setText(z14 ? this.f21724c : null);
        CharSequence charSequence = this.f21723b.f21877q;
        if (TextUtils.isEmpty(charSequence)) {
            setContentDescription(z14 ? null : this.f21723b.f21865e);
        } else {
            setContentDescription(charSequence);
        }
        CharSequence charSequence2 = this.f21723b.f21878r;
        if (TextUtils.isEmpty(charSequence2)) {
            K0.a(this, z14 ? null : this.f21723b.f21865e);
        } else {
            K0.a(this, charSequence2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    @Override // androidx.appcompat.view.menu.p.a
    public k getItemData() {
        return this.f21723b;
    }

    @Override // androidx.appcompat.widget.ActionMenuView.a
    public final boolean i() {
        return !TextUtils.isEmpty(getText());
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        h.b bVar = this.f21726e;
        if (bVar != null) {
            bVar.e(this.f21723b);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.f21729h = b();
        f();
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public final void onMeasure(int i12, int i13) throws ExecutionException, InterruptedException {
        int i14;
        boolean zIsEmpty = TextUtils.isEmpty(getText());
        if (!zIsEmpty && (i14 = this.f21732k) >= 0) {
            super.setPadding(i14, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i12, i13);
        int mode = View.MeasureSpec.getMode(i12);
        int size = View.MeasureSpec.getSize(i12);
        int measuredWidth = getMeasuredWidth();
        int i15 = this.f21731j;
        int iMin = mode == Integer.MIN_VALUE ? Math.min(size, i15) : i15;
        if (mode != 1073741824 && i15 > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i13);
        }
        if (!zIsEmpty || this.f21725d == null) {
            return;
        }
        super.setPadding((getMeasuredWidth() - this.f21725d.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Z z12;
        if (this.f21723b.hasSubMenu() && (z12 = this.f21727f) != null && z12.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setExpandedFormat(boolean z12) {
        if (this.f21730i != z12) {
            this.f21730i = z12;
            k kVar = this.f21723b;
            if (kVar != null) {
                h hVar = kVar.f21874n;
                hVar.f21841k = true;
                hVar.p(true);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.f21725d = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i12 = this.f21733l;
            if (intrinsicWidth > i12) {
                intrinsicHeight = (int) (intrinsicHeight * (i12 / intrinsicWidth));
                intrinsicWidth = i12;
            }
            if (intrinsicHeight > i12) {
                intrinsicWidth = (int) (intrinsicWidth * (i12 / intrinsicHeight));
            } else {
                i12 = intrinsicHeight;
            }
            drawable.setBounds(0, 0, intrinsicWidth, i12);
        }
        setCompoundDrawables(drawable, null, null, null);
        f();
    }

    public void setItemInvoker(h.b bVar) {
        this.f21726e = bVar;
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPadding(int i12, int i13, int i14, int i15) {
        this.f21732k = i12;
        super.setPadding(i12, i13, i14, i15);
    }

    public void setPopupCallback(b bVar) {
        this.f21728g = bVar;
    }

    public void setTitle(CharSequence charSequence) {
        this.f21724c = charSequence;
        f();
    }

    public void setCheckable(boolean z12) {
    }

    public void setChecked(boolean z12) {
    }
}
