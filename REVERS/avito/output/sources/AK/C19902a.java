package ak;

import Y61.k;
import Y61.l;
import android.content.Intent;
import android.os.Bundle;
import com.avito.android.bottom_navigation.NavigationTabSetItem;
import com.avito.android.bottom_navigation.ui.fragment.factory.TabFragmentFactory;
import com.avito.android.bottom_navigation.ui.fragment.h;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;

/* compiled from: Intents.kt */
@s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"_avito_bottom-navigation_impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* renamed from: ak.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C19902a {
    @l
    public static final TabFragmentFactory.Data a(@k Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            return (TabFragmentFactory.Data) extras.getParcelable("tab_fragment_data");
        }
        return null;
    }

    @k
    public static final void b(@k Intent intent, @l NavigationTabSetItem navigationTabSetItem) {
        if (navigationTabSetItem == null) {
            return;
        }
        TabFragmentFactory.Data dataA = a(intent);
        if (dataA instanceof h) {
            intent.putExtra("tab_fragment_data", ((h) dataA).a(navigationTabSetItem));
        }
    }
}
