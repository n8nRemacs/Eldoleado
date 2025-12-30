package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LinearLayoutWithCenteredDividers.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/appcompat/widget/b0;", "Landroidx/appcompat/widget/LinearLayoutCompat;", "div_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
@InterfaceC42830m
/* renamed from: androidx.appcompat.widget.b0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19940b0 extends LinearLayoutCompat {
    public C19940b0(Context context, AttributeSet attributeSet, int i12, int i13, C42822w c42822w) {
        super(context, (i13 & 2) != 0 ? null : attributeSet, (i13 & 4) != 0 ? 0 : i12);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    public final void g(@Y61.k Canvas canvas, int i12) {
        n(canvas, getDividerPadding() + getPaddingLeft(), i12, (getWidth() - getPaddingRight()) - getDividerPadding(), getDividerDrawable().getIntrinsicHeight() + i12);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    public final void i(@Y61.k Canvas canvas, int i12) {
        n(canvas, i12, getDividerPadding() + getPaddingTop(), getDividerDrawable().getIntrinsicWidth() + i12, (getHeight() - getPaddingBottom()) - getDividerPadding());
    }

    public final void n(Canvas canvas, int i12, int i13, int i14, int i15) {
        Drawable dividerDrawable = getDividerDrawable();
        float f12 = (i12 + i14) / 2.0f;
        float f13 = (i13 + i15) / 2.0f;
        float intrinsicWidth = dividerDrawable.getIntrinsicWidth() / 2.0f;
        float intrinsicHeight = dividerDrawable.getIntrinsicHeight() / 2.0f;
        dividerDrawable.setBounds((int) (f12 - intrinsicWidth), (int) (f13 - intrinsicHeight), (int) (f12 + intrinsicWidth), (int) (f13 + intrinsicHeight));
        dividerDrawable.draw(canvas);
    }
}
