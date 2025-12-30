package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.widget.D0;
import com.avito.android.R;
import l.C43521a;

@RestrictTo
/* loaded from: classes.dex */
public class ListMenuItemView extends LinearLayout implements p.a, AbsListView.SelectionBoundsAdjuster {

    /* renamed from: b, reason: collision with root package name */
    public k f21737b;

    /* renamed from: c, reason: collision with root package name */
    public ImageView f21738c;

    /* renamed from: d, reason: collision with root package name */
    public RadioButton f21739d;

    /* renamed from: e, reason: collision with root package name */
    public TextView f21740e;

    /* renamed from: f, reason: collision with root package name */
    public CheckBox f21741f;

    /* renamed from: g, reason: collision with root package name */
    public TextView f21742g;

    /* renamed from: h, reason: collision with root package name */
    public ImageView f21743h;

    /* renamed from: i, reason: collision with root package name */
    public ImageView f21744i;

    /* renamed from: j, reason: collision with root package name */
    public LinearLayout f21745j;

    /* renamed from: k, reason: collision with root package name */
    public final Drawable f21746k;

    /* renamed from: l, reason: collision with root package name */
    public final int f21747l;

    /* renamed from: m, reason: collision with root package name */
    public final Context f21748m;

    /* renamed from: n, reason: collision with root package name */
    public boolean f21749n;

    /* renamed from: o, reason: collision with root package name */
    public final Drawable f21750o;

    /* renamed from: p, reason: collision with root package name */
    public final boolean f21751p;

