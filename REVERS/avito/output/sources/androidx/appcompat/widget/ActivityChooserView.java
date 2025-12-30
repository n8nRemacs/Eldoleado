package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.C19943d;
import androidx.core.view.AbstractC22810b;
import java.lang.reflect.InvocationTargetException;

@RestrictTo
/* loaded from: classes.dex */
public class ActivityChooserView extends ViewGroup implements C19943d.a {

    /* renamed from: b, reason: collision with root package name */
    public AbstractC22810b f22041b;

    /* renamed from: c, reason: collision with root package name */
    public final DataSetObserver f22042c;

    /* renamed from: d, reason: collision with root package name */
    public final ViewTreeObserver.OnGlobalLayoutListener f22043d;

    /* renamed from: e, reason: collision with root package name */
    public C19948f0 f22044e;

    /* renamed from: f, reason: collision with root package name */
    public PopupWindow.OnDismissListener f22045f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f22046g;

    /* renamed from: h, reason: collision with root package name */
    public int f22047h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f22048i;

    @RestrictTo
    public static class InnerLayout extends LinearLayout {

        /* renamed from: b, reason: collision with root package name */
        public static final int[] f22049b = {R.attr.background};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            D0 d02 = new D0(context.obtainStyledAttributes(attributeSet, f22049b), context);
            setBackgroundDrawable(d02.b(0));
            d02.f();
        }
    }

    public class a extends DataSetObserver {
        public a() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() {
            super.onChanged();
            ActivityChooserView.this.getClass();
            throw null;
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            super.onInvalidated();
            ActivityChooserView.this.getClass();
            throw null;
        }
    }

    public class b implements ViewTreeObserver.OnGlobalLayoutListener {
        public b() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            AbstractC22810b.a aVar;
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (activityChooserView.b()) {
                if (!activityChooserView.isShown()) {
                    activityChooserView.getListPopupWindow().dismiss();
                    return;
                }
                activityChooserView.getListPopupWindow().show();
                AbstractC22810b abstractC22810b = activityChooserView.f22041b;
                if (abstractC22810b == null || (aVar = abstractC22810b.f45026a) == null) {
                    return;
                }
                ((ActionMenuPresenter) aVar).o(true);
            }
        }
    }

    public class c extends BaseAdapter {

        /* renamed from: b, reason: collision with root package name */
        public C19943d f22052b;

        /* renamed from: c, reason: collision with root package name */
        public int f22053c;

        /* renamed from: d, reason: collision with root package name */
        public boolean f22054d;

        /* renamed from: e, reason: collision with root package name */
        public boolean f22055e;

        /* renamed from: f, reason: collision with root package name */
        public boolean f22056f;

        @Override // android.widget.Adapter
        public final int getCount() {
            int size;
            C19943d c19943d = this.f22052b;
            synchronized (c19943d.f22492a) {
                c19943d.a();
                size = c19943d.f22493b.size();
            }
            if (!this.f22054d) {
                this.f22052b.c();
            }
            int iMin = Math.min(size, this.f22053c);
            return this.f22056f ? iMin + 1 : iMin;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i12) {
            int itemViewType = getItemViewType(i12);
            if (itemViewType != 0) {
                if (itemViewType == 1) {
                    return null;
                }
                throw new IllegalArgumentException();
            }
            if (!this.f22054d) {
                this.f22052b.c();
            }
            C19943d c19943d = this.f22052b;
            synchronized (c19943d.f22492a) {
                c19943d.a();
                throw null;
            }
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i12) {
            return i12;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getItemViewType(int i12) {
            return (this.f22056f && i12 == getCount() - 1) ? 1 : 0;
        }

        @Override // android.widget.Adapter
        public final View getView(int i12, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i12);
            if (itemViewType == 0) {
                if (view == null || view.getId() != com.avito.android.R.id.list_item) {
                    throw null;
                }
                throw null;
            }
            if (itemViewType != 1) {
                throw new IllegalArgumentException();
            }
            if (view == null || view.getId() != 1) {
                throw null;
            }
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getViewTypeCount() {
            return 3;
        }
    }

    public class d implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            throw null;
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public final void onDismiss() {
            throw null;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public final void onItemClick(AdapterView<?> adapterView, View view, int i12, long j12) {
            int itemViewType = ((c) adapterView.getAdapter()).getItemViewType(i12);
            if (itemViewType == 0) {
                throw null;
            }
            if (itemViewType != 1) {
                throw new IllegalArgumentException();
            }
            throw null;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) {
            throw null;
        }
    }

    public ActivityChooserView() {
        super(null, null, 0);
        this.f22042c = new a();
        this.f22043d = new b();
        this.f22047h = 4;
        throw null;
    }

    public final void a() {
        if (b()) {
            getListPopupWindow().dismiss();
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this.f22043d);
            }
        }
    }

    public final boolean b() {
        return getListPopupWindow().f22505A.isShowing();
    }

    public final void c(int i12) {
        throw null;
    }

    @RestrictTo
    public C19943d getDataModel() {
        throw null;
    }

    public C19948f0 getListPopupWindow() {
        if (this.f22044e == null) {
            C19948f0 c19948f0 = new C19948f0(getContext());
            this.f22044e = c19948f0;
            c19948f0.o(null);
            C19948f0 c19948f02 = this.f22044e;
            c19948f02.f22520p = this;
            c19948f02.r();
            C19948f0 c19948f03 = this.f22044e;
            c19948f03.f22521q = null;
            c19948f03.f22505A.setOnDismissListener(null);
        }
        return this.f22044e;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z12, int i12, int i13, int i14, int i15) {
        throw null;
    }

    @Override // android.view.View
    public final void onMeasure(int i12, int i13) {
        throw null;
    }

    @RestrictTo
    public void setActivityChooserModel(C19943d c19943d) {
        throw null;
    }

    public void setExpandActivityOverflowButtonContentDescription(int i12) {
        getContext().getString(i12);
        throw null;
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        throw null;
    }

    public void setInitialActivityCount(int i12) {
        this.f22047h = i12;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.f22045f = onDismissListener;
    }

    @RestrictTo
    public void setProvider(AbstractC22810b abstractC22810b) {
        this.f22041b = abstractC22810b;
    }

    public void setDefaultActionButtonContentDescription(int i12) {
    }
}
