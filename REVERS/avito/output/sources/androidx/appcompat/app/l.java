package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.C22823h0;
import androidx.core.widget.NestedScrollView;
import j.N;
import j.P;
import j.e0;
import j.f0;

/* compiled from: AlertDialog.java */
/* loaded from: classes.dex */
public class l extends A implements DialogInterface {

    /* renamed from: g, reason: collision with root package name */
    public final AlertController f21553g;

    /* compiled from: AlertDialog.java */
    public static class a {

        /* renamed from: P, reason: collision with root package name */
        private final AlertController.b f21554P;
        private final int mTheme;

        public a(@N Context context) {
            this(context, l.s(0, context));
        }

        @N
        public l create() {
            ListAdapter dVar;
            l lVar = new l(this.f21554P.f21353a, this.mTheme);
            AlertController.b bVar = this.f21554P;
            View view = bVar.f21357e;
            AlertController alertController = lVar.f21553g;
            if (view != null) {
                alertController.f21347x = view;
            } else {
                CharSequence charSequence = bVar.f21356d;
                if (charSequence != null) {
                    alertController.f21327d = charSequence;
                    TextView textView = alertController.f21345v;
                    if (textView != null) {
                        textView.setText(charSequence);
                    }
                }
                Drawable drawable = bVar.f21355c;
                if (drawable != null) {
                    alertController.f21343t = drawable;
                    ImageView imageView = alertController.f21344u;
                    if (imageView != null) {
                        imageView.setVisibility(0);
                        alertController.f21344u.setImageDrawable(drawable);
                    }
                }
            }
            CharSequence charSequence2 = bVar.f21358f;
            if (charSequence2 != null) {
                alertController.f21328e = charSequence2;
                TextView textView2 = alertController.f21346w;
                if (textView2 != null) {
                    textView2.setText(charSequence2);
                }
            }
            CharSequence charSequence3 = bVar.f21359g;
            if (charSequence3 != null) {
                alertController.c(-1, charSequence3, bVar.f21360h);
            }
            CharSequence charSequence4 = bVar.f21361i;
            if (charSequence4 != null) {
                alertController.c(-2, charSequence4, bVar.f21362j);
            }
            CharSequence charSequence5 = bVar.f21363k;
            if (charSequence5 != null) {
                alertController.c(-3, charSequence5, bVar.f21364l);
            }
            if (bVar.f21369q != null || bVar.f21370r != null) {
                AlertController.RecycleListView recycleListView = (AlertController.RecycleListView) bVar.f21354b.inflate(alertController.f21317B, (ViewGroup) null);
                boolean z12 = bVar.f21375w;
                ContextThemeWrapper contextThemeWrapper = bVar.f21353a;
                if (z12) {
                    dVar = new C19934h(bVar, contextThemeWrapper, alertController.f21318C, bVar.f21369q, recycleListView);
                } else {
                    int i12 = bVar.f21376x ? alertController.f21319D : alertController.f21320E;
                    dVar = bVar.f21370r;
                    if (dVar == null) {
                        dVar = new AlertController.d(contextThemeWrapper, i12, R.id.text1, bVar.f21369q);
                    }
                }
                alertController.f21348y = dVar;
                alertController.f21349z = bVar.f21377y;
                if (bVar.f21371s != null) {
                    recycleListView.setOnItemClickListener(new C19936j(bVar, alertController));
                } else if (bVar.f21378z != null) {
                    recycleListView.setOnItemClickListener(new k(bVar, recycleListView, alertController));
                }
                if (bVar.f21376x) {
                    recycleListView.setChoiceMode(1);
                } else if (bVar.f21375w) {
                    recycleListView.setChoiceMode(2);
                }
                alertController.f21329f = recycleListView;
            }
            View view2 = bVar.f21373u;
            if (view2 != null) {
                alertController.f21330g = view2;
                alertController.f21331h = 0;
                alertController.f21332i = false;
            } else {
                int i13 = bVar.f21372t;
                if (i13 != 0) {
                    alertController.f21330g = null;
                    alertController.f21331h = i13;
                    alertController.f21332i = false;
                }
            }
            lVar.setCancelable(this.f21554P.f21365m);
            if (this.f21554P.f21365m) {
                lVar.setCanceledOnTouchOutside(true);
            }
            lVar.setOnCancelListener(this.f21554P.f21366n);
            lVar.setOnDismissListener(this.f21554P.f21367o);
            DialogInterface.OnKeyListener onKeyListener = this.f21554P.f21368p;
            if (onKeyListener != null) {
                lVar.setOnKeyListener(onKeyListener);
            }
            return lVar;
        }

        @N
        public Context getContext() {
            return this.f21554P.f21353a;
        }

