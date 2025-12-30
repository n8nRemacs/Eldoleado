package aB0;

import Y61.k;
import android.graphics.Rect;
import android.view.View;
import androidx.compose.runtime.internal.P;
import androidx.recyclerview.widget.RecyclerView;
import com.avito.android.tariff.cpa.configure_info.items.feature.h;
import com.avito.android.util.y6;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: CpaConfigureInfoDecoration.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaB0/a;", "Landroidx/recyclerview/widget/RecyclerView$l;", "_avito_tariff_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aB0.a, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public final class C19752a extends RecyclerView.l {

    /* renamed from: b, reason: collision with root package name */
    public final int f20811b;

    @Inject
    public C19752a(@k com.avito.konveyor.a aVar) {
        this.f20811b = aVar.Y(com.avito.android.tariff.cpa.configure_info.items.feature.b.class);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public final void getItemOffsets(@k Rect rect, @k View view, @k RecyclerView recyclerView, @k RecyclerView.z zVar) {
        int iB2;
        int iU2;
        if (recyclerView.W(view) instanceof h) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            int itemViewType = -1;
            if (adapter != null && (iU2 = RecyclerView.U(view)) != -1 && iU2 != 0) {
                itemViewType = adapter.getItemViewType(iU2 - 1);
            }
            iB2 = itemViewType == this.f20811b ? y6.b(20) : y6.b(32);
        } else {
            iB2 = 0;
        }
        rect.top = iB2;
        rect.bottom = RecyclerView.U(view) == zVar.b() + (-1) ? y6.b(40) : 0;
        rect.left = y6.b(24);
        rect.right = y6.b(24);
    }
}
