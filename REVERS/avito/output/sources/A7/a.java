package A7;

import Y61.k;
import android.net.Uri;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;
import kotlin.collections.B0;

/* compiled from: AdvertDetailsImageContentTypeResolver.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LA7/a;", "Lcom/avito/android/analytics/screens/image/c;", "<init>", "()V", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class a implements com.avito.android.analytics.screens.image.c {

    /* renamed from: a, reason: collision with root package name */
    @k
    public Object f108a = B0.f406639b;

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, java.util.Set] */
    @Override // com.avito.android.analytics.screens.image.c
    @k
    public final String a(@k Uri uri) {
        return this.f108a.contains(uri) ? "advertisement" : "complementary";
    }
}
