package AB0;

import Y41.l;
import android.net.Uri;
import com.avito.android.deep_linking.links.error.DeeplinkParsingError;
import kotlin.Metadata;
import kotlin.jvm.internal.N;
import kotlin.jvm.internal.s0;

/* compiled from: DeepLinkParsingScope.kt */
@s0
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0004\u0010\b\u001a\u00020\u0004\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\n¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"", "T", "", "cause", "", "invoke", "(Ljava/lang/Throwable;)Ljava/lang/Void;", "Ku/f", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class c extends N implements l {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ Uri f194l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public c(Uri uri) {
        super(1);
        this.f194l = uri;
    }

    @Override // Y41.l
    public final Object invoke(Object obj) throws DeeplinkParsingError.WrongParameterValue {
        throw new DeeplinkParsingError.WrongParameterValue(this.f194l.toString(), "contentJson", DeeplinkParsingError.FieldConstraint.Json.f133987c, (Throwable) obj);
    }
}
