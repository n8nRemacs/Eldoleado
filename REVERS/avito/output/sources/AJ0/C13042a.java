package Aj0;

import Y61.k;
import Y61.l;
import com.avito.android.remote.model.text.AttributedText;
import com.google.gson.annotations.c;
import kotlin.Metadata;

/* compiled from: GetStepOrderStatusApiResponse.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\nR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u000e\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"LAj0/a;", "", "Lcom/avito/android/remote/model/text/AttributedText;", "description", "LAj0/b;", "primaryAction", "secondaryAction", "", "title", "<init>", "(Lcom/avito/android/remote/model/text/AttributedText;LAj0/b;LAj0/b;Ljava/lang/String;)V", "Lcom/avito/android/remote/model/text/AttributedText;", "a", "()Lcom/avito/android/remote/model/text/AttributedText;", "LAj0/b;", "b", "()LAj0/b;", "c", "Ljava/lang/String;", "d", "()Ljava/lang/String;", "_avito-discouraged_avito-api_autoteka"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Aj0.a, reason: case insensitive filesystem */
/* loaded from: classes17.dex */
public final class C13042a {

    @c("description")
    @l
    private final AttributedText description;

    @c("primaryAction")
    @l
    private final C13043b primaryAction;

    @c("secondaryAction")
    @l
    private final C13043b secondaryAction;

    @c("title")
    @k
    private final String title;

    public C13042a(@l AttributedText attributedText, @l C13043b c13043b, @l C13043b c13043b2, @k String str) {
        this.description = attributedText;
        this.primaryAction = c13043b;
        this.secondaryAction = c13043b2;
        this.title = str;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final AttributedText getDescription() {
        return this.description;
    }

    @l
    /* renamed from: b, reason: from getter */
    public final C13043b getPrimaryAction() {
        return this.primaryAction;
    }

    @l
    /* renamed from: c, reason: from getter */
    public final C13043b getSecondaryAction() {
        return this.secondaryAction;
    }

    @k
    /* renamed from: d, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
