package androidx.appcompat.widget;

import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import j.InterfaceC42164u;

/* compiled from: AppCompatTextClassifierHelper.java */
/* loaded from: classes.dex */
final class L {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public TextView f22186a;

    /* renamed from: b, reason: collision with root package name */
    @j.P
    public TextClassifier f22187b;

    /* compiled from: AppCompatTextClassifierHelper.java */
    @j.X
    public static final class a {
        @InterfaceC42164u
        @j.N
        public static TextClassifier a(@j.N TextView textView) {
            TextClassificationManager textClassificationManager = (TextClassificationManager) textView.getContext().getSystemService(TextClassificationManager.class);
            return textClassificationManager != null ? textClassificationManager.getTextClassifier() : TextClassifier.NO_OP;
        }
    }
}
