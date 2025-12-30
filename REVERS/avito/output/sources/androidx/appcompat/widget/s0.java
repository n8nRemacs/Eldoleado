package androidx.appcompat.widget;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import com.avito.android.R;

/* compiled from: SearchView.java */
/* loaded from: classes.dex */
class s0 implements View.OnLayoutChangeListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SearchView f22624b;

    public s0(SearchView searchView) {
        this.f22624b = searchView;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19) {
        SearchView searchView = this.f22624b;
        View view2 = searchView.f22297y;
        if (view2.getWidth() > 1) {
            Resources resources = searchView.getContext().getResources();
            int paddingLeft = searchView.f22291s.getPaddingLeft();
            Rect rect = new Rect();
            boolean z12 = searchView.getLayoutDirection() == 1;
            int dimensionPixelSize = searchView.f22269Q ? resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left) + resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) : 0;
            SearchView.SearchAutoComplete searchAutoComplete = searchView.f22289q;
            searchAutoComplete.getDropDownBackground().getPadding(rect);
            searchAutoComplete.setDropDownHorizontalOffset(z12 ? -rect.left : paddingLeft - (rect.left + dimensionPixelSize));
            searchAutoComplete.setDropDownWidth((((view2.getWidth() + rect.left) + rect.right) + dimensionPixelSize) - paddingLeft);
        }
    }
}
