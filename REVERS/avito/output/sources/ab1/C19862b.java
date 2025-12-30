package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.ArbiterRepository", f = "ArbiterRepository.kt", i = {0, 0, 1}, l = {119, 104}, m = "clear", n = {"this", "$this$withLock_u24default$iv", "$this$withLock_u24default$iv"}, s = {"L$0", "L$1", "L$0"})
/* renamed from: ab1.b, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19862b extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public Object f21013q;

    /* renamed from: r, reason: collision with root package name */
    public kotlinx.coroutines.sync.d f21014r;

    /* renamed from: s, reason: collision with root package name */
    public /* synthetic */ Object f21015s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ e f21016t;

    /* renamed from: u, reason: collision with root package name */
    public int f21017u;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C19862b(e eVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21016t = eVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21015s = obj;
        this.f21017u |= BeduinInputModel.MIN_TEXT_VERSION;
        return this.f21016t.b(this);
    }
}
