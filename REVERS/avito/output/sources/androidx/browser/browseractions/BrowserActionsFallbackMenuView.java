package androidx.browser.browseractions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import j.N;

@RestrictTo
@Deprecated
/* loaded from: classes.dex */
public class BrowserActionsFallbackMenuView extends LinearLayout {

    /* renamed from: b, reason: collision with root package name */
    public final int f22819b;

    /* renamed from: c, reason: collision with root package name */
    public final int f22820c;

    public BrowserActionsFallbackMenuView(@N Context context, @N AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f22819b = getResources().getDimensionPixelOffset(R.dimen.browser_actions_context_menu_min_padding);
        this.f22820c = getResources().getDimensionPixelOffset(R.dimen.browser_actions_context_menu_max_width);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i12, int i13) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.min(getResources().getDisplayMetrics().widthPixels - (this.f22819b * 2), this.f22820c), 1073741824), i13);
    }
}
