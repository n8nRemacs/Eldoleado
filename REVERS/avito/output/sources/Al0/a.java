package AL0;

import Y61.k;
import com.avito.android.remote.model.UniversalImage;
import com.avito.android.remote.model.text.AttributedText;
import com.google.gson.annotations.c;
import kotlin.Metadata;

/* compiled from: AdditionalInfo.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"LAL0/a;", "", "Lcom/avito/android/remote/model/text/AttributedText;", "text", "Lcom/avito/android/remote/model/UniversalImage;", "image", "<init>", "(Lcom/avito/android/remote/model/text/AttributedText;Lcom/avito/android/remote/model/UniversalImage;)V", "Lcom/avito/android/remote/model/text/AttributedText;", "b", "()Lcom/avito/android/remote/model/text/AttributedText;", "Lcom/avito/android/remote/model/UniversalImage;", "a", "()Lcom/avito/android/remote/model/UniversalImage;", "_avito_vas-performance_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class a {

    @c("image")
    @k
    private final UniversalImage image;

    @c("text")
    @k
    private final AttributedText text;

    public a(@k AttributedText attributedText, @k UniversalImage universalImage) {
        this.text = attributedText;
        this.image = universalImage;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final UniversalImage getImage() {
        return this.image;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final AttributedText getText() {
        return this.text;
    }
}