        public a setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21370r = listAdapter;
            bVar.f21371s = onClickListener;
            return this;
        }

        public a setCancelable(boolean z12) {
            this.f21554P.f21365m = z12;
            return this;
        }

        public a setCustomTitle(@P View view) {
            this.f21554P.f21357e = view;
            return this;
        }

        public a setIcon(@P Drawable drawable) {
            this.f21554P.f21355c = drawable;
            return this;
        }

        public a setItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21369q = charSequenceArr;
            bVar.f21371s = onClickListener;
            return this;
        }

        public a setMessage(@e0 int i12) {
            AlertController.b bVar = this.f21554P;
            bVar.f21358f = bVar.f21353a.getText(i12);
            return this;
        }

        public a setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21369q = charSequenceArr;
            bVar.f21378z = onMultiChoiceClickListener;
            bVar.f21374v = zArr;
            bVar.f21375w = true;
            return this;
        }

        public a setNegativeButton(@e0 int i12, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21361i = bVar.f21353a.getText(i12);
            this.f21554P.f21362j = onClickListener;
            return this;
        }

        public a setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21363k = charSequence;
            bVar.f21364l = onClickListener;
            return this;
        }

        public a setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.f21554P.f21366n = onCancelListener;
            return this;
        }

        public a setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.f21554P.f21367o = onDismissListener;
            return this;
        }

        public a setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.f21554P.f21368p = onKeyListener;
            return this;
        }

        public a setPositiveButton(@e0 int i12, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21359g = bVar.f21353a.getText(i12);
            this.f21554P.f21360h = onClickListener;
            return this;
        }

        public a setSingleChoiceItems(CharSequence[] charSequenceArr, int i12, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21369q = charSequenceArr;
            bVar.f21371s = onClickListener;
            bVar.f21377y = i12;
            bVar.f21376x = true;
            return this;
        }

        public a setTitle(@e0 int i12) {
            AlertController.b bVar = this.f21554P;
            bVar.f21356d = bVar.f21353a.getText(i12);
            return this;
        }

        public a setView(int i12) {
            AlertController.b bVar = this.f21554P;
            bVar.f21373u = null;
            bVar.f21372t = i12;
            return this;
        }

        public l show() {
            l lVarCreate = create();
            lVarCreate.show();
            return lVarCreate;
        }

        public a(@N Context context, @f0 int i12) {
            this.f21554P = new AlertController.b(new ContextThemeWrapper(context, l.s(i12, context)));
            this.mTheme = i12;
        }

        public a setMessage(@P CharSequence charSequence) {
            this.f21554P.f21358f = charSequence;
            return this;
        }

        public a setTitle(@P CharSequence charSequence) {
            this.f21554P.f21356d = charSequence;
            return this;
        }

        public a setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21361i = charSequence;
            bVar.f21362j = onClickListener;
            return this;
        }

        public a setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21359g = charSequence;
            bVar.f21360h = onClickListener;
            return this;
        }

        public a setView(View view) {
            AlertController.b bVar = this.f21554P;
            bVar.f21373u = view;
            bVar.f21372t = 0;
            return this;
        }

        public a setSingleChoiceItems(ListAdapter listAdapter, int i12, DialogInterface.OnClickListener onClickListener) {
            AlertController.b bVar = this.f21554P;
            bVar.f21370r = listAdapter;
            bVar.f21371s = onClickListener;
            bVar.f21377y = i12;
            bVar.f21376x = true;
            return this;
        }
    }

    public l(@N ContextThemeWrapper contextThemeWrapper, @f0 int i12) {
        super(contextThemeWrapper, s(i12, contextThemeWrapper));
        this.f21553g = new AlertController(getContext(), this, getWindow());
    }

    public static int s(@f0 int i12, @N Context context) {
        if (((i12 >>> 24) & 255) >= 1) {
            return i12;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.avito.android.R.attr.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    @Override // androidx.appcompat.app.A, androidx.view.r, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        int i12;
        ListAdapter listAdapter;
        View viewFindViewById;
        super.onCreate(bundle);
        AlertController alertController = this.f21553g;
        alertController.f21325b.setContentView(alertController.f21316A);
        Window window = alertController.f21326c;
        View viewFindViewById2 = window.findViewById(com.avito.android.R.id.parentPanel);
        View viewFindViewById3 = viewFindViewById2.findViewById(com.avito.android.R.id.topPanel);
        View viewFindViewById4 = viewFindViewById2.findViewById(com.avito.android.R.id.contentPanel);
        View viewFindViewById5 = viewFindViewById2.findViewById(com.avito.android.R.id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById2.findViewById(com.avito.android.R.id.customPanel);
        View viewInflate = alertController.f21330g;
        Context context = alertController.f21324a;
        if (viewInflate == null) {
            viewInflate = alertController.f21331h != 0 ? LayoutInflater.from(context).inflate(alertController.f21331h, viewGroup, false) : null;
        }
        boolean z12 = viewInflate != null;
        if (!z12 || !AlertController.a(viewInflate)) {
            window.setFlags(131072, 131072);
        }
        if (z12) {
            FrameLayout frameLayout = (FrameLayout) window.findViewById(com.avito.android.R.id.custom);
            frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
            if (alertController.f21332i) {
                frameLayout.setPadding(0, 0, 0, 0);
            }
            if (alertController.f21329f != null) {
                ((LinearLayout.LayoutParams) ((LinearLayoutCompat.b) viewGroup.getLayoutParams())).weight = 0.0f;
            }
        } else {
            viewGroup.setVisibility(8);
        }
        View viewFindViewById6 = viewGroup.findViewById(com.avito.android.R.id.topPanel);
        View viewFindViewById7 = viewGroup.findViewById(com.avito.android.R.id.contentPanel);
        View viewFindViewById8 = viewGroup.findViewById(com.avito.android.R.id.buttonPanel);
        ViewGroup viewGroupB = AlertController.b(viewFindViewById6, viewFindViewById3);
        ViewGroup viewGroupB2 = AlertController.b(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupB3 = AlertController.b(viewFindViewById8, viewFindViewById5);
        NestedScrollView nestedScrollView = (NestedScrollView) window.findViewById(com.avito.android.R.id.scrollView);
        alertController.f21342s = nestedScrollView;
        nestedScrollView.setFocusable(false);
        alertController.f21342s.setNestedScrollingEnabled(false);
        TextView textView = (TextView) viewGroupB2.findViewById(R.id.message);
        alertController.f21346w = textView;
        if (textView != null) {
            CharSequence charSequence = alertController.f21328e;
            if (charSequence != null) {
                textView.setText(charSequence);
            } else {
                textView.setVisibility(8);
                alertController.f21342s.removeView(alertController.f21346w);
                if (alertController.f21329f != null) {
                    ViewGroup viewGroup2 = (ViewGroup) alertController.f21342s.getParent();
                    int iIndexOfChild = viewGroup2.indexOfChild(alertController.f21342s);
                    viewGroup2.removeViewAt(iIndexOfChild);
                    viewGroup2.addView(alertController.f21329f, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
                } else {
                    viewGroupB2.setVisibility(8);
                }
            }
        }
        Button button = (Button) viewGroupB3.findViewById(R.id.button1);
        alertController.f21333j = button;
        View.OnClickListener onClickListener = alertController.f21323H;
        button.setOnClickListener(onClickListener);
        if (TextUtils.isEmpty(alertController.f21334k)) {
            alertController.f21333j.setVisibility(8);
            i12 = 0;
        } else {
            alertController.f21333j.setText(alertController.f21334k);
            alertController.f21333j.setVisibility(0);
            i12 = 1;
        }
        Button button2 = (Button) viewGroupB3.findViewById(R.id.button2);
        alertController.f21336m = button2;
        button2.setOnClickListener(onClickListener);
        if (TextUtils.isEmpty(alertController.f21337n)) {
            alertController.f21336m.setVisibility(8);
        } else {
            alertController.f21336m.setText(alertController.f21337n);
            alertController.f21336m.setVisibility(0);
            i12 |= 2;
        }
        Button button3 = (Button) viewGroupB3.findViewById(R.id.button3);
        alertController.f21339p = button3;
        button3.setOnClickListener(onClickListener);
        if (TextUtils.isEmpty(alertController.f21340q)) {
            alertController.f21339p.setVisibility(8);
        } else {
            alertController.f21339p.setText(alertController.f21340q);
            alertController.f21339p.setVisibility(0);
            i12 |= 4;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.avito.android.R.attr.alertDialogCenterButtons, typedValue, true);
        if (typedValue.data != 0) {
            if (i12 == 1) {
                Button button4 = alertController.f21333j;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button4.getLayoutParams();
                layoutParams.gravity = 1;
                layoutParams.weight = 0.5f;
                button4.setLayoutParams(layoutParams);
            } else if (i12 == 2) {
                Button button5 = alertController.f21336m;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button5.getLayoutParams();
                layoutParams2.gravity = 1;
                layoutParams2.weight = 0.5f;
                button5.setLayoutParams(layoutParams2);
            } else if (i12 == 4) {
                Button button6 = alertController.f21339p;
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) button6.getLayoutParams();
                layoutParams3.gravity = 1;
                layoutParams3.weight = 0.5f;
                button6.setLayoutParams(layoutParams3);
            }
        }
        if (i12 == 0) {
            viewGroupB3.setVisibility(8);
        }
        if (alertController.f21347x != null) {
            viewGroupB.addView(alertController.f21347x, 0, new ViewGroup.LayoutParams(-1, -2));
            window.findViewById(com.avito.android.R.id.title_template).setVisibility(8);
        } else {
            alertController.f21344u = (ImageView) window.findViewById(R.id.icon);
            if (TextUtils.isEmpty(alertController.f21327d) || !alertController.f21321F) {
                window.findViewById(com.avito.android.R.id.title_template).setVisibility(8);
                alertController.f21344u.setVisibility(8);
                viewGroupB.setVisibility(8);
            } else {
                TextView textView2 = (TextView) window.findViewById(com.avito.android.R.id.alertTitle);
                alertController.f21345v = textView2;
                textView2.setText(alertController.f21327d);
                Drawable drawable = alertController.f21343t;
                if (drawable != null) {
                    alertController.f21344u.setImageDrawable(drawable);
                } else {
                    alertController.f21345v.setPadding(alertController.f21344u.getPaddingLeft(), alertController.f21344u.getPaddingTop(), alertController.f21344u.getPaddingRight(), alertController.f21344u.getPaddingBottom());
                    alertController.f21344u.setVisibility(8);
                }
            }
        }
        boolean z13 = viewGroup.getVisibility() != 8;
        int i13 = (viewGroupB == null || viewGroupB.getVisibility() == 8) ? 0 : 1;
        boolean z14 = viewGroupB3.getVisibility() != 8;
        if (!z14 && (viewFindViewById = viewGroupB2.findViewById(com.avito.android.R.id.textSpacerNoButtons)) != null) {
            viewFindViewById.setVisibility(0);
        }
        if (i13 != 0) {
            NestedScrollView nestedScrollView2 = alertController.f21342s;
            if (nestedScrollView2 != null) {
                nestedScrollView2.setClipToPadding(true);
            }
            View viewFindViewById9 = (alertController.f21328e == null && alertController.f21329f == null) ? null : viewGroupB.findViewById(com.avito.android.R.id.titleDividerNoCustom);
            if (viewFindViewById9 != null) {
                viewFindViewById9.setVisibility(0);
            }
        } else {
            View viewFindViewById10 = viewGroupB2.findViewById(com.avito.android.R.id.textSpacerNoTitle);
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0);
            }
        }
        AlertController.RecycleListView recycleListView = alertController.f21329f;
        if (recycleListView != null) {
            recycleListView.getClass();
            if (!z14 || i13 == 0) {
                recycleListView.setPadding(recycleListView.getPaddingLeft(), i13 != 0 ? recycleListView.getPaddingTop() : recycleListView.f21350b, recycleListView.getPaddingRight(), z14 ? recycleListView.getPaddingBottom() : recycleListView.f21351c);
            }
        }
        if (!z13) {
            ViewGroup viewGroup3 = alertController.f21329f;
            if (viewGroup3 == null) {
                viewGroup3 = alertController.f21342s;
            }
            if (viewGroup3 != null) {
                int i14 = z14 ? 2 : 0;
                View viewFindViewById11 = window.findViewById(com.avito.android.R.id.scrollIndicatorUp);
                View viewFindViewById12 = window.findViewById(com.avito.android.R.id.scrollIndicatorDown);
                C22823h0.N(viewGroup3, i13 | i14);
                if (viewFindViewById11 != null) {
                    viewGroupB2.removeView(viewFindViewById11);
                }
                if (viewFindViewById12 != null) {
                    viewGroupB2.removeView(viewFindViewById12);
                }
            }
        }
        AlertController.RecycleListView recycleListView2 = alertController.f21329f;
        if (recycleListView2 == null || (listAdapter = alertController.f21348y) == null) {
            return;
        }
        recycleListView2.setAdapter(listAdapter);
        int i15 = alertController.f21349z;
        if (i15 > -1) {
            recycleListView2.setItemChecked(i15, true);
            recycleListView2.setSelection(i15);
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i12, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f21553g.f21342s;
        if (nestedScrollView == null || !nestedScrollView.c(keyEvent)) {
            return super.onKeyDown(i12, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i12, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f21553g.f21342s;
        if (nestedScrollView == null || !nestedScrollView.c(keyEvent)) {
            return super.onKeyUp(i12, keyEvent);
        }
        return true;
    }

    public final AlertController.RecycleListView r() {
        return this.f21553g.f21329f;
    }

    @Override // androidx.appcompat.app.A, android.app.Dialog
    public final void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        AlertController alertController = this.f21553g;
        alertController.f21327d = charSequence;
        TextView textView = alertController.f21345v;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public final void t(View view) {
        AlertController alertController = this.f21553g;
        alertController.f21330g = view;
        alertController.f21331h = 0;
        alertController.f21332i = false;
    }
}
