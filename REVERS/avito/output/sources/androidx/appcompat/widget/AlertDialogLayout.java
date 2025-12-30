package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.C22823h0;
import com.avito.android.R;
import java.util.WeakHashMap;

@RestrictTo
/* loaded from: classes.dex */
public class AlertDialogLayout extends LinearLayoutCompat {
    public AlertDialogLayout(@j.P Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static int n(View view) {
        WeakHashMap<View, androidx.core.view.A0> weakHashMap = C22823h0.f45055a;
        int minimumHeight = view.getMinimumHeight();
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return n(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009d  */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onLayout(boolean r11, int r12, int r13, int r14, int r15) {
        /*
            r10 = this;
            int r11 = r10.getPaddingLeft()
            int r14 = r14 - r12
            int r12 = r10.getPaddingRight()
            int r12 = r14 - r12
            int r14 = r14 - r11
            int r0 = r10.getPaddingRight()
            int r14 = r14 - r0
            int r0 = r10.getMeasuredHeight()
            int r1 = r10.getChildCount()
            int r2 = r10.getGravity()
            r3 = r2 & 112(0x70, float:1.57E-43)
            r4 = 8388615(0x800007, float:1.1754953E-38)
            r2 = r2 & r4
            r4 = 16
            r5 = 2
            if (r3 == r4) goto L3a
            r4 = 80
            if (r3 == r4) goto L31
            int r13 = r10.getPaddingTop()
            goto L43
        L31:
            int r3 = r10.getPaddingTop()
            int r3 = r3 + r15
            int r3 = r3 - r13
            int r13 = r3 - r0
            goto L43
        L3a:
            int r3 = r10.getPaddingTop()
            int r15 = r15 - r13
            int r15 = r15 - r0
            int r15 = r15 / r5
            int r13 = r15 + r3
        L43:
            android.graphics.drawable.Drawable r15 = r10.getDividerDrawable()
            r0 = 0
            if (r15 != 0) goto L4c
            r15 = r0
            goto L50
        L4c:
            int r15 = r15.getIntrinsicHeight()
        L50:
            if (r0 >= r1) goto Laf
            android.view.View r3 = r10.getChildAt(r0)
            if (r3 == 0) goto Lac
            int r4 = r3.getVisibility()
            r6 = 8
            if (r4 == r6) goto Lac
            int r4 = r3.getMeasuredWidth()
            int r6 = r3.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r7 = r3.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$b r7 = (androidx.appcompat.widget.LinearLayoutCompat.b) r7
            int r8 = r7.gravity
            if (r8 >= 0) goto L73
            r8 = r2
        L73:
            int r9 = r10.getLayoutDirection()
            int r8 = android.view.Gravity.getAbsoluteGravity(r8, r9)
            r8 = r8 & 7
            r9 = 1
            if (r8 == r9) goto L8d
            r9 = 5
            if (r8 == r9) goto L87
            int r8 = r7.leftMargin
            int r8 = r8 + r11
            goto L97
        L87:
            int r8 = r12 - r4
            int r9 = r7.rightMargin
        L8b:
            int r8 = r8 - r9
            goto L97
        L8d:
            int r8 = androidx.appcompat.app.r.f(r14, r4, r5, r11)
            int r9 = r7.leftMargin
            int r8 = r8 + r9
            int r9 = r7.rightMargin
            goto L8b
        L97:
            boolean r9 = r10.m(r0)
            if (r9 == 0) goto L9e
            int r13 = r13 + r15
        L9e:
            int r9 = r7.topMargin
            int r13 = r13 + r9
            int r4 = r4 + r8
            int r9 = r13 + r6
            r3.layout(r8, r13, r4, r9)
            int r3 = r7.bottomMargin
            int r6 = r6 + r3
            int r6 = r6 + r13
            r13 = r6
        Lac:
            int r0 = r0 + 1
            goto L50
        Laf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AlertDialogLayout.onLayout(boolean, int, int, int, int):void");
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i12, int i13) {
        int iCombineMeasuredStates;
        int iN2;
        int measuredHeight;
        int measuredHeight2;
        int childCount = getChildCount();
        View view = null;
        View view2 = null;
        View view3 = null;
        for (int i14 = 0; i14 < childCount; i14++) {
            View childAt = getChildAt(i14);
            if (childAt.getVisibility() != 8) {
                int id2 = childAt.getId();
                if (id2 == R.id.topPanel) {
                    view = childAt;
                } else if (id2 == R.id.buttonPanel) {
                    view2 = childAt;
                } else {
                    if ((id2 != R.id.contentPanel && id2 != R.id.customPanel) || view3 != null) {
                        super.onMeasure(i12, i13);
                        return;
                    }
                    view3 = childAt;
                }
            }
        }
        int mode = View.MeasureSpec.getMode(i13);
        int size = View.MeasureSpec.getSize(i13);
        int mode2 = View.MeasureSpec.getMode(i12);
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (view != null) {
            view.measure(i12, 0);
            paddingBottom += view.getMeasuredHeight();
            iCombineMeasuredStates = View.combineMeasuredStates(0, view.getMeasuredState());
        } else {
            iCombineMeasuredStates = 0;
        }
        if (view2 != null) {
            view2.measure(i12, 0);
            iN2 = n(view2);
            measuredHeight = view2.getMeasuredHeight() - iN2;
            paddingBottom += iN2;
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
        } else {
            iN2 = 0;
            measuredHeight = 0;
        }
        if (view3 != null) {
            view3.measure(i12, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingBottom), mode));
            measuredHeight2 = view3.getMeasuredHeight();
            paddingBottom += measuredHeight2;
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
        } else {
            measuredHeight2 = 0;
        }
        int i15 = size - paddingBottom;
        if (view2 != null) {
            int i16 = paddingBottom - iN2;
            int iMin = Math.min(i15, measuredHeight);
            if (iMin > 0) {
                i15 -= iMin;
                iN2 += iMin;
            }
            view2.measure(i12, View.MeasureSpec.makeMeasureSpec(iN2, 1073741824));
            paddingBottom = i16 + view2.getMeasuredHeight();
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
        }
        if (view3 != null && i15 > 0) {
            view3.measure(i12, View.MeasureSpec.makeMeasureSpec(measuredHeight2 + i15, mode));
            paddingBottom = (paddingBottom - measuredHeight2) + view3.getMeasuredHeight();
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
        }
        int iMax = 0;
        for (int i17 = 0; i17 < childCount; i17++) {
            View childAt2 = getChildAt(i17);
            if (childAt2.getVisibility() != 8) {
                iMax = Math.max(iMax, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(getPaddingRight() + getPaddingLeft() + iMax, i12, iCombineMeasuredStates), View.resolveSizeAndState(paddingBottom, i13, 0));
        if (mode2 != 1073741824) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
            for (int i18 = 0; i18 < childCount; i18++) {
                View childAt3 = getChildAt(i18);
                if (childAt3.getVisibility() != 8) {
                    LinearLayoutCompat.b bVar = (LinearLayoutCompat.b) childAt3.getLayoutParams();
                    if (((LinearLayout.LayoutParams) bVar).width == -1) {
                        int i19 = ((LinearLayout.LayoutParams) bVar).height;
                        ((LinearLayout.LayoutParams) bVar).height = childAt3.getMeasuredHeight();
                        measureChildWithMargins(childAt3, iMakeMeasureSpec, 0, i13, 0);
                        ((LinearLayout.LayoutParams) bVar).height = i19;
                    }
                }
            }
        }
    }
}
