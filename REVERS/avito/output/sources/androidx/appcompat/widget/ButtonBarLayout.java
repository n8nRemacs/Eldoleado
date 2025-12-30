package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import androidx.core.view.C22823h0;
import com.avito.android.R;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import java.util.WeakHashMap;
import l.C43521a;

@RestrictTo
/* loaded from: classes.dex */
public class ButtonBarLayout extends LinearLayout {

    /* renamed from: b, reason: collision with root package name */
    public boolean f22099b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f22100c;

    /* renamed from: d, reason: collision with root package name */
    public int f22101d;

    public ButtonBarLayout(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f22101d = -1;
        int[] iArr = C43521a.m.f413362k;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        C22823h0.B(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, 0);
        this.f22099b = typedArrayObtainStyledAttributes.getBoolean(0, true);
        typedArrayObtainStyledAttributes.recycle();
        if (getOrientation() == 1) {
            setStacked(this.f22099b);
        }
    }

    private void setStacked(boolean z12) {
        if (this.f22100c != z12) {
            if (!z12 || this.f22099b) {
                this.f22100c = z12;
                setOrientation(z12 ? 1 : 0);
                setGravity(z12 ? 8388613 : 80);
                View viewFindViewById = findViewById(R.id.spacer);
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(z12 ? 8 : 4);
                }
                for (int childCount = getChildCount() - 2; childCount >= 0; childCount--) {
                    bringChildToFront(getChildAt(childCount));
                }
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i12, int i13) {
        int iMakeMeasureSpec;
        boolean z12;
        int i14;
        int size = View.MeasureSpec.getSize(i12);
        int paddingBottom = 0;
        if (this.f22099b) {
            if (size > this.f22101d && this.f22100c) {
                setStacked(false);
            }
            this.f22101d = size;
        }
        if (this.f22100c || View.MeasureSpec.getMode(i12) != 1073741824) {
            iMakeMeasureSpec = i12;
            z12 = false;
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, BeduinInputModel.MIN_TEXT_VERSION);
            z12 = true;
        }
        super.onMeasure(iMakeMeasureSpec, i13);
        if (this.f22099b && !this.f22100c && (getMeasuredWidthAndState() & (-16777216)) == 16777216) {
            setStacked(true);
            z12 = true;
        }
        if (z12) {
            super.onMeasure(i12, i13);
        }
        int childCount = getChildCount();
        int i15 = 0;
        while (true) {
            i14 = -1;
            if (i15 >= childCount) {
                i15 = -1;
                break;
            } else if (getChildAt(i15).getVisibility() == 0) {
                break;
            } else {
                i15++;
            }
        }
        if (i15 >= 0) {
            View childAt = getChildAt(i15);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight() + getPaddingTop() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (this.f22100c) {
                int i16 = i15 + 1;
                int childCount2 = getChildCount();
                while (true) {
                    if (i16 >= childCount2) {
                        break;
                    }
                    if (getChildAt(i16).getVisibility() == 0) {
                        i14 = i16;
                        break;
                    }
                    i16++;
                }
                paddingBottom = i14 >= 0 ? getChildAt(i14).getPaddingTop() + ((int) (getResources().getDisplayMetrics().density * 16.0f)) + measuredHeight : measuredHeight;
            } else {
                paddingBottom = getPaddingBottom() + measuredHeight;
            }
        }
        WeakHashMap<View, androidx.core.view.A0> weakHashMap = C22823h0.f45055a;
        if (getMinimumHeight() != paddingBottom) {
            setMinimumHeight(paddingBottom);
            if (i13 == 0) {
                super.onMeasure(i12, i13);
            }
        }
    }

    public void setAllowStacking(boolean z12) {
        if (this.f22099b != z12) {
            this.f22099b = z12;
            if (!z12 && this.f22100c) {
                setStacked(false);
            }
            requestLayout();
        }
    }
}
