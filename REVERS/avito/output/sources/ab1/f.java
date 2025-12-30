package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.BaseClientAnalyticsRepository", f = "BaseClientAnalyticsRepository.kt", i = {0}, l = {12}, m = "getBaseParams", n = {"this"}, s = {"L$0"})
/* loaded from: classes9.dex */
public final class f extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public g f21036q;

    /* renamed from: r, reason: collision with root package name */
    public /* synthetic */ Object f21037r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ g f21038s;

    /* renamed from: t, reason: collision with root package name */
    public int f21039t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public f(g gVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21038s = gVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21037r = obj;
        this.f21039t |= BeduinInputModel.MIN_TEXT_VERSION;
        return this.f21038s.a(this);
    }
}
