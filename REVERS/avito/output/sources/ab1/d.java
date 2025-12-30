package ab1;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "ru.rustore.sdk.pushclient.internal.data.repository.ArbiterRepository", f = "ArbiterRepository.kt", i = {0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 6, 7, 7}, l = {119, 30, 31, 39, 50, 57, 68, 96}, m = "getMasterHost", n = {"this", "$this$withLock_u24default$iv", "this", "$this$withLock_u24default$iv", "defaultHost", "this", "$this$withLock_u24default$iv", "defaultHost", "this", "$this$withLock_u24default$iv", "this", "$this$withLock_u24default$iv", "$this$withLock_u24default$iv", "master", "this", "$this$withLock_u24default$iv", "hosts", "$this$withLock_u24default$iv", "master"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$0", "L$1", "L$0", "L$1", "L$0", "L$1", "L$0", "L$1", "L$2", "L$0", "L$1"})
/* loaded from: classes9.dex */
public final class d extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public Object f21022q;

    /* renamed from: r, reason: collision with root package name */
    public Object f21023r;

    /* renamed from: s, reason: collision with root package name */
    public Object f21024s;

    /* renamed from: t, reason: collision with root package name */
    public /* synthetic */ Object f21025t;

    /* renamed from: u, reason: collision with root package name */
    public final /* synthetic */ e f21026u;

    /* renamed from: v, reason: collision with root package name */
    public int f21027v;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public d(e eVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f21026u = eVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f21025t = obj;
        this.f21027v |= BeduinInputModel.MIN_TEXT_VERSION;
        return this.f21026u.c(this);
    }
}
