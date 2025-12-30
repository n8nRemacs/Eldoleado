package a91;

import com.avito.android.remote.model.messenger.message.MessageBody;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.MessageBodyAppCallNullable;
import ru.avito.messenger.generated.api.create_item_channel.MessageBodyCompositeNullable;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b(\u0018\u00002\u00020\u0001B\u007f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\b\u0010\r\u001a\u0004\u0018\u00010\f\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0016\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018¢\u0006\u0004\b\u001a\u0010\u001bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u001f\u001a\u0004\b \u0010!R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\"\u001a\u0004\b#\u0010$R\u001c\u0010\t\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010%\u001a\u0004\b&\u0010'R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000b\u0010(\u001a\u0004\b)\u0010*R\u001c\u0010\r\u001a\u0004\u0018\u00010\f8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\r\u0010+\u001a\u0004\b,\u0010-R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000f\u0010.\u001a\u0004\b/\u00100R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u00108\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0011\u00101\u001a\u0004\b2\u00103R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u00128\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0013\u00104\u001a\u0004\b5\u00106R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u00148\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0015\u00107\u001a\u0004\b8\u00109R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u00168\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0017\u0010:\u001a\u0004\b;\u0010<R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u00188\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0019\u0010=\u001a\u0004\b>\u0010?¨\u0006@"}, d2 = {"La91/S;", "", "Lru/avito/messenger/generated/api/create_item_channel/MessageBodyAppCallNullable;", MessageBody.AppCall.TYPE, "La91/T;", "call", "Lru/avito/messenger/generated/api/create_item_channel/MessageBodyCompositeNullable;", "composite", "La91/U;", "file", "", "idempotencyKey", "La91/V;", "image", "La91/W;", "item", "La91/X;", "link", "La91/Y;", "location", "La91/Z;", "text", "La91/a0;", "video", "La91/b0;", "voice", "<init>", "(Lru/avito/messenger/generated/api/create_item_channel/MessageBodyAppCallNullable;La91/T;Lru/avito/messenger/generated/api/create_item_channel/MessageBodyCompositeNullable;La91/U;Ljava/lang/String;La91/V;La91/W;La91/X;La91/Y;La91/Z;La91/a0;La91/b0;)V", "Lru/avito/messenger/generated/api/create_item_channel/MessageBodyAppCallNullable;", "a", "()Lru/avito/messenger/generated/api/create_item_channel/MessageBodyAppCallNullable;", "La91/T;", "b", "()La91/T;", "Lru/avito/messenger/generated/api/create_item_channel/MessageBodyCompositeNullable;", "c", "()Lru/avito/messenger/generated/api/create_item_channel/MessageBodyCompositeNullable;", "La91/U;", "d", "()La91/U;", "Ljava/lang/String;", "e", "()Ljava/lang/String;", "La91/V;", "f", "()La91/V;", "La91/W;", "g", "()La91/W;", "La91/X;", "h", "()La91/X;", "La91/Y;", "i", "()La91/Y;", "La91/Z;", "j", "()La91/Z;", "La91/a0;", "k", "()La91/a0;", "La91/b0;", "l", "()La91/b0;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.S, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19719S {

    @com.google.gson.annotations.c(MessageBody.AppCall.TYPE)
    @Y61.l
    private final MessageBodyAppCallNullable appCall;

    @com.google.gson.annotations.c("call")
    @Y61.l
    private final T call;

    @com.google.gson.annotations.c("composite")
    @Y61.l
    private final MessageBodyCompositeNullable composite;

    @com.google.gson.annotations.c("file")
    @Y61.l
    private final U file;

    @com.google.gson.annotations.c("idempotencyKey")
    @Y61.l
    private final String idempotencyKey;

    @com.google.gson.annotations.c("image")
    @Y61.l
    private final V image;

    @com.google.gson.annotations.c("item")
    @Y61.l
    private final W item;

    @com.google.gson.annotations.c("link")
    @Y61.l
    private final X link;

    @com.google.gson.annotations.c("location")
    @Y61.l
    private final Y location;

    @com.google.gson.annotations.c("text")
    @Y61.l
    private final Z text;

    @com.google.gson.annotations.c("video")
    @Y61.l
    private final a0 video;

    @com.google.gson.annotations.c("voice")
    @Y61.l
    private final b0 voice;

    public C19719S(@Y61.l MessageBodyAppCallNullable messageBodyAppCallNullable, @Y61.l T t12, @Y61.l MessageBodyCompositeNullable messageBodyCompositeNullable, @Y61.l U u12, @Y61.l String str, @Y61.l V v12, @Y61.l W w12, @Y61.l X x12, @Y61.l Y y12, @Y61.l Z z12, @Y61.l a0 a0Var, @Y61.l b0 b0Var) {
        this.appCall = messageBodyAppCallNullable;
        this.call = t12;
        this.composite = messageBodyCompositeNullable;
        this.file = u12;
        this.idempotencyKey = str;
        this.image = v12;
        this.item = w12;
        this.link = x12;
        this.location = y12;
        this.text = z12;
        this.video = a0Var;
        this.voice = b0Var;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final MessageBodyAppCallNullable getAppCall() {
        return this.appCall;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final T getCall() {
        return this.call;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final MessageBodyCompositeNullable getComposite() {
        return this.composite;
    }

    @Y61.l
    /* renamed from: d, reason: from getter */
    public final U getFile() {
        return this.file;
    }

    @Y61.l
    /* renamed from: e, reason: from getter */
    public final String getIdempotencyKey() {
        return this.idempotencyKey;
    }

    @Y61.l
    /* renamed from: f, reason: from getter */
    public final V getImage() {
        return this.image;
    }

    @Y61.l
    /* renamed from: g, reason: from getter */
    public final W getItem() {
        return this.item;
    }

    @Y61.l
    /* renamed from: h, reason: from getter */
    public final X getLink() {
        return this.link;
    }

    @Y61.l
    /* renamed from: i, reason: from getter */
    public final Y getLocation() {
        return this.location;
    }

    @Y61.l
    /* renamed from: j, reason: from getter */
    public final Z getText() {
        return this.text;
    }

    @Y61.l
    /* renamed from: k, reason: from getter */
    public final a0 getVideo() {
        return this.video;
    }

    @Y61.l
    /* renamed from: l, reason: from getter */
    public final b0 getVoice() {
        return this.voice;
    }
}
