package aC0;

import Y61.k;
import androidx.compose.runtime.internal.P;
import com.avito.android.remote.model.ButtonAction;
import com.google.gson.annotations.c;
import java.util.List;
import kotlin.Metadata;
import qD0.C47283a;
import vD0.C49197a;

/* compiled from: ConfigureSubCategoriesResult.kt */
@P
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0007\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\rR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u000e\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R \u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u000b\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000b\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"LaC0/a;", "", "", "title", "category", "Lcom/avito/android/remote/model/ButtonAction;", "button", "", "LvD0/a;", "list", "LqD0/a;", "alertInfo", "<init>", "(Ljava/lang/String;Ljava/lang/String;Lcom/avito/android/remote/model/ButtonAction;Ljava/util/List;LqD0/a;)V", "Ljava/lang/String;", "e", "()Ljava/lang/String;", "c", "Lcom/avito/android/remote/model/ButtonAction;", "b", "()Lcom/avito/android/remote/model/ButtonAction;", "Ljava/util/List;", "d", "()Ljava/util/List;", "LqD0/a;", "a", "()LqD0/a;", "_avito_tariff-lf-constructor_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aC0.a, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public final class C19756a {

    @c("alertInfo")
    @k
    private final C47283a alertInfo;

    @c("button")
    @k
    private final ButtonAction button;

    @c("category")
    @k
    private final String category;

    @c("list")
    @k
    private final List<C49197a> list;

    @c("title")
    @k
    private final String title;

    public C19756a(@k String str, @k String str2, @k ButtonAction buttonAction, @k List<C49197a> list, @k C47283a c47283a) {
        this.title = str;
        this.category = str2;
        this.button = buttonAction;
        this.list = list;
        this.alertInfo = c47283a;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final C47283a getAlertInfo() {
        return this.alertInfo;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final ButtonAction getButton() {
        return this.button;
    }

    @k
    /* renamed from: c, reason: from getter */
    public final String getCategory() {
        return this.category;
    }

    @k
    public final List<C49197a> d() {
        return this.list;
    }

    @k
    /* renamed from: e, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