    /* renamed from: q, reason: collision with root package name */
    public LayoutInflater f21752q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f21753r;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        D0 d0E = D0.e(getContext(), attributeSet, C43521a.m.f413369r, R.attr.listMenuViewStyle);
        this.f21746k = d0E.b(5);
        TypedArray typedArray = d0E.f22121b;
        this.f21747l = typedArray.getResourceId(1, -1);
        this.f21749n = typedArray.getBoolean(7, false);
        this.f21748m = context;
        this.f21750o = d0E.b(8);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, new int[]{android.R.attr.divider}, R.attr.dropDownListViewStyle, 0);
        this.f21751p = typedArrayObtainStyledAttributes.hasValue(0);
        d0E.f();
        typedArrayObtainStyledAttributes.recycle();
    }

    private LayoutInflater getInflater() {
        if (this.f21752q == null) {
            this.f21752q = LayoutInflater.from(getContext());
        }
        return this.f21752q;
    }

    private void setSubMenuArrowVisible(boolean z12) {
        ImageView imageView = this.f21743h;
        if (imageView != null) {
            imageView.setVisibility(z12 ? 0 : 8);
        }
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    public final void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.f21744i;
        if (imageView == null || imageView.getVisibility() != 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.f21744i.getLayoutParams();
        rect.top = this.f21744i.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin + rect.top;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005e  */
    @Override // androidx.appcompat.view.menu.p.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void d(androidx.appcompat.view.menu.k r11) {
        /*
            Method dump skipped, instructions count: 325
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.ListMenuItemView.d(androidx.appcompat.view.menu.k):void");
    }

    @Override // androidx.appcompat.view.menu.p.a
    public k getItemData() {
        return this.f21737b;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setBackground(this.f21746k);
        TextView textView = (TextView) findViewById(R.id.title);
        this.f21740e = textView;
        int i12 = this.f21747l;
        if (i12 != -1) {
            textView.setTextAppearance(this.f21748m, i12);
        }
        this.f21742g = (TextView) findViewById(R.id.shortcut);
        ImageView imageView = (ImageView) findViewById(R.id.submenuarrow);
        this.f21743h = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.f21750o);
        }
        this.f21744i = (ImageView) findViewById(R.id.group_divider);
        this.f21745j = (LinearLayout) findViewById(R.id.content);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i12, int i13) {
        if (this.f21738c != null && this.f21749n) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.f21738c.getLayoutParams();
            int i14 = layoutParams.height;
            if (i14 > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = i14;
            }
        }
        super.onMeasure(i12, i13);
    }

    public void setCheckable(boolean z12) {
        CompoundButton compoundButton;
        View view;
        if (!z12 && this.f21739d == null && this.f21741f == null) {
            return;
        }
        if ((this.f21737b.f21884x & 4) != 0) {
            if (this.f21739d == null) {
                RadioButton radioButton = (RadioButton) getInflater().inflate(R.layout.abc_list_menu_item_radio, (ViewGroup) this, false);
                this.f21739d = radioButton;
                LinearLayout linearLayout = this.f21745j;
                if (linearLayout != null) {
                    linearLayout.addView(radioButton, -1);
                } else {
                    addView(radioButton, -1);
                }
            }
            compoundButton = this.f21739d;
            view = this.f21741f;
        } else {
            if (this.f21741f == null) {
                CheckBox checkBox = (CheckBox) getInflater().inflate(R.layout.abc_list_menu_item_checkbox, (ViewGroup) this, false);
                this.f21741f = checkBox;
                LinearLayout linearLayout2 = this.f21745j;
                if (linearLayout2 != null) {
                    linearLayout2.addView(checkBox, -1);
                } else {
                    addView(checkBox, -1);
                }
            }
            compoundButton = this.f21741f;
            view = this.f21739d;
        }
        if (z12) {
            compoundButton.setChecked(this.f21737b.isChecked());
            if (compoundButton.getVisibility() != 0) {
                compoundButton.setVisibility(0);
            }
            if (view == null || view.getVisibility() == 8) {
                return;
            }
            view.setVisibility(8);
            return;
        }
        CheckBox checkBox2 = this.f21741f;
        if (checkBox2 != null) {
            checkBox2.setVisibility(8);
        }
        RadioButton radioButton2 = this.f21739d;
        if (radioButton2 != null) {
            radioButton2.setVisibility(8);
        }
    }

    public void setChecked(boolean z12) {
        CompoundButton compoundButton;
        if ((this.f21737b.f21884x & 4) != 0) {
            if (this.f21739d == null) {
                RadioButton radioButton = (RadioButton) getInflater().inflate(R.layout.abc_list_menu_item_radio, (ViewGroup) this, false);
                this.f21739d = radioButton;
                LinearLayout linearLayout = this.f21745j;
                if (linearLayout != null) {
                    linearLayout.addView(radioButton, -1);
                } else {
                    addView(radioButton, -1);
                }
            }
            compoundButton = this.f21739d;
        } else {
            if (this.f21741f == null) {
                CheckBox checkBox = (CheckBox) getInflater().inflate(R.layout.abc_list_menu_item_checkbox, (ViewGroup) this, false);
                this.f21741f = checkBox;
                LinearLayout linearLayout2 = this.f21745j;
                if (linearLayout2 != null) {
                    linearLayout2.addView(checkBox, -1);
                } else {
                    addView(checkBox, -1);
                }
            }
            compoundButton = this.f21741f;
        }
        compoundButton.setChecked(z12);
    }

    public void setForceShowIcon(boolean z12) {
        this.f21753r = z12;
        this.f21749n = z12;
    }

    public void setGroupDividerEnabled(boolean z12) {
        ImageView imageView = this.f21744i;
        if (imageView != null) {
            imageView.setVisibility((this.f21751p || !z12) ? 8 : 0);
        }
    }

    public void setIcon(Drawable drawable) {
        this.f21737b.f21874n.getClass();
        boolean z12 = this.f21753r;
        if (z12 || this.f21749n) {
            ImageView imageView = this.f21738c;
            if (imageView == null && drawable == null && !this.f21749n) {
                return;
            }
            if (imageView == null) {
                ImageView imageView2 = (ImageView) getInflater().inflate(R.layout.abc_list_menu_item_icon, (ViewGroup) this, false);
                this.f21738c = imageView2;
                LinearLayout linearLayout = this.f21745j;
                if (linearLayout != null) {
                    linearLayout.addView(imageView2, 0);
                } else {
                    addView(imageView2, 0);
                }
            }
            if (drawable == null && !this.f21749n) {
                this.f21738c.setVisibility(8);
                return;
            }
            ImageView imageView3 = this.f21738c;
            if (!z12) {
                drawable = null;
            }
            imageView3.setImageDrawable(drawable);
            if (this.f21738c.getVisibility() != 0) {
                this.f21738c.setVisibility(0);
            }
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence == null) {
            if (this.f21740e.getVisibility() != 8) {
                this.f21740e.setVisibility(8);
            }
        } else {
            this.f21740e.setText(charSequence);
            if (this.f21740e.getVisibility() != 0) {
                this.f21740e.setVisibility(0);
            }
        }
    }
}
