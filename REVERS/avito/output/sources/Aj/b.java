package AJ;

import Q7.a;
import Y41.l;
import YM0.a;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.view.dynamicfeatures.j;
import com.avito.android.bxcontent.mvi.entity.InterfaceC29127b;
import com.avito.android.home.HomeActivity;
import com.avito.android.str_seller_orders.strsellerorders.StrSellerOrdersFragment;
import com.avito.avcalls.stats.a;
import com.google.android.gms.tasks.InterfaceC37023f;
import com.google.android.gms.tasks.InterfaceC37024g;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.P0;
import l41.r;
import org.webrtc.RTCStats;
import org.webrtc.RTCStatsCollectorCallback;
import org.webrtc.RTCStatsReport;
import yz0.InterfaceC50328b;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes13.dex */
public final /* synthetic */ class b implements RTCStatsCollectorCallback, InterfaceC37024g, SwipeRefreshLayout.f, r, InterfaceC37023f, com.avito.android.lib.beduin_v2.component.exception.callback.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f280b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ l f281c;

    public /* synthetic */ b(int i12, l lVar) {
        this.f280b = i12;
        this.f281c = lVar;
    }

    @Override // com.avito.android.lib.beduin_v2.component.exception.callback.a
    public void a(String str, String str2) {
        l lVar = this.f281c;
        switch (this.f280b) {
            case 7:
                com.avito.android.bxcontent.beduin_v2.di.b bVar = com.avito.android.bxcontent.beduin_v2.di.b.f109514a;
                lVar.invoke(new InterfaceC29127b.InterfaceC29141h.c(str, str2));
                break;
            default:
                com.avito.android.bxcontent.beduin_v2.di.b bVar2 = com.avito.android.bxcontent.beduin_v2.di.b.f109514a;
                lVar.invoke(new InterfaceC29127b.InterfaceC29141h.c(str, str2));
                break;
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.f
    public void j0() {
        l lVar = this.f281c;
        switch (this.f280b) {
            case 2:
                lVar.invoke(a.f.f13513a);
                break;
            case 15:
                StrSellerOrdersFragment.a aVar = StrSellerOrdersFragment.f289655z0;
                lVar.invoke(InterfaceC50328b.k.f443643a);
                break;
            default:
                lVar.invoke(a.c.f19454a);
                break;
        }
    }

    @Override // com.google.android.gms.tasks.InterfaceC37023f
    public void onFailure(Exception exc) {
        this.f281c.invoke(exc);
    }

    @Override // org.webrtc.RTCStatsCollectorCallback
    public void onStatsDelivered(RTCStatsReport rTCStatsReport) {
        BJ.a.f1347a.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, RTCStats> entry : rTCStatsReport.getStatsMap().entrySet()) {
            String key = entry.getKey();
            RTCStats value = entry.getValue();
            String id2 = value.getId();
            String type = value.getType();
            double timestampUs = value.getTimestampUs() / 1000;
            Map<String, Object> members = value.getMembers();
            if (members == null) {
                members = P0.c();
            }
            linkedHashMap.put(key, new a.C10362a(id2, type, timestampUs, members));
        }
        this.f281c.invoke(new com.avito.avcalls.stats.a(rTCStatsReport.getTimestampUs() / 1000, P0.q(linkedHashMap)));
    }

    @Override // com.google.android.gms.tasks.InterfaceC37024g
    public void onSuccess(Object obj) {
        l lVar = this.f281c;
        switch (this.f280b) {
            case 1:
                j.a aVar = j.f53089c;
                lVar.invoke(obj);
                break;
            case 4:
                lVar.invoke(obj);
                break;
            case 9:
                int i12 = HomeActivity.f162176x;
                lVar.invoke(obj);
                break;
            default:
                lVar.invoke(obj);
                break;
        }
    }

    @Override // l41.r
    public boolean test(Object obj) {
        switch (this.f280b) {
        }
        return ((Boolean) this.f281c.invoke(obj)).booleanValue();
    }
}
