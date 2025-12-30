package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.RestrictTo;
import java.lang.ref.WeakReference;
import l.C43521a;

@RestrictTo
/* loaded from: classes.dex */
public final class ViewStubCompat extends View {

    /* renamed from: b, reason: collision with root package name */
    public int f22433b;

    /* renamed from: c, reason: collision with root package name */
    public int f22434c;

    /* renamed from: d, reason: collision with root package name */
    public WeakReference<View> f22435d;

    /* renamed from: e, reason: collision with root package name */
    public LayoutInflater f22436e;

    /* renamed from: f, reason: collision with root package name */
    public a f22437f;

    public interface a {
        void a();
    }

    public ViewStubCompat(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f22433b = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C43521a.m.f413351B, 0, 0);
        this.f22434c = typedArrayObtainStyledAttributes.getResourceId(2, -1);
        this.f22433b = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        setId(typedArrayObtainStyledAttributes.getResourceId(0, -1));
        typedArrayObtainStyledAttributes.recycle();
        setVisibility(8);
        setWillNotDraw(true);
    }

    public final View a() {
        ViewParent parent = getParent();
        if (!(parent instanceof ViewGroup)) {
            throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
        }
        if (this.f22433b == 0) {
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        LayoutInflater layoutInflaterFrom = this.f22436e;
        if (layoutInflaterFrom == null) {
            layoutInflaterFrom = LayoutInflater.from(getContext());
        }
        View viewInflate = layoutInflaterFrom.inflate(this.f22433b, viewGroup, false);
        int i12 = this.f22434c;
        if (i12 != -1) {
            viewInflate.setId(i12);
        }
        int iIndexOfChild = viewGroup.indexOfChild(this);
        viewGroup.removeViewInLayout(this);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            viewGroup.addView(viewInflate, iIndexOfChild, layoutParams);
        } else {
            viewGroup.addView(viewInflate, iIndexOfChild);
        }
        this.f22435d = new WeakReference<>(viewInflate);
        a aVar = this.f22437f;
        if (aVar != null) {
            aVar.a();
        }
        return viewInflate;
    }

    public int getInflatedId() {
        return this.f22434c;
    }

    public LayoutInflater getLayoutInflater() {
        return this.f22436e;
    }

    public int getLayoutResource() {
        return this.f22433b;
    }

    @Override // android.view.View
    public final void onMeasure(int i12, int i13) {
        setMeasuredDimension(0, 0);
    }

    public void setInflatedId(int i12) {
        this.f22434c = i12;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.f22436e = layoutInflater;
    }

    public void setLayoutResource(int i12) {
        this.f22433b = i12;
    }

    public void setOnInflateListener(a aVar) {
        this.f22437f = aVar;
    }

    @Override // android.view.View
    public void setVisibility(int i12) {
        WeakReference<View> weakReference = this.f22435d;
        if (weakReference != null) {
            View view = weakReference.get();
            if (view == null) {
                throw new IllegalStateException("setVisibility called on un-referenced view");
            }
            view.setVisibility(i12);
            return;
        }
        super.setVisibility(i12);
        if (i12 == 0 || i12 == 4) {
            a();
        }
    }

    @Override // android.view.View
    public final void dispatchDraw(Canvas canvas) {
    }

    @Override // android.view.View
    @SuppressLint({"MissingSuperCall"})
    public final void draw(@j.N Canvas canvas) {
    }
}
