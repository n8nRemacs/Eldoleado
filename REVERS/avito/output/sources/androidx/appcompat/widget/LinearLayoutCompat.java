package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import androidx.core.view.C22823h0;
import j.InterfaceC42144A;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import l.C43521a;

/* loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {

    /* renamed from: b, reason: collision with root package name */
    public boolean f22188b;

    /* renamed from: c, reason: collision with root package name */
    public int f22189c;

    /* renamed from: d, reason: collision with root package name */
    public int f22190d;

    /* renamed from: e, reason: collision with root package name */
    public int f22191e;

    /* renamed from: f, reason: collision with root package name */
    public int f22192f;

    /* renamed from: g, reason: collision with root package name */
    public int f22193g;

    /* renamed from: h, reason: collision with root package name */
    public float f22194h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f22195i;

    /* renamed from: j, reason: collision with root package name */
    public int[] f22196j;

    /* renamed from: k, reason: collision with root package name */
    public int[] f22197k;

    /* renamed from: l, reason: collision with root package name */
    public Drawable f22198l;

    /* renamed from: m, reason: collision with root package name */
    public int f22199m;

    /* renamed from: n, reason: collision with root package name */
    public int f22200n;

    /* renamed from: o, reason: collision with root package name */
    public int f22201o;

    /* renamed from: p, reason: collision with root package name */
    public int f22202p;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface a {
    }

    public static class b extends LinearLayout.LayoutParams {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface c {
    }

    public LinearLayoutCompat(@j.N Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof b;
    }

    public void g(Canvas canvas, int i12) {
        this.f22198l.setBounds(getPaddingLeft() + this.f22202p, i12, (getWidth() - getPaddingRight()) - this.f22202p, this.f22200n + i12);
        this.f22198l.draw(canvas);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i12;
        if (this.f22189c < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i13 = this.f22189c;
        if (childCount <= i13) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i13);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.f22189c == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int iF2 = this.f22190d;
        if (this.f22191e == 1 && (i12 = this.f22192f & 112) != 48) {
            if (i12 == 16) {
                iF2 = androidx.appcompat.app.r.f(((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom(), this.f22193g, 2, iF2);
            } else if (i12 == 80) {
                iF2 = ((getBottom() - getTop()) - getPaddingBottom()) - this.f22193g;
            }
        }
        return iF2 + ((LinearLayout.LayoutParams) ((b) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.f22189c;
    }

    public Drawable getDividerDrawable() {
        return this.f22198l;
    }

    public int getDividerPadding() {
        return this.f22202p;
    }

    @RestrictTo
    public int getDividerWidth() {
        return this.f22199m;
    }

    @InterfaceC42144A
    public int getGravity() {
        return this.f22192f;
    }

    public int getOrientation() {
        return this.f22191e;
    }

    public int getShowDividers() {
        return this.f22201o;
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.f22194h;
    }

    public void i(Canvas canvas, int i12) {
        this.f22198l.setBounds(i12, getPaddingTop() + this.f22202p, this.f22199m + i12, (getHeight() - getPaddingBottom()) - this.f22202p);
        this.f22198l.draw(canvas);
    }

    @Override // android.view.ViewGroup
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public b generateDefaultLayoutParams() {
        int i12 = this.f22191e;
        if (i12 == 0) {
            return new b(-2, -2);
        }
        if (i12 == 1) {
            return new b(-1, -2);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public b generateLayoutParams(AttributeSet attributeSet) {
        return new b(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public b generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof b ? new b((b) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new b((ViewGroup.MarginLayoutParams) layoutParams) : new b(layoutParams);
    }

    @RestrictTo
    public final boolean m(int i12) {
        if (i12 == 0) {
            return (this.f22201o & 1) != 0;
        }
        if (i12 == getChildCount()) {
            return (this.f22201o & 4) != 0;
        }
        if ((this.f22201o & 2) == 0) {
            return false;
        }
        for (int i13 = i12 - 1; i13 >= 0; i13--) {
            if (getChildAt(i13).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public final void onDraw(@j.N Canvas canvas) {
        int right;
        int left;
        int i12;
        if (this.f22198l == null) {
            return;
        }
        int i13 = 0;
        if (this.f22191e == 1) {
            int virtualChildCount = getVirtualChildCount();
            while (i13 < virtualChildCount) {
                View childAt = getChildAt(i13);
                if (childAt != null && childAt.getVisibility() != 8 && m(i13)) {
                    g(canvas, (childAt.getTop() - ((LinearLayout.LayoutParams) ((b) childAt.getLayoutParams())).topMargin) - this.f22200n);
                }
                i13++;
            }
            if (m(virtualChildCount)) {
                View childAt2 = getChildAt(virtualChildCount - 1);
                g(canvas, childAt2 == null ? (getHeight() - getPaddingBottom()) - this.f22200n : childAt2.getBottom() + ((LinearLayout.LayoutParams) ((b) childAt2.getLayoutParams())).bottomMargin);
                return;
            }
            return;
        }
        int virtualChildCount2 = getVirtualChildCount();
        boolean z12 = getLayoutDirection() == 1;
        while (i13 < virtualChildCount2) {
            View childAt3 = getChildAt(i13);
            if (childAt3 != null && childAt3.getVisibility() != 8 && m(i13)) {
                b bVar = (b) childAt3.getLayoutParams();
                i(canvas, z12 ? childAt3.getRight() + ((LinearLayout.LayoutParams) bVar).rightMargin : (childAt3.getLeft() - ((LinearLayout.LayoutParams) bVar).leftMargin) - this.f22199m);
            }
            i13++;
        }
        if (m(virtualChildCount2)) {
            View childAt4 = getChildAt(virtualChildCount2 - 1);
            if (childAt4 != null) {
                b bVar2 = (b) childAt4.getLayoutParams();
                if (z12) {
                    left = childAt4.getLeft() - ((LinearLayout.LayoutParams) bVar2).leftMargin;
                    i12 = this.f22199m;
                    right = left - i12;
                } else {
                    right = childAt4.getRight() + ((LinearLayout.LayoutParams) bVar2).rightMargin;
                }
            } else if (z12) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i12 = this.f22199m;
                right = left - i12;
            }
            i(canvas, right);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01ae  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onLayout(boolean r23, int r24, int r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 466
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.onLayout(boolean, int, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0486  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x048b  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x04de  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x04f2  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0537  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x05e4  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0698  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x07cb  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0806  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x083d  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x0860  */
    /* JADX WARN: Removed duplicated region for block: B:442:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onMeasure(int r38, int r39) {
        /*
            Method dump skipped, instructions count: 2208
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.onMeasure(int, int):void");
    }

    public void setBaselineAligned(boolean z12) {
        this.f22188b = z12;
    }

    public void setBaselineAlignedChildIndex(int i12) {
        if (i12 >= 0 && i12 < getChildCount()) {
            this.f22189c = i12;
            return;
        }
        throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.f22198l) {
            return;
        }
        this.f22198l = drawable;
        if (drawable != null) {
            this.f22199m = drawable.getIntrinsicWidth();
            this.f22200n = drawable.getIntrinsicHeight();
        } else {
            this.f22199m = 0;
            this.f22200n = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i12) {
        this.f22202p = i12;
    }

    public void setGravity(@InterfaceC42144A int i12) {
        if (this.f22192f != i12) {
            if ((8388615 & i12) == 0) {
                i12 |= 8388611;
            }
            if ((i12 & 112) == 0) {
                i12 |= 48;
            }
            this.f22192f = i12;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i12) {
        int i13 = i12 & 8388615;
        int i14 = this.f22192f;
        if ((8388615 & i14) != i13) {
            this.f22192f = i13 | ((-8388616) & i14);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z12) {
        this.f22195i = z12;
    }

    public void setOrientation(int i12) {
        if (this.f22191e != i12) {
            this.f22191e = i12;
            requestLayout();
        }
    }

    public void setShowDividers(int i12) {
        if (i12 != this.f22201o) {
            requestLayout();
        }
        this.f22201o = i12;
    }

    public void setVerticalGravity(int i12) {
        int i13 = i12 & 112;
        int i14 = this.f22192f;
        if ((i14 & 112) != i13) {
            this.f22192f = i13 | (i14 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f12) {
        this.f22194h = Math.max(0.0f, f12);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        this.f22188b = true;
        this.f22189c = -1;
        this.f22190d = 0;
        this.f22192f = 8388659;
        int[] iArr = C43521a.m.f413365n;
        D0 d0E = D0.e(context, attributeSet, iArr, i12);
        C22823h0.B(this, context, iArr, attributeSet, d0E.f22121b, i12);
        TypedArray typedArray = d0E.f22121b;
        int i13 = typedArray.getInt(1, -1);
        if (i13 >= 0) {
            setOrientation(i13);
        }
        int i14 = typedArray.getInt(0, -1);
        if (i14 >= 0) {
            setGravity(i14);
        }
        boolean z12 = typedArray.getBoolean(2, true);
        if (!z12) {
            setBaselineAligned(z12);
        }
        this.f22194h = typedArray.getFloat(4, -1.0f);
        this.f22189c = typedArray.getInt(3, -1);
        this.f22195i = typedArray.getBoolean(7, false);
        setDividerDrawable(d0E.b(5));
        this.f22201o = typedArray.getInt(8, 0);
        this.f22202p = typedArray.getDimensionPixelSize(6, 0);
        d0E.f();
    }
}
