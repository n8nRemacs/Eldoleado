package a90;

import Y61.k;
import a90.e;
import android.view.View;
import androidx.compose.runtime.internal.P;
import androidx.recyclerview.widget.RecyclerView;
import com.avito.android.R;
import com.avito.android.analytics.screens.mvi.r;
import com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker;
import com.avito.android.position_in_search.stats.screen.old_position_in_search.view.renderer.toast.a;
import com.avito.android.position_in_search.stats.screen.old_position_in_search.view.widget.bottom_bar.SearchPositionBottomBar;
import com.avito.android.position_in_search.stats.screen.old_position_in_search.view.widget.toolbar.SearchPositionToolbar;
import com.avito.konveyor.adapter.h;
import java.util.List;
import javax.inject.Inject;
import kotlin.G0;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.s0;

/* compiled from: ScreenRenderer.kt */
@P
@s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"La90/b;", "", "a", "_avito_position-in-search_stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final ScreenPerformanceTracker f20759a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final SearchPositionToolbar f20760b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final RecyclerView f20761c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final SearchPositionBottomBar f20762d;

    /* renamed from: e, reason: collision with root package name */
    @k
    public final com.avito.konveyor.adapter.d f20763e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final com.avito.android.position_in_search.stats.screen.old_position_in_search.view.renderer.toast.a f20764f;

    /* compiled from: ScreenRenderer.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La90/b$a;", "", "_avito_position-in-search_stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final a.b f20765a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final com.avito.konveyor.a f20766b;

        /* renamed from: c, reason: collision with root package name */
        @k
        public final com.avito.android.position_in_search.stats.screen.old_position_in_search.view.items.b f20767c;

        /* renamed from: d, reason: collision with root package name */
        @k
        public final ScreenPerformanceTracker f20768d;

        @Inject
        public a(@k a.b bVar, @k com.avito.konveyor.a aVar, @k com.avito.android.position_in_search.stats.screen.old_position_in_search.view.items.b bVar2, @k ScreenPerformanceTracker screenPerformanceTracker) {
            this.f20765a = bVar;
            this.f20766b = aVar;
            this.f20767c = bVar2;
            this.f20768d = screenPerformanceTracker;
        }
    }

    public b(View view, com.avito.konveyor.a aVar, com.avito.android.position_in_search.stats.screen.old_position_in_search.view.items.b bVar, a.b bVar2, ScreenPerformanceTracker screenPerformanceTracker, C42822w c42822w) {
        this.f20759a = screenPerformanceTracker;
        SearchPositionToolbar searchPositionToolbar = (SearchPositionToolbar) view.findViewById(R.id.search_position_toolbar);
        this.f20760b = searchPositionToolbar;
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search_position_rv);
        this.f20761c = recyclerView;
        SearchPositionBottomBar searchPositionBottomBar = (SearchPositionBottomBar) view.findViewById(R.id.search_position_bottom_bar);
        this.f20762d = searchPositionBottomBar;
        com.avito.konveyor.adapter.d dVar = new com.avito.konveyor.adapter.d(new h(aVar, aVar, null, 4, null), aVar, bVar);
        this.f20763e = dVar;
        bVar2.getClass();
        this.f20764f = new com.avito.android.position_in_search.stats.screen.old_position_in_search.view.renderer.toast.a(searchPositionBottomBar, bVar2.f221244a, bVar2.f221245b, null);
        screenPerformanceTracker.b(recyclerView);
        recyclerView.setAdapter(dVar);
        recyclerView.l(new com.avito.android.position_in_search.stats.screen.old_position_in_search.view.items.d(aVar), -1);
        ViewOnLayoutChangeListenerC19701a viewOnLayoutChangeListenerC19701a = new ViewOnLayoutChangeListenerC19701a(this, 0);
        searchPositionToolbar.addOnLayoutChangeListener(viewOnLayoutChangeListenerC19701a);
        searchPositionBottomBar.addOnLayoutChangeListener(viewOnLayoutChangeListenerC19701a);
    }

    public final void a(e.a aVar) {
        boolean z12 = aVar instanceof e.a.C1484a;
        SearchPositionBottomBar searchPositionBottomBar = this.f20762d;
        if (z12) {
            searchPositionBottomBar.setVisibility(0);
            searchPositionBottomBar.setState(((e.a.C1484a) aVar).f20775a);
        } else if (aVar instanceof e.a.b) {
            searchPositionBottomBar.setVisibility(8);
        }
    }

    public final void b(r rVar, List<? extends com.avito.android.position_in_search.stats.screen.old_position_in_search.view.items.a> list) {
        G0 g02;
        com.avito.konveyor.adapter.d dVar = this.f20763e;
        if (rVar != null) {
            ScreenPerformanceTracker screenPerformanceTracker = this.f20759a;
            String str = rVar.f90666a;
            screenPerformanceTracker.m(str);
            dVar.l(list, null);
            ScreenPerformanceTracker.a.c(screenPerformanceTracker, str, rVar.f90667b, null, 4);
            g02 = G0.f406611a;
        } else {
            g02 = null;
        }
        if (g02 == null) {
            dVar.l(list, null);
        }
    }
}
