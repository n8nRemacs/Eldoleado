package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.ArbiterRepository", f = "ArbiterRepository.kt", i = {0}, l = {109}, m = "getHostList", n = {"this"}, s = {"L$0"})
/* renamed from: ab1.c, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19863c extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public e f21018q;

    /* renamed from: r, reason: collision with root package name */
    public /* synthetic */ Object f21019r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ e f21020s;

    /* renamed from: t, reason: collision with root package name */
    public int f21021t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C19863c(e eVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21020s = eVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21019r = obj;
        this.f21021t |= BeduinInputModel.MIN_TEXT_VERSION;
        return this.f21020s.a(null, this);
    }
}
