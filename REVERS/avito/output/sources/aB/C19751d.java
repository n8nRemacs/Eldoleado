package aB;

import Ku.AbstractC14350a;
import Ku.i;
import android.net.Uri;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.deep_linking.links.error.DeeplinkParsingError;
import com.avito.android.deep_linking.x;
import com.avito.android.extended_profile_serp.deep_linking.ExtendedProfileSerpLink;
import com.avito.android.remote.model.SearchParamsFactory;
import com.google.gson.Gson;
import kotlin.Metadata;

/* compiled from: ExtendedProfileSerpLinkParser.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"LaB/d;", "LKu/a;", "Lcom/avito/android/extended_profile_serp/deep_linking/ExtendedProfileSerpLink;", "<init>", "()V", "_avito_extended-profile-serp_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aB.d, reason: case insensitive filesystem */
/* loaded from: classes13.dex */
public final class C19751d extends AbstractC14350a<ExtendedProfileSerpLink> {
    @Override // Ku.AbstractC14350a
    public final DeepLink r(Uri uri, Gson gson, x xVar) throws DeeplinkParsingError.WrongParameterValue {
        return new ExtendedProfileSerpLink(i.n(uri, "userKey"), uri.getQueryParameter("context"), SearchParamsFactory.INSTANCE.fromUri(uri), uri.getQueryParameter("s"), null, 16, null);
    }
}
