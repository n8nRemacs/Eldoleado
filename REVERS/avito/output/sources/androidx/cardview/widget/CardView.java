package androidx.cardview.widget;

import J.a;
import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import j.InterfaceC42156l;
import j.N;
import j.P;
import j.U;

/* loaded from: classes.dex */
public class CardView extends FrameLayout {
    private static final int[] COLOR_BACKGROUND_ATTR = {R.attr.colorBackground};
    private static final g IMPL = new c();
    private final f mCardViewDelegate;
    private boolean mCompatPadding;
    final Rect mContentPadding;
    private boolean mPreventCornerOverlap;
    final Rect mShadowBounds;
    int mUserSetMinHeight;
    int mUserSetMinWidth;

    public class a implements f {

        /* renamed from: a, reason: collision with root package name */
        public Drawable f25490a;

        public a() {
        }

        @Override // androidx.cardview.widget.f
        public final void a(int i12, int i13) {
            CardView cardView = CardView.this;
            if (i12 > cardView.mUserSetMinWidth) {
                CardView.super.setMinimumWidth(i12);
            }
            if (i13 > cardView.mUserSetMinHeight) {
                CardView.super.setMinimumHeight(i13);
            }
        }

        @Override // androidx.cardview.widget.f
        public final Drawable b() {
            return this.f25490a;
        }

        @Override // androidx.cardview.widget.f
        public final boolean c() {
            return CardView.this.getPreventCornerOverlap();
        }

        @Override // androidx.cardview.widget.f
        public final CardView d() {
            return CardView.this;
        }

        @Override // androidx.cardview.widget.f
        public final boolean e() {
            return CardView.this.getUseCompatPadding();
        }

        @Override // androidx.cardview.widget.f
        public final void f(int i12, int i13, int i14, int i15) {
            CardView cardView = CardView.this;
            cardView.mShadowBounds.set(i12, i13, i14, i15);
            Rect rect = cardView.mContentPadding;
            CardView.super.setPadding(i12 + rect.left, i13 + rect.top, i14 + rect.right, i15 + rect.bottom);
        }

        public final void g(Drawable drawable) {
            this.f25490a = drawable;
            CardView.this.setBackgroundDrawable(drawable);
        }
    }

    public CardView(@N Context context, @P AttributeSet attributeSet) {
        this(context, attributeSet, com.avito.android.R.attr.cardViewStyle);
    }

    @N
    public ColorStateList getCardBackgroundColor() {
        return IMPL.e(this.mCardViewDelegate);
    }

    public float getCardElevation() {
        return IMPL.a(this.mCardViewDelegate);
    }

    @U
    public int getContentPaddingBottom() {
        return this.mContentPadding.bottom;
    }

    @U
    public int getContentPaddingLeft() {
        return this.mContentPadding.left;
    }

    @U
    public int getContentPaddingRight() {
        return this.mContentPadding.right;
    }

    @U
    public int getContentPaddingTop() {
        return this.mContentPadding.top;
    }

    public float getMaxCardElevation() {
        return IMPL.i(this.mCardViewDelegate);
    }

    public boolean getPreventCornerOverlap() {
        return this.mPreventCornerOverlap;
    }

    public float getRadius() {
        return IMPL.h(this.mCardViewDelegate);
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i12, int i13) {
        if (IMPL instanceof c) {
            super.onMeasure(i12, i13);
            return;
        }
        int mode = View.MeasureSpec.getMode(i12);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            i12 = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil(r0.k(this.mCardViewDelegate)), View.MeasureSpec.getSize(i12)), mode);
        }
        int mode2 = View.MeasureSpec.getMode(i13);
        if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
            i13 = View.MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil(r0.j(this.mCardViewDelegate)), View.MeasureSpec.getSize(i13)), mode2);
        }
        super.onMeasure(i12, i13);
    }

    public void setCardBackgroundColor(@InterfaceC42156l int i12) {
        IMPL.n(this.mCardViewDelegate, ColorStateList.valueOf(i12));
    }

    public void setCardElevation(float f12) {
        IMPL.d(this.mCardViewDelegate, f12);
    }

    public void setContentPadding(@U int i12, @U int i13, @U int i14, @U int i15) {
        this.mContentPadding.set(i12, i13, i14, i15);
        IMPL.b(this.mCardViewDelegate);
    }

    public void setMaxCardElevation(float f12) {
        IMPL.c(this.mCardViewDelegate, f12);
    }

    @Override // android.view.View
    public void setMinimumHeight(int i12) {
        this.mUserSetMinHeight = i12;
        super.setMinimumHeight(i12);
    }

    @Override // android.view.View
    public void setMinimumWidth(int i12) {
        this.mUserSetMinWidth = i12;
        super.setMinimumWidth(i12);
    }

    public void setPreventCornerOverlap(boolean z12) {
        if (z12 != this.mPreventCornerOverlap) {
            this.mPreventCornerOverlap = z12;
            IMPL.m(this.mCardViewDelegate);
        }
    }

    public void setRadius(float f12) {
        IMPL.l(this.mCardViewDelegate, f12);
    }

    public void setUseCompatPadding(boolean z12) {
        if (this.mCompatPadding != z12) {
            this.mCompatPadding = z12;
            IMPL.g(this.mCardViewDelegate);
        }
    }

    public CardView(@N Context context, @P AttributeSet attributeSet, int i12) {
        ColorStateList colorStateListValueOf;
        super(context, attributeSet, i12);
        Rect rect = new Rect();
        this.mContentPadding = rect;
        this.mShadowBounds = new Rect();
        a aVar = new a();
        this.mCardViewDelegate = aVar;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.e.f8694a, i12, com.avito.android.R.style.CardView);
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            colorStateListValueOf = typedArrayObtainStyledAttributes.getColorStateList(2);
        } else {
            TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
            int color = typedArrayObtainStyledAttributes2.getColor(0, 0);
            typedArrayObtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color, fArr);
            colorStateListValueOf = ColorStateList.valueOf(fArr[2] > 0.5f ? getResources().getColor(com.avito.android.R.color.cardview_light_background) : getResources().getColor(com.avito.android.R.color.cardview_dark_background));
        }
        ColorStateList colorStateList = colorStateListValueOf;
        float dimension = typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
        float dimension3 = typedArrayObtainStyledAttributes.getDimension(5, 0.0f);
        this.mCompatPadding = typedArrayObtainStyledAttributes.getBoolean(7, false);
        this.mPreventCornerOverlap = typedArrayObtainStyledAttributes.getBoolean(6, true);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, 0);
        rect.left = typedArrayObtainStyledAttributes.getDimensionPixelSize(10, dimensionPixelSize);
        rect.top = typedArrayObtainStyledAttributes.getDimensionPixelSize(12, dimensionPixelSize);
        rect.right = typedArrayObtainStyledAttributes.getDimensionPixelSize(11, dimensionPixelSize);
        rect.bottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(9, dimensionPixelSize);
        float f12 = dimension2 > dimension3 ? dimension2 : dimension3;
        this.mUserSetMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.mUserSetMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
        typedArrayObtainStyledAttributes.recycle();
        IMPL.f(aVar, context, colorStateList, dimension, dimension2, f12);
    }

    public void setCardBackgroundColor(@P ColorStateList colorStateList) {
        IMPL.n(this.mCardViewDelegate, colorStateList);
    }

    @Override // android.view.View
    public void setPadding(int i12, int i13, int i14, int i15) {
    }

    @Override // android.view.View
    public void setPaddingRelative(int i12, int i13, int i14, int i15) {
    }
}
