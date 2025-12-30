package Al;

import Y61.k;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.avito.android.R;
import com.avito.android.util.y6;
import kotlin.Metadata;

/* compiled from: BrandGlobalSnippetDecorator.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAl/a;", "LUr0/c;", "_avito_serp_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Al.a, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final class C13048a extends Ur0.c {

    /* renamed from: s, reason: collision with root package name */
    public final int f607s;

    /* renamed from: t, reason: collision with root package name */
    public final int f608t;

    /* renamed from: u, reason: collision with root package name */
    public final int f609u;

    /* renamed from: v, reason: collision with root package name */
    public final int f610v;

    /* renamed from: w, reason: collision with root package name */
    public final int f611w;

    /* renamed from: x, reason: collision with root package name */
    public final int f612x;

    public C13048a(@k Resources resources, @k com.avito.konveyor.a aVar) {
        super(resources, aVar);
        this.f607s = aVar.Y(com.avito.android.brand_global_snippet.a.class);
        this.f608t = y6.b(6);
        this.f609u = y6.b(10);
        this.f610v = y6.b(8);
        this.f611w = y6.b(16);
        this.f612x = resources.getInteger(R.integer.serp_columns);
    }

    @Override // Ur0.c
    public final void c(@k Rect rect, @k View view, @k RecyclerView recyclerView, @k RecyclerView.z zVar) {
        RecyclerView.C cW2 = recyclerView.W(view);
        int iU2 = RecyclerView.U(view);
        int i12 = this.f612x;
        int i13 = iU2 % i12;
        if (cW2.getItemViewType() == this.f607s) {
            rect.top = this.f611w;
            rect.bottom = this.f610v;
            int i14 = this.f609u;
            rect.left = (i13 * i14) / i12;
            rect.right = i14 - (((i13 + 1) * i14) / i12);
            int i15 = this.f608t;
            if (i13 == 0) {
                rect.left = i15;
            } else if (i13 == i12 - 1) {
                rect.right = i15;
            }
        }
    }
}
